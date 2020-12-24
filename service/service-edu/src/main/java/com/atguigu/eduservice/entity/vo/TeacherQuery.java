package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/18 11:28
 */

@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
@Data
public class TeacherQuery {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:32")
    // 使用String类型 全段传过来的数据无需进行类型转换
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-01-01 10:10:32")
    private String end;
}
