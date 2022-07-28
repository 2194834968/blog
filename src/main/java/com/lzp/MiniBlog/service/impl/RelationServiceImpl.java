package com.lzp.MiniBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzp.MiniBlog.DAO.mapper.RelationMapper;
import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.service.RelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements RelationService {

}
