package com.kakarote.km.entity.VO;

import com.kakarote.core.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel("知识库内文档信息分页")
public class DocumentInLibraryVO {
    @ApiModelProperty("分页")
    private BasePage<DocumentVO> page;

    @ApiModelProperty("操作权限")
    private Integer currentUserAuth;
}
