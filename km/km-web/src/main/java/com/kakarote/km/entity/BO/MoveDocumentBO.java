package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("移动文档参数")
public class MoveDocumentBO {
    @ApiModelProperty("2 文件夹 3 文档")
    private Integer type;

    @ApiModelProperty("文档id")
    private Long documentId;

    @ApiModelProperty("目标id")
    private Long targetId;

    @ApiModelProperty("知识库id")
    private Long libraryId;

    @ApiModelProperty("权限id")
    private Long authId;
}
