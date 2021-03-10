package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.course.CourseInfoVo;
import com.atguigu.eduservice.entity.course.CoursePublishVo;
import com.atguigu.eduservice.entity.course.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
@Api(tags = "课程")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String cid = courseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId", cid);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("/{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id
    ) {
        return R.ok().data("courseInfo", courseService.getCourseInfoFormById(id));
    }

    @ApiOperation(value = "根据ID更新课程")
    @PutMapping()
    public R update(
            @RequestBody CourseInfoVo courseInfoVo
    ) {
        String cid = courseService.update(courseInfoVo);
        return R.ok().data("courseId", cid);
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("/publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id
    ){
        return R.ok().data("courseInfo",courseService.selectCoursePublishVoById(id));
    }

    @ApiOperation(value = "根据id发布课程(修改课程状态为发布)")
    @GetMapping("/publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id
    ){
        return R.integerJudge(courseService.publishCourseById(id));
    }

    @ApiOperation(value = "条件分页查询课程")
    @GetMapping("/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page",value = "当前页码")
            @PathVariable long page,
            @ApiParam(name = "limit",value = "每页记录数")
            @PathVariable long limit,
            @ApiParam(name = "courseQuery",value = "条件入参",required = false)
            CourseQuery courseQuery
    ){
        Page<EduCourse> pageParam = new Page<>(page,limit);
        courseService.pageQuery(pageParam,courseQuery);

        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("records",records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        return R.integerJudge(courseService.removeCourseById(id));

    }
}

