package com.fystart.gmall.product.controller;

import com.fystart.gmall.common.result.Result;
import com.fystart.gmall.model.product.BaseCategoryTrademark;
import com.fystart.gmall.model.product.BaseTrademark;
import com.fystart.gmall.model.product.CategoryTrademarkVo;
import com.fystart.gmall.product.service.BaseCategoryTrademarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作的是三级分类和品牌表的中间表
 *
 * @author fy
 * @date 2022/12/3 19:38
 */
@Api(tags = "分类品牌管理")
@RestController
@RequestMapping("/admin/product/baseCategoryTrademark")
public class BaseCategoryTrademarkController {

    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;

    @ApiOperation(httpMethod = "GET", value = "根据三级分类id获取品牌列表")
    @GetMapping("/findTrademarkList/{category3Id}")
    public Result findTrademarkList(@PathVariable Long category3Id) {
        List<BaseTrademark> baseCategoryTrademarkList = baseCategoryTrademarkService.findTrademarkList(category3Id);
        return Result.ok(baseCategoryTrademarkList);
    }

    @ApiOperation(httpMethod = "DELETE", value = "根据三级分类id和品牌id删除中间表的一条记录")
    @DeleteMapping("remove/{category3Id}/{trademarkId}")
    public Result removeTrademarkByCategory3IdTrademarkId(@PathVariable Long category3Id,
                                                          @PathVariable Long trademarkId) {

        baseCategoryTrademarkService.remove(category3Id, trademarkId);
        return Result.ok();
    }

    @ApiOperation(httpMethod = "GET", value = "根据三级分类获取可选品牌列表")
    @GetMapping("/findCurrentTrademarkList/{category3Id}")
    public Result findCurrentTrademarkList(@PathVariable Long category3Id) {
        List<BaseTrademark> baseTrademarkList = baseCategoryTrademarkService.findCurrentTrademarkList(category3Id);
        return Result.ok(baseTrademarkList);
    }

    @ApiOperation(httpMethod = "POST", value = "向中间表中添加记录")
    @PostMapping("/save")
    public Result save(@RequestBody CategoryTrademarkVo categoryTrademarkVo) {
        baseCategoryTrademarkService.save(categoryTrademarkVo);
        return Result.ok();
    }
}
