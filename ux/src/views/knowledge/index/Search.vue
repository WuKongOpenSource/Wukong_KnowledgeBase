<template>
  <div class="search-container">
    <div class="header">
      <el-button
        type="text"
        icon="el-icon-arrow-left"
        class="btn-back"
        @click="$router.go(-1)">
        返回
      </el-button>
    </div>
    <div class="search-input">
      <el-input
        v-model="form.search"
        :maxlength="50"
        placeholder="请输入关键字">
        <el-button
          slot="append"
          type="icon"
          icon="wk wk-sousuo"
          @click="searcClick" />
      </el-input>
    </div>
    <el-container class="search-body">
      <el-aside width="240px">
        <div class="filter-sec">
          <div class="input-box">
            <div class="label">
              创建人
            </div>
            <wk-user-dialog-select
              v-model="form.createUserId"
              radio
              style="width: 100%;"
              @change="refreshList"
            />
          </div>
          <div class="input-box">
            <div class="label">
              在下面知识库中
            </div>
            <el-select
              v-model="form.libraryId"
              filterable
              clearable
              @change="refreshList">
              <el-option
                v-for="item in libraryList"
                :key="item.libraryId"
                :label="item.name"
                :value="item.libraryId" />
            </el-select>
          </div>
        </div>

        <div class="filter-sec">
          <div class="sec-title">
            最近更新
          </div>
          <div class="sec-body">
            <ul class="options">
              <li
                v-for="(item, index) in timeIntervalType"
                :key="index"
                :class="{active: form.type === item.value}"
                class="option-item"
                @click="typeClick(item)">
                {{ item.label }}
              </li>
            </ul>
            <div
              v-if="form.type === 5"
              class="time-select">
              <el-date-picker
                v-model="form.startTime"
                value-format="yyyy-MM-dd"
                placeholder="开始时间"
                @change="refreshList" />
              <div class="line" />
              <el-date-picker
                v-model="form.endTime"
                value-format="yyyy-MM-dd"
                placeholder="结束时间"
                @change="refreshList" />
            </div>
          </div>
        </div>

        <!--<div class="filter-sec">
          <div class="sec-title">
            根据类型
          </div>
          <div class="sec-body">
            <ul class="options">
              <li
                v-for="(item, index) in typeList"
                :key="index"
                :class="{active: item.value === form.type}"
                class="option-item"
                @click="form.type = item.value">
                {{ item.label }}
              </li>
            </ul>
          </div>
        </div>-->
      </el-aside>
      <el-main v-loading="loading">
        <div class="search-res">
          <template v-if="dataList.length">
            <search-res-item
              v-for="(item, index) in dataList"
              :key="index"
              :data="item"
              @titleClick="titleClick" />
          </template>
          <no-data
            v-else
            style="height: calc(100vh - 300px);"
            class="no-data" />
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
import { kmLibrarySearchAPI, kmLibraryListAPI } from '@/api/knowledge/index'

import NoData from '../components/NoData'

import SearchResItem from '../components/SearchResItem'
import WkUserDialogSelect from '@/components/NewCom/WkUserDialogSelect'

