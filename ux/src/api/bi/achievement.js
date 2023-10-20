import request from '@/utils/request'

/**
 * 合同数量分析/金额分析/回款金额分析
 * count：合同数量分析；money：金额分析；back：回款金额分析
 */
export function biAchievementAnalysisAPI(data) {
  const type = data.tableType
  if (!type) return Promise.reject()
  delete data.tableType
  const url = {
    count: 'biEmployee/contractNumStats',
    money: 'biEmployee/contractMoneyStats',
    back: 'biEmployee/receivablesMoneyStats'
  }[type]
  if (!url) return Promise.reject()
  return request({
    url,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biAchievementAnalysisExportAPI(data) {
  const type = data.tableType
  if (!type) return Promise.reject()
  delete data.tableType
  const url = {
    count: 'biEmployee/contractNumStatsExport',
    money: 'biEmployee/contractMoneyStatsExport',
    back: 'biEmployee/receivablesMoneyStatsExport'
  }[type]
  if (!url) return Promise.reject()
  return request({
    url,
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 合同汇总表
 * @param {*} data
 */
export function biAchievementSummaryAPI(data) {
  return request({
    url: 'biEmployee/totalContract',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biAchievementSummaryExportAPI(data) {
  return request({
    url: 'biEmployee/totalContractExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 发票统计分析
 * @param {*} data
 */
export function biAchievementInvoiceAPI(data) {
  return request({
    url: 'biEmployee/invoiceStats',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 发票统计分析导出
 * @param {*} data
 */
export function biAchievementInvoiceExportAPI(data) {
  return request({
    url: 'biEmployee/invoiceStatsExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 发票详情列表
 * @param {*} data
 */
export function biInvoiceDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchInvoicePageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 回款详情列表
 * @param {*} data
 */
export function biReceivablesDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchReceivablesPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 金额比例明细表
 * @param {*} data
 */
export function biEmployeeMoneyRatioDetailAPI(data) {
  return request({
    url: 'biEmployee/moneyRatioDetail',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 金额比例明细导出
 * @param {*} data
 */
export function biEmployeeMoneyRatioDetailExportAPI(data) {
  return request({
    url: 'biEmployee/moneyRatioDetailExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 金额比例汇总表
 * @param {*} data
 */
export function biEmployeeMoneyRatioTotalAPI(data) {
  return request({
    url: 'biEmployee/moneyRatioTotal',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 金额比例汇总导出
 * @param {*} data
 */
export function biEmployeeMoneyRatioTotalExportAPI(data) {
  return request({
    url: 'biEmployee/moneyRatioTotalExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
