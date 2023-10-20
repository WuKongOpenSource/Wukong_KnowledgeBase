import request from '@/utils/request'

/**
 *  添加知识库分组
 * @param {*} data
 */
export function kmGroupAddGroupAPI(data) {
  return request({
    url: `/kmGroup/addGroup`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

/**
 *  移除分组
 * @param {*} data
 */
export function kmGroupRemoveGroupByIdAPI(data) {
  return request({
    url: `/kmGroup/removeGroupById`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

/**
 *  查询分组
 * @param {*} data
 */
export function kmGroupSearchGroupListAPI(data) {
  return request({
    url: `/kmGroup/searchGroupList`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

/**
 *  更新知识库分组
 * @param {*} data
 */
export function kmGroupUpdateGroupAPI(data) {
  return request({
    url: `/kmGroup/updateGroup`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

/**
 *  批量更新知识库分组
 * @param {*} data
 */
export function kmGroupUpdateGroupBatchAPI(data) {
  return request({
    url: `/kmGroup/updateGroupBatch`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

/**
 *  移动-增加知识库到分组
 * @param {*} data
 */
export function kmGroupManagementMoveToGroupAPI(data) {
  return request({
    url: `/kmGroupManagement/moveToGroup`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

/**
 *  移除知识库分组
 * @param {*} data
 */
export function kmGroupManagementRemoveToGroupAPI(data) {
  return request({
    url: `/kmGroupManagement/removeToGroup`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

/**
 *  查询当前分组下的知识库
 * @param {*} data
 */
export function kmGroupManagementSearchLibraryGroupListAPI(data) {
  return request({
    url: `/kmGroupManagement/searchLibraryGroupList`,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
