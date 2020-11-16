package com.quan.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/15
 */
@Data
@Component
public class Config {
    @Value("${server.port}")
    private Integer serverPort;
}
