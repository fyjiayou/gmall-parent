package com.fystart.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fystart.gmall.model.product.BaseAttrInfo;
import com.fystart.gmall.model.product.BaseCategory1;
import com.fystart.gmall.model.product.BaseCategory2;
import com.fystart.gmall.model.product.BaseCategory3;

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
}
