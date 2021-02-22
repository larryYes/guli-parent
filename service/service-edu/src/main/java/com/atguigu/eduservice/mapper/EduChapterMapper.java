package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.CourseInfoTreeVo;
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
public interface EduChapterMapper extends BaseMapper<EduChapter> {

    /**
     * 根据课程Id查询所有章节和小节
     * @param courseId
     * @return
     */
    List<CourseInfoTreeVo> getAllChapterVideo(String courseId);
}
