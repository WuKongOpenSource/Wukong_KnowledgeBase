package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.PO.KmGroup;

import java.util.List;

/**
 * <p>
 * 知识库分组表 服务类
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
public interface IKmGroupService extends BaseService<KmGroup> {

    /**
     * 添加知识库分组
     *
     * @param kmGroup 知识库
     */
    public void addGroup(KmGroup kmGroup);

    /**
     * 更新知识库分组
     *
     * @param kmGroup 知识库
     */
    public void updateGroup(KmGroup kmGroup);

    /**
     * 批量更新知识库分组
     *
     * @param kmGroups 知识库分组
     */
    public void updateGroupBatch(List<KmGroup> kmGroups);

    /**
     * 查询分组
     *
     * @return 分组列表
     */
    public List<KmGroup> searchGroupList();

    /**
     * 移除分组
     *
     * @param groupId 分组ID
     */
    public void removeGroupById(Long groupId);
}
