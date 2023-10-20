package com.kakarote.km.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.PO.KmDocument;
import com.kakarote.km.entity.VO.DocumentDetailVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmDocumentMapper extends BaseMapper<KmDocument> {
    DocumentDetailVO queryById(@Param("userId") Long userId, @Param("documentId") Long documentId);


    Long getFileByFileId(Long fielId);
}
