package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.QueryDeleteRecordBO;
import com.kakarote.km.entity.BO.RestoreBO;
import com.kakarote.km.entity.PO.KmActionRecord;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.VO.DeleteRecordListVO;
import com.kakarote.km.entity.VO.RecordListVO;

import java.util.List;

/**
 * <p>
 * 知识库操作记录（最近使用） 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmActionRecordService extends BaseService<KmActionRecord> {
    public void add(Integer status, Integer type, Long typeId);

    public RecordListVO queryList(Long libraryId);

    public DeleteRecordListVO queryDeleteList(QueryDeleteRecordBO queryDeleteRecordBO);

    public List<KmKnowledgeLibrary> queryLibraryDeleteList();

    public void restore(RestoreBO restoreBO);
}
