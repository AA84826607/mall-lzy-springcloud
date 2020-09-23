package com.lzy.springcloud.manage.service;

import com.lzy.springcloud.manage.bean.PmsSkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "MALL-MANAGE-SERVICE")
public interface SkuService {

    @RequestMapping("/sku/saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo);
}
