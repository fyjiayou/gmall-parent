package com.fystart.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fystart.gmall.model.product.BaseCategoryTrademark;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fy
 * @date 2022/12/3 19:40
 */
@Mapper
@Repository
public interface BaseCategoryTrademarkMapper extends BaseMapper<BaseCategoryTrademark> {
}
