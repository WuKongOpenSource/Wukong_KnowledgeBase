<template>
  <div class="el-dialog__wrapper" style="z-index: 2001;">
    <div
      v-loading="loading"
      class="move-dialog">
      <flexbox class="move-dialog-header">
        <div
          v-if="!showChoose"
          class="box-move">
          移动至
        </div>
        <div
          v-else
          class="box-choose">
          <i
            class="el-icon-arrow-left icon-back"
            @click="handleBack" />
          <span class="text">选择移动位置</span>
        </div>
        <i
          class="el-icon-close icon-close"
          @click="$emit('close')" />
      </flexbox>

      <div
        class="move-dialog-body">
        <div class="search-box">
          <el-input
            v-model="searchKeyword"
            :placeholder="placeholder"
            @input="handleSearchValChange" />
        </div>
        <div v-if="!showChoose" class="main">
          <div class="main-desc">
            我参与的知识库
          </div>
          <div class="list">
            <flexbox
              v-for="(library, index) in filterLibraryList"
              :key="index"
              align="center"
              class="knowledge-item"
              @click.native="handleToChoose(library)">
              <img v-src="library.coverUrl" alt="" class="pic">
              <flexbox-item class="info-box">
                <div class="name text-one-line">
                  {{ library.name }}
                </div>
                <div class="desc text-one-line">
                  {{ library.description }}
                </div>
              </flexbox-item>
              <i class="el-icon-arrow-right icon-more" />
            </flexbox>
          </div>
        </div>

        <div v-else-if="chosenLibrary" class="choose-main">
          <div class="radio-group">
            <flexbox
              align="center"
              class="knowledge-item">
              <span
                :class="{ 'is-checked': chosenLibrary.isChecked }"
                class="el-radio__input"
                @click="handleChooseLib()">
                <span class="el-radio__inner" />
              </span>
              <img v-src="chosenLibrary.coverUrl" alt="" class="pic">
              <flexbox-item class="info-box">
                <div class="name text-one-line">
                  {{ chosenLibrary.name }}
                </div>
                <div class="desc text-one-line">
                  {{ chosenLibrary.description }}
                </div>
              </flexbox-item>
            </flexbox>

            <flexbox
              v-for="(node, index) in treeNode"
              :key="index"
              align="center"
              class="folder-item"
              @click.native="handleToNext(node)">
              <span
                :class="{
                  'is-checked': isRadioCheck(node),
                  'is-disabled': isRadioDisable(node)
                }"
                class="el-radio__input"
                @click.stop="handleChooseNode(node)">
                <span class="el-radio__inner" />
              </span>
              <i
                v-if="!node.hasOwnProperty('documentId')"
                class="wk wk-folder file-icon folder" />
              <i
                v-else-if="node.type === 3"
                class="wk wk-doc file-icon doc" />
              <img
                v-else
                :src="getFileIcon(node.title)"
                alt=""
                class="img-icon">
              <flexbox-item class="text-one-line" style="width: 0;">{{ node.title }}</flexbox-item>
              <i
                v-if="node.childList && node.childList.length > 0"
                class="el-icon-arrow-right icon-more" />
            </flexbox>
          </div>
        </div>
      </div>

      <flexbox
        align="center"
        class="move-dialog-footer el-dialog__footer">
        <flexbox-item
          v-if="chosenLibrary || chosenNode"
          class="text-one-line margin-right-interval">
          移动至{{ chosenLibrary.isChecked ? chosenLibrary.name : chosenNode.title }}
        </flexbox-item>
        <flexbox-item
          v-else
          class="text-one-line" />
        <el-button
          type="primary"
          @click="handleMove">
          移动
        </el-button>
      </flexbox>
    </div>
  </div>
</template>

<script>
import { kmFolderQueryTreeList, kmFolderMoveAPI } from '@/api/knowledge/folder'
import { kmDocMoveAPI } from '@/api/knowledge/doc'
import { kmLibraryListAPI } from '@/api/knowledge/index'
import { getFileIconWithSuffix } from '@/utils/index'

