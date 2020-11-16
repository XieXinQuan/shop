package com.quan.advice;

import com.quan.Enum.ResultEnum;
import com.quan.exception.GlobalException;
import com.quan.util.ResultUtil;
import com.quan.util.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/1/11
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @Resource
    HttpServletRequest request;

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e){
        String name = e.getClass().getName();
        log.error("There is a serious system exception, please deal with it...");
        log.error("Exception Start...");
        log.error("Exception Type : {}", e.getClass().getName());
        log.error("Exception Case: {}" , e.getMessage());
        log.error("Exception", e);
        log.error("Exception End...");
        return ResultUtil.CustomException("出现错误,请重试.");
    }

    @ExceptionHandler(value = GlobalException.class)
    public String exceptionHandler(GlobalException e){
        Integer status = e.getStatus() == null ? ResultEnum.CustomException.getKey() : e.getStatus();
        String msg = e.getMsg();
        if(msg == null){
            for(ResultEnum re : ResultEnum.values()){
                if(re.getKey().equals(status)) {
                    msg = re.getValue();
                    break;
                }
            }
        }
        log.info("Custom Exception Start");
        log.info(ResultUtil.CustomException(status, msg));
        log.info("Custom Exception End");
        return ResultUtil.CustomException(status, msg);
    }

    @ExceptionHandler(value = ValidationException.class)
    public String exceptionHandler(ValidationException e){
        log.info("Valid Exception Start...");
        String msg = StringUtils.validExceptionStr(e.getMessage());
        log.info(msg);
        log.info("Valid Exception End...");
        return ResultUtil.CustomException(msg);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public String exceptionHandler(ConstraintViolationException e){
        log.info("Valid Exception Start...");
        String msg = StringUtils.validExceptionStr(e.getMessage());
        log.info(msg);
        log.info("Valid Exception End...");
        return ResultUtil.CustomException(msg);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public String exceptionHandler(MissingServletRequestParameterException e){
        log.info("MissingServletRequestParameter Exception Start...");
        log.info("Please Check Parameter Is Not Null, Thanks. ");
        log.info("MissingServletRequestParameter Exception  End...");

        return ResultUtil.CustomException(ResultEnum.PasswordIsError.getKey(), "Please Check Parameter Is Not Null, Thanks.");
    }

    @ExceptionHandler(value = JpaSystemException.class)
    public String exceptionHandler(JpaSystemException e){
        log.info("JpaSystem Exception Start...");
        log.info("Exception Case : {}" , e.getMessage());
        log.info("JpaSystem Exception  End...");

        return ResultUtil.CustomException(ResultEnum.PasswordIsError.getKey(), "Dd Error...");
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public String exceptionHandler(ExpiredJwtException e){
        log.info("Login Info Expired");
        log.info("Exception Case : {}", e.getMessage());
        return ResultUtil.CustomException(ResultEnum.LoginOverDue.getKey(), ResultEnum.LoginOverDue.getValue());
    }

    @ExceptionHandler(SignatureException.class)
    public String exceptionHandler(SignatureException e){
        log.info("Login Info Expired");
        log.info("Exception Case : {}", e.getMessage());
        return ResultUtil.CustomException(ResultEnum.LoginOverDue.getKey(), ResultEnum.LoginOverDue.getValue());
    }

}
