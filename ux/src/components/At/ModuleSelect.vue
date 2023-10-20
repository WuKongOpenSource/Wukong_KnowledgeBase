<template>
  <el-card
    class="module-select-wrap"
    :body-style="{ padding: '8px', height: '100%' }">
    <div
      v-loading="loading"
      class="module-select">
      <div class="module-select-header">
        <el-tabs
          v-model="activeName"
          nav-mode="more"
          @tab-click="tabClick">
          <el-tab-pane
            v-for="item in tabs"
            :key="item.value"
            :label="item.label"
            :name="item.value"
            lazy />
        </el-tabs>
      </div>
      <div class="module-select-body">
        <flexbox
          v-for="(item, index) in list"
          :key="index"
          class="cell"
          :class="{'is-select': selectIndex === index}"
          @click.native="selectClick(index)"
          @mouseenter.native="selectIndex = index"
        >
          <div class="cell-label text-one-line">{{ item[tabConfig.nameKey] }}</div>
          <div class="cell-value">{{ item.ownerUserName }}</div>
        </flexbox>
      </div>
      <div class="module-select-footer">
        <el-button
          type="primary-text"
          @click="showListClick">从列表选择</el-button>
      </div>
    </div>

    <!-- 关联业务 -->
    <crm-relative
      v-if="showRelatePopover"
      ref="crmrelative"
      :visible.sync="showRelatePopover"
      :radio="false"
      :show-types="[
        'customer',
        'contacts',
        'business',
        'contract',
        'receivables'
      ]"
      @close="showRelatePopover= false"
      @changeCheckout="relateDataChange" />

    <!-- 员工选择 -->
    <wk-dep-user-dialog
      v-if="userDialogSelectVisible"
      :visible.sync="userDialogSelectVisible"
      :props="{
        showDept: false
      }"
      @change="selectUserChange" />
  </el-card>
</template>

<script>
import { userListAPI } from '@/api/common'
import { crmCustomerIndexAPI } from '@/api/crm/customer'
import { crmContactsIndexAPI } from '@/api/crm/contacts'
import { crmBusinessIndexAPI } from '@/api/crm/business'
import { crmContractIndexAPI } from '@/api/crm/contract'
import { crmReceivablesIndexAPI } from '@/api/crm/receivables'

import CrmRelative from '@/components/CreateCom/CrmRelative'
import WkDepUserDialog from '@/components/NewCom/WkUserDialogSelect/Dialog'

import { debounce } from 'throttle-debounce'
import merge from '@/utils/merge'

const DefaultModuleSelect = {
  showUser: false // 默认不能选员工
}

