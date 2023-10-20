<template>
  <el-empty
    style="padding-top: 75px;"
    :image-size="300"
    :image="require('@/assets/img/empty/data.png')"
    :description="text" />
</template>

<script>
export default {
  name: 'NoData',
  props: {
    text: {
      type: String,
      default: '暂无数据'
    }
  }
}
</script>

<style scoped lang="scss">
</style>
