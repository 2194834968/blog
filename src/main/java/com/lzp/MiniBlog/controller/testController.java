package com.lzp.MiniBlog.controller;

import com.lzp.MiniBlog.DAO.model.Users;
import com.lzp.MiniBlog.common.result.Result;
import com.lzp.MiniBlog.common.result.ResultCodeEnum;
import com.lzp.MiniBlog.common.token.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/test")
    public Result test(){
        return Result.ok("这个接口就是测试一下");
    }
}
