package com.kakarote.km.controller;

import com.kakarote.core.common.Result;
import com.kakarote.km.entity.PO.KmGroupManagement;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.service.IKmGroupManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 知识库分组管理表 前端控制器
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/kmGroupManagement")
@Api(tags = "知识库分组管理")
public class KmGroupManagementController {

    @Autowired
    private IKmGroupManagementService kmGroupManagementService;


    @PostMapping("/moveToGroup")
    @ApiOperation("移动/增加知识库到分组")
    public Result moveToGroup(@RequestBody KmGroupManagement kmGroupManagement) {
        kmGroupManagementService.moveToGroup(kmGroupManagement);
        return Result.ok();
    }

    @ApiOperation("移除知识库分组")
    @PostMapping("/removeToGroup")
    public Result removeToGroup(@RequestParam("groupId") Long groupId, @RequestParam("libraryId") Long libraryId) {
        kmGroupManagementService.removeToGroup(groupId, libraryId);
        return Result.ok();
    }

    @PostMapping("/searchLibraryGroupList")
    @ApiOperation("查询当前分组下的知识库")
    public Result<List<KmKnowledgeLibrary>> searchLibraryGroupList(@RequestParam Long groupId) {
        return Result.ok(kmGroupManagementService.searchLibraryGroupList(groupId));
    }
}
