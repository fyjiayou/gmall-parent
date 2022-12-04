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
}
