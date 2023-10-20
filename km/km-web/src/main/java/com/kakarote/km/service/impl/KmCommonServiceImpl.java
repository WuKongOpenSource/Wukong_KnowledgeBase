package com.kakarote.km.service.impl;

import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.enums.SystemCodeEnum;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.feign.admin.service.AdminMessageService;
import com.kakarote.core.utils.BaseUtil;
import com.kakarote.km.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JiaS
 * @date 2020/11/13
 */
@Slf4j
@Service
public class KmCommonServiceImpl implements IKmCommonService {

    @Autowired
    private IKmActionRecordService kmActionRecordService;

    @Autowired
    private IKmCollectService kmCollectService;

    @Autowired
    private IKmDocumentShareService kmDocumentShareService;
    @Autowired
    private IKmDocumentLabelService kmDocumentLabelService;
    @Autowired
    private IKmDocumentFavorService kmDocumentFavorService;
    @Autowired
    private IKmFolderService kmFolderService;

    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;
    @Autowired
    private IKmKnowledgeLibraryUserService kmKnowledgeLibraryUserService;

    @Autowired
    private AdminMessageService adminMessageService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initKmData() {
        if (!UserUtil.isAdmin()) {
            if (this.verifyInitAuth()) {
                throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
            }
        }
        log.info("开始初始化知识库模块数据！");
        kmActionRecordService.lambdaUpdate().remove();
        kmCollectService.lambdaUpdate().remove();


        kmDocumentShareService.lambdaUpdate().remove();
        kmDocumentLabelService.lambdaUpdate().remove();
        kmDocumentFavorService.lambdaUpdate().remove();
        kmFolderService.lambdaUpdate().remove();

        kmKnowledgeLibraryService.lambdaUpdate().remove();
        kmKnowledgeLibraryUserService.lambdaUpdate().remove();

        adminMessageService.deleteByLabel(7);
        log.info("知识库模块数据初始化完成！");
        return true;
    }


    private static final String INIT_AUTH_URL = "/adminConfig/moduleInitData";


    /**
     * 验证非管理员有无权限
     *
     * @param
     * @return boolean
     * @date 2020/11/23 10:35
     **/
    private boolean verifyInitAuth() {
        boolean isNoAuth = false;
        Long userId = UserUtil.getUserId();
        String key = userId.toString();
        List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
        if (noAuthMenuUrls != null && noAuthMenuUrls.contains(INIT_AUTH_URL)) {
            isNoAuth = true;
        }
        return isNoAuth;
    }

}
