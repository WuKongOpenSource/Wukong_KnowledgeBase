/**
 * Create by yxk at 2020/6/15
 */
import request from '@/utils/request'

/**
 * 出库单列表
 */
export function jxcOutboundPageListAPI(data) {
  return request({
    url: 'jxcOutbound/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 出库单详情
 */
export function jxcOutboundQueryByIdAPI(data) {
  return request({
    url: `jxcOutbound/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 出库单基本信息
 */
export function jxcOutboundInformationAPI(data) {
  return request({
    url: `jxcOutbound/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 出库单新建/编辑
 */
export function jxcOutboundAddOrUpdateAPI(data) {
  return request({
    url: 'jxcOutbound/addOrUpdate',
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
export function jxcOutboundFieldAPI(data) {
  return request({
    url: 'jxcOutbound/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 出库单作废
 */
export function jxcOutboundInvalidAPI(data) {
  return request({
    url: 'jxcOutbound/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 查询可出库的产品
 */
export function jxcOutboundQueryCouldOutboundProductsAPI(data) {
  return request({
    url: 'jxcOutboundProduct/queryCouldOutboundProducts',
    method: 'post',
    data: data
  })
}

/**
 * 查询出库单下的产品
 */
export function jxcOutboundQueryProductsAPI(data) {
  return request({
    url: 'jxcOutboundProduct/queryOutboundProducts',
    method: 'post',
    data: data
  })
}

/**
 * 出库单导出
 */
export function jxcOutboundExportAPI(data) {
  return request({
    url: 'jxcOutbound/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}
