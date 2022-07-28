package com.lzp.MiniBlog.controller;


import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.FavoriteService;
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
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/list")
    public Result favoriteList(@RequestParam(value = "token") String token,
                               @RequestParam(value = "user_id") String targetUserIdStr){
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

        Integer targetUserId = null;
        //取出目标用户id
        if(!targetUserIdStr.isEmpty()){
            try {
                targetUserId = Integer.valueOf(targetUserIdStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
        }

        return Result.ok(favoriteService.favoriteList(targetUserId,userId));
    }

    @PostMapping("/action")
    public Result favoriteAction(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "video_id") String videoIdStr,
                                 @RequestParam(value = "action_type") String actionTypeStr){
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
                return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
        }

        Integer actionType = null;
        //取出目标操作类别
        if(!actionTypeStr.isEmpty()){
            try {
                actionType = Integer.valueOf(actionTypeStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.ACTION_TYPE_ERROR);
            }

            if(actionType < 1 || actionType > 2){
                return Result.fail(ResultCodeEnum.ACTION_TYPE_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.ACTION_TYPE_ERROR);
        }

        if(favoriteService.favoriteAction(userId, videoId, actionType)){
            return Result.ok();
        }else{
            return Result.fail(ResultCodeEnum.FAVORITE_ERROR);
        }
    }
}

