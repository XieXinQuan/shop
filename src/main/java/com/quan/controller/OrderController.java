package com.quan.controller;

import com.quan.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/23
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderService orderService;

    @PostMapping("/addShopCar")
    public Object addShopCar(@RequestParam("commodityId") Long commodityId,
                             @RequestParam("num") Integer num){
        Integer count = orderService.addShopCar(commodityId, num);
        return count;
    }

    /**
     * 获取购物车商品数量
     */
    @GetMapping("/getShopCarNum/{commodityId}")
    public Object getShopCarNum(@PathVariable("commodityId") Long commodityId){
        Integer shopCarNum = orderService.getShopCarNum(commodityId);
        return shopCarNum;
    }

    @GetMapping("/loadMyShopCar")
    public Object loadMyShopCar(){
        List<Map<String, Object>> myShopCarList = orderService.getMyShopCarList();
        BigDecimal totalPrice = new BigDecimal("0.00");
        for (int i = 0; i < myShopCarList.size(); i++){
            totalPrice = totalPrice.add(new BigDecimal(myShopCarList.get(i).get("price").toString()));
        }
        Map<String, Object> res = new HashMap<>(2);
        res.put("totalPrice", totalPrice.multiply(new BigDecimal("100")));
        res.put("list", myShopCarList);
        return res;
    }

    @PostMapping("/submitOrder")
    public String submitOrder(){
        orderService.submitOrder();
        return "Success";
    }

    @GetMapping("/loadMyOrder")
    public Object loadMyOrder(){
        return orderService.loadMyOrder();
    }

    @PostMapping("/buy")
    public String buy(@RequestParam("commodityId") Long commodityId){
        orderService.submitOrder(commodityId);
        return "下单成功";
    }
}
