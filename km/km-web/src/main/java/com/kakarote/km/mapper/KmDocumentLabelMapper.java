package com.kakarote.km.mapper;

import com.kakarote.core.entity.BasePage;
import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.PO.KmDocumentLabel;
import com.kakarote.km.entity.VO.SearchVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文档标签表 Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmDocumentLabelMapper extends BaseMapper<KmDocumentLabel> {
    BasePage<SearchVO> queryDocumentByLabelId(BasePage<SearchVO> page, @Param("isAdmin") boolean isAdmin, @Param("userId") Long userId, @Param("labelId") Long labelId);
}
