package com.quan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "t_order")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private Long userId;

    private Long commodityId;

    private Long productId;

    private BigDecimal money;

    private BigDecimal amount;

    private BigDecimal price;

    private Integer count;

    private BigDecimal totalPrice;

    private Long shippingAddressId;

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