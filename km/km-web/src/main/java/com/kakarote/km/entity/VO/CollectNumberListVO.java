package com.kakarote.km.entity.VO;

import com.kakarote.core.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel("收藏列表及数量信息")
public class CollectNumberListVO {
    @ApiModelProperty("收藏列表")
    private BasePage<CollectListVO> page;

    @ApiModelProperty("文件夹数量")
    private Integer folderNum;

    @ApiModelProperty("文件数量")
    private Integer documentNum;

    @ApiModelProperty("附件数量")
    private Integer fileNum;

    @ApiModelProperty("知识库数量")
    private Integer libraryNum;
}
