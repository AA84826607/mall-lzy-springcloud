package com.lzy.springcloud.manage.service;


import com.lzy.springcloud.manage.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {

    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId, String ip);

    public PmsSkuInfo getSkuByIdMysql(String skuId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    List<PmsSkuInfo> getAllSku();


}
