package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("恢复参数")
public class RestoreBO {
    @ApiModelProperty("数据id")
    private Long id;

    @ApiModelProperty("1 知识库 2 文件夹 3 文档 4 文件")
    private Integer type;
}
