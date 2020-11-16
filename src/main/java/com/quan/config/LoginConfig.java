package com.quan.config;

import com.quan.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        //拦截所有
        registration.addPathPatterns("/**");
        //放行
        registration.excludePathPatterns(
                "/user/login",
                "/user/code",
                "/user/register",
                "/commodity/**",

                "/files/**",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css",
                "/**/*.woff",
                "/**/*.ttf"
        );
    }
}