/**
 * Create by yxk at 2020/6/13
 */
import request from '@/utils/request'

/**
 * 查询产品库存
 */
export function jxcWarehouseProductQueryPageListAPI(data) {
  return request({
    url: 'jxcWarehouseProduct/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询产品库存不分页
 */
export function jxcWarehouseProductQueryListAPI(data) {
  return request({
    url: 'jxcWarehouseProduct/queryList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 仓库商品处理导出
 */
export function jxcWarehouseProductAllExportExcelAPI(data) {
  return request({
    url: '/jxcWarehouseProduct/allExportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

