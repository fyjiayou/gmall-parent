package com.fystart.gmall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fystart.gmall.common.result.Result;
import com.fystart.gmall.model.product.BaseSaleAttr;
import com.fystart.gmall.model.product.SpuInfo;
import com.fystart.gmall.product.service.BaseMangerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品spu保存
 *
 * @author fy
 * @date 2022/12/3 15:55
 */
@RestController
@RequestMapping("/admin/product")
public class SpuManagerController {

    @Autowired
    private BaseMangerService managerService;

    /**
     * spu分页列表查询
     * http://api.gmall.com/admin/product/1/10?category3Id=61
     *
     * @RequestBody的作用 ：将前台传递过来的json{"category3Id":"61"}  字符串变为java 对象。
     */
    @ApiOperation(httpMethod = "GET", value = "spu分页列表查询")
    @GetMapping("/{current}/{limit}")
    public Result getSpuInfoPage(@PathVariable Long current,
                                 @PathVariable Long limit,
                                 Long category3Id) {

        Page<SpuInfo> page = new Page<>(current, limit);
        // 获取数据
        IPage<SpuInfo> spuInfoPageList = managerService.getSpuInfoPage(page, category3Id);
        // 将获取到的数据返回即可！
        return Result.ok(spuInfoPageList);
    }

    @ApiOperation(httpMethod = "GET", value = "获取销售属性列表")
    @GetMapping("/baseSaleAttrList")
    public Result baseSaleAttrList() {
        List<BaseSaleAttr> baseSaleAttrList = managerService.baseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }

    @ApiOperation(httpMethod = "POST", value = "spu保存")
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo) {
        managerService.saveSpuInfo(spuInfo);
        return Result.ok();
    }
}
