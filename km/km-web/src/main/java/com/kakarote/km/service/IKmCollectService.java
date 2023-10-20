package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.CollectListBO;
import com.kakarote.km.entity.PO.KmCollect;
import com.kakarote.km.entity.VO.CollectNumberListVO;

/**
 * <p>
 * 知识库收藏表 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmCollectService extends BaseService<KmCollect> {
    public void addCollect(KmCollect kmCollect);

    public void cancelCollect(KmCollect kmCollect);

    public CollectNumberListVO queryList(CollectListBO collectListBO);
}
