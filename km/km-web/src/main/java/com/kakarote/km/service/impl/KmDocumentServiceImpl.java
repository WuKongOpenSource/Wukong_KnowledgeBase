package com.kakarote.km.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kakarote.common.entity.SimpleUser;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.Const;
import com.kakarote.core.common.enums.SystemCodeEnum;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.core.utils.AdminLanguageUtil;
import com.kakarote.core.utils.SeparatorUtil;
import com.kakarote.core.utils.TagUtil;
import com.kakarote.ids.provider.utils.UserCacheUtil;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.common.KmEnum;
import com.kakarote.km.entity.BO.MoveDocumentBO;
import com.kakarote.km.entity.PO.*;
import com.kakarote.km.entity.VO.DocumentAuthVO;
import com.kakarote.km.entity.VO.DocumentDetailVO;
import com.kakarote.km.entity.VO.DocumentInfoVO;
import com.kakarote.km.mapper.KmDocumentMapper;
import com.kakarote.km.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmDocumentServiceImpl extends BaseServiceImpl<KmDocumentMapper, KmDocument> implements IKmDocumentService {
    @Autowired
    private IKmFolderService kmFolderService;

    @Autowired
    private IKmDocumentService kmDocumentService;

    @Autowired
    private IKmAuthService kmAuthService;

    @Autowired
    private IKmAuthUserService kmAuthUserService;

    @Autowired
    private IKmDocumentLabelService kmDocumentLabelService;

    @Autowired
    private IKmDocumentShareService kmDocumentShareService;

    @Autowired
    private IKmCollectService kmCollectService;

    @Autowired
    private IKmActionRecordService kmActionRecordService;

    @Autowired
    private IKmDocumentFavorService kmDocumentFavorService;

    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;

    private static final String IMAGE_PATTERN = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|exe|PNG|JPG|GIF|BMP|JPEG|EXE)$";

    private static final int TWO = 2;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(KmDocument kmDocument) {
        if (kmDocument.getFolderId() != null && kmDocument.getFolderId() != 0) {
            Long folderAuthId = kmFolderService.query().eq("folder_id", kmDocument.getFolderId()).one().getAuthId();

            Integer role = kmAuthService.getFolderOrDocumentAuth(folderAuthId, kmDocument.getLibraryId());

            ArrayList<Integer> noAddAuth = CollectionUtil.toList(3, 4);
            if (noAddAuth.contains(role)) {
                throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
            }
            kmDocument.setAuthId(folderAuthId);
        } else if (kmDocument.getParentId() != null && kmDocument.getParentId() != 0) {
            kmDocument.setAuthId(kmDocumentService.query().eq("document_id", kmDocument.getParentId()).one().getAuthId());
        } else {
            KmAuth kmAuth = new KmAuth();
            kmAuth.setIsOpen(1);
            kmAuth.setCreateUserId(UserUtil.getUserId());
            kmAuth.setOpenAuth(3);
            kmAuthService.save(kmAuth);

            Integer libraryIdRole = kmAuthService.getLibraryIdRole(kmDocument.getLibraryId(), UserUtil.getUserId(), UserUtil.getUser().getDeptId());

            if (ObjectUtil.isNull(libraryIdRole)) {
                throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
            }
            KmAuthUser kmAuthUser = new KmAuthUser();
            kmAuthUser.setAuthId(kmAuth.getAuthId());
            kmAuthUser.setCreateUserId(UserUtil.getUserId());
            kmAuthUser.setCreateTime(LocalDateTimeUtil.now());
            kmAuthUser.setAuth(KmEnum.PRIVATE_MANAGE_AUTH.getValue());
            kmAuthUser.setUserId(UserUtil.getUserId());
            kmAuthUserService.save(kmAuthUser);
            kmDocument.setAuthId(kmAuth.getAuthId());
        }
        if (ObjectUtil.notEqual(0, kmDocument.getFolderId())) {
            kmDocument.setParentId(0L);
        }
        save(kmDocument);
    }

    @Override
    public void set(KmDocument kmDocument) {
        KmDocument document = getById(kmDocument.getDocumentId());
        Integer role = kmAuthService.getFolderOrDocumentAuth(document.getAuthId(), document.getLibraryId());

        //不可修改权限
        ArrayList<Integer> notUpdateRole = CollectionUtil.toList(4, 3);
        if (notUpdateRole.contains(role)) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        }
        if (ObjectUtil.notEqual(0, kmDocument.getFolderId())) {
            kmDocument.setParentId(0L);
        }
        updateById(kmDocument);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(MoveDocumentBO moveDocumentBO) {
        Integer type = moveDocumentBO.getType();
        Long documentId = moveDocumentBO.getDocumentId();
        Long authId = moveDocumentBO.getAuthId();
        Long targetId = moveDocumentBO.getTargetId();
        Long libraryId = moveDocumentBO.getLibraryId();
        if (documentId == null) {
            documentId = 0L;
        }
        KmDocument document = getById(documentId);
        if (!UserUtil.isAdmin()) {
            Integer role;
            if (authId != null) {
                role = kmAuthService.getFolderOrDocumentAuth(authId, document.getLibraryId());
            } else {
                role = 1;
            }

            if (targetId == 0) {
                //移动到知识库根目录
                boolean b = !role.equals(KmEnum.PRIVATE_MANAGE_AUTH.getValue()) || !kmAuthService.checkLibraryAuth(libraryId);
                if (b) {
                    throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
                }
            } else {
                boolean b;
                if (type == TWO) {
                    KmFolder folder = kmFolderService.getById(targetId);
                    b = !role.equals(KmEnum.PRIVATE_MANAGE_AUTH.getValue()) ||
                            !kmAuthService.getFolderOrDocumentAuth(folder.getAuthId(), folder.getLibraryId()).equals(KmEnum.PRIVATE_MANAGE_AUTH.getValue());
                } else {
                    b = !role.equals(KmEnum.PRIVATE_MANAGE_AUTH.getValue()) ||
                            !kmAuthService.getFolderOrDocumentAuth(getById(targetId).getAuthId(), document.getLibraryId()).equals(KmEnum.PRIVATE_MANAGE_AUTH.getValue());
                }
                if (b) {
                    throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
                }
            }
        }

        document.setLibraryId(libraryId);
        if (Objects.equals(TWO, type)) {
            document.setParentId(0L);
            document.setFolderId(targetId);
        } else {
            document.setFolderId(0L);
            document.setParentId(targetId);
        }
        document.setUpdateTime(LocalDateTimeUtil.now());
        updateById(document);
    }

    @Override
    public DocumentDetailVO queryById(Long documentId) {
        DocumentDetailVO document = getBaseMapper().queryById(UserUtil.getUserId(), documentId);
        if (document == null) {
            throw new CrmException(KmCodeEnum.KM_DATA_EXIST_ERROR);
        }
        document.setCreateUser(UserCacheUtil.getSimpleUser(document.getCreateUserId()));
        DocumentAuthVO auth = kmAuthService.queryAuth(document.getAuthId(), document.getLibraryId());
        Integer currentUserAuth = auth.getCurrentUserAuth();
        if ((currentUserAuth == null || currentUserAuth < 1)) {
            //权限不足
            return new DocumentDetailVO().setCurrentUserAuth(currentUserAuth);
        }
        document.setAuth(auth).setCurrentUserAuth(currentUserAuth);
        if (SeparatorUtil.toSetL(document.getLabelIds()).size() > 0) {
            List<KmDocumentLabel> labelList = kmDocumentLabelService.query().select("label_id", "name", "color").in("label_id", SeparatorUtil.toSetL(document.getLabelIds())).list();
            document.setLabelList(labelList);
        }
        KmDocumentShare share = kmDocumentShareService.query().eq("status", 1).eq("document_id", documentId).one();
        if (share != null) {
            List<SimpleUser> userList = UserCacheUtil.getSimpleUsers(ListUtil.toList(TagUtil.toLongSet(share.getShareUserIds())));
            share.setUserList(userList);
        }
        document.setShare(share);
        List<KmDocumentFavor> list = kmDocumentFavorService.lambdaQuery().eq(KmDocumentFavor::getDocumentId, document.getDocumentId()).select(KmDocumentFavor::getCreateUserId).list();
        List<SimpleUser> starUserList = new ArrayList<>();
        if (list.size() > 0) {
            starUserList.addAll(UserCacheUtil.getSimpleUsers(list.stream().map(KmDocumentFavor::getCreateUserId).collect(Collectors.toList())));
        }
        document.setStarUserList(starUserList);
        kmActionRecordService.add(1, document.getType(), documentId);
        KmKnowledgeLibrary library = kmKnowledgeLibraryService.getById(document.getLibraryId());
        document.setLibraryIsOpen(library.getIsOpen());
        if (document.getType() == 4) {
            KmDocument kmDocument = kmDocumentService.getById(document.getDocumentId());
            if (kmDocument.getContent() != null) {
                int openLength = kmDocument.getContent().lastIndexOf("/") + 1;
                int lastLength = kmDocument.getContent().length();
                Long fielId = Long.parseLong(kmDocument.getContent().substring(openLength, lastLength));
                Long fileSize = kmDocumentService.getFileByFileId(fielId);
                document.setFileSize(fileSize);
                document.setFileType(kmDocument.getFileType());
            }
        }
        return document;
    }

    @Override
    public DocumentInfoVO queryInfoById(Long documentId) {
        KmDocument document = getById(documentId);
        if (document == null) {
            throw new CrmException(KmCodeEnum.KM_DATA_EXIST_ERROR);
        }
        Integer auth = kmAuthService.getFolderOrDocumentAuth(document.getAuthId(), document.getLibraryId());
        DocumentInfoVO documentInfoVO = new DocumentInfoVO();
        if (auth == null || auth < 1) {
            KmDocumentShare share = kmDocumentShareService.query().eq("document_id", documentId).one();
            if (share == null) {
                throw new CrmException(KmCodeEnum.KM_SHARE_EXIST_ERROR);
            }
            if (share.getStatus() == 0) {
                throw new CrmException(KmCodeEnum.KM_SHARE_INVALID_ERROR);
            }
            //权限不足,查看分享详情
            return documentInfoVO.setType("share").setData(share.getToken());
        } else {
            return documentInfoVO.setType("info").setData(documentId.toString()).setFolderId(document.getFolderId()).setLibraryId(document.getLibraryId());
        }
    }

    @Override
    public void delete(Long documentId) {
        delete(documentId, true, Const.AUTH_DATA_RECURSION_NUM);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long documentId, Boolean addRecord, Integer num) {
        if (num <= 0) {
            throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
        }
        KmDocument document = getById(documentId);
        if (document == null || document.getStatus() == -1) {
            return;
        }
        if (!UserUtil.isAdmin()) {
            ArrayList<Integer> notDeleteAuth = CollectionUtil.toList(3, 4, 5);
            Integer auth = kmAuthService.getFolderOrDocumentAuth(document.getAuthId(), document.getLibraryId());
            if (notDeleteAuth.contains(auth)) {
                throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
            }
        }
        List<Long> documentIds = listObjs(new QueryWrapper<KmDocument>().eq("parent_id", documentId).ne("status", -1), o -> Long.valueOf(o.toString()));
        documentIds.forEach(d -> {
            Integer i = num - 1;
            delete(d, false, i);
        });
        update().set("status", -1).set("delete_time", new Date()).set("delete_user_id", UserUtil.getUserId()).eq("document_id", documentId).update();
        if (addRecord) {
            kmActionRecordService.add(2, document.getType(), documentId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completelyDelete(Long documentId) {
        KmDocument document = getById(documentId);
        if (document == null || document.getStatus() != -1) {
            return;
        }
        List<KmDocument> documentList = query().eq("parent_id", document.getDocumentId()).list();
        documentList.forEach(doc -> completelyDelete(doc.getDocumentId()));
        delete(documentId);
        kmCollectService.update().eq("type_id", documentId).in("type", Arrays.asList("3", "4")).remove();
        kmActionRecordService.update().eq("type_id", documentId).in("type", Arrays.asList("3", "4")).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void favor(KmDocumentFavor kmDocumentFavor) {
        Long userId = UserUtil.getUserId();
        boolean count = kmDocumentFavorService.query().eq("document_id", kmDocumentFavor.getDocumentId()).eq("create_user_id", userId).exists();
        if (count) {
            kmDocumentFavorService.update().eq("document_id", kmDocumentFavor.getDocumentId()).eq("create_user_id", userId).remove();
        } else {
            kmDocumentFavorService.save(kmDocumentFavor);
        }
    }

    @Override
    public Long getFileByFileId(Long fielId) {
        return baseMapper.getFileByFileId(fielId);
    }

    /**
     * 功能描述: <br>
     * 〈获取字段与语言包key关联〉
     *
     * @param:
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @throws:
     * @author: zyh
     * @date: 2022/6/21 15:50
     */
    @Override
    public List<Map<String, Object>> getAllFieldLanguageRel() {
        List<Map<String, Object>> listMap = new ArrayList<>();

        //错误信息
        for (KmCodeEnum codeEnum : KmCodeEnum.values()) {
            //添加语言包key
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", "error." + codeEnum.getCode());
            map.put("chinese", AdminLanguageUtil.repalceMsg(codeEnum.getMsg()));
            map.put("translateName", "");
            listMap.add(map);
        }

        return listMap;
    }

}
