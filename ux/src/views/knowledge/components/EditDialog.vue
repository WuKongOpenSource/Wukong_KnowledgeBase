<template>
  <div class="create-container">
    <el-dialog
      class="kl-edit-dialog"
      :show-close="false"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="800px">
      <flexbox
        v-loading="loading"
        align="flex-start"
        class="create-body">
        <div class="create-body_nav">
          <div class="title">知识库设置</div>
          <div
            :class="{active: activeNavIndex === 0}"
            class="create-body_nav__item"
            @click="activeNavIndex = 0">
            <i class="wk wk-project icon" />
            <span>知识库信息</span>
          </div>
          <div
            v-if="form.isOpen === 0"
            :class="{active: activeNavIndex === 1}"
            class="create-body_nav__item"
            @click="activeNavIndex = 1">
            <i class="wk wk-employees icon" />
            <span>成员管理</span>
          </div>
          <!-- <div
            :class="{active: activeNavIndex === 2}"
            class="create-body_nav__item"
            @click="activeNavIndex = 2">
            <i class="wk wk-workbench icon" />
            <span>悟空AI</span>
          </div> -->
        </div>
        <flexbox-item class="create-body_content">
          <!-- base info -->
          <el-form
            v-if="activeNavIndex === 0"
            ref="form"
            :model="form"
            label-position="top"
            class="form">
            <el-form-item
              class="wk-form-item"
              label="知识库封面">
              <flexbox class="img-body">
                <div
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
          </el-form>

          <!-- group user -->
          <div v-else-if="activeNavIndex === 1" class="group">
            <flexbox class="group-title">
              <flexbox-item>
                知识库成员
              </flexbox-item>
              <el-button
                type="primary"
                @click="selectEmployeeShow = true">
                添加成员
              </el-button>
            </flexbox>
            <div class="group-body">
              <!-- 员工 -->
              <flexbox
                v-for="(user, index) in userList"
                :key="`user-${index}`"
                align="flex-start"
                justify="center"
                class="group-user">
                <div class="group-user-info">
                  <xr-avatar
                    :name="$getUserName(user)"
                    :size="32"
                    :src="user.img"
                    class="user-img" />
                  <div class="user-info">
                    <div class="name">
                      {{ $getUserName(user) }}
                    </div>
                    <div class="dept-name">
                      {{ user.deptName || '暂无部门信息' }}
                    </div>
                  </div>
                </div>
                <el-dropdown
                  ref="userDropdown"
                  trigger="click"
                  @command="handleCommand($event, index)">
                  <el-button
                    :disabled="user.role === 1"
                    :class="{disabled: user.role === 1}"
                    type="text"
                    class="dropdown-btn">
                    {{ roleObj[user.role] }}
                    <i class="el-icon-arrow-down el-icon--right " />
                  </el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item
                      v-for="(item, sIndex) in memberAuthList"
                      :key="sIndex"
                      :command="item.value">
                      <div style="display: flex; flex-direction: column;">
                        <span>{{ item.label }}</span>
                        <span style="line-height: 25px; color: #7a869a;">{{ item.desc }}</span>
                      </div>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </flexbox>

              <!-- 部门 -->

              <el-tree
                :data="showDeptTreeList"
                :props="{
                  label: 'name',
                  children: 'childrenList'
                }"
                node-key="deptId"
                class="group-dept">
                <div
                  slot-scope="{ data }"
                  class="group-dept-tree">
                  <xr-avatar
                    :name="data.name"
                    :size="32"
                    class="user-img" />
                  {{ data.name }}

                  <div
                    style="margin-left: auto;"
                    @click.stop>
                    <el-dropdown
                      :ref="`deptDropdown${data.deptId}`"
                      trigger="click"
                      @command="handleDeptTreeCommand($event, data.deptId)">
                      <el-button
                        type="text"
                        class="dropdown-btn">
                        {{ roleObj[data.role] }}
                        <i class="el-icon-arrow-down el-icon--right " />
                      </el-button>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item
                          v-for="(item, index) in memberAuthList"
                          :key="index"
                          :command="item.value">
                          <div style="display: flex; flex-direction: column;">
                            <span>{{ item.label }}</span>
                            <span style="line-height: 25px; color: #7a869a;">{{ item.desc }}</span>
                          </div>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </div>
                </div>
              </el-tree>

            </div>
          </div>
          <!-- ai设置 -->
          <!-- <div v-else-if="activeNavIndex === 2" class="ai-set">
            <div class="ai-set__title">
              悟空Al
            </div>
            <div class="ai-set__doc">
              您可以开启悟空A服务。开启后可以通过A调用知识库内容来实现：其千知识库内容的文章编写、基干知识库内容的在线客服
              以及基于知识库的内部学习系统等。
            </div>
            <div class="ai-set__form">
              <el-switch
                v-model="form.aiServiceSwitch"
                active-color="#13ce66"
                inactive-color="#ff4949"
                inactive-text="是否开启AI服务"
                active-value="2"
                inactive-value="1" />
            </div>
          </div> -->

          <div class="dialog-footer">
            <el-button type="primary" @click="handleConfirm">保存</el-button>
            <el-button @click="handleClose">取消</el-button>
          </div>
        </flexbox-item>
      </flexbox>
    </el-dialog>

    <input
      ref="fileHook"
      accept="image/*"
      type="file"
      class="file-input"
      @change="handleFileUpload">

    <!-- 选择员工 -->
    <wk-dep-user-dialog
      :visible.sync="selectEmployeeShow"
      :sub-dept-contain.sync="isNeedChild"
      :user-value="selectUserIds"
      :dep-value="selectDeptIds"
      :props="{
        showDisableUser: false,
        showDept: true,
        showSubDeptContain: true
      }"
      @change="userSelectChange" />
  </div>
