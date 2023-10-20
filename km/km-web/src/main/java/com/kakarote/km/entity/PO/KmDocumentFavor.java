package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文档点赞表
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_document_favor")
@ApiModel(value = "KmDocumentFavor对象", description = "文档点赞表")
public class KmDocumentFavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "favor_id", type = IdType.ASSIGN_ID)
    private Long favorId;

    private Long documentId;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
