package com.lzy.springcloud.search.controller;

import com.lzy.springcloud.manage.bean.PmsBaseAttrInfo;
import com.lzy.springcloud.manage.bean.PmsSearchParam;
import com.lzy.springcloud.manage.bean.PmsSkuinfotoElasticSearch;

import com.lzy.springcloud.manage.service.AttrService;
import com.lzy.springcloud.manage.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Resource(name="search")
    private AttrService attrService;


    @RequestMapping("list.html")
    @ResponseBody
    public List<PmsSkuinfotoElasticSearch> searchList(@RequestBody PmsSearchParam pmsSearchParam){
        List<PmsSkuinfotoElasticSearch> list = searchService.list(pmsSearchParam);
        return list;
    }

    @RequestMapping("/attrInfo")
    @ResponseBody
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(@RequestParam("valueIdSet") String valueIdSet){
        List<PmsBaseAttrInfo> attrValueListByValueId = attrService.getAttrValueListByValueId(valueIdSet);

        return attrValueListByValueId;
    }
}
