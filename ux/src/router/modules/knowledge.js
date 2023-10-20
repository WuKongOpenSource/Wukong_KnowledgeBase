// 日历
import Layout from '@/views/layout/KnowledgeLayout'

const layout = function(hidden = false, meta = {}) {
  return {
    path: '/knowledge',
    component: Layout,
    hidden: hidden,
    meta: {
      requiresAuth: true,
      permissions: ['knowledge'],
      ...meta
    }
  }
}

export default [
  {
    // ...layout({
    //   permissions: ['crm']
    // }),
    ...layout(),
    children: [{
      path: 'index',
      component: () => import('@/views/knowledge/index/index'),
      meta: {
        title: '知识库',
        icon: 'book'
      }
    }, {
      path: 'main',
      hidden: true,
      component: () => import('@/views/knowledge/index/KnowledgeMain'),
      meta: {
        title: '知识库主页',
        icon: 'book',
        activeMenu: '/knowledge/index'
      }
    }, {
      path: 'search',
      hidden: true,
      component: () => import('@/views/knowledge/index/Search'),
      meta: {
        title: '搜索',
        icon: 'book',
        activeMenu: '/knowledge/index'
      }
    }, {
      path: 'filterByLabel',
      hidden: true,
      component: () => import('@/views/knowledge/index/FilterByLabel'),
      meta: {
        title: '搜索',
        icon: 'book',
        activeMenu: '/knowledge/index'
      }
    }]
  },
  {
    ...layout(),
    children: [{
      path: 'collection',
      component: () => import('@/views/knowledge/collection/index'),
      meta: {
        title: '我的关注',
        icon: 'focus-on'
      }
    }]
  },
  {
    ...layout(),
    children: [{
      path: 'recent',
      component: () => import('@/views/knowledge/recent/index'),
      meta: {
        title: '最近使用',
        icon: 'time'
      }
    }]
  },
  {
    ...layout(),
    children: [{
      path: 'recycle',
      component: () => import('@/views/knowledge/recycle/index'),
      meta: {
        title: '回收站',
        icon: 'recycle-bin'
      }
    }]
  }
]
