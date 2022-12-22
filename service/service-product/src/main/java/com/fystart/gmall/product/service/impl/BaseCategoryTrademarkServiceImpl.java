package com.fystart.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fystart.gmall.model.product.BaseCategoryTrademark;
import com.fystart.gmall.model.product.BaseTrademark;
import com.fystart.gmall.model.product.CategoryTrademarkVo;
import com.fystart.gmall.product.mapper.BaseCategoryTrademarkMapper;
import com.fystart.gmall.product.mapper.BaseTrademarkMapper;
import com.fystart.gmall.product.service.BaseCategoryTrademarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fy
 * @date 2022/12/3 19:41
 */
@Service
@Slf4j
public class BaseCategoryTrademarkServiceImpl extends ServiceImpl<BaseCategoryTrademarkMapper, BaseCategoryTrademark> implements BaseCategoryTrademarkService {

    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

    @Override
    public List<BaseTrademark> findTrademarkList(Long category3Id) {
        //根据三级分类查询中间表`BaseCategoryTrademark`,获取关联的品牌id列表
        LambdaQueryWrapper<BaseCategoryTrademark> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(BaseCategoryTrademark::getCategory3Id, category3Id);

        List<BaseCategoryTrademark> baseCategoryTrademarkList = baseMapper.selectList(queryWrapper1);

        //取出品牌id列表
        List<Long> trademarkIds = baseCategoryTrademarkList.stream()
                .map(item -> item.getTrademarkId())
                .collect(Collectors.toList());

        //用品牌id查询品牌表BaseTrademark
        LambdaQueryWrapper<BaseTrademark> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(BaseTrademark::getId, trademarkIds);

        //select * from base_trademark where id in (?,?);
        List<BaseTrademark> baseTrademarks = baseTrademarkMapper.selectList(queryWrapper2);

        return baseTrademarks;
    }

    @Override
    public void remove(Long category3Id, Long trademarkId) {

        LambdaQueryWrapper<BaseCategoryTrademark> queryWrapper = Wrappers
                .lambdaQuery(BaseCategoryTrademark.class)
                .eq(BaseCategoryTrademark::getCategory3Id, category3Id)
                .eq(BaseCategoryTrademark::getTrademarkId, trademarkId);

        baseMapper.delete(queryWrapper);
        log.info("删除分类品牌...{category3Id},{trademarkId}", category3Id, trademarkId);
    }

    @Override
    public List<BaseTrademark> findCurrentTrademarkList(Long category3Id) {
        //1.获取当前分类以及关联的品牌id
        LambdaQueryWrapper<BaseCategoryTrademark> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(BaseCategoryTrademark::getCategory3Id, category3Id);

        List<BaseCategoryTrademark> baseCategoryTrademarkList = baseMapper.selectList(queryWrapper1);

        List<BaseTrademark> resultList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(baseCategoryTrademarkList)) {
            //得到对应的品牌id列表
            List<Long> trademarkIds = baseCategoryTrademarkList.stream()
                    .map(item -> item.getTrademarkId())
                    .collect(Collectors.toList());

            //方法1：直接not in
            LambdaQueryWrapper<BaseTrademark> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.notIn(BaseTrademark::getId, trademarkIds);
            resultList = baseTrademarkMapper.selectList(queryWrapper2);


            //方法2：挨个判断
            //2.1、查询所有的品牌
//            List<BaseTrademark> baseTrademarkList = baseTrademarkMapper.selectList(null);

//            //2.2、过滤已经关联的,返回没有关联的品牌数据集合
//            resultList = baseTrademarkList.stream()
//                    .filter(item -> !trademarkIds.contains(item.getId()))
//                    .collect(Collectors.toList());
        }

        return resultList;
    }

    @Override
    public void save(CategoryTrademarkVo categoryTrademarkVo) {
        //获取品牌id集合
        List<Long> trademarkIdList = categoryTrademarkVo.getTrademarkIdList();

        //遍历处理，封装成对象
        List<BaseCategoryTrademark> collect = trademarkIdList.stream().map(item -> {
            BaseCategoryTrademark categoryTrademark = new BaseCategoryTrademark();
            categoryTrademark.setCategory3Id(categoryTrademarkVo.getCategory3Id());
            categoryTrademark.setTrademarkId(item);

            return categoryTrademark;
        }).collect(Collectors.toList());

        this.saveBatch(collect);
    }
}
