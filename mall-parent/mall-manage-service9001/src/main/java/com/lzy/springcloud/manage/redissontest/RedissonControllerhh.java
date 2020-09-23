package com.lzy.springcloud.manage.redissontest;


import com.lzy.springcloud.manage.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Controller
public class RedissonControllerhh {
    @Resource
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("/testRedisson")
    public String testRedisson(){
        Jedis jedis = redisUtil.getJedis();
        RLock lock = redissonClient.getLock("lock");
        return "success";
    }
}
