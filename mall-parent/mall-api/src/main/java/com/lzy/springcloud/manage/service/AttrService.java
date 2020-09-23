package com.lzy.springcloud.manage.service;



import com.lzy.springcloud.manage.bean.PmsBaseAttrInfo;
import com.lzy.springcloud.manage.bean.PmsBaseAttrValue;
import com.lzy.springcloud.manage.bean.PmsBaseSaleAttr;

import java.util.List;
import java.util.Set;


public interface AttrService {


    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getAttrValueListByValueId(String valueIdSet);
}
