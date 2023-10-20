<template>
  <el-dialog
    v-bind="$attrs"
    ref="wkDialog"
    title="创建分组"
    :visible="true"
    width="500px"
    v-on="$listeners"
  >
    <el-input v-model="groupName" placeholder="请输入分组名称" size="normal" clearable />
    <span slot="footer">
      <el-button type="primary" @click="saveHandle">保存</el-button>
      <el-button @click="()=>$emit('close')">取消</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { kmGroupAddGroupAPI } from '@/api/knowledge/group.js'

import ElDialogLoading from '@/mixins/ElDialogLoading.js'
export default {
  mixins: [ElDialogLoading],
  data() {
    return {
      loading: false,
      groupName: ''
    }
  },
  methods: {
    saveHandle() {
      if (!this.groupName) return this.$message.error('分组名称不能为空')
      this.loading = true
      const params = { name: this.groupName }
      kmGroupAddGroupAPI(params).then(res => {
        this.$message.success('添加成功')
        this.loading = false
        this.$emit('close')
        this.$emit('update')
      }).catch(e => {
        this.loading = false
      })
    }
  }
}
</script>
<style scoped lang="scss"></style>
