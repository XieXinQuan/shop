package com.quan.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/3
 */
public class AmountValidator implements ConstraintValidator<DecimalMax, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal o, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
