package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.atguigu.demo.excelUtils.ExcelListener;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/25 10:40
 */
public class ReadExcel {
    public static void main(String[] args) {
        // 写法1：
        String fileName = "D:\\_Code\\在线教育\\跟学\\read.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(
                fileName,          // 文件路径
                ReadData.class,    // 实体类，与Excel内容对应
                new ExcelListener()   // 新建监听器，一行一行读取
        )
                .sheet()     // 读取的sheet
                .doRead();   // 读操作
    }
}
