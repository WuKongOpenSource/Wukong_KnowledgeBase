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
@ApiModel("记录列表信息")
public class RecordListVO {
    @ApiModelProperty("今日")
    private List<RecordVO> todayList;

    @ApiModelProperty("昨日")
    private List<RecordVO> yesterdayList;

    @ApiModelProperty("本月")
    private List<RecordVO> monthList;
}
