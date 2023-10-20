package com.kakarote.km.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.PO.KmAuth;
import com.kakarote.km.entity.VO.AuthUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文档文件夹权限表 Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmAuthMapper extends BaseMapper<KmAuth> {
    List<AuthUserVO> queryAuthUserList(@Param("authId") Long authId);
}
