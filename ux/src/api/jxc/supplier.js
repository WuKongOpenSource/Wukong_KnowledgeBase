/**
 * Created by yxk at 2020/6/1
 */
import request from '@/utils/request'

/**
 * 供应商列表
 */
export function jxcSupplierPageListAPI(data) {
  return request({
    url: 'jxcSupplier/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商详情
 */
export function jxcSupplierQueryByIdAPI(data) {
  return request({
    url: `jxcSupplier/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 供应商基本信息
 */
export function jxcSupplierInformationAPI(data) {
  return request({
    url: `jxcSupplier/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 供应商新建/编辑
 */
export function jxcSupplierAddOrUpdateAPI(data) {
  return request({
    url: 'jxcSupplier/addOrUpdate',
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
export function jxcSupplierFieldAPI(data) {
  return request({
    url: 'jxcSupplier/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 删除供应商
 */
export function jxcSupplierDeleteByIdsAPI(data) {
  return request({
    url: 'jxcSupplier/delete',
    method: 'post',
    data: data
  })
}

/**
 * 供应商导出
 */
export function jxcSupplierExportAPI(data) {
  return request({
    url: 'jxcSupplier/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 查询供应商下关联的产品
 */
export function jxcSupplierProductsListAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/products`,
    method: 'post',
    data: data
  })
}

/**
 * 查询供应商下关联的上架产品
 */
export function jxcSupplierProductsNormalAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/normalProducts`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询供应商未关联的产品
 */
export function jxcSupplierProductsNotRelatedAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/notRelatedProducts`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商关联产品
 */
export function jxcSupplierRelatedProductsAPI(data) {
  return request({
    url: 'jxcSupplier/relatedProducts',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商一键关联所有产品
 */
export function jxcSupplierRelatedAllProductsAPI(data) {
  return request({
    url: `jxcSupplier/relatedAllProducts/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 供应商解除关联产品
 */
export function jxcSupplierUnRelatedProductsAPI(data) {
  return request({
    url: 'jxcSupplier/unRelatedProducts',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 供应商下的采购单
 */
export function jxcSupplierPurchaseListAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/purchases`,
    method: 'post',
    data: data
  })
}

/**
 * 供应商下的采购退货单
 */
export function jxcSupplierRetreatsListAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/retreats`,
    method: 'post',
    data: data
  })
}

/**
 * 供应商下的回款单
 */
export function jxcSupplierCollectionsListAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/collections`,
    method: 'post',
    data: data
  })
}

/**
 * 供应商下的付款单
 */
export function jxcSupplierPaymentListAPI(data) {
  return request({
    url: `jxcSupplier/${data.supplierId}/payments`,
    method: 'post',
    data: data
  })
}
