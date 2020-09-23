package com.lzy.springcloud.item.service;


import com.lzy.springcloud.manage.bean.PmsSkuInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component
@FeignClient(value = "MALL-MANAGE-SERVICE")
public interface itemSkuService {

    @RequestMapping("skuId.html")
    @ResponseBody
    public Map<String,Object> item(@RequestParam("skuId") String skuId,@RequestParam("remoteAddr") String remoteAddr);



    @RequestMapping("/sku/saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo);
}
