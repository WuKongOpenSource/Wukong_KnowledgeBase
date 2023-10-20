package com.kakarote.km.mapper;

import com.kakarote.core.entity.BasePage;
import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.BO.LibraryUserBO;
import com.kakarote.km.entity.BO.SearchBO;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.VO.DocumentVO;
import com.kakarote.km.entity.VO.LibraryListVO;
import com.kakarote.km.entity.VO.SearchVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 知识库 Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmKnowledgeLibraryMapper extends BaseMapper<KmKnowledgeLibrary> {

    List<Long> queryLibraryAuth(@Param("libraryId") Long libraryId);

    List<LibraryListVO> queryLibraryList(@Param("userId") Long userId, @Param("deptId") Long deptId, @Param("star") Integer star, @Param("isAdmin") boolean isAdmin, @Param("libraryIds") List<Long> libraryIds);

    List<LibraryUserBO> queryLibraryUserList(@Param("libraryId") Long libraryId);

    BasePage<DocumentVO> queryDocumentByLibraryId(BasePage<LibraryUserBO> page, @Param("userId") Long userId, @Param("libraryId") Long libraryId, @Param("isAdmin") boolean isAdmin);

    BasePage<DocumentVO> queryCollectDocumentByLibraryId(BasePage<LibraryUserBO> page, @Param("userId") Long userId, @Param("libraryId") Long libraryId);

    BasePage<SearchVO> search(BasePage<SearchBO> page, @Param("data") Map<String, Object> map);
}
