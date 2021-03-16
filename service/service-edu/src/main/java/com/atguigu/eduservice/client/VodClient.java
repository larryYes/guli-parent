package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author liugj
 * @since 2021-01-07
 */
/**
 * feign调用视频点播模块的功能
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient{

	/**
	 * feign调用删除阿里视频方法
	 * @param videoId
	 * @return
	 */
	@DeleteMapping(value = "/eduvod/video/{videoId}")
	public R reVideo(@PathVariable("videoId") String videoId);
}