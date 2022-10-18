package com.lzp.MiniBlog.DAO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.VideosRespond;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface FavoriteDao{

    public void updateUser_TotalFavorite_ByUserId(Integer userId, Integer actionType);

    public void updateUser_FavoriteCount_ByUserId(Integer userId, Integer actionType);

    public void updateVideo_FavoriteCount_ByVideoId(Integer videoId, Integer actionType);

    public void insertFavorite(Favorite favorite);

    public void deleteFavorite(Favorite favorite);

    //查询已存在的记录
    public Favorite QueryFavoriteVideoByUserIdAndVideoId(Favorite favorite);

    public List<Videos> QueryFavoriteVideoByUserId(Integer targetUserId);

    public boolean QueryVideoIsFavorite(Integer videoId, Integer userId);
}