export default {
  name: 'MoveDialog',
  props: {
    data: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showChoose: false,

      libraryList: [],
      filterLibraryList: [],
      chosenLibrary: null,

      treeList: [],
      treeNode: [],
      chosenNode: null,

      searchKeyword: '',
      timer: null,
      stopSearch: false,

      loading: false
    }
  },
  computed: {
    placeholder() {
      this.clearFilter()
      return this.showChoose ? '搜索文件夹名称' : '搜索知识库名称'
    }
  },
  created() {
    this.getLibrary()
  },
  methods: {
    /**
       * 获取知识库列表
       */
    getLibrary() {
      this.loading = true
      kmLibraryListAPI().then(res => {
        this.loading = false
        this.libraryList = res.data || []
        this.filterLibraryList = [].concat(this.libraryList)
      }).catch(() => {
        this.loading = false
      })
    },

    /**
       * 搜索关键字改变
       */
    handleSearchValChange() {
      if (this.stopSearch) return
      if (this.timer) {
        clearTimeout(this.timer)
        this.timer = null
      }
      this.timer = setTimeout(() => {
        clearTimeout(this.timer)
        this.timer = null
        console.log('searchKeyword', this.searchKeyword)
        this.handleSearch()
      }, 800)
    },

    // 清空搜索框
    clearFilter() {
      this.stopSearch = true
      this.searchKeyword = ''
      this.$nextTick(() => {
        this.stopSearch = false
      })
    },

    /**
       * 搜索
       */
    handleSearch() {
      if (!this.showChoose) {
        // 如果搜索的是知识库
        if (!this.searchKeyword) {
          this.filterLibraryList = [].concat(this.libraryList)
          return
        }
        this.filterLibraryList = this.libraryList.filter(lib => {
          return lib.name.includes(this.searchKeyword)
        })
        console.log('search res', this.filterLibraryList)
      } else {
        // 如果搜索的是文件夹
        this.treeNode = this.filterFolder(this.treeList)
        this.handleChooseLib()
      }
    },

    filterFolder(treeList) {
      let arr = []
      treeList.forEach(node => {
        if (node.title.includes(this.searchKeyword)) {
          arr.push(node)
        } else if (node.childList) {
          arr = arr.concat(this.filterFolder(node.childList))
        }
      })
      return arr
    },

    /**
       * 获取知识库下的文件夹树
       */
    getMoveTree() {
      this.loading = true
      kmFolderQueryTreeList({
        libraryId: this.chosenLibrary.libraryId
      }).then(res => {
        this.loading = false
        let list = []
        list = this.formatTreeList(res.data ? res.data.childList : [])
        list = list.concat(res.data.kmDocumentList || [])
        this.treeList = list
        this.treeNode = [].concat(list)
      }).catch(() => {
        this.loading = false
      })
    },

    formatTreeList(list) {
      const that = this
      list.forEach(item => {
        if (!item.documentId) {
          if (!item.childList || item.childList.length === 0) {
            item.childList = []
            item.childList = item.childList.concat(item.kmDocumentList)
          } else {
            item.childList = item.childList.concat(item.kmDocumentList)
            that.formatTreeList(item.childList)
          }
        }
      })
      return list
    },

    /**
       * 选择知识库
       * @param library
       */
    handleToChoose(library) {
      this.handleChooseLib(library)
      this.getMoveTree()
      this.showChoose = true

      this.clearFilter()
    },

    /**
       * 选择知识库文件夹
       * @param node
       */
    handleChooseNode(node) {
      if (this.isRadioDisable(node)) {
        return
      }
      this.$set(this.chosenLibrary, 'isChecked', false)
      this.chosenNode = node
    },

    /**
       * 切换到下一级目录
       */
    handleToNext(node) {
      if (this.isRadioDisable(node)) {
        return
      }

      if (node.childList && node.childList.length > 0) {
        this.handleChooseLib()
        this.treeNode = node.childList
      } else {
        this.handleChooseNode(node)
      }

      this.clearFilter()
    },

    /**
       * 选择知识库根目录
       * @param data
       */
    handleChooseLib(data = null) {
      if (data) {
        this.chosenLibrary = data
      }
      this.$set(this.chosenLibrary, 'isChecked', true)
      this.chosenNode = null
    },

    /**
       * 返回上一级
       */
    handleBack() {
      if (this.treeNode.length === 0) {
        this.showChoose = false
        this.chosenLibrary = null
        this.chosenNode = null
        return
      }
      this.treeNode = this.getParentNode(this.treeNode[0].parentId, this.treeList)
      if (this.treeNode.length === 0) {
        this.showChoose = false
        this.chosenLibrary = null
        this.chosenNode = null
      }
    },

    /**
       * 获取和父节点同级的所有文件夹
       * @param parentId
       * @param treeList
       * @return {Array|*|Array|*}
       */
    getParentNode(parentId, treeList = []) {
      const findRes = treeList.find(node => node.folderId === parentId)
      if (findRes) return treeList
      for (let i = 0; i < treeList.length; i++) {
        const tree = treeList[i]
        if (tree.childList) {
          return this.getParentNode(parentId, tree.childList)
        }
      }
      return []
    },

    handleMove() {
      if (!this.chosenLibrary || (!this.chosenLibrary.isChecked && !this.chosenNode)) {
        this.$message.error('请选择目标文件夹')
        return
      }
      console.log('move')
      this.loading = true
      let params = {}
      let request = null
      if (this.data.documentId) {
        params = {
          // type: 3, // 文档
          documentId: this.data.documentId,
          libraryId: this.chosenLibrary.libraryId
        }

        if (this.chosenNode) {
          if (!this.chosenNode.hasOwnProperty('documentId')) {
            params.type = 2
            params.targetId = this.chosenNode.folderId
          } else {
            params.type = 3
            params.targetId = this.chosenNode.documentId
          }
        } else {
          params.type = 0
          params.targetId = 0
        }

        if (this.chosenNode) {
          params.authId = this.chosenNode.authId
        }
        request = kmDocMoveAPI
      } else {
        params = {
          type: 2, // 文件夹
          folderId: this.data.folderId,
          libraryId: this.chosenLibrary.libraryId,
          targetId: this.chosenNode ? this.chosenNode.folderId : 0
        }
        if (this.chosenNode) {
          params.authId = this.chosenNode.authId
        }
        request = kmFolderMoveAPI
      }

      request(params).then(() => {
        this.loading = false
        this.$message.success('移动成功')
        this.$emit('update')
        this.$emit('close')
      }).catch(() => {
        this.loading = false
      })
    },

    getFileIcon(filename) {
      const lastIndex = filename.lastIndexOf('.')
      const suffix = filename.slice(lastIndex + 1)
      return getFileIconWithSuffix(suffix)
    },

    isRadioDisable(node) {
      console.log(node, this.data.type)
      // 3 文档  4 附件 不允许
      if (node.type == 4 || node.type == 3) {
        return true
      }

      // 移动文件夹 文档不能选中
      if (!this.data.hasOwnProperty('documentId')) {
        if (node.type == 3) {
          return true
        } else if (this.data.folderId == node.folderId) {
          return true
        }
      }

      // 选中的文档
      if (this.data.type == 3 && this.data.documentId == node.documentId) {
        return true
      }

      return false
    },

    isRadioCheck(node) {
      if (this.chosenNode && node.type !== 4) {
        if (!node.hasOwnProperty('documentId')) {
          return node.folderId === this.chosenNode.folderId
        } else {
          return node.documentId === this.chosenNode.documentId
        }
      }

      return false
    }
  }
}
</script>

