import request from '@/utils/request'

/**
 * 查询知识库文档结构树
 * @param params
 */
export function kmFolderQueryTreeList(params) {
  return request({
    url: '/kmFolder/queryTreeList',
    method: 'post',
    data: params
  })
}

/**
 * 查询文件夹树
 * @param params
 */
export function kmFolderQueryMoveTreeListAPI(params) {
  return request({
    url: '/kmFolder/queryMoveTreeList',
    method: 'post',
    data: params
  })
}

/**
 * 添加或修改文件夹
 * @param params
 */
export function kmFolderAddOrUpdateAPI(params) {
  const url = params.folderId ? 'update' : 'add'
  return request({
    url: '/kmFolder/' + url,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 文件夹详情
 * @param params
 */
export function kmFolderQueryByIdAPI(params) {
  return request({
    url: '/kmFolder/queryById',
    method: 'post',
    data: params
  })
}

/**
 * 文件夹删除
 * @param params
 */
export function kmFolderDeleteByIdAPI(params) {
  return request({
    url: '/kmFolder/delete',
    method: 'post',
    data: params
  })
}

/**
 * 文件夹永久删除
 * @param params
 */
export function kmFolderCompletelyDeleteAPI(params) {
  return request({
    url: '/kmFolder/completelyDelete',
    method: 'post',
    data: params
  })
}

/**
 * 移动文件夹
 * @param params
 */
export function kmFolderMoveAPI(params) {
  return request({
    url: '/kmFolder/move',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
