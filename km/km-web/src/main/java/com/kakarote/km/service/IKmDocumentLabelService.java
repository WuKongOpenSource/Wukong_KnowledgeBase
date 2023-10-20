package com.kakarote.km.service;

import com.kakarote.core.entity.BasePage;
import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.QueryDocumentByLabelBO;
import com.kakarote.km.entity.PO.KmDocumentLabel;
import com.kakarote.km.entity.VO.SearchVO;

import java.util.List;

/**
 * <p>
 * 文档标签表 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmDocumentLabelService extends BaseService<KmDocumentLabel> {
    public void add(KmDocumentLabel kmDocumentLabel);

    public void update(KmDocumentLabel kmDocumentLabel);

    public void deleteById(Integer labelId);

    public List<KmDocumentLabel> queryList();

    public BasePage<SearchVO> queryDocumentByLabelId(QueryDocumentByLabelBO queryDocumentByLabelBO);
}
