package com.lzy.springcloud.search.controller;

import com.lzy.springcloud.manage.bean.*;
import com.lzy.springcloud.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("list.html")
    public String list(ModelMap map,PmsSearchParam pmsSearchParam){//三级分类关键字
        //调用搜素服务，返回搜索结果
        List<PmsSkuinfotoElasticSearch> pmsSkuinfotoElasticSearches = searchService.searchList(pmsSearchParam);
        map.put("skuLsInfoList",pmsSkuinfotoElasticSearches);

        //抽取平台属性值

        Set<String> valueSet = new HashSet<>();

        pmsSkuinfotoElasticSearches.forEach((pmsSearchSkuInfo)->
        {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            skuAttrValueList.forEach((pmsSkuAttrValue -> {
                valueSet.add(pmsSkuAttrValue.getValueId());
            }));
        });
        //根据valueId属性列表查询出来
        String valueIdStr = StringUtils.join(valueSet, ",");
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = searchService.getAttrValueListByValueId(valueIdStr);


        //拼接url
        String urlParam=getUrlParam(pmsSearchParam);
        map.put("urlParam",urlParam);


        //删除已选的
        String[] delvalueId = pmsSearchParam.getValueId();
        HashMap<String, String> delmap = new HashMap<>();
        if (delvalueId!=null) {

            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
            while (iterator.hasNext()) {
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                attrValueList.forEach((u) -> {
                    for (String del : delvalueId) {
                        if (del.equals(u.getId())) {
                            delmap.put(u.getId(),u.getValueName());
                            iterator.remove();
                        }
                    }
                });
            }
        }
        //关键字查询
        map.put("keyword",pmsSearchParam.getKeyword());
        //面包屑
        List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();

        if (delvalueId!=null){
            for (String valueid : delvalueId) {

                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setValueId(valueid);
                pmsSearchCrumb.setValueName(delmap.get(valueid));
                pmsSearchCrumb.setUrlParam(getUrlParamForCrumb(pmsSearchParam,valueid));

                pmsSearchCrumbs.add(pmsSearchCrumb);
            }
        }

        map.put("attrValueSelectedList",pmsSearchCrumbs);
        map.put("attrList",pmsBaseAttrInfos);
        return "list";

    }
    private String getUrlParamForCrumb(PmsSearchParam pmsSearchParam,String delValueId){
        String  urlparams="";
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())){
            if (StringUtils.isNotBlank(urlparams)) {
                urlparams += "&catalog3Id=" + pmsSearchParam.getCatalog3Id();
            }
            else{
                urlparams="catalog3Id="+pmsSearchParam.getCatalog3Id();
            }
        }
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
            if (StringUtils.isNotBlank(urlparams)) {
                urlparams += "&keyword="+pmsSearchParam.getKeyword();
            }
            else{
                urlparams="keyword="+pmsSearchParam.getKeyword();
            }
        }
        if (pmsSearchParam.getValueId()!=null) {
            for (String pmsSkuAttrValue : pmsSearchParam.getValueId()) {
                if (!pmsSkuAttrValue.equals(delValueId)) {
                    urlparams += "&valueId=" + pmsSkuAttrValue;

                }
//                else {
//                    urlparams += "&valueId=" + delValueId;
//                    break;
//                }
            }

        }
        return urlparams;
    }

    //
    private String getUrlParam(PmsSearchParam pmsSearchParam) {
        String  urlparams="";
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())){
            if (StringUtils.isNotBlank(urlparams)) {
                urlparams += "&catalog3Id=" + pmsSearchParam.getCatalog3Id();
            }
            else{
                urlparams="catalog3Id="+pmsSearchParam.getCatalog3Id();
            }
        }
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
            if (StringUtils.isNotBlank(urlparams)) {
                urlparams += "&keyword="+pmsSearchParam.getKeyword();
            }
            else{
                urlparams="keyword="+pmsSearchParam.getKeyword();
            }
        }
        if (pmsSearchParam.getValueId()!=null){
            for (String pmsSkuAttrValue : pmsSearchParam.getValueId()) {
                    urlparams += "&valueId="+pmsSkuAttrValue;
            }
        }
        return urlparams;
    }
}
