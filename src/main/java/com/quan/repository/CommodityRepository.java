package com.quan.dao;

import com.quan.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/1
 */
@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    List<Commodity> findAllByCategory(Byte b);

    Page<Commodity> findAllByCategoryOrderByUpdateTimeDesc(Byte category, Pageable page);

    @Query(value = " select c.id, c.title, c.name, c.price, c.category, c.sell_time sellTime, c.image_url imageUrl  from commodity c   \n" +
            " where c.status = 1 GROUP BY c.id ORDER BY c.CATEGORY, c.SELL_TIME desc", nativeQuery = true)
    List<Map<String, Object>> indexCommodity(Integer categoryCount);

    Commodity findByUserIdAndStatus(Long userId, Byte status);

    Page<Commodity> findByCategoryAndStatusOrderBySellTime(Byte category, Byte status, Pageable page);

    @Query(value = "select c.id, c.name, c.title, c.price, c.SHOP_ID shopId, GROUP_CONCAT(ci.IMAGE_URL) as imagelist " +
            "from commodity c LEFT JOIN commodity_image ci on c.id = ci.COMMODITY_ID\n" +
            " where c.id = ?1 and c.status = 1 and ci.status = 1 GROUP BY c.id", nativeQuery = true)
    Map<String, Object> getCommodityDetails(Long id);

    List<Commodity> findByIdIn(List<Long> ids);

    Page<Commodity> findAllByStatusOrderBySellTime(Byte status, Pageable page);


}
