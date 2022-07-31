package com.lzp.MiniBlog.service;

import com.lzp.MiniBlog.DAO.model.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface CommentService extends IService<Comment> {

    //评论操作
    public CommentRespond commentAction(Integer userId, Integer videoId, String commentText);

    //取消评论操作
    public boolean deleteCommentAction(Integer userId, Integer videoId, Integer commentId);

    public List<CommentRespond> commentList(Integer userId, Integer videoId);

}
