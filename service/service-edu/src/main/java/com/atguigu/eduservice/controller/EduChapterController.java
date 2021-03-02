package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/eduservice/chapter")
@Api(tags = "章节管理")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "根据课程ID查询章节和小节")
    @GetMapping("{courseId}/chapterAndVideo")
    public R getChapterVideo(@PathVariable String courseId){

        return R.ok().data("courseInfo",chapterService.getChapterVideo(courseId));
    }
}

