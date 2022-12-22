package com.fystart.gmall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fystart.gmall.common.result.Result;
import com.fystart.gmall.model.product.BaseTrademark;
import com.fystart.gmall.product.service.BaseTrademarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌管理的CRUD操作
 *
 * @author fy
 * @date 2022/12/3 19:17
 */
@Api(tags = "品牌管理")
@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

    @Autowired
    private BaseTrademarkService baseTrademarkService;

    @ApiOperation(httpMethod = "GET", value = "分页列表")
    @GetMapping("/{current}/{limit}")
    public Result index(@PathVariable Long current,
                        @PathVariable Long limit) {

        Page<BaseTrademark> page = new Page<>(current, limit);

        LambdaQueryWrapper<BaseTrademark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(BaseTrademark::getId);

        Page<BaseTrademark> trademarkPage = baseTrademarkService.page(page, queryWrapper);

        return Result.ok(trademarkPage);
    }

    @ApiOperation(httpMethod = "GET", value = "获取品牌id得到详细数据")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.ok(baseTrademark);
    }

    @ApiOperation(httpMethod = "POST", value = "新增操作")
    @PostMapping("save")
    public Result save(@RequestBody BaseTrademark banner) {
        baseTrademarkService.save(banner);
        return Result.ok();
    }

    @ApiOperation(httpMethod = "PUT", value = "修改操作")
    @PutMapping("update")
    public Result updateById(@RequestBody BaseTrademark banner) {
        baseTrademarkService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(httpMethod = "DELETE", value = "删除操作")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        baseTrademarkService.removeById(id);
        return Result.ok();
    }

}
