package com.lzy.springcloud.manage.controller;

import com.lzy.springcloud.manage.utils.Utils.PmsUploadUtil;
import com.lzy.springcloud.manage.bean.PmsProductImage;
import com.lzy.springcloud.manage.bean.PmsProductInfo;
import com.lzy.springcloud.manage.bean.PmsProductSaleAttr;
import com.lzy.springcloud.manage.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class SpuController {
    @Resource
   private  SpuService spuService;

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        //把图片存储但分布文件存储系统服务器
        //将图片的存储路径返回给页面
        String url= PmsUploadUtil.uploadImage(multipartFile);
        //返回图片的地址到前台
        System.out.println(url);
        return url;
    }

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(@RequestParam("catalog3Id") String catalog3Id){
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }
    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(@RequestParam("spuId") String spuId){
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;

    }
    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(@RequestParam("spuId") String spuId){
        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;

    }




}
