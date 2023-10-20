package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;
import com.kakarote.km.entity.BO.QueryDocumentByLabelBO;
import com.kakarote.km.entity.PO.KmDocumentLabel;
import com.kakarote.km.entity.VO.SearchVO;
import com.kakarote.km.service.IKmDocumentLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文档标签表 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmDocumentLabel")
@Api(tags = "标签")
public class KmDocumentLabelController {
    @Autowired
    private IKmDocumentLabelService kmDocumentLabelService;

    @PostMapping("/add")
    @ApiOperation("添加标签")
    public Result add(@RequestBody KmDocumentLabel kmDocumentLabel) {
        kmDocumentLabelService.add(kmDocumentLabel);
        return Result.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改标签")
    public Result update(@RequestBody KmDocumentLabel kmDocumentLabel) {
        kmDocumentLabelService.update(kmDocumentLabel);
        return Result.ok();
    }

    @PostMapping("/deleteById/{labelId}")
    @ApiOperation("删除标签")
    public Result deleteById(@PathVariable("labelId") Integer labelId) {
        kmDocumentLabelService.deleteById(labelId);
        return Result.ok();
    }

    @PostMapping("/queryList")
    @ApiOperation("查询标签列表")
    public Result<List<KmDocumentLabel>> queryList() {
        List<KmDocumentLabel> kmDocumentLabels = kmDocumentLabelService.queryList();
        return Result.ok(kmDocumentLabels);
    }

    @PostMapping("/queryDocumentByLabelId")
    @ApiOperation("根据标签id查询文档列表")
    public Result<BasePage<SearchVO>> queryDocumentByLabelId(@RequestBody QueryDocumentByLabelBO queryDocumentByLabelBO) {
        BasePage<SearchVO> page = kmDocumentLabelService.queryDocumentByLabelId(queryDocumentByLabelBO);
        return Result.ok(page);
    }
}

