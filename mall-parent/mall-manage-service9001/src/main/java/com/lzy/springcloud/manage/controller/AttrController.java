package com.lzy.springcloud.manage.controller;

import com.lzy.springcloud.manage.bean.PmsBaseAttrInfo;
import com.lzy.springcloud.manage.bean.PmsBaseAttrValue;
import com.lzy.springcloud.manage.bean.PmsBaseSaleAttr;
import com.lzy.springcloud.manage.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AttrController {
    @Resource(name = "manage")
    private AttrService attrService;


    @RequestMapping("/attr/baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = attrService.baseSaleAttrList();

        return pmsBaseSaleAttrs;
    }

    @PostMapping("/attr/saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        String s=null;
        try {
            s = attrService.saveAttrInfo(pmsBaseAttrInfo);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }

    @RequestMapping("/attr/attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id){

        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }
    @RequestMapping("/attr/getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam("attrId") String attrId){
        List<PmsBaseAttrValue> attrValueList = attrService.getAttrValueList(attrId);
        return attrValueList;
    }
}
