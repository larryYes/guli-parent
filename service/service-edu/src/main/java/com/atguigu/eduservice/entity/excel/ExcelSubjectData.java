package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description 与Excel对应的实体类
 * @since 2020-12-25
 */
@Data
public class ExcelSubjectData {
    /**
     *  一级分类
      */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    /**
     *  二级分类
     */
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}