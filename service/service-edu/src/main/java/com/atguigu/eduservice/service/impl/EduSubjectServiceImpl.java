package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.config.SubjectExcelListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.entity.vo.SubjectNestedVo;
import com.atguigu.eduservice.entity.vo.SubjectVo;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private EduSubjectMapper eduSubjectMapper;

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

    @Override
    public List<SubjectNestedVo> nestedList() {

        //最终要的到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        //获取一级分类数据记录
       /* QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<EduSubject> subjects = baseMapper.selectList(queryWrapper);*/
        List<EduSubject> subjects = eduSubjectMapper.findAllOneTitle("0");

        //获取二级分类数据记录
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper2.orderByAsc("sort", "id");
        List<EduSubject> subSubjects = baseMapper.selectList(queryWrapper2);

        /**
         *   构建树形结构
          */
        //填充一级分类vo数据
        for (int i = 0; i < subjects.size(); i++) {
            EduSubject subject = subjects.get(i);

            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            //subject值传入subjectNestedVo中
            BeanUtils.copyProperties(subject, subjectNestedVo);


            //通过一级标题的ID去遍历旗下所有的二级标题并加入subjectVoArrayList数组
            //填充二级分类vo数据
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            for (int j = 0; j < subSubjects.size(); j++) {

                EduSubject subSubject = subSubjects.get(j);
                //一级标题的id等于二级标题的父id
                if(subject.getId().equals(subSubject.getParentId())){

                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            // 每遍历一个一级标题，则new一个subjectNestedVo
            subjectNestedVo.setChildren(subjectVoArrayList);

            // 每做好一个标题的树状结构则将其添加到subjectNestedVoArrayList中
            subjectNestedVoArrayList.add(subjectNestedVo);
        }

        return subjectNestedVoArrayList;
    }
}
