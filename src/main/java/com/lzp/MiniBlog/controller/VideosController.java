package com.lzp.MiniBlog.controller;


import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.VideosService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@RestController
public class VideosController {

    @Autowired
    VideosService videosService;

    @GetMapping("/feed")
    public Result feed(@RequestParam(value = "latest_time", required = false) String latestTime ,
                       @RequestParam(value = "token", required = false) String token){

        //获取时间戳
        Date date = new Date();
        if(!latestTime.isEmpty()){
            if(isTimeStr(latestTime)){
                date = timeChange(latestTime);
            }else{
                return Result.fail(ResultCodeEnum.TIMESTAMP_WRONG);
            }
        }

        Integer userId = null;

        //校验token
        if(!token.isEmpty()){
            if(!JwtUtils.verifyToken(token)){
                return Result.fail(ResultCodeEnum.TOKEN_OUTTIME_OR_UN_EXIST);
            }
            userId = JwtUtils.verifyTokenBackUserId(token);
        }else{
            userId = 0;
        }

        return Result.ok(videosService.feed(date,userId));
    }

    @Data
    private class PublishVideo{
        private String data;
        private String token;
        private String title;
    }

    @PostMapping("/publish/action")
    public Result publish(PublishVideo publishVideo){
        //取出用户id
        Integer userId = null;
        //校验token
        if(!publishVideo.token.isEmpty()){
            if(!JwtUtils.verifyToken(publishVideo.token)){
                return Result.fail(ResultCodeEnum.TOKEN_OUTTIME_OR_UN_EXIST);
            }
            userId = JwtUtils.verifyTokenBackUserId(publishVideo.token);
        }else{
            return Result.fail(ResultCodeEnum.NEED_TOKEN);
        }

        if(publishVideo.data.isEmpty() || publishVideo.title.isEmpty()){
            return Result.fail(ResultCodeEnum.NEED_DATA_OR_TITLE);
        }

        boolean flag = videosService.publish(publishVideo.data, userId, publishVideo.title);
        if(flag){
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/publish/list")
    public Result publishList(@RequestParam(value = "token") String token,
                              @RequestParam(value = "user_id") String targetUserIdStr){
        //取出用户id
        Integer userId = null;
        //校验token
        if(!token.isEmpty()){
            if(!JwtUtils.verifyToken(token)){
                return Result.fail(ResultCodeEnum.TOKEN_OUTTIME_OR_UN_EXIST);
            }
            userId = JwtUtils.verifyTokenBackUserId(token);
        }else{
            return Result.fail(ResultCodeEnum.NEED_TOKEN);
        }

        Integer targetUserId = null;
        //取出目标用户id
        if(!targetUserIdStr.isEmpty()){
            targetUserId = Integer.valueOf(targetUserIdStr);
        }else{
            return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
        }

        return Result.ok(videosService.publishList(targetUserId,userId));
    }

    //判断时间戳是否正确
    private boolean isTimeStr(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try{
            date = sdf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //时间戳转换成date
    private Date timeChange(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try{
            date = sdf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
        //输出：Date:Wed Oct 14 10:10:00 CST 2020
    }
}

