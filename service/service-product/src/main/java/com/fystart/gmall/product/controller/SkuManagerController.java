package com.fystart.gmall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fystart.gmall.common.result.Result;
import com.fystart.gmall.model.product.SkuInfo;
import com.fystart.gmall.model.product.SpuImage;
import com.fystart.gmall.model.product.SpuSaleAttr;
import com.fystart.gmall.product.service.BaseMangerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/4 15:11
 */
@Api(tags = "商品SKU接口")
@RestController
@RequestMapping("/admin/product")
public class SkuManagerController {

    @Autowired
    private BaseMangerService baseMangerService;

    @ApiOperation("获取销售属性")
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable Long spuId){

        List<SpuSaleAttr> spuSaleAttrList = baseMangerService.spuSaleAttrList(spuId);

        return Result.ok(spuSaleAttrList);
    }

    @ApiOperation("根据spuId查询图片列表")
    @GetMapping("/spuImageList/{spuId}")
    public Result spuImageList(@PathVariable Long spuId){

        List<SpuImage> spuImageList = baseMangerService.spuImageList(spuId);

        return Result.ok(spuImageList);
    }

    @ApiOperation("保存sku")
    @PostMapping("/saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){

        baseMangerService.saveSkuInfo(skuInfo);

        return Result.ok();
    }

    @ApiOperation("查询sku列表")
    @GetMapping("/list/{current}/{limit}")
    public Result list(@PathVariable Long current,@PathVariable Long limit){

        //封装分页对象
        Page<SkuInfo> page = new Page<>(current,limit);

        IPage<SkuInfo> iPgae = baseMangerService.skuListPage(page);

        return Result.ok(iPgae);
    }
}
