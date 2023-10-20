import request from '@/utils/request'

/**
 * 产品分类销量分析
 */
export function biProductCategoryAPI(data) {
  return request({
    url: 'biProduct/contractProductRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品分类销量分析 --详情
 */
export function biProductCategoryListAPI(data) {
  return request({
    url: 'biCustomer/queryProductTypeList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品详情列表
 * @param {*} data
 */
export function biProductDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchProductPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品成交周期客户数
 * @param {*} data
 */
export function biProductCircleDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/queryProductSucceedCustomerList ',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

