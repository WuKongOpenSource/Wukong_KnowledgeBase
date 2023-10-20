package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("树形图主页信息")
public class TreeListVO {
    @ApiModelProperty("root标识顶级目录")
    private String title;

    @ApiModelProperty("当前人操作权限")
    private Integer currentUserAuth;

    @ApiModelProperty("文件夹列表")
    private List<FolderTreeVO> childList;

    @ApiModelProperty("文件列表")
    private List<DocumentTreeVO> kmDocumentList;
}
