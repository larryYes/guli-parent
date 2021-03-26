package com.atguigu.ucenterservice.controller;


import ch.qos.logback.core.boolex.EvaluationException;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import sun.net.httpserver.HttpServerImpl;
import sun.security.x509.FreshestCRLExtension;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author liugj
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

    @Resource
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据Token获取登录信息")
    @GetMapping("getToken")
    public R getLoginInfo(HttpServletRequest request){
        try{
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            LoginVo loginVo = memberService.getLoginInfo(memberId);
            return R.ok().data("item",loginVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }

}

