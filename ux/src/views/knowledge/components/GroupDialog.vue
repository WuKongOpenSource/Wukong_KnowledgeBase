<template>
  <el-dialog
    ref="wkDialog"
    class="group-dialog"
    title="管理分组"
    v-bind="$attrs"
    :visible="true"
    width="500px"
    v-on="$listeners">
    <div class="group-manage">
      <draggable v-model="draggableGroupList" v-bind="dragOptions" class="group-list">
        <flexbox v-for="item in draggableGroupList" :key="item.groupId" class="group-list-item sortable">
          <span class="grid">⋮⋮</span>
          <flexbox-item class="name">{{ item.name }}</flexbox-item>
          <span class="desc">{{ item.num || 0 }}个项目</span>
          <i
            class="el-icon-edit delete-icon"
            @click="editGroup(item)" />
          <i v-if="item.type !== 1 && item.type !== 2" class="el-icon-delete delete-icon" @click="deleteGroup(item)" />
        </flexbox>
      </draggable>
      <div class="group-list">
        <flexbox class="group-list-item">
          <i class="el-icon-plus" />
          <el-input v-model.trim="groupName" :maxlength="20" placeholder="添加项目分组" />
          <el-button type="primary" size="medium" @click="handleAdd">添加分组</el-button>
        </flexbox>
      </div>
    </div>
    <span slot="footer">
      <el-button type="primary" @click="confirmHandle">确定</el-button>
      <el-button @click="() => $emit('close')">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {
  kmGroupAddGroupAPI,
  kmGroupRemoveGroupByIdAPI,
  kmGroupUpdateGroupBatchAPI,
  kmGroupUpdateGroupAPI
} from '@/api/knowledge/group.js'

import draggable from 'vuedraggable'
import ElDialogLoadingMixin from '@/mixins/ElDialogLoading'
import { objDeepCopy } from '@/utils'

export default {
  components: {
    draggable
  },
  mixins: [ElDialogLoadingMixin],
  props: {
    groupList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      loading: false,
      groupName: '',
      dragOptions: {
        sort: true,
        handle: '.grid',
        draggable: '.sortable'
      },
      draggableGroupList: []
    }
  },
  watch: {
    groupList: {
      handler(value) {
        if (value) this.draggableGroupList = objDeepCopy(value)
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    confirmHandle() {
      this.loading = true
      kmGroupUpdateGroupBatchAPI(this.draggableGroupList.map((item, index) => {
        return {
          ...item,
          sort: index
        }
      })).then(res => {
        this.loading = false
        this.$emit('close')
        this.$emit('update')
      }).catch(e => {
        this.loading = false
      })
    },
    handleAdd() {
      const name = this.groupName
      if (!name) return this.$message.error('分组名称不能为空')
      kmGroupAddGroupAPI({ name }).then(res => {
        this.$message.success('添加成功')
        this.groupName = ''
        this.$emit('update')
      }).catch(console.error)
    },

    deleteGroup(item) {
      kmGroupRemoveGroupByIdAPI({ groupId: item.groupId }).then(res => {
        this.$message.success('操作成功')
        this.$emit('update')
      }).catch(console.error)
    },

    /**
     * @description: 编辑分组
     * @param {*} item
     * @return {*}
     */
    editGroup(item) {
      this.$prompt('请输入分组名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        inputPattern: /^.{1,50}$/
      }).then(({ value }) => {
        kmGroupUpdateGroupAPI({ groupId: item.groupId, name: value }).then(res => {
          this.$message.success('操作成功')
          this.$emit('update')
        }).catch(console.error)
      }).catch(() => {})
    }
  }
}
</script>

<style lang="scss" scoped>
.group-manage {
}

.group-list {
  max-height: 300px;
  overflow-y: auto;
  border: $--border-base;
  border-radius: $--border-radius-base;

  & + & {
    margin-top: 16px;
  }
}

.group-list-item {
  width: 100%;
  height: 38px;
  padding: 0 15px;
  font-size: $--font-size-base;
  background-color: white;
  border-bottom: $--border-base;

  &:last-child {
    border: 0 none;
  }

  .grid {
    font-weight: bold;
    color: $--color-n90;
    cursor: move;
  }

  .name {
    color: $--color-black;
  }

  .desc {
    color: $--color-n200;
  }

  .delete-icon {
    display: none;
    cursor: pointer;
  }

  .delete-icon + .delete-icon {
    margin-left: 8px;
  }

  .el-input {
    margin-left: 2px;

    ::v-deep .el-input__inner {
      border: 0 none;
    }
  }

  .el-button {
    margin-left: 6px;
  }

  &:hover {
    .desc {
      display: none;
    }

    .delete-icon {
      display: block;
    }
  }
}
</style>
