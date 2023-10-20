package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 知识库成员
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_km_knowledge_library_user")
@ApiModel(value = "KmKnowledgeLibraryUser对象", description = "知识库成员")
public class KmKnowledgeLibraryUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "r_id", type = IdType.ASSIGN_ID)
    private Long rId;

    private Long libraryId;

    private Long userId;

    private Long deptId;

    @ApiModelProperty(value = "0 用户 1 部门")
    private Integer type;

    @ApiModelProperty(value = "1 创建人 2 管理员 3 成员(可上传)")
    private Integer role;

    private Long createUserId;

}
