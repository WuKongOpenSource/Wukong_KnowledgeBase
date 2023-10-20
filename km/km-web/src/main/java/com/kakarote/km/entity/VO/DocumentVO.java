package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyq
 */
@Data
@ApiModel("知识库内文档信息")
public class DocumentVO {
    @ApiModelProperty(value = "文件id")
    private Long documentId;

    @ApiModelProperty(value = "文档标题")
    private String title;

    @ApiModelProperty(value = "3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "-1 删除 0 草稿 1 正常 2 模板")
    private Integer status;

    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "文件夹id")
    private Long folderId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "收藏人数")
    private Integer collectStatus;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "AI解析状态（1.解析中，2.解析完成）")
    private Integer aiSyncStatus;
}
