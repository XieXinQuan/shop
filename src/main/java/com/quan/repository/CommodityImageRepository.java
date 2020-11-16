package com.quan.dao;

import com.quan.entity.CommodityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/19
 */
@Repository
public interface CommodityImageRepository extends JpaRepository<CommodityImage, Long> {
    List<CommodityImage> findAllByCommodityIdAndStatus(Long commodityIdm, Byte status);

}
