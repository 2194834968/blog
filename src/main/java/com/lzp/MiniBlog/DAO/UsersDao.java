package com.lzp.MiniBlog.DAO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface UsersDao {
    public boolean QueryUserIsFollow(Integer targetUserId, Integer userId);

    public Users QueryUserById(Integer userId);

    public Users QueryUserByUsername(String username);

    public void InsertUser(Users user);
}
