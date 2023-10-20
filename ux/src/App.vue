<template>
  <div id="app">
    <router-view class="router-view" />
  </div>
</template>

<script>

export default {
  name: 'App',
  watch: {
    $route(to, from) {
      const { meta, params/*, name*/ } = to
      let title = this.WKConfig.companyName
      if (meta.title) {
        title += ' - ' + meta.title
      } else if (params && params.title) {
        title += ' - ' + params.title
      }
      document.title = title

      this.$wkPreviewFile.closeViewer() // 切换页面隐藏图片预览
    }
  }
}
</script>

<style lang="scss">
#app {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
  min-width: 1200px;
  height: 100%;
  min-height: 605px;
}
</style>
