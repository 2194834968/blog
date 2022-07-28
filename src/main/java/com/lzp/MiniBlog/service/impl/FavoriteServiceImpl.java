package com.lzp.MiniBlog.service.impl;

import com.lzp.MiniBlog.DAO.mapper.FavoriteMapper;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.service.FavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

}
