package com.fystart.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fystart.gmall.model.product.BaseAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fy
 * @date 2022/12/1 22:01
 */
@Mapper
@Repository
public interface BaseAttrValueMapper extends BaseMapper<BaseAttrValue> {
}
