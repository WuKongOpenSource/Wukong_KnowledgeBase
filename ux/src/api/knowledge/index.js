import request from '@/utils/request'

/**
 * 知识库列表
 * @param params
 */
export function kmLibraryListAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/queryList',
    method: 'post',
    data: params
  })
}

/**
 * 知识库模版
 * @param params
 */
export function kmLibraryTemplateAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/queryLibraryTemplate',
    method: 'post',
    data: params
  })
}

/**
 * 知识库新建/编辑
 * @param params
 */
export function kmLibraryAddOrUpdateAPI(params) {
  const url = params.libraryId ? 'update' : 'add'
  return request({
    url: 'kmKnowledgeLibrary/' + url,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 知识库详情
 * @param params
 */
export function kmLibraryQueryByIdAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/queryById',
    method: 'post',
    data: params
  })
}

/**
 * 知识库删除
 * @param params
 */
export function kmLibraryDeleteByIdAPI(libraryId) {
  return request({
    url: `kmKnowledgeLibrary/delete/${libraryId}`,
    method: 'post'
  })
}

/**
 * 知识库永久删除
 * @param params
 */
export function kmLibraryCompleteDeleteByIdAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/completelyDelete',
    method: 'post',
    data: params
  })
}

/**
 * 查询知识库下文档
 * @param params
 */
export function kmLibraryQueryDocumentByIdAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/queryDocumentByLibraryId',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询知识库下文档
 * @param params
 */
export function kmLibraryQueryCollectDocumentByIdAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/queryCollectDocumentByLibraryId',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 查询知识库成员
 * @param params
 */
export function kmLibraryQueryMemberAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/queryMember',
    method: 'post',
    data: params
  })
}

/**
 * 更新知识库成员
 * @param params
 */
export function kmLibraryUpdateMemberAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/updateMember',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 查询收藏列表
 * @param params
 */
export function kmCollectListAPI(params) {
  return request({
    url: 'kmCollect/queryList',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 添加收藏
 * @param params
 */
export function kmCollectAddAPI(params) {
  return request({
    url: 'kmCollect/addCollect',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 取消收藏
 * @param params
 */
export function kmCollectCancelAPI(params) {
  return request({
    url: 'kmCollect/cancelCollect',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 最近使用/删除
 * @param params
 */
export function kmRecordListAPI(params) {
  return request({
    url: 'kmActionRecord/queryList',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 新最近删除
 * @param params
 */
export function kmRecordRecentDeleteListAPI(params) {
  return request({
    url: 'kmActionRecord/queryDeleteList',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 恢复 文件夹：2 文档：3 文件：4
 * @param params
 */
export function kmRecordRestoreAPI(params) {
  return request({
    url: 'kmActionRecord/restore',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }

  })
}

/**
 * 回收站
 * @param params
 */
export function kmRecordDeleteListAPI(data) {
  return request({
    url: 'kmActionRecord/queryLibraryDeleteList',
    method: 'post',
    data
  })
}

/**
 * 修改权限
 * @param params
 */
export function kmAuthUpdateAPI(params) {
  return request({
    url: 'kmAuth/updateAuth',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 文档点赞/取消
 * @param params
 */
export function kmLibrarySearchAPI(params) {
  return request({
    url: 'kmKnowledgeLibrary/search',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 开启分享
 * @param params
 */
export function kmLibraryShareOpenAPI(params) {
  return request({
    url: 'kmDocumentShare/openShare',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 关闭分享
 * @param params
 */
export function kmLibraryShareCloseAPI(params) {
  return request({
    url: 'kmDocumentShare/closeShare',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

/**
 * 分享成员
 * @param params
 */
export function kmLibraryShareMemberAPI(params) {
  return request({
    url: 'kmDocumentShare/addShareMember',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    data: params
  })
}

