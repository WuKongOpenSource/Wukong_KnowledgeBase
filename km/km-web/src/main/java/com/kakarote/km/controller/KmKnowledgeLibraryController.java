package com.kakarote.km.controller;


import com.kakarote.core.common.ApiExplain;
import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;
import com.kakarote.km.entity.BO.LibraryPageBO;
import com.kakarote.km.entity.BO.SearchBO;
import com.kakarote.km.entity.BO.SetKmKnowledgeLibraryBO;
import com.kakarote.km.entity.BO.UpdateMemberBO;
import com.kakarote.km.entity.PO.KmKnowledgeLibraryUser;
import com.kakarote.km.entity.VO.DocumentVO;
import com.kakarote.km.entity.VO.QueryMemberVO;
import com.kakarote.km.service.IKmCommonService;
import com.kakarote.km.service.IKmKnowledgeLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 知识库 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmKnowledgeLibrary")
@Api(tags = "知识库")
public class KmKnowledgeLibraryController {
    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;

    @Autowired
    private IKmCommonService kmCommonService;

    @PostMapping("/initKmData")
    @ApiExplain("初始化知识库")
    public Result<Boolean> initKmData() {
        return Result.ok(kmCommonService.initKmData());
    }

    @PostMapping("/add")
    @ApiOperation("新建知识库")
    public Result add(@RequestBody SetKmKnowledgeLibraryBO setKmKnowledgeLibraryBO) {
        kmKnowledgeLibraryService.add(setKmKnowledgeLibraryBO);
        return Result.ok();
    }

    @PostMapping("/update")
    @ApiOperation("编辑知识库")
    public Result update(@RequestBody SetKmKnowledgeLibraryBO setKmKnowledgeLibraryBO) {
        kmKnowledgeLibraryService.set(setKmKnowledgeLibraryBO);
        return Result.ok();
    }

    @PostMapping("/delete/{libraryId}")
    @ApiOperation("删除知识库")
    public Result delete(@PathVariable("libraryId") Long libraryId) {
        kmKnowledgeLibraryService.delete(libraryId);
        return Result.ok();
    }

    @PostMapping("/completelyDelete")
    @ApiOperation("彻底删除知识库")
    public Result completelyDelete(Long libraryId) {
        kmKnowledgeLibraryService.completelyDelete(libraryId);
        return Result.ok();
    }

    @PostMapping("/updateMember")
    @ApiOperation("修改团队成员")
    public Result updateMember(@RequestBody UpdateMemberBO updateMemberBO) {
        kmKnowledgeLibraryService.updateMember(updateMemberBO);
        return Result.ok();
    }

    @PostMapping("/exitMember")
    @ApiOperation("退出团队")
    public Result exitMember(@RequestBody KmKnowledgeLibraryUser kmKnowledgeLibraryUser) {
        kmKnowledgeLibraryService.exitMember(kmKnowledgeLibraryUser);
        return Result.ok();
    }

    @PostMapping("/queryMember")
    @ApiOperation("查询团队成员")
    public Result<List<QueryMemberVO>> queryMember(Long libraryId) {
        return Result.ok(kmKnowledgeLibraryService.queryMember(libraryId));
    }

    @PostMapping("/queryList")
    @ApiOperation("查询知识库列表")
    public Result queryList(@RequestParam(name = "groupId", required = false) Long groupId, @RequestParam(name = "star", required = false) Integer star) {
        return Result.ok(kmKnowledgeLibraryService.queryList(groupId, star));
    }

    @PostMapping("/queryById")
    @ApiOperation("查询知识库详情")
    public Result queryById(@RequestParam("libraryId") Long libraryId) {
        return Result.ok(kmKnowledgeLibraryService.queryById(libraryId));
    }

    @PostMapping("/queryDocumentByLibraryId")
    @ApiOperation("查询知识库下文档")
    public Result<BasePage<DocumentVO>> queryDocumentByLibraryId(@RequestBody LibraryPageBO libraryPageBO) {
        return Result.ok(kmKnowledgeLibraryService.queryDocumentByLibraryId(libraryPageBO));
    }

    @PostMapping("/queryCollectDocumentByLibraryId")
    @ApiOperation("查询知识库下收藏文档")
    public Result queryCollectDocumentByLibraryId(@RequestBody LibraryPageBO libraryPageBO) {
        return Result.ok(kmKnowledgeLibraryService.queryCollectDocumentByLibraryId(libraryPageBO));
    }

    @PostMapping("/queryLibraryTemplate")
    @ApiOperation("查询知识库模块")
    public Result queryLibraryTemplate() {
        return Result.ok(kmKnowledgeLibraryService.queryLibraryTemplate());
    }

    @PostMapping("/search")
    @ApiOperation("文档搜索")
    public Result search(@RequestBody SearchBO searchBO) {
        return Result.ok(kmKnowledgeLibraryService.search(searchBO));
    }

    @PostMapping("/queryLibraryListByGroup")
    @ApiOperation("通过分组查询知识库列表")
    public Result queryLibraryListByGroup() {
        return Result.ok(kmKnowledgeLibraryService.queryLibraryListByGroup());
    }

    @PostMapping("/getAISwitch")
    @ApiOperation("查询是否存在开启AI服务的知识库")
    public Result getAISwitch() {
        return Result.ok(kmKnowledgeLibraryService.getAISwitch());
    }
}

