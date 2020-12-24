package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/24 15:44
 */

@Api(tags="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss/file")
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     * 上传头像
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file
            ){
        String uploadUrl = ossService.upload(file);


        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
