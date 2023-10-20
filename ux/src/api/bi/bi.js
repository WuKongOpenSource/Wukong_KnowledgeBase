import request from '@/utils/request'

/**
 * 业绩目标完成情况
 * @param {*} data
 */
export function biAchievementStatisticsAPI(data) {
  return request({
    url: 'biAchievement/taskCompleteStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biAchievementStatisticsExportAPI(data) {
  return request({
    url: 'biAchievement/taskCompleteStatisticsExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品销售情况统计
 * @param {*} data
 */
export function biProductStatisticsAPI(data) {
  return request({
    url: 'biProduct/productStatistics',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biProductStatisticsExportAPI(data) {
  return request({
    url: 'biProduct/productStatisticsExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 销售漏斗
 * @param {*} data
 */
export function biBusinessFunnelAPI(data) {
  return request({
    url: 'biFunnel/sellFunnel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 回访合同总数
 * @param {*} data
 */
export function biContractSatisfactionTableAPI(data) {
  return request({
    url: 'crmBiSearch/employeesSatisfactionTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 回访合同总数
 * @param {*} data
 */
export function biProductSatisfactionTableAPI(data) {
  return request({
    url: 'crmBiSearch/productSatisfactionTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 合同详情列表
 * @param {*} data
 */
export function biContractDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchContractPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 回访合同数详情列表
 * @param {*} data
 */
export function biVisitContractDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/employeesSatisfactionTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
