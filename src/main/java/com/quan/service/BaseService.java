package com.quan.service;

import com.quan.Enum.ResultEnum;
import com.quan.constant.Constant;
import com.quan.entity.User;
import com.quan.exception.GlobalException;
import com.quan.repository.UserRepository;
import com.quan.util.JwtUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
public class BaseService {
    @Resource
    protected UserRepository userRepository;

    HttpServletRequest request;

    public Long getCurrentUserId(){

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        this.request = request;
        if (!checkIsLogin()) {
            return 0L;
        }

        String token = request.getHeader(Constant.jwtHeader);
        String subject = JwtUtil.getSubjectFromToken(token);

        return Long.parseLong(subject);
    }
    private boolean checkIsLogin(){
        if (request == null || request.getHeader(Constant.jwtHeader) == null) {
            return false;
        }
        String token = request.getHeader(Constant.jwtHeader);
        return !JwtUtil.isTokenExpired(token);
    }
    public User getCurrentUser(){
        Long currentUserId = getCurrentUserId();

        if (currentUserId == 0L) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), ResultEnum.LoginOverDue.getValue());
        }
        User user = userRepository.findById(currentUserId).get();

        return user;
    }
}