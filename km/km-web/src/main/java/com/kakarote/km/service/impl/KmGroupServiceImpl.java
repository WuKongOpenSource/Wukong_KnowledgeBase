package com.kakarote.km.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.km.common.KmGroupTypeEnum;
import com.kakarote.km.entity.PO.KmGroup;
import com.kakarote.km.entity.PO.KmGroupManagement;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.PO.KmKnowledgeLibraryUser;
import com.kakarote.km.mapper.KmGroupMapper;
import com.kakarote.km.service.IKmGroupManagementService;
import com.kakarote.km.service.IKmGroupService;
import com.kakarote.km.service.IKmKnowledgeLibraryService;
import com.kakarote.km.service.IKmKnowledgeLibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 知识库分组表 服务实现类
 * </p>
 *
 * @author guomenghao
 * @since 2023-05-15
 */
@Service
public class KmGroupServiceImpl extends BaseServiceImpl<KmGroupMapper, KmGroup> implements IKmGroupService {

    @Autowired
    private IKmGroupManagementService kmGroupManagementService;

    @Autowired
    private IKmKnowledgeLibraryUserService kmKnowledgeLibraryUserService;

    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;

    /**
     * 添加知识库分组
     *
     * @param kmGroup 知识库
     */
    @Override
    public void addGroup(KmGroup kmGroup) {
        kmGroup.setType(KmGroupTypeEnum.CUSTOM.getType());
        save(kmGroup);
    }

    /**
     * 更新知识库分组
     *
     * @param kmGroup 知识库
     */
    @Override
    public void updateGroup(KmGroup kmGroup) {
        updateById(kmGroup);
    }

    /**
     * 批量更新知识库分组
     *
     * @param kmGroups 知识库分组
     */
    @Override
    public void updateGroupBatch(List<KmGroup> kmGroups) {
        if (CollectionUtil.isNotEmpty(kmGroups)) {
            updateBatchById(kmGroups);
        }
    }

    /**
     * 查询分组
     *
     * @return 分组列表
     */
    @Override
    public List<KmGroup> searchGroupList() {
        //查询当前人的的分组信息
        List<KmGroup> list = lambdaQuery().eq(KmGroup::getCreateUserId, UserUtil.getUserId()).list().stream().sorted(Comparator.comparing(KmGroup::getSort, Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList());
        //如果没有添加默认分组
        if (CollectionUtil.isEmpty(list)) {
            //增加全部知识库,未分组 两个默认分组，这两个分组不需要绑定知识库，用途是页面分组排序
            saveBatch(this.getDefaultGroup());
        } else {
            //查询是否有全部知识库，未分组知识库，如果没有则添加，主要用途是页面排序
            List<KmGroup> defaultList = list.stream().filter(item -> (ObjectUtil.isNotEmpty(item.getType()) && item.getType().equals(KmGroupTypeEnum.ALL.getType()))
                    || (ObjectUtil.isNotEmpty(item.getType()) && item.getType().equals(KmGroupTypeEnum.NONE.getType()))).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(defaultList)) {
                List<KmGroup> defaultGroups = getDefaultGroup();
                saveBatch(defaultGroups);
            }
        }

        //获取全部分组
        list = lambdaQuery().eq(KmGroup::getCreateUserId, UserUtil.getUserId()).list().stream().sorted(Comparator.comparing(KmGroup::getSort, Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(list)) {
            int allCount;
            int noneCount;
            //查询我参加的知识库
            List<Long> libraryIds = kmKnowledgeLibraryUserService.lambdaQuery().eq(KmKnowledgeLibraryUser::getUserId, UserUtil.getUserId()).list().stream().map(KmKnowledgeLibraryUser::getLibraryId).distinct().collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(libraryIds)) {
                //获取全部知识库
                List<KmKnowledgeLibrary> knowledgeLibraryList = kmKnowledgeLibraryService.lambdaQuery().select(KmKnowledgeLibrary::getLibraryId).eq(KmKnowledgeLibrary::getStatus, 1)
                        .in(KmKnowledgeLibrary::getLibraryId, libraryIds).list();
                allCount = knowledgeLibraryList.size();
                //查询未分组的数据(分组根据根据个人自定义)
                List<Long> groupLibraryIds = kmGroupManagementService.lambdaQuery().eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).list().stream().map(KmGroupManagement::getLibraryId).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(groupLibraryIds)) {
                    List<KmKnowledgeLibrary> noneKnowledgeLibraryList = knowledgeLibraryList.stream().filter(knowledgeLibrary -> !groupLibraryIds.contains(knowledgeLibrary.getLibraryId())).distinct().collect(Collectors.toList());
                    noneCount = noneKnowledgeLibraryList.size();
                } else {
                    noneCount = 0;
                }
            } else {
                noneCount = 0;
                allCount = 0;
            }
            list.forEach(l -> {
                //如果是全部知识库分组查询所有知识库
                if (ObjectUtil.isNotEmpty(l.getType()) && KmGroupTypeEnum.ALL.getType().equals(l.getType())) {
                    l.setNum(allCount);
                } else if (ObjectUtil.isNotEmpty(l.getType()) && KmGroupTypeEnum.NONE.getType().equals(l.getType())) {
                    //如果是未分组查询所有未分组知识库
                    l.setNum(noneCount);
                } else {
                    List<Long> knowledgeIds = kmGroupManagementService.lambdaQuery().eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).eq(KmGroupManagement::getGroupId, l.getGroupId()).list()
                            .stream().map(KmGroupManagement::getLibraryId).distinct().collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(knowledgeIds)) {
                        l.setNum(knowledgeIds.size());
                    }
                }

            });
        }
        return list;
    }

    /**
     * 移除分组
     *
     * @param groupId 分组ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeGroupById(Long groupId) {
        //删除分组关系
        kmGroupManagementService.lambdaUpdate().eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).eq(KmGroupManagement::getGroupId, groupId).remove();
        //删除分组
        removeById(groupId);

    }

    private List<KmGroup> getDefaultGroup() {
        List<KmGroup> list = new ArrayList<>();
        KmGroup allGroup = new KmGroup();
        allGroup.setName(KmGroupTypeEnum.ALL.getDesc());
        allGroup.setSort(0);
        allGroup.setType(KmGroupTypeEnum.ALL.getType());
        list.add(allGroup);
        KmGroup noneGroup = new KmGroup();
        noneGroup.setName(KmGroupTypeEnum.NONE.getDesc());
        noneGroup.setSort(1);
        noneGroup.setType(KmGroupTypeEnum.NONE.getType());
        list.add(noneGroup);
        return list;

    }
}
