package com.quan.util;

import com.quan.Enum.BaseEnum;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/1/30
 */
public class EnumUtil {
    public static boolean isContain(BaseEnum[] baseEnums, Integer value){
        for(BaseEnum baseEnum : baseEnums){
            if(baseEnum.getKey().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