</template>

<script>
import { crmFileSaveAPI, crmFileDeleteAPI } from '@/api/common'
import { kmLibraryQueryMemberAPI, kmLibraryAddOrUpdateAPI, kmLibraryUpdateMemberAPI } from '@/api/knowledge/index'

import WkDepUserDialog from '@/components/NewCom/WkUserDialogSelect/Dialog'

import { mapGetters } from 'vuex'

export default {
  name: 'EditDialog',
  components: {
    WkDepUserDialog
  },
  props: {
    data: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      dialogVisible: true,
      activeNavIndex: 0,

      fieldId: null,
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
      openOptions: [
        { value: 0, label: '私有：只有加入的成员才能看见此项目' },
        { value: 1, label: '公开：企业所有成员都可以看见此项目' }
      ],
      form: {
        isOpen: 0
      },

      memberAuthList: [{
        label: '管理员',
        desc: '当前知识库所有权限',
        value: 2
      }, {
        label: '成员',
        desc: '可上传文件',
        value: 3
      }, {
        label: '移出',
        desc: '',
        value: -1
      }],

      ownerUser: null,
      selectEmployeeShow: false, // 选择员工
      userList: [],
      deptList: [],
      isNeedChild: 0,
      roleObj: {
        1: '拥有者',
        2: '管理员',
        3: '成员'
      },

      loading: false
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'userDeptObj']),
    // 计算属性有缓存稳定，直接写到template 会无故触发userValue的watch方法
    selectUserIds() {
      return (this.userList || []).map(item => item.userId)
    },
    selectDeptIds() {
      return (this.deptList || []).map(item => item.deptId)
    },

    // 处理展示的部门树
    showDeptTreeList() {
      const listToTree = (list) => {
        const map = {}
        const roots = []
        for (let i = 0; i < list.length; i += 1) {
          map[list[i].deptId] = i // 初始化指针
          list[i].childrenList = [] // 初始化子节点
        }
        for (let i = 0; i < list.length; i += 1) {
          const node = list[i]
          if (node.parentId !== '0' && map[node.parentId] != undefined) {
            // 如果有父节点且父节点存在
            list[map[node.parentId]].childrenList.push(node)
          } else {
            roots.push(node)
          }
        }
        return roots
      }

      return listToTree(this.deptList)
    }
  },
  created() {
    this.form = {
      libraryId: this.data.libraryId,
      name: this.data.name,
      description: this.data.description,
      isOpen: this.data.isOpen,
      coverUrl: this.data.coverUrl,
      aiServiceSwitch: this.data.aiServiceSwitch
    }
    this.getMember()
  },
  methods: {
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

    handleFileUpload(event) {
      const file = event.target.files[0]
      this.loading = true
      crmFileSaveAPI({
        file: file
      }).then(res => {
        const data = res.data || {}
        this.loading = false
        if (this.fieldId) {
          // 如果已经上传过图片则删除上一次上传的图片节省空间
          this.deleteFile()
          this.fileId = data.fileId
        }
        this.form.coverUrl = data.url
      }).catch(() => {
        this.loading = false
      })
    },
    deleteFile() {
      crmFileDeleteAPI({
        id: this.fileId
      }).then(() => {}).catch(() => {})
    },

    /**
     * 查询知识库团队成员
     */
    getMember() {
      this.loading = true
      kmLibraryQueryMemberAPI({
        libraryId: this.data.libraryId
      }).then(res => {
        this.loading = false
        const arr = res.data || []
        // 把拥有者放到第一个
        const findIndex = arr.findIndex(o => o.role == 1)
        this.ownerUser = arr.splice(findIndex, 1)[0]
        arr.unshift(this.ownerUser)
        this.userList = arr.filter(o => !o.type || o.userId)
        this.deptList = arr.filter(o => o.type || o.deptId).map(item => {
          return {
            ...item,
            parentId: item.parentDeptId,
            name: item.deptName
          }
        })
      }).catch(() => {
        this.loading = false
      })
    },

    handleCommand(command, index) {
      if (!command) { // 选择仅手机端查看
        this.$refs.userDropdown[index].show()
        return
      }

      if (command !== -1) {
        this.userList[index].role = command
        this.$set(this.userList, index, this.userList[index])
      } else {
        this.userList.splice(index, 1)
      }
    },

    /**
     * @description: 处理部门树下拉
     * @param {*} command
     * @param {*} deptId
     * @return {*}
     */
    handleDeptTreeCommand(command, deptId) {
      if (!command) { // 选择仅手机端查看
        this.$refs[`deptDropdown${deptId}`].show()
        return
      }
      const item = this.deptList.filter(o => o.deptId == deptId)[0]
      if (command !== -1) {
        item.role = command
      } else {
        this.deptList = this.deptList.filter(o => o.deptId != deptId)
      }
    },

    handleClose() {
      this.dialogVisible = false
      this.$emit('close')
    },

    /**
     * 知识库成员选择
     */
    userSelectChange(_, deptIds, members, deptData) {
      console.log(this.isNeedChild, '回家开始的')
      console.log(deptData, '黄金时代看')
      // 禁止删除知识库拥有者
      this.userList = [this.ownerUser]
      this.deptList = []
      members.forEach(user => {
        if (user.userId == this.ownerUser.userId) return
        // 给选择刚选择员工赋予默认角色值
        if (!user.hasOwnProperty('role')) {
          this.userList.push({
            ...user,
            role: 3
          })
        } else {
          this.userList.push(user)
        }
      })

      let deptList = []
      if (this.isNeedChild) { // 勾选包含下级部门
        this.handleIncludeSubsDept(deptData, deptList)
      } else {
        deptList = deptData
      }

      deptList.forEach(dept => {
        this.deptList.push({
          ...dept,
          role: 3
        })
      })
    },

    /**
     * @description: 处理包含下级部门展开
     * @param {*} deptList
     * @param {*} list
     * @return {*}
     */
    handleIncludeSubsDept(deptList, list) {
      const deptIds = list.map(o => o.deptId)
      deptList.forEach(item => {
        if (!deptIds.includes(item.deptId)) {
          list.push(item)
        }

        if (item.children && item.children.length) {
          this.handleIncludeSubsDept(item.children, list)
        }
      })
    },

    handleConfirm() {
      if (!this.form.name) {
        this.$message.error('知识库名称不能为空')
        return
      }

      const params = {
        ...this.form,
        isSystemCover: this.coverImgList.includes(this.form.coverUrl) ? 1 : 0,
        userList: this.form.isOpen === 0 ? this.userList.map(o => o.userId) : null
      }
      const arr = this.userList.filter(user => user.role != 1) || []
      const userList = arr.map(item => {
        return {
          userId: item.userId,
          role: item.role,
          type: 0
        }
      })

      const deptList = this.deptList.map(item => {
        return {
          deptId: item.deptId,
          role: item.role,
          type: 1
        }
      })
      const memberParams = {
        userList: userList.concat(deptList),
        libraryId: this.data.libraryId
      }
      if (this.form.isOpen !== 0) {
        memberParams.userList = []
      }
      // console.log('save base info: ', params, memberParams)

      const reqArr = [
        kmLibraryAddOrUpdateAPI(params),
        kmLibraryUpdateMemberAPI(memberParams)
      ]
      Promise.all(reqArr).then(() => {
        this.$emit('update')
        this.handleClose()
        this.$message.success('修改成功')
      }).catch()
    }
  }
}
</script>

