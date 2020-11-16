package com.quan.Enum;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/3
 */
public enum  CommonByteEnum {
    /**
     * 公共字节枚举
     */
    No(0, "非正常"),
    Normal(1, "正常"),
    Unavailable(2, "不可用");

    Byte key;
    String value;

    CommonByteEnum(int key, String value) {
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
