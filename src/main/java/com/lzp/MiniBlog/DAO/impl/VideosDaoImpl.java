package com.lzp.MiniBlog.DAO.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.DAO.VideosDao;
import com.lzp.MiniBlog.DAO.mapper.FavoriteMapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.VideosRespond;
import com.lzp.MiniBlog.service.UsersService;
import com.lzp.MiniBlog.service.VideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@Service
public class VideosDaoImpl implements VideosDao {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    VideosMapper videosMapper;

    @Override
    public void insertVideo(Videos videos){
        int result = videosMapper.insert(videos);
    }

    @Override
    public Videos QueryVideoById(Integer videoId){
        return videosMapper.selectById(videoId);
    }

    @Override
    public List<Videos> QueryVideoByUserId(Integer targetUserId){
        QueryWrapper<Videos> videosWrapper = new QueryWrapper<>();
        videosWrapper.eq("user_id",targetUserId);
        return videosMapper.selectList(videosWrapper);
    }

    @Override
    public List<Videos> QueryVideoByTimeLimit(Date latestTime){
        QueryWrapper<Videos> videosWrapper = new QueryWrapper<>();
        videosWrapper.le("created_At",latestTime);
        videosWrapper.last("limit 30");
        return videosMapper.selectList(videosWrapper);
    }

    /*
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
