/**
 * Create by yxk at 2020/6/12
 */
import request from '@/utils/request'

/**
 * 盘点订单列表
 */
export function jxcInventoryPageListAPI(data) {
  return request({
    url: 'jxcInventory/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 盘点订单详情
 */
export function jxcInventoryQueryByIdAPI(data) {
  return request({
    url: `jxcInventory/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 盘点订单基本信息
 */
export function jxcInventoryInformationAPI(data) {
  return request({
    url: `jxcInventory/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 盘点新建/编辑
 */
export function jxcInventoryAddOrUpdateAPI(data) {
  return request({
    url: 'jxcInventory/addOrUpdate',
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
export function jxcInventoryFieldAPI(data) {
  return request({
    url: 'jxcInventory/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 盘点导出
 */
export function jxcInventoryExportAPI(data) {
  return request({
    url: 'jxcInventory/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 盘点删除
 */
export function jxcInventoryDeleteByIdsAPI(data) {
  return request({
    url: 'jxcInventory/delete',
    method: 'post',
    data: data
  })
}

/**
 * 盘点作废
 */
export function jxcInventoryInvalidAPI(data) {
  return request({
    url: 'jxcInventory/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 盘点-入库单
 */
export function jxcInventoryRecordAddOrUpdateAPI(data) {
  return request({
    url: 'jxcInventoryRecord/addOrUpdate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 盘点-入库单-自动编号规则
 */
export function jxcInventoryRecordAutomaticAPI(data) {
  return request({
    url: 'jxcInventoryRecord/automatic',
    method: 'post',
    data: data
  })
}

/**
 * 查询盘点的产品
 */
export function jxcInventoryRecordProductsAPI(data) {
  return request({
    url: 'jxcInventoryRecord/products',
    method: 'post',
    data: data
  })
}

/**
 * 盘点-查看盘点入库信息
 */
export function jxcInventoryQueryRecordAPI(data) {
  return request({
    url: 'jxcInventoryRecord/queryRecord ',
    method: 'post',
    data: data
  })
}

/**
 * 盘点-盈亏作废
 */
export function jxcInventoryRecordSetStateByIdsAPI(data) {
  return request({
    url: '/jxcInventoryRecord/setStateByIds',
    method: 'post',
    data: data
  })
}

