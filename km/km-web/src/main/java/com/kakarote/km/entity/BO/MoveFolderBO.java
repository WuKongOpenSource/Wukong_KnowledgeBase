package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("移动文件夹参数")
public class MoveFolderBO {
    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty("目标id")
    private Long targetId;

    @ApiModelProperty("知识库id")
    private Long libraryId;


}
