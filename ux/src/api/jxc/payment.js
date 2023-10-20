/**
 * Create by yxk at 2020/6/15
 */
import request from '@/utils/request'

/**
 * 付款单订单列表
 */
export function jxcPaymentPageListAPI(data) {
  return request({
    url: 'jxcPayment/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 付款单订单详情
 */
export function jxcPaymentQueryByIdAPI(data) {
  return request({
    url: `jxcPayment/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 付款单订单基本信息
 */
export function jxcPaymentInformationAPI(data) {
  return request({
    url: `jxcPayment/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 付款单新建/编辑
 */
export function jxcPaymentAddOrUpdateAPI(data) {
  return request({
    url: 'jxcPayment/addOrUpdate',
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
export function jxcPaymentFieldAPI(data) {
  return request({
    url: 'jxcPayment/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 删除付款单
 */
export function jxcPaymentDeleteByIdsAPI(data) {
  return request({
    url: 'jxcPayment/delete',
    method: 'post',
    data: data
  })
}

/**
 * 付款单作废
 */
export function jxcPaymentInvalid(data) {
  return request({
    url: 'jxcPayment/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 付款单作废
 */
export function jxcPaymentExportAPI(data) {
  return request({
    url: 'jxcPayment/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}
