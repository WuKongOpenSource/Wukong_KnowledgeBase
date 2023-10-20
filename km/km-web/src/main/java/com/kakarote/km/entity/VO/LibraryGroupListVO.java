package com.kakarote.km.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("知识库分组列表信息")
public class LibraryGroupListVO {
    @ApiModelProperty("项目分组ID")
    @TableId(value = "group_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("分组类型")
    private Integer type;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("知识库列表")
    private List<LibraryListVO> libraryListVOList;
}
