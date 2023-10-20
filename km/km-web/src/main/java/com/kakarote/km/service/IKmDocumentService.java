package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.MoveDocumentBO;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.PO.KmDocumentFavor;
import com.kakarote.km.entity.VO.DocumentDetailVO;
import com.kakarote.km.entity.VO.DocumentInfoVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmDocumentService extends BaseService<KmDocument> {
    public void add(KmDocument kmDocument);

    public void set(KmDocument kmDocument);

    public void move(MoveDocumentBO moveDocumentBO);

    public DocumentDetailVO queryById(Long documentId);

    public DocumentInfoVO queryInfoById(Long documentId);

    public void delete(Long documentId);

    public void delete(Long documentId, Boolean addRecord, Integer num);

    public void completelyDelete(Long documentId);

//    public List<KmDocument> queryList(QueryDocumentListBO queryDocumentListBO);

    public void favor(KmDocumentFavor kmDocumentFavor);

    Long getFileByFileId(Long fielId);


    public List<Map<String, Object>> getAllFieldLanguageRel();

}
