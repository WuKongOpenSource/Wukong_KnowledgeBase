package com.kakarote.km.entity.BO;

import com.kakarote.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("文档搜索参数")
public class SearchBO extends PageEntity {
    @ApiModelProperty("搜索关键字")
    private String search;

    @ApiModelProperty("1 今天 2 上周 3 上月 4 去年 5 自定义时间")
    private Integer type;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("创建人id")
    private String createUserId;

    @ApiModelProperty("知识库id")
    private String libraryId;
}
