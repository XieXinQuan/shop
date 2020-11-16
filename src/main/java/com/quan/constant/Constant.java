package com.quan.constant;

import io.netty.util.internal.StringUtil;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/2
 */
public class Constant {
    public static Integer pageInitIndex = 1;
    public static Integer pageInitCount = 20;

    public static String redisLoginCodePre = "login-code-";

    public static long jwtExpire = 3600000L;
    public static String jwtHeader = "token";

    public static String emptyString = StringUtil.EMPTY_STRING;
}
