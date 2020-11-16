package com.quan.annotation;

import com.quan.validator.SimpleValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = SimpleValueValidator.class)
public @interface SimpleValue {
    int[] value();

    String message() default "参数值不匹配";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
