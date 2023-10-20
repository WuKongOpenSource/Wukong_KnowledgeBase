package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyq
 */
@Data
@ApiModel("收藏信息")
public class CollectListVO {
    @ApiModelProperty(value = "收藏id")
    private Long collectId;

    @ApiModelProperty(value = "1 知识库 2 文件夹 3 文件")
    private Integer type;

    @ApiModelProperty(value = "收藏对象id")
    private Long typeId;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人id")
    private Long createUserId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "知识库名称")
    private String libraryName;

    @ApiModelProperty(value = "收藏状态")
    private Long collectStatus;

    @ApiModelProperty(value = "文件夹id")
    private Long folderId;

    @ApiModelProperty(value = "文件id")
    private Long documentId;

    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
}
