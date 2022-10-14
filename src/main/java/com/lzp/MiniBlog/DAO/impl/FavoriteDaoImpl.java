package com.lzp.MiniBlog.DAO.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.DAO.FavoriteDao;
import com.lzp.MiniBlog.DAO.mapper.FavoriteMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.VideosRespond;
import com.lzp.MiniBlog.service.FavoriteService;
import com.lzp.MiniBlog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Repository
public class FavoriteDaoImpl implements FavoriteDao {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    VideosMapper videosMapper;

    @Autowired
    UsersMapper usersMapper;

    @Override
    public Videos queryVideoIdByVideoId(Integer videoId){
        return videosMapper.selectById(videoId);
    }

    @Override
    public void updateUser_TotalFavorite_ByUserId(Integer userId, Integer actionType){
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

    @Override
    public void updateUser_FavoriteCount_ByUserId(Integer userId, Integer actionType){
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

    @Override
    public void updateVideo_FavoriteCount_ByVideoId(Integer videoId, Integer actionType){
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


    @Override
    public void insertFavorite(Favorite favorite){
        int result = favoriteMapper.insert(favorite);
    }

    @Override
    public void deleteFavorite(Favorite favorite){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("user_id",favorite.getUserId());
        favoriteWrapper.eq("video_id",favorite.getVideoId());
        int result = favoriteMapper.delete(favoriteWrapper);
    }

    @Override
    public Favorite QueryFavoriteVideoByUserIdAndVideoId(Favorite favorite){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("user_id",favorite.getUserId());
        favoriteWrapper.eq("video_id",favorite.getVideoId());

        return favoriteMapper.selectOne(favoriteWrapper);
    }

    @Override
    public List<Videos> QueryFavoriteVideoByUserId(Integer targetUserId){
        QueryWrapper<Favorite> favoriteWrapper = new QueryWrapper<>();
        favoriteWrapper.eq("user_id",targetUserId);
        List<Favorite> FavoriteVideoIdList = favoriteMapper.selectList(favoriteWrapper);

        List<Videos> videosList = new ArrayList<Videos>();
        for(Favorite favoriteTemp: FavoriteVideoIdList){
            videosList.add(QueryVideoById(favoriteTemp.getVideoId()) );
        }
        return videosList;
    }

    @Override
    public Videos QueryVideoById(Integer videoId){
        return videosMapper.selectById(videoId);
    }

    @Override
    public boolean QueryVideoIsFavorite(Integer videoId, Integer userId){
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
