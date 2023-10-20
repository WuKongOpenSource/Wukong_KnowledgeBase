package com.kakarote.km.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.kakarote.common.entity.SimpleDept;
import com.kakarote.common.entity.SimpleUser;
import com.kakarote.common.utils.UserUtil;
import com.kakarote.core.common.Const;
import com.kakarote.core.exception.CrmException;
import com.kakarote.core.servlet.BaseServiceImpl;
import com.kakarote.ids.provider.service.DeptService;
import com.kakarote.ids.provider.service.UserService;
import com.kakarote.ids.provider.utils.UserCacheUtil;
import com.kakarote.km.common.KmCodeEnum;
import com.kakarote.km.common.KmEnum;
import com.kakarote.km.entity.BO.AddAuthBO;
import com.kakarote.km.entity.BO.AuthUserBO;
import com.kakarote.km.entity.BO.UpdateAuthBO;
import com.kakarote.km.entity.PO.KmAuth;
import com.kakarote.km.entity.PO.KmAuthUser;
import com.kakarote.km.entity.PO.KmKnowledgeLibrary;
import com.kakarote.km.entity.PO.KmKnowledgeLibraryUser;
import com.kakarote.km.entity.VO.AuthUserVO;
import com.kakarote.km.entity.VO.DocumentAuthVO;
import com.kakarote.km.mapper.KmAuthMapper;
import com.kakarote.km.service.IKmAuthService;
import com.kakarote.km.service.IKmAuthUserService;
import com.kakarote.km.service.IKmKnowledgeLibraryService;
import com.kakarote.km.service.IKmKnowledgeLibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 文档文件夹权限表 服务实现类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
@Service
public class KmAuthServiceImpl extends BaseServiceImpl<KmAuthMapper, KmAuth> implements IKmAuthService {
    @Autowired
    private IKmKnowledgeLibraryService kmKnowledgeLibraryService;

    @Autowired
    private IKmKnowledgeLibraryUserService kmKnowledgeLibraryUserService;

    @Autowired
    private IKmAuthUserService kmAuthUserService;

    @Autowired
    private IKmAuthService kmAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService  deptService;

    /**
     * 判断知识库编辑权限
     */
    @Override
    public boolean checkLibraryAuth(Long libraryId) {
        if (UserUtil.isAdmin()) {
            return true;
        }
        KmKnowledgeLibrary library = kmKnowledgeLibraryService.getById(libraryId);
        return checkLibraryAuth(library);
    }

    @Override
    public boolean checkLibraryAuth(KmKnowledgeLibrary library) {
        if (UserUtil.isAdmin()) {
            return true;
        }
        Long userId = UserUtil.getUserId();
        if (library.getIsOpen() == 1) {
            //公开知识库 只有创建人可以编辑知识库
            if (library.getCreateUserId().equals(userId)) {
                return true;
            } else {
                return false;
            }
        } else {
            Integer libraryIdRole = kmAuthService.getLibraryIdRole(library.getLibraryId(), userId, UserUtil.getUser().getDeptId());
            if (libraryIdRole == null) {
                return false;
            }

            //修改权限
            ArrayList<Integer> updateAuth = CollectionUtil.toList(1, 2);

            return updateAuth.contains(libraryIdRole);
        }
    }

    @Override
    @Transactional
    public void addAuth(AddAuthBO addAuthBO) {
        KmAuth auth = new KmAuth();
        BeanUtil.copyProperties(addAuthBO, auth);
        save(auth);
        if (auth.getIsOpen() == 0) {
            List<AuthUserBO> authUserList = addAuthBO.getAuthUserList();
            authUserList.removeIf(authUser -> authUser.getUserId().equals(UserUtil.getUserId()));
            List<KmAuthUser> userList = new ArrayList<>();
            authUserList.forEach(authUserBO -> {
                KmAuthUser kmAuthUser = new KmAuthUser();
                kmAuthUser.setAuth(authUserBO.getAuth());
                kmAuthUser.setUserId(authUserBO.getUserId());
                kmAuthUser.setAuthId(auth.getAuthId());
                kmAuthUser.setCreateUserId(UserUtil.getUserId());
                kmAuthUser.setCreateTime(LocalDateTimeUtil.now());
                userList.add(kmAuthUser);
            });
            KmAuthUser currentAuthUser = new KmAuthUser()
                    .setAuth(KmEnum.PRIVATE_MANAGE_AUTH.getValue()).setUserId(UserUtil.getUserId()).setAuthId(auth.getAuthId())
                    .setCreateUserId(UserUtil.getUserId()).setCreateTime(LocalDateTimeUtil.now());
            userList.add(currentAuthUser);
            kmAuthUserService.saveBatch(userList, Const.BATCH_SAVE_SIZE);
        }
    }

