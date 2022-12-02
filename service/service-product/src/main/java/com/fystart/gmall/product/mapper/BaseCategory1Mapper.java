package com.fystart.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fystart.gmall.model.product.BaseCategory1;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 冯粤
* @description 针对表【base_category1(一级分类表)】的数据库操作Mapper
* @createDate 2022-12-01 20:57:34
* @Entity com.fystart.gmall.product.pojo.BaseCategory1
*/
@Mapper
@Repository
public interface BaseCategory1Mapper extends BaseMapper<BaseCategory1> {

}




