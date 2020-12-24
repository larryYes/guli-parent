package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/24 15:46
 */

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String upload(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtils.FILE_HOST;

        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。

        //构建日期路径：avatar/2019/02/26/文件名
        String filePath = new DateTime().toString("yyyy/MM/dd");

        //文件名：uuid.扩展名
        String originalName = file.getOriginalFilename();
        String uuidName = UUID.randomUUID().toString().replaceAll("-","");
        String newName = uuidName +"-"+originalName;
        String fileUrl =  filePath + "/" + newName;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            if(!ossClient.doesBucketExist(bucketName)){
                //创建bucketNam
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            // 调用Oss方法实现上传
            ossClient.putObject(bucketName, fileUrl, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        //返回上传之后的文件路径
        String uploadUrl = "https://"+bucketName+"."+endPoint+"/"+fileUrl;
        return uploadUrl;
    }
}
