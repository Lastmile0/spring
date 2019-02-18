package com.qianfeng.like.controller;

import com.qianfeng.like.bean.JsonResult;
import com.qianfeng.like.cache.RedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("/dz")
public class LikeController {
    @Autowired
    private RedisHandler redisHandler;

    @RequestMapping("/like")
    @ResponseBody
    public String like(String userId,String videoId){
        redisHandler.like(userId,videoId);
        return "{code:1}";
    }

    @RequestMapping("/find")
    @ResponseBody
    public JsonResult findAllLikeByUserId(String userId){
        JsonResult jsonResult = new JsonResult();
        Set<String> likeByUserId = redisHandler.findLikeByUserId(userId);
        jsonResult.setCode(1);
        jsonResult.setObject(likeByUserId);
        return  jsonResult;
    }
}
