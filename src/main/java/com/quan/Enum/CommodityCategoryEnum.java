package com.quan.Enum;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/3
 */
public enum  CommodityCategoryEnum {
    /**
     * 商品类型
     */
    FOOD(1, "食品"),
    FRESH(2, "生鲜"),
    SKIN_CARE_PRODUCTS(3, "护肤"),
    CLEAN(4, "清洁"),
    CLOTHES(5, "服装"),

    TRAVEL(6, "旅行"),
    ELECTRONIC(7, "电子"),
    MOTION(8, "运动"),
    KITCHENWARE(9, "厨具");

    Byte key;
    String value;


    CommodityCategoryEnum(int key, String value) {
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
