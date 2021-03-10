package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.course.CoursePublishVo;
import com.atguigu.eduservice.entity.subject.SubjectVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 根据课程ID查询课程类别
     * @param courseId
     * @return
     */
    List<SubjectVo> getSubject(String courseId);

    /**
     * 查询课程发布信息
     *
     * @param id
     * @return
     */
    CoursePublishVo selectCoursePublishVoById(String id);
}
