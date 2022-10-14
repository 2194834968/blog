package com.lzp.MiniBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzp.MiniBlog.DAO.mapper.RelationMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RelationMapper relationMapper;

    @Override
    public Integer register(Users newUser){
        Users userTemp = QueryUserByUsername(newUser.getUsername());
        if(userTemp == null){
            userTemp = new Users(newUser.getUsername(), BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(4)) );
            InsertUser(userTemp);
            return QueryUserByUsername(newUser.getUsername()).getId();
        }else{
            return 0;
        }

    }

    @Override
    public Integer login(Users user){
        Users userTemp = QueryUserByUsername(user.getUsername());
        if(userTemp != null && BCrypt.checkpw(user.getPassword(), userTemp.getPassword()) ){
            return userTemp.getId();
        }else{
            return 0;
        }

    }

    @Override
    public Users userInfo(Integer targetUserId, Integer userId){
        Users userTemp = QueryUserById(targetUserId);
        if(userTemp == null){
            return null;
        }
        userTemp.setFollow(QueryUserIsFollow(targetUserId,userId));
        userTemp.setPassword("");
        return userTemp;
    }
    private boolean QueryUserIsFollow(Integer targetUserId, Integer userId){
        QueryWrapper<Relation> relationWrapper = new QueryWrapper<>();
        relationWrapper.eq("followee_id",targetUserId);
        relationWrapper.eq("follower_id",userId);
        Relation relationTemp = relationMapper.selectOne(relationWrapper);
        if(relationTemp != null){
            return true;
        }
        return false;
    }
    private Users QueryUserById(Integer userId){
        Users userTemp = usersMapper.selectById(userId);
        return userTemp;
    }

    private Users QueryUserByUsername(String username){
        QueryWrapper<Users> newUserWrapper = new QueryWrapper<>();
        newUserWrapper.eq("username",username);
        Users userTemp = usersMapper.selectOne(newUserWrapper);
        return userTemp;
    }

    private void InsertUser(Users user){
        int result = usersMapper.insert(user);
    }
}
