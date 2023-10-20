<template>
  <div class="knowledge-main">
    <el-container>
      <el-aside width="240px">
        <flexbox v-if="libraryData" class="header-cell">
          <flexbox justify="center" class="header-cell__hd">
            <img v-src="libraryData.coverUrl" alt="" class="pic">
          </flexbox>
          <div class="header-cell__bd">
            <div class="header-cell--label">{{ libraryData.name }}</div>
            <div class="header-cell--des">{{ libraryData.description }}</div>
          </div>
        </flexbox>

        <div class="main-nav">
          <div>
            <div
              :class="{active: activeCom === 'ContentMain'}"
              class="sec-item home"
              style="cursor: pointer;"
              @click="homeClick">
              <i class="wk wk-enterprise sec-item-icon" /><span class="sec-item-label">主页</span>
            </div>
            <flexbox class="sec-item catalog-box">
              <i class="wk wk-catalog sec-item-icon" /><div class="sec-item-label sec-item-body">目录</div>
              <!--<div @click="chooseNodeData = {}">-->
              <div v-if="canAccess">
                <el-dropdown
                  placement="bottom-start"
                  trigger="click"
                  @command="handleRootCommand">
                  <el-button
                    icon="el-icon-plus"
                    type="text"
                    class="common-text-btn dropdown-btn" />
                  <el-dropdown-menu slot="dropdown">

                    <el-tooltip effect="dark" content="创建可在线编辑的文档" placement="right">
                      <el-dropdown-item
                        command="doc"
                        icon="wk wk-doc">
                        创建文档
                      </el-dropdown-item>
                    </el-tooltip>
                    <el-tooltip effect="dark" content="创建文件夹管理文档与文件" placement="right">
                      <el-dropdown-item
                        command="folder"
                        icon="wk wk-folder">
                        创建文件夹
                      </el-dropdown-item>
                    </el-tooltip>
                    <el-tooltip effect="dark" content="支持office、pdf、rar、png等格式文件上传并预览" placement="right">
                      <el-dropdown-item
                        command="upload"
                        icon="wk wk-upload-simple">
                        上传文件
                      </el-dropdown-item>
                    </el-tooltip>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </flexbox>
          </div>

          <div class="catalog">
            <el-tree
              ref="catalogTree"
              :data="treeList"
              :props="treeConfig"
              :default-expanded-keys="[currentNodeKey]"
              :current-node-key="currentNodeKey"
              node-key="nodeKey"
              @node-click="handleNodeClick">
              <flexbox
                :id="data.nodeKey"
                slot-scope="{ node, data }"
                class="catalog-item">
                <i v-if="!data.hasOwnProperty('documentId')" class="wk wk-folder" />
                <i v-else-if="data.type === 3" class="wk wk-doc" />
                <img v-else :src="getFileIcon(data.title)" class="file-icon">
                <flexbox-item class="text-one-line" style="width: 0;">
                  {{ node.label }}
                </flexbox-item>
                <div>
                  <el-dropdown
                    v-if="data.currentUserAuth != 3 && canAccess"
                    placement="bottom-start"
                    trigger="click"
                    class="catalog-item__more"
                    @command="handleCommand($event, data)">
                    <el-button
                      icon="el-icon-more"
                      type="text"
                      class="common-text-btn dropdown-btn"
                      @click.stop="" />
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item command="move">移动</el-dropdown-item>
                      <el-dropdown-item :command="data | getType">重命名</el-dropdown-item>
                      <el-dropdown-item command="delete">删除</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  <el-dropdown
                    v-if="(!data.hasOwnProperty('documentId')) && data.currentUserAuth != 3 && canAccess"
                    placement="bottom-start"
                    trigger="click"
                    class="catalog-item__add"
                    @command="handleCommand($event, data)">
                    <el-button
                      icon="el-icon-plus"
                      type="text"
                      class="common-text-btn dropdown-btn"
                      @click.stop="" />
                    <el-dropdown-menu slot="dropdown">
                      <el-tooltip effect="dark" content="创建可在线编辑的文档" placement="right">
                        <el-dropdown-item
                          command="doc"
                          icon="wk wk-doc">
                          创建文档
                        </el-dropdown-item>
                      </el-tooltip>
                      <el-tooltip
                        v-if="!data.hasOwnProperty('documentId')"
                        effect="dark"
                        content="创建文件夹管理文档与文件"
                        placement="right">
                        <el-dropdown-item
                          command="folder"
                          icon="wk wk-folder">
                          创建文件夹
                        </el-dropdown-item>
                      </el-tooltip>
                      <el-tooltip
                        v-if="!data.hasOwnProperty('documentId')"
                        effect="dark"
                        content="支持office、pdf、rar、png等格式文件上传并预览"
                        placement="right">
                        <el-dropdown-item
                          command="upload"
                          icon="wk wk-upload-simple">
                          上传文件
                        </el-dropdown-item>
                      </el-tooltip>
                    </el-dropdown-menu>
                  </el-dropdown>
                </div>
              </flexbox>
            </el-tree>
          </div>

          <flexbox
            v-if="canAccess"
            :class="{active: activeCom === 'ContentDelete'}"
            class="sec-item recent-delete"
            style="cursor: pointer;"
            @click.native="checkDeleteClick">
            <i class="wk wk-icon-bin" />
            <flexbox-item>最近删除</flexbox-item>
          </flexbox>
        </div>
      </el-aside>

      <el-main :key="`${refreshKey}`">
        <content-main
          v-if="activeCom === 'ContentMain'"
          :id="libraryId"
          @update="libraryData = $event"
          @next="handleNodeClick" />

        <content-folder
          v-else-if="activeCom === 'ContentFolder'"
          :id="chooseNodeData.folderId"
          @delete="deleteFolderOrDoc"
          @move="handleToMove"
          @rename="handleToRename"
          @action="handleCommand"
          @next="handleNodeClick" />

        <content-file
          v-else-if="activeCom === 'ContentFile'"
          :id="chooseNodeData.documentId"
          @delete="deleteFolderOrDoc"
          @move="handleToMove" />

        <content-word
          v-else-if="activeCom === 'ContentWord'"
          :id="chooseNodeData.documentId"
          @delete="deleteFolderOrDoc"
          @move="handleToMove" />

        <content-delete
          v-else-if="activeCom === 'ContentDelete'"
          :id="libraryId"
          ref="contentDelete"
          @change="getFolderTree" />
      </el-main>
    </el-container>

    <move-dialog
      v-if="showMove"
      :data="moveItem"
      @close="showMove = false"
      @update="getFolderTree" />

    <el-dialog
      v-loading="loading"
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="500px">
      <el-form>
        <el-form-item :label="dialogLabel">
          <el-input
            v-model.trim="folderOrDocName"
            :maxlength="50" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createFolderOrDoc()">保存</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <input
      ref="fileHook"
      accept="*/*"
      type="file"
      class="file-input"
      @change="handleFileUpload">
  </div>
