package com.quan.util;

/**
 *
 * @author Administrator
 */
public class StringUtils {

    /**
    判断字符串是否为空
     */
    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }
    /**
     * 处理校验异常时的message
     * @param str
     * @return
     */
    public static String validExceptionStr(String str){

        String comma = ",";
        String colon = ":";
        boolean isEmpty = (isEmpty(str) || !str.contains(comma) && !str.contains(colon));
        if (isEmpty){
            return "";
        }

        String[] split = str.split(",");
        StringBuffer result = new StringBuffer("");
        for(int i = 0; i < split.length; i++){
            result.append(split[i].contains(":") ? split[i].split(":")[1] : split[i]);
            if(i != split.length - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

}
