package com.lzp.MiniBlog.service.impl;

import com.lzp.MiniBlog.DAO.mapper.CommentMapper;
import com.lzp.MiniBlog.DAO.model.Comment;
import com.lzp.MiniBlog.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
