package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/25 10:22
 */


@Data
public class WriteData {

    // 学生编号
    @ExcelProperty("学生编号")
    private Integer sno;

    //学生名
    @ExcelProperty("学生姓名")
    private String sname;

    //
}


