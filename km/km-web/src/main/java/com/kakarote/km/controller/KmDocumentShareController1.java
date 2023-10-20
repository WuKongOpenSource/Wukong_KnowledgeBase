package com.kakarote.km.controller;


import com.kakarote.core.common.ParamAspect;
import com.kakarote.core.common.Result;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.service.IKmDocumentShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文档分享 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/documentShare")
@Api(tags = "分享")
public class KmDocumentShareController1 {
    @Autowired
    private IKmDocumentShareService kmDocumentShareService;

    @PostMapping("/queryShareUrl/{token}")
    @ApiOperation("通过链接查看分享文档")
    @ParamAspect
    public Result<KmDocument> queryShareUrl(@PathVariable("token") String token) {
        KmDocument kmDocument = kmDocumentShareService.queryShareUrl(token);
        return Result.ok(kmDocument);
    }

}

