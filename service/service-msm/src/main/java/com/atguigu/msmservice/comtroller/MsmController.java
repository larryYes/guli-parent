package com.atguigu.msmservice.comtroller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RandomUtil;
import com.atguigu.msmservice.service.TxMsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author: roc
 * @date: 2021/3/20 下午 10:49
 * @description: TODO
 */
@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private TxMsmService txMsmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/tx/send/{phone}")
    public R send(@PathVariable String phone) {
        //1、从redis里获取验证码，如果获取的到就直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2、如果获取不到就通过阿里云进行发送
        //生成随机数，使用阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        //调用service发送短信
        boolean isSend = txMsmService.send(phone, code, "5");
        if(isSend) {
            //发送成功，把验证码放到redis种，并设置有效时间
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }

}
