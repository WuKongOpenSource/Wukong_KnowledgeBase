<template>
  <div
    :style="{zIndex: zIndex}"
    class="el-dialog__wrapper">
    <div
      v-if="!showCreate && !showEdit"
      v-loading="loading"
      class="tag-dialog">
      <flexbox class="tag-dialog-header">
        <div class="tag-dialog-header__text">
          添加标签
        </div>
        <i
          class="el-icon-close icon-close"
          @click="handleClose" />
      </flexbox>

      <div class="tag-container">
        <div class="search-box">
          <el-input
            v-model.trim="searchKeyword"
            placeholder="搜索标签"
            @input="handleSearch" />
        </div>
        <div class="wrapper">
          <div class="title">
            已选择标签
          </div>
          <ul class="label-list">
            <li
              v-for="(item, index) in selectedList"
              :key="index"
              :style="{ backgroundColor: item.color }"
              class="label-list-item">
              <span>{{ item.name }}</span>
              <i
                class="el-icon-close"
                @click="handleRemoveTag(index)" />
            </li>
          </ul>
        </div>
        <div class="wrapper">
          <div class="title">
            全部标签
          </div>
          <ul class="label-list">
            <li
              v-for="(item, index) in tagList"
              :key="index"
              :style="{ backgroundColor: item.color }"
              class="label-list-item"
              @click="handleSelect(item)">
              <span>{{ item.name }}</span>
            </li>
          </ul>
        </div>
      </div>

      <flexbox
        align="center"
        class="dialog-footer el-dialog__footer">
        <flexbox-item>
          <el-dropdown
            placement="top-start"
            trigger="click"
            @command="handleMoreCommand">
            <el-button class="dropdown-btn" icon="el-icon-more" style="margin-right: 8px;" />
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                command="create"
                icon="el-icon-plus">
                创建新标签
              </el-dropdown-item>
              <el-dropdown-item
                command="setting"
                icon="el-icon-setting">
                管理标签
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </flexbox-item>
        <el-button type="primary" @click="handleConfirm">保存</el-button>
        <el-button @click="handleClose">取消</el-button>
      </flexbox>
    </div>

    <new-tag
      v-else-if="showCreate"
      v-loading="loading"
      :new-tag-title="newTagTitle"
      :new-tag-input="newTagInput"
      :bg-color-props="bgColorProps"
      @close="tagClose"
      @changeColor="changeColor"
      @tagCreateSubmit="tagCreateSubmit"
      @tagCancel="tagClose"
      @back="tagClose" />

    <!-- 标签管理 -->
    <edit-tag
      v-else-if="showEdit"
      v-loading="loading"
      :edit-tag-list="tagList"
      @back="tagClose"
      @close="tagClose"
      @editBtn="editTagClick"
      @deleteBtn="deleteBtn" />
  </div>
</template>

<script>
import {
  kmLabelQueryListAPI,
  kmLabelAddOrUpdateAPI,
  kmLabelDeleteByIdAPI
} from '@/api/knowledge/label'

import NewTag from '@/components/NewCom/WkTag/Tag/NewTag'
import EditTag from '@/components/NewCom/WkTag/Tag/EditTag'
import { getMaxIndex } from '@/utils'

