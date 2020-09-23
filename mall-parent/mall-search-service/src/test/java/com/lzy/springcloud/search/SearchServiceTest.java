package com.lzy.springcloud.search;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.springcloud.manage.bean.PmsSkuInfo;
import com.lzy.springcloud.manage.service.SkuService;
import com.lzy.springcloud.search.bean.PmsSkuinfotoElasticSearch;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.*;
import org.elasticsearch.indices.recovery.RecoveryState;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainSearchService8074.class)
public class SearchServiceTest {
    @Autowired
    private SkuService skuService;
    @Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void content() throws IOException {
        GetIndexRequest mall = new GetIndexRequest("mall");
        boolean exists = restHighLevelClient.indices().exists(mall, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
       @Test
    public void selectal() throws IOException {
           SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
           BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
           List<String> list = Arrays.asList("39", "40", "41");
           TermsQueryBuilder catalog3Id = new TermsQueryBuilder("catalog3Id", "59");
           TermsQueryBuilder termsQueryBuilder = new TermsQueryBuilder("skuAttrValueList.valueId", list);
           MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "华为");
           boolQueryBuilder.filter(termsQueryBuilder);
           boolQueryBuilder.filter(catalog3Id);
           boolQueryBuilder.must(matchQueryBuilder);
           searchSourceBuilder.query(boolQueryBuilder);
           SearchRequest se = new SearchRequest("mall");
           se.types("PmsSkuInfo");
           se.source(searchSourceBuilder);
           SearchHits hits = null;
           try {
               hits = restHighLevelClient.search(se).getHits();
           } catch (IOException e) {
               e.printStackTrace();
           }
           List<PmsSkuinfotoElasticSearch> list1 = new ArrayList<>();
           hits.forEach((one)->{
               Map<String, Object> sourceAsMap = one.getSourceAsMap();
               PmsSkuinfotoElasticSearch pmsSkuinfotoElasticSearch = JSON.parseObject(JSON.toJSONString(sourceAsMap),PmsSkuinfotoElasticSearch.class);
               System.out.println(pmsSkuinfotoElasticSearch);
               list1.add(pmsSkuinfotoElasticSearch);
           });


       }

       @Test
       public void select2() throws Exception{
           SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
           BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
           //先过滤后开始
           boolQueryBuilder.filter();
           List<String> list = Arrays.asList("39", "40", "41");

           TermsQueryBuilder termsQueryBuilder = new TermsQueryBuilder("skuAttrValueList.valueId", list);
           MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "华为");
           boolQueryBuilder.filter(termsQueryBuilder);
           boolQueryBuilder.must(matchQueryBuilder);
           searchSourceBuilder.query(boolQueryBuilder);
           SearchRequest se = new SearchRequest("mall");
           se.types("PmsSkuInfo");
           se.source(searchSourceBuilder);
           SearchHits hits = restHighLevelClient.search(se).getHits();
           List<Map<String, Object>> maps = new ArrayList<>();
           hits.forEach((one)->{
               Map<String, Object> sourceAsMap = one.getSourceAsMap();
               String s = JSON.toJSONString(sourceAsMap);
               System.out.println(s);
           });
       }
       @Test
       public void insert() throws Exception{
           //查询数据库数据
           List<PmsSkuInfo> pmsSkuInfos = skuService.getAllSku();
           pmsSkuInfos.forEach(pmsSkuInfo -> {
               JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(pmsSkuInfo));
               Map map = jsonObject.toJavaObject(jsonObject, Map.class);

               BulkRequest bulkRequest = new BulkRequest();
               IndexRequest indexRequest = new IndexRequest("mall","PmsSkuInfo",jsonObject.getString("id")).source(map);
               bulkRequest.add(indexRequest);
               try {
                   restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });

       }
}
