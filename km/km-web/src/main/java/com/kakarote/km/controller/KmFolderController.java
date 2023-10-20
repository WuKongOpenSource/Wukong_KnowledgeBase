package com.kakarote.km.controller;


import com.kakarote.core.common.Result;
import com.kakarote.km.entity.BO.MoveFolderBO;
import com.kakarote.km.entity.PO.KmFolder;
import com.kakarote.km.entity.VO.FolderDetailVO;
import com.kakarote.km.entity.VO.TreeListVO;
import com.kakarote.km.service.IKmFolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 知识库文件夹 前端控制器
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/kmFolder")
@Api(tags = "文件夹")
public class KmFolderController {

    @Autowired
    public IKmFolderService kmFolderService;

    @PostMapping("/add")
    @ApiOperation("添加文件夹")
    public Result add(@RequestBody KmFolder kmFolder) {
        kmFolderService.add(kmFolder);
        return Result.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改文件夹")
    public Result update(@RequestBody KmFolder kmFolder) {
        kmFolderService.set(kmFolder);
        return Result.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除文件夹")
    public Result delete(Long folderId) {
        kmFolderService.delete(folderId);
        return Result.ok();
    }

    @PostMapping("/completelyDelete")
    @ApiOperation("彻底删除文件夹")
    public Result completelyDelete(Long folderId) {
        kmFolderService.completelyDelete(folderId);
        return Result.ok();
    }

    @PostMapping("/queryTreeList")
    @ApiOperation("查询文件夹树")
    public Result<TreeListVO> queryTreeList(Long libraryId) {
        return Result.ok(kmFolderService.queryTreeList(libraryId));
    }

    @PostMapping("/move")
    @ApiOperation("移动文件夹")
    public Result move(@RequestBody MoveFolderBO moveFolderBO) {
        kmFolderService.move(moveFolderBO);
        return Result.ok();
    }

    @PostMapping("/queryById")
    @ApiOperation("文件夹详情")
    public Result<FolderDetailVO> queryById(Long folderId) {
        return Result.ok(kmFolderService.queryById(folderId));
    }
}