export default {
  name: 'TagDialog',
  components: {
    NewTag,
    EditTag
  },
  props: {
    defaultValue: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      dialogVisible: false,

      zIndex: getMaxIndex(),

      searchKeyword: '',

      selectedList: [],

      tagList: [],
      allTagList: [],

      newTagTitle: '新增标签',
      newTagInput: '',
      bgColorProps: '',
      editTagId: null,

      showCreate: false,
      showEdit: false,

      loading: false
    }
  },
  created() {
    this.selectedList = [].concat(this.defaultValue)
    this.getAllTag()
    this.$nextTick(() => {
      this.dialogVisible = true
    })
  },
  methods: {
    /**
     * 获取全部标签
     */
    getAllTag() {
      this.loading = true
      kmLabelQueryListAPI().then(res => {
        this.loading = false
        this.tagList = res.data || []
        this.allTagList = [].concat(this.tagList)
        this.searchKeyword = ''
      }).catch(() => {
        this.loading = false
      })
    },

    /**
     * 标签搜索
     */
    handleSearch() {
      if (!this.searchKeyword) {
        this.tagList = [].concat(this.allTagList)
        return
      }
      this.tagList = this.allTagList.filter(tag => tag.name.includes(this.searchKeyword))
    },
    /**
     * 选择标签
     */
    handleSelect(tag) {
      const findIndex = this.selectedList.findIndex(o => o.labelId == tag.labelId)
      if (findIndex === -1) {
        this.selectedList.push(tag)
      }
    },
    /**
     * 移除标签
     */
    handleRemoveTag(index) {
      this.selectedList.splice(index, 1)
    },

    handleClose() {
      this.$emit('close')
    },
    handleConfirm() {
      this.$emit('update', this.selectedList)
      this.$emit('close')
    },

    handleMoreCommand(command) {
      this.tagClose()
      if (command === 'create') {
        // create
        this.showCreate = !this.showCreate
      } else if (command === 'setting') {
        // setting
        this.showEdit = true
      }
    },

    /**
     * 新建/编辑标签
     * @param name
     * @param color
     */
    tagCreateSubmit(name, color) {
      this.loading = true
      const params = {
        name,
        color
      }
      if (this.editTagId) {
        params.labelId = this.editTagId
      }
      kmLabelAddOrUpdateAPI(params).then(res => {
        this.tagClose()
        this.getAllTag()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    /**
     * 编辑标签
     */
    editTagClick(data) {
      this.newTagTitle = '编辑标签'
      this.newTagInput = data.name
      this.bgColorProps = data.color
      this.editTagId = data.labelId
      this.showEdit = false
      this.showCreate = true
    },
    /**
     * 关闭标签弹窗
     */
    tagClose() {
      this.newTagTitle = '新增标签'
      this.newTagInput = ''
      this.bgColorProps = ''
      this.editTagId = false
      this.showCreate = false
      this.showEdit = false
    },
    // 标签点击变色
    changeColor(val) {
      this.bgColorProps = val
    },
    /**
     * 标签管理 ——— 删除按钮
     */
    deleteBtn(val) {
      this.$confirm('确定删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        kmLabelDeleteByIdAPI(val.labelId).then(() => {
          this.$message.success('删除成功!')
          this.getAllTag()
        }).catch(() => {
          this.loading = true
        })
      }).catch()
    }
  }
}
</script>

<style scoped lang="scss">
  .el-dialog__wrapper {
    background-color: rgba(0, 0, 0, 0.5);

    .tag-dialog {
      width: 500px;
      padding-top: 10px;
      margin: 15vh auto 0;
      overflow: hidden;
      background-color: white;
      border-radius: $--border-radius-base;

      .tag-dialog-header {
        position: relative;
        height: 40px;
        padding: 0 24px;

        .tag-dialog-header__text {
          font-size: 16px;
          color: $--color-text-primary;
        }

        .icon-close {
          position: absolute;
          top: 50%;
          right: 24px;
          font-size: 20px;
          color: $--color-text-secondary;
          cursor: pointer;
          transform: translateY(-50%);
        }
      }

      .tag-container {
        padding: 10px 20px;

        .search-box {
          width: 100%;
          margin-bottom: 10px;
        }

        .wrapper {
          margin-bottom: 15px;

          .title {
            margin: 5px 0;
            color: $--color-text-secondary;
          }

          .label-list {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: flex-start;
            width: 100%;

            .label-list-item {
              width: unset;
              padding: 5px 10px;
              margin: 5px;
              font-size: 12px;
              color: white;
              cursor: pointer;
              border-radius: $--border-radius-base;

              &:first-child {
                margin-left: 0;
              }

              &:last-child {
                margin-right: 0;
              }
            }
          }
        }
      }

      .dialog-footer {
        height: 50px;
        padding: 0 24px;
        overflow: hidden;
      }
    }

    .new-tag-dialog,
    .edit-tag-dialog {
      width: 500px;
      margin: 15vh auto 0;
    }

    .edit-tag-dialog ::v-deep .tag-list {
      margin-bottom: 3px;
    }
  }
</style>
