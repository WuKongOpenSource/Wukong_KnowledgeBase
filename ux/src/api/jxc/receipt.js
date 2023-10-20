/**
 * Create by yxk at 2020/6/12
 */
import request from '@/utils/request'

/**
 * 入库单列表
 */
export function jxcReceiptPageListAPI(data) {
  return request({
    url: 'jxcReceipt/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 入库单详情
 */
export function jxcReceiptQueryByIdAPI(data) {
  return request({
    url: `jxcReceipt/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 入库单基本信息
 */
export function jxcReceiptInformationAPI(data) {
  return request({
    url: `jxcReceipt/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 入库单新建/编辑
 */
export function jxcReceiptAddOrUpdateAPI(data) {
  return request({
    url: 'jxcReceipt/addOrUpdate',
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
export function jxcReceiptFieldAPI(data) {
  return request({
    url: 'jxcReceipt/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 入库单作废
 */
export function jxcReceiptInvalidAPI(data) {
  return request({
    url: 'jxcReceipt/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 查询可入库的产品
 */
export function jxcReceiptProductCouldReceiptAPI(data) {
  return request({
    url: 'jxcReceiptProduct/queryCouldReceiptProducts',
    method: 'post',
    data: data
  })
}

/**
 * 查询入库单下的产品
 */
export function jxcReceiptQueryProductListAPI(data) {
  return request({
    url: 'jxcReceiptProduct/queryReceiptProducts',
    method: 'post',
    data: data
  })
}

/**
 * 入库单导出
 */
export function jxcReceiptExportAPI(data) {
  return request({
    url: 'jxcReceipt/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

