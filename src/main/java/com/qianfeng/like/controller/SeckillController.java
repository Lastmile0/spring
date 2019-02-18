package com.qianfeng.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ms")
public class SeckillController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/update")
    @ResponseBody
    public String Seckill(){
        List<String> list = redisTemplate.execute(new SessionCallback<List<String>>() {
            /**
             *
             * @param redisOperations redis的工具类
             * @return
             * @throws DataAccessException
             */
            @Override
            public List<String> execute(RedisOperations redisOperations) throws DataAccessException {
               //对指定的key加锁。比如两个同时获取到goodsNum为10，其中一个执行减一操作之后，其他人就不能进行减一操作
                //上锁需要在事务开启前，并且在获取被锁的key之前执行watch操作
                redisTemplate.watch("goods_num");
                //获取库存，需要在事务开启之前获取。
                BoundValueOperations operations = redisOperations.boundValueOps("goods_num");
                Object goodsNumStr = operations.get();
                int goodsNum = Integer.parseInt(goodsNumStr.toString());

                //开启事务
                redisOperations.multi();
                //判断库存数量是否大于0;
                if(goodsNum>0){
                    goodsNum--;
                    redisTemplate.boundValueOps("goods_num").set(String.valueOf(goodsNum));
                }
                //提交事务，提交事务之后释放锁
                return redisOperations.exec();
            }
        });

//        System.out.println(list);
        if(list != null && !list.isEmpty()){
            System.out.println("购买成功");
        }
        return "{code:1}";
    }
}
