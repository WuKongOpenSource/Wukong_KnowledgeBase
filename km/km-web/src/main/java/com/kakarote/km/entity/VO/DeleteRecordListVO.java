package com.kakarote.km.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel("删除记录列表信息")
public class DeleteRecordListVO {
    @ApiModelProperty("删除记录列表")
    private List<DeleteRecordVO> list;

    @ApiModelProperty("文件夹数量")
    private Integer folderNum;

    @ApiModelProperty("文件数量")
    private Integer documentNum;

    @ApiModelProperty("附件数量")
    private Integer fileNum;
}
