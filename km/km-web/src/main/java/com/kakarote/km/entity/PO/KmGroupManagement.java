package com.kakarote.km.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 知识库分组管理表
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("wk_km_group_management")
@ApiModel(value = "KmGroupManagement对象", description = "知识库分组管理表")
public class KmGroupManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("知识库组ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty("知识库ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long libraryId;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("对应主键batchId")
    private String batchId;

    @ApiModelProperty("创建人ID")
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人ID")
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUserId;


}
