package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("树形图中文件信息")
public class DocumentTreeVO {
    @ApiModelProperty("文档id")
    private Long documentId;

    @ApiModelProperty("文档标题")
    private String title;

    @ApiModelProperty("3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty("权限id")
    private Long authId;

    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty("知识库id")
    private Long libraryId;

    @ApiModelProperty("子文件列表")
    private List<DocumentTreeVO> childList;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("文件类型")
    private String fileType;
}
