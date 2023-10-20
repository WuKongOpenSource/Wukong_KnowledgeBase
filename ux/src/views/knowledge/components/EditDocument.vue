<template>
  <div>
    <flexbox
      :style="{zIndex: maxIndex}"
      align="flex-start"
      justify="flex-start"
      class="edit-document">

      <div class="left">
        <i
          class="wk wk-back-large"
          @click="handleBack" />
      </div>

      <flexbox-item style="margin: 0 15px;">
        <tinymce
          ref="createTinymce"
          v-model="docContent"
          :toolbar="['undo redo | bold italic underline strikethrough | charmap emoticons | fontselect fontsizeselect formatselect | rowspacingtop rowspacingbottom | lineheight | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | table | forecolor backcolor removeformat | pagebreak | fullscreen  preview save print | insertfile image media link anchor codesample | ltr rtl | paste | uploadFile']"
          :init="getEditConfig()"
          :height="editHeight"
          class="rich-txt"
          @input="debouncedEdiorInput" />
      </flexbox-item>

      <div class="right">
        <el-button
          type="primary"
          @click="handleSave">
          发布
        </el-button>
        <el-button
          @click="handleBack">
          取消
        </el-button>
        <el-dropdown
          trigger="click"
          @command="handleCommand">
          <el-button class="dropdown-btn" icon="el-icon-more" />
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="preview">预览</el-dropdown-item>
            <el-dropdown-item command="tag">添加标签</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </flexbox>

    <tag-dialog
      v-if="showTag"
      :default-value="tagList"
      @update="handleUpdateTag"
      @close="showTag = false" />

    <input
      ref="fileInput"
      type="file"
      class="rc-head-file"
      accept="*/*"
      multiple
      @change="uploadFile">

  </div>
</template>

<script>
import { kmDocAddOrUpdateAPI } from '@/api/knowledge/doc'

import Tinymce from '@/components/Tinymce'
import TagDialog from './TagDialog'

import { getMaxIndex, objDeepCopy, downloadFile, getFileTypeIcon } from '@/utils/index'
import { debounce } from 'throttle-debounce'

const sysStr = `<div id="wk-knowledge-title-wukong">
  <p id="wk-knowledge-content-wukong">为页面添加标题</p>
</div><p></p>
`

