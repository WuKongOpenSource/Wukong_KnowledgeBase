<template>
  <div
    v-loading="loading"
    class="content-container">
    <flexbox v-if="detailData" class="main-top">
      <flexbox-item>
        <xr-breadcrumb v-model="breadcrumbs" @change="breadcrumbChange" />
      </flexbox-item>
      <div v-if="breadcrumbs.length <= 2" class="control">
        <el-button type="text" @click="recoverClick()">恢复</el-button>
        <el-button type="text" class="delete-btn" @click="deleteClick()">永久删除</el-button>
      </div>
    </flexbox>

    <div class="content-folder" :class="{'is-file': showType == 4 }">
      <template v-if="detailData && showType == 2">
        <flexbox class="folder-father">
          <flexbox-item class="folder-father__name">
            <img src="@/assets/img/knowledge/folder.png" alt="" class="icon">
            <span>{{ detailData.title || '' }}</span>
          </flexbox-item>
        </flexbox>
        <div class="container">
          <div class="static">
            {{ detailData.childList.length }}个文件夹，{{ detailData.kmDocumentList.length }}个文档
          </div>
          <ul class="list">
            <li
              v-for="(item, index) in contentList"
              :key="index"
              class="list-item"
              @click="nextClick(item)">
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
              </flexbox>
            </li>
          </ul>
        </div>
      </template>

      <template v-if="detailData && showType == 4">
        <div
          v-if="isImg"
          style="height: 100%;"
          class="img-view">
          <img
            v-src="detailData.content"
            alt="">
        </div>
        <!-- <perview
          v-else-if="detailData.content"
          :path="detailData.content"
          :name="detailData.title"
          style="width: 100%;height: 100%;"
          class="preview-file"
        /> -->
        <iframe
          v-else-if="detailData.content"
          :src="getPreviewUrl(detailData.content, detailData.title)"
          style="width: 100%;height: 100%;"
          frameborder="0"
          class="preview-file" />
      </template>

      <div v-show="showType == 3">
        <div
          v-if="detailData && !detailData.content && showType == 3"
          class="empty">
          这个文档是空的
        </div>
        <tinymce
          disabled
          :toolbar="[]"
          :init="{
            menubar: false,
            toolbar_sticky: true,
            statusbar: false,
            content_style: ' * {color: #262626;}',
            plugins: 'autoresize'
          }"
          :value="showType == 3 && detailData ? detailData.content : ''"
          class="rich-txt" />
      </div>
    </div>

  </div>
</template>

<script>
import {
  kmDocCompletelyDeleteAPI,
  kmDocQueryByIdAPI
} from '@/api/knowledge/doc'
import {
  kmFolderQueryByIdAPI,
  kmFolderCompletelyDeleteAPI
} from '@/api/knowledge/folder'
import { kmRecordRestoreAPI } from '@/api/knowledge/index'
import { getFileIconWithSuffix, wkPreviewFileUrl } from '@/utils/index'

import XrBreadcrumb from '@/components/XrBreadcrumb'
import Tinymce from '@/components/Tinymce'
// import Perview from '@/views/system/perview'

