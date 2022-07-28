package com.lzp.MiniBlog;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Videos;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class QueryVideo {
    VideosMapper videosMapper;

    public static void main(String[] args) throws ParseException {
        QueryVideo queryVideo = new QueryVideo();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "2022-07-28 14:45:00";
        Date date = simpleDateFormat.parse(dateStr);


        if(queryVideo.QueryVideoBy_Data_UserId_Title_Date("<div>很简单的，Q起手EAWA然后追着A，谁都打不过好吧，打不过存粹就是寄吧菜，哎我操铸币吧怎么那么菜啊</div>", 2, "诺手怎么打上单", date) == null){
            System.out.println("找不到啊");
        }
        System.out.println("找到了");
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
}
