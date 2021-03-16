package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;
/**
 *
 * @author liugj
 * @Description 熔断器
 * @since 2021-01-07
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    /**
     * 接口方法调用失败则执行该方法
     * @param videoId
     * @return
     */
    @Override
    public R reVideo(String videoId) {
        return R.error().message("调用服务删除视频失败");
    }

}