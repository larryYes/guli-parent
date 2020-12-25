package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author liugj
 * @since 2020-12-25
 */
@Component
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

    /**
     * 根据parentId 查询课程列表所有数据
     */
    List<EduSubject> findAllOneTitle(String parentId);

}
