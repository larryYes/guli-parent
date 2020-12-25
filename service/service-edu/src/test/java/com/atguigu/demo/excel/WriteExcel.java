package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/25 10:24
 */
public class WriteExcel {

    public static void main(String[] args){
        // 1 设置写入文件路径
        String fileName = "D:\\_Code\\在线教育\\跟学\\write.xlsx";

        //实现Excel写操作
        EasyExcel.write(fileName, WriteData.class)// 文件路径和实体类
                .sheet("学生列表") //sheet设置
                .doWrite(getData());  // 将要写入的数据放入
    }



    // 造一个有数据的list集合
    private static List<WriteData> getData(){
        List<WriteData> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            WriteData dataExcel = new WriteData();
            dataExcel.setSno(i);
            dataExcel.setSname("lucy"+i);
            list.add(dataExcel);
        }
        return list;
    }
}
