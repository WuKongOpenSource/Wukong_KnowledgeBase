package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("项目成员角色信息")
public class LibraryUserBO {
    @ApiModelProperty("员工id")
    private Long userId;

    @ApiModelProperty("1 创建人 2 管理员 3 成员")
    private Integer role;

    @ApiModelProperty("是否公开 1 公开 2 私有")
    private Integer isOpen;
}
