package com.fystart.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
 * @author fy
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

    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public List<BaseCategory1> getCategory1Info() {
        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2Info(String category1Id) {
        LambdaQueryWrapper<BaseCategory2> queryWrapper = Wrappers
                .lambdaQuery(BaseCategory2.class)
                .eq(BaseCategory2::getCategory1Id, category1Id);
        return baseCategory2Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseCategory3> getCategory3Info(String category2Id) {
        LambdaQueryWrapper<BaseCategory3> queryWrapper = Wrappers
                .lambdaQuery(BaseCategory3.class)
                .eq(BaseCategory3::getCategory2Id, category2Id);
        return baseCategory3Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        return baseAttrInfoMapper.getAttrInfoList(category1Id, category2Id, category3Id);
    }

    /**
     * @Transactional???????????????????????????????????????????????????RuntimeException????????????
     * @param baseAttrInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //??????????????????

        //????????????
        if (baseAttrInfo.getId() != null) {
            baseAttrInfoMapper.updateById(baseAttrInfo);
        } else {
            //????????????
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        //?????????????????????:??????????????????????????????????????????id?????????????????????????????????????????????

        //1.1 ???????????????????????????????????????????????????????????????
        LambdaQueryWrapper<BaseAttrValue> queryWrapper = Wrappers
                .lambdaQuery(BaseAttrValue.class)
                .eq(BaseAttrValue::getAttrId,baseAttrInfo.getId());
        baseAttrValueMapper.delete(queryWrapper);

        //1.2?????????????????????
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

        if (!CollectionUtils.isEmpty(attrValueList)) {
            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }
    }

    /**
     * ??????????????????id???????????????????????????
     *
     * @param attrId
     * @return
     */
    private List<BaseAttrValue> getAttrValueList(Long attrId) {
        LambdaQueryWrapper<BaseAttrValue> queryWrapper = Wrappers
                .lambdaQuery(BaseAttrValue.class)
                .eq(BaseAttrValue::getAttrId, attrId);
        return baseAttrValueMapper.selectList(queryWrapper);
    }

    @Override
    public BaseAttrInfo getAttrInfo(Long attrId) {
        //??????????????????
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);

        //???????????????????????????
        List<BaseAttrValue> attrValues = getAttrValueList(attrId);
        baseAttrInfo.setAttrValueList(attrValues);

        return baseAttrInfo;
    }

    @Override
    public IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> page, Long category3Id) {
        LambdaQueryWrapper<SpuInfo> queryWrapper = Wrappers.lambdaQuery(SpuInfo.class)
                .eq(SpuInfo::getCategory3Id,category3Id)
                .orderByDesc(SpuInfo::getId);
        Page<SpuInfo> spuInfoPage = spuInfoMapper.selectPage(page, queryWrapper);

        return spuInfoPage;
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectList(null);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        /*
        ??????????????????????????????
            - spu_info???spu???????????????
            - spu_sale_attr??????????????????
            - spu_sale_attr_value?????????????????????
            - spu_image?????????
            - spu_poster?????????
         */

        //??????spu??????
        spuInfoMapper.insert(spuInfo);
        //spuId
        Long spuId = spuInfo.getId();

        //????????????
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (!CollectionUtils.isEmpty(spuImageList)) {
            for (SpuImage spuImage : spuImageList) {
                //??????spuId
                spuImage.setSpuId(spuId);
                //??????
                spuImageMapper.insert(spuImage);
            }
        }

        //????????????
        List<SpuPoster> spuPosterList = spuInfo.getSpuPosterList();
        if (!CollectionUtils.isEmpty(spuPosterList)) {
            for (SpuPoster spuPoster : spuPosterList) {
                //??????spuId
                spuPoster.setSpuId(spuId);
                //??????
                spuPosterMapper.insert(spuPoster);
            }
        }

        //????????????????????????????????????
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if (!CollectionUtils.isEmpty(spuSaleAttrList)) {
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                //??????spuId
                spuSaleAttr.setSpuId(spuId);
                //???????????????????????????
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if (!CollectionUtils.isEmpty(spuSaleAttrValueList)) {
                    //?????????????????????
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        //??????spuId
                        spuSaleAttrValue.setSpuId(spuId);
                        //????????????????????????
                        spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
                        //?????????????????????
                        spuSaleAttrValueMapper.insert(spuSaleAttrValue);
                    }
                }
                spuSaleAttrMapper.insert(spuSaleAttr);
            }
        }
    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {
        return spuSaleAttrMapper.spuSaleAttrList(spuId);
    }

    @Override
    public List<SpuImage> spuImageList(Long spuId) {
        LambdaQueryWrapper<SpuImage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SpuImage::getSpuId, spuId);
        return spuImageMapper.selectList(queryWrapper);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        /*
            ??????skuInfo
                ???????????????
                    - sku_info??????????????????
                    - sku_image???sku?????????
                    - sku_sale_attr_value???sku???????????????
                    - sku_attr_value???sku???????????????
         */
        //0?????????????????????
        skuInfo.setIsSale(0);

        //1.??????skuInfo
        skuInfoMapper.insert(skuInfo);
        //skuId
        Long skuId = skuInfo.getId();

        //2.????????????
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImageList)) {
            for (SkuImage skuImage : skuImageList) {
                //??????skuId
                skuImage.setSkuId(skuId);
                skuImageMapper.insert(skuImage);
            }
        }

        //3.??????????????????
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }

        //4.??????????????????
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if (!CollectionUtils.isEmpty(skuSaleAttrValueList)) {
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                //??????skuId
                skuSaleAttrValue.setSkuId(skuId);
                //??????spuId
                skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
                skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            }
        }
    }

    @Override
    public IPage<SkuInfo> skuListPage(Page<SkuInfo> page) {
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SkuInfo::getId);
        return skuInfoMapper.selectPage(page, queryWrapper);
    }
}




