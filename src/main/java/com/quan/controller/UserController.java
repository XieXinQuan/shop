package com.quan.controller;

import com.quan.service.UserService;
import com.quan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@RestController
@Slf4j
@Validated
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @PostMapping("/login")
    public Object login(@RequestParam("username") @Size(max = 5, min = 1, message = "用户名长度在1~5位") String username,
                        @RequestParam("password") @Size(max = 18, min = 6, message = "密码的长度为6~18位") String password){
        Long uId = userService.login(username, password);
        return JwtUtil.createToken(uId);
    }

    @PostMapping("/register")
    public Object register(@RequestParam("username") @Size(max = 5, min = 1, message = "用户名长度在1~5位") String username,
                           @RequestParam("password") @Size(max = 18, min = 6, message = "密码的长度为6~18位") String password,
                           @RequestParam("email") @Email(message = "邮箱地址不合法") String email,
                           @RequestParam("code") @Size(max = 4, min = 4, message = "验证码的长度为4位") String code){
        Long uId = userService.registerUser(username, email, password, code);
        return JwtUtil.createToken(uId);
    }


    @PostMapping("/code")
    public Object code(@RequestParam("email") @Email String email) {
        int code = userService.code(email);
        return "发送成功";
    }

    @PostMapping("/info")
    public Object info(){
        return userService.userInfo();
    }

    @GetMapping("/applyMerchant")
    public Object applyMerchant(){
        userService.registerMerchant();
        return "您成功成为商家";
    }
}
