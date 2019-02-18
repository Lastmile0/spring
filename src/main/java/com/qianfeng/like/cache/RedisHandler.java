package com.qianfeng.like.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Repository
public class RedisHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 重点：
     * 1、key设计：like-userid-videoid
     */
    public void like(String userId,String videoId){
        String keyFormat ="like-"+userId+"-"+videoId;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String s = sdf.format(date);
        redisTemplate.boundValueOps(keyFormat).set(s);
    }

    public Set<String> findLikeByUserId(String userId) {
        String keyFormat = "like-" + userId + "-*";
        Set<String> keys = redisTemplate.keys(keyFormat);
        return keys;
    }
}
