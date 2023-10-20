package com.kakarote.km.common;

import java.util.Objects;

/**
 * @author zyh
 * 分组类型
 */

public enum KmGroupTypeEnum {
    //分组类型
    ALL(1, "全部项目"),
    NONE(2, "未分组"),
    CUSTOM(3, "自定义"),
    ;

    private KmGroupTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static KmGroupTypeEnum enumByType(Integer type) {
        for (KmGroupTypeEnum moduleTypeEnum : values()) {
            if (Objects.equals(type, moduleTypeEnum.getType())) {
                return moduleTypeEnum;
            }
        }
        return null;
    }

    public static KmGroupTypeEnum enumByDesc(String desc) {
        for (KmGroupTypeEnum moduleTypeEnum : values()) {
            if (Objects.equals(desc, moduleTypeEnum.getDesc())) {
                return moduleTypeEnum;
            }
        }
        return null;
    }

}
