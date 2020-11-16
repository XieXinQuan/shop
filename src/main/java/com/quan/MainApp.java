package com.quan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@SpringBootApplication
@EnableJpaAuditing
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
