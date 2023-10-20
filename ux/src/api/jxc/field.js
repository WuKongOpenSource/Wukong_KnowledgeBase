/**
 * Create by yxk at 2020/5/29 0029
 */
import request from '@/utils/request'

/**
 * 查询类型下的字段
 */
export function jxcFieldQueryByLabelAPI(data) {
  return request({
    url: `jxcField/queryFieldByLabel/${data.label}`,
    method: 'post'
  })
}

export function jxcFieldQueryByFieldName(data) {
  return request({
    url: `jxcField/${data.label}`,
    method: 'post',
    data: data
  })
}

/**
 * 修改类型下的字段
 */
export function jxcFieldEditAPI(data) {
  return request({
    url: 'jxcField/editField',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询列表表头
 */
export function jxcFieldQueryListHeadAPI(data) {
  return request({
    url: `jxcField/queryListHead/${data.label}`,
    method: 'post'
  })
}

/**
 * 查询列表分页数据
 */
export function jxcFieldQueryPageListAPI(data) {
  return request({
    url: `/jxcField/queryPageList/${data.label}`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询列表数据(不分页)
 */
export function jxcFieldQueryListAPI(data) {
  return request({
    url: `/jxcField/queryList/${data.label}`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询高级筛选字段
 */
export function jxcFieldSceneQueryFieldAPI(data) {
  return request({
    url: `jxcField/sceneQueryField/${data.label}`,
    method: 'post'
  })
}

/**
 * 编辑时查询字段
 */
export function jxcFieldQueryFieldAPI(data) {
  return request({
    url: `jxcField/queryField/${data.label}`,
    method: 'post',
    data: data
  })
}

/**
 * 编号规则唯一验重
 */
export function jxcFieldOderNumberOnlyAPI(data) {
  return request({
    url: `jxcField/fieldOderNumberOnly/${data.label}`,
    method: 'post',
    data: data
  })
}

/**
 * 查询表头字段显示隐藏
 */
export function jxcFieldQueryConfigAPI(data) {
  return request({
    url: 'jxcField/queryFieldConfig',
    method: 'post',
    data: data
  })
}

/**
 * 修改表头字段显示隐藏
 */
export function jxcFieldUpdateConfigAPI(data) {
  return request({
    url: 'jxcField/setFieldConfig',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询详情基本信息
 */
export function jxcFieldDetailInformationAPI(data) {
  return request({
    url: `jxcField/information/${data.label}`,
    method: 'post',
    data: data
  })
}

/**
 * 查询详情关联项表头
 */
export function jxcFieldDetailOtherFieldListAPI(data) {
  return request({
    url: `/jxcField/queryOtherListHead/${data.label}`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 设置表头字段宽度
 */
export function jxcFieldSetFieldStyleAPI(data) {
  return request({
    url: 'jxcField/setFieldStyle',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 转移
 */
export function jxcFieldTransferOwnerAPI(data) {
  return request({
    url: `jxcField/transfer/${data.label}`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 字段验证唯一
 */
export function jxcFieldOnlyAPI(data) {
  return request({
    url: `jxcField/fieldOnly/${data.label}`,
    method: 'post',
    data: data
  })
}

/**
 * 操作记录
 */
export function jxcFieldRecordListAPI(data) {
  return request({
    url: `jxcOperationRecord/queryList/${data.label}`,
    method: 'post',
    data: data
  })
}

export function crmCustomerPageListAPI(data) {
  return request({
    url: 'crmCustomer/queryPageList',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function crmFieldCustomerQueryListHeadAPI(data) {
  return request({
    url: `crmField/queryListHead/${data.label}`,
    method: 'post',
    data: data
  })
}

export function jxcFieldQueryContactsByIdAPI(data) {
  return request({
    url: `crmContacts/queryById/${data.contactsId}`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询单个字段
 */
export function jxcFieldFindFieldNameAPI(data) {
  return request({
    url: `jxcField/findFieldName/${data.label}`,
    method: 'post',
    data: data
  })
}

/**
 * 导出
 */
export function jxcFieldAllExportExcelAPI(data) {
  return request({
    url: `/jxcField/allExportExcel/${data.label}`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    responseType: 'blob'
  })
}

/**
 * 字段唯一验重
 */
export function jxcFieldCheckDoubleAPI(data) {
  return request({
    url: 'jxcField/checkDouble',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询某条数据是否已经被删除
 */
export function jxcDataIsDeleteAPI(data) {
  return request({
    url: `jxcCommon/isDeleted/${data.id}`,
    method: 'post',
    params: data
  })
}

