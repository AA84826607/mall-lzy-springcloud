package com.lzy.springcloud.search.service;

import com.alibaba.fastjson.JSON;
import com.lzy.springcloud.manage.bean.PmsSearchParam;
import com.lzy.springcloud.manage.bean.PmsSkuAttrValue;
import com.lzy.springcloud.manage.bean.PmsSkuinfotoElasticSearch;
import com.lzy.springcloud.manage.service.SearchService;
import com.lzy.springcloud.manage.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SearchServicelmpl implements SearchService {

    @Autowired
    private SkuService skuService;
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public List<PmsSkuinfotoElasticSearch> list(PmsSearchParam pmsSearchParam) {
        SearchRequest se = getSearchDsl(pmsSearchParam);
        SearchHits hits = null;
        try {
            hits = restHighLevelClient.search(se).getHits();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<PmsSkuinfotoElasticSearch> list1 = new ArrayList<>();
        hits.forEach((one)->{
            Map<String, Object> sourceAsMap = one.getSourceAsMap();
            Map<String, HighlightField> highlightFields = one.getHighlightFields();
            PmsSkuinfotoElasticSearch pmsSkuinfotoElasticSearch = JSON.parseObject(JSON.toJSONString(sourceAsMap), PmsSkuinfotoElasticSearch.class);
            if (highlightFields.size()!=0) {
                String skuName = highlightFields.get("skuName").toString();
                int a = skuName.indexOf("[[");
                int b = skuName.indexOf("]]");
                if ((a != -1) && (b != -1) && (a < b)) {
                    skuName = skuName.substring(a + 2, b);
                    pmsSkuinfotoElasticSearch.setSkuName(skuName);
                }
            }
                list1.add(pmsSkuinfotoElasticSearch);

        });
        return list1;
    }
    public static SearchRequest getSearchDsl(PmsSearchParam pmsSearchParam){
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder matchQueryBuilder=null;
        TermQueryBuilder catalog3Id=null;
        //先过滤后开始
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())) {
            catalog3Id = new TermQueryBuilder("catalog3Id", pmsSearchParam.getCatalog3Id());
            boolQueryBuilder.filter(catalog3Id);
        }
        if (skuAttrValueList!=null){
            for (String s : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", s);
                boolQueryBuilder.filter(termQueryBuilder);
            }

        }
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())) {
            matchQueryBuilder = new MatchQueryBuilder("skuName", pmsSearchParam.getKeyword());
            boolQueryBuilder.must(matchQueryBuilder);
        }



        searchSourceBuilder.query(boolQueryBuilder);
        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        searchSourceBuilder.sort("id", SortOrder.DESC);
        SearchRequest se = new SearchRequest("mall");
        se.types("PmsSkuInfo");
        se.source(searchSourceBuilder);
        return se;
    }
}
