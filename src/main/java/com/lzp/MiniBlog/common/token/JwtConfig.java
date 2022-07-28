package com.lzp.MiniBlog.common.token;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class JwtConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                //添加拦截路径
                .addPathPatterns("/**")
                //添加放行路径
                .excludePathPatterns("/douyin/feed","/douyin/user/register/","/douyin/user/login/","/douyin/user");
    }
}
