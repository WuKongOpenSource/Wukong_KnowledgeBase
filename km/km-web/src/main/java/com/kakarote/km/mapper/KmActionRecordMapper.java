package com.kakarote.km.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.PO.KmActionRecord;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.VO.DeleteRecordListVO;
import com.kakarote.km.entity.VO.DeleteRecordVO;
import com.kakarote.km.entity.VO.RecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 知识库操作记录（最近使用） Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmActionRecordMapper extends BaseMapper<KmActionRecord> {
    List<RecordVO> queryRecordList(@Param("map") Map<String, Object> map);

    List<DeleteRecordVO> queryDeleteList(@Param("type") Integer type, @Param("libraryId") Long libraryId, @Param("authIdList") List<Long> authIds);

    DeleteRecordListVO queryDeleteCount(@Param("libraryId") Long libraryId, @Param("authIdList") List<Long> authIds);

    List<KmKnowledgeLibrary> queryLibraryDeleteList(@Param("userId") Long userId);

    List<Long> restoreFolderIdList(@Param("folderId") Long folderId);

    List<KmDocument> restoreDocumentList(@Param("folderId") Long folderId, @Param("documentId") Long documentId);
}
