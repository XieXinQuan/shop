package com.quan.dao;

import com.quan.entity.TOrder;
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
public interface TOrderRepository extends JpaRepository<TOrder, Long> {
    List<TOrder> findAllByUserIdAndStatusOrderByUpdateTime(Long userId, Byte status);
    
    @Query(value = "select torder.id, torder.amount, torder.price, torder.SERIAL_NUMBER serialNumber, " +
            " torder.TOTAL_PRICE totalPrice, torder.status, c.title, c.name, c.id commodityId, c.image_url imageUrl " +
            " from t_order torder \n" +
            "  LEFT JOIN commodity c on torder.COMMODITY_ID = c.ID\n" +
            " where torder.USER_ID = ?1 order by torder.update_time desc ", nativeQuery = true)
    List<Map<String, Object>> loadMyOrder(Long userId);
}
