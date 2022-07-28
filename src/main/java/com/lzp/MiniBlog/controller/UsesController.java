package com.lzp.MiniBlog.controller;

import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import com.lzp.MiniBlog.service.UsersService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsesController {

    @Autowired
    private UsersService usersService;

    //登陆和注册返回id和token的结构体
    @Data
    private class UserLRRespond{
        Integer userId;
        String token;
        public UserLRRespond(Integer userId,String token){
            this.setUserId(userId);
            this.setToken(token);
        }
    }

    @PostMapping("/user/register")
    public Result register(Users newUser){
        if(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty()){
            return Result.fail(ResultCodeEnum.NEED_USERNAME_OR_PASS);
        }

        Integer userId = usersService.register(newUser);
        if(userId != 0){
            UserLRRespond Respond = new UserLRRespond(userId, JwtUtils.createToken(userId));
            return Result.ok(Respond);
        }else{
            return Result.fail(ResultCodeEnum.USERNAME_EXIST);
        }
    }

    @PostMapping("/user/login")
    public Result login(Users user){
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            return Result.fail(ResultCodeEnum.NEED_USERNAME_OR_PASS);
        }

        Integer userId = usersService.login(user);
        if(userId != 0){
            UserLRRespond Respond = new UserLRRespond(userId, JwtUtils.createToken(userId));
            return Result.ok(Respond);
        }else{
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
    }

    @GetMapping("/user")
    public Result userInfo(@RequestParam("user_id") String userIdStr ,
                           @RequestParam("token") String token){
        Integer targetUserId = null;

        //取出目标用户id
        if(!userIdStr.isEmpty()){
            try {
                targetUserId = Integer.valueOf(userIdStr);
            } catch (NumberFormatException e) {
                return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
            }
        }else{
            return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
        }

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


        Users reUser = usersService.userInfo(targetUserId,userId);
        if(reUser != null){
            return Result.ok(reUser);
        }else{
            return Result.fail(ResultCodeEnum.QUERY_USER_ERROR);
        }
    }
}

