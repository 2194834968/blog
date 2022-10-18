package com.lzp.MiniBlog.DAO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.VideosRespond;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface VideosDao {
    public void insertVideo(Videos videos);

    public Videos QueryVideoById(Integer videoId);

    public List<Videos> QueryVideoByUserId(Integer targetUserId);

    public List<Videos> QueryVideoByTimeLimit(Date latestTime);
}
