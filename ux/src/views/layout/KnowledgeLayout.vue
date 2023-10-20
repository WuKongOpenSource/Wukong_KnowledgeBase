<template>
  <el-container>
    <el-header class="nav-container">
      <navbar
        nav-index="/knowledge"
        title="知识库"
        @nav-items-click="navClick" />
    </el-header>
    <wk-container
      :menu="knowledgeRouter"
      :header-obj="headerCellObj"
      side-type="normal">
      <el-main style="padding: 0;">
        <app-main v-if="showMain" />
      </el-main>
    </wk-container>

    <create-dialog
      v-if="showCreate"
      @update="updateCom"
      @close="showCreate = false" />
  </el-container>
</template>

<script>
// import { mapGetters } from 'vuex'
import { Navbar, AppMain } from './components'
import CreateDialog from '../knowledge/components/CreateDialog'
import WkContainer from './components/WkContainer'

import knowledgeRouter from '@/router/modules/knowledge'
import { getNavMenus } from './components/utils'

export default {
  name: 'KnowledgeLayout',

  components: {
    Navbar,
    // Sidebar,
    AppMain,
    CreateDialog,
    WkContainer
  },

  data() {
    return {
      isCreate: false,
      createCRMType: '',
      knowledgeRouter: knowledgeRouter,

      showCreate: false,
      showMain: true,

      headerCellObj: {
        icon: 'wk wk-book',
        label: '知识库',
        des: '知识管理系统'
      }
    }
  },

  computed: {
    menus() {
      return getNavMenus(knowledgeRouter || [], '/knowledge')
    }
  },

  mounted() {},

  methods: {
    navClick(index) {},

    quicklyCreate() {
      console.log('add')
      this.showCreate = true
    },

    updateCom() {
      this.showMain = false
      this.$nextTick(() => {
        this.showMain = true
      })
    },

    quickAddOffset() {
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "./styles/common.scss";

  .el-container {
    height: 100%;
    min-height: 0;
  }

  .aside-container {
    position: relative;
    box-sizing: border-box;
    background-color: #2d3037;
  }

  .nav-container {
    z-index: 100;
    min-width: 1200px;
    padding: 0;
    box-shadow: 0 1px 2px #dbdbdb;
  }
</style>
