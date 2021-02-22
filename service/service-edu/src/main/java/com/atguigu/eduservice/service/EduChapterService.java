package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.CourseInfoTreeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程ID获取章节和小节以树形结构展示
     * @param courseId
     * @return
     */
    List<CourseInfoTreeVo> getChapterVideo(String courseId);
}
