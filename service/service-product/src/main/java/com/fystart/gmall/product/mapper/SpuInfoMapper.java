package com.fystart.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fystart.gmall.model.product.SpuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fy
 * @date 2022/12/3 16:00
 */
@Mapper
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {
}
