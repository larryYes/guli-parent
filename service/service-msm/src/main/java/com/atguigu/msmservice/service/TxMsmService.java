package com.atguigu.msmservice.service;

/**
 * @author: roc
 * @date: 2021/3/21 下午 02:49
 * @description: TODO
 */
public interface TxMsmService {

    boolean send(String phone, String code, String time);
}
