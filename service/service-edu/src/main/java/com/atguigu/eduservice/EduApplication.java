package com.atguigu.eduservice;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.atguigu.eduservice.config.ConstantPropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.net.InetAddress;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/17 17:09
 */
@EnableFeignClients  // 服务调用，客户端启动注解
@EnableDiscoveryClient  // Nacos客户端注解
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"}) //加载service-base的config类，不然只能扫描当前项目com.atguigu.eduservice下的文件
public class EduApplication {
    public static void main(String[] args){
        SpringApplication.run(EduApplication.class,args);

        String port = ConstantPropertiesUtils.SERVER_PORT;
        System.out.println("\n----------------------------------------------------------\n\t" +
                "swagger-ui: http://localhost:"+port+"/swagger-ui.html"+
                "\n----------------------------------------------------------");
    }
    //    记得先启动nginx
}
