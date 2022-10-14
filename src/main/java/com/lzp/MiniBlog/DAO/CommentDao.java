package com.lzp.MiniBlog.DAO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lzp.MiniBlog.DAO.model.Comment;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.CommentRespond;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
public interface CommentDao{

    public Videos queryVideoIdByVideoId(Integer videoId);

    public List<Comment> queryCommentListByVideoId(Integer videoId);

    //评论增加
    public void updateVideo_CommentCount_ByVideoId_1(Integer videoId);

    //评论删除
    public void updateVideo_CommentCount_ByVideoId_2(Integer videoId);

    public Comment queryCommentBy_UserId_VideoId_CommentId(Integer userId, Integer videoId, Integer commentId);

    public void insertComment(Comment comment);

    public void deleteComment(Comment comment);

}
