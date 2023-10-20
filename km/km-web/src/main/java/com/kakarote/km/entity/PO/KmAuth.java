package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文档文件夹权限表
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_auth")
@ApiModel(value = "KmAuth对象", description = "文档文件夹权限表")
public class KmAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "auth_id", type = IdType.ASSIGN_ID)
    private Long authId;

    @ApiModelProperty(value = "是否公开 0 私有 1 公开")
    private Integer isOpen;

    @ApiModelProperty(value = "公开权限 2 均可编辑 3 均可见，不可编辑 4 管理员 5 可上传下载 6 可下载")
    private Integer openAuth;

    @ApiModelProperty(value = "是否手机端仅查看")
    private Integer isMobile;

    @ApiModelProperty(value = "创建人id")
    private Long createUserId;

}