    @Override
    @Transactional
    public void updateAuth(UpdateAuthBO updateAuthBO) {
        Long createUserId = updateAuthBO.getCreateUserId();
        KmAuth auth = updateAuthBO.getAuth();
        kmAuthUserService.removeByMap(new JSONObject().fluentPut("auth_id", auth.getAuthId()));
        List<KmAuthUser> authUserList = new ArrayList<>();
        List<AuthUserBO> userList = updateAuthBO.getAuthUserList();
        userList.forEach(authUserBO -> {

            if (authUserBO.getUserId() != null && authUserBO.getUserId().equals(createUserId)) {
                return;
            }
            KmAuthUser kmAuthUser = new KmAuthUser();
            kmAuthUser.setAuth(authUserBO.getAuth());
            kmAuthUser.setUserId(authUserBO.getUserId());
            kmAuthUser.setDeptId(authUserBO.getDeptId());
            kmAuthUser.setAuthId(auth.getAuthId());
            kmAuthUser.setIsMobile(authUserBO.getIsMobile());
            kmAuthUser.setCreateUserId(UserUtil.getUserId());
            kmAuthUser.setCreateTime(LocalDateTimeUtil.now());
            authUserList.add(kmAuthUser);
        });
        //单独设置创建人是管理员
        KmAuthUser currentAuthUser = new KmAuthUser()
                .setAuth(KmEnum.PRIVATE_MANAGE_AUTH.getValue())
                .setUserId(createUserId)
                .setAuthId(auth.getAuthId())
                .setCreateUserId(UserUtil.getUserId())
                .setCreateTime(LocalDateTimeUtil.now())
                .setIsMobile(1);
        authUserList.add(currentAuthUser);
        kmAuthUserService.saveBatch(authUserList, Const.BATCH_SAVE_SIZE);
        updateById(auth);
    }

    @Override
    public void deleteAuthUser(KmAuthUser kmAuthUser) {
        kmAuthUserService.update().eq("user_id", kmAuthUser.getUserId()).eq("auth_id", kmAuthUser.getAuthId()).remove();
    }

    @Override
    public Integer getFolderOrDocumentAuth(Long authId, Long libraryId) {
        KmAuth auth = getById(authId);
        JSONObject documentAuth = getFolderOrDocumentAuth(auth, libraryId);
        return documentAuth.getInteger("auth");
    }

