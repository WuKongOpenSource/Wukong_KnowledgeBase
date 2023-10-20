/**
 * Created by yxk at 2020/6/5
 */
import request from '@/utils/request'

/**
 * 查询场景
 */
export function jxcSceneQueryAPI(data) {
  return request({
    url: 'jxcScene/queryScene',
    method: 'post',
    data: data
  })
}

/**
 * 新增场景
 */
export function jxcSceneAddAPI(data) {
  return request({
    url: 'jxcScene/addScene',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 修改场景
 */
export function jxcSceneUpdateAPI(data) {
  return request({
    url: 'jxcScene/updateScene',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 查询场景搜索字段
 */
export function jxcSceneQueryFieldAPI(data) {
  return request({
    url: 'jxcScene/queryField',
    method: 'post',
    data: data
  })
}

/**
 * 查询场景配置
 */
export function jxcSceneQueryConfigAPI(data) {
  return request({
    url: 'jxcScene/querySceneConfig',
    method: 'post',
    data: data
  })
}

/**
 * 修改场景显示隐藏
 */
export function jxcSceneUpdateConfigAPI(data) {
  return request({
    url: 'jxcScene/sceneConfig',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * 场景删除
 */
export function jxcSceneDeleteByIdAPI(data) {
  return request({
    url: 'jxcScene/deleteScene',
    method: 'post',
    data: data
  })
}

/**
 * 获取单个场景值
 */
export function jxcSceneQueryByIdAPI(data) {
  return request({
    url: `jxcScene/${data.sceneId}`,
    method: 'post',
    data: data
  })
}

/**
 * 设置为默认场景
 */
export function jxcSceneSetDefaultAPI(data) {
  return request({
    url: 'jxcScene/setDefaultScene',
    method: 'post',
    data: data
  })
}
