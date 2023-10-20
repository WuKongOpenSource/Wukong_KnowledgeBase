package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.km.entity.BO.AddAuthBO;
import com.kakarote.km.entity.BO.UpdateAuthBO;
import com.kakarote.km.entity.PO.KmAuthUser;
import com.kakarote.km.service.IKmAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文档文件夹权限表 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmAuth")
@Api(tags = "知识库操作权限")
public class KmAuthController {
    @Autowired
    private IKmAuthService kmAuthService;

    @PostMapping("/addAuth")
    @ApiOperation("添加权限")
    public Result addAuth(@RequestBody AddAuthBO addAuthBO) {
        kmAuthService.addAuth(addAuthBO);
        return Result.ok();
    }

    @PostMapping("/updateAuth")
    @ApiOperation("修改权限")
    public Result updateAuth(@RequestBody UpdateAuthBO updateAuthBO) {
        kmAuthService.updateAuth(updateAuthBO);
        return Result.ok();
    }

    @PostMapping("/deleteAuthUser")
    @ApiOperation("删除权限")
    public Result deleteAuthUser(@RequestBody KmAuthUser kmAuthUser) {
        kmAuthService.deleteAuthUser(kmAuthUser);
        return Result.ok();
    }
}

