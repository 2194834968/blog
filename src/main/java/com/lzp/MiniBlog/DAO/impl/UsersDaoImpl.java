package com.lzp.MiniBlog.DAO.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.DAO.UsersDao;
import com.lzp.MiniBlog.DAO.mapper.RelationMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.service.UsersService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@Service
public class UsersDaoImpl implements UsersDao {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RelationMapper relationMapper;

    @Override
    public boolean QueryUserIsFollow(Integer targetUserId, Integer userId){
        QueryWrapper<Relation> relationWrapper = new QueryWrapper<>();
        relationWrapper.eq("followee_id",targetUserId);
        relationWrapper.eq("follower_id",userId);
        Relation relationTemp = relationMapper.selectOne(relationWrapper);
        if(relationTemp != null){
            return true;
        }
        return false;
    }

    @Override
    public Users QueryUserById(Integer userId){
        Users userTemp = usersMapper.selectById(userId);
        return userTemp;
    }

    @Override
    public Users QueryUserByUsername(String username){
        QueryWrapper<Users> newUserWrapper = new QueryWrapper<>();
        newUserWrapper.eq("username",username);
        Users userTemp = usersMapper.selectOne(newUserWrapper);
        return userTemp;
    }

    @Override
    public void InsertUser(Users user){
        int result = usersMapper.insert(user);
    }
}
