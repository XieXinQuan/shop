package com.quan.dao;

import com.quan.entity.ShopCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/23
 */
@Repository
public interface ShopCarRepository extends JpaRepository<ShopCar, Long> {
    Integer countByUserIdAndStatus(Long userId, Byte status);

    Integer countByUserIdAndCommodityIdAndStatus(Long userId, Long commodityId, Byte status);

    @Query(value = "select sc.id id, sc.num num, c.id commodityId, c.price, c.title, c.name, c.image_url imageUrl from shop_car sc LEFT JOIN commodity c on c.id = sc.commodity_id" +
            " where sc.user_id = ?1 and sc.status = 1", nativeQuery = true)
    List<Map<String, Object>> getMyShopCarList(Long userId);

    List<ShopCar> findByIdInAndUserIdAndStatus(List<Long> ids, Long userId, Byte status);

    List<ShopCar> findAllByUserIdAndStatus(Long userId, Byte status);
}
