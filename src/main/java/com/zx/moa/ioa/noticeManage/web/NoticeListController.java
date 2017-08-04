package com.zx.moa.ioa.noticeManage.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.noticeManage.service.INoticeListService;
import com.zx.moa.util.bean.ResultBean;


/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: NoticeListController
 * 模块名称：新闻公告管理控制层
 * @Description: 内容摘要：新闻公告管理控制层->查询新闻公告信息
 * @author suncf
 * @date 2016年12月29日
 * @version V1.0
 * history:
 * 1、2016年12月29日 suncf 创建文件
 */
@Controller
public class NoticeListController
{
    @Autowired
    private INoticeListService noticeListService;

    /**
     * @Title: getNoticeList
     * @Description: TODO(查询新闻公告信息)
     * @param request
     * @return 
     * @author: suncf
     * @time:2016年12月29日 下午3:01:54
     * history:
     * 1、2016年12月29日 suncf 创建方法
     */
    @RequestMapping(value = "/ioa/getNoticeList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getNoticeList(HttpServletRequest request)
    {
        return noticeListService.getNoticeList(request);
    }

}
