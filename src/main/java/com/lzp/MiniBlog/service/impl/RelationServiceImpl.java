package com.lzp.MiniBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lzp.MiniBlog.DAO.RelationDao;
import com.lzp.MiniBlog.DAO.mapper.RelationMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.service.RelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements RelationService {

    /*
    @Autowired
    RelationMapper relationMapper;

    @Autowired
    UsersMapper usersMapper;
    */

    @Autowired
    UsersService usersService;

    @Autowired
    RelationDao relationDao;

    @Override
    public List<Users> followList(Integer targetUserId, Integer userId){
        List<Relation> relationsList = relationDao.queryFollowListByUserId(targetUserId);
        List<Users> followList = new ArrayList<Users>();

        for(Relation relationTemp : relationsList){
            followList.add(usersService.userInfo(relationTemp.getFolloweeId(),targetUserId));
        }
        return followList;
    }

    @Override
    public List<Users> followerList(Integer targetUserId, Integer userId){
        List<Relation> relationsList = relationDao.queryFollowerListByUserId(targetUserId);
        List<Users> followList = new ArrayList<Users>();

        for(Relation relationTemp : relationsList){
            followList.add(usersService.userInfo(relationTemp.getFollowerId(),targetUserId));
        }
        return followList;
    }

    @Override
    @Transactional
    //@Transactional用于开启事务
    public boolean followAction(Integer targetUserId, Integer userId, Integer actionType){
        //确认targetUserId是否存在
        //确认是不是关注自己
        Users targetUser = usersService.userInfo(targetUserId,userId);
        if(targetUser == null || targetUserId.equals(userId)){
            return false;
        }

        //查询已有的记录确认关注记录是否存在
        Relation relation = new Relation(targetUserId,userId);
        Relation relationExist = relationDao.queryRelation(relation);
        //确定类别
        //更新relation表
        if(actionType == 1 && relationExist == null){
            relationDao.insertRelation(relation);
        }else if(actionType == 2 && relationExist != null){
            relationDao.deleteRelation(relation);
        }else{
            return false;
        }
        //更新user表targetUserId的Follower_Count
        relationDao.updateUser_FollowerCount_ByTargetUserId(targetUserId, actionType);
        //更新user表UserId的Follow_Count
        relationDao.updateUser_FollowCount_ByTargetUserId(userId, actionType);
        return true;
    }

    /*
    private void updateUser_FollowerCount_ByTargetUserId(Integer userId, Integer actionType){
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

    private void updateUser_FollowCount_ByTargetUserId(Integer userId, Integer actionType){
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

    private Relation queryRelation(Relation relation){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("followee_id",relation.getFolloweeId());
        relationQueryWrapper.eq("follower_id",relation.getFollowerId());
        return relationMapper.selectOne(relationQueryWrapper);
    }

    private void insertRelation(Relation relation){
        int result = relationMapper.insert(relation);
    }

    private void deleteRelation(Relation relation){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("followee_id",relation.getFolloweeId());
        relationQueryWrapper.eq("follower_id",relation.getFollowerId());
        int result = relationMapper.delete(relationQueryWrapper);
    }

    private List<Relation> queryFollowerListByUserId(Integer targetUserId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("followee_id",targetUserId);
        return relationMapper.selectList(relationQueryWrapper);
    }

    private List<Relation> queryFollowListByUserId(Integer targetUserId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("follower_id",targetUserId);
        return relationMapper.selectList(relationQueryWrapper);
    }
    */
}
