package com.lzp.MiniBlog.controller;


import com.lzp.MiniBlog.common.respond.CommentRespond;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.CommentService;
import com.lzp.MiniBlog.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/action")
    public Result commentAction(@RequestParam(value = "token") String token,
                                @RequestParam(value = "video_id") String videoIdStr,
                                @RequestParam(value = "action_type") String actionTypeStr,
                                @RequestParam(value = "comment_text") String commentText,
                                @RequestParam(value = "comment_id") String commentIdStr){
        //取出用户id
        Integer userId = null;
        //校验token
        if(!token.isEmpty()){
            if(!JwtUtils.verifyToken(token)){
                return Result.fail(ResultCodeEnum.TOKEN_OUTTIME_OR_UN_EXIST);
            }
            userId = JwtUtils.verifyTokenBackUserId(token);
        }else{
            return Result.fail(ResultCodeEnum.NEED_TOKEN);
        }

        Integer videoId = null;
        //取出目标视频id
        if(!videoIdStr.isEmpty()){
            try {
                videoId = Integer.valueOf(videoIdStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.QUERY_VIDEO_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.QUERY_VIDEO_ERROR);
        }

        Integer actionType = null;
        //取出目标操作类别
        if(!actionTypeStr.isEmpty()){
            try {
                actionType = Integer.valueOf(actionTypeStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.ACTION_TYPE_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.ACTION_TYPE_ERROR);
        }

        //取出目标评论id
        Integer commentId = null;
        if(!commentIdStr.isEmpty()){
            try {
                commentId = Integer.valueOf(commentIdStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.QUERY_COMMENT_ERROR);
            }
        }

        if(actionType == 1 && !commentText.isEmpty()){
            //评论操作
            CommentRespond commentRespond = commentService.commentAction(userId, videoId, commentText);
            if(commentRespond != null){
                return Result.ok(commentRespond);
            }else{
                return Result.fail(ResultCodeEnum.QUERY_VIDEO_ERROR);
            }
        }else if(actionType == 2 && !commentIdStr.isEmpty()){
            //删除评论
            if(commentService.deleteCommentAction(userId,videoId,commentId)){
                return Result.ok();
            }else{
                return Result.fail(ResultCodeEnum.OPERATE_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.OPERATE_ERROR);
        }
    }

    @GetMapping("/list")
    public Result commentList(@RequestParam(value = "token") String token,
                              @RequestParam(value = "video_id") String targetVideoIdStr){
        //取出用户id
        Integer userId = null;
        //校验token
        if(!token.isEmpty()){
            if(!JwtUtils.verifyToken(token)){
                return Result.fail(ResultCodeEnum.TOKEN_OUTTIME_OR_UN_EXIST);
            }
            userId = JwtUtils.verifyTokenBackUserId(token);
        }else{
            return Result.fail(ResultCodeEnum.NEED_TOKEN);
        }

        Integer targetVideoId = null;
        //取出目标视频id
        if(!targetVideoIdStr.isEmpty()){
            try {
                targetVideoId = Integer.valueOf(targetVideoIdStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.QUERY_VIDEO_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.QUERY_VIDEO_ERROR);
        }


        return Result.ok(commentService.commentList(userId, targetVideoId));
    }

}

