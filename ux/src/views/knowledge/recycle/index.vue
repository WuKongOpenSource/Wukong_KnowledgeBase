<template>
  <div class="knowledge-collection">
    <wk-page-header
      title="回收站" />
    <wk-filter-header
      :tabs="tabsList"
      :active-tab.sync="activeTab"
      :props="{
        tabSetShow: false,
        showSearch: false
      }"
      @tabs-change="getData"
    />

    <div class="container">
      <el-table
        :data="tableList"
        height="100%"
        class="recycle-table">
        <el-table-column
          show-overflow-tooltip
          label="知识库名称"
          width="300"
          prop="name" />
        <el-table-column
          show-overflow-tooltip
          label="知识库描述"
          prop="description" />
        <el-table-column
          show-overflow-tooltip
          label="删除时间"
          width="240">
          <template slot-scope="scope">
            删除于{{ scope.row.deleteTime }}
          </template>
        </el-table-column>
        <el-table-column
          width="150"
          label="操作"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button type="primary-text" style="padding: 0;" @click="recoverClick(scope)">恢复</el-button>
            <el-button type="primary-text" style="padding: 0;" @click="deleteClick(scope)">永久删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { kmRecordDeleteListAPI, kmRecordRestoreAPI, kmLibraryCompleteDeleteByIdAPI } from '@/api/knowledge/index'

import WkPageHeader from '@/components/Page/WkPageHeader'
import WkFilterHeader from '@/components/NewCom/WkFilterHeader'

export default {
  name: 'KnowledgeRecycle',
  components: {
    WkPageHeader,
    WkFilterHeader
  },
  filters: {
  },
  data() {
    return {
      tabsList: [{ label: '全部', value: 'all' }],

      activeTab: 'all',
      tableList: []
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      kmRecordDeleteListAPI().then(res => {
        console.log(res.data)
        this.tableList = res.data
      }).catch()
    },

    /**
     * @description: 知识库恢复
     * @param {*} data
     * @param {*} index
     * @return {*}
     */
    recoverClick({ row, $index }) {
      this.$confirm('您确定要恢复该知识库吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        kmRecordRestoreAPI({
          id: row.libraryId,
          type: 1
        }).then(() => {
          this.$message.success('恢复成功!')
          this.tableList.splice($index, 1)
        }).catch()
      }).catch(() => {})
    },

    /**
     * @description: 删除
     * @param {*} scope
     * @return {*}
     */
    deleteClick({ row, $index }) {
      this.$confirm('确定要永久删除吗，删除后将无法恢复?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        kmLibraryCompleteDeleteByIdAPI({
          libraryId: row.libraryId
        }).then(() => {
          this.$message.success('删除成功')
          this.tableList.splice($index, 1)
        }).catch(() => {})
      }).catch(() => {})
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

  .recycle-table {
    .cover-img {
      vertical-align: middle;
      border-radius: 4px;
    }

    .knowledage-name {
      font-size: 14px;
    }

    .knowledage-des {
      font-size: 12px;
      color: $--color-text-secondary;
    }
  }
</style>