export default {
  name: 'DeleteDetail',
  components: {
    XrBreadcrumb,
    Tinymce
    // Perview
  },
  props: {
    type: [String, Number], // 3 文档 2 文件夹 4 文件
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
      breadcrumbs: [],
      showType: ''
    }
  },
  computed: {
    contentList() {
      if (!this.detailData) return []
      return this.detailData.childList.concat(this.detailData.kmDocumentList)
    },

    isImg() {
      if (!this.detailData || this.detailData.type === 3) return false
      const arr = ['jpg', 'png', 'jpeg', 'gif']
      const lastIndex = this.detailData.title.lastIndexOf('.')
      if (lastIndex === -1) return false
      const suffix = this.detailData.title.slice(lastIndex + 1)
      return arr.includes(suffix)
    }
  },
  watch: {
    id: {
      handler(val) {
        if (this.id) {
          this.detailData = null
          this.breadcrumbs = [{
            label: '最近删除'
          }]

          this.showType = this.type
          if (this.type == 2) {
            this.getFolderDetail(this.id)
          } else {
            this.getDocDetail()
          }
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    /**
     * @description: 获取预览地址
     * @param {*} path 地址
     * @param {*} name 名称
     * @return {*}
     */
    getPreviewUrl(path, name) {
      return wkPreviewFileUrl(path, name)
    },

    /**
     * 文件夹详情
     */
    getFolderDetail(id) {
      this.loading = true
      kmFolderQueryByIdAPI({
        folderId: id
      }).then(res => {
        this.loading = false
        this.detailData = res.data || {}
        this.showType = 2
        if (this.detailData.title) {
          this.breadcrumbs.push({
            label: this.detailData.title,
            type: this.showType,
            data: res.data || {}
          })
        }
      }).catch(() => {
        this.loading = false
      })
    },

    /**
     * 获取文档内容
     */
    getDocDetail(id) {
      if (id || this.id) {
        this.loading = true
        kmDocQueryByIdAPI({
          documentId: id || this.id
        }).then(res => {
          this.loading = false
          this.detailData = res.data
          this.showType = this.detailData.type
          if (this.detailData.title) {
            this.breadcrumbs.push({
              label: this.detailData.title,
              type: this.showType,
              data: res.data || {}
            })
          }
        }).catch(() => {
          this.loading = false
        })
      }
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

    /**
     * 恢复 和 删除
     */
    recoverClick() {
      kmRecordRestoreAPI({
        id: this.id,
        type: this.type
      }).then(() => {
        this.$message.success('恢复成功')
        this.$emit('change', 'restore')
        this.$emit('close')
      }).catch()
    },

    deleteClick() {
      this.$confirm('确定要永久删除吗，删除后将无法恢复?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let request = null
        const params = {}
        if (this.type == 2) {
          request = kmFolderCompletelyDeleteAPI
          params.folderId = this.id
        } else {
          request = kmDocCompletelyDeleteAPI
          params.documentId = this.id
        }
        request(params).then(() => {
          this.$message.success('删除成功')
          this.$emit('change', 'delete')
          this.$emit('close')
        }).catch(() => {})
      }).catch(() => {})
    },

    /**
     * 文件夹下一层
     */
    nextClick(data) {
      if (!data.hasOwnProperty('documentId')) {
        this.getFolderDetail(data.folderId)
      } else {
        this.showType = data
        this.getDocDetail(data.documentId)
      }
    },

    breadcrumbChange(index) {
      if (index == 0) {
        this.$emit('close')
      } else {
        // 文件夹
        if (this.type == 2) {
          if (this.breadcrumbs.length - 1 != index) {
            const breadcrumbItem = this.breadcrumbs[index]
            this.detailData = breadcrumbItem.data
            this.showType = breadcrumbItem.type || 2
            this.breadcrumbs = this.breadcrumbs.slice(0, index + 1)
          }
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
  .content-container {
    height: 100%;

    .main-top {
      min-height: 32px;
      border-bottom: $--border-base;

      .time {
        margin-left: 20px;
        font-size: 12px;
        color: $--color-text-secondary;
      }

      .control {
        .el-dropdown {
          margin-left: 10px;
        }

        .control-btn {
          ::v-deep i {
            margin-right: 5px;
          }
        }

        ::v-deep .wk-focus-on.collected {
          color: #fac23d;
        }
      }
    }

    .content-folder {
      height: calc(100% - 32px);
      overflow: auto;

      &.is-file {
        height: calc(100% - 48px);
        margin-top: 16px;
        overflow: hidden;
      }

      .empty {
        padding-top: 80px;
        font-size: 14px;
        color: #ccc;
        text-align: center;
      }

      .folder-father {
        padding: 20px 0;
        border-bottom: 1px solid $--border-color-base;

        .folder-father__name {
          display: flex;
          align-items: center;
          justify-content: flex-start;
          font-size: 24px;
          color: $--color-text-regular;

          .icon {
            width: 36px;
            margin-right: 10px;
            vertical-align: middle;
          }
        }
      }

      .img-view {
        width: 100%;
        padding: 24px;
        overflow: auto;
        text-align: center;
        background-color: $--background-color-base;
        border-radius: $--border-radius-base;

        img {
          margin: auto;
          vertical-align: middle;
        }
      }

      .container {
        width: 100%;

        .static {
          margin: 15px 0 5px;
          color: $--color-text-secondary;
        }

        .list {
          .list-item {
            position: relative;
            padding: 10px 15px 10px 5px;
            cursor: pointer;
            border-bottom: 1px solid $--border-color-base;

            .file-icon {
              font-size: 16px;
              color: $--color-text-regular;
            }

            .img-icon {
              width: 16px;
            }

            .list-item__name {
              font-size: 14px;
              color: $--color-text-regular;
            }

            .list-item__desc {
              margin: 0 10px;
              font-size: 14px;
              color: $--color-text-secondary;
            }

            .collect-btn {
              margin-right: 10px;
              font-size: 14px;
              color: #e2e4e9;

              &.active {
                color: #fac23d;
              }
            }

            .list-item__control {
              visibility: hidden;
            }

            &:hover {
              background-color: #f7f7f7;

              .list-item__control {
                visibility: unset;
              }
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

  ::v-deep .el-dropdown-menu__item {
    i {
      color: #8a94a6;
    }
  }

  ::v-deep .tox-tinymce {
    border: 0 none;
  }
</style>
