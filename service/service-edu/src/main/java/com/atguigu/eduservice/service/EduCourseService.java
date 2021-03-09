package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */

public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);


    /**
     * 根据课程ID查询课程 用于回显
     * @param id
     * @return
     */
    CourseInfoVo getCourseInfoFormById(String id);

    /**
     * 根据课程ID更新
     * @param courseInfoVo
     */
    String update(CourseInfoVo courseInfoVo);

}
