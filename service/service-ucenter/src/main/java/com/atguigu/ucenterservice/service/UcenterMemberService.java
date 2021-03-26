package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author liugj
 * @since 2021-03-26
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 会员注册
      * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据token获取登录信息
     * @param memberId
     * @return
     */
    LoginVo getLoginInfo(String memberId);
}
