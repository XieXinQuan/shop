package com.quan.controller;

import com.quan.Enum.ResultEnum;
import com.quan.exception.GlobalException;
import com.quan.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/19
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;

    @PostMapping("/image")
    public String image(MultipartFile file, @RequestParam("commodityId") Long commodityId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new GlobalException(ResultEnum.ParameterError.getKey(), "请添加至少一张图片.");
        }
        return fileService.save(commodityId, file);

    }
}
