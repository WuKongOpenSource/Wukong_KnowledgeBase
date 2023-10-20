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
 * 知识库收藏表
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_collect")
@ApiModel(value = "KmCollect对象", description = "知识库收藏表")
public class KmCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "collect_id", type = IdType.ASSIGN_ID)
    private Long collectId;

    @ApiModelProperty(value = "1 知识库 2 文件夹 3 文件")
    private Integer type;

    private Long typeId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;
}
