package com.quan.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Administrator
 */
@Table(name = "commodity")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class Commodity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long shopId;

    @Column(nullable = false, columnDefinition = "varchar(20) default ''")
    private String title;

    @Column(nullable = false, columnDefinition = "varchar(20) default ''")
    private String name;

    @Generated(value = GenerationTime.INSERT)
    @Column(nullable = false, columnDefinition = "tinyint(4) default 1")
    private Byte category;

    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private Integer stock;

    @Column(nullable = false, columnDefinition = "decimal(6, 2) default 0.00")
    private BigDecimal price;

    private Byte status;

    private Date sellTime;

    private String imageUrl;

    @CreatedBy
    private Long createUser;

    @LastModifiedBy
    private Long updateUser;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}