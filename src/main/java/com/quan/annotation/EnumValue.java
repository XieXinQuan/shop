package com.quan.annotation;

import com.quan.validator.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 检验枚举值
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/1/31
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {
    Class<?> value();

    String message() default "参数值不匹配";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
