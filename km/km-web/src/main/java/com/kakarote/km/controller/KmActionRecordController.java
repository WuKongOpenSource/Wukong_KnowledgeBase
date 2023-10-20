package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.km.entity.BO.QueryDeleteRecordBO;
import com.kakarote.km.entity.BO.RestoreBO;
import com.kakarote.km.service.IKmActionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 知识库操作记录（最近使用） 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmActionRecord")
@Api(tags = "知识库操作记录")
public class KmActionRecordController {
    @Autowired
    private IKmActionRecordService kmActionRecordService;

    @PostMapping("/queryList")
    @ApiOperation("查询记录列表")
    public Result queryList(Long libraryId) {
        return Result.ok(kmActionRecordService.queryList(libraryId));
    }

    @PostMapping("/queryDeleteList")
    @ApiOperation("查询最近删除列表")
    public Result queryDeleteList(@RequestBody QueryDeleteRecordBO queryDeleteRecordBO) {
        return Result.ok(kmActionRecordService.queryDeleteList(queryDeleteRecordBO));
    }

    @PostMapping("/queryLibraryDeleteList")
    @ApiOperation("查询被删除的知识库")
    public Result queryLibraryDeleteList() {
        return Result.ok(kmActionRecordService.queryLibraryDeleteList());
    }

    @PostMapping("/restore")
    @ApiOperation("恢复")
    public Result restore(@RequestBody RestoreBO restoreBO) {
        kmActionRecordService.restore(restoreBO);
        return Result.ok();
    }
}

