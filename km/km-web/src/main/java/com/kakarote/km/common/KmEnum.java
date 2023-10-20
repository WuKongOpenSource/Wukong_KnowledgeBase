package com.kakarote.km.common;

public enum KmEnum {
    /**
     * 知识库模块权限枚举
     */
    LIBRARY_CREATE_ROLE(1), LIBRARY_MANAGE_ROLE(2), LIBRARY_READ_ROLE(3),
    OPEN_EDIT_AUTH(2), OPEN_READ_AUTH(3),
    PRIVATE_MANAGE_AUTH(1), PRIVATE_EDIT_AUTH(2), PRIVATE_READ_AUTH(3);


    private Integer value;

    KmEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
