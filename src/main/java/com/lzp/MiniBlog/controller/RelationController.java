package com.lzp.MiniBlog.controller;


import com.lzp.MiniBlog.DAO.model.Relation;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.RelationService;
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
@RequestMapping("/relation")
public class RelationController {

    @Autowired
    RelationService relationService;

    @GetMapping("/follow/list")
    public Result followList(@RequestParam(value = "user_id", required = false) String targetUserIdStr ,
                             @RequestParam(value = "token", required = false) String token){
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

        return Result.ok(relationService.followList(targetUserId,userId));
    }

    @GetMapping("/follower/list")
    public Result followerList(@RequestParam(value = "user_id", required = false) String targetUserIdStr ,
                               @RequestParam(value = "token", required = false) String token){
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

        return Result.ok(relationService.followerList(targetUserId,userId));
    }

    @PostMapping("/action")
    public Result followerList(@RequestParam(value = "to_user_id", required = false) String targetUserIdStr ,
                               @RequestParam(value = "token", required = false) String token ,
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

        if(relationService.followAction(targetUserId, userId, actionType)){
            return Result.ok();
        }else{
            return Result.fail(ResultCodeEnum.OPERATE_ERROR);
        }
    }

}

