package com.lzy.springcloud.item.controller;


import com.alibaba.fastjson.JSON;
import com.lzy.springcloud.item.service.itemSkuService;
import com.lzy.springcloud.item.service.itemSpuService;
import com.lzy.springcloud.manage.bean.PmsProductSaleAttr;
import com.lzy.springcloud.manage.bean.PmsSkuInfo;
import com.lzy.springcloud.manage.bean.PmsSkuSaleAttrValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {
    @Resource
    itemSkuService skuService;

    @Resource
    itemSpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        Map<String, Object> item = skuService.item(skuId, remoteAddr);

        map.addAllAttributes(item);
        return "item";
    }

}
