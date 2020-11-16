package com.quan.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quan.Enum.CommodityCategoryEnum;
import com.quan.Enum.CommodityStatus;
import com.quan.Enum.CommonByteEnum;
import com.quan.Enum.ResultEnum;
import com.quan.dao.CommodityImageRepository;
import com.quan.dao.CommodityRepository;
import com.quan.entity.Commodity;
import com.quan.entity.CommodityImage;
import com.quan.exception.GlobalException;
import com.quan.util.PageUtil;
import com.quan.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/4/18
 */
@Service
@Slf4j
public class CommodityService extends BaseService{
    @Resource
    CommodityRepository commodityRepository;
    @Resource
    CommodityImageRepository commodityImageRepository;
    @Resource
    RedisTemplate redisTemplate;

    private String redisIndexCommodityListKey = "indexCommodityList";

    /**
     * 添加商品
     */
    public void addCommodity(Long id, String title, String name, Byte category, Integer stock, BigDecimal price, Date sellTime, String imageUrl){
        Commodity commodity = new Commodity();
        if (id != null) commodity = commodityRepository.findById(id).get();

        commodity.setTitle(title);
        commodity.setName(name);
        commodity.setCategory(category);
        commodity.setStock(stock);
        commodity.setPrice(price);
        commodity.setShopId(1L);
        commodity.setImageUrl(imageUrl);
        //设置为可用的状态
        commodity.setStatus(CommonByteEnum.Normal.getKey());
        Date nowDate = new Date();
        Date date = Optional.ofNullable(sellTime).orElse(nowDate);

        if (date.after(DateUtil.offsetDay(date, 7)))
            throw new GlobalException(ResultEnum.CustomException.getKey(),
                    "开售时间不得超过" + DateUtil.format(DateUtil.offsetDay(date, 7), "yyyy-MM-dd HH:mm"));
        commodity.setSellTime(date);

        commodityRepository.save(commodity);
        //更新主页缓存
        indexCommodityUpdateToRedis();

    }

    /**
     * 加载商品列表
     */
    public List<Page<Commodity>> loadCommodityList(){
        List<Page<Commodity>> result = new ArrayList<Page<Commodity>>();

        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();

        for (int i = 0; i < values.length; i++){
            Page<Commodity> commodities = loadCommodityListByCategory(values[i].getKey());
            result.add(commodities);
        }
        return result;
    }

    public Page<Commodity> loadCommodityListByCategory(Byte category){
        if (category == null) throw new GlobalException(ResultEnum.CustomException.getKey(), "商品分类不能为空");

        //检验category 值
        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();
        boolean isCan = false;
        List<CommodityCategoryEnum> collect = Arrays.asList(values).stream().filter(commodityCategoryEnum -> commodityCategoryEnum.getKey() == category).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) throw new GlobalException(ResultEnum.CustomException.getKey(), "商品分类不合法.");


