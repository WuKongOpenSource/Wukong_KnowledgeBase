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
 * 知识库文件夹
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_folder")
@ApiModel(value = "KmFolder对象", description = "知识库文件夹")
public class KmFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "folder_id", type = IdType.ASSIGN_ID)
    private Long folderId;

    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    private String title;

    @ApiModelProperty(value = "-1 删除 1 正常")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Long authId;

    private Long deleteUserId;

    private LocalDateTime deleteTime;

}
