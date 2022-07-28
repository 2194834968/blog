package com.lzp.MiniBlog.controller;


import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.VideosService;
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
            date = timeChange(latestTime);
        }

        Integer userId = null;
        //校验token
        if(!token.isEmpty()){
            if(!JwtUtils.verifyToken(token)){
                return Result.fail(ResultCodeEnum.TOKEN_OUTTIME);
            }
            userId = JwtUtils.verifyTokenBackUserId(token);
        }else{
            userId = 0;
        }

        return Result.ok(videosService.feed(date,userId));
    }

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

