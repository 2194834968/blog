package com.lzp.MiniBlog.DAO.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.DAO.RelationDao;
import com.lzp.MiniBlog.DAO.mapper.RelationMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.service.RelationService;
import com.lzp.MiniBlog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class RelationDaoImpl implements RelationDao {

    @Autowired
    RelationMapper relationMapper;
    @Autowired
    UsersMapper usersMapper;

    @Override
    public void updateUser_FollowerCount_ByTargetUserId(Integer userId, Integer actionType){
        UpdateWrapper<Users> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("Id",userId);
        userWrapper.last("FOR UPDATE");//上锁
        Users userTemp = usersMapper.selectOne(userWrapper);

        if(actionType == 1){
            userTemp.setFollowerCount(userTemp.getFollowerCount() + 1);
        }else if(actionType == 2){
            userTemp.setFollowerCount(userTemp.getFollowerCount() - 1);
        }

        int result = usersMapper.updateById(userTemp);
    }

    @Override
    public void updateUser_FollowCount_ByTargetUserId(Integer userId, Integer actionType){
        UpdateWrapper<Users> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("Id",userId);
        userWrapper.last("FOR UPDATE");//上锁
        Users userTemp = usersMapper.selectOne(userWrapper);

        if(actionType == 1){
            userTemp.setFollowCount(userTemp.getFollowCount() + 1);
        }else if(actionType == 2){
            userTemp.setFollowCount(userTemp.getFollowCount() - 1);
        }

        int result = usersMapper.updateById(userTemp);
    }

    @Override
    public Relation queryRelation(Relation relation){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("followee_id",relation.getFolloweeId());
        relationQueryWrapper.eq("follower_id",relation.getFollowerId());
        return relationMapper.selectOne(relationQueryWrapper);
    }

    @Override
    public void insertRelation(Relation relation){
        int result = relationMapper.insert(relation);
    }

    @Override
    public void deleteRelation(Relation relation){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("followee_id",relation.getFolloweeId());
        relationQueryWrapper.eq("follower_id",relation.getFollowerId());
        int result = relationMapper.delete(relationQueryWrapper);
    }

    @Override
    public List<Relation> queryFollowerListByUserId(Integer targetUserId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("followee_id",targetUserId);
        return relationMapper.selectList(relationQueryWrapper);
    }

    @Override
    public List<Relation> queryFollowListByUserId(Integer targetUserId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("follower_id",targetUserId);
        return relationMapper.selectList(relationQueryWrapper);
    }
}
