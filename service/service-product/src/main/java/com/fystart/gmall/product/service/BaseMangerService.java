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
    List<BaseCategory1> getCategory1Info();

    List<BaseCategory2> getCategory2Info(String category1Id);

    List<BaseCategory3> getCategory3Info(String category2Id);

    List<BaseAttrInfo> attrInfoList(Long category1Id,Long category2Id,Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrInfo(Long attrId);

    IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> page, Long category3Id);

    List<BaseSaleAttr> baseSaleAttrList();

    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 根据spuId 查询销售属性集合
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    /**
     * 根据spuId查询图片列表
     * @param spuId
     * @return
     */
    List<SpuImage> spuImageList(Long spuId);

    /**
     * 保存sku信息
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * 分页查询sku信息
     * @param page
     * @return
     */
    IPage<SkuInfo> skuListPage(Page<SkuInfo> page);
}
