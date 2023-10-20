<template>
  <div class="knowledge-collection">
    <wk-page-header
      title="最近使用" />
    <wk-filter-header
      :tabs="tabsList"
      :active-tab.sync="activeTab"
      :props="{
        tabSetShow: false,
        showSearch: false
      }"
    />

    <div class="container">
      <section-table :data="tableData[activeTab] || []" />
    </div>
  </div>
</template>

<script>
import { kmRecordListAPI } from '@/api/knowledge/index'

import WkPageHeader from '@/components/Page/WkPageHeader'
import WkFilterHeader from '@/components/NewCom/WkFilterHeader'
import SectionTable from '../components/SectionTable'

export default {
  name: 'KnowledgeRecent',
  components: {
    WkPageHeader,
    WkFilterHeader,
    SectionTable
  },
  filters: {
    getCount(count, str) {
      if (count) return `${str}(${count})`
      else return str
    }
  },
  data() {
    return {
      tabsList: [{ label: '最近浏览', value: 'todayList' },
        { label: '昨天', value: 'yesterdayList' },
        { label: '最近30天', value: 'monthList' }],
      activeTab: 'todayList',
      tableData: {}
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      kmRecordListAPI({
        status: 1
      }).then(res => {
        console.log(res.data)
        this.tableData = res.data
      }).catch()
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
</style>
