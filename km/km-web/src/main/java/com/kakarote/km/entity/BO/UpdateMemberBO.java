package com.kakarote.km.entity.BO;

import com.kakarote.km.entity.PO.KmKnowledgeLibraryUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("修改团队成员参数")
public class UpdateMemberBO {
    @ApiModelProperty("知识库id")
    private Long libraryId;

    @ApiModelProperty("员工id")
    private List<KmKnowledgeLibraryUser> userList;
}
