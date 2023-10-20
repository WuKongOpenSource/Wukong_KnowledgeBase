<template>
  <div
    v-loading="loading"
    class="content-container">
    <flexbox
      v-if="canAccess && detailData"
      class="main-top">
      <flexbox-item class="title">
        <span>{{ detailData.title }}</span><span>{{ $getUserName(detailData.createUser) }}于{{ detailData.createTime }}创建</span>
      </flexbox-item>
      <div class="control">
        <el-button
          v-if="[1, 2, 5].includes(currentUserAuth)"
          icon="wk wk-edit"
          type="text"
          class="control-btn"
          @click="isEdit = !isEdit">
          编辑
        </el-button>
        <el-button
          v-if="currentUserAuth === 1"
          icon="wk wk-employees"
          type="text"
          class="control-btn"
          @click="showGroupControl = !showGroupControl">
          协作
        </el-button>
        <el-button
          :icon="detailData.collectStatus === 0 ? 'wk wk-focus-on' : 'wk wk-focus-on collected'"
          type="text"
          class="control-btn"
          @click="handleCollection">
          {{ detailData.collectStatus === 0 ? '关注' : '取消关注' }}
        </el-button>

        <!-- share -->
        <el-popover
          placement="bottom"
          width="300"
          trigger="click"
          popper-class="no-padding-popover">
          <div v-if="detailData.share" v-loading="shareLoading" class="share-container">
            <div class="section">
              <div class="share-title">
                分享链接文档
              </div>
              <div class="share-title-desc">
                分享开启后，任何人都可以通过公开链接的方式，查询当前文档的内容
              </div>
              <flexbox
                align="center"
                justify="center"
                direction="column"
                class="qrcode-wrapper">
                <qrcode
                  :value="shareUrl"
                  :options="qrcodeOption"
                  class="qrcode" />
                <span class="qrcode-desc">
                  使用手机扫描二维码即可在手机上查看
                </span>
              </flexbox>
              <el-input
                :value="shareUrl"
                disabled
                class="link-input">
                <el-button
                  slot="append"
                  v-clipboard:copy="shareUrl"
                  v-clipboard:success="clipboardSuccess">
                  复制链接
                </el-button>
              </el-input>
            </div>
            <div class="section">
              <div class="share-title">
                分享给团队成员
              </div>
              <flexbox class="user-select">
                <wk-user-dialog-select
                  v-model="shareUserList"
                  :radio="false"
                  style="flex: 1;" />
                <div class="user-select__append">
                  <el-button @click="shareMemberClick">
                    立即分享
                  </el-button>
                </div>
              </flexbox>
            </div>
            <el-button
              type="primary"
              class="share-control"
              @click="shareClick('close')">
              关闭分享
            </el-button>
            <div class="desc">
              关闭分享后已分享链接将失效，重新打开分享将生成新的二维码链接
            </div>
          </div>
          <div v-else v-loading="shareLoading" class="share-container">
            <div class="section">
              <div class="share-title">
                分享链接文档
              </div>
              <img src="@/assets/img/knowledge/share.png" alt="" class="share-image">
              <div
                style="margin-bottom: 16px;"
                class="share-title-desc">
                分享开启后，任何人都可以通过公开链接的方式，查询当前文档的内容
              </div>
            </div>
            <el-button
              type="primary"
              class="share-control"
              style="margin-bottom: 16px;"
              @click="shareClick('open')">
              开启分享
            </el-button>
          </div>
          <el-button
            slot="reference"
            class="margin-left-interval"
            type="primary">
            分享
          </el-button>
        </el-popover>

        <!-- more -->
        <el-dropdown
          v-if="currentUserAuth === 1"
          trigger="click"
          @command="handleCommand">
          <el-button class="dropdown-btn" icon="el-icon-more" />
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="move">移动文档至</el-dropdown-item>
            <el-dropdown-item command="delete">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </flexbox>

    <div v-if="canAccess" class="content-body">
      <div class="content">
        <template v-if="detailData">
          <div
            v-if="!detailData.content"
            class="empty">
            这个文档是空的
          </div>
          <tinymce
            ref="createTinymce"
            :value="detailData.content"
            :toolbar="[]"
            :plugins="[]"
            :init="getEditConfig()"
            class="rich-txt" />
        </template>
      </div>

      <flexbox
        v-if="detailData"
        align="center"
        justify="flex-start"
        class="praise-wrapper">
        <flexbox-item>
          <el-button
            :class="{active: Boolean(detailData.faverStatus)}"
            type="text"
            icon="wk wk-good"
            class="control-btn"
            @click="handleStar">
            赞
          </el-button>
          <span class="desc">{{ starStr }}</span>
        </flexbox-item>
        <ul class="label-list">
          <li
            v-for="(item, index) in detailData.labelList"
            :key="index"
            :style="{backgroundColor: item.color}"
            class="label-list-item"
            @click="handleToFilter(item.labelId)">
            {{ item.name }}
          </li>
          <span v-if="detailData.labelList && detailData.labelList.length > 0" class="wk wk-label icon" />
        </ul>
      </flexbox>

      <div
        v-if="showReply"
        class="comment-wrapper">
        <div class="comment-title">
          {{ replyTotal > 0 ? `${replyTotal}条评论` : '暂无评论' }}
        </div>
        <reply-comment
          ref="f_reply"
          v-loading="commentLoading"
          @toggle="closeOtherReply"
          @reply="handleReply" />
        <comment-list
          v-if="replyList.length > 0"
          ref="comment_list"
          :props="commentListProps"
          :list="replyList"
          @delete="deleteComment"
          @close-other-reply="$refs.f_reply.toggleFocus(true)" />
      </div>
    </div>

    <no-data v-if="!canAccess" />
    <group-control
      v-if="showGroupControl"
      :data="detailData"
      @close="showGroupControl = false"
      @update="getDetail" />

    <edit-document
      v-if="isEdit"
      :data="detailData"
      @back="isEdit = !isEdit"
      @update="getDetail" />
  </div>
