package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.PO.KmDocumentShare;

/**
 * <p>
 * 文档分享 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmDocumentShareService extends BaseService<KmDocumentShare> {
    public KmDocumentShare openShare(KmDocumentShare documentShare);

    public void addShareMember(KmDocumentShare documentShare);

    public void closeShare(KmDocumentShare documentShare);

    public KmDocument queryShareUrl(String token);

    public KmDocument queryShareSys(Integer documentId);
}
