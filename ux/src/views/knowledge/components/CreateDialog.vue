<template>
  <div class="create-container">
    <el-dialog
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      title="选择知识库模板"
      width="900px">
      <flexbox
        v-loading="loading && dialogVisible"
        align="flex-start"
        class="tpl-container">
        <flexbox-item
          style="padding-right: 16px;"
          class="tpl-container-sec">
          <flexbox
            align="center"
            justify="flex-start"
            class="tpl-box empty"
            @click.native="handleNextStep()">
            <div class="tpl-box-left">
              <i class="el-icon-plus" />
            </div>
            <div class="tpl-box-right">
              <div class="tpl-title">空白知识库</div>
              <div class="tpl-description">邀请团队成员一起创作和交流知识</div>
            </div>
          </flexbox>
          <flexbox
            v-for="(tpl, index) in tplList"
            :key="index"
            :class="{active: index === activeTplIndex}"
            align="center"
            justify="flex-start"
            class="tpl-box"
            @click.native="activeTplIndex = index">
            <div class="tpl-box-left">
              <img v-src="tpl.coverUrl" alt="" class="tpl-pic">
            </div>
            <flexbox-item class="tpl-box-right">
              <div class="tpl-title">{{ tpl.name }}</div>
              <div class="tpl-description">{{ tpl.description }}</div>
            </flexbox-item>
          </flexbox>
        </flexbox-item>
        <flexbox-item
          v-if="tplList.length > 0"
          style="margin: 0;"
          class="tpl-container-sec is-right">
          <flexbox
            align="center"
            justify="flex-start"
            class="tpl-box">
            <div class="tpl-box-left">
              <img v-src="tplList[activeTplIndex].coverUrl" alt="" class="tpl-pic">
            </div>
            <flexbox-item class="tpl-box-right">
              <div class="tpl-title">{{ tplList[activeTplIndex].name }}</div>
              <div class="tpl-description">{{ tplList[activeTplIndex].description }}</div>
            </flexbox-item>
          </flexbox>
          <div class="tpl-doc">
            <flexbox
              v-for="(doc, index) in tplList[activeTplIndex].documentList"
              :key="index"
              align="center"
              justify="flex-start"
              class="tpl-doc-box">
              <i class="wk wk-doc" />
              <span>{{ doc.title }}</span>
            </flexbox>
          </div>
        </flexbox-item>
      </flexbox>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleNextStep(tplList[activeTplIndex])">确定</el-button>
        <el-button @click="handleClose">取消</el-button>
      </div>
    </el-dialog>

    <xr-create
      v-if="showNextStep"
      v-loading="loading"
      title="新建知识库"
      @close="handleClose"
      @save="handleSave">
      <create-sections title="基本信息">
        <el-form
          ref="form"
          :model="form"
          label-position="top"
          class="form-container">
          <el-form-item
            class="wk-form-item"
            label="知识库名称"
            prop="name"
            :rules="[{ required: true, message: '知识库名称不能为空', trigger: 'change' }]">
            <el-input
              v-model="form.name"
              :maxlength="50"
              placeholder="请输入内容" />
          </el-form-item>

          <div class="form-item cover">
            <el-form-item
              class="wk-form-item"
              label="知识库封面">
              <flexbox class="img-body">
                <div
                  v-loading="imgLoading"
                  class="img-left">
                  <img
                    v-src="form.coverUrl"
                    alt=""
                    class="img">
                </div>
                <flexbox-item
                  align="flex-start"
                  justify="center"
                  direction="column"
                  class="img-right">
                  <div class="img-list">
                    <img
                      v-for="(img, index) in coverImgList"
                      :key="index"
                      :src="img"
                      alt="封面图"
                      class="img"
                      @click="handleChooseImg(index)">
                  </div>
                  <el-button @click="handleToChooseFile">
                    本地上传
                  </el-button>
                </flexbox-item>
              </flexbox>
            </el-form-item>
          </div>

          <div class="form-item">
            <el-form-item
              class="wk-form-item"
              label="知识库描述">
              <el-input
                v-model="form.description"
                :rows="4"
                :maxlength="300"
                show-word-limit
                type="textarea"
                resize="none"
                placeholder="请输入内容" />
            </el-form-item>
          </div>

          <div class="form-item">
            <el-form-item
              class="wk-form-item"
              label="可见范围">
              <el-select
                v-model="form.isOpen">
                <el-option
                  v-for="item in openOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
          </div>

          <div
            v-if="form.isOpen === 0"
            class="form-item member">
            <el-form-item
              class="wk-form-item"
              label="知识库成员">
              <div>
                <xr-avatar
                  v-for="(user, userIndex) in selectUserList"
                  :key="userIndex"
                  :name="$getUserName(user)"
                  :size="25"
                  :src="user.img"
                  class="user-img" />
                <i
                  class="el-icon-plus add-user-btn"
                  @click="selectEmployeeShow = true" />
              </div>
            </el-form-item>
          </div>
        </el-form>
      </create-sections>
    </xr-create>

    <input
      ref="fileHook"
      accept="image/*"
      type="file"
      class="file-input"
      @change="handleFileUpload">

    <!-- 选择员工 -->
    <wk-dep-user-dialog
      v-if="selectEmployeeShow"
      :visible.sync="selectEmployeeShow"
      :user-value="selectUserIds"
      :props="{
        showDisableUser: false,
        showDept: false,
      }"
      @change="userSelectChange" />
  </div>
