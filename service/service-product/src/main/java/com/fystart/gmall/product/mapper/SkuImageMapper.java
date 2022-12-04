package com.fystart.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fystart.gmall.model.product.SkuImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fy
 * @date 2022/12/4 15:49
 */
@Mapper
@Repository
public interface SkuImageMapper extends BaseMapper<SkuImage> {
}
