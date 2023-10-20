<template>
  <div
    v-loading="loading"
    class="content-main">
    <wk-page-header
      :title="detailData.name"
      :dropdowns="headerDropdowns"
      @command="pageHeaderCommand" />
    <!-- <wk-filter-header
      :tabs="tabs"
      :props="{
        showSearch: false
      }"
      :active-tab.sync="activeTab"
      @tabs-change="handleChange"
    /> -->

    <div class="kl-desc">{{ detailData.description || '暂无描述信息' }}</div>

    <section class="section static">
      <div class="section-body">
        <el-tabs
          v-model="activeTab"
          @tab-click="handleChange">
          <el-tab-pane label="全部文档" name="all" />
          <el-tab-pane label="我关注的" name="collect" />
        </el-tabs>

        <div
          v-loading="listLoading"
          v-infinite-scroll="getList"
          class="list">
          <flexbox
            v-for="(item, index) in docList"
            :key="index"
            class="list-item"
            @click.native="handleToDetail(item)">
            <i
              v-if="item.type === 2"
              class="wk wk-folder file-icon folder" />
            <i
              v-else-if="item.type === 3"
              class="wk wk-doc file-icon doc" />
            <img
              v-else
              :src="getFileIcon(item.title)"
              alt=""
              class="img-icon">
            <flexbox-item class="list-item__name text-one-line">{{ item.title }}<span v-if="item.fileSize" class="list-item__info">（{{ item.fileSize | getFileSize }}）</span></flexbox-item>
            <flexbox-item v-if="item.aiSyncStatus" class="list-item__name text-one-line">
              <el-tag
                :type="{
                  1:'',
                  2:'success',
                  3:'danger'
                }[item.aiSyncStatus]">
                <i v-if="item.aiSyncStatus == 1" class="el-icon-loading" />
                {{ AISyncStatus[item.aiSyncStatus] }}
              </el-tag>
            </flexbox-item>
            <span v-if="item.createTime" class="list-item__desc">创建于{{ item.createTime }}</span>
            <i
              :class="{active: item.collectStatus != 0}"
              class="wk wk-focus-on collect-btn"
              @click.stop="handleCollection(item)" />
          </flexbox>
          <no-data v-if="docList.length === 0" :text="tips" />
        </div>
      </div>
    </section>

    <edit-dialog
      v-if="showEdit"
      :data="detailData"
      @close="showEdit = false"
      @update="getDetail(true)" />
  </div>
</template>

<script>
import {
  kmLibraryQueryByIdAPI,
  kmLibraryQueryDocumentByIdAPI,
  kmLibraryQueryCollectDocumentByIdAPI,
  kmLibraryDeleteByIdAPI,
  kmCollectAddAPI,
  kmCollectCancelAPI
} from '@/api/knowledge/index'

import EditDialog from '../components/EditDialog'
import NoData from './NoData'
import WkPageHeader from '@/components/Page/WkPageHeader'
// import WkFilterHeader from '@/components/NewCom/WkFilterHeader'

import { getFileIconWithSuffix, fileSize } from '@/utils/index'

