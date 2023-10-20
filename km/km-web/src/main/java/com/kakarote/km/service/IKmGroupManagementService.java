package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.PO.KmGroupManagement;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;

import java.util.List;

/**
 * <p>
 * 知识库分组管理表 服务类
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
public interface IKmGroupManagementService extends BaseService<KmGroupManagement> {

    /**
     * 移动/增加知识库到分组
     *
     * @param kmGroupManagement 知识库管理
     */
    void moveToGroup(KmGroupManagement kmGroupManagement);

    /**
     * 移除知识库分组
     *
     * @param groupId   分组ID
     * @param libraryId 知识库ID
     */
    void removeToGroup(Long groupId, Long libraryId);

    /**
     * 查询当前分组下的知识库
     *
     * @param groupId 分组ID
     * @return
     */
    List<KmKnowledgeLibrary> searchLibraryGroupList(Long groupId);
}
