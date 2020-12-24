package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author liugj
 * @since 2020-12-17
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 条件分页查询
     * @param pageParam
     * @param teacherQuery
     */
    void pageTeacherQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

}
