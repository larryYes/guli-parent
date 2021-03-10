package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.course.CourseInfoVo;
import com.atguigu.eduservice.entity.course.CoursePublishVo;
import com.atguigu.eduservice.entity.course.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
    private EduVideoService videoService;

    @Resource
    private EduChapterService chapterService;

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

        //查询描述信息
        courseInfoVo.setDescription(courseDescriptionService.getById(id).getDescription());
        return courseInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 事务
    public String update(CourseInfoVo courseInfoVo){
        // 保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        boolean resultCourseInfo = this.updateById(eduCourse);
        if(!resultCourseInfo){
            throw new GuliException(20001, "课程信息保存失败");
        }

        // 保存课程详细信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseInfoVo.getId());
        boolean resultCourseDescription = courseDescriptionService.updateById(courseDescription);
        if(!resultCourseDescription){
            throw new GuliException(20001, "课程描述保存失败");
        }
        return eduCourse.getId();
    }

    @Override
    public CoursePublishVo selectCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public Integer publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        return baseMapper.updateById(course);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(EduCourse::getGmtCreate);

        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            queryWrapper.lambda().like(EduCourse::getTitle, courseQuery.getTitle());
        }

        if (!StringUtils.isEmpty(courseQuery.getTeacherId()) ) {
            queryWrapper.lambda().eq(EduCourse::getTeacherId, courseQuery.getTeacherId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.lambda().ge(EduCourse::getSubjectParentId, courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.lambda().ge(EduCourse::getSubjectId, courseQuery.getSubjectId());
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeCourseById(String id) {

        //根据id删除所有视频
        videoService.removeVideoByCourseId(id);

        //根据id删除所有章节
        chapterService.removeChapterByCourseId(id);

        return baseMapper.deleteById(id);
    }

}
