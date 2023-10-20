package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wyq
 */
@Data
@ApiModel("成员权限列表信息")
public class AuthUserVO implements Serializable {
    @ApiModelProperty("成员id")
    private Long userId;

    @ApiModelProperty("权限id")
    private Long authId;

    @ApiModelProperty("私有权限 1 所有权限 2 编辑权限 3只读权限")
    private Integer auth;

    @ApiModelProperty("成员姓名")
    private String realname;

    @ApiModelProperty("成员头像")
    private String img;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("是否支持手机端 0 否 1是")
    private Integer isMobile;

    @ApiModelProperty("父级部门id")
    private Long parentDeptId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
