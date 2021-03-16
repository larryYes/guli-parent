package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.video.VideoInfoForm;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> query = new QueryWrapper<>();
        Integer result = baseMapper.selectCount(query.lambda().eq(EduVideo::getChapterId, chapterId));

        // 返回true：该章节下有子级
        return null != result && result > 0;
    }

    @Override
    public boolean saveVideo(VideoInfoForm video) {
        EduVideo videoDto = new EduVideo();
        BeanUtils.copyProperties(video,videoDto);
        return this.save(videoDto);
    }

    @Override
    public VideoInfoForm getVideoById(String id) {
        EduVideo video = this.getById(id);
        if(null==video){
            throw new GuliException(20001,"数据不存在");
        }
        VideoInfoForm videoInfo = new VideoInfoForm();
        BeanUtils.copyProperties(video,videoInfo);
        return videoInfo;
    }

    @Override
    public boolean updateVideo(VideoInfoForm videoInfo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfo,video);
        return this.updateById(video);
    }

    @Override
    public int removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EduVideo::getChapterId, courseId);
        return baseMapper.delete(queryWrapper);
    }

    @Override
    public int removeVideoById(String id) {

        //查询云端视频id
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();

        System.out.println("test string"+videoSourceId);
        //删除视频资源
        if(videoSourceId.length()>0){
            R result = vodClient.reVideo(videoSourceId);
            if(result.getCode()==40000){
                throw new GuliException(40000,"删除视频失败了,熔断器");
            }
        }
        // 删除课时
        return baseMapper.deleteById(id);
    }
}
