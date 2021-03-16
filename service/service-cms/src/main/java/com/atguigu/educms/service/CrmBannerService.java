package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author liugj
 * @since 2021-03-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 分页查询banner
     * @param pageParam
     * @param o
     */
    void pageBanner(Page<CrmBanner> pageParam, Object o);

    /**
     * 获取首页banner
     * @return
     */
    List<CrmBanner> selectIndexList();
}
