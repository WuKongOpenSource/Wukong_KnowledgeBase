package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyq
 */
@Data
@ApiModel("查询文档列表参数")
public class QueryDocumentListBO {
    @ApiModelProperty("知识库id")
    @NotNull
    private Long libraryId;

    @ApiModelProperty("1 根据更新时间排序 2 根据阅读数排序")
    @NotNull
    private Integer type;
}
