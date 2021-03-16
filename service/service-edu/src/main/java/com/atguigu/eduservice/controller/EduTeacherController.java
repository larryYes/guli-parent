package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.teacher.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liugj
 * @since 2020-12-17
 */
@CrossOrigin
@Api(tags="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class
EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师表中的所有数据
     * @return
     */
    @ApiOperation(value = "查询讲师表中的所有数据")
    @GetMapping("findAll")
    public R findAllTeacher(){

        List<EduTeacher> list = teacherService.list(null);
        // 链式编程
        return R.ok().data("items", list);
        //return teacherService.list(null); 未使用返回结果集
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师ID") @PathVariable String id){
        teacherService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value= "分页讲师列表")
    @GetMapping("/pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit
    ){
        //Page对象
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //调用方法时，底层封装，把分页的所有数据都封装到page对象中
        teacherService.page(pageTeacher, null);

        //拿到结果集
        List<EduTeacher> records = pageTeacher.getRecords();
        //拿到记录数
        long total = pageTeacher.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    //TODO: 无法模糊查询name
    @ApiOperation(value= "条件分页查询讲师列表")
    @PostMapping("/pageTeacherQuery/{page}/{limit}")
    public R pageTeacherQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            //参数可以为空，用POST请求提交
            @RequestBody(required = false) TeacherQuery teachQuery
    ){
        // Page对象
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        // 调用方法时，底层封装，把分页的所有数据都封装到page对象中
        teacherService.pageTeacherQuery(pageTeacher, teachQuery);

        // 拿到结果集
        List<EduTeacher> records = pageTeacher.getRecords();
        // 拿到记录数
        long total = pageTeacher.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher
    ){
        //TODO：数据校验
        teacherService.save(eduTeacher);
        return R.ok();
    }

    @ApiOperation(value = "根据Id查询讲师")
    @GetMapping("/select/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

       /* try{
            int i = 10/0;
        }catch (Exception e){
            throw new GuliException(20001,"出现了自定义异常TestException");
        }*/
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "根据讲师ID修改")
    @PutMapping("/update/{id}")
    public R updateById(
            @ApiParam(name = "ID", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "id", value = "讲师ID", required = true)
            @RequestBody EduTeacher eduTeacher
    ){
        eduTeacher.setId(id);
        teacherService.updateById(eduTeacher);
        return R.ok();
    }

}

