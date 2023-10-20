package com.kakarote.km.mapper;

import com.kakarote.core.entity.BasePage;
import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.km.entity.PO.KmCollect;
import com.kakarote.km.entity.VO.CollectListVO;
import com.kakarote.km.entity.VO.CollectNumberListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 知识库收藏表 Mapper 接口
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface KmCollectMapper extends BaseMapper<KmCollect> {
    BasePage<CollectListVO> queryList(BasePage<CollectListVO> parse, @Param("userId") Long userId, @Param("type") Integer type, @Param("search") String search, @Param("collectIds") List<Long> collectIds);

    CollectNumberListVO queryListCount(@Param("userId") Long userId, @Param("collectIds") List<Long> collectIds);
}
