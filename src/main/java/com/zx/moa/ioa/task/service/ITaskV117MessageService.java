package com.zx.moa.ioa.task.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zx.moa.util.bean.ResultBean;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: ITaskV117ListService
 * 模块名称：管理分组信息接口
 * @Description: 内容摘要：查看分组信息
 * @author suncf
 * @date 2016年12月8日
 * @version V1.1.7
 * history:
 * 1、2016年12月8日 suncf 创建文件
 */
public interface ITaskV117MessageService
{

    /**
     * @Title: selectGroupInfoList
     * @Description: TODO(查看分组信息)
     * @param request
     * @return 成功/失败ResultBean(包含分组信息)
     * @author: suncf
     * @time:2016年12月10日 上午10:49:10
     * history:
     * 1、2016年12月10日 Administrator 创建方法
     */
    public ResultBean<Map<String, Object>> selectGroupInfoList(HttpServletRequest request);

}
