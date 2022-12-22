package com.fystart.gmall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fystart.gmall.model.product.*;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/1 20:59
 */
public interface BaseMangerService {
    /**
     * 返回一级分类数据
     *
     * @return
     */
    List<BaseCategory1> getCategory1Info();

    /**
     * 返回二级分类数据
     *
     * @param category1Id
     * @return
     */
    List<BaseCategory2> getCategory2Info(String category1Id);

    /**
     * 返回三级分类数据
     *
     * @param category2Id
     * @return
     */
    List<BaseCategory3> getCategory3Info(String category2Id);

    /**
     * 根据三级分类查询按户型值列表
     *
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id);

    /**
     * 保存/修改 平台属性
     *
     * @param baseAttrInfo
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 根据平台属性id查询属性值列表
     *
     * @param attrId
     * @return
     */
    BaseAttrInfo getAttrInfo(Long attrId);


    /**
     * spu分页列表查询
     *
     * @param page
     * @param category3Id
     * @return
     */
    IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> page, Long category3Id);

    /**
     * 获取销售属性列表
     *
     * @return
     */
    List<BaseSaleAttr> baseSaleAttrList();

    /**
     * 保存spu信息
     *
     * @param spuInfo
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 根据spuId 查询销售属性和销售属性值集合
     *
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    /**
     * 根据spuId查询图片列表
     *
     * @param spuId
     * @return
     */
    List<SpuImage> spuImageList(Long spuId);

    /**
     * 保存sku信息
     *
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * 分页查询sku信息
     *
     * @param page
     * @return
     */
    IPage<SkuInfo> skuListPage(Page<SkuInfo> page);
}
