/**
 * Create by yxk at 2020/6/12
 */
import request from '@/utils/request'

/**
 * 调拨列表
 */
export function jxcAllocationPageListAPI(data) {
  return request({
    url: 'jxcAllocation/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 调拨详情
 */
export function jxcAllocationQueryByIdAPI(data) {
  return request({
    url: 'jxcAllocation/queryById',
    method: 'post',
    data: data
  })
}

/**
 * 调拨基本信息
 */
export function jxcAllocationInformationAPI(data) {
  return request({
    url: `jxcAllocation/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 调拨新建/编辑
 */
export function jxcAllocationAddOrUpdateAPI(data) {
  return request({
    url: 'jxcAllocation/addOrUpdate',
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
export function jxcAllocationFieldAPI(data) {
  return request({
    url: 'jxcAllocation/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 调拨删除
 */
export function jxcAllocationDeleteByIdsAPI(data) {
  return request({
    url: 'jxcAllocation/delete',
    method: 'post',
    data: data
  })
}

/**
 * 调拨导出
 */
export function jxcAllocationExportAPI(data) {
  return request({
    url: 'jxcAllocation/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 调拨作废
 */
export function jxcAllocationInvalidAPI(data) {
  return request({
    url: 'jxcAllocation/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 查询调拨下的关联产品
 */
export function jxcAllocationQueryChasProductListAPI(data) {
  return request({
    url: 'jxcAllocationProduct/products',
    method: 'post',
    data: data
  })
}

