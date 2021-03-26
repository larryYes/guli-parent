package com.atguigu.ucenterservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author liugj
 * @since 2021-03-26
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ){
            throw new GuliException(20001,"error");
        }

        // 获取会员
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UcenterMember::getMobile, mobile);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        if(null == member){
            throw new GuliException(20001,"error");
        }

        // 校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(20001,"error");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());

        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if(StringUtils.isEmpty(nickname) ||
        StringUtils.isEmpty(mobile) ||
        StringUtils.isEmpty(password)||
        StringUtils.isEmpty(code)){
            throw new GuliException(20001,"error");
        }

        // 校验验证码
        // 从redis获取用户收到的验证码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobileCode)){
            throw new GuliException(20001,"验证码错误");
    }

        // 查询数据库中是否存在相同的手机号码
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UcenterMember::getMobile, mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count.intValue()>0){
            throw new GuliException(20001,"error");
        }

        // 添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(registerVo.getNickname());
        member.setMobile(registerVo.getMobile());
        member.setPassword(registerVo.getPassword());
        member.setIsDisabled(false);
        member.setAvatar("https://i.pinimg.com/originals/ce/10/81/ce10818cef2696f7445bd0673c51d1ba.png");
        this.save(member);
    }

    @Override
    public LoginVo getLoginInfo(String memberId) {
        UcenterMember member = baseMapper.selectById(memberId);
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(member,loginVo);
        return  loginVo;
    }
}
