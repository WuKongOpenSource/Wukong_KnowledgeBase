package com.kakarote.km.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("新建知识库参数")
public class SetKmKnowledgeLibraryBO {
    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "知识库名称")
    private String name;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "是否公开 1 公开 2 私有")
    private Integer isOpen;

    @ApiModelProperty(value = "-1 删除 1 正常 2 模板")
    private Integer status;

    @ApiModelProperty(value = "0 否 1 是")
    private Integer isSystemCover;

    @ApiModelProperty(value = "知识库封面")
    private String coverUrl;

    @ApiModelProperty(value = "模板id")
    private Long templateId;

    @ApiModelProperty(value = "用户id")
    private List<Long> userList;

    @ApiModelProperty(value = "部门id")
    private List<Long> deptList;

    @ApiModelProperty(value = "是否开启AI服务，1 关 2 开 默认1")
    private Integer aiServiceSwitch;
}
