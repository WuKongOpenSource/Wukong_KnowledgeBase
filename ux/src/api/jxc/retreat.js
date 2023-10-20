/**
 * Create by yxk at 2020/6/11
 */
import request from '@/utils/request'

/**
 * 查询采购退货列表
 */
export function jxcRetreatPageListAPI(data) {
  return request({
    url: 'jxcRetreat/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购退货单详情
 */
export function jxcRetreatQueryByIdAPI(data) {
  return request({
    url: `jxcRetreat/queryById/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 采购退货单基本信息
 */
export function jxcRetreatInformationAPI(data) {
  return request({
    url: `jxcRetreat/information/${data.id}`,
    method: 'post',
    data: data
  })
}

/**
 * 查询新增所需字段
 */
export function jxcRetreatFieldAPI(data) {
  return request({
    url: 'jxcRetreat/field/' + (data.id || ''),
    method: 'post',
    data: data
  })
}

/**
 * 采购退货单新建/编辑
 */
export function jxcRetreatAddOrUpdateAPI(data) {
  return request({
    url: 'jxcRetreat/addOrUpdate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购退货单删除
 */
export function jxcRetreatDeleteByIdsAPI(data) {
  return request({
    url: 'jxcRetreat/delete',
    method: 'post',
    data: data
  })
}

/**
 * 采购退货单导出
 */
export function jxcRetreatExportAPI(data) {
  return request({
    url: 'jxcRetreat/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 查询采购可退货单关联产品
 */
export function jxcRetreatQueryEdProductListAPI(data) {
  return request({
    url: 'jxcRetreatProduct/queryCouldRetreatProducts ',
    method: 'post',
    data: data
  })
}

/**
 * 查询采购退货单关联产品
 */
export function jxcRetreatQueryProductAPI(data) {
  return request({
    url: 'jxcRetreatProduct/queryRetreatProducts  ',
    method: 'post',
    data: data
  })
}

/**
 * 编辑时查询采购退货单关联产品
 */
export function jxcRetreatQueryProductListAPI(data) {
  return request({
    url: 'jxcRetreatProduct/queryRetreatProducts',
    method: 'post',
    data: data
  })
}

/**
 * 查询可出库的采购退货单
 */
export function jxcRetreatCouldOutboundAPI(data) {
  return request({
    url: 'jxcRetreat/couldOutboundRetreat',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 采购退货出库记录
 */
export function jxcRetreatQueryRecordAPI(data) {
  return request({
    url: 'jxcRetreat/queryRecord',
    method: 'post',
    data: data
  })
}

/**
 * 采购退货作废
 */
export function jxcRetreatInvalidAPI(data) {
  return request({
    url: 'jxcRetreat/invalid',
    method: 'post',
    data: data
  })
}

/**
 * 采购退货单下的回款
 */
export function jxcRetreatCollectionsAPI(data) {
  return request({
    url: `jxcRetreat/${data.retreatId}/collections`,
    method: 'post',
    data: data
  })
}
