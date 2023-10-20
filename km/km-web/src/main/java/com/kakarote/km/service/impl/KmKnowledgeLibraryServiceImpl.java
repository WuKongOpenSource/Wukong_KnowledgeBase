package com.kakarote.km.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kakarote.common.entity.SimpleDept;
import com.kakarote.common.entity.SimpleUser;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.Const;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.core.utils.TransferUtil;
import com.kakarote.ids.provider.service.DeptService;
import com.kakarote.ids.provider.service.UserService;
import com.kakarote.ids.provider.utils.UserCacheUtil;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.common.KmEnum;
import com.kakarote.km.common.KmGroupTypeEnum;
import com.kakarote.km.entity.BO.*;
import com.kakarote.km.entity.PO.*;
import com.kakarote.km.entity.VO.*;
import com.kakarote.km.mapper.KmFolderMapper;
import com.kakarote.km.mapper.KmKnowledgeLibraryMapper;
import com.kakarote.km.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 知识库 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
@Slf4j
public class KmKnowledgeLibraryServiceImpl extends BaseServiceImpl<KmKnowledgeLibraryMapper, KmKnowledgeLibrary> implements IKmKnowledgeLibraryService {
    @Autowired
    private IKmKnowledgeLibraryUserService kmKnowledgeLibraryUserService;

    @Autowired
    private IKmAuthService kmAuthService;

    @Autowired
    private IKmAuthUserService kmAuthUserService;

    @Autowired
    private IKmDocumentService kmDocumentService;

    @Autowired
    private IKmFolderService kmFolderService;

    @Autowired
    private IKmCollectService kmCollectService;

    @Autowired
    private IKmActionRecordService kmActionRecordService;

    @Autowired
    private KmFolderMapper kmFolderMapper;

    @Autowired
    private IKmGroupManagementService groupManagementService;

