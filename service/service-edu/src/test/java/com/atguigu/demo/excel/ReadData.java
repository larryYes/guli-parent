package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/25 10:41
 */
@Data
public class ReadData {

    //设置列对应的属性
    @ExcelProperty(index = 0)
    private String sid;

    //设置列对应的属性
    @ExcelProperty(index = 1)
    private String sname;
}
