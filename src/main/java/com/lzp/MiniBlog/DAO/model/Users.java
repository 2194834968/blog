package com.lzp.MiniBlog.DAO.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    @TableField("Name")
    private String name;
    //昵称

    private String username;

    @TableField("Password")
    private String password;

    @TableField("Total_Favorite")
    private Integer totalFavorite;

    @TableField("Favorite_Count")
    private Integer favoriteCount;

    //关注数目
    @TableField("Follow_Count")
    private Integer followCount;

    //粉丝数目
    @TableField("Follower_Count")
    private Integer followerCount;

    @TableField("create_At")
    private Date createAt;

    @TableField(exist = false)
    private boolean isFollow;

    public Users(){}
    public Users(String username,String password){
        this.setName("用户" + username);
        this.setUsername(username);
        this.setPassword(password);
        this.setTotalFavorite(0);
        this.setFavoriteCount(0);
        this.setFollowCount(0);
        this.setFollowerCount(0);
        this.setFollow(false);

        Date date = new Date();
        this.setCreateAt(date);
    }

}