</template>

<script>
import {
  kmLibraryQueryByIdAPI
} from '@/api/knowledge/index'
import {
  kmFolderQueryTreeList
} from '@/api/knowledge/folder'
import {
  kmFolderAddOrUpdateAPI,
  kmFolderDeleteByIdAPI
} from '@/api/knowledge/folder'
import {
  kmDocAddOrUpdateAPI,
  kmDocDeleteByIdAPI
} from '@/api/knowledge/doc'

import ContentMain from '../components/ContentMain'
import ContentFolder from '../components/ContentFolder'
import ContentFile from '../components/ContentFile'
import ContentWord from '../components/ContentWord'
import ContentDelete from '../components/ContentDelete'
import MoveDialog from '../components/MoveDialog'

import { getFileIconWithSuffix, objDeepCopy } from '@/utils/index'

export default {
  name: 'KnowledgeMain',
  filters: {
    getType(data) {
      return data.hasOwnProperty('documentId') ? 'renamedoc' : 'renamefolder'
    }
  },
  components: {
    ContentMain,
    ContentFolder,
    ContentFile,
    ContentWord,
    ContentDelete,
    MoveDialog
  },
  beforeRouteUpdate(to, from, next) {
    this.initInfo(to)
    next()
  },
  data() {
    return {
      activeCom: 'ContentMain',
      libraryData: null, // 知识库详情

      showMove: false,
      moveItem: null,

      treeList: [], // 文件夹树
      treeConfig: {
        label: 'title',
        children: 'childList'
      },
      domIds: [],
      chooseNodeData: null,

      dialogTitle: '新建文件夹', // 弹窗标题
      dialogVisible: false, // 弹窗控制标志
      dialogLabel: '文件夹名称', // 弹窗表单描述
      folderOrDocName: null, // 弹窗表单值
      editData: null, // 弹窗要编辑的对象

      nowCommand: null,

      loading: false,
      refreshKey: Date.now(),
      canAccess: false // 能访问
    }
  },
  computed: {
    currentNodeKey() {
      if (!this.chooseNodeData || Object.keys(this.chooseNodeData).length === 0) return ''
      const o = this.chooseNodeData
      if (this.chooseNodeData.documentId) {
        return `${o.libraryId}-${o.folderId}-${o.documentId}`
      } else {
        return `${o.libraryId}-${o.folderId}`
      }
    }
  },
  watch: {
    // 标记当前选中的目录树节点
    currentNodeKey: {
      handler(newVal, oldVal) {
        this.$nextTick(() => {
          // console.log('val: ', newVal)
          setTimeout(() => {
            this.domIds.forEach(id => {
              // console.log('id: ', id)
              if (id === newVal || id === oldVal) {
                const el = document.getElementById(id)
                if (el) {
                  let className = el.parentNode.className
                  className = className.replace(' current', '')
                  if (newVal === id) {
                    className += ' current'
                  }
                  // console.log('class-----', newVal, id, className)
                  el.parentNode.setAttribute('class', className)
                }
              }
            })
          }, 400)
        })
      }
      // immediate: true
    },

    activeCom(val) {
      if (val == 'ContentDelete' || val == 'ContentMain') {
        this.chooseNodeData = null
      }
    }
  },
  created() {
    this.initInfo(this.$route)
  },
  methods: {
    /**
     * 获取知识库详情
     */
    getDetail() {
      kmLibraryQueryByIdAPI({
        libraryId: this.libraryId
      }).then(res => {
        this.libraryData = res.data
      }).catch(() => {
      })
    },

    initInfo(route) {
      this.libraryId = route.query.id
      const folderId = route.query.folderId
      const documentId = route.query.documentId
      const type = route.query.type
      let nodeData = null
      if (folderId || folderId == 0) {
        nodeData = {
          libraryId: this.libraryId,
          folderId
        }
        if (documentId) {
          nodeData.documentId = documentId
          nodeData.type = Number(type)
        }
      }
      this.getFolderTree(nodeData)
      this.getDetail()
    },

    /**
     * 查询文件夹树
     */
    getFolderTree(nodeData = null) {
      kmFolderQueryTreeList({
        libraryId: this.libraryId
      }).then(res => {
        // 有权限访问
        this.canAccess = res.data.currentUserAuth == 1
        if (this.canAccess) {
          let list = []
          list = this.formatTreeList(res.data ? res.data.childList : [])
          list = list.concat(res.data.kmDocumentList || [])
          this.domIds = []
          this.treeList = this.addNodeKey(list)

          this.$nextTick(() => {
            if (nodeData) {
              this.handleNodeClick(nodeData)
            } else if (!this.activeCom) {
              this.activeCom = 'ContentMain'
            }
          })
        } else {
          this.treeList = []
        }
      }).catch()
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
     * 给目录树追加唯一key
     */
    addNodeKey(list) {
      list.forEach(o => {
        o.nodeKey = `${o.libraryId}-${o.folderId}`
        if (o.documentId && o.type == 4) {
          o.nodeKey += `-${o.documentId}`
        } else {
          if (o.type == 3) {
            o.nodeKey += `-${o.documentId}`
          }
          this.addNodeKey(o.childList)
        }
        this.domIds.push(o.nodeKey)
      })
      return list
    },

    getFileIcon(filename) {
      const lastIndex = filename.lastIndexOf('.')
      const suffix = filename.slice(lastIndex + 1)
      console.log('suffix', suffix)
      return getFileIconWithSuffix(suffix)
    },

    /**
     * @description: 主页点击
     * @param {*}
     * @return {*}
     */
    homeClick() {
      this.$refs.catalogTree.setCurrentKey(null)
      this.activeCom = 'ContentMain'
    },

    /**
     * @description: 查看删除列表
     * @param {*}
     * @return {*}
     */
    checkDeleteClick() {
      this.$refs.catalogTree.setCurrentKey(null)
      this.activeCom = 'ContentDelete'
    },
    /**
     * node click
     */
    handleNodeClick(data, node = null) {
      console.log(data, node)
      this.chooseNodeData = data
      if (data.hasOwnProperty('documentId')) {
        if (data.type === 3) {
          // 文档
          this.activeCom = 'ContentWord'
        } else if (data.type === 4) {
          // 文件
          this.activeCom = 'ContentFile'
        }
      } else {
        // 文件夹
        this.activeCom = 'ContentFolder'
      }
    },

    /**
     * 去移动
     */
    handleToMove(data) {
      this.moveItem = data
      this.showMove = true
    },

    handleRootCommand(command) {
      this.handleCommand(command, null, {})
    },

    handleCommand(command, data = null) {
      console.log(command, data, arguments)
      this.folderOrDocName = null
      this.nowCommand = command
      if (data) {
        this.editData = objDeepCopy(data) // editData 为空 创建的 是根文件
      } else {
        // 仅创建 根文件
        // this.editData = Object.assign({}, this.chooseNodeData)
      }
      switch (command) {
        case 'doc':
          this.dialogTitle = '新建文档'
          this.dialogLabel = '文档名称'
          this.dialogVisible = true
          break
        case 'folder':
          this.dialogTitle = '新建文件夹'
          this.dialogLabel = '文件夹名称'
          this.dialogVisible = true
          break
        case 'upload':
          this.handleToChooseFile()
          break
        case 'renamedoc':
          this.handleToRename('doc', this.editData)
          break
        case 'renamefolder':
          this.handleToRename('folder', this.editData)
          break
        case 'delete':
          this.deleteFolderOrDoc(data)
          break
        case 'move':
          this.handleToMove(data) // 和详情共用方法
          break
      }
    },

    handleToChooseFile() {
      this.$refs.fileHook.value = null
      this.$refs.fileHook.click()
    },

    /**
     * 文件上传
     */
    handleFileUpload(event) {
      const file = event.target.files[0]
      this.loading = true
      this.$wkUploadFile.upload({
        file: file
      }).then(({ res }) => {
        this.createFile(res.data || {})
      }).catch(() => {
        this.loading = false
      })
    },

    /**
     * 创建文件
     */
    createFile(data) {
      const params = {
        content: `${data.url}`,
        title: data.name,
        libraryId: this.libraryId,
        folderId: (this.editData && this.editData.folderId) ? this.editData.folderId : 0,
        status: 1,
        type: 4
      }
      kmDocAddOrUpdateAPI(params).then(() => {
        this.$message.success('添加成功')
        this.loading = false
        this.dialogVisible = false
        this.nowCommand = null
        this.editData = null
        this.refreshKey = Date.now()
        this.getFolderTree()
      }).catch()
    },

    /**
     * 重命名弹窗
     */
    handleToRename(type, data) {
      this.dialogTitle = type === 'doc' ? '重命名文档' : '重命名文件夹'
      this.dialogLabel = type === 'doc' ? '文档名称' : '文件夹名称'
      this.nowCommand = type === 'doc' ? 'renamedoc' : 'renamefolder'
      this.folderOrDocName = data.title
      this.editData = data
      this.dialogVisible = true
    },

    /**
     * 新建/重命名(文档/文件夹)
     */
    createFolderOrDoc() {
      console.log('choose', this.editData)
      if (!this.folderOrDocName) {
        this.$message.error('名称不能为空')
        return
      }
      let request = null
      let params = {
        title: this.folderOrDocName,
        libraryId: this.libraryId
      }
      if (['renamedoc', 'doc'].includes(this.nowCommand)) {
        request = kmDocAddOrUpdateAPI
        params = {
          ...params,
          content: '',
          status: 1,
          type: (this.editData && this.editData.type) ? this.editData.type : 3
        }

        // 文档新建
        if (this.nowCommand == 'doc' && this.editData && this.editData.type == 3) {
          params.folderId = 0
          params.parentId = this.editData.documentId
        } else {
          params.folderId = (this.editData && this.editData.folderId) ? this.editData.folderId : 0
        }

        if (this.nowCommand === 'renamedoc') {
          params.documentId = this.editData.documentId
          delete params.content
        }
      } else {
        request = kmFolderAddOrUpdateAPI
        params = {
          ...params,
          parentId: this.editData ? this.editData.folderId : 0
        }
        if (this.nowCommand === 'renamefolder') {
          params.folderId = this.editData.folderId
          params.parentId = this.editData.parentId
        }
      }

      console.log('save: ', params)
      this.loading = true
      request(params).then(res => {
        this.$message.success('操作成功')
        this.loading = false
        this.dialogVisible = false
        this.editData = null
        if (['doc', 'folder'].includes(this.nowCommand)) {
        // if (this.nowCommand === 'doc') {
          this.getFolderTree(res.data)
        } else {
          this.getFolderTree()
        }
        this.refreshKey = Date.now()
        this.nowCommand = null
      }).catch(() => {
        this.loading = false
      })
    },

    /**
     * 删除文档/文件夹
     */
    deleteFolderOrDoc(data = null) {
      if (!data) {
        data = this.chooseNodeData
      }

      console.log(this.chooseNodeData, 'this.chooseNodeData')

      const type = data.hasOwnProperty('documentId') ? 'doc' : 'folder'
      const message = type === 'folder'
        ? `您确定要删除该文件夹及文件夹下所有内容吗？<br/>删除30天内可在知识库【${this.libraryData ? this.libraryData.name : ''}】的最近删除恢复`
        : `您确定要删除该文档吗？<br/>删除30天内可在知识库【${this.libraryData ? this.libraryData.name : ''}】的最近删除恢复`
      this.$confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        dangerouslyUseHTMLString: true,
        type: 'warning'
      }).then(() => {
        const request = type === 'folder' ? kmFolderDeleteByIdAPI : kmDocDeleteByIdAPI
        const paramsKey = type === 'folder' ? 'folderId' : 'documentId'
        request({
          [paramsKey]: data[paramsKey]
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          // 如果再最近删除 左侧菜单进行了删除 刷新
          if (this.activeCom === 'ContentDelete') {
            this.$refs.contentDelete.getDetail()
          } else {
            if (this.activeCom !== 'ContentMain' && !this.treeList.length) {
              this.activeCom = 'ContentMain'
            } else if (this.activeCom !== 'ContentMain' && this.activeCom !== 'ContentDelete') {
              // 未找到可切换索引 返回主页
              if (this.deleteChangeSelect(data) === false) {
                this.activeCom = 'ContentMain'
              } else {
                this.$nextTick(() => {
                  const nodeData = this.$refs.catalogTree.getNode(this.currentNodeKey)
                  if (nodeData && nodeData.data) {
                    this.handleNodeClick(nodeData.data)
                  }
                })
              }
            }
          }

          this.getFolderTree()
        }).catch()
      }).catch(() => {})
    },

    /**
     * 删除后如果在内容区域刷新索引
     */
    deleteChangeSelect(data) {
      let currentNodeKey = ''
      if (data.documentId) {
        currentNodeKey = `${data.libraryId}-${data.folderId}-${data.documentId}`
      } else {
        currentNodeKey = `${data.libraryId}-${data.folderId}`
      }
      const brotherNodeKey = this.getBrotherNodeKey(currentNodeKey)
      if (brotherNodeKey) {
        const brotherIds = brotherNodeKey.split('-')

        if (brotherIds.length == 3) {
          this.chooseNodeData = {
            libraryId: brotherIds[0],
            folderId: brotherIds[1],
            documentId: brotherIds[2]
          }
        } else if (brotherIds.length == 2) {
          this.chooseNodeData = {
            libraryId: brotherIds[0],
            folderId: brotherIds[1]
          }
        } else {
          return false
        }
      } else {
        return false
      }
    },

    getBrotherNodeKey(nodeKey) {
      let brotherIndex = -1
      this.domIds.forEach((key, index) => {
        if (nodeKey == key) {
          brotherIndex = index
        }
      })

      if (brotherIndex >= 0) {
        return this.domIds[brotherIndex + 1] || this.domIds[brotherIndex - 1]
      }

      return null
    }
  }
}
</script>

