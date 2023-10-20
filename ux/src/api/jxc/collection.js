/**
 * Create by yxk at 2020/6/15
 */
import request from '@/utils/request'

/**
 * 回款单订单列表
 */
export function jxcCollectionPageListAPI(data) {
  return request({
    url: 'jxcCollection/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 回款单订单详情
 */
export function jxcCollectionQueryByIdAPI(data) {
  return request({
    url: `jxcCollection/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 回款单订单基本信息
 */
export function jxcCollectionInformationAPI(data) {
  return request({
    url: `jxcCollection/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 回款单新建/编辑
 */
export function jxcCollectionAddOrUpdateAPI(data) {
  return request({
    url: 'jxcCollection/addOrUpdate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询新增所需字段
 */
export function jxcCollectionFieldAPI(data) {
  return request({
    url: 'jxcCollection/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 删除回款单
 */
export function jxcCollectionDeleteByIdsAPI(data) {
  return request({
    url: 'jxcCollection/delete',
    method: 'post',
    data: data
  })
}

/**
 * 回款单作废
 */
export function jxcCollectionInvalidAPI(data) {
  return request({
    url: 'jxcCollection/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 回款单作废
 */
export function jxcCollectionExportAPI(data) {
  return request({
    url: 'jxcCollection/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}