    /**
     * @param auth
     * @param libraryId
     * @return auth  1 所有权限 2 编辑权限 3仅预览 4可下载 5可上传下载
     */
    @Override
    public JSONObject getFolderOrDocumentAuth(KmAuth auth, Long libraryId) {
        JSONObject jsonObject = new JSONObject();
        if (UserUtil.isAdmin()) {
            //超级管理员默认是管理权限
            jsonObject.put("auth", 1);
            jsonObject.put("isMobile", 1);
            return jsonObject;
        }
        Long userId = UserUtil.getUserId();
        Long deptId = UserUtil.getUser().getDeptId();
        KmAuthUser authUser = null;
        Integer libraryUser = getLibraryIdRole(libraryId, userId, deptId);

        //知识库超级管理员 创建者和管理员权限
        ArrayList<Integer> librarySuperAuth = CollectionUtil.toList(1, 2);

        if (librarySuperAuth.contains(libraryUser)) {
            jsonObject.put("auth", 1);
            jsonObject.put("isMobile", 1);
            return jsonObject;
        }

        if (auth.getIsOpen() == 1) {
            Integer openAuth = auth.getOpenAuth();
            switch (openAuth) {
                case 4:
                    openAuth = 1;
                    break;
                case 6:
                    openAuth = 4;
                    break;
            }

            jsonObject.put("auth", openAuth);
            jsonObject.put("isMobile", auth.getIsMobile());
            return jsonObject;
        } else {
            List<KmAuthUser> kmAuthUsers = kmAuthUserService.query().eq("auth_id", auth.getAuthId()).list();
            if (ObjectUtil.isNotEmpty(kmAuthUsers)) {
                Optional<KmAuthUser> first = kmAuthUsers.stream().filter(kmAuthUser -> Objects.equals(kmAuthUser.getUserId(), userId)).findFirst();
                if (first.isPresent()) {
                    authUser = first.get();
                } else {
                    Optional<KmAuthUser> first1 = kmAuthUsers.stream().filter(e -> ObjectUtil.isNotEmpty(e.getDeptId())).filter(kmAuthUser -> kmAuthUser.getDeptId().equals(deptId)).findFirst();
                    if (first1.isPresent()) {
                        authUser = first1.get();
                    }
                }
            }
        }

        if (authUser == null) {
            throw new CrmException(KmCodeEnum.KM_AUTH_ERROR);
        } else {
            jsonObject.put("auth", authUser.getAuth());
            jsonObject.put("isMobile", authUser.getIsMobile());
        }
        return jsonObject;
    }

    /**
     * 获取知识库角色权限
     *
     * @param libraryId
     * @param userId
     * @param deptId
     * @return
     */
    @Override
    public Integer getLibraryIdRole(Long libraryId, Long userId, Long deptId) {
        if (UserUtil.isAdmin()) {
            return 1;
        }
        List<KmKnowledgeLibraryUser> libraryUserList = kmKnowledgeLibraryUserService.query().eq("library_id", libraryId).list();
        for (KmKnowledgeLibraryUser libraryUser : libraryUserList) {
            if (userId.equals(libraryUser.getUserId())) {
                return libraryUser.getRole();
            }
        }
        for (KmKnowledgeLibraryUser libraryUser : libraryUserList) {
            if (deptId.equals(libraryUser.getDeptId())) {
                return libraryUser.getRole();
            }
        }
        return 0;
    }

    @Override
    public DocumentAuthVO queryAuth(Long authId, Long libraryId) {
        KmAuth auth = getById(authId);
        DocumentAuthVO documentAuthVO = new DocumentAuthVO();
        BeanUtil.copyProperties(auth, documentAuthVO);
        List<AuthUserVO> authUserList = getBaseMapper().queryAuthUserList(authId);

        List<Long> deptIds = authUserList.stream().map(AuthUserVO::getDeptId).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
        Map<Long, SimpleDept> collect = new HashMap<>();

        if (ObjectUtil.isNotEmpty(deptIds)) {
            //查询部门信息
            List<SimpleDept> simpleDeptList = deptService.queryDeptByIds(deptIds).getData();
            collect = simpleDeptList.stream().collect(Collectors.toMap(SimpleDept::getDeptId, Function.identity()));
        }

        for (AuthUserVO authUserVO : authUserList) {
            if (ObjectUtil.isNotEmpty(authUserVO.getUserId())) {
                SimpleUser simpleUser = UserCacheUtil.getSimpleUser(authUserVO.getUserId());
                authUserVO.setRealname(simpleUser.getNickname());
            }
            if (ObjectUtil.isNotEmpty(authUserVO.getDeptId())) {
                SimpleDept simpleDept = collect.get(authUserVO.getDeptId());
                authUserVO.setDeptName(simpleDept.getDeptName());
                authUserVO.setParentDeptId(simpleDept.getParentId());
            }
        }
        JSONObject documentAuth = getFolderOrDocumentAuth(auth, libraryId);
        return documentAuthVO.setAuthUserList(authUserList).setCurrentUserAuth(documentAuth.getInteger("auth"));
    }

}
