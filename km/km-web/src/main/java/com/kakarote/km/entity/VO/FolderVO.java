package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("文件夹信息")
public class FolderVO {
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

    @ApiModelProperty(value = "收藏人数")
    private Integer collectStatus;
}
