/**
 * Create by yxk at 2020/6/9
 */
import request from '@/utils/request'

/**
 * 出入库明细
 */
export function jxcDetailedQueryPageListAPI(data) {
  return request({
    url: 'jxcDetailed/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 出入库明细导出
 */
export function jxcDetailedExportAPI(data) {
  return request({
    url: '/jxcDetailed/exportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

