package com.lzp.MiniBlog.common.respond;

import com.lzp.MiniBlog.DAO.model.Users;
import lombok.Data;

import java.util.Date;
@Data
public class VideosRespond{

    private Integer id;

    private String title;

    private Users author;

    private String playUrl;
    //文章内容

    private String coverUrl;
    //文章简介

    private Integer favoriteCount;

    private Integer commentCount;

    private Date createdAt;

    private boolean isFavorite;
}
