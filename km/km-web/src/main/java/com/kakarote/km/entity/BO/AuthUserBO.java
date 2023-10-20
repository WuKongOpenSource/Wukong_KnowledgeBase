package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("成员权限参数")
public class AuthUserBO {
    @ApiModelProperty("员工id")
    private Long userId;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty(value = "私有权限 1 所有权限 2 编辑权限 3只读权限 4可上传下载 5可下载")
    private Integer auth;

    @ApiModelProperty(value = "是否是支持手机端 0 否 1 是")
    private Integer isMobile;
}
