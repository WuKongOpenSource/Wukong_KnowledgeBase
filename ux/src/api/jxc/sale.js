/**
 * Create by yxk at 2020/6/12
 */
import request from '@/utils/request'

/**
 * 销售订单列表
 */
export function jxcSalePageListAPI(data) {
  return request({
    url: 'jxcSale/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售订单详情
 */
export function jxcSaleQueryByIdAPI(data) {
  return request({
    url: `jxcSale/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 销售订单基本信息
 */
export function jxcSaleInformationAPI(data) {
  return request({
    url: `jxcSale/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 销售订单新建/编辑
 */
export function jxcSaleAddOrUpdateAPI(data) {
  return request({
    url: 'jxcSale/addOrUpdate',
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
export function jxcSaleFieldAPI(data) {
  return request({
    url: 'jxcSale/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 销售订单删除
 */
export function jxcSaleDeleteByIdsAPI(data) {
  return request({
    url: 'jxcSale/delete',
    method: 'post',
    data: data
  })
}

/**
 * 销售订单导出
 */
export function jxcSaleExportAPI(data) {
  return request({
    url: 'jxcSale/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 查询销售订单下的产品
 */
export function jxcSaleProductQuerySaleProductAPI(data) {
  return request({
    url: 'jxcSaleProduct/querySaleProducts',
    method: 'post',
    data: data
  })
}

/**
 * 查询可进行销售的产品
 */
export function jxcSaleProductQueryPageListAPI(data) {
  return request({
    url: 'jxcSaleProduct/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询可进行退货的销售单(审核通过，没有全部退货)
 */
export function jxcSaleCouldRetreatAPI(data) {
  return request({
    url: 'jxcSale/couldRetreatSale',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询销售单下可以进行退货的产品
 */
export function jxcSaleCouldRetreatProductsAPI(data) {
  return request({
    url: 'jxcSale/couldRetreatSale',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 编辑销售单时查询产品
 */
export function jxcSaleEditProductAPI(data) {
  return request({
    url: `jxcSale/${data.saleId}/products`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询可以出库的销售单(审核通过，没有全部出库)
 */
export function jxcSaleCouldOutboundAPI(data) {
  return request({
    url: 'jxcSale/couldOutboundSale',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售订单出库记录
 */
export function jxcSaleQueryRecordAPI(data) {
  return request({
    url: 'jxcSale/queryRecord',
    method: 'post',
    data: data
  })
}

/**
 * 销售订单作废
 */
export function jxcSaleInvalidAPI(data) {
  return request({
    url: 'jxcSale/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 销售订单下的销售退货记录
 */
export function jxcSaleReturnsListAPI(data) {
  return request({
    url: `jxcSale/${data.id}/saleReturns`,
    method: 'post',
    data: data
  })
}

/**
 * 销售订单下的回款
 */
export function jxcSaleCollectionsAPI(data) {
  return request({
    url: `jxcSale/${data.saleId}/collections`,
    method: 'post',
    data: data
  })
}