export default {
  name: 'ContentMain',
  components: {
    EditDialog,
    NoData,
    WkPageHeader
    // WkFilterHeader
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
      activeTab: 'all',

      showEdit: false,
      detailData: {},
      docList: [],

      loading: false,
      listLoading: false,
      page: 0,
      overFlag: false,

      AISyncStatus: Object.freeze(['', '解析中', '解析成功', '解析失败'])
      // 筛选
      // tabs: [{ label: '全部文档', value: 'all' }, { label: '我关注的', value: 'collect' }]
    }
  },
  computed: {
    tips() {
      const canAccess = this.detailData && this.detailData.currentUserAuth > 0
      return canAccess ? '暂无数据' : '暂无数据'
    },
    // 头部更多事件
    headerDropdowns() {
      if (this.detailData.isEdit) {
        return [{ command: 'manage', name: '设置' },
          { command: 'delete', name: '删除' }]
      }
      return []
    }
  },
  created() {
    this.getDetail()
  },
  methods: {
    /**
     * @description: 头部更多点击事件
     * @param {*}
     * @return {*}
     */
    pageHeaderCommand(type) {
      if (type === 'manage') {
        this.showEdit = true
      } else if (type === 'delete') {
        this.handleDelete()
      }
    },

    /**
     * 获取知识库详情
     * isSet 是true 刷新父组件数据
     */
    getDetail(isSet = false) {
      this.loading = true
      kmLibraryQueryByIdAPI({
        libraryId: this.id
      }).then(res => {
        console.log('detail res: ', res)
        this.loading = false
        this.detailData = res.data
        if (isSet) {
          this.$emit('update', this.detailData)
        }
      }).catch(() => {
        this.loading = false
      })
    },

    getFileIcon(filename) {
      const lastIndex = filename.lastIndexOf('.')
      const suffix = filename.slice(lastIndex + 1)
      return getFileIconWithSuffix(suffix)
    },

    /**
     * 切换tab
     */
    handleChange() {
      this.page = 0
      this.overFlag = false
      this.getList()
    },

    /**
     * 获取文档统计数据
     */
    getList() {
      if (this.overFlag || this.listLoading) return
      this.listLoading = true
      this.page++
      const request = this.activeTab === 'all' ? kmLibraryQueryDocumentByIdAPI : kmLibraryQueryCollectDocumentByIdAPI
      request({
        libraryId: this.id,
        page: this.page
      }).then(res => {
        if (this.page === 1) {
          this.docList = []
        }
        this.docList = this.docList.concat(res.data.list || [])
        this.overFlag = res.data.lastPage
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },

    /**
     * 删除
     */
    handleDelete() {
      this.$confirm('您确定要删除该知识库和该知识库下的所有文件吗?<br />删除后可在回收站恢复知识库。', '提示', {
        confirmButtonText: '确定',
        dangerouslyUseHTMLString: true,
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        kmLibraryDeleteByIdAPI(this.id).then(() => {
          this.$message.success('删除成功!')
          this.$router.go(-1)
        }).catch(() => {})
      }).catch(() => {
      })
    },

    /**
     * 判断是文件夹还是文档
     */
    getType(data) {
      return data.hasOwnProperty('documentId') ? 'doc' : 'folder'
    },

    /**
     * 关注/取消关注
     */
    handleCollection(data) {
      const type = this.getType(data)
      const params = {
        type: type === 'folder' ? 2 : data.type,
        typeId: type === 'folder' ? data.folderId : data.documentId
      }
      const request = data.collectStatus === 0 ? kmCollectAddAPI : kmCollectCancelAPI
      request(params).then(() => {
        this.$message.success(data.collectStatus === 0 ? '关注成功' : '已取消关注')
        data.collectStatus = data.collectStatus === 0 ? 1 : 0
        this.handleChange()
      }).catch(() => {})
    },

    /**
     * 跳转到详情
     */
    handleToDetail(data) {
      this.$emit('next', data)
    }
  }
}
</script>

<style scoped lang="scss">
  .content-main {
    padding: 32px;

    .wk-filter-header {
      margin-top: 24px;
    }

    .kl-desc {
      padding: 15px;
      margin-top: 8px;
      line-height: 1.5;
      background-color: $--background-color-base;
      border-radius: $--border-radius-base;
    }

    .section {
      margin-top: 8px;

      .section-header__text {
        font-size: 16px;
        font-weight: 600;
      }

      &.static {
        .el-tabs {
          ::v-deep .el-tabs__header {
            margin-bottom: 0;
          }

          ::v-deep .el-tabs__header .el-tabs__nav-wrap::after {
            height: 1px;
          }
        }

        .list {
          .list-item {
            position: relative;
            line-height: 40px;
            cursor: pointer;
            border-bottom: $--border-base;

            .file-icon {
              font-size: 16px;
              color: $--color-text-regular;
            }

            .img-icon {
              width: 16px;
            }

            .list-item__name {
              font-size: 14px;
              color: $--color-text-primary;
            }

            .list-item__desc {
              margin: 0 16px;
              font-size: 14px;
              color: $--color-text-secondary;
            }

            .list-item__info {
              font-size: 14px;
              color: $--color-text-placeholder;
            }

            .collect-btn {
              margin-right: 16px;
              font-size: 14px;
              color: #e2e4e9;

              &.active {
                color: #fac23d;
              }
            }

            &:hover {
              background-color: #f7f7f7;
            }
          }
        }
      }
    }
  }
</style>
