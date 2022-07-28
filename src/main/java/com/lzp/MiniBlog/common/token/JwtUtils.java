package com.lzp.MiniBlog.common.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {


    /**
     * @description 创建token
     * @author lee
     * @date 20:49 2022/3/31
     **/
    public static String createToken(int userId) {
        HashMap<String,Object> map = new HashMap<>();
        //日历类，用于获取当前时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,30);//设定过期时间30分钟

        String token = JWT.create()
                .withHeader(map) //可以不设定，就是使用默认的
                .withClaim("userId",userId)//payload  //自定义用户名
                .withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256("OuNeiDeShouHaoHan"));//签名
        return token;
    }

    /**
     * @description 解析token
     * @author lee
     * @date 23:36 2022/3/31
     **/

    public static int verifyTokenBackUserId(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("OuNeiDeShouHaoHan")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        //日历类，用于获取当前时间
        Date date = new Date();

        if(decodedJWT.getExpiresAt().after(date)){
            return decodedJWT.getClaim("userId").asInt();//获取负载里面对应的内容
        }else{
            return 0;
        }
    }

    /**
     * @description 判断token是否过期
     * @author lee
     * @date 23:36 2022/3/31
     **/

    public static boolean verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("OuNeiDeShouHaoHan")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        //日历类，用于获取当前时间
        Date date = new Date();

        if(decodedJWT.getExpiresAt().after(date)){
            return true;
        }else{
            return false;
        }
    }

}

