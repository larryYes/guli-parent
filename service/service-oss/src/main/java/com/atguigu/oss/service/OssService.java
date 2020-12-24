package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/24 15:45
 */
public interface OssService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);

}
