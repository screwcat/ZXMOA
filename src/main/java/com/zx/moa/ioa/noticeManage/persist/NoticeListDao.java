package com.zx.moa.ioa.noticeManage.persist;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zx.moa.util.mybatis.MyBatisRepository;


/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: NoticeListDao
 * 模块名称：新闻公告管理Dao层
 * @Description: 内容摘要：查询新闻公告信息
 * @author suncf
 * @date 2016年12月29日
 * @version V1.0
 * history:
 * 1、2016年12月29日 suncf 创建文件
 */
@MyBatisRepository
public interface NoticeListDao
{
    /**
     * @Title: getNoticeList
     * @Description: TODO(查询新闻公告信息)
     * @param request
     * @return 
     * @author: suncf
     * @time:2016年12月29日 下午3:03:27
     * history:
     * 1、2016年12月29日 suncf 创建方法
     */
    public List<Map<String, Object>> getNoticeList(HttpServletRequest request);
}