export default {
  name: 'KnowLedgeSearch',
  components: {
    SearchResItem,
    WkUserDialogSelect,
    NoData
  },
  data() {
    return {
      loading: false,
      timeIntervalType: [
        { label: '任意时间', value: 'all' },
        { label: '今天', value: 1 },
        { label: '上周', value: 2 },
        { label: '上月', value: 3 },
        { label: '去年', value: 4 },
        { label: '自定义时间', value: 5 }
      ],
      // typeList: [
      //   { label: '全部', value: 'all' },
      //   { label: '知识库', value: 1 },
      //   { label: '文档', value: 3 },
      //   { label: '文件夹', value: 2 }
      // ],
      libraryList: [],

      timeIndex: 1,

      form: {
        type: 'all',
        search: '',
        createUserId: ''
      },

      currentPage: 1,
      pageSize: 15,
      pageSizes: [15, 30, 60, 100],
      total: 100,
      dataList: []
    }
  },
  created() {
    this.form.search = this.$route.query.kw
    this.getLibraryList()
    this.refreshList()
  },
  methods: {
    /**
     * 获取知识库列表
     */
    getLibraryList() {
      kmLibraryListAPI().then(res => {
        this.libraryList = res.data || []
      }).catch()
    },

    /**
     * 搜索按钮点击
     */
    searcClick() {
      // this.$route.query.kw = this.form.search
      this.$router.replace({ path: this.$route.params[0], query: {
        kw: this.form.search,
        navActiveMenu: this.$route.query.navActiveMenu || '/knowledge/index'
      }})
      this.refreshList()
    },

    /**
     * 类型修改
     */
    typeClick(item) {
      this.form.type = item.value
      this.refreshList()
    },

    refreshList() {
      this.handleCurrentChange(1)
    },

    getList() {
      const params = {
        page: this.currentPage,
        limit: this.pageSize,
        search: this.form.search
      }

      if (this.form.type != 'all') {
        params.type = this.form.type

        if (this.form.type == 5) {
          params.startTime = this.form.startTime
          params.endTime = this.form.endTime
        }
      }

      if (this.form.createUserId) {
        params.createUserId = this.form.createUserId
      }

      params.libraryId = this.form.libraryId

      this.loading = true
      kmLibrarySearchAPI(params)
        .then(res => {
          this.dataList = res.data.list
          if (res.data.totalRow && Math.ceil(res.data.totalRow / this.pageSize) < this.currentPage && this.currentPage > 1) {
            this.currentPage = this.currentPage - 1
            this.getList()
          } else {
            this.total = res.data.totalRow
            this.loading = false
          }
        })
        .catch(() => {
          this.loading = false
        })
    },

    handleSizeChange(size) {
      this.pageSize = size
      this.getList()
    },

    handleCurrentChange(page) {
      this.currentPage = page
      this.getList()
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
      params.navActiveMenu = this.$route.query.navActiveMenu || '/knowledge/index' // 导航头部菜单

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

  .header {
    padding: 24px 40px;
    .btn-back {}
  }

  .search-input {
    padding: 0 40px 16px;

    ::v-deep .el-input {
      width: 220px;

      .el-input__inner {
        padding-right: 36px;
      }
    }
  }

  .search-body {
    height: calc(100% - 150px);
    padding: 0 40px;

    .el-aside {
      padding: 16px;
      background-color: white;
      border: 1px solid $--border-color-base;
      border-radius: $--border-radius-base;

      .filter-sec {
        &:last-child {
          border-bottom: 0 none;
        }

        .input-box {
          margin-bottom: 16px;

          &:last-child {
            margin-bottom: 0;
          }

          .label {
            margin-bottom: 4px;
            font-size: 12px;
          }

          .el-select {
            width: 100%;
          }
        }

        .sec-title {
          margin-bottom: 4px;
          font-size: 12px;
        }

        .sec-body {
          .options {
            margin-top: 10px;

            .option-item {
              padding: 8px 16px;
              color: $--color-primary;
              cursor: pointer;
              border-radius: $--border-radius-base;

              &.active,
              &:hover {
                background-color: $--background-color-base;
              }
            }

            .option-item + .option-item {
              margin-top: 4px;
            }
          }

          .time-select {
            .el-date-editor {
              width: 100%;
            }

            .line {
              width: 8px;
              height: 2px;
              margin: 10px 0;
              background-color: $--color-black;
              border-radius: 2px;
            }
          }
        }
      }

      .filter-sec + .filter-sec {
        margin-top: 16px;
      }
    }

    .el-main {
      padding: 16px 0;
      margin-left: 16px;
      background-color: white;
      border: 1px solid $--border-color-base;
      border-radius: $--border-radius-base;

      .search-res {
        height: calc(100% - 42px);
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
