<template>
  <div
    v-loading="loading"
    class="content-container">
    <flexbox v-if="detailData" class="main-top">
      <flexbox-item class="title">
        <span>{{ detailData.title }}</span><span>{{ $getUserName(detailData.createUser) }}于{{ detailData.createTime }}创建</span>
      </flexbox-item>
      <div class="control">
        <el-button
          v-if="currentUserAuth === 1"
          icon="wk wk-employees"
          type="text"
          class="control-btn"
          @click="showGroupControl = !showGroupControl">
          协作
        </el-button>
        <el-button
          :icon="detailData.collectStatus === 0 ? 'wk wk-focus-on' : 'wk wk-focus-on collected'"
          type="text"
          class="control-btn"
          @click="handleCollection(detailData)">
          {{ detailData.collectStatus === 0 ? '关注' : '取消关注' }}
        </el-button>
        <el-dropdown
          v-if="[1, 2].includes(currentUserAuth)"
          placement="bottom-start"
          trigger="click"
          @command="handleCommand">
          <el-button class="dropdown-btn" icon="el-icon-plus">添加内容</el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              command="doc"
              icon="wk wk-doc">
              创建文档
            </el-dropdown-item>
            <el-dropdown-item
              command="folder"
              icon="wk wk-folder">
              创建文件夹
            </el-dropdown-item>
            <el-dropdown-item
              command="upload"
              icon="wk wk-upload-simple">
              上传文件
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-dropdown
          v-if="currentUserAuth === 1"
          trigger="click"
          @command="handleCommand">
          <el-button class="dropdown-btn" icon="el-icon-more" />
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="move">移动文件夹至</el-dropdown-item>
            <el-dropdown-item command="deleteAll">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </flexbox>

    <div
      v-if="detailData"
      class="content-folder">
      <div class="folder-father">{{ detailData.title || '' }}</div>

      <div class="container">
        <div class="static">
          {{ detailData.childList.length }}个文件夹，{{ detailData.kmDocumentList.length }}个文档
        </div>
        <ul class="list">
          <li
            v-for="(item, index) in contentList"
            :key="index"
            class="list-item"
            @click="$emit('next', item)">
            <flexbox align="center" justify="flex-start">
              <i
                v-if="!item.hasOwnProperty('documentId')"
                class="wk wk-folder file-icon folder" />
              <i
                v-else-if="item.type === 3"
                class="wk wk-doc file-icon doc" />
              <img
                v-else
                :src="getFileIcon(item.title)"
                alt=""
                class="img-icon">
              <flexbox-item class="list-item__name">{{ item.title }}</flexbox-item>
              <span v-if="item.createTime" class="list-item__desc">创建于{{ item.createTime }}</span>
              <i
                :class="{active: item.collectStatus != 0}"
                class="wk wk-focus-on collect-btn"
                @click.stop="handleCollection(item)" />
              <div
                v-if="currentUserAuth === 1"
                @click.stop="chooseNodeData = item">
                <el-dropdown
                  trigger="hover"
                  class="list-item__control"
                  @command="handleCommand">
                  <el-button
                    icon="el-icon-more"
                    type="text" />
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="rename">重命名</el-dropdown-item>
                    <el-dropdown-item command="delete">删除</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </flexbox>
          </li>
        </ul>
      </div>
    </div>

    <group-control
      v-if="showGroupControl"
      :data="detailData"
      @close="showGroupControl = false"
      @update="getDetail" />
  </div>
</template>

<script>
import { kmFolderQueryByIdAPI } from '@/api/knowledge/folder'
import { kmCollectAddAPI, kmCollectCancelAPI } from '@/api/knowledge/index'
import GroupControl from '../components/GroupControl'

import { getFileIconWithSuffix } from '@/utils/index'

export default {
  name: 'ContentFolder',
  components: {
    GroupControl
  },
  props: {
    id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      loading: false,
      detailData: null,
      chooseNodeData: null,

      showGroupControl: false
    }
  },
  computed: {
    contentList() {
      if (!this.detailData) return []
      return this.detailData.childList.concat(this.detailData.kmDocumentList)
    },
    // 当前用户的权限  1 管理权限 2 编辑权限 3 只读权限
    currentUserAuth() {
      return Number(this.detailData.auth.currentUserAuth)
    }
  },
  watch: {
    id: {
      handler(val) {
        if (this.id && this.id > 0) {
          this.getDetail()
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    getDetail() {
      this.loading = true
      kmFolderQueryByIdAPI({
        folderId: this.id
      }).then(res => {
        this.loading = false
        this.detailData = res.data
        console.log('this.detailData', this.detailData)
      }).catch(() => {
        this.loading = false
      })
    },

    getFileIcon(filename) {
      const lastIndex = filename.lastIndexOf('.')
      const suffix = filename.slice(lastIndex + 1)
      return getFileIconWithSuffix(suffix)
    },

    /**
     * 判断是文件夹还是文档
     */
    getType(data) {
      return data.hasOwnProperty('documentId') ? 'doc' : 'folder'
    },

    handleCommand(command) {
      if (['doc', 'folder', 'upload'].includes(command)) {
        this.$emit('action', command, this.detailData)
        return
      }
      switch (command) {
        case 'delete':
          this.$emit('delete', this.chooseNodeData)
          break
        case 'deleteAll':
          this.$emit('delete', this.detailData)
          break
        case 'move':
          this.$emit('move', this.detailData)
          break
        case 'rename':
          this.$emit('rename', this.getType(this.chooseNodeData), this.chooseNodeData)
      }
    },

    /**
     * 添加到关注
     */
    handleCollection(data) {
      // console.log('collection')
      const type = this.getType(data)
      const params = {
        type: type === 'folder' ? 2 : data.type,
        typeId: type === 'folder' ? data.folderId : data.documentId
      }
      const request = data.collectStatus === 0 ? kmCollectAddAPI : kmCollectCancelAPI
      request(params).then(() => {
        this.$message.success(data.collectStatus === 0 ? '关注成功' : '已取消关注')
        data.collectStatus = data.collectStatus === 0 ? 1 : 0
        this.getDetail()
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
  .content-container {
    height: 100%;
    padding: 32px;

    .main-top {
      .title {
        color: $--color-text-secondary;

        span:first-child {
          margin-right: 4px;
        }
      }

      .control {
        .dropdown-btn {
          padding: 8px;
        }

        .el-dropdown {
          margin-left: 8px;
        }

        ::v-deep .wk-focus-on.collected {
          color: #fac23d;
        }
      }
    }

    .content-folder {
      .folder-father {
        padding-top: 8px;
        font-size: 24px;
      }

      .container {
        width: 100%;

        .static {
          padding: 32px 0 8px;
          color: $--color-text-placeholder;
          border-bottom: $--border-base;
        }

        .list {
          .list-item {
            position: relative;
            line-height: 40px;
            cursor: pointer;
            border-bottom: $--border-base;

            .file-icon {
              font-size: 16px;
              color: $--color-text-regular;
            }

            .img-icon {
              width: 16px;
            }

            .list-item__name {
              font-size: 14px;
              color: $--color-text-primary;
            }

            .list-item__desc {
              margin: 0 16px;
              font-size: 14px;
              color: $--color-text-secondary;
            }

            .collect-btn {
              margin-right: 16px;
              font-size: 14px;
              color: #e2e4e9;

              &.active {
                color: #fac23d;
              }
            }

            &:hover {
              background-color: #f7f7f7;
            }
          }
        }
      }
    }
  }

  .slide-wrapper {
    position: absolute;
    top: 0;
    right: 0;
    width: 350px;
    height: 100%;
  }
</style>
