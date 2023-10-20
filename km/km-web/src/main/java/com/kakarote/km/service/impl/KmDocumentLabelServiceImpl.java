package com.kakarote.km.service.impl;

import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.km.entity.BO.QueryDocumentByLabelBO;
import com.kakarote.km.entity.PO.KmDocumentLabel;
import com.kakarote.km.entity.VO.SearchVO;
import com.kakarote.km.mapper.KmDocumentLabelMapper;
import com.kakarote.km.service.IKmDocumentLabelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文档标签表 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmDocumentLabelServiceImpl extends BaseServiceImpl<KmDocumentLabelMapper, KmDocumentLabel> implements IKmDocumentLabelService {

    @Override
    public void add(KmDocumentLabel kmDocumentLabel) {
        save(kmDocumentLabel);
    }

    @Override
    public void update(KmDocumentLabel kmDocumentLabel) {
        updateById(kmDocumentLabel);
    }

    @Override
    public void deleteById(Integer labelId) {
        removeById(labelId);
    }

    @Override
    public List<KmDocumentLabel> queryList() {
        return list();
    }

    @Override
    public BasePage<SearchVO> queryDocumentByLabelId(QueryDocumentByLabelBO queryDocumentByLabelBO) {
        Long labelId = queryDocumentByLabelBO.getLabelId();
        return getBaseMapper().queryDocumentByLabelId(queryDocumentByLabelBO.parse(), UserUtil.isAdmin(), UserUtil.getUserId(), labelId);
    }
}
