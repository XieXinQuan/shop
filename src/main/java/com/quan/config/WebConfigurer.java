package com.quan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectDir = System.getProperty("user.dir");
        String resourcesDir = projectDir + File.separator + "files" + File.separator;
        File file = new File(resourcesDir);
        if (!file.exists()){
            file.mkdir();
        }
        resourcesDir = resourcesDir.replace("\\", "/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + resourcesDir);
    }
}
