package com.kakarote.km.entity.BO;

import com.kakarote.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("知识库分页参数")
public class LibraryPageBO extends PageEntity {
    @ApiModelProperty("知识库id")
    private Long libraryId;
}
