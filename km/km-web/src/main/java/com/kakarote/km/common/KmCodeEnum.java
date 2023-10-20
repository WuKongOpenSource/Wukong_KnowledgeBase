package com.kakarote.km.common;

import com.kakarote.core.common.ResultCode;

/**
 * @author wyq
 */
public enum KmCodeEnum implements ResultCode {

    KM_AUTH_ERROR(8001, "无权访问"),
    KM_DATA_EXIST_ERROR(8002, "该数据不存在或不可操作"),
    KM_PARAMS_ERROR(8003, "参数错误"),
    KM_OWNER_REMOVE_MEMBER_ERROR(8004, "拥有者不能移除团队成员"),
    KM_SEARCH_ERROR(8005, "请输入搜索条件"),
    KM_COLLECT_ERROR(8006, "请勿重复收藏"),
    KM_SHARE_EXIST_ERROR(8007, "分享链接不存在"),
    KM_SHARE_INVALID_ERROR(8008, "分享链接已失效"),
    KM_SHARING_ERROR(8009, "该文档正在分享中"),
    KM_MOVE_FOLDER_ERROR(8010, "不能移动到自己的子文件下面"),
    KM_SYNC_AI_ERROR(8011, "同步数据到AI错误，本地文件复制异常"),
    KM_SYNC_AI_CLOSE_STREAM_ERROR(8012, "同步数据到AI关闭输入流异常"),
    KM_SYNC_AI_REQUEST_ERROR(8013, "同步数据到AI错误，请求响应异常");

    KmCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
