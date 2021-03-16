package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags="阿里云视频点播微服务")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduvod/video")
public class VideoAdminController {

	@Autowired
	private VideoService videoService;

	//TODO: 未完成
	@PostMapping("upload")
	public R uploadVideo(
			@ApiParam(name = "file", value = "文件", required = true)
			@RequestParam("file") MultipartFile file) throws Exception {

		String videoId = videoService.uploadVideo(file);
		return R.ok().message("视频上传成功").data("videoId", videoId);
	}

	@ApiOperation(value= "根据ID删除视频")
	@DeleteMapping("/{videoId}")
	public R removeVideo(
			@PathVariable String videoId
	){
		videoService.removeById(videoId);
		return R.ok();
	}
}