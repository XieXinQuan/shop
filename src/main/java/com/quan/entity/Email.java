package com.quan.entity;

import com.quan.Enum.ResultEnum;
import com.quan.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@Slf4j
@Component
public class Email {

    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender mailSender;

    public void sendEmail(String loginEmail, String title, String content){
        log.info("Start Call 163 Send To {}, title: {}, content: {}", loginEmail, title, content);
        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(loginEmail);
            helper.setSubject(title);
            helper.setText(content);
            mailSender.send(message);
            log.info("Send To {} Success.", loginEmail);
        }catch (MessagingException e){
            log.error("发送邮件失败.", e);
            throw new GlobalException(ResultEnum.CustomException.getKey(), "验证码发送失败.");
        }
    }
}
