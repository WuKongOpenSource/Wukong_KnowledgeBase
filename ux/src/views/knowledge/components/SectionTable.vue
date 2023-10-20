<template>
  <el-table
    :data="data"
    height="100%"
    @row-click="handleRowClick">
    <el-table-column
      label="名称"
      show-overflow-tooltip>
      <template slot-scope="scope">
        <div class="file-main">
          <i
            v-if="scope.row.type === 2"
            class="wk wk-folder file-icon folder" />
          <i
            v-else-if="scope.row.type === 3"
            class="wk wk-doc file-icon doc" />
          <img
            v-else
            :src="getFileIcon(scope.row.title)"
            alt=""
            class="img-icon">
          <span class="file-name">
            {{ scope.row.title }}
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
      label="知识库名称"
      width="300"
      show-overflow-tooltip
      prop="libraryName" />
    <el-table-column
      label="浏览时间"
      width="240"
      show-overflow-tooltip>
      <template slot-scope="scope">
        浏览于{{ scope.row.createTime }}
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getFileIconWithSuffix, fileSize } from '@/utils/index'

export default {
  name: 'SectionTable',
  filters: {
    getFileSize(size) {
      return fileSize(Number(size))
    }
  },
  props: {
    data: {
      type: Array
    }
  },
  data() {
    return {
    }
  },
  methods: {
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
      params.navActiveMenu = '/knowledge/recent' // 定位导航头部菜单
      this.$router.push({
        path: '/knowledge/main',
        query: params
      })
    },

    getFileIcon(filename) {
      let suffix = ''
      if (filename) {
        const lastIndex = filename.lastIndexOf('.')
        suffix = filename.slice(lastIndex + 1)
      }
      console.log('suffix', suffix)
      return getFileIconWithSuffix(suffix)
    },

    handleRecover(data) {

    }
  }
}
</script>

<style scoped lang="scss">
  @import "../style/index";

  .el-table {
    .recover-btn {
      visibility: hidden;
    }

    ::v-deep .el-table__body tr:hover {
      .recover-btn {
        visibility: visible;
      }
    }
  }
</style>
