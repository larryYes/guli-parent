package com.atguigu.eduservice.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/1/27
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${server.port}")
    private String serverPort;


    public static String SERVER_PORT;
    @Override
    public void afterPropertiesSet() throws Exception {
        SERVER_PORT = serverPort;
    }
}
