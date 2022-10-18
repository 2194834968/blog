package com.lzp.MiniBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lzp.MiniBlog.DAO.CommentDao;
import com.lzp.MiniBlog.DAO.UsersDao;
import com.lzp.MiniBlog.DAO.mapper.CommentMapper;
import com.lzp.MiniBlog.DAO.mapper.FavoriteMapper;
import com.lzp.MiniBlog.DAO.mapper.UsersMapper;
import com.lzp.MiniBlog.DAO.mapper.VideosMapper;
import com.lzp.MiniBlog.DAO.model.Comment;
import com.lzp.MiniBlog.DAO.model.Favorite;
import com.lzp.MiniBlog.DAO.model.Videos;
import com.lzp.MiniBlog.common.respond.CommentRespond;
import com.lzp.MiniBlog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.MiniBlog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UsersService usersService;

    @Autowired
    CommentDao commentDao;

    /*
    @Autowired
    VideosMapper videosMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UsersMapper usersMapper;
    */

    @Override
    @Transactional
    //@Transactional用于开启事务
    public CommentRespond commentAction(Integer userId, Integer videoId, String commentText){
        //确认视频存在
        Videos videosTemp = commentDao.queryVideoIdByVideoId(videoId);
        if(videosTemp == null){
            return null;
        }
        //添加评论记录
        Comment newComment = new Comment();
        Date date = new Date();

        newComment.setUserId(userId);
        newComment.setVideoId(videoId);
        newComment.setCommentText(commentText);
        newComment.setCreateAt(date);

        commentDao.insertComment(newComment);
        //修改video表中评论总数
        commentDao.updateVideo_CommentCount_ByVideoId_1(videoId);

        CommentRespond commentRespond = new CommentRespond();

        commentRespond.setId(newComment.getId());
        commentRespond.setVideoId(newComment.getVideoId());
        commentRespond.setCommentText(newComment.getCommentText());
        commentRespond.setCreateAt(newComment.getCreateAt());

        commentRespond.setAuthor(usersService.userInfo(newComment.getUserId(),userId));

        return commentRespond;
    }

    @Override
    @Transactional
    //@Transactional用于开启事务
    public boolean deleteCommentAction(Integer userId, Integer videoId, Integer commentId){
        //确认评论信息（是否是本人所发？）
        Comment targetComment = commentDao.queryCommentBy_UserId_VideoId_CommentId(userId, videoId, commentId);
        if(targetComment == null){
            return false;
        }
        //删除评论记录
        commentDao.deleteComment(targetComment);
        //修改video表中评论总数
        commentDao.updateVideo_CommentCount_ByVideoId_2(targetComment.getVideoId());
        return true;
    }

    @Override
    public List<CommentRespond> commentList(Integer userId, Integer videoId){
        List<CommentRespond> commentRespondsList = new ArrayList<CommentRespond>();

        //查询评论列表
        List<Comment> commentTempList = commentDao.queryCommentListByVideoId(videoId);

        //对查询到的评论列表做数据处理
        for(Comment commentTemp : commentTempList){
            CommentRespond commentRespond = new CommentRespond();

            commentRespond.setId(commentTemp.getId());
            commentRespond.setVideoId(commentTemp.getVideoId());
            commentRespond.setCommentText(commentTemp.getCommentText());
            commentRespond.setCreateAt(commentTemp.getCreateAt());

            commentRespond.setAuthor(usersService.userInfo(commentTemp.getUserId(),userId));
            commentRespondsList.add(commentRespond);
        }
        return commentRespondsList;
    }

    /*

    private Videos queryVideoIdByVideoId(Integer videoId){
        return videosMapper.selectById(videoId);
    }

    private List<Comment> queryCommentListByVideoId(Integer videoId){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("video_id",videoId);
        return commentMapper.selectList(commentQueryWrapper);
    }

    //评论增加
    private void updateVideo_CommentCount_ByVideoId_1(Integer videoId){
        UpdateWrapper<Videos> videosWrapper = new UpdateWrapper<>();
        videosWrapper.eq("Id",videoId);
        videosWrapper.last("FOR UPDATE");//上锁
        Videos videoTemp = videosMapper.selectOne(videosWrapper);

        videoTemp.setCommentCount(videoTemp.getCommentCount() + 1);
        int result = videosMapper.updateById(videoTemp);
    }

    //评论删除
    private void updateVideo_CommentCount_ByVideoId_2(Integer videoId){
        UpdateWrapper<Videos> videosWrapper = new UpdateWrapper<>();
        videosWrapper.eq("Id",videoId);
        videosWrapper.last("FOR UPDATE");//上锁
        Videos videoTemp = videosMapper.selectOne(videosWrapper);

        videoTemp.setCommentCount(videoTemp.getCommentCount() - 1);
        int result = videosMapper.updateById(videoTemp);
    }

    private Comment queryCommentBy_UserId_VideoId_CommentId(Integer userId, Integer videoId, Integer commentId){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("Id",commentId);
        commentQueryWrapper.eq("video_id",videoId);
        commentQueryWrapper.eq("user_id",userId);

        return commentMapper.selectOne(commentQueryWrapper);
    }
    private void insertComment(Comment comment){
        int result = commentMapper.insert(comment);
    }

    private void deleteComment(Comment comment){
        int result = commentMapper.deleteById(comment.getId());
    }
    */
}
