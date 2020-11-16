package com.quan.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "commodity_image")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class CommodityImage {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long commodityId;
    private String fileName;
    private Integer size;
    private Byte status;
    private String imageUrl;
    @CreatedBy
    private Long createUser;
    @LastModifiedBy
    private Long updateUser;
    @CreatedDate
    private Timestamp createTime;
    @LastModifiedDate
    private Timestamp updateTime;

}
