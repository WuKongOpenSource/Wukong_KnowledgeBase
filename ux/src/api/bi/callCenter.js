import request from '@/utils/request'

/**
 * 通话记录列表
 * type
 * structure_id: 部门id
 * user_id 员工ID
 * 分页
 */
export function crmCallIndexAPI(data) {
  return request({
    url: 'biCustomer/queryCallList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 通话记录统计分析
 * type
 * structure_id: 部门id
 * user_id 员工ID
 * 分页
 */
export function crmCallAnalysisAPI(data) {
  return request({
    url: 'biCustomer/queryCallAnalysis',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 下载
 */
export function crmCallDownloadAPI(data) {
  return request({
    url: 'crmCall/download',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

/**
 * 搜索呼入电话
 */
export function crmCallInNumberSearchAPI(data) {
  return request({
    url: 'crmCall/searchPhone',
    method: 'post',
    data: data
  })
}

/**
 * 查询客户模块电话号码
 */
export function crmCallQueryPhoneNumberAPI(data) {
  return request({
    url: 'crmCall/queryPhoneNumber',
    method: 'post',
    data: data
  })
}

/**
 * 录音转文字
 */
// export function crmCallAudioToStrAPI(data) {
//   return request({
//     url: 'crmCall/queryPhoneNumber',
//     method: 'post',
//     data: data
//   })
// }
