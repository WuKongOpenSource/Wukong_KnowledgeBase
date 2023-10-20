package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.kakarote.common.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文档分享
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_document_share")
@ApiModel(value = "KmDocumentShare对象", description = "文档分享")
public class KmDocumentShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "share_id", type = IdType.ASSIGN_ID)
    private Long shareId;

    @ApiModelProperty(value = "文档id")
    private Long documentId;

    @ApiModelProperty(value = "分享内部成员id")
    private String shareUserIds;

    @ApiModelProperty(value = "分享内部成员列表")
    @TableField(exist = false)
    private List<SimpleUser> userList;

    @ApiModelProperty(value = "外部分享链接")
    private String shareUrl;

    @ApiModelProperty(value = "外部查看文档的唯一标识")
    private String token;

    @ApiModelProperty(value = "1 启用 0 关闭分享")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Long closeUserId;

    private LocalDateTime closeTime;

}
