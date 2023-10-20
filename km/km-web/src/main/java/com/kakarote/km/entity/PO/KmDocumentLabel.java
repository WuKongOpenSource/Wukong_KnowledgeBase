package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文档标签表
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_document_label")
@ApiModel(value = "KmDocumentLabel对象", description = "文档标签表")
public class KmDocumentLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "label_id", type = IdType.ASSIGN_ID)
    private Long labelId;

    private String name;

    private String color;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
