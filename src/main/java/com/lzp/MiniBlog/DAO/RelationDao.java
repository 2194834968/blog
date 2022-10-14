package com.lzp.MiniBlog.DAO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface RelationDao{
    public void updateUser_FollowerCount_ByTargetUserId(Integer userId, Integer actionType);

    public void updateUser_FollowCount_ByTargetUserId(Integer userId, Integer actionType);

    public Relation queryRelation(Relation relation);

    public void insertRelation(Relation relation);


    public void deleteRelation(Relation relation);

    public List<Relation> queryFollowerListByUserId(Integer targetUserId);

    public List<Relation> queryFollowListByUserId(Integer targetUserId);
}
