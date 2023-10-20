import request from '@/utils/request'

/**
 * 保存外漏高级筛选条件
 * @param {*} data
 */
export function jxcSearchExpertSaveAPI(data) {
  console.log(data)
  return request({
    url: 'jxcSearchExpert/save',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
