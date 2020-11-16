package com.quan.service;

import cn.hutool.core.util.IdUtil;
import com.quan.Enum.CommonByteEnum;
import com.quan.Enum.ResultEnum;
import com.quan.dao.CommodityRepository;
import com.quan.dao.ShopCarRepository;
import com.quan.dao.TOrderRepository;
import com.quan.entity.Commodity;
import com.quan.entity.ShopCar;
import com.quan.entity.TOrder;
import com.quan.exception.GlobalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/23
 */
@Service
public class OrderService extends BaseService{
    @Resource
    ShopCarRepository shopCarRepository;
    @Resource
    TOrderRepository orderRepository;
    @Resource
    CommodityRepository commodityRepository;

    public Integer addShopCar(Long commodityId, Integer num){

        Integer count = shopCarRepository.countByUserIdAndCommodityIdAndStatus(getCurrentUserId(), commodityId, CommonByteEnum.Normal.getKey());
        if (count > 0) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "您已经添加过了.");
        }

        ShopCar shopCar = new ShopCar();
        shopCar.setUserId(getCurrentUserId());
        shopCar.setCommodityId(commodityId);
        shopCar.setNum(num);
        shopCar.setStatus(CommonByteEnum.Normal.getKey());

        shopCarRepository.save(shopCar);
        return shopCarRepository.countByUserIdAndStatus(getCurrentUserId(), CommonByteEnum.Normal.getKey());
    }
    public Integer getShopCarNum(Long commodityId){
        Integer count = shopCarRepository.countByUserIdAndCommodityIdAndStatus(getCurrentUserId(), commodityId, CommonByteEnum.Normal.getKey());
        return count;
    }

    public List<Map<String, Object>> getMyShopCarList(){
        List<Map<String, Object>> myShopCarList = shopCarRepository.getMyShopCarList(getCurrentUserId());
        return myShopCarList;
    }

    /**
     * commodityId 为空 清空购物车
     */
    public void submitOrder(Long commodityId){
        List<Long> commodityIds = new ArrayList<>();
        List<ShopCar> shops = null;
        if (commodityId == null) {
            shops = shopCarRepository.findAllByUserIdAndStatus(getCurrentUserId(), CommonByteEnum.Normal.getKey());
            List<Long> collect = shops.stream().map(ShopCar::getCommodityId).collect(Collectors.toList());
            commodityIds.addAll(collect);
        }else {
            commodityIds.add(commodityId);
        }

        List<TOrder> orders = new ArrayList<>();
        List<Commodity> commodityList = commodityRepository.findByIdIn(commodityIds);


        for (Commodity commodity : commodityList) {
            TOrder order = TOrder.builder().amount(commodity.getPrice()).commodityId(commodity.getId()).count(1).money(commodity.getPrice()).serialNumber(IdUtil.getSnowflake(1L, 1L).nextIdStr())
                    .userId(getCurrentUserId()).price(commodity.getPrice()).productId(0L).totalPrice(commodity.getPrice()).status(CommonByteEnum.Normal.getKey()).shippingAddressId(0L).build();
            orders.add(order);
        }
        orderRepository.saveAll(orders);
        if (shops != null){
            shopCarRepository.deleteInBatch(shops);
        }
    }

    public void submitOrder(){
        submitOrder(null);
    }

    public List<Map<String, Object>> loadMyOrder(){
        List<Map<String, Object>> list = orderRepository.loadMyOrder(getCurrentUserId());
        return list;
    }


}
