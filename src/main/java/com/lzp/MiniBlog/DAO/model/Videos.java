package com.lzp.MiniBlog.DAO.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Videos implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private Integer userId;

    @TableField("Play_Url")
    private String playUrl;
    //文章简介

    @TableField("Cover_Url")
    private String coverUrl;
    //文章内容

    @TableField("Favorite_Count")
    private Integer favoriteCount;

    @TableField("Comment_Count")
    private Integer commentCount;

    @TableField("Created_At")
    private Date createdAt;
}
