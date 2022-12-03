package com.fystart.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fystart.gmall.model.product.BaseCategoryTrademark;
import com.fystart.gmall.model.product.BaseTrademark;
import com.fystart.gmall.model.product.CategoryTrademarkVo;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/3 19:40
 */
public interface BaseCategoryTrademarkService extends IService<BaseCategoryTrademark> {
    List<BaseTrademark> findTrademarkList(Long category3Id);

    void remove(Long category3Id, Long trademarkId);

    List<BaseTrademark> findCurrentTrademarkList(Long category3Id);

    void save(CategoryTrademarkVo categoryTrademarkVo);
}