    @Autowired
    private IKmGroupService kmGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    private static final String IMAGE_PATTERN = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|exe|PNG|JPG|GIF|BMP|JPEG|EXE)$";


    @Override
    @Transactional
    public void add(SetKmKnowledgeLibraryBO setKmKnowledgeLibraryBO) {
        KmKnowledgeLibrary kmKnowledgeLibrary = new KmKnowledgeLibrary();
        BeanUtil.copyProperties(setKmKnowledgeLibraryBO, kmKnowledgeLibrary);
        if (ObjectUtil.isNull(setKmKnowledgeLibraryBO.getAiServiceSwitch())) {
            kmKnowledgeLibrary.setAiServiceSwitch(1);
        }
        save(kmKnowledgeLibrary);
        if (setKmKnowledgeLibraryBO.getTemplateId() != null) {
            templateCreate(setKmKnowledgeLibraryBO.getTemplateId(), kmKnowledgeLibrary.getLibraryId());
        }
        List<KmKnowledgeLibraryUser> kmKnowledgeLibraryUsers = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(setKmKnowledgeLibraryBO.getUserList())) {
            List<Long> userList = setKmKnowledgeLibraryBO.getUserList();
            userList.forEach(userId -> {
                KmKnowledgeLibraryUser libraryUser = new KmKnowledgeLibraryUser();
                libraryUser.setUserId(userId);
                libraryUser.setLibraryId(kmKnowledgeLibrary.getLibraryId());
                if (userId.equals(UserUtil.getUserId())) {
                    libraryUser.setRole(KmEnum.LIBRARY_CREATE_ROLE.getValue());
                } else {
                    libraryUser.setRole(KmEnum.LIBRARY_MANAGE_ROLE.getValue());
                }
                libraryUser.setCreateUserId(UserUtil.getUserId());
                kmKnowledgeLibraryUsers.add(libraryUser);
            });
        } else {
            KmKnowledgeLibraryUser libraryUser = new KmKnowledgeLibraryUser();
            libraryUser.setUserId(UserUtil.getUserId());
            libraryUser.setLibraryId(kmKnowledgeLibrary.getLibraryId());
            libraryUser.setRole(KmEnum.LIBRARY_CREATE_ROLE.getValue());
            libraryUser.setCreateUserId(UserUtil.getUserId());
            kmKnowledgeLibraryUsers.add(libraryUser);
        }
        kmKnowledgeLibraryUserService.saveBatch(kmKnowledgeLibraryUsers, Const.BATCH_SAVE_SIZE);
    }

    @Override
    public void set(SetKmKnowledgeLibraryBO setKmKnowledgeLibraryBO) {
        if (!kmAuthService.checkLibraryAuth(setKmKnowledgeLibraryBO.getLibraryId())) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        }
        KmKnowledgeLibrary kmKnowledgeLibrary = new KmKnowledgeLibrary();
        BeanUtil.copyProperties(setKmKnowledgeLibraryBO, kmKnowledgeLibrary);
        updateById(kmKnowledgeLibrary);
    }

    public void templateCreate(Long templateId, Long libraryId) {
        List<KmDocument> kmDocuments = KmLibraryTemplate.ME.getTemplateDocument(templateId);
        kmDocuments.forEach(document -> {
            document.setDocumentId(null);
            document.setLibraryId(libraryId);
            document.setCreateTime(LocalDateTimeUtil.now());
            document.setUpdateTime(LocalDateTimeUtil.now());
            document.setCreateUserId(UserUtil.getUserId());
            document.setStatus(1);
            //模板创建默认是公开仅预览权限
            KmAuth kmAuth = new KmAuth();
            kmAuth.setIsOpen(1);
            kmAuth.setOpenAuth(3);
            kmAuth.setCreateUserId(UserUtil.getUserId());
            kmAuthService.save(kmAuth);
            KmAuthUser kmAuthUser = new KmAuthUser();
            kmAuthUser.setAuthId(kmAuth.getAuthId());
            kmAuthUser.setCreateUserId(UserUtil.getUserId());
            kmAuthUser.setCreateTime(LocalDateTimeUtil.now());
            kmAuthUser.setAuth(KmEnum.PRIVATE_MANAGE_AUTH.getValue());
            kmAuthUser.setUserId(UserUtil.getUserId());
            kmAuthUserService.save(kmAuthUser);
            document.setAuthId(kmAuth.getAuthId());
        });
        kmDocumentService.saveBatch(kmDocuments, Const.BATCH_SAVE_SIZE);
    }

    @Override
    @Transactional
    public void delete(Long libraryId) {
        if (!kmAuthService.checkLibraryAuth(libraryId)) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        }
        Long userId = UserUtil.getUserId();
        KmKnowledgeLibrary kmKnowledgeLibrary = new KmKnowledgeLibrary().setLibraryId(libraryId).setStatus(-1).setDeleteUserId(userId).setDeleteTime(LocalDateTimeUtil.now());
        updateById(kmKnowledgeLibrary);
        kmActionRecordService.add(2, 1, libraryId);
        kmDocumentService.lambdaUpdate().set(KmDocument::getStatus, -1).eq(KmDocument::getLibraryId, libraryId).update();
        kmFolderService.lambdaUpdate().set(KmFolder::getStatus, -1).eq(KmFolder::getLibraryId, libraryId).update();
        kmCollectService.lambdaUpdate().eq(KmCollect::getType, 1).eq(KmCollect::getTypeId, libraryId).remove();
    }

    @Override
    @Transactional
    public void completelyDelete(Long libraryId) {
        KmKnowledgeLibrary kmKnowledgeLibrary = getById(libraryId);
        if (kmKnowledgeLibrary == null || kmKnowledgeLibrary.getStatus() != -1) {
            throw new CrmException(KmCodeEnum.KM_DATA_EXIST_ERROR);
        }
        //删除文件夹收藏以及浏览历史
        List<Long> folderIds = kmDocumentService.listObjs(new QueryWrapper<KmDocument>().select("document_id").eq("library_id", libraryId), o -> Long.parseLong(o.toString()));
        if (folderIds.size() > 0) {
            kmCollectService.update().eq("type", 2).in("type_id", folderIds).remove();
            kmActionRecordService.update().eq("type", 2).in("type_id", folderIds).remove();
        }

        //删除文档收藏以及浏览历史
        List<Long> documentIds = kmFolderService.listObjs(new QueryWrapper<KmFolder>().select("folder_id").eq("library_id", libraryId), o -> Long.parseLong(o.toString()));
        if (documentIds.size() > 0) {
            kmCollectService.update().in("type", Arrays.asList(3, 4)).in("type_id", documentIds).remove();
            kmActionRecordService.update().in("type", Arrays.asList(3, 4)).in("type_id", documentIds).remove();
        }

        //删除权限所属数据
        List<Long> authIds = getBaseMapper().queryLibraryAuth(libraryId);
        if (authIds.size() > 0) {
            kmAuthUserService.update().in("auth_id", authIds).ne("user_id", 0).remove();
        }
        //删除相关数据
        List<KmDocument> kmDocuments = kmDocumentService.query().eq("library_id", libraryId).eq("parent_id", 0).eq("folder_id", 0).list();
        kmDocuments.forEach(kmDocument -> kmDocumentService.completelyDelete(kmDocument.getDocumentId()));
        List<KmFolder> folders = kmFolderService.query().eq("library_id", libraryId).eq("parent_id", 0).list();
        folders.forEach(kmFolder -> kmFolderService.completelyDelete(kmFolder.getFolderId()));
        kmFolderService.update().eq("library_id", libraryId).remove();
        kmKnowledgeLibraryUserService.update().eq("library_id", libraryId).remove();
        kmCollectService.update().eq("type", 1).eq("type_id", libraryId).remove();
        kmActionRecordService.update().eq("type", 1).eq("type_id", libraryId).remove();

        //删除自身
        removeById(libraryId);
    }

    @Override
    public void updateMember(UpdateMemberBO updateMemberBO) {
        Long libraryId = updateMemberBO.getLibraryId();
        if (!kmAuthService.checkLibraryAuth(libraryId)) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        }
        KmKnowledgeLibrary library = getById(libraryId);
        if (library == null) {
            throw new CrmException(KmCodeEnum.KM_PARAMS_ERROR);
        }

        List<KmKnowledgeLibraryUser> libraryUserList = kmKnowledgeLibraryUserService.lambdaQuery().eq(KmKnowledgeLibraryUser::getLibraryId, libraryId).ne(KmKnowledgeLibraryUser::getRole, 1).list();

        List<Long> integers = libraryUserList.stream().filter(e -> e.getType() == 0).map(KmKnowledgeLibraryUser::getUserId).collect(Collectors.toList());

        List<Long> deptIds = libraryUserList.stream().filter(e -> e.getType() == 1).map(KmKnowledgeLibraryUser::getDeptId).collect(Collectors.toList());

        List<KmKnowledgeLibraryUser> userList = updateMemberBO.getUserList();
        if (userList != null) {
            //查询删除用户
            List<Long> addUserIds = userList.stream().map(KmKnowledgeLibraryUser::getUserId).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
            Collection<Long> deleteUserIds = CollectionUtil.subtract(integers, addUserIds);


            List<Long> addDeptIds = userList.stream().map(KmKnowledgeLibraryUser::getDeptId).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());

            Collection<Long> deleteDeptIds = CollectionUtil.subtract(deptIds, addDeptIds);


            List<Long> documentIds = kmDocumentService.lambdaQuery().select(KmDocument::getDocumentId).eq(KmDocument::getLibraryId, libraryId).list()
                    .stream().map(KmDocument::getDocumentId).collect(Collectors.toList());

            List<Long> folderIds = kmFolderService.lambdaQuery().select(KmFolder::getFolderId).eq(KmFolder::getLibraryId, libraryId).list()
                    .stream().map(KmFolder::getFolderId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(deleteUserIds) && CollUtil.isNotEmpty(folderIds)) {
                kmCollectService.lambdaUpdate().eq(KmCollect::getType, 2).in(KmCollect::getTypeId, folderIds).in(KmCollect::getCreateUserId, deleteUserIds).remove();
            }
            if (CollUtil.isNotEmpty(deleteUserIds) && CollUtil.isNotEmpty(documentIds)) {
                kmCollectService.lambdaUpdate().eq(KmCollect::getType, 3).in(KmCollect::getTypeId, documentIds).in(KmCollect::getCreateUserId, deleteUserIds).remove();
            }

            List<KmKnowledgeLibraryUser> collect = userList.stream().peek(kmKnowledgeLibraryUser -> {
                kmKnowledgeLibraryUser.setLibraryId(libraryId);
            }).collect(Collectors.toList());
            userList.forEach(user -> user.setLibraryId(libraryId));
            List<Long> list = new ArrayList<>();
            collect.forEach(c -> list.add(c.getUserId()));
            integers.retainAll(list);
            integers.add(library.getCreateUserId());
            kmKnowledgeLibraryUserService.update().eq("library_id", libraryId).ne("role", 1).remove();

            List<Long> authIds = getBaseMapper().queryLibraryAuth(libraryId);
            if (CollUtil.isNotEmpty(authIds)) {
                if (CollUtil.isNotEmpty(deleteUserIds)) {
                    kmAuthUserService.update().in("auth_id", authIds).in("user_id", deleteUserIds).remove();
                }

                if (CollUtil.isNotEmpty(deleteDeptIds)) {
                    kmAuthUserService.update().in("auth_id", authIds).in("dept_id", deleteDeptIds).remove();
                }
            }
            kmKnowledgeLibraryUserService.saveBatch(collect, Const.BATCH_SAVE_SIZE);
        }
    }

    @Override
    public void exitMember(KmKnowledgeLibraryUser kmKnowledgeLibraryUser) {
        KmKnowledgeLibraryUser libraryUser = kmKnowledgeLibraryUserService.query().eq("user_id", UserUtil.getUserId()).eq("library_id", kmKnowledgeLibraryUser.getLibraryId()).one();
        if (libraryUser.getRole().equals(KmEnum.LIBRARY_CREATE_ROLE.getValue())) {
            throw new CrmException(KmCodeEnum.KM_OWNER_REMOVE_MEMBER_ERROR);
        }
        kmKnowledgeLibraryUserService.removeById(libraryUser.getRId());
    }

    @Override
    public List<QueryMemberVO> queryMember(Long libraryId) {
        List<KmKnowledgeLibraryUser> list = kmKnowledgeLibraryUserService.lambdaQuery()
                .eq(KmKnowledgeLibraryUser::getLibraryId, libraryId).list();
        List<Long> deptIds = list.stream().map(KmKnowledgeLibraryUser::getDeptId).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
        List<SimpleDept> simpleDeptList = deptService.queryDeptByIds(deptIds).getData();

        Map<Long, Long> deptMap = simpleDeptList.stream().collect(Collectors.toMap(SimpleDept::getDeptId, SimpleDept::getParentId));

        List<QueryMemberVO> memberVOList = TransferUtil.transferList(list, QueryMemberVO.class);
        memberVOList.forEach(member -> {
            if (member.getType().equals(0)) {
                SimpleUser simpleUser = UserCacheUtil.getSimpleUser(member.getUserId());
                member.setRealname(simpleUser.getNickname());
                member.setDeptName(simpleUser.getDeptName());
            } else {
                member.setDeptName(UserCacheUtil.getDeptName(member.getDeptId()));
                member.setParentDeptId(deptMap.get(member.getDeptId()));
            }
        });
        return memberVOList;
    }

    @Override
    public List<LibraryListVO> queryList(Long groupId, Integer star) {
        List<Long> libraryIds = new ArrayList<>();
        if (ObjectUtil.isNotNull(groupId)) {
            //获取当前分组信息
            KmGroup kmGroup = kmGroupService.getById(groupId);
            List<KmKnowledgeLibrary> kmKnowledgeLibraryList = lambdaQuery().list();
            if (CollUtil.isNotEmpty(kmKnowledgeLibraryList)) {
                if (ObjectUtil.equal(KmGroupTypeEnum.ALL.getType(), kmGroup.getType())) {//全部分组
                    libraryIds = kmKnowledgeLibraryList.stream().map(KmKnowledgeLibrary::getLibraryId).collect(Collectors.toList());
                } else if (ObjectUtil.equal(KmGroupTypeEnum.NONE.getType(), kmGroup.getType())) {//未分组
                    List<Long> groupLibraryIdList = groupManagementService.lambdaQuery().eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).list().stream().map(KmGroupManagement::getLibraryId).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(groupLibraryIdList)) {
                        libraryIds = kmKnowledgeLibraryList.stream().map(KmKnowledgeLibrary::getLibraryId)
                                .filter(libraryId -> !groupLibraryIdList.contains(libraryId)).distinct().collect(Collectors.toList());
                    } else {
                        libraryIds = kmKnowledgeLibraryList.stream().map(KmKnowledgeLibrary::getLibraryId).collect(Collectors.toList());
                    }
                    if (CollUtil.isEmpty(libraryIds)) {
                        libraryIds.add(0L);
                    }
                } else {
                    libraryIds = groupManagementService.lambdaQuery().eq(KmGroupManagement::getGroupId, groupId).list().stream().map(KmGroupManagement::getLibraryId).collect(Collectors.toList());
                    if (CollUtil.isEmpty(libraryIds)) {
                        libraryIds.add(0L);
                    }
                }
            }
        }

        List<LibraryListVO> libraryListVOS = getBaseMapper().queryLibraryList(UserUtil.getUserId(), UserUtil.getUser().getDeptId(), star, UserUtil.isAdmin(), libraryIds);
        libraryListVOS.forEach(libraryListVO -> {
            libraryListVO.setDocumentNumber(kmFolderMapper.queryStatusThreeNumber(libraryListVO.getLibraryId()));
            libraryListVO.setFileNumber(kmFolderMapper.queryStatusFourNumber(libraryListVO.getLibraryId()));
        });
        return libraryListVOS;
    }

    @Override
    public LibraryVO queryById(Long libraryId) {
        KmKnowledgeLibrary knowledgeLibrary = getById(libraryId);
        if (knowledgeLibrary == null) {
            throw new CrmException(KmCodeEnum.KM_DATA_EXIST_ERROR);
        }
        Long memberNum = kmKnowledgeLibraryUserService.query().eq("library_id", libraryId).count();
        LibraryVO libraryVO = new LibraryVO();
        BeanUtil.copyProperties(knowledgeLibrary, libraryVO);
        libraryVO.setMemberNum(memberNum.intValue());
        boolean count = kmCollectService.lambdaQuery().eq(KmCollect::getType, 1).eq(KmCollect::getTypeId, libraryId).exists();
        libraryVO.setCollectStatus(count);
        libraryVO.setEdit(kmAuthService.checkLibraryAuth(knowledgeLibrary));
        return libraryVO;
    }

    @Override
    public BasePage<DocumentVO> queryDocumentByLibraryId(LibraryPageBO libraryPageBO) {
        Long libraryId = libraryPageBO.getLibraryId();
        List<LibraryUserBO> userList = getBaseMapper().queryLibraryUserList(libraryId);
        Long userId = UserUtil.getUserId();
        for (LibraryUserBO libraryUser : userList) {
            if (Objects.equals(1, libraryUser.getIsOpen()) || Objects.equals(userId, libraryUser.getUserId())) {
                break;
            }
        }
        return getBaseMapper().queryDocumentByLibraryId(libraryPageBO.parse(), userId, libraryId, UserUtil.isAdmin());
    }

    @Override
    public BasePage<DocumentVO> queryCollectDocumentByLibraryId(LibraryPageBO libraryPageBO) {
        BasePage<DocumentVO> documentVOBasePage = getBaseMapper().queryCollectDocumentByLibraryId(libraryPageBO.parse(), UserUtil.getUserId(), libraryPageBO.getLibraryId());
        documentVOBasePage.getList().forEach(documentVO -> {
            if (documentVO.getType() == 4) {
                KmDocument document = kmDocumentService.getById(documentVO.getDocumentId());
                if (document.getContent() != null) {
                    int openLength = document.getContent().lastIndexOf("/") + 1;
                    int lastLength = document.getContent().length();
                    Long fielId = Long.parseLong(document.getContent().substring(openLength, lastLength));
                    Long fileSize = kmDocumentService.getFileByFileId(fielId);
                    documentVO.setFileSize(fileSize);
                    documentVO.setFileType(document.getFileType());
                }
            }
        });
        return documentVOBasePage;
    }

    @Override
    public List<KmKnowledgeLibrary> queryLibraryTemplate() {
        return KmLibraryTemplate.ME.getTemplate();
    }

    @Override
    public BasePage<SearchVO> search(SearchBO searchBO) {
        String search = searchBO.getSearch();
        if (StrUtil.isEmpty(search)) {
            throw new CrmException(KmCodeEnum.KM_SEARCH_ERROR);
        }
        Map<String, Object> map = BeanUtil.beanToMap(searchBO);
        map.put("search", search);
        boolean isAdmin = UserUtil.isAdmin();
        Long userId = UserUtil.getUserId();
        if (!isAdmin) {
            //用户可查看的
            List<Long> libraryIds = kmKnowledgeLibraryUserService.listObjs(new QueryWrapper<KmKnowledgeLibraryUser>().select("library_id").eq("user_id", userId), o -> Long.parseLong(o.toString()));
            List<Long> authIds = kmAuthUserService.listObjs(new QueryWrapper<KmAuthUser>().select("auth_id").eq("user_id", userId), o -> Long.parseLong(o.toString()));
            map.put("libraryIds", libraryIds);
            map.put("authIds", authIds);
        }
        map.put("isAdmin", isAdmin);
        return getBaseMapper().search(searchBO.parse(), map);
    }

    /**
     * 通过分组查询知识库列表
     *
     * @return
     */
    @Override
    public List<LibraryGroupListVO> queryLibraryListByGroup() {
        List<LibraryGroupListVO> libraryGroupListVOList = new ArrayList<>();
        List<KmGroup> kmGroupList = kmGroupService.lambdaQuery().eq(KmGroup::getCreateUserId, UserUtil.getUserId()).ne(KmGroup::getType, KmGroupTypeEnum.ALL.getType()).list();
        List<KmKnowledgeLibrary> kmKnowledgeLibraryList = lambdaQuery().list();
        kmGroupList.forEach(kmGroup -> {
            LibraryGroupListVO libraryGroupListVO = new LibraryGroupListVO();
            List<Long> libraryIds;
            libraryGroupListVO.setGroupId(kmGroup.getGroupId());
            libraryGroupListVO.setSort(kmGroup.getSort());
            libraryGroupListVO.setName(kmGroup.getName());
            libraryGroupListVO.setType(kmGroup.getType());
            if (ObjectUtil.equal(KmGroupTypeEnum.NONE.getType(), kmGroup.getType())) {//未分组
                List<Long> groupLibraryIdList = groupManagementService.lambdaQuery().eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId()).list()
                        .stream().map(KmGroupManagement::getLibraryId).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(groupLibraryIdList)) {
                    libraryIds = kmKnowledgeLibraryList.stream().map(KmKnowledgeLibrary::getLibraryId)
                            .filter(libraryId -> !groupLibraryIdList.contains(libraryId)).distinct().collect(Collectors.toList());
                } else {
                    libraryIds = kmKnowledgeLibraryList.stream().map(KmKnowledgeLibrary::getLibraryId).collect(Collectors.toList());
                }
            } else {
                libraryIds = groupManagementService.lambdaQuery().eq(KmGroupManagement::getCreateUserId, UserUtil.getUserId())
                        .eq(KmGroupManagement::getGroupId, kmGroup.getGroupId()).list().stream().map(KmGroupManagement::getLibraryId).collect(Collectors.toList());
            }
            if (CollUtil.isEmpty(libraryIds)) {
                libraryIds.add(0L);
            }
            List<LibraryListVO> libraryListVOS = getBaseMapper().queryLibraryList(UserUtil.getUserId(), UserUtil.getUser().getDeptId(), null, UserUtil.isAdmin(), libraryIds);
            libraryListVOS.forEach(libraryListVO -> {
                libraryListVO.setDocumentNumber(kmFolderMapper.queryStatusThreeNumber(libraryListVO.getLibraryId()));
                libraryListVO.setFileNumber(kmFolderMapper.queryStatusFourNumber(libraryListVO.getLibraryId()));
            });
            libraryGroupListVO.setLibraryListVOList(libraryListVOS);
            libraryGroupListVOList.add(libraryGroupListVO);
        });
        return libraryGroupListVOList;
    }

    @Override
    public Boolean getAISwitch() {
        List<KmKnowledgeLibrary> list =
                lambdaQuery().eq(KmKnowledgeLibrary::getAiServiceSwitch, 2).select(KmKnowledgeLibrary::getLibraryId).list();
        return CollUtil.isNotEmpty(list);
    }


}
