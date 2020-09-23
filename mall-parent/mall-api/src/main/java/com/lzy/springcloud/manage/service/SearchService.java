package com.lzy.springcloud.manage.service;

import com.lzy.springcloud.manage.bean.PmsSearchParam;
import com.lzy.springcloud.manage.bean.PmsSkuinfotoElasticSearch;

import java.util.List;

public interface SearchService {

    public List<PmsSkuinfotoElasticSearch> list(PmsSearchParam pmsSearchParam);
}
