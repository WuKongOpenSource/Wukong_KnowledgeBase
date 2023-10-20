package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyq
 */
@Data
@ApiModel("操作记录信息")
public class RecordVO {
    @ApiModelProperty("操作记录id")
    private Long recordId;

    @ApiModelProperty(value = "1 浏览 2 删除")
    private Integer status;

    @ApiModelProperty(value = "2 文件夹 3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("数据id")
    private Long typeId;

    @ApiModelProperty("创建人")
    private Long createUserId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;


    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("知识库名称")
    private String libraryName;

    @ApiModelProperty("知识库id")
    private Long libraryId;

    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty("文档id")
    private Long documentId;

    @ApiModelProperty("文件大小")
    private Long fileSize;
}
