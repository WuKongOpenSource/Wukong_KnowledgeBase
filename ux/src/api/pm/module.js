/**
 * 关联应用管理
 * @author yxk
 * @date 2022/9/13
 */
import request from '@/utils/request'

/**
 * 查询全部项目
 */
export function adminConfigQueryModulePageListAPI(data) {
  return request({
    url: 'adminConfig/queryModulePageList',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=utf-8;'
    },
    data
  })
}
