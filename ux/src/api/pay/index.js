import request from '@/utils/request'

/**
 * @description: 统一下单
 * @param {*}
 * @return {*}
 */

export function payCreateOrderAPI(params) {
  return request({
    url: 'adminPay/createOrder',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * @description: 获取订单历表
 * @param {*} params
 * @return {*}
 */
export function payGetOrderListAPI(params) {
  return request({
    url: 'adminPay/getOrderList',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * @description: 取消订单
 * @param {*}
 * @return {*}
 */

export function payCloseOrderAPI(params) {
  return request({
    url: `adminPay/closeOrder/${params}`,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * @description: 支付订单结果查询
 * @param {*}
 * @return {*}
 */
export function payQueryOrderAPI(params) {
  return request({
    url: '/adminPay/queryOrder',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * @description: 获取是未支付的订单
 * @param {*}
 * @return {*}
 */

export function payGetNotPaidOrderByGoodsIdAPI(goodsId) {
  return request({
    url: `/adminPay/getNotPaidOrderByGoodsId/${goodsId}`,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
