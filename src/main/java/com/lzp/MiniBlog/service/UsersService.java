package com.lzp.MiniBlog.service;

import com.lzp.MiniBlog.DAO.model.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.relational.core.sql.In;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface UsersService extends IService<Users> {
    public Integer register(Users newUser);

    public Integer login(Users newUser);

    public Users userInfo(Integer targetUserId, Integer userId);
}
