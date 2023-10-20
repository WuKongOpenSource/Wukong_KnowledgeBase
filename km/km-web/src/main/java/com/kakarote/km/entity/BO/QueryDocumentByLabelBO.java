package com.kakarote.km.entity.BO;

import com.kakarote.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("查询文档下标签分页参数")
public class QueryDocumentByLabelBO extends PageEntity {
    @ApiModelProperty("标签id")
    private Long labelId;
}
