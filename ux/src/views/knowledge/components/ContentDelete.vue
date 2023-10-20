<template>
  <div
    v-loading="loading"
    class="content-main">
    <delete-detail
      v-show="showDeleteDetail"
      :id="deleteDetailId"
      :type="deleteDetailType"
      @close="deleteDetailClose"
      @change="deleteDetailChange" />

    <section v-show="!showDeleteDetail" class="section static">
      <div class="section-body">
        <span class="tips">内容最多保留30天，之后将被永久删除。</span>
        <el-tabs
          v-model="activeTab"
          @tab-click="handleChange">
          <el-tab-pane :label="`文档 ${countObj.documentNum || ''}`" name="doc" />
          <el-tab-pane :label="`文件夹 ${countObj.folderNum || ''}`" name="folder" />
          <el-tab-pane :label="`文件 ${countObj.fileNum || ''}`" name="file" />
        </el-tabs>
        <div
          class="list">
          <flexbox
            v-for="(item, index) in list"
            :key="index"
            class="list-item"
            @click.native="handleToDetail(item)">
            <i
              v-if="item.type === 2"
              class="wk wk-folder file-icon folder" />
            <i
              v-else-if="item.type === 3"
              class="wk wk-doc file-icon doc" />
            <img
              v-else
              :src="getFileIcon(item.title)"
              alt=""
              class="img-icon">
            <flexbox-item class="list-item__name text-one-line">{{ item.title }}<span v-if="item.fileSize" class="list-item__info">（{{ item.fileSize | getFileSize }}）</span></flexbox-item>
            <span class="list-item__desc">删除于 {{ item.deleteTime }}</span>

            <el-button style=" padding: 0;margin-left: 8px;" type="primary-text" @click.stop="recoverClick(item)">恢复</el-button>
            <el-button type="primary-text" style="padding: 0;" @click.stop="deleteClick(item, index)">永久删除</el-button>
          </flexbox>
          <no-data v-if="list.length === 0" />
        </div>
      </div>
    </section>

  </div>
</template>

<script>
import {
  kmRecordRecentDeleteListAPI,
  kmRecordRestoreAPI
} from '@/api/knowledge/index'
import {
  kmDocCompletelyDeleteAPI
} from '@/api/knowledge/doc'
import {
  kmFolderCompletelyDeleteAPI
} from '@/api/knowledge/folder'

import NoData from './NoData'
import DeleteDetail from './DeleteDetail'
import { getFileIconWithSuffix, fileSize } from '@/utils/index'

export default {
  name: 'ContentDelete',
  components: {
    NoData,
    DeleteDetail
  },
  filters: {
    getFileSize(size) {
      return fileSize(Number(size))
    }
  },
  props: {
    id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      activeTab: 'doc',

      list: [],
      countObj: {},

      loading: false,
      showDeleteDetail: false,
      deleteDetailType: '',
      deleteDetailId: ''
    }
  },
  created() {
    this.getDetail()
  },
  methods: {
    /**
     * 获取数据 文件夹：2 文档：3 文件：4
     */
    getDetail() {
      this.loading = true
      kmRecordRecentDeleteListAPI({
        libraryId: this.id,
        type: {
          doc: 3,
          folder: 2,
          file: 4
        }[this.activeTab]
      }).then(res => {
        this.loading = false
        this.countObj = res.data || {}
        this.list = res.data.list || []
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
     * 切换tab
     */
    handleChange() {
      this.getDetail()
    },

    /**
     * 跳转到详情
     */
    handleToDetail(data) {
      this.deleteDetailId = data.type == 2 ? data.folderId : data.documentId
      this.deleteDetailType = data.type
      this.showDeleteDetail = true
    },

    /**
     * 恢复 和 删除
     */
    recoverClick(data) {
      const typeId = data.type == 2 ? data.folderId : data.documentId
      kmRecordRestoreAPI({
        id: typeId,
        type: data.type
      }).then(() => {
        this.$message.success('恢复成功')
        this.$emit('change')
        this.getDetail()
      }).catch()
    },

    deleteClick(data) {
      this.$confirm('确定要永久删除吗，删除后将无法恢复?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let request = null
        const params = {}
        if (data.type == 2) {
          request = kmFolderCompletelyDeleteAPI
          params.folderId = data.folderId
        } else {
          request = kmDocCompletelyDeleteAPI
          params.documentId = data.documentId
        }
        request(params).then(() => {
          this.$message.success('删除成功')
          this.getDetail()
          this.$emit('change')
        }).catch(() => {})
      }).catch(() => {})
    },

    /**
     * 删除详情关闭
     */
    deleteDetailClose() {
      this.showDeleteDetail = false
      this.getDetail()
    },

    /**
     * 详情内恢复或者删除
     */
    deleteDetailChange() {
      this.$emit('change')
    }
  }
}
</script>

<style scoped lang="scss">
  .content-main {
    height: 100%;
    padding: 32px;

    .section {
      height: 100%;

      .section-header__text {
        font-size: 16px;
        font-weight: 600;
      }

      .section-body {
        position: relative;
        height: 100%;
      }

      .tips {
        position: absolute;
        top: 13px;
        right: 0;
        color: $--color-text-placeholder;
      }

      &.static {
        .el-tabs {
          ::v-deep .el-tabs__header {
            margin-bottom: 0;
          }

          ::v-deep .el-tabs__header .el-tabs__nav-wrap::after {
            height: 1px;
          }
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

            .list-item__info {
              font-size: 14px;
              color: $--color-text-placeholder;
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
</style>
