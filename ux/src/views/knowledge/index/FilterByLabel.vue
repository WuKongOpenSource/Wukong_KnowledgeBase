<template>
  <div class="search-container">
    <xr-header
      icon-class="wk wk-book"
      icon-color="#fb9924"
      label="知识库" />
    <el-container class="search-body">
      <el-aside>
        <div class="sec-title">
          全部标签
        </div>

        <el-input
          v-model="searchKeyword"
          prefix-icon="el-icon-search"
          placeholder="搜索标签"
          class="input-box"
          @input="handleSearchTag" />

        <ul class="options">
          <li
            v-for="(item, index) in tagList"
            :key="index"
            :class="{active: item.labelId == activeLabel.labelId}"
            class="option-item text-one-line"
            @click="handleFilter(item)">
            {{ item.name }}
          </li>
        </ul>
      </el-aside>

      <el-main>
        <div v-if="activeLabel" class="search-res-title">
          标签为 <span class="special">“ {{ activeLabel.name }} ”</span> 的筛选结果共{{ total || 0 }}条
        </div>
        <div class="search-res">
          <search-res-item
            v-for="(item, index) in dataList"
            :key="index"
            :data="item"
            :show-content="false"
            show-label
            @titleClick="titleClick" />
        </div>
        <div class="page-container">
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
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { kmLabelQueryListAPI, kmLabelQueryDocByIdAPI } from '@/api/knowledge/label'

import SearchResItem from '../components/SearchResItem'
import XrHeader from '@/components/XrHeader'

export default {
  name: 'FilterByLabel',
  components: {
    XrHeader,
    SearchResItem
  },
  data() {
    return {
      allTagList: [],
      tagList: [],
      activeLabel: null,

      searchKeyword: '',
      filterLabelId: '',

      currentPage: 1,
      pageSize: 15,
      pageSizes: [15, 30, 60, 100],
      total: 100,
      dataList: []
    }
  },
  created() {
    this.getAllTag()
  },
  methods: {
    getAllTag() {
      this.filterLabelId = this.$route.query.labelId || null
      kmLabelQueryListAPI().then(res => {
        this.tagList = res.data || []
        this.allTagList = [].concat(this.tagList)
        this.activeLabel = this.tagList.find(o => o.labelId == this.filterLabelId) || null
        this.refreshList()
      })
    },

    /**
     * 筛选
     * @param tag
     */
    handleFilter(tag) {
      this.activeLabel = tag
      this.filterLabelId = tag.labelId
      this.refreshList()
    },

    handleSearchTag() {
      if (!this.searchKeyword) {
        this.tagList = [].concat(this.allTagList)
        return
      }
      this.tagList = this.allTagList.filter(tag => tag.name.includes(this.searchKeyword))
    },

    /**
     * 列表
     * @param labelId
     */
    getListByLabelId() {
      kmLabelQueryDocByIdAPI({
        page: this.currentPage,
        limit: this.pageSize,
        labelId: this.filterLabelId || null

      }).then(res => {
        // console.log('res', res)
        const list = res.data.list || []
        list.forEach(o => {
          const arr = o.labelIds.split(',')
          o.labelList = []
          arr.forEach(id => {
            const findItem = this.tagList.find(tag => tag.labelId == id)
            if (findItem) {
              o.labelList.push(findItem)
            }
          })
        })
        this.dataList = list
        this.total = res.data.totalRow
      })
    },

    refreshList() {
      this.handleCurrentChange(1)
    },

    handleSizeChange(size) {
      this.pageSize = size
      this.getListByLabelId()
    },
    handleCurrentChange(page) {
      this.currentPage = page
      this.getListByLabelId()
    },

    /**
     * 详情查看
     */
    titleClick(data) {
      if (this.type === 'bin') return
      const params = {
        id: data.libraryId,
        folderId: data.folderId
      }
      if (data.documentId) {
        params.documentId = data.documentId
        params.type = data.type
      }
      this.$router.push({
        path: '/knowledge/main',
        query: params
      })
    }
  }
}
</script>

<style scoped lang="scss">
.search-container {
  height: 100%;

  .search-body {
    height: calc(100% - 75px);
    margin: 0 15px;

    .el-aside {
      background-color: white;
      border: 1px solid $--border-color-base;
      border-radius: $--border-radius-base;

      .sec-title {
        width: 100%;
        height: 50px;
        padding-left: 15px;
        font-size: 15px;
        font-weight: bold;
        line-height: 50px;
        color: $--color-black;
        border-bottom: 1px solid $--border-color-base;
      }

      .input-box {
        width: calc(100% - 40px);
        margin: 15px 20px;
      }

      .options {
        padding: 0 20px;

        .option-item {
          padding: 10px 10px 10px 25px;
          margin: 5px 0;
          font-size: 14px;
          cursor: pointer;
          border-radius: $--border-radius-base;

          &::before {
            display: inline-block;
            width: 5px;
            height: 5px;
            margin-right: 10px;
            margin-left: -10px;
            vertical-align: middle;
            content: "";
            background-color: $--border-color-base;
            border-radius: 50%;
          }

          &.active,
          &:hover {
            color: $--color-primary;
            background-color: #f6f9f9;

            &::before {
              background-color: $--color-primary;
            }
          }
        }
      }
    }

    .el-main {
      padding: 25px 0 15px;
      margin-left: 15px;
      background-color: white;
      border: 1px solid $--border-color-base;
      border-radius: $--border-radius-base;

      .search-res-title {
        padding: 0 20px;
        margin-bottom: 20px;
        font-size: 16px;

        .special {
          font-weight: 600;
        }
      }

      .search-res {
        height: calc(100% - 80px);
        padding: 0 20px;
        overflow: auto;

        .search-res-item {
          margin-bottom: 25px;

          &:last-child {
            margin-bottom: 0;
          }
        }
      }

      .page-container {
        padding: 0 20px;
        margin-top: 10px;

        .el-pagination {
          text-align: right;
        }
      }
    }
  }
}
</style>
