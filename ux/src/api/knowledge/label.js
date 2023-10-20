import request from '@/utils/request'

/**
 * 添加或修改文档标签
 * @param params
 */
export function kmLabelAddOrUpdateAPI(params) {
  const url = params.labelId ? 'update' : 'add'
  return request({
    url: 'kmDocumentLabel/' + url,
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 删除文档标签
 * @param params
 */
export function kmLabelDeleteByIdAPI(labelId) {
  return request({
    url: `kmDocumentLabel/deleteById/${labelId}`,
    method: 'post'
  })
}

/**
 * 查询标签列表
 * @param params
 */
export function kmLabelQueryListAPI(params) {
  return request({
    url: 'kmDocumentLabel/queryList',
    method: 'post',
    data: params
  })
}

/**
 * 根据标签id查询文档列表
 * @param data
 */
export function kmLabelQueryDocByIdAPI(data) {
  return request({
    url: 'kmDocumentLabel/queryDocumentByLabelId',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