        return commodityRepository.findAllByCategoryOrderByUpdateTimeDesc(category, PageUtil.getSimplePageable(1, 5));


    }

    /**
     * 加载商品列表
     */
    public List<Map<String, Object>> loadIndexCommodityContainsCategory(){
        List<Map<String, Object>> result = new ArrayList<>();
        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();

        for (int i = 0; i < values.length; i++){
            Map<String, Object> temp = new LinkedHashMap<>();
            Map<String, Object> data = new LinkedHashMap<>();
            Page<Commodity> commodities = loadCommodityListByCategory(values[i].getKey());
            data.put("category", values[i].getValue());
            data.put("data", commodities.getContent());

            temp.put("category", values[i].getValue());
            temp.put("data", data);
            result.add(temp);
        }
        return result;
    }
    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 主页商品先访问redis
     */
    public List<List<JSONObject>> indexCommodityList(){
        List<List<JSONObject>> results = new ArrayList<>();

        List<Map<String, Object>> maps = null;
        List<JSONObject> redisListData = null;
        Long num = redisTemplate.opsForList().size(redisIndexCommodityListKey);

        if (num != null && num > 0){
            redisListData = new ArrayList<>();
            List<String> redisData = redisTemplate.opsForList().range(redisIndexCommodityListKey, 0, redisTemplate.opsForList().size(redisIndexCommodityListKey));
            for (String str : redisData){
                JSONObject jsonObject = JSONObject.parseObject(str);
                redisListData.add(jsonObject);
            }
        }else {
            maps = this.indexCommodityUpdateToRedis();
        }
        if (maps != null){
            redisListData = new ArrayList<>(maps.size());
            for (Map<String, Object> map : maps){
                JSONObject jsonObject = new JSONObject(map);
                redisListData.add(jsonObject);
            }
        }

        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();
        for (CommodityCategoryEnum categoryEnum : values){
            List<JSONObject> jsonObjects = new ArrayList<>(2);
            for (JSONObject jsonObject : redisListData){
                if (categoryEnum.getKey().equals(jsonObject.getByte("category"))){
                    jsonObjects.add(jsonObject);
                }
            }
            if (jsonObjects.isEmpty()) {
                continue;
            }
            results.add(jsonObjects);
        }
        return results;
    }
    /**
     * 获取主页商品 并存入redis -- 更新
     */
    private List<Map<String, Object>> indexCommodityUpdateToRedis(){
        List<Map<String, Object>> maps = commodityRepository.indexCommodity(2);
        redisTemplate.delete(redisIndexCommodityListKey);
        for (int i = 0; i < maps.size(); i++){
            Map<String, Object> map = maps.get(i);
            redisTemplate.opsForList().rightPush(redisIndexCommodityListKey, JSONObject.toJSONString(map));
        }
        return maps;
    }
    /**
     * 申请创建商品Id 如status=2,则直接返回
     */
    public Object applyCreateCommodity(){
        Commodity commodity = commodityRepository.findByUserIdAndStatus(getCurrentUserId(), CommodityStatus.Draft.getKey());

        if (commodity == null){
            commodity = new Commodity();
            commodity.setUserId(getCurrentUserId());
            commodity.setStatus(CommodityStatus.Draft.getKey());
            commodity.setShopId(1L);
            Commodity save = commodityRepository.saveAndFlush(commodity);
        }

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(commodity));
        List<CommodityImage> imageList = commodityImageRepository.findAllByCommodityIdAndStatus(commodity.getId(), CommonByteEnum.Normal.getKey());
        if (imageList != null && imageList.size() != 0){
            List<Map<String, String>> list = new ArrayList<>(imageList.size());

            imageList.stream().forEach(commodityImage -> {
                Map<String, String> map = new HashMap<>(1);
                map.put("url", commodityImage.getImageUrl());
                list.add(map);
            });

            jsonObject.put("imageList", list);
        }
        CommodityCategoryEnum[] values = CommodityCategoryEnum.values();
        for (CommodityCategoryEnum value : values) {
            if (value.getKey().equals(commodity.getCategory())) {
                jsonObject.put("categoryname", value.getValue());
            }
        }

        return jsonObject;
    }


    public List<Commodity> commodityList(Byte category, Integer pageIndex, Integer count, Integer priceOrder){


        count = Optional.ofNullable(count).orElse(5);
        Pageable page = PageUtil.getSimplePageable(pageIndex, count);
        Page<Commodity> data ;
        if (category > 0) {
            data = commodityRepository.findByCategoryAndStatusOrderBySellTime(category, CommodityStatus.Normal.getKey(), page);
        }else {
            data = commodityRepository.findAllByStatusOrderBySellTime(CommonByteEnum.Normal.getKey(), page);
        }
        List<Commodity> content = data.getContent();
        if (priceOrder != null && priceOrder != 0) {
            content = content.stream().sorted(Comparator.comparing(Commodity::getPrice)).collect(Collectors.toList());
        }
        return content;
    }

    public JSONObject getCommodityDetails(Long id){
        Map<String, Object> commodityDetails = commodityRepository.getCommodityDetails(id);
        if (commodityDetails == null || commodityDetails.size() == 0) {
            throw new GlobalException(ResultEnum.CustomException.getKey(), "该商品不存在或者已下架.");
        }

        String s = JSONObject.toJSONString(commodityDetails);
        JSONObject jsonObject = JSON.parseObject(s);
        String imagelist = jsonObject.getString("imagelist");

        List<String> list = new ArrayList<>(5);
        if (!StringUtils.isEmpty(imagelist)){
            Collections.addAll(list, imagelist.split(","));
        }
        jsonObject.put("imageList", list);
        return jsonObject;

    }

}