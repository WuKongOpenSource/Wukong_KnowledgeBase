package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel("通过消息查看知识库详情信息")
public class DocumentInfoVO {
    @ApiModelProperty("share是分享，info是查看详情")
    private String type;

    @ApiModelProperty("share是分享的token，info是查看详情的id")
    private String data;

    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty("知识库id")
    private Long libraryId;
}