<style scoped lang="scss">
</style>
<style scoped lang="scss">
@import "@/styles/wk-form.scss";

::v-deep .kl-edit-dialog {
  .el-dialog__body {
    padding: 0;
  }

  .el-dialog__header,
  .el-dialog__footer {
    display: none;
  }
}

.dialog-footer {
  text-align: right;
}

.create-body {
  width: 100%;
  height: 500px;

  &_nav {
    width: 180px;
    height: 100%;
    padding: 24px 8px 0;
    background-color: $--background-color-base;

    .title {
      padding: 0 8px 16px;
      font-size: 20px;
    }

    &__item {
      width: 100%;
      height: 40px;
      padding-left: 8px;
      line-height: 40px;
      cursor: pointer;
      border-radius: $--border-radius-base;

      &.active {
        color: $--color-primary;
        background-color: $--background-hover-color-base;
      }
    }
  }

  &_content {
    display: flex;
    flex-direction: column;
    height: 100%;
    padding: 24px;
    margin: 0;

    .form {
      flex: 1;
      overflow: hidden;

      ::v-deep .wk-form-item {
        .el-form-item__label {
          color: $--color-text-primary;
        }
      }

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
        margin-left: 20px !important;

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

      .el-select {
        width: 280px;
      }
    }

    .group {
      height: 100%;
      overflow-y: auto;

      .group-title {
        font-size: 20px;
        color: $--color-text-primary;
      }

      .group-body {
        .group-user {
          margin: 10px 0;

          .group-user-info {
            display: flex;
            flex: 1;
            align-items: center;
            justify-content: flex-start;
            padding-left: 18px;

            .user-img {
              margin-right: 8px;
            }
          }

          .user-info {
            .dept-name {
              font-size: 12px;
              color: $--color-text-regular;
            }
          }

          .dropdown-btn {
            padding-right: 4px;
            padding-left: 4px;
          }
        }

        .group-dept {
          ::v-deep .el-tree-node__content {
            height: 40px;

            &:hover {
              background-color: unset;
            }

            .el-icon-caret-right {
              padding: unset;
              padding-right: 6px;
            }
          }

          .group-dept-tree {
            display: flex;
            flex: 1;
            align-items: center;

            .el-avatar {
              margin-right: 8px;
            }

            .dropdown-btn {
              padding: 8px 4px;
            }
          }
        }
      }
    }

    .ai-set {
      flex: 1;
      overflow: hidden;

      &__title {
        font-size: 1.5em;
        line-height: 50px;
      }

      &__doc {
        line-height: 25px;
      }

      &__form {
        line-height: 25px;
      }
    }
  }
}

.file-input {
  display: none;
}
</style>
