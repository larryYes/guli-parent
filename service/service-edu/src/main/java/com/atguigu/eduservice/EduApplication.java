package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/17 17:09
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"}) //加载service-base的config类，不然只能扫描当前项目com.atguigu.eduservice下的文件
public class EduApplication {
    public static void main(String[] args){
        SpringApplication.run(EduApplication.class,args);

        /*Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String serviceName = env.getProperty("spring.application.name");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Service:" + serviceName + " is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "swagger-ui: http://" + ip + ":" + port + path + "/swagger-ui.html\n" +
                "----------------------------------------------------------");*/
    }
}
