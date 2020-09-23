package com.lzy.springcloud.manage.service;

import com.lzy.springcloud.manage.bean.PmsBaseCatalog1;
import com.lzy.springcloud.manage.bean.PmsBaseCatalog2;
import com.lzy.springcloud.manage.bean.PmsBaseCatalog3;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "MALL-MANAGE-SERVICE")
public interface CatalogService {


    @RequestMapping("/getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(@RequestParam("catalog2Id") String catalog2Id);

    @RequestMapping("/getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(@RequestParam("catalog1Id") String catalog1Id);


    @RequestMapping("/getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1();
}
