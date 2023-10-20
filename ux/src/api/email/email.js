import request from '@/utils/request'

/**
 * 邮件发送
 * @param {*} data
 */
export function emailBindingAPI(data) {
  return request({
    url: 'emailAccount/validationAccount',
    method: 'post'
  })
}

/**
 * 邮件列表
 * @param {*} data
 */
export function emailListsAPI(data) {
  return request({
    url: 'emailRecord/queryEmailList',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

// /**
//  * 邮件列表 crm 模块详情下用
//  * @param {*} data
//  */
// export function emailAccountListsAPI(data) {
//   return request({
//     url: 'emailRecord/accountList',
//     method: 'post',
//     data,
//     headers: {
//       'Content-Type': 'application/json;charset=UTF-8'
//     }
//   })
// }

// /**
//  * 验证邮箱是否已存在
//  * @param {*} data
//  */
// export function isExistenceEmailAPI(data) {
//   return request({
//     url: 'emailAccount/validationAccount',
//     method: 'post',
//     data
//   })
// }

/**
 * 邮件发送
 * @param {*} data
 */
export function emailSendAPI(data) {
  return request({
    url: 'emailRecord/sendEmail',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 存稿箱
 * @param {*} data
 */
export function saveDraftBoxAPI(data) {
  return request({
    url: 'emailRecord/saveDraftsBox',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 数量查询
 * @param {*} data
 */
export function emailNumAPI(data) {
  return request({
    url: 'emailRecord/queryEmailNumber',
    method: 'post',
    data
  })
}

/**
 * 邮件验证
 * @param {*} data
 */
export function emailValidAPI(data) {
  return request({
    url: 'emailAccount/saveOrUpdateEmailAccount',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 操作邮件状态 emailAccount/queryAccountById
 * @param {*} data
 */
export function emailStateUpdateAPI(data) {
  return request({
    url: 'emailRecord/changeEmailState',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 邮箱模糊查询
 * @param {*} data
 */
export function emailAccountSearchEmailAPI(data) {
  return request({
    url: 'emailAccount/searchEmail',
    method: 'post',
    data
  })
}

/**
 * 同步数据
 * @param {*} data
 */
export function emailRecordReceivingEmailAPI() {
  return request({
    url: 'emailRecord/receivingEmail',
    method: 'post'
  })
}

/**
 * 解除绑定
 * @param {*} data
 */
export function emailUnbundleAccountAPI(data) {
  return request({
    url: 'emailAccount/unbundleEmailAccount',
    method: 'post',
    data
  })
}

/**
 * 设置账号信息
 * @param {*} data
 */
export function emailSetAccountAPI(data) {
  return request({
    url: 'emailAccount/setEmailSetting',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 逻辑删除
 * @param {*} data
 */
export function emailRecordLogicDeleteAPI(data) {
  return request({
    url: 'emailRecord/logicDelete',
    method: 'post',
    data
  })
}

/**
 * 彻底删除
 * @param {*} data
 */
export function emailRecordDeleteByEmailIdAPI(data) {
  return request({
    url: 'emailRecord/deleteByEmailId',
    method: 'post',
    data
  })
}

/**
 * 移动邮件
 * @param {*} data
 */
export function emailRecordShiftEmailAPI(data) {
  return request({
    url: 'emailRecord/shiftEmail',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 移动邮件 emailRecord/getEmailFileByBatchId
 * @param {*} data
 */
export function emailAccountQueryLatelyAPI(data) {
  return request({
    url: 'emailAccount/queryLately',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询邮件附件
 * @param {*} data
 */
export function emailGetEmailFileByBatchIdAPI(data) {
  return request({
    url: 'emailRecord/getEmailFileByBatchId',
    method: 'post',
    data
  })
}

/**
 * 查询邮件附件 emailRecord/downFile
 * @param {*} data
 */
export function emailGetDownFileAPI(data) {
  return request({
    url: 'emailRecord/downFile',
    method: 'post',
    responseType: 'blob',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 上传文件（仅适用于富文本）
 * @param {*} data
 */
export function emailRecordUploadAPI(data) {
  var param = new FormData()
  Object.keys(data).forEach(key => {
    param.append(key, data[key])
  })
  return request({
    url: 'emailRecord/upload',
    method: 'post',
    data: param,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

/**
 * 上传文件（仅适用于富文本）URL
 * @param {*} data
 */
export const emailRecordUploadUrl = process.env.VUE_APP_BASE_API + 'emailRecord/upload'

/**
 * 附件删除
 * @param {*} data
 */
export function emailRecordDeleteFileAPI(data) {
  return request({
    url: 'emailRecord/deleteFile',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 列表
 * @param {*} data
 */
export function emailBindingListAPI(data) {
  return request({
    url: 'emailAccount/accountList',
    method: 'post'
  })
}

/**
 * 邮箱切换
 * @param {*} data
 */
export function emailToggleAccountAPI(data) {
  return request({
    url: 'emailAccount/switchSelectEmail',
    method: 'post',
    data
  })
}

/**
 * AI自动生成内容
 * @param {*} data
 */
export function emailAIGenerateContentAPI(data) {
  return request({
    url: 'adminBlogPosts/generateEmailText',
    method: 'post',
    data
  })
}

