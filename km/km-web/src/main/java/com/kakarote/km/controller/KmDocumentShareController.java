package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.PO.KmDocumentShare;
import com.kakarote.km.service.IKmDocumentShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文档分享 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmDocumentShare")
@Api(tags = "分享")
public class KmDocumentShareController {
    @Autowired
    private IKmDocumentShareService kmDocumentShareService;

    @PostMapping("/openShare")
    @ApiOperation("开启分享")
    public Result<KmDocumentShare> openShare(@RequestBody KmDocumentShare documentShare) {
        KmDocumentShare kmDocumentShare = kmDocumentShareService.openShare(documentShare);
        return Result.ok(kmDocumentShare);
    }

    @PostMapping("/addShareMember")
    @ApiOperation("分享成员")
    public Result addShareMember(@RequestBody KmDocumentShare documentShare) {
        kmDocumentShareService.addShareMember(documentShare);
        return Result.ok();
    }

    @PostMapping("/closeShare")
    @ApiOperation("关闭分享")
    public Result closeShare(@RequestBody KmDocumentShare documentShare) {
        kmDocumentShareService.closeShare(documentShare);
        return Result.ok();
    }

    @PostMapping("/queryShareSys/{documentId}")
    @ApiOperation("在系统内分享")
    public Result<KmDocument> queryShareSys(@PathVariable("documentId") Integer documentId) {
        KmDocument kmDocument = kmDocumentShareService.queryShareSys(documentId);
        return Result.ok(kmDocument);
    }
}

