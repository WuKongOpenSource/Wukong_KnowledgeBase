package com.kakarote.km.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.Const;
import com.kakarote.core.common.enums.SystemCodeEnum;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.ids.provider.utils.UserCacheUtil;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.common.KmEnum;
import com.kakarote.km.entity.BO.MoveFolderBO;
import com.kakarote.km.entity.PO.*;
import com.kakarote.km.entity.VO.*;
import com.kakarote.km.mapper.KmFolderMapper;
import com.kakarote.km.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 知识库文件夹 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmFolderServiceImpl extends BaseServiceImpl<KmFolderMapper, KmFolder> implements IKmFolderService {
    @Autowired
    private IKmAuthService kmAuthService;

    @Autowired
    private IKmAuthUserService kmAuthUserService;

    @Autowired
    private IKmActionRecordService kmActionRecordService;

    @Autowired
    private IKmDocumentService kmDocumentService;

    @Autowired
    private IKmCollectService kmCollectService;

    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;

    @Autowired
    private IKmFolderService iKmFolderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(KmFolder kmFolder) {
        if (kmFolder.getParentId() == null || kmFolder.getParentId() == 0) {
            KmAuth kmAuth = new KmAuth();
            kmAuth.setIsOpen(1);
            kmAuth.setCreateUserId(UserUtil.getUserId());
            kmAuth.setOpenAuth(3);
            kmAuthService.save(kmAuth);

            KmAuthUser kmAuthUser = new KmAuthUser();
            kmAuthUser.setAuthId(kmAuth.getAuthId());
            kmAuthUser.setAuth(KmEnum.PRIVATE_MANAGE_AUTH.getValue());
            kmAuthUser.setUserId(UserUtil.getUserId());
            kmAuthUserService.save(kmAuthUser);
            kmFolder.setAuthId(kmAuth.getAuthId());
        } else {
            kmFolder.setAuthId(query().select("auth_id").eq("folder_id", kmFolder.getParentId()).one().getAuthId());
        }
        save(kmFolder);
    }

    @Override
    public void set(KmFolder kmFolder) {
        KmFolder folder = getById(kmFolder.getFolderId());
        Integer role = kmAuthService.getFolderOrDocumentAuth(folder.getAuthId(), folder.getLibraryId());

        //不可修改权限
        ArrayList<Integer> notUpdateRole = CollectionUtil.toList(4, 5);
        if (notUpdateRole.contains(role)) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        }
        updateById(kmFolder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long folderId) {
        delete(folderId, true, Const.AUTH_DATA_RECURSION_NUM);
    }


    private void delete(Long folderId, Boolean addRecord, Integer num) {
        KmFolder folder = getById(folderId);
        if (!UserUtil.isAdmin()) {
            ArrayList<Integer> notDeleteAuth = CollectionUtil.toList(3, 4, 5);
            Integer auth = kmAuthService.getFolderOrDocumentAuth(folder.getAuthId(), folder.getLibraryId());
            if (notDeleteAuth.contains(auth)) {
                throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
            }
        }
        List<Long> documentIds = kmDocumentService.listObjs(new QueryWrapper<KmDocument>().eq("folder_id", folderId), o -> Long.valueOf(o.toString()));
        List<Long> folderIds = listObjs(new QueryWrapper<KmFolder>().eq("parent_id", folderId), o -> Long.valueOf(o.toString()));
        documentIds.forEach(documentId -> {
            Integer i = num - 1;
            kmDocumentService.delete(documentId, false, i);
        });
        folderIds.forEach(id -> {
            Integer i = num - 1;
            delete(id, false, i);
        });
        update().set("status", -1).set("delete_user_id", UserUtil.getUserId()).set("delete_time", new Date()).eq("folder_id", folderId).update();
        if (addRecord) {
            kmActionRecordService.add(2, 2, folderId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completelyDelete(Long folderId) {
        List<Long> folderIds = listObjs(new QueryWrapper<KmFolder>().eq("parent_id", folderId), o -> Long.valueOf(o.toString()));
        List<Long> documentIds = kmDocumentService.listObjs(new QueryWrapper<KmDocument>().eq("folder_id", folderId), o -> Long.valueOf(o.toString()));
        folderIds.forEach(this::completelyDelete);
        documentIds.forEach(documentId -> kmDocumentService.completelyDelete(documentId));
        removeById(folderId);
        kmCollectService.update().eq("type_id", folderId).eq("type", 2).remove();
        kmActionRecordService.update().eq("type_id", folderId).eq("type", 2).remove();
    }

    @Override
    public TreeListVO queryTreeList(Long libraryId) {
        Long userId = UserUtil.getUserId();
        Long deptId = UserUtil.getUser().getDeptId();

        KmKnowledgeLibrary library = kmKnowledgeLibraryService.getById(libraryId);
        if (ObjectUtil.equals(library.getIsOpen(), 0)) {
            Integer libraryIdRole = kmAuthService.getLibraryIdRole(libraryId, userId, deptId);
            if (libraryIdRole == 0) {
                TreeListVO root = new TreeListVO();
                root.setTitle("root");
                root.setChildList(new ArrayList<>());
                root.setKmDocumentList(new ArrayList<>());
                root.setCurrentUserAuth(0);
                return root;
            }
        }

        List<FolderTreeVO> folderList = getBaseMapper().queryFolderList(userId, deptId, libraryId, UserUtil.isAdmin());

        List<DocumentTreeVO> documentList = getBaseMapper().queryFirstDocumentList(userId, deptId, libraryId, UserUtil.isAdmin());
        documentList.forEach(document -> queryChildDocument(document, Const.AUTH_DATA_RECURSION_NUM));
        List<FolderTreeVO> treeList = buildTreeBy2Loop(folderList);
        TreeListVO root = new TreeListVO();
        root.setTitle("root");
        root.setChildList(treeList);
        root.setKmDocumentList(documentList);
        root.setCurrentUserAuth(1);
        return root;
    }

    private List<FolderTreeVO> buildTreeBy2Loop(List<FolderTreeVO> treeNodes) {
        List<FolderTreeVO> trees = new ArrayList<>();
        for (FolderTreeVO node : treeNodes) {
            List<DocumentTreeVO> kmDocumentList = getBaseMapper().queryFolderDocumentList(node.getFolderId());
            kmDocumentList.forEach(document -> {
                queryChildDocument(document, Const.AUTH_DATA_RECURSION_NUM);
            });
            node.setKmDocumentList(kmDocumentList);
            if (Objects.equals(0L, node.getParentId())) {
                trees.add(node);
            }
            List<FolderTreeVO> childTrees = new ArrayList<>();
            for (FolderTreeVO treeNode : treeNodes) {
                if (node.getFolderId().equals(treeNode.getParentId())) {
                    childTrees.add(treeNode);
                }
            }
            node.setChildList(childTrees);
        }
        return trees;
    }

    private void queryChildDocument(DocumentTreeVO document, Integer num) {
        List<DocumentTreeVO> childKmDocuments = getBaseMapper().queryChildKmDocumentList(document.getDocumentId());
        document.setChildList(childKmDocuments);
        Integer finalNum = --num;
        if (finalNum > 0) {
            childKmDocuments.forEach(childDocument -> queryChildDocument(childDocument, finalNum));
        }
    }

    private List<Long> folderids = new ArrayList<>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(MoveFolderBO moveFolderBO) {
        Long folderId = moveFolderBO.getFolderId();
        Long targetId = moveFolderBO.getTargetId();
        Long libraryId = moveFolderBO.getLibraryId();
        List<FolderTreeVO> folderList = getBaseMapper().queryFolderList(UserUtil.getUserId(), UserUtil.getUser().getDeptId(), libraryId, UserUtil.isAdmin());
        List<Long> list = queryChildList(folderList, targetId);
        if (Objects.equals(folderId, targetId) || list.contains(targetId)) {
            throw new CrmException(KmCodeEnum.KM_MOVE_FOLDER_ERROR);
        }
        KmFolder folder = getById(folderId);
        folder.setParentId(targetId);
        folder.setLibraryId(libraryId);
        queryAllFolder(folderId);
        updateById(folder);
        folderids.forEach(folderid -> {
            iKmFolderService.lambdaUpdate().eq(KmFolder::getFolderId, folderid).set(KmFolder::getLibraryId, moveFolderBO.getLibraryId()).update();
        });
    }

    public void queryAllFolder(Long folderId) {
        List<KmFolder> kmFolders = iKmFolderService.lambdaQuery().select().eq(KmFolder::getParentId, folderId).list();
        kmFolders.forEach(kmFolder -> {
            folderids.add(kmFolder.getFolderId());
            queryAllFolder(kmFolder.getFolderId());
        });
    }

    private List<Long> queryChildList(List<FolderTreeVO> treeNodes, Long root) {
        List<Long> trees = new ArrayList<>();
        for (FolderTreeVO node : treeNodes) {
            if (root.equals(node.getParentId())) {
                trees.add(node.getFolderId());
                trees.addAll(queryChildList(treeNodes, node.getFolderId()));
            }
        }
        return trees;
    }

    @Override
    public FolderDetailVO queryById(Long folderId) {
        FolderDetailVO folder = getBaseMapper().queryById(UserUtil.getUserId(), folderId);
        if (folder == null) {
            throw new CrmException(KmCodeEnum.KM_DATA_EXIST_ERROR);
        }
        folder.setCreateUser(UserCacheUtil.getSimpleUser(folder.getCreateUserId()));
        List<DocumentVO> documentVOList = getBaseMapper().queryDocumentByFolderId(UserUtil.getUserId(), folderId, UserUtil.isAdmin());
        List<FolderVO> childFolderList = getBaseMapper().queryChildFolderByFolderId(UserUtil.getUserId(), folderId, UserUtil.isAdmin());
        folder.setKmDocumentList(documentVOList);
        folder.setChildList(childFolderList);
        folder.setAuth(kmAuthService.queryAuth(folder.getAuthId(), folder.getLibraryId()));
        kmActionRecordService.add(1, 2, folderId);
        KmKnowledgeLibrary library = kmKnowledgeLibraryService.getById(folder.getLibraryId());
        folder.setLibraryIsOpen(library.getIsOpen());
        return folder;
    }
}
