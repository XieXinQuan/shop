package com.quan.Enum;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/6/4
 */
public enum  EmailType {
    /**
     * 邮件类型
     */
    Normal(1, "下单"),
    Unavailable(2, "发货"),
    Code(3, "验证码");

    Byte key;
    String value;

    EmailType(int key, String value) {
        this.key = (byte)(key & 0xFF);
        this.value = value;
    }

    public Byte getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
