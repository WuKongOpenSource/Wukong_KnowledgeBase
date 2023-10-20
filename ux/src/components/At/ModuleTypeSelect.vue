<template>
  <el-card
    class="module-type-select-wrap"
    :body-style="{ padding: '8px', height: '100%' }">
    <div
      v-loading="loading"
      class="module-type-select">
      <flexbox
        v-for="(item, index) in showTabs"
        :key="index"
        class="cell"
        :class="{'is-select': selectIndex === index}"
        @click.native="selectClick(index)"
        @mouseenter.native="selectIndex = index"
      >
        <i :class="item.icon" class="cell-icon" />
        <div class="cell-label text-one-line">{{ item.label }}</div>
        <div class="cell-value">{{ item.keyName }}</div>
      </flexbox>
      <el-empty
        v-if="showTabs.length === 0"
        :image-size="120"
        :image="require('@/assets/img/empty/data.png')"
        description="暂无匹配的搜索结果" />
    </div>

    <!-- 关联业务 -->
    <crm-relative
      v-if="showRelatePopover"
      ref="crmrelative"
      :visible.sync="showRelatePopover"
      :radio="false"
      :data-type="config.value"
      @close="showRelatePopover= false"
      @changeCheckout="relateDataChange" />

  </el-card>

</template>

<script>
import CrmRelative from '@/components/CreateCom/CrmRelative'

export default {
  // ModuleTypeSelect 模块大类选择
  name: 'ModuleTypeSelect',

  components: {
    CrmRelative
  },

  props: {
    search: String
  },

  data() {
    return {
      loading: false,
      selectIndex: 0,
      tabs: [
        { label: '客户', value: 'customer', nameKey: 'customerName', keyName: '/ke', icon: 'wk wk-customer-line' },
        { label: '联系人', value: 'contacts', nameKey: 'name', keyName: '/lxr', icon: 'wk wk-contacts-line' },
        { label: '商机', value: 'business', nameKey: 'businessName', keyName: '/sj', icon: 'wk wk-business-line' },
        { label: '合同', value: 'contract', nameKey: 'num', keyName: '/ht', icon: 'wk wk-contract-line' },
        { label: '回款', value: 'receivables', nameKey: 'number', keyName: '/hk', icon: 'wk wk-receivables-line' }
      ],

      showRelatePopover: false // 关联业务信息框
    }
  },

  computed: {
    config() {
      return this.tabs[this.selectIndex]
    },

    showTabs() {
      return this.tabs.filter(item => item.keyName.includes(this.search))
    }
  },

  watch: {
  },

  created() {
    document.addEventListener('keyup', this.keyDownHandler)
  },

  mounted() {},

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
        if (this.selectIndex < this.tabs.length - 1) {
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

      this.showRelatePopover = true
    },

    /**
     * @description: 关闭
     * @return {*}
     */
    close() {
      this.$emit('close')
    },

    /**
     * 关联业务选择
     */
    relateDataChange(val) {
      const { data } = val
      const dataValue = {}
      if (data && data.length > 0) {
        const listValue = data.map(item => {
          item.name = item[this.config.nameKey]
          item.id = item[`${this.config.value}Id`]
          item.type = this.config.value
          return item
        })
        dataValue[this.config.value ] = listValue
      }

      this.showRelatePopover = false
      this.$emit('change', dataValue) // 多选址
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>
.module-type-select {
  .cell {
    height: 35px;
    padding: 0 8px;
    cursor: pointer;
    border-radius: $--border-radius-base;

    &-icon {
      flex-shrink: 0;
      width: 30px;
    }

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
}
</style>
