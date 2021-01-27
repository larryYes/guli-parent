package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.SubjectVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService courseDescriptionService;

    @Resource
    private EduTeacherService eduTeacherService;

    @Override
    @Transactional(rollbackFor = Exception.class) // 事务
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        /**
         * 1 向课程表添加课程基本信息
         */

        //courseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        //添加课程基本信息
        if (!this.save(eduCourse)) {
            // 添加失败
            throw new GuliException(20001, "添加课程基本信息失败");
        }

        /**
         * 添加成功获取到ID传入Description表中用于关联
         *  2 向课程简介表添加课程简介
         */
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述ID等于课程ID  由于课程表与描述表一对一关系
        courseDescription.setId(eduCourse.getId());
        boolean saveCD = courseDescriptionService.save(courseDescription);
        if (!saveCD) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }

        // 返回课程ID用于前端继续添加改课程其他信息
        return eduCourse.getId();
    }


    @Override
    public CourseInfoVo getCourseInfoFormById(String id) {
        EduCourse course = this.getById(id);
        if (StringUtils.isEmpty(course.getId())) {
            throw new GuliException(20001, "数据不存在");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        // 后端实现课程信息回显
        EduTeacher name = eduTeacherService.getById(course.getTeacherId());
        courseInfoVo.setTeacherId(name.getName());
        List<SubjectVo> subject = baseMapper.getSubject(id);
        for (SubjectVo sub :
                subject) {
            if (sub.getId().equals(courseInfoVo.getSubjectId())) {
                courseInfoVo.setSubjectId(sub.getTitle());
            }
            if (sub.getId().equals(courseInfoVo.getSubjectParentId())){
                courseInfoVo.setSubjectParentId(sub.getTitle());
            }

        }

        courseInfoVo.setDescription(courseDescriptionService.getById(id).getDescription());
        return courseInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 事务
    public void update(CourseInfoVo courseInfoVo){
        // 保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        this.updateById(eduCourse);

        // 保存课程详细信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseInfoVo.getId());
        courseDescriptionService.updateById(courseDescription);
    }


}
