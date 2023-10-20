package com.kakarote.km.entity.BO;

import com.kakarote.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("收藏列表请求参数")
public class CollectListBO extends PageEntity {
    @ApiModelProperty("2 文件夹 3 文件")
    private Integer type;

    @ApiModelProperty("搜索")
    private String search;
}
