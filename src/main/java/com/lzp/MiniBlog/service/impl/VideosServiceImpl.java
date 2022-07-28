package com.lzp.MiniBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.DAO.mapper.FavoriteMapper;
import com.lzp.MiniBlog.DAO.mapper.RelationMapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Relation;
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
public class VideosServiceImpl extends ServiceImpl<VideosMapper, Videos> implements VideosService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    VideosMapper videosMapper;

    @Autowired
    private UsersService usersService;

    @Override
    public List<VideosRespond> feed(Date latestTime, Integer userId){
        //取得视频列表
        List<Videos> videosList = QueryVideoByTimeLimit(latestTime);

        List<VideosRespond> respondList = new ArrayList<VideosRespond>();

        //进行数据处理,查询作者信息以及当前用户是否喜欢
        for(Videos videoTemp: videosList){
            VideosRespond videosRespondTemp = new VideosRespond();

            //数据处理
            videosRespondTemp.setAuthor(usersService.userInfo(videoTemp.getUserId(),userId));
            if(userId == 0){
                videosRespondTemp.setFavorite(false);
            }else{
                videosRespondTemp.setFavorite(QueryVideoIsFavorite(videoTemp.getId(),userId));
            }

            videosRespondTemp.setId(videoTemp.getId());
            videosRespondTemp.setTitle(videoTemp.getTitle());
            videosRespondTemp.setPlayUrl(videoTemp.getPlayUrl());
            videosRespondTemp.setCoverUrl(videoTemp.getCoverUrl());
            videosRespondTemp.setFavoriteCount(videoTemp.getFavoriteCount());
            videosRespondTemp.setCommentCount(videoTemp.getCommentCount());
            videosRespondTemp.setCreatedAt(videoTemp.getCreatedAt());

            respondList.add(videosRespondTemp);
        }
        return respondList;
    }

    private List<Videos> QueryVideoByTimeLimit(Date latestTime){
        QueryWrapper<Videos> videosWrapper = new QueryWrapper<>();
        videosWrapper.le("created_At",latestTime);
        videosWrapper.last("limit 30");
        return videosMapper.selectList(videosWrapper);
    }

    private boolean QueryVideoIsFavorite(Integer videoId, Integer userId){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("video_id",videoId);
        favoriteWrapper.eq("user_id",userId);
        Favorite favoriteTemp = favoriteMapper.selectOne(favoriteWrapper);
        if(favoriteTemp != null){
            return true;
        }
        return false;
    }

}
