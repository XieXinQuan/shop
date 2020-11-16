package com.quan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer status;
    private String msg;
    private T data;
}
