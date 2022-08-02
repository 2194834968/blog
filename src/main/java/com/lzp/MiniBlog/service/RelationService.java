package com.lzp.MiniBlog.service;

import com.lzp.MiniBlog.DAO.model.Relation;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface RelationService extends IService<Relation> {
    public List<Users> followList(Integer targetUserId, Integer userId);
    public List<Users> followerList(Integer targetUserId, Integer userId);
    public boolean followAction(Integer targetUserId, Integer userId, Integer actionType);
}
