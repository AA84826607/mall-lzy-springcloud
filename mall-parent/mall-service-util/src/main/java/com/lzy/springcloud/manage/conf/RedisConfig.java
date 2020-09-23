package com.lzy.springcloud.manage.conf;


import com.lzy.springcloud.manage.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    //读取配置文件中的redis的ip地址
    @Value("${spring.redis.host:localhost}")
    private String host;
    @Value("${spring.redis.port:5672}")
    private int port ;
    @Value("${spring.redis.database:0}")
    private int database;
    @Bean
    public RedisUtil getRedisUtil(){
        if(host.equals("disabled")){
            return null;
        }
        RedisUtil redisUtil=new RedisUtil();
        redisUtil.initPool(host,port,database);
        return redisUtil;
    }
}
