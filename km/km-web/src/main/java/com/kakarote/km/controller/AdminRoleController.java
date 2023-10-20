package com.kakarote.km.controller;


import com.alibaba.fastjson.JSONObject;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/adminRole")
@Api(tags = "角色模块")
public class AdminRoleController {
    @PostMapping("/auth")
    @ApiOperation("角色权限")
    public Result<JSONObject> auth() {
        JSONObject object = new JSONObject();
        JSONObject knowledge = new JSONObject();
        object.put("knowledge",knowledge);
        return Result.ok(object);
    }
}

