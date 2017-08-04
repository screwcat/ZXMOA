package com.zx.moa.ioa.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.task.persist.TaskV117MessageDao;
import com.zx.moa.ioa.task.service.ITaskV117MessageService;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: TaskV117MessageServiceImpl
 * 模块名称：管理分组信息接口实现类
 * @Description: 内容摘要：查看分组信息
 * @author suncf
 * @date 2016年12月8日
 * @version V1.1.7
 * history:
 * 1、2016年12月8日 suncf 创建文件
 */
@Service("taskV117MessageService")
public class TaskV117MessageServiceImpl implements ITaskV117MessageService
{
    @Autowired
    private TaskV117MessageDao taskV117MessageDao;

    /**
     * 
     * @Title: creatGroupInfo
     * @Description: TODO(查看分组信息)
     * @param request 
     * @author: suncf
     * @time:2016年12月8日 下午1:31:09
     * @see com.zx.moa.ioa.task.service.ITaskV117MessageService#creatGroupInfo(javax.servlet.http.HttpServletRequest)
     * history:
     * 1、2016年12月8日 suncf 创建方法
     */
    @Override
    public ResultBean<Map<String, Object>> selectGroupInfoList(HttpServletRequest request)
    {
        PmPersonnel pm = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", pm.getPersonnel_id());
        map.put("personnel_name", pm.getPersonnel_name());
        List<Map<String, Object>> list = taskV117MessageDao.selectGroupInfoList(map);
        map.put("groupList", list);
        return ResultHelper.getSuccess(map);
    }

}