export default {
  // ModuleSelect 模块选择
  name: 'ModuleSelect',

  components: {
    WkDepUserDialog,
    CrmRelative
  },

  props: {
    props: {
      type: Object,
      default: () => {}
    },
    search: String
  },

  data() {
    return {
      loading: false,
      activeName: 'customer',
      selectIndex: -1,
      list: [],

      showRelatePopover: false, // 关联业务信息框
      userDialogSelectVisible: false // 关联员工
    }
  },

  computed: {
    // 合并 props
    config() {
      return merge({ ...DefaultModuleSelect }, this.props || {})
    },

    tabs() {
      const temps = [
        { label: '客户', value: 'customer', request: crmCustomerIndexAPI, nameKey: 'customerName' },
        { label: '联系人', value: 'contacts', request: crmContactsIndexAPI, nameKey: 'name' },
        { label: '商机', value: 'business', request: crmBusinessIndexAPI, nameKey: 'businessName' },
        { label: '合同', value: 'contract', request: crmContractIndexAPI, nameKey: 'num' },
        { label: '回款', value: 'receivables', request: crmReceivablesIndexAPI, nameKey: 'number' }
      ]
      if (this.config.showUser) {
        temps.unshift({ label: '员工', value: 'user', request: userListAPI, nameKey: 'realname' })
      }
      return temps
    },

    tabConfig() {
      return this.tabs.find(item => item.value === this.activeName)
    }
  },

  watch: {
    search() {
      this.debounceGetList()
    }
  },

  created() {
    this.activeName = this.tabs[0].value

    this.debounceGetList = debounce(300, this.getList)
    this.debounceGetList()
    document.addEventListener('keyup', this.keyDownHandler)
  },

  mounted() {
  },

  beforeDestroy() {
    document.removeEventListener('keyup', this.keyDownHandler)
  },

  methods: {
    /**
     * @description: 键盘事件
     * @param {*} e
     * @return {*}
     */
    keyDownHandler(e) {
      if (e.code === 'Escape') {
        this.close()
      } else if (e.code === 'ArrowDown') {
        if (this.selectIndex < this.list.length - 1) {
          this.selectIndex++
        }
      } else if (e.code === 'ArrowUp') {
        if (this.selectIndex > 0) {
          this.selectIndex--
        }
      } else if (e.code === 'Enter') {
        this.selectClick(this.selectIndex)
      }
    },

    /**
     * @description: 选择点击
     * @return {*}
     */
    selectClick(index) {
      this.selectIndex = index
      if (this.selectIndex >= 0) {
        const data = this.list[this.selectIndex]
        data.name = data[this.tabConfig.nameKey]
        data.id = data[`${this.tabConfig.value}Id`]
        data.type = this.activeName
        this.$emit('change', data, {
          multiple: false,
          type: this.activeName
        })
        this.$emit('close')
      }
    },

    /**
     * @description: 关闭
     * @return {*}
     */
    close() {
      this.$emit('close')
    },

    /**
     * @description: tab 切换
     * @return {*}
     */
    tabClick() {
      this.getList()
    },

    /**
     * @description: 获取数据
     * @return {*}
     */
    getList() {
      this.loading = true
      const params = {
        page: 1,
        limit: 10,
        search: this.search || ''
      }

      // 员工搜索接口传值 realname
      if (this.config.showUser) {
        params.realname = params.search
        delete params.search
      }

      this.tabConfig.request(params).then(res => {
        this.list = res.data.list
        this.selectIndex = this.list.length > 0 ? 0 : -1
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },

    /**
     * @description: 暂时列表选择
     * @return {*}
     */
    showListClick() {
      if (this.activeName === 'user') {
        this.userDialogSelectVisible = true
      } else {
        this.showRelatePopover = true
      }
    },

    /**
     * @description: 员工选择
     * @return {*}
     */
    selectUserChange(userIds, deptIds, userList) {
      this.relateDataChange({ data: {
        user: userList
      }})
    },

    /**
     * 关联业务选择
     */
    relateDataChange(val) {
      const { data } = val
      const dataValue = {}
      for (let index = 0; index < this.tabs.length; index++) {
        const tab = this.tabs[index]
        const list = data[tab.value]
        if (list && list.length > 0) {
          const listValue = list.map(item => {
            item.name = item[tab.nameKey]
            item.id = item[`${tab.value}Id`]
            item.type = tab.value
            return item
          })
          dataValue[tab.value ] = listValue
        }
      }

      this.showRelatePopover = false
      if (this.activeName === 'user') {
        this.$emit('change', dataValue, {
          multiple: true, // 模块选择无类型
          type: this.activeName
        })
      } else {
        this.$emit('change', dataValue, {
          multiple: true // 模块选择无类型
        })
      }
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>
.module-select {
  display: flex;
  flex-direction: column;
  height: 100%;

  &-wrap {
    width: 300px;
    height: 400px;
  }

  &-header {
    flex-shrink: 0;
  }

  &-body {
    flex: 1;
    overflow-y: auto;
  }

  &-footer {
    flex-shrink: 0;
    text-align: right;
    border-top: $--border-base;

    .el-button {
      padding-bottom: 0;
    }
  }
}

.cell {
  height: 35px;
  padding: 0 8px;
  cursor: pointer;
  border-radius: $--border-radius-base;

  &-label {
    flex: 1;
  }

  &-value {
    margin-left: 8px;
    color: $--color-text-regular;
  }

  &.is-select {
    background-color: $--color-n30;
  }
}

::v-deep .el-tabs__header {
  margin: 0 0 8px;
}

::v-deep .el-tabs__item {
  padding: 0 6px;
}
</style>
