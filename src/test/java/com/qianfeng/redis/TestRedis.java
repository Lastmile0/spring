package com.qianfeng.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-redis.xml")
public class TestRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 存储字符串
     */
    @Test
    public void testCase1(){
        redisTemplate.boundValueOps("spring-name").set("666");
    }

    /**
     * 读取字符串
     */
    @Test
    public void testCase2(){
        String s = redisTemplate.boundValueOps("spring-name").get();
        System.out.println(s);

    }
    @Test
    public void testCase3(){
        /**
         * 参数1：保存的key
         * 参数2：值
         * 参数3：时间数字
         * 参数4：时间单位
         */
        redisTemplate.boundValueOps("test_restore").set("java",30,TimeUnit.SECONDS);
    }

    /**
     * 存储hash值
     */
    @Test
    public void testCase4(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name","zhangsan");
        hashMap.put("age","23");
        redisTemplate.boundHashOps("spring-hash").putAll(hashMap);
    }

    /**
     * 读取hash值
     */
    @Test
    public void testCase5(){
        Map<Object, Object> map = redisTemplate.boundHashOps("spring-hash").entries();
        System.out.println(map);
    }

    @Test
    public void testCase6(){
        redisTemplate.boundListOps("spring-list").leftPushAll("abc","def","sjsisa","sfvsdf");
    }

    @Test
    public void testCase7(){
        List<String> range = redisTemplate.boundListOps("spring-list").range(0, 4);
        System.out.println(range);
    }

    @Test
    public void testCase8(){
        redisTemplate.boundSetOps("spring-set2").add("3333","333");
        redisTemplate.boundZSetOps("spring-set2").add("3333",3);

    }

    @Test
    public void testCase9(){
        Set<String> members = redisTemplate.boundSetOps("spring-set").members();
        System.out.println(members);

    }
}
