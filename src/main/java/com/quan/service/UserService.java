package com.quan.service;

import cn.hutool.core.util.RandomUtil;
import com.quan.Enum.CommonByteEnum;
import com.quan.Enum.ResultEnum;
import com.quan.constant.Constant;
import com.quan.entity.Email;
import com.quan.entity.User;
import com.quan.exception.GlobalException;
import com.quan.repository.UserRepository;
import com.quan.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@Service
public class UserService extends BaseService{

    @Resource
    private UserRepository userRepository;
    @Resource
    private Email email;
    @Resource
    private RedisTemplate redisTemplate;

    public Long registerUser(String name, String email, String password, String code) {
        //先校验Redis Code
        Object redisCode = redisTemplate.opsForValue().get(Constant.redisLoginCodePre + email);
        if (!code.equals(redisCode)) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "验证码错误.");
        }

        //检验用户名
        User isExists = userRepository.findByName(name);
        if (isExists != null) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "用户名已存在.");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setBalance(new BigDecimal("0.00"));
        user.setSex(CommonByteEnum.Normal.getKey());
        user.setStatus(CommonByteEnum.Normal.getKey());
        //设置商家和会员为不是的状态
        user.setIsMerchant(CommonByteEnum.No.getKey());
        user.setIsVip(CommonByteEnum.No.getKey());
        userRepository.save(user);

        //删除redis
        redisTemplate.delete(Constant.redisLoginCodePre + email);
        return user.getId();
    }

    public Long login(String name, String password) {
        User user = userRepository.findByName(name);

        String dbPassword = Optional.ofNullable(user).map(User::getPassword).orElse("");

        if (!com.alibaba.druid.util.StringUtils.equals(password, dbPassword)) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "用户名或密码错误.");
        }

        return user.getId();
    }

    public int code(String loginEmail) {
        if (StringUtils.isEmpty(loginEmail)) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "手机或邮箱为空");
        }
        int code = RandomUtil.randomInt(1000, 9999);

        String redisKey = Constant.redisLoginCodePre + loginEmail;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(code), 5L, TimeUnit.MINUTES);
        email.sendEmail(loginEmail, "验证码", "您的验证码为: " + code);
        return code;
    }

    public User userInfo(){
        return getCurrentUser();
    }

    public void registerMerchant(){
        Long currentUserId = getCurrentUserId();
        User user = userRepository.findById(currentUserId).orElse(null);
        if (user == null) {
            throw new GlobalException(ResultEnum.LoginOverDue.getKey());
        }
        if (CommonByteEnum.Normal.getKey().equals(user.getIsMerchant())) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "您已经是商家了");
        }
        user.setIsMerchant(CommonByteEnum.Normal.getKey());
        userRepository.save(user);
    }
}
