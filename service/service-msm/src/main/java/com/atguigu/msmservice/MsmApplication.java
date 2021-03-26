package com.atguigu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/3/18
 */

@EnableFeignClients  // 服务调用，客户端启动注解
@EnableDiscoveryClient  // Nacos客户端注解
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"}) //加载service-base的config类，不然只能扫描当前项目com.atguigu.eduservice下的文件
public class MsmApplication {
    public static void main(String[] args){
        SpringApplication.run(MsmApplication.class,args);
    }
    //    记得先启动nginx
}
