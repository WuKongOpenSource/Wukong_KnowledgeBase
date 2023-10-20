package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 知识库
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_knowledge_library")
@ApiModel(value = "KmKnowledgeLibrary对象", description = "知识库")
public class KmKnowledgeLibrary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "library_id", type = IdType.ASSIGN_ID)
    private Long libraryId;

    @ApiModelProperty(value = "知识库名称")
    private String name;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "是否公开 1 公开 0 私有")
    private Integer isOpen;

    @ApiModelProperty(value = "-1 删除 1 正常 2 模板")
    private Integer status;

    @ApiModelProperty(value = "0 否 1 是")
    private Integer isSystemCover;

    @ApiModelProperty(value = "知识库封面")
    private String coverUrl;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private Long deleteUserId;

    private LocalDateTime deleteTime;

    @TableField(exist = false)
    private List<KmDocument> documentList;

    @ApiModelProperty(value = "语言包map")
    @TableField(exist = false)
    private Map<String, String> languageKeyMap;

    @ApiModelProperty(value = "是否开启AI服务，1 关 2 开 默认1")
    private Integer aiServiceSwitch;
}
