package com.kakarote.km.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.entity.BO.CollectListBO;
import com.kakarote.km.entity.PO.*;
import com.kakarote.km.entity.VO.CollectListVO;
import com.kakarote.km.entity.VO.CollectNumberListVO;
import com.kakarote.km.mapper.KmCollectMapper;
import com.kakarote.km.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 知识库收藏表 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmCollectServiceImpl extends BaseServiceImpl<KmCollectMapper, KmCollect> implements IKmCollectService {

    @Override
    public void addCollect(KmCollect kmCollect) {
        boolean count = query().eq("type", kmCollect.getType()).eq("type_id", kmCollect.getTypeId()).eq("create_user_id", UserUtil.getUserId()).exists();
        if (count) {
            throw new CrmException(KmCodeEnum.KM_COLLECT_ERROR);
        }
        save(kmCollect);
    }

    @Override
    public void cancelCollect(KmCollect kmCollect) {
        update().eq("type", kmCollect.getType()).eq("type_id", kmCollect.getTypeId()).eq("create_user_id", UserUtil.getUserId()).remove();
    }

    @Autowired
    private IKmDocumentService documentService;

    @Autowired
    private IKmFolderService folderService;

    @Autowired
    private IKmAuthService authService;

    @Autowired
    private IKmDocumentService kmDocumentService;

    @Autowired
    private IKmAuthUserService authUserService;

    @Autowired
    private IKmKnowledgeLibraryService knowledgeLibraryService;

    @Autowired
    private IKmKnowledgeLibraryUserService knowledgeLibraryUserService;

    @Override
    public CollectNumberListVO queryList(CollectListBO collectListBO) {
        Long userId = UserUtil.getUserId();
        List<KmCollect> list = lambdaQuery().eq(KmCollect::getCreateUserId, UserUtil.getUserId()).list();
        List<Long> collectIds = new ArrayList<>();

        List<Long> removeIds = new ArrayList<>();
        if (!UserUtil.isAdmin()) {
            for (KmCollect kmCollect : list) {
                Long authId;
                Long libraryId;
                if (kmCollect.getType() == 3) {
                    KmDocument document = documentService.getById(kmCollect.getTypeId());
                    if (document == null) {
                        removeIds.add(kmCollect.getCollectId());
                        continue;
                    }
                    authId = document.getAuthId();
                    libraryId = document.getLibraryId();
                } else if (kmCollect.getType() == 1) {
                    libraryId = kmCollect.getTypeId();
                    authId = null;
                } else {
                    KmFolder folder = folderService.getById(kmCollect.getTypeId());
                    if (folder == null) {
                        removeIds.add(kmCollect.getCollectId());
                        continue;
                    }
                    authId = folder.getAuthId();
                    libraryId = folder.getLibraryId();
                }
                KmKnowledgeLibrary library = knowledgeLibraryService.getById(libraryId);
                boolean libraryCount = knowledgeLibraryUserService.lambdaQuery().eq(KmKnowledgeLibraryUser::getLibraryId, libraryId).eq(KmKnowledgeLibraryUser::getUserId, userId).exists();
                boolean libraryAuth = library.getIsOpen() == 1 || (library.getIsOpen() == 0 && libraryCount);
                if (kmCollect.getType() == 1) {
                    if (libraryAuth) {
                        collectIds.add(kmCollect.getCollectId());
                    }
                } else {
                    KmAuth kmAuth = authService.getById(authId);
                    boolean authCount = authUserService.lambdaQuery().eq(KmAuthUser::getAuthId, authId).eq(KmAuthUser::getUserId, userId).exists();
                    boolean auth = kmAuth.getIsOpen() == 1 || (kmAuth.getIsOpen() == 0 && authCount);
                    if (libraryAuth && auth) {
                        collectIds.add(kmCollect.getCollectId());
                    }
                }
            }
            if (CollUtil.isEmpty(collectIds)) {
                collectIds.add((long) -1);
            }
        }

        if (CollUtil.isNotEmpty(removeIds)) {
            removeByIds(removeIds);
        }

        BasePage<CollectListVO> page = getBaseMapper().queryList(collectListBO.parse(), UserUtil.getUserId(), collectListBO.getType(), collectListBO.getSearch(), collectIds);
        page.getList().forEach(collectListVO -> {
            if (collectListVO.getType() == 4) {
                KmDocument document = kmDocumentService.getById(collectListVO.getDocumentId());
                if (document.getContent() != null) {
                    int openLength = document.getContent().lastIndexOf("/") + 1;
                    int lastLength = document.getContent().length();
                    Long fielId = Long.parseLong(document.getContent().substring(openLength, lastLength));
                    Long fileSize = kmDocumentService.getFileByFileId(fielId);
                    collectListVO.setFileSize(fileSize);
                }
            }
        });
        CollectNumberListVO collectNumberListVO = getBaseMapper().queryListCount(UserUtil.getUserId(), collectIds);
        collectNumberListVO.setPage(page);
        return collectNumberListVO;
    }
}
