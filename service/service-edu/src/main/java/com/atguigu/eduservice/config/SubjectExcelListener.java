package com.atguigu.eduservice.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @author by liuguangjin
 * @Description Excel读取监听器
 * @Date 20/12/25 11:53
 */

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    /**
     * 不能交给spring管理，不能@Autowrite注入，需要通过有参构造来传值
      */
    public EduSubjectService subjectService;
    
    public SubjectExcelListener() {}
    /**
     * 创建有参数构造，传递subjectService用于操作数据库
     * @param subjectService
     */
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行一行去读取excle内容
     * @param objData
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData objData, AnalysisContext analysisContext) {
        if(objData == null) {
            throw new GuliException(20001,"添加失败");
        }

        //添加一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService,objData.getOneSubjectName());
        //没有相同的
        if(existOneSubject == null) {
            existOneSubject = new EduSubject(objData.getOneSubjectName(),"0");
            //existOneSubject.setTitle(objData.getOneSubjectName());
            //existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        //获取一级分类id值
        String pid = existOneSubject.getId();
        
        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectService,objData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject(objData.getTwoSubjectName(),pid);
            //existTwoSubject.setTitle(objData.getTwoSubjectName());
            //existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    /**
     * 读取excel表头信息
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    /**
     * 读取完成后执行
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}

    /**
     * 判断二级分类是否重复
     * @param subjectService
     * @param name
     * @param pid
     * @return
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    /**
     * 判断一级分类是否重复
     * @param subjectService
     * @param name
     * @return
     */
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        // 传入service的作用 用于调用查询数据库操作
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
}