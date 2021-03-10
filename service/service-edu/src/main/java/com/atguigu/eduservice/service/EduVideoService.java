package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.video.VideoInfoForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
public interface EduVideoService extends IService<EduVideo> {
    /**
     * 根据章节Id查询是否有video
     *
     * @param chapterId
     * @return
     */
    boolean getCountByChapterId(String chapterId);

    boolean saveVideo(VideoInfoForm video);

    VideoInfoForm getVideoById(String id);

    boolean updateVideo(VideoInfoForm videoInfo);


    /**
     * 根据courseId删除video业务方法
     * @param id
     * @return
     */
    int removeVideoByCourseId(String id);
}
