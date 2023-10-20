package com.kakarote.km.service;

import com.kakarote.core.entity.BasePage;
import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.LibraryPageBO;
import com.kakarote.km.entity.BO.SearchBO;
import com.kakarote.km.entity.BO.SetKmKnowledgeLibraryBO;
import com.kakarote.km.entity.BO.UpdateMemberBO;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.PO.KmKnowledgeLibraryUser;
import com.kakarote.km.entity.VO.*;

import java.util.List;

/**
 * <p>
 * 知识库 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmKnowledgeLibraryService extends BaseService<KmKnowledgeLibrary> {

    public void add(SetKmKnowledgeLibraryBO setKmKnowledgeLibraryBO);

    public void set(SetKmKnowledgeLibraryBO setKmKnowledgeLibraryBO);

    public void delete(Long libraryId);

    public void completelyDelete(Long libraryId);

    public void updateMember(UpdateMemberBO updateMemberBO);

    public void exitMember(KmKnowledgeLibraryUser kmKnowledgeLibraryUser);

    public List<QueryMemberVO> queryMember(Long libraryId);

    public List<LibraryListVO> queryList(Long groupId, Integer star);

    public LibraryVO queryById(Long libraryId);

    public BasePage<DocumentVO> queryDocumentByLibraryId(LibraryPageBO libraryPageBO);

    public BasePage<DocumentVO> queryCollectDocumentByLibraryId(LibraryPageBO libraryPageBO);

    public List<KmKnowledgeLibrary> queryLibraryTemplate();

    public BasePage<SearchVO> search(SearchBO searchBO);

    /**
     * 通过分组查询知识库列表
     *
     * @return
     */
    List<LibraryGroupListVO> queryLibraryListByGroup();

    Boolean getAISwitch();
}
