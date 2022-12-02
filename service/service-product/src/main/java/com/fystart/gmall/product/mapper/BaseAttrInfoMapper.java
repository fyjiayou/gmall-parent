package com.fystart.gmall.product.mapper;

import com.fystart.gmall.model.product.BaseAttrInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/1 22:01
 */
@Mapper
@Repository
public interface BaseAttrInfoMapper {
    /**
     * 根据一二三级分类查询平台属性和value
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id);
}
