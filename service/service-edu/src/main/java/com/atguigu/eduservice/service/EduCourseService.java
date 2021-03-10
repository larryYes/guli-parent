package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.course.CourseInfoVo;
import com.atguigu.eduservice.entity.course.CoursePublishVo;
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

    /**
     * 查询课程发布信息
     * @param id
     * @return
     */
    CoursePublishVo selectCoursePublishVoById(String id);

    /**
     * 根据id发布课程
     * @param id
     * @return
     */
    Integer publishCourseById(String id);
}