</template>

<script>
import { kmDocQueryByIdAPI, kmDocStarAPI } from '@/api/knowledge/doc'
import { kmCollectAddAPI, kmCollectCancelAPI, kmLibraryShareOpenAPI, kmLibraryShareCloseAPI, kmLibraryShareMemberAPI } from '@/api/knowledge/index'
import { setCommentAPI, queryCommentListAPI, deleteCommentAPI } from '@/api/oa/common'

import Tinymce from '@/components/Tinymce'
import ReplyComment from '@/components/ReplyComment'
import CommentList from '@/components/CommentList'
import EditDocument from './EditDocument'
import GroupControl from './GroupControl'
import WkUserDialogSelect from '@/components/NewCom/WkUserDialogSelect'
import NoData from './NoData'

import { downloadFile } from '@/utils'
import Qrcode from '@chenfengyuan/vue-qrcode'
import { mapGetters } from 'vuex'
import clipboard from '@/directives/clipboard/index.js' // use clipboard by v-directive

export default {
  name: 'ContentWord',
  directives: {
    clipboard
  },
  components: {
    ReplyComment,
    CommentList,
    EditDocument,
    GroupControl,
    Tinymce,
    WkUserDialogSelect,
    Qrcode,
    NoData
  },
  props: {
    id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      editHeight: document.documentElement.clientHeight - 280,
      detailData: null,
      loading: false,
      commentLoading: false,

      timer: null,

      showGroupControl: false,
      isEdit: false,

      replyList: [],
      showReply: true,

      // 分享
      shareLoading: false,
      // 二维码
      qrcodeOption: {
        width: 500,
        height: 500,
        colorDark: '#000000',
        colorLight: '#ffffff'
      },
      shareUserList: []
    }
  },
  computed: {
    ...mapGetters([
      'userInfo'
    ]),
    // 当前用户的权限  1 管理权限 2 编辑权限 3 只读权限
    currentUserAuth() {
      if (this.detailData && this.detailData.auth) {
        return this.detailData.auth.currentUserAuth || 3
      }
      return 3
    },
    // 能访问内容
    canAccess() {
      return this.detailData && this.detailData.currentUserAuth > 0
    },
    // 评论总数
    replyTotal() {
      let num = 0
      this.replyList.forEach(item => {
        num++
        num += item.childCommentList.length || 0
      })
      return num
    },
    // 文档赞
    starStr() {
      const userList = this.detailData.starUserList // 列表中包含有自己
      if (userList.length === 0) return ''

      if (this.detailData.faverStatus) {
        // 自己已赞
        if (userList.length > 1) {
          return `您和其他${userList.length - 1}人`
        } else {
          return '您赞了该文档'
        }
      } else {
        // 自己未赞
        if (userList.length > 1) {
          return `${this.$getUserName(userList[0])}和其他${userList.length - 1}人`
        } else {
          return `${this.$getUserName(userList[0])}赞了该文档`
        }
      }
    },
    // 分享地址
    shareUrl() {
      if (this.detailData && this.detailData.share) {
        return `${WKConfig.getLocationOrigin()}/share/#/${this.detailData.share.token}`
      }
      return ''
    },

    // 评论列表配置
    commentListProps() {
      return {
        addRequest: setCommentAPI, // 添加请求和参数
        addParams: { typeId: this.id, type: '3' }, // 3 知识库 2 日志 1 任务
        replyKey: 'pid', // 日志 任务 pid  阶段 replyId
        replyValueKey: 'userId', // 获取值的keys 日志 任务 userId  阶段 user.userId
        deleteRequest: deleteCommentAPI, // 删除请求和参数
        deleteParams: null
      }
    },
    tinymce() {
      return this.$refs.createTinymce.editor
    }
  },
  watch: {
    id: {
      handler() {
        this.isEdit = false
        document.querySelector('#app').click()
        this.showGroupControl = false
        if (this.id) {
          this.getDetail()
          this.getCommentList()
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    /**
     * 获取文档内容
     */
    getDetail() {
      if (this.id) {
        this.loading = true
        kmDocQueryByIdAPI({
          documentId: this.id
        }).then(res => {
          this.loading = false
          this.detailData = res.data
        }).catch(() => {
          this.loading = false
        })
      }
    },

    handleCommand(command) {
      switch (command) {
        case 'delete':
          this.$emit('delete', this.detailData)
          break
        case 'move':
          this.$emit('move', this.detailData)
          break
      }
    },

    /**
     * 根据标签筛选文档
     */
    handleToFilter(labelId) {
      this.$router.push({
        path: '/knowledge/filterByLabel',
        query: {
          labelId: labelId
        }
      })
    },

    /**
     * 关注
     */
    handleCollection() {
      const request = this.detailData.collectStatus === 0 ? kmCollectAddAPI : kmCollectCancelAPI
      request({
        type: this.detailData.type,
        typeId: this.detailData.documentId
      }).then(() => {
        this.$message.success(this.detailData.collectStatus === 0 ? '关注成功' : '已取消关注')
        this.detailData.collectStatus = this.detailData.collectStatus === 0 ? 1 : 0
      }).catch(() => {})
    },

    /**
     * 点赞
     */
    handleStar() {
      kmDocStarAPI({
        documentId: this.id
      }).then(() => {
        const status = this.detailData.faverStatus == 0 ? 1 : 0
        if (status == 1) {
          this.detailData.starUserList.push(this.userInfo)
        } else {
          this.detailData.starUserList = this.detailData.starUserList.filter(item => item.userId != this.userInfo.userId)
        }
        this.$set(this.detailData, 'faverStatus', status)
      }).catch()
    },

    /**
     * 获取全部回复列表
     */
    getCommentList() {
      queryCommentListAPI({
        typeId: this.id,
        type: 3
      }).then(res => {
        this.replyList = res.data || []
      }).catch(() => {})
    },
    /**
     * 切换回复框
     */
    closeOtherReply(flag) {
      if (!flag && this.$refs.comment_list) {
        this.$refs.comment_list.closeReply()
      }
    },
    /**
     * 删除某条一级回复
     */
    deleteComment(index) {
      this.replyList.splice(index, 1)
    },
    /**
     * 新增一级回复
     */
    handleReply(data) {
      this.commentLoading = true
      setCommentAPI({
        content: data,
        ...this.commentListProps.addParams
      }).then(res => {
        res.data.user = {
          userId: this.userInfo.userId,
          realname: this.$getUserName(this.userInfo),
          img: this.userInfo.img
        }
        res.data.childCommentList = []
        this.replyList = [res.data].concat(this.replyList)
        this.commentLoading = false
        this.showReply = false
        this.$nextTick(() => {
          this.showReply = true
        })
      }).catch(() => {
        this.commentLoading = false
      })
    },

    /**
     * 分享点击
     */
    shareClick(type) {
      if (type == 'open') {
        this.shareLoading = true
        kmLibraryShareOpenAPI({
          documentId: this.id
        }).then(res => {
          this.shareLoading = false
          this.$set(this.detailData, 'share', res.data)
        }).catch(
          this.shareLoading = false
        )
      } else if (type == 'close') {
        this.shareLoading = true
        kmLibraryShareCloseAPI({
          shareId: this.detailData.share.shareId
        }).then(() => {
          this.shareLoading = false
          this.$set(this.detailData, 'share', null)
        }).catch(
          this.shareLoading = false
        )
      }
    },

    /**
     * 复制地址
     */
    clipboardSuccess() {
      this.$message.success('复制成功')
    },

    shareMemberClick() {
      if (!this.shareUserList.length) {
        return
      }
      kmLibraryShareMemberAPI({
        documentId: this.id,
        shareId: this.detailData.share.shareId,
        shareUserIds: this.shareUserList.join(',')
      }).then(() => {
        this.shareUserList = []
        this.$message.success('已分享成功')
      }).catch()
    },

    /**
     * @description: 初始化tinymce
     * @return {*}
     */
    getEditConfig() {
      return {
        menubar: false,
        toolbar_sticky: true,
        statusbar: false,
        quickbars_selection_toolbar: false,
        content_style: ' * {color: #262626; outline: unset !important} ',
        plugins: 'autoresize',
        contextmenu: false,
        mage_advtab: false,
        table_responsive_width: false,
        object_resizing: false,
        setup: (editor) => {
          // 文档禁止编辑
          editor.on('init', () => {
            editor.getBody().setAttribute('contenteditable', 'false')
          })

          // 处理点击附件名称操作
          let url = ''
          let name = ''
          editor.ui.registry.addContextToolbar('customToolbar', {
            items: 'previewFile ｜ downloadFile',
            predicate: (node) => {
              if (node.tagName == 'SPAN' && node.getAttribute('data-wk-file-type') == 'file') {
                url = node.getAttribute('data-wk-file-url')
                name = node.innerText
              }
              return node.tagName == 'SPAN' && node.getAttribute('data-wk-file-type') == 'file'
            }
          })

          editor.ui.registry.addButton('previewFile', {
            text: '<span style="cursor: pointer;">预览</span>',
            onAction: () => {
              this.preview({
                url,
                name
              })
            }
          })

          editor.ui.registry.addButton('downloadFile', {
            text: '<span style="cursor: pointer;">下载</span>',
            onAction: () => {
              this.downloadFile({
                path: url,
                name
              })
            }
          })
        }
      }
    },

    /**
     * @description: 预览附件
     * @param {*} data
     * @return {*}
     */
    preview(data) {
      this.$wkPreviewFile.preview({
        index: 0,
        data: [data]
      })
    },

    /**
     * @description: 下载附件
     * @param {*} file
     * @return {*}
     */
    downloadFile(file) {
      downloadFile(file)
    }
  }
}
</script>

<style scoped lang="scss">
  .content-container {
    height: 100%;
    padding: 32px;

    .main-top {
      .title {
        color: $--color-text-secondary;

        span:first-child {
          margin-right: 4px;
        }
      }

      .control {
        .dropdown-btn {
          padding: 8px;
        }

        .el-dropdown {
          margin-left: 8px;
        }

        ::v-deep .wk-focus-on.collected {
          color: #fac23d;
        }
      }
    }

    .content-body {
      width: 100%;
      height: calc(100% - 34px);
      overflow: auto;

      .content {
      }

      .empty {
        padding-top: 80px;
        font-size: 14px;
        color: $--color-text-placeholder;
        text-align: center;
      }

      .praise-wrapper {
        .control-btn.active {
          color: $--color-primary;

          ::v-deep i {
            color: $--color-primary;
          }
        }

        .desc {
          margin-left: 20px;
        }

        .label-list {
          display: flex;
          align-items: center;
          justify-content: flex-end;

          .label-list-item {
            padding: 4px 8px;
            margin: 0 4px;
            font-size: 12px;
            color: white;
            cursor: pointer;
            border-radius: $--border-radius-base;
          }
        }
      }

      .comment-wrapper {
        width: 100%;

        .comment-title {
          margin: 16px 0;
          font-size: 16px;
          font-weight: bold;
        }

        .comment-list {
          margin-top: 20px;
        }
      }
    }
  }

  .slide-wrapper {
    position: absolute;
    top: 0;
    right: 0;
    width: 350px;
    height: 100%;
  }

  ::v-deep .tox-tinymce {
    border: 0 none;
  }

  .share-container {
    padding: 0 20px;

    .section {
      padding-top: 16px;
      border-bottom: $--border-base;

      .share-title {
        font-size: 16px;
        font-weight: bold;
        color: $--color-text-regular;
      }

      .share-title-desc {
        margin-top: 4px;
        font-size: 14px;
        color: $--color-n100;
      }

      .share-image {
        width: 100%;
        padding: 30px 20px 20px;
      }

      .qrcode-wrapper {
        .qrcode {
          display: inline-block;
          width: 110px !important;
          height: 110px !important;
          margin: 24px 0 8px;
          border: $--border-base;
        }

        .qrcode-desc {
          font-size: 12px;
          color: $--color-text-secondary;
          text-align: center;
        }
      }

      .link-input {
        width: 100%;
        margin: 16px 0;

        ::v-deep .el-input-group__append {
          background-color: $--color-primary;
          border-color: $--color-primary;
        }

        .el-button {
          font-size: 12px;
          color: white;
        }
      }
    }

    .share-control {
      width: 100%;
      margin-top: 16px;
    }

    .desc {
      padding: 16px 4px;
      font-size: 12px;
      color: $--color-n100;
      text-align: center;
    }

    .user-select {
      padding: 8px 0 16px;

      .wk-user-select {
        height: 32px !important;
        min-height: 32px !important;
        overflow-y: hidden;
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
      }

      &__append {
        position: relative;
        white-space: nowrap;
        background-color: $--color-primary;
        border: $--border-base;
        border-color: $--color-primary;
        border-left: 0;
        border-radius: 3px;
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;

        .el-button {
          font-size: 12px;
          color: white;
          background-color: transparent;
          border-color: transparent;
        }
      }
    }
  }
</style>

