package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author liugj
 * @since 2020-12-25
 */
public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 添加课程分类
     * @param file
     */
    void importSubjectData(MultipartFile file, EduSubjectService subjectService);
}
