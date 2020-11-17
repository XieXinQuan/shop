package com.quan.interceptor;

import com.google.common.collect.Lists;
import com.quan.Enum.ResultEnum;
import com.quan.annotation.NeedLogin;
import com.quan.constant.Constant;
import com.quan.exception.GlobalException;
import com.quan.util.JwtUtil;
import com.quan.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
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

        //判断是否有@NeedLogin注解

        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NeedLogin methodAnnotation = handlerMethod.getMethodAnnotation(NeedLogin.class);
        Class declaringClass = handlerMethod.getMethod().getDeclaringClass();
        Annotation annotation = declaringClass.getAnnotation(NeedLogin.class);
        if (methodAnnotation == null && annotation == null){
            return true;
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
                throw new GlobalException(ResultEnum.GoToLogin.getKey());
            }
        }catch (Exception e){
            throw new GlobalException(ResultEnum.GoToLogin.getKey());
        }

        return true;
    }
}
