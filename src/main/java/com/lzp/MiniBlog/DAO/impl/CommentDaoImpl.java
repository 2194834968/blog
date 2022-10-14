package com.lzp.MiniBlog.DAO.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.DAO.CommentDao;
import com.lzp.MiniBlog.DAO.mapper.CommentMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Comment;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.CommentRespond;
import com.lzp.MiniBlog.service.CommentService;
import com.lzp.MiniBlog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    VideosMapper videosMapper;

    @Autowired
    UsersMapper usersMapper;

    @Override
    public Videos queryVideoIdByVideoId(Integer videoId){
        return videosMapper.selectById(videoId);
    }

    @Override
    public List<Comment> queryCommentListByVideoId(Integer videoId){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("video_id",videoId);
        return commentMapper.selectList(commentQueryWrapper);
    }

    @Override
    //评论增加
    public void updateVideo_CommentCount_ByVideoId_1(Integer videoId){
        UpdateWrapper<Videos> videosWrapper = new UpdateWrapper<>();
        videosWrapper.eq("Id",videoId);
        videosWrapper.last("FOR UPDATE");//上锁
        Videos videoTemp = videosMapper.selectOne(videosWrapper);

        videoTemp.setCommentCount(videoTemp.getCommentCount() + 1);
        int result = videosMapper.updateById(videoTemp);
    }

    //评论删除
    @Override
    public void updateVideo_CommentCount_ByVideoId_2(Integer videoId){
        UpdateWrapper<Videos> videosWrapper = new UpdateWrapper<>();
        videosWrapper.eq("Id",videoId);
        videosWrapper.last("FOR UPDATE");//上锁
        Videos videoTemp = videosMapper.selectOne(videosWrapper);

        videoTemp.setCommentCount(videoTemp.getCommentCount() - 1);
        int result = videosMapper.updateById(videoTemp);
    }

    @Override
    public Comment queryCommentBy_UserId_VideoId_CommentId(Integer userId, Integer videoId, Integer commentId){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("Id",commentId);
        commentQueryWrapper.eq("video_id",videoId);
        commentQueryWrapper.eq("user_id",userId);

        return commentMapper.selectOne(commentQueryWrapper);
    }

    @Override
    public void insertComment(Comment comment){
        int result = commentMapper.insert(comment);
    }

    @Override
    public void deleteComment(Comment comment){
        int result = commentMapper.deleteById(comment.getId());
    }
}
