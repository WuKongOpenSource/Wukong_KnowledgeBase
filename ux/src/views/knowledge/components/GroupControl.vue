<template>
  <transition name="slide-fade">
    <el-card
      v-if="showContent"
      id="slide"
      ref="slide"
      v-loading="loading"
      v-clickoutside="handleClose"
      :style="{ 'z-index': zIndex }"
      :body-style="{padding: '15px'}"
      class="slide-wrapper">
      <div class="slider-body">
        <div class="section">
          <flexbox class="section-title">
            <flexbox-item class="name">文档权限</flexbox-item><i
              class="el-icon-close close"
              @click="handleClose" />
          </flexbox>
          <div class="section-body">
            <div class="open-option">
              <div class="open">
                <div class="mini-title">
                  <i class="wk wk-unlock" />
                  <span>公开</span>
                </div>
                <div class="desc">
                  当前知识库成员及可访问当前知识库的人
                </div>
                <el-radio-group
                  v-model="authData.openAuth"
                  @change="handleChangeOpen(1)">
                  <el-radio
                    v-for="(item, index) in openAuthList"
                    :key="index"
                    :label="item.value"
                    style="display: flex;">
                    <div class="el-radio-container">
                      <span>{{ item.label }}</span>
                      <span>{{ item.desc }}</span>
                    </div>
                  </el-radio>

                  <el-checkbox
                    v-model="authData.isMobile"
                    class="mobile-checkbox"
                    label="手机端仅查看"
                    :true-label="1"
                    :false-label="0"
                    @change="updateAuth" />
                </el-radio-group>
              </div>
              <flexbox
                class="self"
                @click.native="handleChangeOpen(0)">
                <i class="wk wk-lock" />
                <flexbox-item>仅自己和被添加的成员可见（隐私）</flexbox-item>
                <i v-if="authData.isOpen === 0" class="el-icon-check" />
              </flexbox>
            </div>
          </div>
        </div>

        <div class="section is-users">
          <flexbox align="center" class="section-title">
            <flexbox-item class="name">协作者</flexbox-item>
            <!-- <span slot="tabs" class="members-dep-tips">（仅可邀请本知识库成员）</span> -->
            <el-button
              icon="el-icon-plus"
              type="text"
              @click="addUserClick">
              添加成员
            </el-button>
          </flexbox>
          <div v-if="data" class="section-body">
            <flexbox
              v-for="(user, userIndex) in authUser"
              :key="userIndex"
              align="center"
              class="user-item">
              <xr-avatar
                :name="$getUserName(user)"
                :size="32"
                :src="user.img"
                class="user-img" />
              <flexbox-item>
                {{ $getUserName(user) }}
              </flexbox-item>
              <el-dropdown
                trigger="click"
                @command="handleChangeUserAuth(userIndex, $event)">
                <el-button
                  type="text"
                  :disabled="user.userId == data.createUserId"
                  :class="{disabled: user.userId == data.createUserId}"
                  class="dropdown-btn">
                  {{ user.userId == data.createUserId ? '拥有者' : roleObj[user.auth] }}
                  <i class="el-icon-arrow-down el-icon--right" />
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
                  <el-dropdown-item>
                    <el-checkbox
                      v-model="user.isMobile"
                      label="手机端仅查看"
                      :true-label="1"
                      :false-label="0" />
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </flexbox>

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
                  :name="data.deptName"
                  :size="32"
                  class="user-img" />
                {{ data.deptName || data.name }}

                <div
                  style="margin-left: auto;"
                  @click.stop>
                  <el-dropdown
                    trigger="click"
                    @command="handleDeptTreeCommand($event, data.deptId)">
                    <el-button
                      type="text"
                      class="dropdown-btn">
                      {{ roleObj[data.auth] }}
                      <i class="el-icon-arrow-down el-icon--right" />
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
                      <el-dropdown-item>
                        <el-checkbox
                          v-model="data.isMobile"
                          label="手机端仅查看"
                          :true-label="1"
                          :false-label="0" />
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </div>
              </div>
            </el-tree>
          </div>
        </div>
      </div>

      <wk-dep-user-dialog
        v-if="selectEmployeeShow"
        :user-value="(authUser || []).map(item => item.userId)"
        :dep-value="(authDept || []).map(item => item.deptId)"
        :props="{
          showUser: !authUserList,
          customDeptList: authDeptList,
          showDisableUser: !!authUserList,
          disableUserList: authUserList,
          disableUserLabel: '员工',
          showDept: true,
          showSubDeptContain: true
        }"
        :sub-dept-contain.sync="isNeedChild"
        :visible.sync="selectEmployeeShow"
        @click.native.stop=""
        @change="userSelectChange"
      />
    </el-card>
  </transition>
