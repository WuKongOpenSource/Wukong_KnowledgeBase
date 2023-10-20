package com.kakarote.km.entity.VO;

import com.kakarote.common.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel("文件夹详情信息")
public class FolderDetailVO {
    @ApiModelProperty("文件夹id")
    private Long folderId;

    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty("文件夹名称")
    private String title;

    @ApiModelProperty(value = "-1 删除 1 正常")
    private Integer status;

    @ApiModelProperty("创建人id")
    private Long createUserId;

    private SimpleUser createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("权限id")
    private Long authId;

    @ApiModelProperty("删除人id")
    private Long deleteUserId;

    @ApiModelProperty("删除时间")
    private LocalDateTime deleteTime;

    @ApiModelProperty(value = "是否公开 1 公开 2 私有")
    private Integer isOpen;

    @ApiModelProperty(value = "收藏人数")
    private Integer collectStatus;

    @ApiModelProperty("文件夹列表")
    private List<FolderVO> childList;

    @ApiModelProperty("文件列表")
    private List<DocumentVO> kmDocumentList;

    private DocumentAuthVO auth;

    private Integer libraryIsOpen;
}
