import request from '@/utils/request'

/**
 * 添加或修改文档
 * @param params
 */
export function kmDocAddOrUpdateAPI(params) {
  const url = params.documentId ? 'update' : 'add'
  return request({
    url: '/kmDocument/' + url,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 移动文档
 * @param params
 */
export function kmDocMoveAPI(params) {
  return request({
    url: '/kmDocument/move',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 查询文档详情
 * @param params
 */
export function kmDocQueryByIdAPI(params) {
  return request({
    url: '/kmDocument/queryById',
    method: 'post',
    data: params
  })
}

/**
 * 文档删除
 * @param params
 */
export function kmDocDeleteByIdAPI(params) {
  return request({
    url: '/kmDocument/delete',
    method: 'post',
    data: params
  })
}

/**
 * 文档删除
 * @param params
 */
export function kmDocCompletelyDeleteAPI(params) {
  return request({
    url: '/kmDocument/completelyDelete',
    method: 'post',
    data: params
  })
}

/**
 * 查询文档列表
 * @param params
 */
export function kmDocQueryListAPI(params) {
  return request({
    url: '/kmDocument/queryList',
    method: 'post',
    data: params
  })
}

/**
 * 文档点赞/取消
 * @param params
 */
export function kmDocStarAPI(params) {
  return request({
    url: '/kmDocument/favor',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 通过消息查看知识库详情
 * @param params
 */
export function kmDocDetailRemindAPI(params) {
  return request({
    url: '/kmDocument/queryInfoById',
    method: 'post',
    data: params
  })
}
