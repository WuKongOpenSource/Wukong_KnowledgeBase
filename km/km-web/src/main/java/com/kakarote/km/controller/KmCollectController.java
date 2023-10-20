package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.km.entity.BO.CollectListBO;
import com.kakarote.km.entity.PO.KmCollect;
import com.kakarote.km.entity.VO.CollectNumberListVO;
import com.kakarote.km.service.IKmCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 知识库收藏表 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmCollect")
@Api(tags = "收藏")
public class KmCollectController {
    @Autowired
    private IKmCollectService kmCollectService;

    @PostMapping("/addCollect")
    @ApiOperation("添加收藏")
    public Result addCollect(@RequestBody KmCollect kmCollect) {
        kmCollectService.addCollect(kmCollect);
        return Result.ok();
    }

    @PostMapping("/cancelCollect")
    @ApiOperation("取消收藏")
    public Result cancelCollect(@RequestBody KmCollect kmCollect) {
        kmCollectService.cancelCollect(kmCollect);
        return Result.ok();
    }

    @PostMapping("/queryList")
    @ApiOperation("收藏列表")
    public Result<CollectNumberListVO> queryList(@RequestBody CollectListBO collectListBO) {
        CollectNumberListVO collectNumberListVO = kmCollectService.queryList(collectListBO);
        return Result.ok(collectNumberListVO);
    }
}

