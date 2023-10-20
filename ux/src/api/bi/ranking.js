import request from '@/utils/request'

/**
 * 合同金额
 * @param {*} data
 * year 年
 * status 1销售（目标）2回款（目标）
 * user_id 员工ID
 * structure_id 部门ID
 */
export function biRankingContractAPI(data) {
  return request({
    url: 'biRanking/contractRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingContractExportAPI(data) {
  return request({
    url: 'biRanking/contractRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 回款金额排行
 * @param {*} data
 */
export function biRankingReceivablesAPI(data) {
  return request({
    url: 'biRanking/receivablesRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingReceivablesExportAPI(data) {
  return request({
    url: 'biRanking/receivablesRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 签约合同排行
 * @param {*} data
 */
export function biRankingSigningAPI(data) {
  return request({
    url: 'biRanking/contractCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingSigningExportAPI(data) {
  return request({
    url: 'biRanking/contractCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品销量排行
 * @param {*} data
 */
export function biRankingProductAPI(data) {
  return request({
    url: 'biRanking/productCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingProductExportAPI(data) {
  return request({
    url: 'biRanking/productCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 新增客户数排行
 * @param {*} data
 */
export function biRankingAddCustomerAPI(data) {
  return request({
    url: 'biRanking/customerCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingAddCustomerExportAPI(data) {
  return request({
    url: 'biRanking/customerCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 新增联系人数排行
 * @param {*} data
 */
export function biRankingAddContactsAPI(data) {
  return request({
    url: 'biRanking/contactsCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingAddContactsExportAPI(data) {
  return request({
    url: 'biRanking/contactsCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 跟进次数排行
 * @param {*} data
 */
export function biRankingRecordNunAPI(data) {
  return request({
    url: 'biRanking/recordCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingRecordNunExportAPI(data) {
  return request({
    url: 'biRanking/recordCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 跟进客户数排行
 * @param {*} data
 */
export function biRankingRecordCustomerAPI(data) {
  return request({
    url: 'biRanking/customerRecordCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingRecordCustomerExportAPI(data) {
  return request({
    url: 'biRanking/customerRecordCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 出差次数排行
 * @param {*} data
 */
export function biRankingExamineAPI(data) {
  return request({
    url: 'biRanking/travelCountRanKing',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biRankingExamineExportAPI(data) {
  return request({
    url: 'biRanking/travelCountRanKingExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 联系人详情列表
 * @param {*} data
 */
export function biContactDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchContactsPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

