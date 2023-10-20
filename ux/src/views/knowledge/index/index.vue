<template>
  <div class="knowledge-index">
    <wk-page-header
      :title="config.showModuleName ? '知识库' : ''"
    >
      <template slot="right">
        <el-button
          type="primary"
          @click="showCreate = true">新建知识库</el-button>
      </template>
    </wk-page-header>

    <wk-filter-header
      :search.sync="search"
      :tabs="headerTabs"
      :active-tab.sync="activeTab"
      :props="{
        tabSetShow: false,
        searchPlaceholder: '请输入关键字'
      }"
      @tabs-change="getList"
      @event-change="headerHandle"
    >
      <template
        slot="dropdownMore"
      >
        <el-dropdown-item
          v-for="(item, index) in groupOptionsList"
          :key="item.value"
          :divided="index === 0"
          :command="item.value">
          {{ item.label }}
        </el-dropdown-item>
      </template>

    </wk-filter-header>

    <!-- <div class="container"> -->
    <!-- <el-tabs
        v-model="activeTab"
        class="wk-tabs"
        @tab-click="getList()">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="我关注的" name="about" />
      </el-tabs> -->

    <!-- <div
        class="list">
        <knowledge-item
          v-for="(item, index) in dataList"
          :key="index"
          :data="item"
          @update="getList()" />
        <knowledge-item-add @click.native="showCreate = true" />
      </div> -->
    <!-- </div> -->

    <el-table
      :data="dataList"
      row-key="libraryId"
      height="calc(100% - 90px)"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick">
      <el-table-column
        show-overflow-tooltip
        reserve-selection
        type="selection"
        fixed
        align="center"
        width="55" />
      <el-table-column
        show-overflow-tooltip
        label="知识库名称"
        width="320">
        <template slot-scope="scope">
          <flexbox class="corver">
            <img v-src="scope.row.coverUrl" class="corver-img" alt="封面图">
            <div class="corver-name text-one-line">{{ scope.row.name }}</div>
          </flexbox>
        </template>
      </el-table-column>
      <el-table-column
        label="知识库描述">
        <template slot-scope="scope">
          <div class="kl-des" :title="scope.row.description">{{ scope.row.description }}</div>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="创建时间"
        width="200"
        prop="createTime" />
      <el-table-column
        show-overflow-tooltip
        width="120"
        label="文档数">
        <template slot-scope="scope">
          <i class="wk wk-doc" /><span class="kl-icon-interval">{{ scope.row.documentNumber }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        width="120"
        label="文件数">
        <template slot-scope="scope">
          <i class="wk wk-icon-file" /><span class="kl-icon-interval">{{ scope.row.fileNumber }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        width="80"
        align="center">
        <template
          slot="header"
          slot-scope="{}">
          <i class="el-icon-star-off focus-icon is-disabled" />
        </template>
        <template slot-scope="scope">
          <el-button
            type="text"
            style="padding: 0;"
            @click.stop="collectStore(scope.row)">
            <i
              :class="{active: scope.row.collectStatus === 1}"
              class="wk wk-focus-on icon-collect" />
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        v-if="!config.isSelect"
        width="180"
        label="操作"
        show-overflow-tooltip
        fixed="right">
        <template slot-scope="scope">
          <el-dropdown size="default" @command="moveClick($event,scope)">
            <el-button type="primary-text" style="padding: 0;" @click.stop>移动到分组</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-for="(item,index) in hasMoveGroupList"
                :key="item.groupId"
                :command="item.groupId">
                {{ item.label }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button type="primary-text" style="padding: 0;" @click.stop="setClick(scope)">设置</el-button>
          <el-button type="primary-text" style="padding: 0;" @click.stop="deleteClick(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <create-dialog
      v-if="showCreate"
      @update="getList()"
      @close="showCreate = false" />

    <!-- 编辑 -->
    <edit-dialog
      v-if="showEdit"
      :data="detailData"
      @close="showEdit = false"
      @update="getList" />

    <!-- 分组管理 -->
    <GroupDialog
      v-if="showGroupSet"
      :group-list="groupList"
      @close="showGroupSet = false"
      @update="getGroupList"
    />

    <!-- 创建分组 -->
    <CreateGroupDialog
      v-if="showGroupCreate"
      @close="showGroupCreate = false"
      @update="getGroupList"
    />
  </div>
</template>

<script>
import { kmLibraryListAPI, kmLibraryDeleteByIdAPI } from '@/api/knowledge/index'
import { kmCollectAddAPI, kmCollectCancelAPI } from '@/api/knowledge/index'
import { kmGroupSearchGroupListAPI, kmGroupManagementMoveToGroupAPI } from '@/api/knowledge/group.js'
import WkPageHeader from '@/components/Page/WkPageHeader'
import WkFilterHeader from '@/components/NewCom/WkFilterHeader'
// import KnowledgeItem from '../components/KnowledgeItem'
// import KnowledgeItemAdd from '../components/KnowledgeItemAdd'
import CreateDialog from '../components/CreateDialog'
import EditDialog from '../components/EditDialog'
import GroupDialog from '../components/GroupDialog.vue'
import CreateGroupDialog from '../components/CreateGroupDialog.vue'

import Table from './Table.js'

export default {
  name: 'KnowledgeIndex',
  components: {
    WkPageHeader,
    WkFilterHeader,
    // KnowledgeItem,
    // KnowledgeItemAdd,
    CreateDialog,
    EditDialog,
    GroupDialog,
    CreateGroupDialog
  },
  mixins: [Table],
  data() {
    return {
      search: '',
      selectionList: [], // 勾选数据
      activeTab: 'all',
      dataList: [],
      showCreate: false,

      searchKeyword: '',
      // 用于编辑
      detailData: null,
      showEdit: false,

      showGroupSet: false,
      showGroupCreate: false,
      groupOptionsList: [
        { label: '创建分组', value: 'create' },
        { label: '管理分组', value: 'manage' }
      ],
      groupList: []
    }
  },
  computed: {
    headerTabs() {
      return this.groupList.map(item => {
        return {
          ...item,
          label: item.name,
          value: item.groupId
        }
      })
    },
    hasMoveGroupList() {
      return this.headerTabs.filter(item => {
        return item.groupId != this.activeTab
      })
    }
  },
  watch: {
    // props 包含的都是弹框筛选逻辑
    selectedData: {
      deep: true,
      immediate: true,
      handler(newVal) {
        // 筛选默认值勾选
        if (newVal) {
          let valueEquals = true
          if (newVal.length !== this.selectionList.length) {
            valueEquals = false
          } else {
            for (let i = 0; i !== newVal.length; ++i) {
              if (newVal[i][`libraryId`] !== this.selectionList[i][`libraryId`]) {
                valueEquals = false
                break
              }
            }
          }

          if (!valueEquals) {
            this.hasExaSelctedData = false
            this.ignoreSelectedChange = true

            setTimeout(() => {
              this.setSelections(this.selectedData).then(() => {
                this.$nextTick(() => {
                  this.ignoreSelectedChange = false
                })
              })
            }, 100)
          }
        }
      }
    }

  },
  created() {
    this.getGroupList().then(list => {
      if (list.length) {
        this.activeTab = list[0]['groupId']
      }
      this.getList()
    })
  },
  methods: {
    getList() {
      this.showCreate = false
      const params = {
        groupId: this.activeTab
      }
      kmLibraryListAPI(params).then(res => {
        if (this.searchKeyword) {
          this.dataList = res.data.filter(o => o.name.includes(this.searchKeyword)) || []
        } else {
          this.dataList = res.data || []
        }
      }).catch()
    },
    // 获取分组列表
    getGroupList() {
      return kmGroupSearchGroupListAPI().then(res => {
        this.groupList = res.data
        return res.data
      }).catch(console.error)
    },
    /**
     * @description: 表格点击
     * @param {*} row
     * @param {*} column
     * @param {*} event
     * @return {*}
     */
    handleRowClick(row, column, event) {
      if (this.config.isSelect) return
      this.$router.push({
        path: '/knowledge/main',
        query: {
          id: row.libraryId,
          navActiveMenu: '/knowledge/index'
        }
      })
    },

    /**
     * @description: 表格头部操作
     * @param {*} type
     * @return {*}
     */
    headerHandle(type) {
      if (type === 'search') {
        this.$router.push({
          path: '/knowledge/search',
          query: {
            kw: this.search,
            navActiveMenu: '/knowledge/index' // 定位导航头部菜单
          }
        })
      } else if (type === 'manage') {
        this.showGroupSet = true
      } else if (type === 'create') {
        this.showGroupCreate = true
      }
    },

    /**
     * 关注/取消关注
     */
    collectStore(data) {
      const params = {
        type: 1,
        typeId: data.libraryId
      }
      const request = data.collectStatus === 0 ? kmCollectAddAPI : kmCollectCancelAPI
      request(params).then(() => {
        const msg = data.collectStatus === 0 ? '关注成功' : '已取消关注'
        this.$message.success(msg)
        data.collectStatus = data.collectStatus === 0 ? 1 : 0
      }).catch()
    },

    /**
     * @description: 设置
     * @param {*} scope 包含 row $index 数据
     * @return {*}
     */
    setClick({ row }) {
      this.detailData = row
      this.showEdit = true
    },

    /**
     * @description: 删除
     * @param {*} scope 包含 row $index 数据
     * @return {*}
     */
    deleteClick({ row }) {
      this.$confirm('您确定要删除该知识库和该知识库下的所有文件吗?<br />删除后可在回收站恢复知识库。', '提示', {
        confirmButtonText: '确定',
        dangerouslyUseHTMLString: true,
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        kmLibraryDeleteByIdAPI(row.libraryId).then(() => {
          this.$message.success('删除成功!')
          this.getList()
        }).catch(() => {})
      }).catch(() => {
      })
    },
    // 移动到分组
    moveClick(groupId, { row }) {
      kmGroupManagementMoveToGroupAPI({
        groupId,
        libraryId: row.libraryId
      }).then(res => {
        this.$message.success('操作成功')
        this.getList()
      }).catch(console.error)
    },
    updateTableHeight() {}
  }
}
</script>

<style scoped lang="scss">
@import "../style/index";

.knowledge-index {
  width: 100%;
  height: 100%;
  padding: 24px 40px;

  .wk-filter-header {
    margin-top: 24px;
    margin-bottom: 8px;
    line-height: 32px;
  }

  .container {
    height: calc(100% - 90px);
    overflow: auto;
    background-color: white;

    .list {
      display: flex;
      flex-wrap: wrap;
      width: 100%;
      margin: 0 -4px;

      .knowledge-item {
        margin: 8px;
      }

      // @media screen and (min-width: 1200px) and (max-width: 1679px) {
      //   .knowledge-item {
      //     width: 20%;
      //   }
      // }
      // @media screen and (min-width: 1680px) {
      //   .knowledge-item {
      //     width: 16.666%;
      //   }
      // }
    }
  }

  // 关注 禁用
  .is-disabled {
    color: $--color-n40;
    cursor: not-allowed;
  }

  .corver {
    cursor: pointer;

    &-img {
      flex-shrink: 0;
      width: 83px;
      height: 50px;
      overflow: hidden;
      border-radius: $--border-radius-base;
    }

    &-name {
      flex: 1;
      margin-left: 8px;
    }
  }

  .kl-des {
    display: flex;
    overflow: hidden;
    line-height: 1.2;
    text-overflow: ellipsis;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }

  .kl-icon-interval {
    margin-left: 4px;
  }

  .icon-collect {
    color: #e2e4e9;
    cursor: pointer;
  }

  .icon-collect.active {
    color: #fac23d;
  }

  ::v-deep .el-table {
    .el-table__cell:last-child {
      border-right: $--border-base;
    }
  }
}
</style>
