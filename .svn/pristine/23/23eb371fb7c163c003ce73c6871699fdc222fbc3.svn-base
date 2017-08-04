package com.zx.moa.ioa.noticeManage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.noticeManage.persist.NoticeListDao;
import com.zx.moa.ioa.noticeManage.service.INoticeListService;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;


/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: NoticeListServiceImpl
 * 模块名称：新闻公告管理接口实现类
 * @Description: 内容摘要：新闻公告管理接口实现类->查询新闻公告信息
 * @author suncf
 * @date 2016年12月29日
 * @version V1.0
 * history:
 * 1、2016年12月29日 suncf 创建文件
 */
@Service("noticeListService")
public class NoticeListServiceImpl implements INoticeListService
{

    @Autowired
    private NoticeListDao noticeListDao;
    
    /**
     * @Title: getNoticeList
     * @Description: TODO(查询新闻公告信息)
     * @param request
     * @return 
     * @author: suncf
     * @time:2016年12月29日 下午3:02:44
     * @see com.zx.moa.ioa.noticeManage.service.INoticeListService#getNoticeList(javax.servlet.http.HttpServletRequest)
     * history:
     * 1、2016年12月29日 suncf 创建方法
     */
    @Override
    public ResultBean<Map<String, Object>> getNoticeList(HttpServletRequest request)
    {
        Map<String, Object> map=new HashMap<>();
        List<Map<String, Object>> list = noticeListDao.getNoticeList(request);
        // String url1 = PlatformGlobalVar.SYS_PROPERTIES.get("moaUrl");
        // MOA专用
        String url1 = HttpClientUtil.getSysUrl("moaUrl");
        for (int i = 0; i < list.size(); i++)
        {
            String url2 = list.get(i).get("view_url").toString();
            list.get(i).put("view_url", url1 + url2);
        }
        map.put("noticeList", list);
        return ResultHelper.getSuccess(map);

    }

}
