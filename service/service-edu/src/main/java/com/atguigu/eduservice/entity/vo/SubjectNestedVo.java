package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 20/12/25 15:36
 */
@Data
public class SubjectNestedVo {

    private String id;
    private String title;

    /**
     * 保存二级分类
     */
    private List<SubjectVo> children = new ArrayList<>();
}
