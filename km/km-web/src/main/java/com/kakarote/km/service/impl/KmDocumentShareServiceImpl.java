package com.kakarote.km.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.Const;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.feign.admin.entity.AdminMessageBO;
import com.kakarote.core.feign.admin.entity.AdminMessageEnum;
import com.kakarote.core.feign.admin.service.AdminMessageService;
import com.kakarote.core.redis.Redis;
import com.kakarote.core.servlet.ApplicationContextHolder;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.common.KmConst;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.PO.KmDocumentShare;
import com.kakarote.km.mapper.KmDocumentShareMapper;
import com.kakarote.km.service.IKmDocumentService;
import com.kakarote.km.service.IKmDocumentShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * <p>
 * 文档分享 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmDocumentShareServiceImpl extends BaseServiceImpl<KmDocumentShareMapper, KmDocumentShare> implements IKmDocumentShareService {
    @Autowired
    private Redis redis;

    @Autowired
    private IKmDocumentService kmDocumentService;

    @Value("${document.share.url:''}")
    private String documentShareUrl;

    @Value("preview.file.url:''")
    private String previewFileUrl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KmDocumentShare openShare(KmDocumentShare documentShare) {
        Long documentId = documentShare.getDocumentId();
        boolean shareStatus = query().eq("status", 1).eq("document_id", documentId).exists();
        if (shareStatus) {
            throw new CrmException(KmCodeEnum.KM_SHARING_ERROR);
        }
        String token = IdUtil.simpleUUID();
        String shareUrl;
        KmDocument document = kmDocumentService.getById(documentId);
        int three = 3;
        if (document.getType() == three) {
            String shareUrlPrefix = documentShareUrl;
            shareUrl = shareUrlPrefix + token;
        } else {
            String shareUrlPrefix = previewFileUrl;
            String fileId = document.getContent().substring(document.getContent().indexOf('?'));
            shareUrl = shareUrlPrefix + fileId + "&token=" + token;
            //TODO 永久token
            redis.set(KmConst.SHARE_CACHE_KEY + token, token);
        }
        documentShare.setCreateTime(LocalDateTimeUtil.now());
        documentShare.setCreateUserId(UserUtil.getUserId());
        documentShare.setStatus(1);
        documentShare.setToken(token);
        documentShare.setShareUrl(shareUrl);
        update().eq("document_id", documentId).remove();
        save(documentShare);
        addMessage(documentShare);
        return documentShare;
    }

    private void addMessage(KmDocumentShare documentShare) {
        KmDocument kmDocument = kmDocumentService.getById(documentShare.getDocumentId());
        if (kmDocument == null) {
            return;
        }
        AdminMessageBO adminMessageBO = new AdminMessageBO();
        adminMessageBO.setTitle(kmDocument.getTitle());
        adminMessageBO.setMessageType(AdminMessageEnum.KM_DOCUMENT_SHARE_OPEN.getType());
        adminMessageBO.setTypeId(documentShare.getDocumentId());
        adminMessageBO.setUserId(UserUtil.getUserId());
        adminMessageBO.setIds(StrUtil.splitTrim(documentShare.getShareUserIds(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList()));
        ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addShareMember(KmDocumentShare documentShare) {
        addMessage(documentShare);
        updateById(documentShare);
    }


    @Override
    public void closeShare(KmDocumentShare documentShare) {
        documentShare.setStatus(0);
        documentShare.setCloseTime(LocalDateTimeUtil.now());
        documentShare.setCloseUserId(UserUtil.getUserId());
        updateById(documentShare);
        redis.del(KmConst.SHARE_CACHE_KEY + documentShare.getToken());
    }

    @Override
    public KmDocument queryShareUrl(String token) {
        KmDocumentShare share = query().eq("token", token).one();
        if (share == null) {
            throw new CrmException(KmCodeEnum.KM_SHARE_EXIST_ERROR);
        }
        if (share.getStatus() == 0) {
            throw new CrmException(KmCodeEnum.KM_SHARE_INVALID_ERROR);
        }
        KmDocument document = kmDocumentService.getById(share.getDocumentId());
        if (document.getStatus() == -1) {
            throw new CrmException(KmCodeEnum.KM_DATA_EXIST_ERROR);
        }
        return document;
    }

    @Override
    public KmDocument queryShareSys(Integer documentId) {
        KmDocumentShare share = query().eq("status", 1).eq("document_id", documentId).one();
        if (share == null) {
            throw new CrmException(KmCodeEnum.KM_SHARE_INVALID_ERROR);
        }
        return kmDocumentService.getById(documentId);
    }
}
