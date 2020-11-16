package com.quan.entity;

import lombok.Data;

import java.util.Date;
/**
 * @author Administrator
 */
@Data
public class ShippingAddress {
    private Long id;

    private Long userId;

    private String recipientName;

    private String address;

    private Byte sex;

    private String iphone;

    private Byte status;

    private Byte isDefault;

    private String createUser;

    private String updateUser;

    private Date createTime;

    private Date updateTime;

}