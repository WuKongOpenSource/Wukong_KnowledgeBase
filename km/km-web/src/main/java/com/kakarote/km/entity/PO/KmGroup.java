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
 * 知识库分组表
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
@TableName("wk_km_group")
@ApiModel(value = "KmGroup对象", description = "知识库分组表")
public class KmGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目分组ID")
    @TableId(value = "group_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("创建人ID")
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;

    @ApiModelProperty("新建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人ID")
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUserId;

    @ApiModelProperty("分组类型(1:全部分组，2:未分组，3:自定义分组)")
    private Integer type;

    @ApiModelProperty(value = "关联知识库数量")
    @TableField(exist = false)
    private Integer num;

}
