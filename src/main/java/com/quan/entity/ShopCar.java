package com.quan.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/23
 */
@Table(name = "shop_car")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ShopCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long commodityId;

    private Integer num;

    private Byte status;

    @CreatedBy
    private Long createUser;

    @LastModifiedBy
    private Long updateUser;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
