/**
 * Create by yxk at 2020/6/12
 */
import request from '@/utils/request'

/**
 * 查询销售退货单列表
 */
export function jxcSaleReturnPageListAPI(data) {
  return request({
    url: 'jxcSaleReturn/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售退货单详情
 */
export function jxcSaleReturnQueryByIdAPI(data) {
  return request({
    url: `jxcSaleReturn/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 销售退货单基本信息
 */
export function jxcSaleReturnInformationAPI(data) {
  return request({
    url: `jxcSaleReturn/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 销售退货单新建/编辑
 */
export function jxcSaleReturnAddOrUpdateAPI(data) {
  return request({
    url: 'jxcSaleReturn/addOrUpdate',
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
export function jxcSaleReturnFieldAPI(data) {
  return request({
    url: 'jxcSaleReturn/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 销售退货单删除
 */
export function jxcSaleReturnDeleteByIdsAPI(data) {
  return request({
    url: 'jxcSaleReturn/delete',
    method: 'post',
    data: data
  })
}

/**
 * 销售退货单导出
 */
export function jxcSaleReturnExportAPI(data) {
  return request({
    url: 'jxcSaleReturn/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 查询可进行销售退货的商品
 */
export function jxcSaleReturnQueryEdProductListAPI(data) {
  return request({
    url: 'jxcSaleReturnProduct/queryEdSalereturnProductList',
    method: 'post',
    data: data
  })
}

/**
 * 查询销售退货单下的商品
 */
export function jxcSaleReturnQueryProductListAPI(data) {
  return request({
    url: 'jxcSaleReturnProduct/querySaleReturnProducts',
    method: 'post',
    data: data
  })
}

/**
 * 销售退货单入库记录
 */
export function jxcSaleReturnQueryRecordAPI(data) {
  return request({
    url: 'jxcSaleReturn/queryRecord',
    method: 'post',
    data: data
  })
}

/**
 * 销售退货单作废
 */
export function jxcSaleReturnInvalidByIdsAPI(data) {
  return request({
    url: 'jxcSaleReturn/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 查询销售单下可退货关联产品
 */
export function jxcSaleReturnCouldReturnAPI(data) {
  return request({
    url: 'jxcSaleReturnProduct/queryCouldSaleReturnProducts',
    method: 'post',
    data: data
  })
}

/**
 * 查询可以入库的销售退货单
 */
export function jxcSaleReturnCouldReceiptAPI(data) {
  return request({
    url: 'jxcSaleReturn/couldReceiptSaleReturn',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售退货下的付款单
 */
export function jxcSaleReturnPaymentsAPI(data) {
  return request({
    url: `jxcSaleReturn/${data.saleReturnId}/payments`,
    method: 'post',
    data: data
  })
}
