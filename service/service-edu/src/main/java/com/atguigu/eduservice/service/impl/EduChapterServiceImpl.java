package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.CourseInfoTreeVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liugj
 * @since 2021-01-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Override
    public int add(EduChapter chapter) {
        if(StringUtils.isEmpty(chapter.getCourseId())){
            throw new GuliException(20001, "课程ID为空");
        }
        return baseMapper.insert(chapter);
    }

    @Override
    public boolean removeChapterById(String id) {
        if (videoService.getCountByChapterId(id)){
            throw new GuliException(20001,"该章节下存在视频课程，请先删除视频课程");
        }
        Integer result = baseMapper.deleteById(id);
        // 删除成功返回True
        return null!=result && result>0;
    }

    @Override
    public List<CourseInfoTreeVo>  getChapterVideo(String courseId) {

        // 获取所有章节和小节
        List<CourseInfoTreeVo> courseTree = baseMapper.getAllChapterVideo(courseId);

        // 将所有菜单权限List数据转化为树形结构
        List<CourseInfoTreeVo> resp = new ArrayList<>();
        for (CourseInfoTreeVo courseInfoTreeVo : courseTree) {
            //获取一级目录,无父级菜单则parentId默认为零
            CourseInfoTreeVo authDto = new CourseInfoTreeVo();
            // TODO:改为判断是否有上级目录，若否则作为顶级目录
            if (courseInfoTreeVo.getParentId().equals("0")) {
                authDto.setChildren(getChildren(courseTree, courseInfoTreeVo.getId()));
                menuCopyProperties(courseInfoTreeVo,authDto);
                resp.add(authDto);
            }
        }
        return resp;
    }

    /**
     * 递归查询
     * @param subList
     * @param parentId 上级id
     * @return
     */
    public List<CourseInfoTreeVo> getChildren(List<CourseInfoTreeVo> subList, String parentId) {
        // 获取下级标题
        List<CourseInfoTreeVo> subTree = new ArrayList<>();
        for (CourseInfoTreeVo subMenu : subList) {
            // 上级ID等于下级的parentID
            if (subMenu.getParentId().equals(parentId)) {
                CourseInfoTreeVo menuTree = new CourseInfoTreeVo();
                menuTree.setChildren(getChildren(subList, subMenu.getId()));
                menuCopyProperties(subMenu,menuTree);
                subTree.add(menuTree);
            }
        }
        return subTree;
    }

    /**
     * 实体数据复制到返回体中
     * @param dto
     * @param subMenu
     * @return
     */
    public CourseInfoTreeVo menuCopyProperties(CourseInfoTreeVo subMenu,CourseInfoTreeVo dto) {
        dto.setId(subMenu.getId());
        dto.setTitle(subMenu.getTitle());
        dto.setParentId(subMenu.getParentId());
        return dto;
    }
}