export default {
  name: 'EditDocument',
  components: {
    Tinymce,
    TagDialog
  },
  props: {
    data: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      maxIndex: 10,
      docContent: '',
      sysStr: '',
      editHeight: document.documentElement.clientHeight - 30,

      showTag: false,
      updateFlag: false,
      tagList: []
    }
  },
  computed: {
    // 当前用户的权限  1 管理权限 2 编辑权限 3 只读权限
    currentUserAuth() {
      return Number(this.data.auth.currentUserAuth)
    },
    tinymce() {
      return this.$refs.createTinymce.editor
    }
  },
  created() {
    this.debouncedEdiorInput = debounce(300, this.editInputChange)

    this.maxIndex = getMaxIndex()
    this.docContent = this.data.content || ''
    this.tagList = objDeepCopy(this.data.labelList || [])
    // if (!this.docContent) {
    //   this.sysStr = sysStr.replace('为页面添加标题', this.data.title)
    //   this.docContent = this.sysStr
    // }
  },
  methods: {
    handleBack() {
      if (this.updateFlag) {
        this.$emit('update')
      }
      this.$emit('back')
    },

    getEditConfig() {
      return {
        menubar: false,
        toolbar_sticky: true,
        statusbar: false,
        paste_data_images: true, // 允许粘贴图片
        paste_enable_default_filters: false,
        placeholder: '点击开始输入正文',
        content_style: ' * {color: #262626;}',
        paste_retain_style_properties: 'border', // 粘贴内容时要保留的样式
        toolbar_mode: 'floating',
        setup: (editor) => {
          editor.on('focus', (e) => {
            if (!this.docContent) {
              this.editInputChange(this.docContent)
            }
          })

          // 处理点击附件名称操作
          let url = ''
          let name = ''
          editor.ui.registry.addContextToolbar('customToolbar', {
            items: 'previewFile ｜ downloadFile',
            predicate: (node) => {
              if (node.tagName == 'SPAN' && node.getAttribute('data-wk-file-type') == 'file') {
                url = node.getAttribute('data-wk-file-url')
                name = node.innerText
              }
              return node.tagName == 'SPAN' && node.getAttribute('data-wk-file-type') == 'file'
            }
          })

          editor.ui.registry.addButton('previewFile', {
            text: '<span style="cursor: pointer;">预览</span>',
            onAction: () => {
              this.preview({
                url,
                name
              })
            }
          })

          editor.ui.registry.addButton('downloadFile', {
            text: '<span style="cursor: pointer;">下载</span>',
            onAction: () => {
              this.downloadFile({
                path: url,
                name
              })
            }
          })

          // toolbar添加附件上传按钮
          editor.ui.registry.addButton('uploadFile', {
            text: `<i class="wk wk-file" style="font-size: 22px"></i>`,
            tooltip: '上传附件',
            onAction: () => {
              this.$refs.fileInput.click()
            }
          })
        },
        // paste_word_valid_elements: 'b,strong,i,em,h1,h2',
        paste_preprocess: function(plugin, args) {
          // 删除部分标签
          var delTag = ['b', 'strong', 'i', 'em']
          delTag.forEach(tag => {
            var reg = new RegExp(`(<${tag}>)|(</${tag}>)]`, 'g')
            args.content = args.content.replace(reg, '')
          })
          // 替换部分标签
          var replaceTag = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6']
          replaceTag.forEach(tag => {
            var reg1 = new RegExp(`<${tag}>`, 'g')
            var reg2 = new RegExp(`</${tag}>`, 'g')
            args.content = args.content.replace(reg1, '<p>')
            args.content = args.content.replace(reg2, '</p>')
          })
          // 删除所有font标签
          args.content = args.content.replace(/<\/font>/ig, '').replace(/<font[^>]+>/ig, '')
          console.log(args.content)
        },
        paste_postprocess: function(plugin, args) {
          var doms = Array.from(args.node.querySelectorAll('*'))
          // 删除字体样式
          doms.forEach(dom => {
            dom.style.color = ''
            dom.style.fontWeight = ''
            dom.style.fontFamily = ''
            dom.style.fontSize = ''
            dom.style.background = ''
            console.log(dom)
          })
        }
      }
    },

    editInputChange(content) {
      if (!content) {
        this.docContent = sysStr.replace('为页面添加标题', this.data.title)
      }
    },

    handleCommand(command) {
      if (command === 'tag') {
        this.showTag = !this.showTag
      } else if (command === 'preview') {
        this.$refs.createTinymce.editor.execCommand('mcePreview')
      }
    },

    /**
     * 更新文档标签
     */
    handleUpdateTag(tagList) {
      this.updateFlag = true
      kmDocAddOrUpdateAPI({
        documentId: this.data.documentId,
        labelIds: tagList.map(o => o.labelId).join(','),
        folderId: this.data.folderId,
        libraryId: this.data.libraryId,
        status: this.data.status
      }).then(() => {
        this.updateFlag = true
        this.tagList = tagList
        this.$message.success('标签更新成功')
      }).catch(() => {
      })
    },

    /**
     * 更新文档内容
     */
    handleSave() {
      if (![1, 2, 5].includes(this.currentUserAuth)) return
      if (this.docContent.replace('\n', '') === this.sysStr) return

      const titleDom = this.tinymce.dom.get('wk-knowledge-content-wukong')
      let title = this.data.title
      if (titleDom) {
        title = titleDom.innerText || titleDom.textContent
      }

      const params = {
        documentId: this.data.documentId,
        title: title || this.data.title,
        content: this.docContent,
        folderId: this.data.folderId,
        libraryId: this.data.libraryId,
        status: this.data.status
      }
      kmDocAddOrUpdateAPI(params).then(() => {
        this.updateFlag = true
        this.handleBack()
      }).catch(() => {
      })
    },

    /**
     * @description: 上传附件
     * @param {*} event
     * @return {*}
     */
    uploadFile(event) {
      var files = event.target.files
      for (let index = 0; index < files.length; index++) {
        const file = files[index]
        this.$wkUploadFile.upload({
          file: file,
          params: {
            batchId: ''
          }
        }).then(res => {
          const data = res.res.data || {}
          const { name, url } = data

          this.tinymce.insertContent(`
          <span style="color: #3578d3; cursor: pointer; outline: unset; font-size: 14px;" contenteditable="false" data-wk-file-type="file" data-wk-file-url="${url}"">
            <img src="${getFileTypeIcon(file)}" style="vertical-align: text-top;" width="15px" height="18px">
            ${name}
          </span>`)
        }).catch(() => {})
      }

      event.target.value = ''
    },

    /**
     * @description: 预览附件
     * @param {*} data
     * @return {*}
     */
    preview(data) {
      this.$wkPreviewFile.preview({
        index: 0,
        data: [data]
      })
    },

    /**
     * @description: 下载附件
     * @param {*} file
     * @return {*}
     */
    downloadFile(file) {
      downloadFile(file)
    }
  }
}
</script>

<style scoped lang="scss">
  .common-text-btn {
    color: #666;
  }

  ::v-deep .el-dialog__body {
    padding: 0;
  }

  .edit-document {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    min-width: 1250px;
    height: 100%;
    padding: 20px 50px 10px;
    background-color: white;

    .left {
      display: flex;
      align-items: center;
      height: 40px;

      .wk-back-large {
        padding: 5px;
        font-size: 14px;
        color: #666;
        cursor: pointer;
      }
    }

    .right {
      display: flex;
      align-items: center;
      height: 40px;

      .el-dropdown {
        margin: 0 8px;
      }
    }
  }

  ::v-deep .tox-editor-container {
    .tox-editor-header {
      width: 90%;
      background-color: unset;

      .tox-toolbar__primary {
        background: unset;
      }

      .tox-toolbar-overlord {
        background: unset;
      }
    }

    .tox-toolbar__group {
      position: relative;
      border: 0 none !important;

      &::after {
        position: absolute;
        top: 50%;
        right: 0;
        display: block;
        width: 1px;
        height: 50%;
        content: "";
        background-color: #e1e1e1;
        transform: translateY(-50%);
      }

      &:last-of-type::after {
        display: none;
      }
    }

    .tox-sidebar-wrap {
      width: 1000px;
      margin: 0 auto;
    }
  }
</style>
