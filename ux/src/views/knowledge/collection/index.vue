<template>
  <div class="knowledge-collection">
    <wk-page-header
      title="我的关注" />
    <wk-filter-header
      :tabs="tabsList"
      :active-tab.sync="activeTab"
      :props="{
        tabSetShow: false,
        showSearch: false
      }"
      @tabs-change="refreshList"
    />

    <div class="container">
      <el-table
        v-loading="loading"
        :data="dataList"
        height="calc(100% - 45px)"
        @row-click="handleRowClick">
        <el-table-column
          show-overflow-tooltip
          label="名称">
          <template slot-scope="scope">
            <div class="file-main">
              <i v-if="scope.row.type === 1" class="wk wk-book file-icon library" />
              <i v-else-if="scope.row.type === 2" class="wk wk-folder file-icon folder" />
              <i v-else-if="scope.row.type === 3" class="wk wk-doc file-icon doc" />
              <img v-else :src="getFileIcon(scope.row.title)" alt="" class="img-icon">
              <span class="file-name">
                {{ scope.row.type === 1 ? scope.row.libraryName : scope.row.title }}
              </span>
              <span
                v-if="scope.row.description"
                class="file-desc">
                {{ scope.row.description }}
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          v-if="activeTab != 'library'"
          show-overflow-tooltip
          label="所属知识库"
          prop="libraryName" />
        <el-table-column
          v-if="activeTab == 'file'"
          show-overflow-tooltip
          label="文件大小"
          width="120">
          <template slot-scope="scope">
            {{ scope.row.fileSize | getFileSize }}
          </template>
        </el-table-column>
        <el-table-column
          show-overflow-tooltip
          label="关注时间"
          width="240">
          <template slot-scope="scope">
            关注于{{ scope.row.createTime }}
          </template>
        </el-table-column>
        <el-table-column
          show-overflow-tooltip
          width="80"
          align="center"
          fixed="right">
          <template
            slot="header"
            slot-scope="{}">
            <i class="el-icon-star-off focus-icon is-disabled" />
          </template>
          <template slot-scope="scope">
            <el-button
              type="text"
              style="padding: 0;"
              @click.stop="cancelCollection(scope.row)">
              <i class="wk wk-focus-on file-collect-icon" />
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="p-contianer">
        <el-pagination
          :current-page="currentPage"
          :page-sizes="pageSizes"
          :page-size="pageSize"
          :total="total"
          :pager-count="5"
          class="p-bar"
          background
          layout="prev, pager, next, sizes, total, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </div>
    </div>
  </div>
</template>

<script>
import { kmCollectListAPI, kmCollectCancelAPI } from '@/api/knowledge/index'

import WkPageHeader from '@/components/Page/WkPageHeader'
import WkFilterHeader from '@/components/NewCom/WkFilterHeader'

import { getFileIconWithSuffix, fileSize } from '@/utils/index'

export default {
  name: 'KnowledgeCollection',
  components: {
    WkPageHeader,
    WkFilterHeader
  },
  filters: {
    getFileSize(size) {
      return fileSize(Number(size))
    }
  },
  data() {
    return {
      loading: false,
      tabsList: [{ label: '全部', value: 'all' },
        { label: '知识库', value: 'library' },
        { label: '文档', value: 'doc' },
        { label: '文件夹', value: 'folder' },
        { label: '上传文件', value: 'file' }],

      activeTab: 'all',
      staticData: { // 关注数据统计
        allNum: 0,
        documentNum: 0,
        libraryNum: 0,
        fileNum: 0,
        folderNum: 0
      },

      dataList: [],

      currentPage: 1,
      pageSize: 15,
      pageSizes: [15, 30, 60, 100],
      total: 100
    }
  },
  created() {
    this.getList()
  },
  methods: {
    refreshList() {
      this.dataList = []
      this.handleCurrentChange(1)
    },

    getList() {
      this.loading = true
      kmCollectListAPI({
        page: this.currentPage,
        limit: this.pageSize,
        type: {
          all: 0,
          library: 1,
          doc: 3,
          folder: 2,
          file: 4
        }[this.activeTab]
      }).then(res => {
        const data = res.data.page || {}
        const staticData = res.data || {}
        staticData.allNum = staticData.documentNum + staticData.fileNum + staticData.folderNum + staticData.libraryNum
        this.staticData = staticData
        this.dataList = data.list || []
        this.total = data.totalRow
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    getFileIcon(filename) {
      const lastIndex = filename.lastIndexOf('.')
      const suffix = filename.slice(lastIndex + 1)
      console.log('suffix', suffix)
      return getFileIconWithSuffix(suffix)
    },

    handleRowClick(row, column, event) {
      console.log('row', row)
      const params = {
        id: row.libraryId,
        folderId: row.folderId
      }
      if (row.documentId) {
        params.documentId = row.documentId
        params.type = row.type
      }
      params.navActiveMenu = '/knowledge/collection' // 定位导航头部菜单
      this.$router.push({
        path: '/knowledge/main',
        query: params
      })
    },

    cancelCollection(data) {
      const params = {
        type: data.type,
        typeId: data.typeId
      }
      kmCollectCancelAPI(params).then(() => {
        this.getList()
      }).catch(() => {})
    },

    handleSizeChange(size) {
      this.pageSize = size
      this.getList()
    },
    handleCurrentChange(page) {
      this.currentPage = page
      this.getList()
    }
  }
}
</script>

<style scoped lang="scss">
  @import "../style/index";

  .knowledge-collection {
    width: 100%;
    height: 100%;
    padding: 24px 32px;

    .wk-filter-header {
      margin-top: 24px;
      margin-bottom: 16px;
      line-height: 32px;
    }

    .container {
      height: calc(100% - 100px);
    }
  }

  // 关注 禁用
  .is-disabled {
    color: $--color-n40;
    cursor: not-allowed;
  }
</style>
