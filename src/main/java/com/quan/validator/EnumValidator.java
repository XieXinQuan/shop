package com.quan.validator;

import com.quan.annotation.EnumValue;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/1/31
 *
 * Object 类型仅支持Integer and Byte
 */
public class EnumValidator implements ConstraintValidator<EnumValue, Object> {
    private Class<?> aClass;
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.aClass = constraintAnnotation.value();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        if(!aClass.isEnum()){
            return false;
        }

//        value = Optional.ofNullable(value).orElse(0);

        //获取所有枚举值
        Object[] enumConstants = aClass.getEnumConstants();

        // 获取getKey 方法
        Method getKey = aClass.getMethod("getKey");

        for (Object obj : enumConstants){

            //取得枚举值
            Object invoke = getKey.invoke(obj);

            if (invoke instanceof Integer
                || invoke instanceof Byte){
                if(invoke == value){
                    return true;
                }
            }

        }


        return false;
    }
}
