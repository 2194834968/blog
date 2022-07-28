package com.lzp.MiniBlog.service;

import com.lzp.MiniBlog.DAO.model.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.MiniBlog.common.respond.VideosRespond;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface FavoriteService extends IService<Favorite> {
    public List<VideosRespond> favoriteList(Integer targetUserId, Integer userId);
}
