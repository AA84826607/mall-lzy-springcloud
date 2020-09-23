package com.lzy.springcloud.search.service;


import com.lzy.springcloud.manage.bean.PmsBaseAttrInfo;
import com.lzy.springcloud.manage.bean.PmsBaseAttrValue;
import com.lzy.springcloud.manage.bean.PmsBaseSaleAttr;

import com.lzy.springcloud.manage.service.AttrService;
import com.lzy.springcloud.search.mapper.PmsBaseAttrInfoMapper;
import com.lzy.springcloud.search.mapper.PmsBaseAttrValueMapper;
import com.lzy.springcloud.search.mapper.PmsBaseSaleAttrMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("search")
public class AttrServiceImpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;




    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {

        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);

        for (PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfos) {
            List<PmsBaseAttrValue> baseAttrValues=new ArrayList<PmsBaseAttrValue>();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());

            baseAttrValues=pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(baseAttrValues);
        }
        return pmsBaseAttrInfos;
    }


    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        //获得属性值

        if (StringUtils.isBlank(id)){
            //id为空，保存
            //存储属性名称
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            //存储属性值

            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();

            for (PmsBaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(id);
                pmsBaseAttrValueMapper.insertSelective(baseAttrValue);
            }

        }
        else{
            //id不空，修改属性

            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            //先按照属性id删除所有属性值
            PmsBaseAttrValue pmsBaseAttrValuedel = new PmsBaseAttrValue();
            pmsBaseAttrValuedel.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.delete(pmsBaseAttrValuedel);
            //后再添加新的属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValues : attrValueList) {
                pmsBaseAttrValues.setAttrId(id);
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValues);
            }

        }


        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
        return pmsBaseAttrValues;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();

        return pmsBaseSaleAttrs;
    }

    @Override
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(String valueIdSet) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos=pmsBaseAttrInfoMapper.selectAttrValueListByValueId(valueIdSet);

        return pmsBaseAttrInfos;
    }
}
