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
 * 知识库操作记录（最近使用）
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_action_record")
@ApiModel(value = "KmActionRecord对象", description = "知识库操作记录（最近使用）")
public class KmActionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.ASSIGN_ID)
    private Long recordId;

    @ApiModelProperty(value = "1 浏览 2 删除")
    private Integer status;

    @ApiModelProperty(value = "1 知识库 2 文件夹 3 文档 4 文件 ")
    private Integer type;

    private Long typeId;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
