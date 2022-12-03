package com.fystart.gmall.product.controller;

import com.fystart.gmall.common.result.Result;
import com.fystart.gmall.model.product.BaseCategoryTrademark;
import com.fystart.gmall.model.product.BaseTrademark;
import com.fystart.gmall.model.product.CategoryTrademarkVo;
import com.fystart.gmall.product.service.BaseCategoryTrademarkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/3 19:38
 */
@Api(tags = "分类品牌管理")
@RestController
@RequestMapping("/admin/product/baseCategoryTrademark")
public class BaseCategoryTrademarkController {

    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;

    /**
     * 根据三级分类获取品牌
     * @param category3Id
     * @return
     */
    @GetMapping("/findTrademarkList/{category3Id}")
    public Result findTrademarkList(@PathVariable Long category3Id){

        List<BaseTrademark> baseCategoryTrademarkList = baseCategoryTrademarkService.findTrademarkList(category3Id);

        return Result.ok(baseCategoryTrademarkList);
    }


    @DeleteMapping("remove/{category3Id}/{trademarkId}")
    public Result removeTrademarkByCategory3IdTrademarkId(@PathVariable Long category3Id,
                                                          @PathVariable Long trademarkId){

        baseCategoryTrademarkService.remove(category3Id,trademarkId);

        return Result.ok();
    }

    /**
     * 根据category3Id获取可选品牌列表
     * @param category3Id
     * @return
     */
    @GetMapping("/findCurrentTrademarkList/{category3Id}")
    public Result findCurrentTrademarkList(@PathVariable Long category3Id){

        List<BaseTrademark> baseTrademarkList = baseCategoryTrademarkService.findCurrentTrademarkList(category3Id);

        return Result.ok(baseTrademarkList);
    }

    /**
     * 保存分类和品牌关联
     * CategoryTrademarkVo：
     *      61 2
     *      61 8
     *
     * @param categoryTrademarkVo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody CategoryTrademarkVo categoryTrademarkVo){

        baseCategoryTrademarkService.save(categoryTrademarkVo);

        return Result.ok();
    }
}
