package com.lzy.springcloud.search.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.springcloud.manage.bean.*;
import com.lzy.springcloud.manage.service.SkuService;
import com.lzy.springcloud.search.mapper.PmsSkuAttrValueMapper;
import com.lzy.springcloud.search.mapper.PmsSkuImageMapper;
import com.lzy.springcloud.search.mapper.PmsSkuInfoMapper;
import com.lzy.springcloud.search.mapper.PmsSkuSaleAttrValueMapper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {


    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    @Transactional
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insert(pmsSkuImage);
        }

        //插入到elasitcsearch

            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(pmsSkuInfo));
            Map map = jsonObject.toJavaObject(jsonObject, Map.class);

            BulkRequest bulkRequest = new BulkRequest();
            IndexRequest indexRequest = new IndexRequest("mall","PmsSkuInfo",jsonObject.getString("id")).source(map);
            bulkRequest.add(indexRequest);
            try {
                restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }



    }


    @Override
    public PmsSkuInfo getSkuById(String skuId,String ip) {
        System.out.println("ip为"+ip+"的同学"+Thread.currentThread().getName()+"进入的商品详情的请求");
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
//        链接缓存

//        查询缓存
        String skuKey="sku:"+skuId+":info";
        BoundValueOperations<String, String> sku = redisTemplate.boundValueOps(skuKey);
        if (StringUtils.isNotBlank(sku.get())) {
            pmsSkuInfo = JSON.parseObject(sku.get(),PmsSkuInfo.class);
            System.out.println("ip为"+ip+"的同学"+Thread.currentThread().getName()+"从缓存中获取商品详情");
        }
        //        如果缓存中没有，查询mysql

        else {
            System.out.println("ip为"+ip+"的同学"+Thread.currentThread().getName()+"发现缓存中没有，申请缓存的分布式锁"+"sku:"+skuId+":lock");
             //设置分布式锁
            String token = UUID.randomUUID().toString();
            RLock lock = redissonClient.getLock("sku:" + skuId + ":lock");
//            String OK = jedis("sku:"+skuId+":lock",token,"nx","px",10);//拿到锁的线程有10秒的过期时间
            if (lock.tryLock()) {
                try {

                    System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "成功拿到锁，有权在十秒内访问" + "sku:" + skuId + ":lock");
                    pmsSkuInfo = getSkuByIdMysql(skuId);
                    try {
                        Thread.sleep(1000 * 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (pmsSkuInfo != null) {
                        //mysql查询结果存入redis
                        redisTemplate.boundValueOps("sku:" + skuId + ":info").set(JSON.toJSONString(pmsSkuInfo));

//                    jedis.set("sku:"+skuId+":info",JSON.toJSONString(pmsSkuInfo));
                        //释放下锁

                    } else {
//              数据库中不存在该sku
//
//              为了防止缓存穿透将null或者空字符串值设置给redis
                        redisTemplate.boundValueOps("sku:" + skuId + ":info").set(JSON.toJSONString(""), 60 * 3);


                    }
                    System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "使用完毕，将锁归还");
                    //释放分布式锁

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    lock.notifyAll();
                }
            }
            else {

                //设置失败，自旋，（该线程在睡眠几秒后，重新尝试访问本方法）
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ip为" + ip + "的同学:" + Thread.currentThread().getName() + "没有拿到锁，开始自旋");
                return getSkuById(skuId, ip);

            }
        }
//
        return pmsSkuInfo;
    }

    @Override
        public PmsSkuInfo getSkuByIdMysql(String skuId) {
            PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
            pmsSkuInfo.setId(skuId);

            PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
            PmsSkuImage pmsSkuImage = new PmsSkuImage();
            pmsSkuImage.setSkuId(skuId);
            List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
            skuInfo.setSkuImageList(pmsSkuImages);
            return skuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();
        pmsSkuInfos.forEach((pmsSkuInfo)->{
            String skuId = pmsSkuInfo.getId();
            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
            pmsSkuInfo.setSkuAttrValueList(select);

        });
        return pmsSkuInfos;
    }


}
