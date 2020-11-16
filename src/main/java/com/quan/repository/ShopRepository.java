package com.quan.dao;

import com.quan.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/3
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