<style scoped lang="scss">
  .el-dialog__wrapper {
    background-color: rgba(0, 0, 0, 0.5);

    .move-dialog {
      width: 450px;
      padding-top: 10px;
      margin: 15vh auto 0;
      background-color: white;
      border-radius: $--border-radius-base;

      .move-dialog-header {
        position: relative;
        height: 40px;
        padding: 0 25px;

        .box-move {
          font-size: 16px;
          color: $--color-text-primary;
        }

        .icon-close {
          position: absolute;
          top: 50%;
          right: 25px;
          font-size: 20px;
          color: $--color-text-secondary;
          cursor: pointer;
          transform: translateY(-50%);
        }

        .box-choose {
          width: 100%;

          .icon-back {
            position: absolute;
            top: 50%;
            left: 25px;
            font-size: 20px;
            color: $--color-text-secondary;
            cursor: pointer;
            transform: translateY(-50%);
          }

          .text {
            display: inline-block;
            width: 100%;
            font-size: 20px;
            text-align: center;
            vertical-align: middle;
          }
        }
      }

      .move-dialog-body {
        height: 430px;
        padding: 0 25px;

        .search-box {
          margin-top: 10px;
          margin-bottom: 20px;
        }

        .knowledge-item {
          margin: 15px 0;
          cursor: pointer;

          .pic {
            width: 75px;
            height: 50px;
            margin-right: 5px;
            border-radius: $--border-radius-base;
          }

          .info-box {
            overflow: hidden;

            .name {
              width: 100%;
              margin-bottom: 5px;
              font-size: 14px;
              font-weight: 600;
            }

            .desc {
              width: 100%;
              font-size: 12px;
              color: $--color-text-secondary;
            }
          }

          .icon-more {
            font-size: 18px;
            color: $--color-text-secondary;
          }
        }

        .main {
          .main-desc {
            margin-bottom: 5px;
            color: $--color-text-regular;
          }

          .list {
            height: 340px;
            overflow-y: auto;
          }
        }

        .choose-main {
          height: 360px;
          overflow-y: auto;

          .el-radio__input {
            display: inline-block;
            margin-right: 10px;
            vertical-align: middle;

            .el-radio__inner {
              width: 16px;
              height: 16px;

              &::after {
                width: 6px;
                height: 6px;
              }
            }
          }

          .folder-item {
            margin: 15px 0;
            font-size: 16px;
            cursor: pointer;

            .file-icon {
              font-size: 16px;
              color: $--color-text-regular;
            }

            .img-icon {
              width: 16px;
            }
          }
        }
      }

      .move-dialog-footer {
        height: 50px;
        padding: 0 25px;
        overflow: hidden;
      }
    }
  }
</style>
