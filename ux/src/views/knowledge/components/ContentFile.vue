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
        <el-dropdown
          v-if="currentUserAuth === 1"
          trigger="click"
          @command="handleCommand">
          <el-button class="dropdown-btn" icon="el-icon-more" />
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="move">移动文件至</el-dropdown-item>
            <el-dropdown-item command="delete">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </flexbox>

    <div
      v-if="canAccess"
      class="content-file">
      <flexbox class="doc-title">
        <flexbox-item class="text-one-line">
          {{ detailData.title }}<span v-if="detailData.fileSize" class="doc-des">（{{ detailData.fileSize | getFileSize }}）</span>
        </flexbox-item>
        <el-button
          v-if="(canPreview || isImg) && currentUserAuth != 3"
          type="primary"
          @click="handleDownloadFile">
          下载文件
        </el-button>
      </flexbox>
      <div
        v-if="isImg"
        :style="{height: editHeight + 'px'}"
        class="img-view">
        <img
          v-src="docContent"
          alt="">
      </div>
      <!-- <perview
        v-else-if="docContent"
        :path="docContent"
        :name="detailData.title"
        :style="{height: editHeight + 'px'}"
        style="width: 100%;"
        class="preview-file"
      /> -->
      <iframe
        v-else-if="docContent"
        :src="getPreviewUrl(docContent, detailData.title)"
        :style="{height: editHeight + 'px'}"
        style="width: 100%;"
        frameborder="0"
        class="preview-file" />
    </div>

    <no-data v-if="!canAccess" />

    <group-control
      v-if="showGroupControl"
      :data="detailData"
      @close="showGroupControl = false"
      @update="getDetail" />
  </div>
</template>

<script>
import { kmDocQueryByIdAPI } from '@/api/knowledge/doc'
import { kmCollectAddAPI, kmCollectCancelAPI } from '@/api/knowledge/index'

import NoData from './NoData'
// import Perview from '@/views/system/perview'

import GroupControl from './GroupControl'
import { downloadFile, fileSize } from '@/utils'
import { canPreviewFile, wkPreviewFileUrl } from '@/utils'

export default {
  name: 'ContentFile',
  components: {
    GroupControl,
    NoData
    // Perview // 文件预览
  },
  filters: {
    getFileSize(size) {
      return fileSize(Number(size))
    }
  },
  props: {
    id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      docContent: '',
      editHeight: document.documentElement.clientHeight - 200,
      detailData: null,
      loading: false,

      timer: null,

      showGroupControl: false
    }
  },
  computed: {
    isImg() {
      if (!this.detailData || this.detailData.type === 3) return false
      const arr = ['jpg', 'png', 'jpeg', 'gif']
      const lastIndex = this.detailData.title.lastIndexOf('.')
      if (lastIndex === -1) return false
      const suffix = this.detailData.title.slice(lastIndex + 1)
      return arr.includes(suffix)
    },

    // 当前用户的权限  1 管理权限 2 编辑权限 3 只读权限
    currentUserAuth() {
      return Number(this.detailData.auth.currentUserAuth)
    },

    // 能访问内容
    canAccess() {
      return this.detailData && this.detailData.currentUserAuth > 0
    },

    // 判断是否能预览 能预览的展示下载。不能预览的在提示页面 下载
    canPreview() {
      const name = this.detailData.title
      return canPreviewFile(name)
    }
  },
  watch: {
    id: {
      handler() {
        this.showGroupControl = false
        this.getDetail()
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    /**
     * @description: 获取预览地址
     * @param {*} path 地址
     * @param {*} name 名称
     * @return {*}
     */
    getPreviewUrl(path, name) {
      return wkPreviewFileUrl(path, name)
    },

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
          this.detailData = null
          this.docContent = null
          this.$nextTick(() => {
            this.detailData = res.data
            this.docContent = res.data.content || ''
          })
        }).catch(() => {
          this.loading = false
        })
      }
    },

    // 文件下载
    handleDownloadFile() {
      downloadFile({
        path: this.detailData.content,
        name: this.detailData.title
      })
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

    .content-file {
      overflow: auto;

      .doc-title {
        margin-top: 8px;
        margin-bottom: 16px;
        font-size: 24px;
      }

      .doc-des {
        font-size: 14px;
        color: $--color-text-placeholder;
      }

      .img-view {
        width: 100%;
        padding: 24px;
        overflow: auto;
        text-align: center;
        background-color: $--background-color-base;
        border-radius: $--border-radius-base;

        img {
          margin: auto;
          vertical-align: middle;
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

  .preview-file {
    overflow: hidden;
    border-radius: $--border-radius-base;
  }
</style>

