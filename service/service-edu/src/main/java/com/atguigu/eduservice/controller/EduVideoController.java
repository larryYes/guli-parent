package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.video.VideoInfoForm;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.internal.metadata.aggregated.rule.OverridingMethodMustNotAlterParameterConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
@Api(tags="课时管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "新增课时")
    @PostMapping()
    public R save(
            @ApiParam(name = "video",value = "课时视图",required = true)
            @RequestBody VideoInfoForm video
    ){
        return R.booleanJudge(videoService.saveVideo(video));
    }

    @ApiOperation(value = "根据Id查询课时")
    @GetMapping("/{id}")
    public R select(
            @ApiParam(name = "id",value = "课时Id")
            @PathVariable String id
    ){
        return R.ok().data("video",videoService.getVideoById(id));
    }

    @ApiOperation(value = "更新课时信息")
    @PutMapping()
    public R update(
            @ApiParam(name = "video",value = "课时视图",required = true)
            @RequestBody VideoInfoForm videoInfo
    ){
        return R.booleanJudge(videoService.updateVideo(videoInfo));
    }

    @ApiOperation(value = "根据Id删除课时")
    @DeleteMapping("/{id}")
    public R delete(
            @ApiParam(name = "id",value = "课时Id")
            @PathVariable String id
    ){
        return R.integerJudge(videoService.removeVideoById(id));
    }
}

