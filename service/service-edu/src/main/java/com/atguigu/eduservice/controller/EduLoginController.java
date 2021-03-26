package com.atguigu.eduservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/23 10:53
 */
@Api(tags = "临时登陆授权")
@CrossOrigin  // 解决跨域问题
@RestController
@RequestMapping("/edu/user")
public class EduLoginController {

    //登陆
    @ResponseBody
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","test");
        // 直接将json信息打印出来
        // 将获取的json数据封装一层，然后在给返回
    }

    //获取用户信息
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://s1.ax1x.com/2020/10/21/B9ROzT.jpg");
    }
}
