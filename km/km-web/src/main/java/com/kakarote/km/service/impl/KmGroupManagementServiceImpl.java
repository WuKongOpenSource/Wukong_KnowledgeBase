package com.kakarote.km.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.km.common.KmGroupTypeEnum;
import com.kakarote.km.entity.PO.KmGroup;
import com.kakarote.km.entity.PO.KmGroupManagement;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.mapper.KmGroupManagementMapper;
import com.kakarote.km.service.IKmGroupManagementService;
import com.kakarote.km.service.IKmGroupService;
import com.kakarote.km.service.IKmKnowledgeLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 知识库分组管理表 服务实现类
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
@Service
public class KmGroupManagementServiceImpl extends BaseServiceImpl<KmGroupManagementMapper, KmGroupManagement> implements IKmGroupManagementService {

    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;

    @Autowired
    private IKmGroupService kmGroupService;

    /**
     * 移动/增加知识库到分组
     *
     * @param kmGroupManagement 知识库管理
     */
    @Override
    public void moveToGroup(KmGroupManagement kmGroupManagement) {

        KmGroupManagement currentGroup = lambdaQuery().eq(KmGroupManagement::getLibraryId, kmGroupManagement.getLibraryId()).eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).one();
        if (ObjectUtil.isNotEmpty(currentGroup)) {
            currentGroup.setGroupId(kmGroupManagement.getGroupId());
        } else {
            currentGroup = BeanUtil.copyProperties(kmGroupManagement, KmGroupManagement.class);
            //确保误传id造成分组失效
            currentGroup.setId(null);
        }
        currentGroup.setBatchId(IdUtil.simpleUUID());
        KmGroup kmGroup = kmGroupService.getById(currentGroup.getGroupId());
        if (ObjectUtil.equal(kmGroup.getType(), KmGroupTypeEnum.NONE.getType())) {
            //如果移动到未分组，则删除当前用户的所有知识库分组
            lambdaUpdate().eq(KmGroupManagement::getLibraryId, kmGroupManagement.getLibraryId()).eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).remove();
        } else {
            saveOrUpdate(currentGroup);
        }
    }

    /**
     * 移除知识库分组
     *
     * @param groupId   分组ID
     * @param libraryId 知识库ID
     */
    @Override
    public void removeToGroup(Long groupId, Long libraryId) {
        lambdaUpdate().eq(KmGroupManagement::getGroupId, groupId).eq(KmGroupManagement::getLibraryId, libraryId).eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).remove();
    }

    /**
     * 查询当前分组下的知识库
     *
     * @param groupId 分组ID
     * @return
     */
    @Override
    public List<KmKnowledgeLibrary> searchLibraryGroupList(Long groupId) {
        List<Long> libraryIds = lambdaQuery().eq(KmGroupManagement::getGroupId, groupId).eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).list()
                .stream().map(KmGroupManagement::getLibraryId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(libraryIds)) {
            return kmKnowledgeLibraryService.lambdaQuery().in(KmKnowledgeLibrary::getLibraryId, libraryIds).list();
        }
        return null;
    }
}
