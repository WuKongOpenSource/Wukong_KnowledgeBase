<template>
  <div
    class="knowledge-item"
    @click="handleToMain">
    <div class="knowledge-item-img">
      <img v-src="data.coverUrl" alt="封面图">
    </div>
    <div class="knowledge-item-body">
      <div class="knowledge-item-title">
        <div class="name text-one-line">{{ data.name }}</div><i
          :class="{active: data.collectStatus === 1}"
          class="wk wk-focus-on icon-collect"
          @click.stop="collectStore" />
      </div>
      <div class="knowledge-item-des">{{ data.description }}</div>
    </div>
  </div>
</template>

<script>
import { kmCollectAddAPI, kmCollectCancelAPI } from '@/api/knowledge/index'

export default {
  name: 'KnowledgeItem',
  props: {
    data: {
      type: Object,
      required: true
    }
  },
  computed: {
    updateTime() {
      const str = this.data.updateTime || this.data.createTime || null
      if (str) {
        return this.$moment(str).format('MM-DD HH:mm')
      } else {
        return ''
      }
    }
  },
  methods: {
    handleToMain() {
      this.$router.push({
        path: '/knowledge/main',
        query: {
          id: this.data.libraryId,
          navActiveMenu: '/knowledge/index'
        }
      })
    },

    /**
     * 关注/取消关注
     */
    collectStore() {
      const params = {
        type: 1,
        typeId: this.data.libraryId
      }
      const request = this.data.collectStatus === 0 ? kmCollectAddAPI : kmCollectCancelAPI
      request(params).then(() => {
        const msg = this.data.collectStatus === 0 ? '关注成功' : '已取消关注'
        this.$message.success(msg)
        this.$emit('update')
      }).catch()
    }
  }
}
</script>

<style scoped lang="scss">
  .knowledge-item {
    display: flex;
    width: 320px;
    padding: 16px;
    cursor: pointer;

    // border: $--border-base;
    border-radius: $--border-radius-base;
    box-shadow: $--box-shadow-base;

    &-img {
      flex-shrink: 0;
      width: 100px;
      height: 60px;
      overflow: hidden;
      border-radius: $--border-radius-base;

      img {
        width: 100%;
        height: 100%;
      }
    }

    &-body {
      flex: 1;
      margin-left: 16px;
      overflow: hidden;
    }

    &-title {
      display: flex;
      align-items: center;

      .name {
        display: inline-block;
        font-size: 16px;
      }

      .icon-collect {
        flex-shrink: 0;
        margin-left: 8px;
        color: #e2e4e9;
        cursor: pointer;
      }

      .icon-collect.active {
        color: #fac23d;
      }
    }

    &-des {
      display: flex;
      margin-top: 4px;
      overflow: hidden;
      line-height: 1.2;
      color: $--color-text-secondary;
      text-overflow: ellipsis;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    }

    // .content {
    //   position: relative;
    //   width: 100%;
    //   height: 0;
    //   padding-top: 50%;
    //   border-radius: $--border-radius-base;
    //   cursor: pointer;
    //   overflow: hidden;
    //   &:hover {
    //     transform: translateY(-3px);
    //     transition: transform ease 0.1s;
    //     box-shadow: 0 3px 5px rgba(0,0,0,0.15);
    //   }
    // }
    // &__pic {
    //   width: 100%;
    //   border-radius: $--border-radius-base;
    //   display: block;
    //   margin-top: -50%;
    // }
    // &__content {
    //   position: absolute;
    //   top: 0;
    //   left: 0;
    //   width: 100%;
    //   height: 100%;
    //   background-image: linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, transparent);
    //   border-radius: $--border-radius-base;
    //   overflow: hidden;

    //   .title {
    //     width: 100%;
    //     font-size: 14px;
    //     font-weight: bold;
    //     color: white;
    //     padding: 10px;
    //     .text {
    //       margin-right: 20px;
    //     }
    //     .icon-collect {
    //       color: #E2E4E9;
    //     }
    //     .icon-collect.active {
    //       color: #FAC23D;
    //     }
    //   }
    // }
  }
</style>
