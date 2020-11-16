package com.quan.Enum;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/3
 */
public enum  CommonEnum implements BaseEnum{
    /**
     * 公共枚举
     */
    Normal(1, "正常"),
    Disable(0, "禁用");


    private Integer key;
    private String value;

    CommonEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取枚举值
     * @return
     */
    @Override
    public Integer getKey() {
        return key;
    }

    /**
     * 获取枚举解释
     * @return
     */
    @Override
    public String getValue() {
        return value;
    }
}
