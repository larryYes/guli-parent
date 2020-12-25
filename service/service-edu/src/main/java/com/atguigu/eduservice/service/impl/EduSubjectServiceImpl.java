package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.config.SubjectExcelListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author liugj
 * @since 2020-12-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file , EduSubjectService eduSubjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            //SubjectExcelListener(eduSubjectService) 通过有参构造函数传值
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }
}