</template>

<script>
import { crmFileSaveAPI, crmFileDeleteAPI } from '@/api/common'
import { kmLibraryAddOrUpdateAPI, kmLibraryTemplateAPI } from '@/api/knowledge/index'

import XrCreate from '@/components/XrCreate'
import CreateSections from '@/components/CreateSections'
import WkDepUserDialog from '@/components/NewCom/WkUserDialogSelect/Dialog'

import { mapGetters } from 'vuex'

export default {
  name: 'CreateDialog',
  components: {
    XrCreate,
    WkDepUserDialog,
    CreateSections
  },
  data() {
    return {
      dialogVisible: true,
      tplList: [],
      showNextStep: false,
      form: {
        name: null,
        description: null,
        isOpen: 0,
        coverUrl: null,
        userList: null,
        templateId: null
      },

      imgLoading: false,

      coverImgList: [
        'https://file.72crm.com/static/pc/images/kw/1.png',
        'https://file.72crm.com/static/pc/images/kw/2.png',
        'https://file.72crm.com/static/pc/images/kw/3.png',
        'https://file.72crm.com/static/pc/images/kw/4.png',
        'https://file.72crm.com/static/pc/images/kw/5.png',
        'https://file.72crm.com/static/pc/images/kw/6.png',
        'https://file.72crm.com/static/pc/images/kw/7.png',
        'https://file.72crm.com/static/pc/images/kw/8.png'
      ],
      activeTplIndex: 0,
      customImg: null,
      fieldId: null,

      openOptions: [
        { value: 0, label: '私有：只有加入的成员才能看见此项目' },
        { value: 1, label: '公开：企业所有成员都可以看见此项目' }
      ],

      selectEmployeeShow: false, // 选择员工
      selectUserList: [],

      loading: false
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    // 计算属性有缓存稳定，直接写到template 会无故触发userValue的watch方法
    selectUserIds() {
      return (this.selectUserList || []).map(item => item.userId)
    }
  },
  created() {
    this.getTplList()
    this.handleChooseImg(0)
    this.selectUserList.push(this.userInfo)
  },
  methods: {
    getTplList() {
      this.loading = true
      kmLibraryTemplateAPI().then(res => {
        this.loading = false
        this.tplList = res.data
      }).catch(() => {
        this.loading = false
      })
    },
    handleNextStep(tpl = null) {
      this.dialogVisible = false
      if (tpl) {
        this.form.templateId = tpl.libraryId
        this.form.name = tpl.name
        this.form.description = tpl.description
        this.form.coverUrl = tpl.coverUrl
      }
      this.showNextStep = true
    },

    /**
     * 封面图选择
     */
    handleChooseImg(index) {
      this.form.coverUrl = this.coverImgList[index]
    },

    handleToChooseFile() {
      this.$refs.fileHook.value = null
      this.$refs.fileHook.click()
    },

    /**
     * 封面图上传
     */
    handleFileUpload(event) {
      const file = event.target.files[0]
      this.imgLoading = true
      crmFileSaveAPI({
        file: file
      }).then(res => {
        const data = res.data || {}
        this.imgLoading = false
        if (this.fieldId) {
          this.deleteFile()
          this.fileId = data.fileId
        }
        this.form.coverUrl = data.url
      }).catch(() => {
        this.imgLoading = false
      })
    },

    deleteFile() {
      crmFileDeleteAPI({
        id: this.fileId
      }).then(() => {}).catch(() => {})
    },

    /**
     * 项目成员
     */
    userSelectChange(_, deptIds, members) {
      this.selectUserList = members
    },

    handleClose() {
      this.dialogVisible = false
      this.showNextStep = false
      this.$emit('close')
    },
    handleSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.isOpen === 0) {
            this.form.userList = this.selectUserList.map(user => user.userId)
          } else {
            this.form.userList = null
          }
          kmLibraryAddOrUpdateAPI({
            ...this.form,
            isSystemCover: this.coverImgList.includes(this.form.coverUrl) ? 1 : 0
          }).then(() => {
            this.$message.success('新建成功')
            this.$emit('update')
            this.handleClose()
          }).catch(() => {})
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
@import "@/styles/wk-form.scss";

::v-deep .el-dialog__body {
  padding: 0 32px 16px;
}

.create-container {
  .tpl-container {
    .tpl-container-sec {
      height: 450px;
      overflow-y: auto;

      &.is-right {
        padding: 8px 0;
        background-color: $--background-color-base;
      }
    }

    .tpl-box {
      padding: 12px 16px;
      margin-bottom: 4px;
      cursor: pointer;
      border-radius: $--border-radius-base;

      .tpl-box-left {
        width: 50px;
        height: 50px;
        overflow: hidden;
        border-radius: $--border-radius-base;

        .tpl-pic {
          width: 100%;
          height: 100%;
        }

        .el-icon-plus {
          color: $--color-n800;
        }
      }

      .tpl-box-right {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: center;
        margin-left: 16px !important;

        .tpl-title {
          font-size: 16px;
          color: $--color-text-primary;
        }

        .tpl-description {
          margin-top: 4px;
          color: $--color-text-secondary;
        }
      }

      &.empty {
        .tpl-box-left {
          font-size: 24px;
          font-weight: bold;
          line-height: 50px;
          color: $--color-primary;
          text-align: center;
          border: 2px dashed $--color-n90;
        }
      }

      &.active,
      &:hover {
        background-color: $--background-color-base;
      }
    }

    .tpl-doc {
      padding-left: 32px;
      margin-top: 16px;

      .tpl-doc-box {
        width: 100%;
        margin-bottom: 24px;
        font-size: 18px;
        color: $--color-text-regular;

        .wk-doc {
          margin-right: 24px;
          font-size: 20px;
          color: $--color-text-regular;
        }
      }
    }
  }
}

.form-container {
  height: 100%;
  padding: 0 12px;
  overflow: auto;

  .el-input,
  .el-select {
    width: 260px;
  }

  .form-item {
    margin-bottom: 8px;

    &-label {
      padding: 12px 0;
    }

    &.member {
      img {
        width: 25px;
        cursor: pointer;
      }

      .item-name {
        margin-right: 7px;
      }

      .user-img {
        margin-right: 7px;
        vertical-align: top;
      }

      .add-user-btn {
        padding: 6px 5.5px 5px;
        font-size: 12px;
        vertical-align: top;
        cursor: pointer;
        border: 1px dotted #666;
        border-radius: 50%;
      }

      .add-user-btn:hover {
        border-color: $--color-primary;
      }
    }

    &.cover {
      .img-body {
        .img-left {
          width: 125px;
          height: 76px;
          overflow: hidden;
          border-radius: $--border-radius-base;

          .img {
            width: 100%;
            height: 100%;
          }
        }

        .img-right {
          margin-left: 20px;

          .img-list {
            margin-bottom: 5px;

            .img {
              width: 32px;
              height: 26px;
              margin: 5px 3px;
              vertical-align: middle;
              cursor: pointer;
              border-radius: $--border-radius-base;
            }
          }
        }
      }
    }
  }

  .handle-bar {
    .handle-button {
      float: right;
      margin-top: 5px;
      margin-right: 20px;
    }
  }
}

.file-input {
  display: none;
}
</style>
