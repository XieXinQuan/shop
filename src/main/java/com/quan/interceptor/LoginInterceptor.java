package com.quan.interceptor;

import com.google.common.collect.Lists;
import com.quan.constant.Constant;
import com.quan.util.JwtUtil;
import com.quan.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/16
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws SignatureException {
        /** 地址过滤 */
        String uri = request.getRequestURI();
        List<String> ignoreUrl = Lists.newArrayList("/user/login", "/user/code", "/user/register", "/file/image", "/error", "/404");
        if (ignoreUrl.contains(uri)){
            return true ;
        }
        /** Token 验证 */
        String token = request.getHeader(Constant.jwtHeader);
        if(StringUtils.isEmpty(token)) {
            token = request.getParameter(Constant.jwtHeader);
        }

        if(StringUtils.isEmpty(token)) {
            return false;
        }

        Claims claims = null;
        try{
            claims = JwtUtil.getTokenClaim(token);
            if(claims == null || JwtUtil.isTokenExpired(token)){
                throw new SignatureException("请重新登录.");
            }
        }catch (Exception e){
            throw new SignatureException("请重新登录.");
        }

        return true;
    }
}
