package com.quan.exception;

import com.alibaba.fastjson.JSON;
/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/1/11
 */
public class GlobalException extends RuntimeException{
    private Integer status;
    private String msg;

    public GlobalException(Integer status) {
        this.status = status;
    }

    public GlobalException(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
