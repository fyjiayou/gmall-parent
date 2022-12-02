package com.fystart.gmall.product.controller;

import com.fystart.gmall.common.result.Result;
import com.fystart.gmall.model.product.BaseAttrInfo;
import com.fystart.gmall.model.product.BaseCategory1;
import com.fystart.gmall.model.product.BaseCategory2;
import com.fystart.gmall.model.product.BaseCategory3;
import com.fystart.gmall.product.service.BaseMangerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fy
 * @date 2022/12/1 21:04
 */
@Api(tags = "后台基本管理")
@RestController
@RequestMapping("/admin/product")
public class BaseManagerController {

    @Autowired
    private BaseMangerService baseMangerService;

    @ApiOperation("获取一级分类")
    @GetMapping("/getCategory1")
    public Result getCategory1() {

        List<BaseCategory1> category1List = baseMangerService.getCategory1Info();

        return Result.ok(category1List);
    }

    @ApiOperation("根据一级分类id查询二级分类")
    @GetMapping("/getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable String category1Id) {

        List<BaseCategory2> category2List = baseMangerService.getCategory2Info(category1Id);

        return Result.ok(category2List);
    }

    @ApiOperation("根据二级分类Id查询三级分类")
    @GetMapping("/getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable String category2Id) {

        List<BaseCategory3> category3List = baseMangerService.getCategory3Info(category2Id);

        return Result.ok(category3List);
    }

    @ApiOperation("查询平台属性")
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable(value = "category1Id", required = false) Long category1Id,
                               @PathVariable(value = "category2Id", required = false) Long category2Id,
                               @PathVariable(value = "category3Id", required = false) Long category3Id) {

        List<BaseAttrInfo> baseAttrInfoList = baseMangerService.attrInfoList(category1Id, category2Id, category3Id);

        return Result.ok(baseAttrInfoList);
    }

}
