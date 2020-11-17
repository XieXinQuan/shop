package com.quan.controller;

import com.alibaba.fastjson.JSONObject;
import com.quan.Enum.CommodityCategoryEnum;
import com.quan.Enum.ResultEnum;
import com.quan.annotation.EnumValue;
import com.quan.annotation.NeedLogin;
import com.quan.annotation.SimpleValue;
import com.quan.entity.Commodity;
import com.quan.exception.GlobalException;
import com.quan.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/4/18
 */
@RestController
@Slf4j
@RequestMapping("/commodity")
@Validated
public class CommodityController {

    @Value("${server.port}")
    String serverPort;

    @Resource
    CommodityService commodityService;


    @NeedLogin
    @PostMapping("/addCommodity")
    public String addCommodity(@RequestParam(value = "id", required = false) Long id,
                               @RequestParam("title") @Size(min = 1, max = 20, message = "商品标题的长度在1~20之间.") String title,
                               @RequestParam("name") @Size(min = 1, max = 20, message = "商品名称的长度在1~20之间.") String name,
                               @RequestParam("category") @EnumValue(value = CommodityCategoryEnum.class, message = "不存在该商品分类") Byte category,
                               @RequestParam("stock") @NotNull(message = "库存不能为空") @Min(value = 1, message = "库存不得小于1.") @Max(value = Integer.MAX_VALUE, message = "库存不得超过2147483647") Integer stock,
                               @RequestParam("price") @NotNull(message = "单价不能为空") @DecimalMax(value = "9999.99", message = "单价不得超过￥9999.99元") @DecimalMin(value = "0.01", message = "商品单价不得少于￥0.01元") BigDecimal price,
                               @RequestParam(value = "sellTime", required = false)  Date sellTime,
                               @RequestParam("imageUrl") String imageUrl
                       ){
        commodityService.addCommodity(id, title, name, category, stock, price, sellTime, imageUrl);
        return "商品添加成功.";
    }

    @GetMapping("/loadCommodityList")
    public Object loadCommodityList(){
        return commodityService.loadIndexCommodityContainsCategory();
    }

    @GetMapping("/categoryList")
    public Object categoryList(){
        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();
        int gridSize = 5;
        List<List<Map<String, Object>>> res = new ArrayList<>(values.length % gridSize == 0 ? values.length / gridSize : values.length / gridSize + 1);

        for (int i = 0; i < values.length; i++){
            CommodityCategoryEnum commodityCategory = values[i];

            Map<String, Object> map = createCategoryMap(commodityCategory.getKey(), commodityCategory.getValue(), "");

            int index = i / gridSize;
            int rowIndex = i % gridSize;
            if (rowIndex == 0){
                List<Map<String, Object>> list = new ArrayList<>(gridSize);
                list.add(map);
                res.add(list);
            }else {
                List<Map<String, Object>> list = res.get(index);
                list.add(map);
            }

        }
        List<Map<String, Object>> maps = res.get(res.size() - 1);
        Map<String, Object> map = createCategoryMap(0, "全部", "");
        if (maps.size() == gridSize){
            List<Map<String, Object>> list = new ArrayList<>(1);
            list.add(map);
            res.add(list);
        }else {
            maps.add(map);
        }
        return res;
    }
    private Map<String, Object> createCategoryMap(Object key, String name, String icon){
        Map<String, Object> map = new HashMap<>(3);
        map.put("key", key);
        map.put("name", name);
        map.put("icon", icon);
        return map;
    }

    @GetMapping("/categoryListMap")
    public Object categoryListMap(){
        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();
        List<Map<String, Object>> res = new ArrayList<>(values.length);
        for (CommodityCategoryEnum value : values){
            Map<String, Object> map = new HashMap<>(2);
            map.put("key", value.getKey());
            map.put("value", value.getValue());
            res.add(map);
        }
        return res;
    }

    @GetMapping("/indexCommodityList")
    public Object indexCommodityList(){
        return commodityService.indexCommodityList();
    }

    @NeedLogin
    @PostMapping("/applyCreateCommodityId")
    public Object applyCreateCommodityId(){
        return commodityService.applyCreateCommodity();
    }

    @PostMapping("/getCommodityListByCategory/{key}")
    public Object getCommodityListByCategory(@PathVariable("key") Byte key,
                 @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                 @RequestParam(value = "pageCount", required = false) Integer pageCount,
                 @RequestParam(value = "priceOrder", required = false) @SimpleValue({0, 1, 2}) Integer priceOrder){
        List<Commodity> commodities = commodityService.commodityList(key, pageIndex, pageCount, priceOrder);
        return commodities;
    }

    @GetMapping("/getCommodityDetails/{id}")
    public Object getCommodityDetails(@PathVariable("id") Long id){
        if (id == null || id <= 0L) {
            throw new GlobalException(ResultEnum.ParameterError.getKey());
        }
        JSONObject commodityDetails = commodityService.getCommodityDetails(id);
        return commodityDetails;
    }
}
