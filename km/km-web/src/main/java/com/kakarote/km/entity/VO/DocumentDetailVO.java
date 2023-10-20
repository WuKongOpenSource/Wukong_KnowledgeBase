package com.kakarote.km.entity.VO;
import com.kakarote.common.entity.SimpleUser;
import com.kakarote.km.entity.PO.KmDocumentLabel;
import com.kakarote.km.entity.PO.KmDocumentShare;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel("文件详情信息")
public class DocumentDetailVO implements Serializable {
    @ApiModelProperty(value = "文件id")
    private Long documentId;

    @ApiModelProperty(value = "文档标题")
    private String title;

    @ApiModelProperty(value = "3 富文本 4 文件")
    private Integer type;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty("文档内容")
    private String content;

    @ApiModelProperty("父级文档id")
    private Long parentId;

    @ApiModelProperty(value = "-1 删除 0 草稿 1 正常 2 模板")
    private Integer status;

    @ApiModelProperty(value = "知识库id")
    private Long libraryId;

    @ApiModelProperty(value = "文件夹id")
    private Long folderId;

    @ApiModelProperty("权限id")
    private Long authId;

    @ApiModelProperty("权限")
    private DocumentAuthVO auth;

    @ApiModelProperty(value = "标签id")
    private String labelIds;

    @ApiModelProperty(value = "标签列表")
    private List<KmDocumentLabel> labelList = new ArrayList<>();

    @ApiModelProperty("创建人id")
    private Long createUserId;

    private SimpleUser createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除人id")
    private Long deleteUserId;

    @ApiModelProperty("删除时间")
    private LocalDateTime deleteTime;

    @ApiModelProperty("当前人权限")
    private Integer currentUserAuth;

    @ApiModelProperty("文档分享")
    private KmDocumentShare share;

    private List<SimpleUser> starUserList;

    private Integer collectStatus;

    private Integer faverStatus;

    private Integer libraryIsOpen;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "知识库名称")
    private String libraryName;
}
