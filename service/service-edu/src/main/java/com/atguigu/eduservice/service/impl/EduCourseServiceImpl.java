package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        /**
         * 1 向课程表添加课程基本信息
          */

        //courseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        //添加课程基本信息
        int insertRows = baseMapper.insert(eduCourse);
        if(insertRows<=0){
            // 添加失败
            throw new GuliException(20001,"添加课程基本信息失败");
        }
        //添加成功获取到ID传入Description表中用于关联
        String cid = eduCourse.getId();

        /**
         *  2 向课程简介表添加课程简介
         */
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述ID等于课程ID  由于课程表与描述表一对一关系
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
    }
}
