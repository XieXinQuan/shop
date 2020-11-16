package com.quan.config;

import com.quan.constant.Constant;
import com.quan.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/22
 */
@Configuration
public class CreateUserAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader(Constant.jwtHeader);
        String subject = JwtUtil.getSubjectFromToken(token);
        if (subject != null) {
            return Optional.of(Long.parseLong(subject));
        }
        return Optional.empty();
    }
}
