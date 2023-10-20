package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_auth_user")
@ApiModel(value = "KmAuthUser对象", description = "")
public class KmAuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "r_id", type = IdType.ASSIGN_ID)
    private Long rId;

    private Long authId;

    private Long userId;

    private Long deptId;

    @ApiModelProperty(value = "私有权限 1 所有权限 2 编辑权限 3仅预览 4可下载 5可上传下载")
    private Integer auth;

    @ApiModelProperty(value = "是否是支持手机端 0 否 1 是")
    private Integer isMobile;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

}
