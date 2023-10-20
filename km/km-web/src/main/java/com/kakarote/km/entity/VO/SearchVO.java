package com.kakarote.km.entity.VO;

import com.kakarote.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wyq
 */
@Data
@ApiModel("搜索结果文档信息")
@EqualsAndHashCode(callSuper = true)
public class SearchVO extends PageEntity {
    @ApiModelProperty(value = "文档id")
    private Long documentId;

    @ApiModelProperty(value = "文档标题")
    private String title;

    @ApiModelProperty(value = "文档内容")
    private String content;

    @ApiModelProperty(value = "3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty(value = "父级文档id")
    private Long parentId;

    @ApiModelProperty(value = "-1 删除 0 草稿 1 正常 2 模板")
    private Integer status;

    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "知识库名称")
    private String libraryName;

    @ApiModelProperty(value = "文件夹id")
    private Long folderId;

    @ApiModelProperty(value = "权限id")
    private Long authId;

    @ApiModelProperty(value = "标签id")
    private String labelIds;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人id")
    private Long createUserId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除人id")
    private Long deleteUserId;

    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
}
