<template>
  <div
    class="knowledge-item-add knowledge-item clearfix">
    <div class="content clearfix">
      <flexbox justify="center" align="center" orient="vertical">
        <i class="wk wk-l-plus" />
        <div>创建知识库</div>
      </flexbox>
    </div>
  </div>
</template>

<script>

export default {
  name: 'KnowledgeItemAdd',
  props: {},
  computed: {},
  methods: {}
}
</script>

<style scoped lang="scss">
  .knowledge-item-add {
    padding: 8px 5px;

    .content {
      position: relative;
      width: 100%;
      height: 0;
      padding-top: 50%;
      overflow: hidden;
      text-align: center;
      cursor: pointer;
      background: $--button-default-background-color;
      border: $--border-base;
      border-radius: $--border-radius-base;

      &:hover {
        background: $--button-default-hover-bg-color;
      }
    }

    .vux-flexbox {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;

      .wk-l-plus {
        display: inline-block;
        font-size: 16px;
      }

      div {
        margin-top: 10px;
      }
    }
  }
</style>
