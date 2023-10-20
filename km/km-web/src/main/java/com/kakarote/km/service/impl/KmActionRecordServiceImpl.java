package com.kakarote.km.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.km.entity.BO.QueryDeleteRecordBO;
import com.kakarote.km.entity.BO.RestoreBO;
import com.kakarote.km.entity.PO.*;
import com.kakarote.km.entity.VO.DeleteRecordListVO;
import com.kakarote.km.entity.VO.DeleteRecordVO;
import com.kakarote.km.entity.VO.RecordListVO;
import com.kakarote.km.entity.VO.RecordVO;
import com.kakarote.km.mapper.KmActionRecordMapper;
import com.kakarote.km.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 知识库操作记录（最近使用） 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmActionRecordServiceImpl extends BaseServiceImpl<KmActionRecordMapper, KmActionRecord> implements IKmActionRecordService {
    @Autowired
    private IKmAuthUserService kmAuthUserService;

    @Autowired
    private IKmKnowledgeLibraryService knowledgeLibraryService;

    @Autowired
    private IKmDocumentService documentService;

    @Autowired
    private IKmFolderService folderService;

    @Autowired
    private IKmAuthService authService;

    @Autowired
    private IKmAuthUserService authUserService;

    @Autowired
    private IKmKnowledgeLibraryUserService knowledgeLibraryUserService;

    @Override
    public void add(Integer status, Integer type, Long typeId) {
        KmActionRecord kmActionRecord = new KmActionRecord();
        kmActionRecord.setType(type);
        kmActionRecord.setStatus(status);
        kmActionRecord.setTypeId(typeId);
        save(kmActionRecord);
    }

    @Override
    public RecordListVO queryList(Long libraryId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserUtil.getUserId());
        map.put("libraryId", libraryId);
        map.put("type", 1);
        List<RecordVO> todayList = filter(getBaseMapper().queryRecordList(map));
        map.put("type", 2);
        List<RecordVO> yesterdayList = filter(getBaseMapper().queryRecordList(map));
        map.put("type", 3);
        List<RecordVO> monthList = filter(getBaseMapper().queryRecordList(map));
        return new RecordListVO()
                .setTodayList(todayList)
                .setYesterdayList(yesterdayList)
                .setMonthList(monthList);
    }

    /**
     * 过滤没有权限的文档
     *
     * @param list
     * @return
     */
    private List<RecordVO> filter(List<RecordVO> list) {
        if (!UserUtil.isAdmin()) {
            Long userId = UserUtil.getUserId();
            List<RecordVO> recordVOList = new ArrayList<>();
            for (RecordVO record : list) {
                Long authId = null;
                Long libraryId = null;
                if (record.getType() == 3) {
                    KmDocument document = documentService.getById(record.getTypeId());
                    authId = document.getAuthId();
                    libraryId = document.getLibraryId();
                } else {
                    KmFolder folder = folderService.getById(record.getTypeId());
                    if (folder != null) {
                        authId = folder.getAuthId();
                        libraryId = folder.getLibraryId();
                    }
                }
                KmKnowledgeLibrary library = knowledgeLibraryService.getById(libraryId);
                boolean libraryCount = knowledgeLibraryUserService.lambdaQuery().eq(KmKnowledgeLibraryUser::getLibraryId, libraryId).eq(KmKnowledgeLibraryUser::getUserId, userId).exists();
                boolean libraryAuth = false;
                if (library != null) {
                    libraryAuth = library.getIsOpen() == 1 || (library.getIsOpen() == 0 && libraryCount);
                }
                KmAuth kmAuth = authService.getById(authId);
                boolean authCount = authUserService.lambdaQuery().eq(KmAuthUser::getAuthId, authId).eq(KmAuthUser::getUserId, userId).exists();
                boolean auth = false;
                if (kmAuth != null) {
                    auth = kmAuth.getIsOpen() == 1 || (kmAuth.getIsOpen() == 0 && authCount);
                }
                if (libraryAuth && auth) {
                    recordVOList.add(record);
                }
            }
            recordVOList.forEach(recordVO -> {
                if (recordVO.getType() == 4) {
                    KmDocument document = documentService.getById(recordVO.getDocumentId());
                    if (document.getContent() != null) {
                        int openLength = document.getContent().lastIndexOf("/") + 1;
                        int lastLength = document.getContent().length();
                        Long fielId = Long.parseLong(document.getContent().substring(openLength, lastLength));
                        Long fileSize = documentService.getFileByFileId(fielId);
                        recordVO.setFileSize(fileSize);
                    }
                }
            });
            return recordVOList;
        } else {
            list.forEach(recordVO -> {
                if (recordVO.getType() == 4) {
                    KmDocument document = documentService.getById(recordVO.getDocumentId());
                    if (document.getContent() != null) {
                        int openLength = document.getContent().lastIndexOf("/") + 1;
                        int lastLength = document.getContent().length();
                        Long fielId = Long.parseLong(document.getContent().substring(openLength, lastLength));
                        Long fileSize = documentService.getFileByFileId(fielId);
                        recordVO.setFileSize(fileSize);
                    }
                }
            });
            return list;
        }
    }

    @Override
    public DeleteRecordListVO queryDeleteList(QueryDeleteRecordBO queryDeleteRecordBO) {
        Integer type = queryDeleteRecordBO.getType();
        QueryWrapper<KmAuthUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", UserUtil.getUserId());
        wrapper.select("auth_id");
        List<Long> authIds = kmAuthUserService.listObjs(wrapper, o -> Long.valueOf(o.toString()));
        List<DeleteRecordVO> recordList = getBaseMapper().queryDeleteList(type, queryDeleteRecordBO.getLibraryId(), authIds);
        recordList.forEach(record -> {
            if (record.getType() == 4) {
                KmDocument document = documentService.getById(record.getDocumentId());
                if (document.getContent() != null) {
                    int openLength = document.getContent().lastIndexOf("/") + 1;
                    int lastLength = document.getContent().length();
                    Long fielId = Long.parseLong(document.getContent().substring(openLength, lastLength));
                    Long fileSize = documentService.getFileByFileId(fielId);
                    record.setFileSize(fileSize);
                }
            }
        });
        DeleteRecordListVO deleteRecordListVO = getBaseMapper().queryDeleteCount(queryDeleteRecordBO.getLibraryId(), authIds);
        return deleteRecordListVO.setList(recordList);
    }

    @Override
    public List<KmKnowledgeLibrary> queryLibraryDeleteList() {
        return getBaseMapper().queryLibraryDeleteList(UserUtil.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restore(RestoreBO restoreBO) {
        update().eq("type", restoreBO.getType()).eq("type_id", restoreBO.getId()).remove();
        restore(restoreBO.getId(), restoreBO.getType(), true);
    }

    public void restore(Long id, Integer type, boolean isVerify) {
        int two = 2;
        int three = 3;
        int four = 4;
        if (1 == type) {
            knowledgeLibraryService.update().setSql("status = 1,delete_user_id =null,delete_time = null").eq("library_id", id).update();
            List<Long> typeIds = listObjs(new QueryWrapper<KmActionRecord>().select("type_id").eq("status", 2).eq("type", 3), o -> Long.valueOf(o.toString()));
            documentService.update().setSql("status = 1,delete_user_id =null,delete_time = null").eq("library_id", id).notIn(CollUtil.isNotEmpty(typeIds), "document_id", typeIds).update();
            typeIds.clear();
            typeIds = listObjs(new QueryWrapper<KmActionRecord>().select("type_id").eq("status", 2).eq("type", 2), o -> Long.valueOf(o.toString()));
            folderService.update().setSql("status = 1,delete_user_id =null,delete_time = null").eq("library_id", id).notIn(CollUtil.isNotEmpty(typeIds), "folder_id", typeIds).update();
        } else if (two == type) {
            List<Long> folderIds = getBaseMapper().restoreFolderIdList(id);
            KmFolder folder = folderService.getById(id);
            if (folder.getParentId() != null && folder.getParentId() != 0) {
                KmFolder parentFolder = folderService.getById(folder.getParentId());
                if (parentFolder == null || parentFolder.getStatus() == -1) {
                    folder.setParentId(0L);
                }
            }
            folder.setDeleteUserId(null);
            folder.setDeleteTime(null);
            folder.setStatus(1);
            folderService.updateById(folder);
            folderIds.forEach(folderId -> {
                restore(folderId, type, false);
            });
            List<KmDocument> documentList = getBaseMapper().restoreDocumentList(id, null);
            documentList.forEach(document -> {
                restore(document.getDocumentId(), document.getType(), false);
            });
        } else if (three == type) {
            restoreDocument(id, isVerify);
            List<KmDocument> documentList = getBaseMapper().restoreDocumentList(null, id);
            documentList.forEach(document -> {
                restore(document.getDocumentId(), document.getType(), false);
            });
        } else if (four == type) {
            restoreDocument(id, isVerify);
        }
    }

    private void restoreDocument(Long id, boolean isVerify) {
        KmDocument document = documentService.getById(id);
        if (isVerify) {
            if (document.getFolderId() != null && document.getFolderId() != 0) {
                KmFolder kmFolder = folderService.getById(document.getFolderId());
                if (kmFolder == null || kmFolder.getStatus() == -1) {
                    document.setFolderId(0L);
                    document.setParentId(0L);
                }
            } else {
                KmDocument kmDocument = documentService.getById(document.getParentId());
                if (kmDocument == null || kmDocument.getStatus() == -1) {
                    document.setParentId(0L);
                    document.setFolderId(0L);
                }
            }
        }
        document.setDeleteUserId(null);
        document.setDeleteTime(null);
        document.setStatus(1);
        documentService.updateById(document);
    }
}
