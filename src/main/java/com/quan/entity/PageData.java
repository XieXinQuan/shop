package com.quan.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@Data
public class PageData<T> {

    private Integer page;
    private Integer size;
    private List<T> data;
    private Integer totalPage;
    private long totalNum;

    public PageData(Integer page, Integer size, List<T> data, Integer totalPage, long totalNum) {
        this.page = page;
        this.size = size;
        this.data = data;
        this.totalPage = totalPage;
        this.totalNum = totalNum;
    }
}
