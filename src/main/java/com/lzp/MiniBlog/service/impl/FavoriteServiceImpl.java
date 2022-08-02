package com.lzp.MiniBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lzp.MiniBlog.DAO.mapper.FavoriteMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.VideosRespond;
import com.lzp.MiniBlog.service.FavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Autowired
    UsersService usersService;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    VideosMapper videosMapper;

    @Autowired
    UsersMapper usersMapper;

    @Override
    public List<VideosRespond> favoriteList(Integer targetUserId, Integer userId){
        //取得视频列表
        List<Videos> videosList = QueryFavoriteVideoByUserId(targetUserId);

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

    @Override
    public boolean favoriteAction(Integer userId, Integer videoId, Integer actionType){
        //确认视频存在
        Videos videosTemp = queryVideoIdByVideoId(videoId);
        if(videosTemp == null){
            return false;
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setVideoId(videoId);
        //查询已存在的记录
        Favorite favoriteTemp = QueryFavoriteVideoByUserIdAndVideoId(favorite);

        if(actionType == 1 && favoriteTemp == null){
            //在favorite表中添加/删除记录
            insertFavorite(favorite);
        }else if(actionType == 2 && favoriteTemp != null){
            //在favorite表中添加/删除记录
            deleteFavorite(favorite);

        }else{
            return false;
        }

        //在user表中修改视频作者的Total_Favorite
        int AuthorId = QueryVideoById(videoId).getUserId();
        updateUser_TotalFavorite_ByUserId(AuthorId, actionType);

        //在user表中修改点赞者的Favorite_Count
        updateUser_FavoriteCount_ByUserId(userId, actionType);

        //在video表中修改视频的Favorite_Count
        updateVideo_FavoriteCount_ByVideoId(videoId, actionType);
        return true;
    }

    private Videos queryVideoIdByVideoId(Integer videoId){
        return videosMapper.selectById(videoId);
    }

    private void updateUser_TotalFavorite_ByUserId(Integer userId, Integer actionType){
        UpdateWrapper<Users> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("Id",userId);
        userWrapper.last("FOR UPDATE");//上锁
        Users userTemp = usersMapper.selectOne(userWrapper);

        if(actionType == 1){
            userTemp.setTotalFavorite(userTemp.getTotalFavorite() + 1);
        }else if(actionType == 2){
            userTemp.setTotalFavorite(userTemp.getTotalFavorite() - 1);
        }
        int result = usersMapper.updateById(userTemp);
    }

    private void updateUser_FavoriteCount_ByUserId(Integer userId, Integer actionType){
        UpdateWrapper<Users> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("Id",userId);
        userWrapper.last("FOR UPDATE");//上锁
        Users userTemp = usersMapper.selectOne(userWrapper);


        if(actionType == 1){
            userTemp.setFavoriteCount(userTemp.getFavoriteCount() + 1);
        }else if(actionType == 2){
            userTemp.setFavoriteCount(userTemp.getFavoriteCount() + 1);
        }
        int result = usersMapper.updateById(userTemp);
    }

    private void updateVideo_FavoriteCount_ByVideoId(Integer videoId, Integer actionType){
        UpdateWrapper<Videos> videosWrapper = new UpdateWrapper<>();
        videosWrapper.eq("Id",videoId);
        videosWrapper.last("FOR UPDATE");//上锁
        Videos videoTemp = videosMapper.selectOne(videosWrapper);

        if(actionType == 1){
            videoTemp.setFavoriteCount(videoTemp.getFavoriteCount() + 1);
        }else if(actionType == 2){
            videoTemp.setFavoriteCount(videoTemp.getFavoriteCount() - 1);
        }
        int result = videosMapper.updateById(videoTemp);
    }


    private void insertFavorite(Favorite favorite){
        int result = favoriteMapper.insert(favorite);
    }

    private void deleteFavorite(Favorite favorite){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("user_id",favorite.getUserId());
        favoriteWrapper.eq("video_id",favorite.getVideoId());
        int result = favoriteMapper.delete(favoriteWrapper);
    }

    private Favorite QueryFavoriteVideoByUserIdAndVideoId(Favorite favorite){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("user_id",favorite.getUserId());
        favoriteWrapper.eq("video_id",favorite.getVideoId());

        return favoriteMapper.selectOne(favoriteWrapper);
    }
    private List<Videos> QueryFavoriteVideoByUserId(Integer targetUserId){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("user_id",targetUserId);
        List<Favorite> FavoriteVideoIdList = favoriteMapper.selectList(favoriteWrapper);

        List<Videos> videosList = new ArrayList<Videos>();
        for(Favorite favoriteTemp: FavoriteVideoIdList){
            videosList.add(QueryVideoById(favoriteTemp.getVideoId()) );
        }
        return videosList;
    }

    private Videos QueryVideoById(Integer videoId){
        return videosMapper.selectById(videoId);
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
