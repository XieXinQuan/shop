package com.quan.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.quan.Enum.CommonByteEnum;
import com.quan.Enum.ResultEnum;
import com.quan.dao.CommodityImageRepository;
import com.quan.entity.CommodityImage;
import com.quan.exception.GlobalException;
import com.quan.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/19
 */
@Service
public class FileService extends BaseService{
    @Resource
    CommodityImageRepository commodityImageRepository;

    public String save(Long commodityId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new GlobalException(ResultEnum.ParameterError.getKey(), "请添加至少一张图片.");
        }
        String originFileName = file.getOriginalFilename();
        String fileName = "" + commodityId +RandomUtil.randomInt(10000, 99999)
                + originFileName.substring(originFileName.lastIndexOf("."));
        String customUrl = getCurrentUserId() + File.separator + fileName;
        String targetFileName = CommonUtil.getImagePath() + customUrl;
        String fileUrl = CommonUtil.getServerWebFilePath() + customUrl;

        fileUrl = fileUrl.replace("\\", "/");

        File targetFile = new File(targetFileName);
        FileUtil.mkParentDirs(targetFile);
        file.transferTo(targetFile);

        CommodityImage image = new CommodityImage();
        image.setCommodityId(commodityId);
        image.setFileName(fileName);
        image.setSize(Integer.parseInt(file.getSize()+""));
        image.setImageUrl(fileUrl);
        image.setStatus(CommonByteEnum.Normal.getKey());
        commodityImageRepository.save(image);
        return fileUrl;
    }
}
