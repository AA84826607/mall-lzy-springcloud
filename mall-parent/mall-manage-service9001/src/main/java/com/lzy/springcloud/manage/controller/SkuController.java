package com.lzy.springcloud.manage.controller;

import com.alibaba.fastjson.JSON;
import com.lzy.springcloud.manage.bean.PmsProductSaleAttr;
import com.lzy.springcloud.manage.bean.PmsSkuInfo;
import com.lzy.springcloud.manage.bean.PmsSkuSaleAttrValue;
import com.lzy.springcloud.manage.service.SkuService;
import com.lzy.springcloud.manage.service.SpuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SkuController {

      @Resource
      private SkuService skuService;

      @Resource
      private SpuService spuService;

    @RequestMapping("/sku/saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){

        // 将spuId封装给productId
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());

        // 处理默认图片
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if(StringUtils.isBlank(skuDefaultImg)){
            pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
        }


        skuService.saveSkuInfo(pmsSkuInfo);

        return "success";
    }
    @RequestMapping("skuId.html")
    @ResponseBody
    public Map<String,Object> item(@RequestParam("skuId") String skuId,@RequestParam("remoteAddr")String remoteAddr){
        Map<String, Object> map = new HashMap<String, Object>();
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId,remoteAddr);

//        //用nginx负载均衡时用header
//        request.getHeader("");
        //sku对象
        map.put("skuInfo",pmsSkuInfo);
        //销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),pmsSkuInfo.getId());
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);

        // 查询当前sku的spu的其他sku的集合的hash表
        Map<String, String> skuSaleAttrHash = new HashMap<String,String>();
        Map<String, String> skuSaleAttrNameHash = new HashMap<String,String>();
        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());

        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String k = "";
            String name = "";
            String v = skuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";// "239|245"
                name+= pmsSkuSaleAttrValue.getSaleAttrValueName()+" ";
            }
            skuSaleAttrHash.put(k,v);
            skuSaleAttrNameHash.put(v,name);
        }

        // 将sku的销售属性hash表放到页面
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
//        String skuSaleAttrNameHashJsonStr = JSON.toJSONString(skuSaleAttrNameHash);
        map.put("skuSaleAttrNameHashJsonStr",skuSaleAttrNameHash);
        map.put("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);

        return map;
    }

}