package com.kakarote.km.controller;

import com.kakarote.core.common.Result;
import com.kakarote.km.entity.PO.KmGroup;
import com.kakarote.km.service.IKmGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 知识库分组表 前端控制器
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/kmGroup")
@Api(tags = "知识库分组")
public class KmGroupController {

    @Autowired
    private IKmGroupService kmGroupService;

    @PostMapping("/addGroup")
    @ApiOperation("添加知识库分组")
    public Result addGroup(@RequestBody KmGroup kmGroup) {
        kmGroupService.addGroup(kmGroup);
        return Result.ok();
    }

    @PostMapping("/updateGroup")
    @ApiOperation("更新知识库分组")
    public Result updateGroup(@RequestBody KmGroup kmGroup) {
        kmGroupService.updateGroup(kmGroup);
        return Result.ok();
    }

    @PostMapping("/updateGroupBatch")
    @ApiOperation("批量更新知识库分组")
    public Result updateGroupBatch(@RequestBody List<KmGroup> kmGroups) {
        kmGroupService.updateGroupBatch(kmGroups);
        return Result.ok();
    }

    @PostMapping("/searchGroupList")
    @ApiOperation("查询分组")
    public Result searchGroupList() {
        return Result.ok(kmGroupService.searchGroupList());
    }

    @PostMapping("/removeGroupById")
    @ApiOperation("移除分组")
    public Result removeGroupById(@RequestParam Long groupId) {
        kmGroupService.removeGroupById(groupId);
        return Result.ok();
    }

}
