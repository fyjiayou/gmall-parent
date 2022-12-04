package com.fystart.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fystart.gmall.model.product.SpuSaleAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/4 14:27
 */
@Mapper
@Repository
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    /**
     * 根据spuId 查询销售属性集合
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> spuSaleAttrList(@Param("spuId") Long spuId);
}
