package com.quan.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/15
 */
@Slf4j
@Component
public class CommonUtil {

    @Value("${local.host.url}")
    private String localHostUrl;

    private static String localHost;

    @PostConstruct
    public void init() {
        localHost = localHostUrl;
    }

    public static String getServerIp(){
        return localHost;
    }
    public static String getServerWebFilePath(){
        return "http://" + getServerIp() + "/files/";
    }
    public static String getProjectPath(){
        return System.getProperty("user.dir");
    }
    public static String getImagePath(){
        return getProjectPath() + File.separator + "files" + File.separator;
    }

}
