package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel("文档权限信息")
public class DocumentAuthVO implements Serializable {
    @ApiModelProperty("权限id")
    private Long authId;

    @ApiModelProperty("是否公开 0 私有 1 公开")
    private Integer isOpen;

    @ApiModelProperty("公开权限 2 均可编辑 3 均可见，不可编辑")
    private Integer openAuth;

    @ApiModelProperty("是否手机端仅查看 0 否 1 是")
    private Integer isMobile;

    @ApiModelProperty("当前人权限")
    private Integer currentUserAuth;

    @ApiModelProperty("成员权限列表")
    private List<AuthUserVO> authUserList;
}
