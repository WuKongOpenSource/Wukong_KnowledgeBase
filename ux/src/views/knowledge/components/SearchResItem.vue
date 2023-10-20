<template>
  <div class="search-res-item">
    <div class="item-header">
      <i class="wk wk-doc icon" />
      <span class="title" @click="titleClick">
        {{ data.title }}
      </span>
    </div>
    <div v-if="showContent" class="item-content">
      {{ content }}
    </div>
    <div class="item-footer">
      {{ data.libraryName }} | {{ data.updateTime || data.createTime }} <!--昨天15:00-->
    </div>
    <flexbox
      v-if="showLabel"
      align="center"
      justify="flex-start"
      class="label-box">
      <div
        v-for="(label, index) in data.labelList"
        :key="index"
        :style="{backgroundColor: label.color }"
        class="label">
        {{ label.name }}
      </div>
    </flexbox>
  </div>
</template>

<script>
export default {
  name: 'SearchResItem',
  props: {
    data: Object,
    showLabel: {
      type: Boolean,
      default: false
    },
    showContent: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    content() {
      // 删除所有html标签、空格、换行
      const str = this.data.content.replace(/<[^>]+>/g, '').replace(/[ ]|[\r\n]/g, '') || ''
      // TODO 搜索关键字加粗、加黑
      return str
    }
  },
  methods: {
    /**
     * 标题点击
     */
    titleClick() {
      this.$emit('titleClick', this.data)
    }
  }
}
</script>

<style scoped lang="scss">
  .search-res-item {
    .item-header {
      .title {
        margin-left: 5px;
        font-size: 16px;
        font-weight: bold;
        color: $--color-primary;
        cursor: pointer;

        &:hover {
          text-decoration: underline;
        }
      }
    }

    .item-content {
      margin-top: 8px;
      margin-left: 0;
      line-height: 1.5;
    }

    .item-footer {
      margin-top: 8px;
      margin-left: 0;
      color: $--color-text-secondary;
    }

    .label-box {
      padding: 5px 10px;

      .label {
        padding: 5px 10px;
        margin: 0 5px;
        font-size: 12px;
        color: white;
        border-radius: $--border-radius-base;
      }
    }
  }
</style>
