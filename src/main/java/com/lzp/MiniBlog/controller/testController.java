package com.lzp.MiniBlog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class testController {

    @Autowired
    VideosMapper videosMapper;
    @GetMapping("/test")
    public Result test(){
        return Result.ok("接口文档：https://www.apifox.cn/apidoc/shared-3ee2c0f4-568c-4757-b5ea-ebc8f7a82993/api-31628726更新计划以及Github地址：https://github.com/2194834968/blog");

    }

    /*
    @GetMapping("/testQueryVideo")
    public Result testQueryVideo(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "2022-07-28 14:45:00";
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Videos video = QueryVideoBy_Data_UserId_Title_Date("<div>很简单的，Q起手EAWA然后追着A，谁都打不过好吧，打不过存粹就是寄吧菜，哎我操铸币吧怎么那么菜啊</div>", 2, "诺手怎么打上单", date);

        System.out.println("找到了");
        return Result.ok(video);
    }

    private Videos QueryVideoBy_Data_UserId_Title_Date(String data,
                                                       Integer userId,
                                                       String title,
                                                       Date date){
        QueryWrapper<Videos> videosWrapper = new QueryWrapper<>();
        videosWrapper.eq("Cover_Url",data);
        videosWrapper.eq("user_id",userId);
        videosWrapper.eq("title",title);
        videosWrapper.eq("created_At",date);
        return videosMapper.selectOne(videosWrapper);
    }
    */
}
