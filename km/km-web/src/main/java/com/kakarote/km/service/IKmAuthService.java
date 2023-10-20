package com.kakarote.km.service;

import com.alibaba.fastjson.JSONObject;
import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.AddAuthBO;
import com.kakarote.km.entity.BO.UpdateAuthBO;
import com.kakarote.km.entity.PO.KmAuth;
import com.kakarote.km.entity.PO.KmAuthUser;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.VO.DocumentAuthVO;

/**
 * <p>
 * 文档文件夹权限表 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmAuthService extends BaseService<KmAuth> {
    public boolean checkLibraryAuth(Long libraryId);

    public boolean checkLibraryAuth(KmKnowledgeLibrary library);

    public void addAuth(AddAuthBO addAuthBO);

    public void updateAuth(UpdateAuthBO updateAuthBO);

    public void deleteAuthUser(KmAuthUser kmAuthUser);

    public Integer getFolderOrDocumentAuth(Long authId, Long libraryId);

    public JSONObject getFolderOrDocumentAuth(KmAuth auth, Long libraryId);

    public DocumentAuthVO queryAuth(Long authId, Long libraryId);


    public Integer getLibraryIdRole(Long libraryId, Long userId, Long deptId);
}
