package com.lzy.springcloud.manage.controller;

import com.lzy.springcloud.manage.bean.PmsSkuInfo;
import com.lzy.springcloud.manage.service.SkuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "item的web sku类")
@CrossOrigin
public class SkuController {
    @Resource
    private SkuService skuService;

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){

        return skuService.saveSkuInfo(pmsSkuInfo);
    }
}
