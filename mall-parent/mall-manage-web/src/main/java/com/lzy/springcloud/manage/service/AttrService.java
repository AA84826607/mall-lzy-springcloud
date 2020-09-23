package com.lzy.springcloud.manage.service;

import com.lzy.springcloud.manage.bean.PmsBaseSaleAttr;
import com.lzy.springcloud.manage.bean.PmsBaseAttrInfo;
import com.lzy.springcloud.manage.bean.PmsBaseAttrValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "MALL-MANAGE-SERVICE")
public interface AttrService {

    @RequestMapping("/attr/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList();

    @PostMapping("/attr/saveAttrInfo")
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo);

    @RequestMapping("/attr/attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam("catalog3Id")String catalog3Id);

    @RequestMapping("/attr/getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam("attrId")String attrId);
}
