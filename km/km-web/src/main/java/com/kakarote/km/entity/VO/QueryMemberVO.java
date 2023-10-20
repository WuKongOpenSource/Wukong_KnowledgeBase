package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("团队成员信息")
public class QueryMemberVO {

    @ApiModelProperty(value = "1 创建人 2 管理员 3 成员(可上传下载) 4可编辑 5 可下载 6仅预览")
    private Integer role;

    @ApiModelProperty("员工id")
    private Long userId;

    @ApiModelProperty("员工姓名")
    private String realname;

    @ApiModelProperty("员工头像")
    private String img;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("父部门id")
    private Long parentDeptId;

    @ApiModelProperty("0 用户 1部门")
    private Integer type;

    @ApiModelProperty(value = "是否支持手机端查看")
    private Integer isMobile;

}
