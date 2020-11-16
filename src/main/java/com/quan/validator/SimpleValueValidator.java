package com.quan.validator;

import com.quan.annotation.SimpleValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/24
 */
public class SimpleValueValidator implements ConstraintValidator<SimpleValue, Integer> {
    private int[] values;
    @Override
    public void initialize(SimpleValue constraintAnnotation) {
        this.values = constraintAnnotation.value();
    }
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || values.length == 0) return false;
        for (int val : values){
            if (val == value) return true;
        }
        return false;
    }
}