<style scoped lang="scss">
// 菜单样式
@import "@/views/layout/components/Sidebar/variables.scss";
@import "@/views/layout/components/style.scss";

.header-cell {
  position: relative;
  padding: 24px 16px 16px;

  &::after {
    position: absolute;
    right: 0;
    bottom: 0;
    left: 0;
    display: block;
    height: 2px;
    content: "";
    background-color: $--border-color-base;
    border-radius: 1px;
  }
}

.header-cell__hd {
  background-color: white;

  .pic {
    width: 100%;
    height: 100%;
    border-radius: $--border-radius-base;
    object-fit: cover;
  }
}

.common-text-btn {
  padding: 2px;
  color: $--color-text-regular;
}

.remind-dialog {
  ::v-deep .el-dialog {
    .el-dialog__body {
      padding: 0 20px 15px;
    }
  }

  ::v-deep .el-form-item__label {
    padding-bottom: 0;
  }
}

.knowledge-main {
  width: 100%;
  height: 100%;

  .el-container {
    height: 100%;

    .el-aside {
      height: 100%;
      background-color: $menuBg;

      .main-nav {
        padding: 8px 4px 8px 16px;
        color: $--color-text-regular;
      }

      .sec-item {
        width: 100%;
        padding: 8px;

        &-icon {
          color: $--color-text-regular;
        }

        &-label {
          margin-left: 8px;
          color: $--color-text-primary;
        }

        &-body {
          flex: 1;
        }

        &.active {
          background-color: $--background-color-base;
          border-radius: $--border-radius-base;
        }

        &.recent-delete {
          border-left: 2px solid transparent;
        }
      }

      .catalog {
        ::v-deep .el-tree {
          .el-tree-node__content {
            height: 40px;
            border-radius: $--border-radius-base;

            &:hover {
              .el-dropdown {
                visibility: unset;
              }
            }
          }

          .el-tree-node__children {
            overflow: visible;
          }

          .el-tree-node__expand-icon {
            color: $--color-text-regular;
          }

          .is-leaf {
            visibility: hidden;
          }

          .el-tree--highlight-current,
          .el-tree-node.is-current > .el-tree-node__content {
            color: $--color-primary;
            background-color: $--background-color-base !important;

            .wk-folder,
            .wk-doc {
              color: $--color-primary;
            }
          }

          .el-tree-node:focus > .el-tree-node__content {
            background-color: white;
          }
        }

        .catalog-item {
          padding-right: 4px;

          .el-dropdown {
            margin-left: 8px;
            visibility: hidden;
          }

          // &.active, &:hover {
          //   background-color: #f6f9f9;
          //   .el-dropdown {
          //     visibility: unset;
          //   }
          // }
          .file-icon {
            width: 11px;
            margin: 0 3px;
          }

          .wk-folder,
          .wk-doc {
            color: $--color-text-regular;
          }
        }
      }
    }

    .el-main {
      position: relative;
      padding: 0;
      overflow-x: hidden;
      overflow-y: auto;
      background-color: white;
      border: $--border-base;
    }
  }
}

.file-input {
  display: none;
}
</style>
