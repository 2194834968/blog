package com.lzp.MiniBlog.service;

import com.lzp.MiniBlog.DAO.model.Videos;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface VideosService extends IService<Videos> {
    public List<VideosRespond> feed(Date latestTime, Integer userId);
}
