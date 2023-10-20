package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.km.entity.BO.MoveDocumentBO;
import com.kakarote.km.entity.BO.QueryDocumentListBO;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.PO.KmDocumentFavor;
import com.kakarote.km.entity.VO.DocumentDetailVO;
import com.kakarote.km.entity.VO.DocumentInfoVO;
import com.kakarote.km.service.IKmDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmDocument")
@Api(tags = "文档")
public class KmDocumentController {
    @Autowired
    private IKmDocumentService kmDocumentService;

    @PostMapping("/add")
    @ApiOperation("添加文档")
    public Result add(@RequestBody KmDocument kmDocument) {
        kmDocumentService.add(kmDocument);
        return Result.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改文档")
    public Result update(@RequestBody KmDocument kmDocument) {
        kmDocumentService.set(kmDocument);
        return Result.ok();
    }

    @PostMapping("/move")
    @ApiOperation("移动文档")
    public Result move(@RequestBody MoveDocumentBO moveDocumentBO) {
        kmDocumentService.move(moveDocumentBO);
        return Result.ok();
    }

    @PostMapping("/queryById")
    @ApiOperation("文档详情")
    public Result<DocumentDetailVO> queryById(Long documentId) {
        DocumentDetailVO documentDetailVO = kmDocumentService.queryById(documentId);
        return Result.ok(documentDetailVO);
    }

    @PostMapping("/queryInfoById")
    @ApiOperation("通过消息查看知识库详情")
    public Result<DocumentInfoVO> queryInfoById(Long documentId) {
        DocumentInfoVO documentInfoVO = kmDocumentService.queryInfoById(documentId);
        return Result.ok(documentInfoVO);
    }

    @PostMapping("/delete")
    @ApiOperation("删除文档")
    public Result delete(Long documentId) {
        kmDocumentService.delete(documentId);
        return Result.ok();
    }

    @PostMapping("/completelyDelete")
    @ApiOperation("彻底删除文档")
    public Result completelyDelete(Long documentId) {
        kmDocumentService.completelyDelete(documentId);
        return Result.ok();
    }

    @PostMapping("/favor")
    @ApiOperation("查询文档列表")
    public Result favor(@RequestBody KmDocumentFavor kmDocumentFavor) {
        kmDocumentService.favor(kmDocumentFavor);
        return Result.ok();
    }

    @ApiOperation("查询所有字段语言包key信息")
    @PostMapping(value = "/getAllFieldLanguageRel")
    public Result<List<Map<String, Object>>> getAllFieldLanguageRel() {
        return Result.ok(kmDocumentService.getAllFieldLanguageRel());
    }

}

