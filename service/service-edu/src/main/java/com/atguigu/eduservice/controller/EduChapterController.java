package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.servicebase.config.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    @GetMapping("{id}/chapterAndVideo")
    public R getChapterVideo(
            @ApiParam(name = "id",value = "章节id",required = true)
            @PathVariable String id
    ){

        return R.ok().data("courseInfo",chapterService.getChapterVideo(id));
    }

    @ApiOperation(value = "新增章节")
    @PostMapping
    public R add(
            @ApiParam(name = "chapterVo",value = "章节对象",required = true)
            @RequestBody EduChapter chapter
    ){
        return R.ok().data("result",chapterService.add(chapter));
    }

    @ApiOperation(value = "根据Id查询章节")
    @GetMapping("/{id}")
    public R select(
            @ApiParam(name = "id",value = "章节id",required = true)
            @PathVariable String id
    ){
        return R.ok().data("chapter",chapterService.getById(id));
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping
    public R update(
            @ApiParam(name = "chapter",value = "章节实体")
            @RequestBody EduChapter chapter
    ){
        if (StringUtils.isEmpty(chapter.getId())){
            throw new GuliException(20001,"请传入章节ID");
        }
        return R.ok().data("result",chapterService.updateById(chapter));
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("/{id}")
    public R delete(
            @ApiParam(name = "id",value = "章节id",required = true)
            @PathVariable String id
    ){
        boolean result = chapterService.removeChapterById(id);
        if (result){
            return R.ok();
        }
        return R.error().message("删除失败");
    }
}