</template>

<script>
import { kmLibraryQueryMemberAPI, kmAuthUpdateAPI } from '@/api/knowledge/index'

import WkDepUserDialog from '@/components/NewCom/WkUserDialogSelect/Dialog'

import { mapGetters } from 'vuex'
import { getMaxIndex, objDeepCopy } from '@/utils/index'

export default {
  name: 'GroupControl',
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
      zIndex: getMaxIndex(),
      drawerVisible: true,
      authList: [
        { label: '所有权限', desc: '可编辑，可以进行管理', value: 1 },
        { label: '编辑权限', desc: '可以进行编辑', value: 2 },
        { label: '只读权限', desc: '仅阅读，不可编辑', value: 3 },
        { label: '移出', desc: '', value: 4 }
      ],

      memberAuthList: [{
        label: '管理员',
        desc: '当前知识库所有权限',
        value: 1
      }, {
        label: '可编辑',
        desc: '可上传/下载/编辑/查看/删除文件',
        value: 2
      }, {
        label: '可上传下载',
        desc: '可上传/下载/编辑/查看，不可删除文件',
        value: 5
      }, {
        label: '可下载',
        desc: '可下载/查看，不可上传文件',
        value: 4
      }, {
        label: '仅预览',
        desc: '仅可在线预览，不可下载文件',
        value: 3
      }, {
        label: '移出',
        desc: '',
        value: -1
      }],

      openAuthList: [{
        label: '管理员',
        desc: '当前知识库所有权限',
        value: 4
      }, {
        label: '可编辑',
        desc: '可上传/下载/编辑/查看/删除文件',
        value: 2
      }, {
        label: '可上传下载',
        desc: '可上传/下载/编辑/查看，不可删除文件',
        value: 5
      }, {
        label: '可下载',
        desc: '可下载/查看，不可上传文件',
        value: 6
      }, {
        label: '仅预览',
        desc: '仅可在线预览，不可下载文件',
        value: 3
      }],

      roleObj: {
        1: '管理员',
        2: '可编辑',
        3: '仅预览',
        4: '可下载',
        5: '可上传下载'
      },

      popoverList: [],

      isNeedChild: 0,
      authUser: [],
      authDept: [],
      authData: {},
      selectEmployeeShow: false, // 选择员工
      authUserRequest: kmLibraryQueryMemberAPI,
      authUserParams: {},
      authUserList: null, // null 全部人员 非空 请求数据
      authDeptList: null, // null 全部部门 非空 请求数据
      loading: false,
      updateFlag: false,

      ownerUser: null,

      showContent: false
    }
  },
  computed: {
    ...mapGetters(['userInfo']),

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
          if (node.parentDeptId !== '0' && map[node.parentDeptId] !== undefined) {
            // 如果有父节点且父节点存在
            list[map[node.parentDeptId]].childrenList.push(node)
          } else {
            roots.push(node)
          }
        }
        return roots
      }

      return listToTree(this.authDept)
    }
  },
  watch: {
    data: {
      handler() {
        this.authData = this.data.auth
        // this.authUser = this.data.auth.authUserList || []

        const arr = objDeepCopy(this.data.auth.authUserList || [])
        // 把拥有者放到第一个
        const findIndex = arr.findIndex(o => o.userId == this.data.createUserId)
        if (findIndex !== -1) {
          this.ownerUser = arr.splice(findIndex, 1)[0]
        }
        if (this.ownerUser) {
          arr.unshift(this.ownerUser)
        }

        this.authUser = arr.filter(o => o.userId)
        this.authDept = arr.filter(o => o.deptId)

        this.authUserParams = {
          libraryId: this.data.libraryId
        }
        if (this.data.libraryIsOpen == 0) {
          this.authUserRequest = kmLibraryQueryMemberAPI
        } else {
          this.authUserRequest = null
        }
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    setTimeout(() => {
      this.showContent = true
    }, 50)
  },
  methods: {
    getAuthText(user) {
      const findItem = this.authList.find(item => item.value === user.auth)
      return findItem.label
    },

    /**
     * @description: 添加员工点击
     * @param {*}
     * @return {*}
     */
    async addUserClick() {
      // 开源展示全部人，否则展示成员
      if (this.authUserRequest) {
        const res = await this.authUserRequest(this.authUserParams)
        this.authUserList = res.data.filter(o => !o.type) || []

        // 部门数据转树形结构
        const listToTree = (list) => {
          const map = {}
          const roots = []
          for (let i = 0; i < list.length; i += 1) {
            map[list[i].deptId] = i // 初始化指针
            list[i].children = [] // 初始化子节点
          }
          for (let i = 0; i < list.length; i += 1) {
            const node = list[i]
            if (node.parentDeptId !== '0' && map[node.parentDeptId] != undefined) {
            // 如果有父节点且父节点存在
              list[map[node.parentDeptId]].children.push(node)
            } else {
              roots.push(node)
            }
          }
          return roots
        }

        this.authDeptList = listToTree(res.data.map(item => {
          return {
            ...item,
            name: item.deptName
          }
        }) || [])
        this.selectEmployeeShow = true
      } else {
        this.authUserList = null
        this.selectEmployeeShow = true
      }
    },

    /**
     * 成员
     */
    userSelectChange(_, deptIds, members, deptData) {
      if (this.ownerUser) {
        this.authUser = [this.ownerUser]
      }
      this.authDept = []
      members.forEach(user => {
        if (this.ownerUser && user.userId == this.ownerUser.userId) return
        if (!user.hasOwnProperty('auth')) {
          this.authUser.push({
            ...user,
            isMobile: 1,
            auth: 3
          })
        } else {
          this.authUser.push(user)
        }
      })

      let deptList = []
      if (this.isNeedChild) { // 勾选包含下级部门
        this.handleIncludeSubsDept(deptData, deptList)
      } else {
        deptList = deptData
      }
      deptList.forEach(dept => {
        this.authDept.push({
          ...dept,
          parentDeptId: dept.parentId,
          deptName: dept.name,
          isMobile: 1,
          auth: 3
        })
      })
      this.popoverList = new Array(this.authUser.length).fill(false)
      this.updateAuth()
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

    /**
     * 修改是否公开
     * @param openStatus
     */
    handleChangeOpen(openStatus) {
      this.authData.isOpen = openStatus
      if (openStatus === 0) {
        this.authData.openAuth = 0
      }
      this.updateAuth()
    },

    /**
     * 修改成员权限
     */
    handleChangeUserAuth(userIndex, command) {
      if (command === -1) {
        this.authUser.splice(userIndex, 1)
        this.popoverList.splice(userIndex, 1)
      } else {
        if (!command) {
          this.authUser[userIndex].isMobile = !this.authUser[userIndex].isMobile
        } else {
          this.authUser[userIndex].auth = command || 3
        }
      }
      this.popoverList[userIndex] = false
      this.updateAuth()
    },

    /**
     * @description: 修改部门权限
     * @param {*} command
     * @param {*} deptId
     * @return {*}
     */
    handleDeptTreeCommand(command, deptId) {
      const item = this.authDept.filter(o => o.deptId == deptId)[0]
      if (!command) { // 选择仅手机端查看
        item.isMobile = !item.isMobile
      }
      if (command !== -1) {
        item.auth = command
      } else {
        this.authDept = this.authDept.filter(o => o.deptId != deptId)
      }

      this.updateAuth()
    },

    /**
     * 更新权限
     */
    updateAuth() {
      if (this.loading) return
      this.loading = true
      kmAuthUpdateAPI({
        auth: {
          isOpen: this.authData.isOpen,
          openAuth: this.authData.openAuth,
          authId: this.data.authId,
          isMobile: this.authData.isMobile
        },
        authUserList: this.authUser.map(u => {
          return { userId: u.userId, auth: u.auth || 3, isMobile: Number(u.isMobile) }
        }).concat(this.authDept.map(u => {
          return { deptId: u.deptId, auth: u.auth || 3, isMobile: Number(u.isMobile) }
        })),
        createUserId: this.data.createUserId,
        folderId: this.data.folderId,
        documentId: this.data.hasOwnProperty('documentId') ? this.data.documentId : null
      }).then(() => {
        this.loading = false
        this.$emit('update')
        this.updateFlag = true
      }).catch(() => {
        this.loading = false
      })
    },

    handleClose() {
      if (this.updateFlag) {
        this.$emit('update')
      }
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
  .slide-fade-enter-active,
  .slide-fade-leave-active {
    will-change: transform;
    transition: all 0.35s ease;
  }

  .slide-fade-enter,
  .slide-fade-leave-to {
    transform: translateX(100%);
  }

  .slide-wrapper {
    background-color: white;

    ::v-deep .el-card__body {
      height: 100%;
    }

    .slider-body {
      height: 100%;
      overflow: hidden;

      .section {
        padding: 8px 0;

        .section-title {
          margin-bottom: 10px;
          font-size: 14px;
          color: $--color-text-regular;

          .name {
            color: $--color-text-secondary;
          }

          .close {
            display: block;
            font-size: 20px;
            color: #909399;
            cursor: pointer;
          }

          .close:hover {
            color: $--color-primary;
          }
        }

        .section-body {
          ::v-deep .el-button--text,
          ::v-deep .el-button--text:hover {
            color: $--color-text-primary;
          }

          .user-item {
            padding-left: 18px;
            margin: 6px 0;

            .el-button.is-disabled {
              color: $--disabled-color-base;
            }
          }

          ::v-deep .el-dropdown.is-visible {
            .el-button {
              background-color: unset;
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

            ::v-deep .el-dropdown.is-visible {
              .el-button {
                background-color: unset;
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
                padding: 8px 0;
              }
            }
          }
        }

        &.is-users {
          height: calc(100% - 80px);
          overflow: auto;
        }
      }
    }
  }

  .open-option {
    .open {
      padding-bottom: 8px;
      border-bottom: 1px solid $--border-color-base;

      .desc {
        padding-left: 20px;
        margin: 5px 0;
        font-size: 12px;
        color: $--color-text-secondary;
      }

      .el-radio {
        display: block;
        margin: 10px 0 10px 20px;

        .el-radio-container {
          display: flex;
          flex-direction: column;

          span {
            &:nth-of-type(2) {
              margin-top: 8px;
              color: $--color-text-placeholder;
            }
          }
        }
      }

      .mobile-checkbox {
        width: 100%;
        padding-top: 20px;
        margin: 0 0 10px 20px;
        border-top: 1px solid $--border-color-base;
      }
    }

    .self {
      padding-top: 10px;
      cursor: pointer;

      .el-icon-check {
        font-weight: bold;
        color: $--color-primary;
      }
    }
  }

  .auth-list {
    .auth-list-item {
      padding: 4px;
      cursor: pointer;
      border-radius: $--border-radius-base;

      &:hover {
        color: $--color-primary;
        background-color: $--background-color-base;

        .desc {
          color: $--color-primary;
        }
      }

      .label {
        font-size: 14px;
      }

      .desc {
        font-size: 12px;
        color: $--color-text-secondary;
      }

      .el-icon-check {
        font-weight: bold;
        color: $--color-primary;
      }
    }
  }

  .members-dep-tips {
    position: absolute;
    top: 13px;
    left: 40px;
    color: $--color-text-secondary;
  }
</style>
