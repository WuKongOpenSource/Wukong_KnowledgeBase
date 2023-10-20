import request from '@/utils/request'

/**
 * 员工客户总量分析
 */
export function biCustomerTotalAPI(data) {
  return request({
    url: 'biCustomer/totalCustomerStats',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerTotalListAPI(data) {
  return request({
    url: 'biCustomer/totalCustomerTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerTotalListExportAPI(data) {
  return request({
    url: 'biCustomer/totalCustomerTableExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 员工客户跟进次数分析
 * @param {*} data
 */
export function biCustomerRecordTimesAPI(data) {
  return request({
    url: 'biCustomer/customerRecordStats',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 员工客户跟进次数分析 具体员工列表
 * @param {*} data
 */
export function biCustomerRecordListAPI(data) {
  return request({
    url: 'biCustomer/customerRecordInfo',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerRecordListExportAPI(data) {
  return request({
    url: 'biCustomer/customerRecordInfoExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 员工跟进方式分析
 * @param {*} data
 */
export function biCustomerRecordModeAPI(data) {
  return request({
    url: 'biCustomer/customerRecodCategoryStats',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerRecordModeExportAPI(data) {
  return request({
    url: 'biCustomer/customerRecodCategoryStatsExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 客户转化率分析具体数据
 * @param {*} data
 */
export function biCustomerConversionInfoAPI(data) {
  return request({
    url: 'biCustomer/customerConversionInfo',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 客户转化率分析
 * @param {*} data
 */
export function biCustomerConversionAPI(data) {
  return request({
    url: 'biCustomer/customerConversionStats',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 公海客户分析
 * @param {*} data
 */
export function biCustomerPoolAPI(data) {
  return request({
    url: 'biCustomer/poolStats',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 公海客户分析
 * @param {*} data
 */
export function biCustomerPoolListAPI(data) {
  return request({
    url: 'biCustomer/poolTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerPoolListExportAPI(data) {
  return request({
    url: 'biCustomer/poolTableExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 员工客户成交周期
 * @param {*} data
 */
export function biCustomerUserCycleAPI(data) {
  return request({
    url: 'biCustomer/employeeCycle',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerUserCycleExportAPI(data) {
  return request({
    url: 'biCustomer/employeeCycleInfoExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function employeeCycleInfoAPI(data) {
  return request({
    url: 'biCustomer/employeeCycleInfo',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 地区成交周期
 * @param {*} data
 */
export function biCustomerAddressCycleAPI(data) {
  return request({
    url: 'biCustomer/districtCycle',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerAddressCycleExportAPI(data) {
  return request({
    url: 'biCustomer/districtCycleExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 产品成交周期
 * @param {*} data
 */
export function biCustomerProductCycleAPI(data) {
  return request({
    url: 'biCustomer/productCycle',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerProductCycleExportAPI(data) {
  return request({
    url: 'biCustomer/productCycleExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 员工客户满意度分析
 * @param {*} data
 */
export function biCustomerSatisfactionTableAPI(data) {
  return request({
    url: 'biCustomer/customerSatisfactionTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerSatisfactionTableExportAPI(data) {
  return request({
    url: 'biCustomer/customerSatisfactionExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 员工客户满意度分析
 * @param {*} data
 */
export function biCustomerProductSatisfactionTableAPI(data) {
  return request({
    url: 'biCustomer/productSatisfactionTable',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function biCustomerProductSatisfactionExportAPI(data) {
  return request({
    url: 'biCustomer/productSatisfactionExport',
    method: 'post',
    data: data,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 进入公海数
 */
export function crmCustomerEntryToSeasAPI(data) {
  return request({
    url: 'crmBiSearch/searchPoolCustomer',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
/**
 * 客户详情列表
 * @param {*} data
 * @returns
 */
export function biCustomerDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchCustomerPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
/**
 * 公海详情列表
 * @param {*} data
 * @returns
 */
export function biPoolDetailListAPI(data) {
  return request({
    url: 'crmBiSearch/searchPoolCustomerPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 跟进客户数
 * @param {*} data
 * @returns
 */
export function biCustomerFollowListAPI(data) {
  return request({
    url: 'crmBiSearch/queryCustomerRecordList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

