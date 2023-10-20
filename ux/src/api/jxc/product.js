/**
 * Created by yxk at 2020/6/1
 */
import request from '@/utils/request'

/**
 * 查询产品列表
 */
export function jxcProductPageListAPI(data) {
  return request({
    url: 'jxcProduct/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品详情
 */
export function jxcProductQueryByIdAPI(data) {
  return request({
    url: `jxcProduct/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 产品基本信息
 */
export function jxcProductInformationAPI(data) {
  return request({
    url: `jxcProduct/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 查询新增所需字段
 */
export function jxcProductFieldAPI(data) {
  return request({
    url: 'jxcProduct/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 产品新建/编辑
 */
export function jxcProductAddOrUpdateAPI(data) {
  return request({
    url: 'jxcProduct/addOrUpdate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 编辑时查询产品信息
 */
export function jxcProductDataAPI(data) {
  return request({
    url: `jxcProduct/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 删除产品
 */
export function jxcProductDeleteByIdsAPI(data) {
  return request({
    url: 'jxcProduct/delete',
    method: 'post',
    data: data
  })
}

/**
 * 产品导出
 */
export function jxcProductExportAPI(data) {
  return request({
    url: 'jxcProduct/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 产品上下架
 */
export function jxcProductUpdateShelfAPI(data) {
  return request({
    url: 'jxcProduct/addOrUpdateShelf',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询产品类别
 */
export function jxcProductQueryProductTypeAPI(data) {
  return request({
    url: 'jxcProductType/queryJxcProductType',
    method: 'post',
    data: data
  })
}

/**
 * 编辑产品类别
 */
export function jxcProductTypeAddOrUpdateAPI(data) {
  return request({
    url: 'jxcProductType/addOrUpdate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 删除产品类别
 */
export function jxcProductDeleteProductTypeByIdAPI(data) {
  return request({
    url: 'jxcProductType/deleteById',
    method: 'post',
    data: data
  })
}

/**
 * 查询产品规格
 */
export function jxcProductQueryProductSizeAPI(data) {
  return request({
    url: 'jxcProductSpecifications/querySpecificationsDate',
    method: 'post',
    data: data
  })
}

/**
 * 新增产品规格
 */
export function jxcProductAddProductSizeAPI(data) {
  return request({
    url: 'jxcProductSpecifications/addOrUpSpecificationsDate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
