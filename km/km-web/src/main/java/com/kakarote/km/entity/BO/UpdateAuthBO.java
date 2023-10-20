package com.kakarote.km.entity.BO;

import com.kakarote.km.entity.PO.KmAuth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("修改权限参数")
public class UpdateAuthBO {
    @ApiModelProperty("权限id")
    private KmAuth auth;

    @ApiModelProperty("员工权限列表")
    private List<AuthUserBO> authUserList;

    @ApiModelProperty("创建人id")
    private Long createUserId;
}
