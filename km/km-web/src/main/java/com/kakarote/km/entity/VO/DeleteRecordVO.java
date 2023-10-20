package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyq
 */
@Data
@ApiModel("删除记录信息")
public class DeleteRecordVO {
    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty(value = "2 文件夹 3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty("知识库id")
    private Long libraryId;

    @ApiModelProperty("知识库名称")
    private String libraryName;

    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty("删除时间")
    private LocalDateTime deleteTime;

    @ApiModelProperty("文档id")
    private Long documentId;

    @ApiModelProperty("文件大小")
    private Long fileSize;
}
