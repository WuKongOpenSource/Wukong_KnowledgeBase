/**
 * Create by yxk at 2020/6/9
 */
import request from '@/utils/request'

/**
 * 添加附件
 */
export function jxcFileAddOrUpdateAPI(data) {
  return request({
    url: 'jxcFile/addOrUpdate',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询附件列表
 */
export function jxcFileQueryListAPI(data) {
  return request({
    url: `jxcFile/queryFileList/${data.label}`,
    method: 'post',
    data: data
  })
}
