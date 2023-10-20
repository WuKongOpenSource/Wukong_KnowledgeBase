package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("添加权限参数")
public class AddAuthBO {
    @ApiModelProperty("0 私有 1 公开")
    private Integer isOpen;

    @ApiModelProperty("公开权限 1 均可编辑 2 均可见，不可编辑")
    private Integer openAuth;

    @ApiModelProperty("员工权限列表")
    private List<AuthUserBO> authUserList;
}
