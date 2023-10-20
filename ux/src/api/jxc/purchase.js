/**
 * Create by yxk at 2020/6/9
 */
import request from '@/utils/request'

/**
 * 查询采购单列表
 */
export function jxcPurchasePageListAPI(data) {
  return request({
    url: 'jxcPurchase/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购单详情
 */
export function jxcPurchaseQueryByIdAPI(data) {
  return request({
    url: `jxcPurchase/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 采购订单基本信息
 */
export function jxcPurchaseInformationAPI(data) {
  return request({
    url: `jxcPurchase/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 采购订单新建/编辑
 */
export function jxcPurchaseAddOrUpdateAPI(data) {
  return request({
    url: 'jxcPurchase/addOrUpdate',
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
export function jxcPurchaseFieldAPI(data) {
  return request({
    url: 'jxcPurchase/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 采购订单删除
 */
export function jxcPurchaseDeleteByIdsAPI(data) {
  return request({
    url: 'jxcPurchase/delete',
    method: 'post',
    data: data
  })
}

/**
 * 采购订单导出
 */
export function jxcPurchaseExportAPI(data) {
  return request({
    url: 'jxcPurchase/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 采购订单入库记录
 */
export function jxcPurchaseQueryRecordAPI(data) {
  return request({
    url: 'jxcPurchase/queryRecord',
    method: 'post',
    data: data
  })
}

/**
 * 采购订单作废
 */
export function jxcPurchaseInvalidAPI(data) {
  return request({
    url: 'jxcPurchase/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 查询采购订单下的产品
 */
export function jxcPurchaseProductsAPI(data) {
  return request({
    url: `jxcPurchase/${data.purchaseId}/products`,
    method: 'post',
    data: data
  })
}

/**
 * 查询能够退货的采购单(审核通过，没有全部退货)
 */
export function jxcPurchaseCanRetreatAPI(data) {
  return request({
    url: 'jxcPurchase/couldRetreatPurchase',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询能够入库的采购单(审核通过，没有全部退货，没有全部入库)
 */
export function jxcPurchaseCanReceiptAPI(data) {
  return request({
    url: 'jxcPurchase/couldReceiptPurchase',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询采购单下的采购退货记录
 */
export function jxcPurchaseRetreatsAPI(data) {
  return request({
    url: `jxcPurchase/${data.id}/retreats`,
    method: 'post',
    data: data
  })
}

/**
 * 查询采购单下的付款
 */
export function jxcPurchasePaymentsAPI(data) {
  return request({
    url: `jxcPurchase/${data.purchaseId}/payments`,
    method: 'post',
    data: data
  })
}
