/**
 * Create by yxk at 2020/6/12
 */
import request from '@/utils/request'

/**
 * 仓库新建/编辑
 */
export function jxcWarehouseAddOrUpdateAPI(data, isUpdate = false) {
  return request({
    url: 'jxcWarehouse/addOrUpdate' + (isUpdate ? '/update' : '/add'),
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 仓库删除
 */
export function jxcWarehouseDeleteByIdsAPI(data) {
  return request({
    url: 'jxcWarehouse/deleteByIds',
    method: 'post',
    data: data
  })
}

/**
 * 分页查询仓库
 */
export function jxcWarehouseQueryPageListAPI(data) {
  return request({
    url: 'jxcWarehouse/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询已启用的仓库
 */
export function jxcWarehouseQueryListAPI(data) {
  return request({
    url: 'jxcWarehouse/queryList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 仓库停用/启用
 */
export function jxcWarehouseSetTurnByIdsAPI(data) {
  return request({
    url: 'jxcWarehouse/setTrunByIds',
    method: 'post',
    data: data
  })
}

/**
 * 仓库处理导出
 */
export function jxcWarehouseAllExportExcelAPI(data) {
  return request({
    url: '/jxcWarehouse/allExportExcel',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}
