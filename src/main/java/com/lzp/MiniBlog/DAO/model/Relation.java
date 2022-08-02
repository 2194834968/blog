package com.lzp.MiniBlog.DAO.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    //被关注者
    private Integer followeeId;

    //关注者
    private Integer followerId;


    public Relation(Integer targetUserId, Integer userId) {
        this.setFolloweeId(targetUserId);
        this.setFollowerId(userId);
    }
}
