package com.lzy.springcloud.search.service;


import com.lzy.springcloud.manage.bean.PmsBaseAttrInfo;
import com.lzy.springcloud.manage.bean.PmsSearchParam;
import com.lzy.springcloud.manage.bean.PmsSkuinfotoElasticSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "MALL-SEARCH-SERVICE")
public interface SearchService {

    @RequestMapping("list.html")
    @ResponseBody
    public List<PmsSkuinfotoElasticSearch> searchList(@RequestBody PmsSearchParam pmsSearchParam);


    @RequestMapping("/attrInfo")
    @ResponseBody
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(@RequestParam("valueIdSet") String valueIdSet);

}
