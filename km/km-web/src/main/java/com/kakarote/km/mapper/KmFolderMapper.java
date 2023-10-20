package com.kakarote.km.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.BO.LibraryUserBO;
import com.kakarote.km.entity.PO.KmFolder;
import com.kakarote.km.entity.VO.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 知识库文件夹 Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmFolderMapper extends BaseMapper<KmFolder> {
    List<LibraryUserBO> queryLibraryUserList(@Param("libraryId") Long libraryId);

    List<FolderTreeVO> queryFolderList(@Param("userId") Long userId, @Param("deptId") Long deptId, @Param("libraryId") Long libraryId, @Param("isAdmin") boolean isAdmin);

    List<DocumentTreeVO> queryFirstDocumentList(@Param("userId") Long userId, @Param("deptId") Long deptId, @Param("libraryId") Long libraryId, @Param("isAdmin") boolean isAdmin);

    List<DocumentTreeVO> queryFolderDocumentList(@Param("folderId") Long folderId);

    List<DocumentTreeVO> queryChildKmDocumentList(@Param("documentId") Long documentId);

    FolderDetailVO queryById(@Param("userId") Long userId, @Param("folderId") Long folderId);

    List<DocumentVO> queryDocumentByFolderId(@Param("userId") Long userId, @Param("folderId") Long folderId, @Param("isAdmin") boolean isAdmin);

    List<FolderVO> queryChildFolderByFolderId(@Param("userId") Long userId, @Param("folderId") Long folderId, @Param("isAdmin") boolean isAdmin);

    List<DocumentTreeVO> queryAllDocumentListBylibraryId(@Param("libraryId") Long libraryId);

    List<Long> queryAllFolderByLibraryId(@Param("libraryId") Long libraryId);

    List<Long> querySonFolderByFolderId(@Param("FolderId") List<Long> FolderId);

    List<DocumentTreeVO> queryDocumentById(@Param("folderId") Long folderId);

    Integer queryStatusThreeNumber(@Param("libraryId") Long libraryId);

    Integer queryStatusFourNumber(@Param("libraryId") Long libraryId);
}
