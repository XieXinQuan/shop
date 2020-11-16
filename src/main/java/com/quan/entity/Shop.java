package com.quan.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Entity
@Table(name = "shop")
@EntityListeners(AuditingEntityListener.class)
public class Shop {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String shopName;

    private String createUser;

    private String updateUser;

    private Date createTime;

    private Date updateTime;

}