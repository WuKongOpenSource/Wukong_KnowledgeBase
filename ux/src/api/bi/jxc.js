/**
 * Create by yxk at 2020/7/9 0009
 */

import request from '@/utils/request'

/**
 * 采购汇总统计
 * @param {*} data
 */
export function jxcPurchasingStatisticsAPI(data) {
  return request({
    url: 'jxcPurchaseStatistics/purchasingStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购订单数列表
 * @param {*} data
 */
export function jxcPurchaseStatisticsListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'time') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcPurchaseStatistics/queryPurchasingStatisticsList/${data.time}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购退货数列表
 * @param {*} data
 */
export function jxcPurchaseStatisticsRetreatListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'time') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcPurchaseStatistics/queryRetreatStatisticsList/${data.time}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购供应商列表
 * @param {*} data
 */
export function jxcPurchaseStatisticsSupplierListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'time') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcPurchaseStatistics/querySupplierPurchasingStatisticsList/${data.time}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 退货供应商列表
 * @param {*} data
 */
export function jxcPurchaseStatisticsSupplierRetreattListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'time') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcPurchaseStatistics/querySupplierRetreatStatisticsList/${data.time}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品采购分析
 * @param {*} data
 */
export function jxcProductPurchaseStatisticsAPI(data) {
  return request({
    url: 'jxcProductPurchaseStatistics/productPurchaseStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购分析统计(头部部分)
 * @param {*} data
 */
export function jxcProductPurchaseStatisticsHeadAPI(data) {
  return request({
    url: 'jxcProductPurchaseStatistics/purchaseHeadStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商采购分析(头部部分)
 * @param {*} data
 */
export function jxcSupplierPurchaseStatisticsHeadAPI(data) {
  return request({
    url: 'jxcSupplierPurchaseStatistics/purchaseHeadStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购订单列表
 * @param {*} data
 */
export function jxcProductPurchaseStatisticsListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'batchId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcProductPurchaseStatistics/queryPurchaseStatisticsList/${data.batchId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购退货列表
 * @param {*} data
 */
export function jxcProductPurchaseStatisticsRetreatAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'batchId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcProductPurchaseStatistics/queryRetreatStatistics/${data.batchId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商采购分析
 * @param {*} data
 */
export function jxcSupplierPurchaseStatisticsAPI(data) {
  return request({
    url: 'jxcSupplierPurchaseStatistics/supplierPurchaseStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商采购分析 采购订单列表
 * @param {*} data
 */
export function jxcSupplierPurchaseStatisticsListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'supplierId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcSupplierPurchaseStatistics/queryPurchaseStatisticsList/${data.supplierId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商采购分析 采购退货列表
 * @param {*} data
 */
export function jxcSupplierPurchaseStatisticsRetreatAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'supplierId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcSupplierPurchaseStatistics/queryRetreatStatistics/${data.supplierId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售汇总分析
 * @param {*} data
 */
export function jxcSaleStatisticsAPI(data) {
  return request({
    url: 'jxcSaleStatistics/saleStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售汇总分析 销售订单列表
 * @param {*} data
 */
export function jxcSaleStatisticsListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'time') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcSaleStatistics/querySaleStatisticsList/${data.time}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售汇总分析 销售退货订单列表
 * @param {*} data
 */
export function jxcSaleStatisticsRetreatListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'time') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcSaleStatistics/querySaleReturnStatisticsList/${data.time}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 产品采购分析
 * @param {*} data
 */
export function jxcProductSaleStatisticsAPI(data) {
  return request({
    url: 'jxcProductSaleStatistics/productSaleStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 采购分析统计(头部部分)
 * @param {*} data
 */
export function jxcProductSaleStatisticsHeadAPI(data) {
  return request({
    url: 'jxcProductSaleStatistics/saleHeadStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 客户销售分析(头部部分)
 * @param {*} data
 */
export function jxcCustomerSaleStatisticsHeadAPI(data) {
  return request({
    url: 'jxcCustomerSaleStatistics/saleHeadStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 订单销售列表
 * @param {*} data
 */
export function jxcProductSaleStatisticsListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'batchId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcProductSaleStatistics/querySaleStatisticsList/${data.batchId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 订单退货列表
 * @param {*} data
 */
export function jxcProductSaleStatisticsRetreatAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'batchId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcProductSaleStatistics/querySaleReturnStatistics/${data.batchId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 客户销售分析
 * @param {*} data
 */
export function jxcCustomerSaleStatisticsAPI(data) {
  return request({
    url: 'jxcCustomerSaleStatistics/customerSaleStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 销售订单列表
 * @param {*} data
 */
export function jxcCustomerSaleStatisticsListAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'customerId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcCustomerSaleStatistics/querySaleStatisticsList/${data.customerId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售分析 退货订单列表
 * @param {*} data
 */
export function jxcCustomerSaleStatisticsRetreatAPI(data) {
  const result = {}
  Object.keys(data).forEach(key => {
    if (key != 'customerId') {
      result[key] = data[key]
    }
  })
  return request({
    url: `jxcCustomerSaleStatistics/querySaleReturnStatistics/${data.customerId}`,
    method: 'post',
    data: result,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品分析
 * @param {*} data
 */
export function jxcProductStatisticsAPI(data) {
  return request({
    url: 'jxcProductStatistics/productStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购分析统计(头部部分)
 * @param {*} data
 */
export function jxcProductStatisticsHeadAPI(data) {
  return request({
    url: 'jxcProductStatistics/productHeadStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
