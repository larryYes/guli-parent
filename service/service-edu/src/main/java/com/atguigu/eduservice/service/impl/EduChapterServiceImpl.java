package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoTreeVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
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

    @Override
    public List<CourseInfoTreeVo>  getChapterVideo(String courseId) {

        List<CourseInfoTreeVo> courseTree = baseMapper.getAllChapterVideo(courseId);

        // 将所有菜单权限List数据转化为树形结构
        List<CourseInfoTreeVo> resp = new ArrayList<>();
        for (CourseInfoTreeVo courseInfoTreeVo : courseTree) {
            //获取一级目录,无父级菜单则parentId默认为零
            CourseInfoTreeVo authDto = new CourseInfoTreeVo();
            if (courseInfoTreeVo.getChapterId().equals("0")) {
                authDto.setChildren(getChildren(courseTree, courseInfoTreeVo.getId()));
                menuCopyProperties(authDto, courseInfoTreeVo);
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
            if (subMenu.getChapterId().equals(parentId)) {
                CourseInfoTreeVo menuTree = new CourseInfoTreeVo();
                menuTree.setChildren(getChildren(subList, subMenu.getId()));
                menuCopyProperties(menuTree, subMenu);
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
    public CourseInfoTreeVo menuCopyProperties(CourseInfoTreeVo dto, CourseInfoTreeVo subMenu) {
        dto.setId(subMenu.getId());
        dto.setTitle(subMenu.getTitle());
        dto.setChapterId(subMenu.getChapterId());
        return dto;
    }
}
