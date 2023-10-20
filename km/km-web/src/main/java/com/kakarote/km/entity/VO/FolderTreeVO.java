package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("树形图中文件夹信息")
public class FolderTreeVO {
    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty("文件夹名称")
    private String title;

    @ApiModelProperty("权限id")
    private Long authId;

    @ApiModelProperty("父级文件夹id")
    private Long parentId;

    @ApiModelProperty("知识库id")
    private Long libraryId;

    @ApiModelProperty("子文件夹列表")
    private List<FolderTreeVO> childList;

    @ApiModelProperty("文件列表")
    private List<DocumentTreeVO> kmDocumentList;
}
