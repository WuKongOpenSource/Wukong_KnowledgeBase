package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author wyq
 */
@Data
@ApiModel("查询删除记录列表参数")
public class QueryDeleteRecordBO {
    @ApiModelProperty("2 文件夹 3 富文本 4 文件")
    @Size(min = 2, max = 4)
    private Integer type;

    @ApiModelProperty("知识库id")
    private Long libraryId;
}
