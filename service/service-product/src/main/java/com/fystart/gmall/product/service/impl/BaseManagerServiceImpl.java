package com.fystart.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fystart.gmall.model.product.*;
import com.fystart.gmall.product.mapper.*;
import com.fystart.gmall.product.service.BaseMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuPosterMapper spuPosterMapper;

    @Override
    public List<BaseCategory1> getCategory1Info() {

        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2Info(String category1Id) {

        LambdaQueryWrapper<BaseCategory2> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseCategory2::getCategory1Id, category1Id);

        return baseCategory2Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseCategory3> getCategory3Info(String category2Id) {

        LambdaQueryWrapper<BaseCategory3> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseCategory3::getCategory2Id, category2Id);

        return baseCategory3Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        return baseAttrInfoMapper.getAttrInfoList(category1Id, category2Id, category3Id);
    }

    /**
     * @Transactional：默认配置的方式：只能对运行时异常RuntimeException进行回滚
     * @param baseAttrInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {

        //修改操作
        if (baseAttrInfo.getId() != null) {
            baseAttrInfoMapper.updateById(baseAttrInfo);
        } else {
            //添加操作
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        //1.1 从数据库中查询属性值，删除原有的属性值
        LambdaQueryWrapper<BaseAttrValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseAttrValue::getAttrId, baseAttrInfo.getId());
        baseAttrValueMapper.delete(queryWrapper);

        //1.2加入新的属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

        if (!CollectionUtils.isEmpty(attrValueList)) {
            attrValueList.stream().forEach(item -> {
                item.setAttrId(baseAttrInfo.getId());

                baseAttrValueMapper.insert(item);
            });
        }
    }

    @Override
    public BaseAttrInfo getAttrInfo(Long attrId) {

        //获取属性对象
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);

        //获取属性值集合
        List<BaseAttrValue> attrValues = getAttrValueList(attrId);

        baseAttrInfo.setAttrValueList(attrValues);

        return baseAttrInfo;
    }
    private List<BaseAttrValue> getAttrValueList(Long attrId) {

        LambdaQueryWrapper<BaseAttrValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseAttrValue::getAttrId,attrId);

        return baseAttrValueMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> page, Long category3Id) {

        LambdaQueryWrapper<SpuInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SpuInfo::getCategory3Id,category3Id);
        queryWrapper.orderByDesc(SpuInfo::getId);

        Page<SpuInfo> spuInfoPage = spuInfoMapper.selectPage(page, queryWrapper);

        return spuInfoPage;
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {

        return baseSaleAttrMapper.selectList(null);
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //要操作哪些表？
        // spu_info：spu基本信息表
        // spu_sale_attr：销售属性表
        // spu_sale_attr_value：销售属性值表
        // spu_image：图片
        // spu_poster：海报

        //保存spu信息
        spuInfoMapper.insert(spuInfo);

        Long spuId = spuInfo.getId();

        //保存图片
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();

        if(! CollectionUtils.isEmpty(spuImageList)){
            for (SpuImage spuImage : spuImageList) {
                //设置spuId
                spuImage.setSpuId(spuId);

                //保存
                spuImageMapper.insert(spuImage);
            }
        }

        //保存海报
        List<SpuPoster> spuPosterList = spuInfo.getSpuPosterList();

        if (!CollectionUtils.isEmpty(spuPosterList)) {
            for (SpuPoster spuPoster : spuPosterList) {
                //设置spuId
                spuPoster.setSpuId(spuId);

                //保存
                spuPosterMapper.insert(spuPoster);
            }
        }

        //保存销售属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();

        if (!CollectionUtils.isEmpty(spuSaleAttrList)) {

            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {

                //设置spuId
                spuSaleAttr.setSpuId(spuId);

                //获取销售属性值集合
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();

                if (!CollectionUtils.isEmpty(spuSaleAttrValueList)) {
                    //保存销售属性值
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {

                        //设置spuId
                        spuSaleAttrValue.setSpuId(spuId);

                        //设置销售属性名称
                        spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());

                        //保存销售属性值
                        spuSaleAttrValueMapper.insert(spuSaleAttrValue);
                    }
                }
            }
        }
    }
}




