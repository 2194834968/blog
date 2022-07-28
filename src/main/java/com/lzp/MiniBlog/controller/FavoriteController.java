package com.lzp.MiniBlog.controller;


import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            targetUserId = Integer.valueOf(targetUserIdStr);
        }else{
            return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
        }

        return Result.ok(favoriteService.favoriteList(targetUserId,userId));
    }
}

