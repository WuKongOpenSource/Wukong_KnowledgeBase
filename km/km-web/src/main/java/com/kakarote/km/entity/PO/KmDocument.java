package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

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
@TableName("wk_km_document")
@ApiModel(value = "KmDocument对象", description = "")
public class KmDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "document_id", type = IdType.ASSIGN_ID)
    private Long documentId;

    @ApiModelProperty(value = "文档标题")
    private String title;

    private String content;

    @ApiModelProperty(value = "3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    private Long parentId;

    @ApiModelProperty(value = "-1 删除 0 草稿 1 正常 2 模板")
    private Integer status;

    private Long libraryId;

    @ApiModelProperty(value = "文件夹id")
    private Long folderId;

    private Long authId;

    @ApiModelProperty(value = "标签id")
    private String labelIds;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long deleteUserId;

    private LocalDateTime deleteTime;

    @ApiModelProperty(value = "语言包map")
    @TableField(exist = false)
    private Map<String, String> languageKeyMap;

    @ApiModelProperty(value = "AI解析状态（1.解析中，2.解析完成）")
    private Integer aiSyncStatus;


}
