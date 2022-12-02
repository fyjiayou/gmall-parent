package com.fystart.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fystart.gmall.model.product.BaseAttrInfo;
import com.fystart.gmall.model.product.BaseCategory1;
import com.fystart.gmall.model.product.BaseCategory2;
import com.fystart.gmall.model.product.BaseCategory3;
import com.fystart.gmall.product.mapper.BaseAttrInfoMapper;
import com.fystart.gmall.product.mapper.BaseCategory1Mapper;
import com.fystart.gmall.product.mapper.BaseCategory2Mapper;
import com.fystart.gmall.product.mapper.BaseCategory3Mapper;
import com.fystart.gmall.product.service.BaseMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 冯粤
 * @description 针对表【base_category1(一级分类表)】的数据库操作Service实现
 * @createDate 2022-12-01 20:57:34
 */
@Service
public class BaseManagerServiceImpl implements BaseMangerService {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Override
    public List<BaseCategory1> getCategory1Info() {

        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2Info(String category1Id) {

        LambdaQueryWrapper<BaseCategory2> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseCategory2::getCategory1Id,category1Id);

        return baseCategory2Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseCategory3> getCategory3Info(String category2Id) {

        LambdaQueryWrapper<BaseCategory3> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseCategory3::getCategory2Id,category2Id);

        return baseCategory3Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id,Long category2Id,Long category3Id) {
        return baseAttrInfoMapper.getAttrInfoList(category1Id,category2Id,category3Id);
    }


}




