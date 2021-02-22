package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/2/22
 */
@ApiModel(value = "课程章节小结树形结构")
@Data
public class CourseInfoTreeVo {

    private String id;

    private String chapterId;

    private String title;

    /**
     * 小节
     */
    private List<CourseInfoTreeVo> children = new ArrayList<>();

}
