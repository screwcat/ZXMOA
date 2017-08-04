package com.zx.moa.ioa.task.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zx.moa.ioa.task.service.ITaskV117MessageService;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: TaskV117Controller
 * 模块名称：分组信息处理
 * @Description: 内容摘要：分组信息(增加、修改、删除)页面的跳转，发送modi请求，查看分组信息
 * @author suncf
 * @date 2016年12月8日
 * @version V1.1.7
 * history:
 * 1、2016年12月8日 suncf 创建文件
 */
@Controller
public class TaskV117Controller
{
    @Autowired
    private ITaskV117MessageService taskV117MessageService;
    
    private HttpServletRequest request = null;
    
    /**
     * 
     * @Title: creatGroupInfo
     * @Description: TODO(发送modi请求)
     * @param request 从前台传入的数据
     * @return ioa处理后的信息是否成功
     * @author: suncf
     * @time:2016年12月8日 下午1:16:58
     * history:
     * 1、2016年12月8日 suncf 创建方法
     */
    private Map<String, Object> getModiConnection(String interfaceNumber, Map<String, Object> map) throws ClientProtocolException
    {

        String url = HttpClientUtil.getSysUrl("nozzleUrl");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", interfaceNumber));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        String releaseTask = JSONObject.toJSONString(map);
        nvps.add(new BasicNameValuePair("releaseTask", releaseTask));
        if (map.get("create_user_id") != null)
        {
            nvps.add(new BasicNameValuePair("user_id", map.get("create_user_id").toString()));
        }
        else
        {
            nvps.add(new BasicNameValuePair("user_id", "0"));
        }

        Map<String, Object> resmap = new HashMap<String, Object>();
        try
        {
            resmap = HttpClientUtil.post(url, nvps, Map.class);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            resmap.put("message", e.getMessage());
        }
        return resmap;
    }

    // json处理
    String jsonstr(String str)
    {
        str = str.replace("\n", "");
        str = str.replace("\\n", "");
        return str;
    }
    
    /**
     * @Title: manageGroupInfo
     * @Description: TODO(创建或修改分组信息)
     * @param request 从前台传入数据
     * @return 成功/失败ResultBean
     * @author: suncf
     * @time:2016年12月9日 下午2:49:07
     * history:
     * 1、2016年12月9日 suncf 创建方法
     */
    @RequestMapping(value = "/ioa/createGroupInfo.do", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> manageGroupInfo(HttpServletRequest request)
    {
        PmPersonnel pm = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String group_id = request.getParameter("group_id");
        String group_name=request.getParameter("group_name");
        String personnelList=request.getParameter("personnelList");
        personnelList = jsonstr(personnelList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("group_id", group_id);
        map.put("group_name", group_name);
        map.put("personnelList", personnelList);
        map.put("create_user_id", pm.getPersonnel_id());
        map.put("create_user_name", pm.getPersonnel_name() + pm.getPersonnel_shortcode());
        String message = "";
        try
        {
            Map<String, Object> resmap = getModiConnection("IOA_OUT_013", map);
            Boolean bl = (Boolean) resmap.get("flag");

            if (bl)
            {
                return ResultHelper.getSuccess(null);
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
            message = e.getMessage();
        }
        ResultBean<Map<String, Object>> re = new ResultBean<Map<String, Object>>();
        re.setRet_msg(message);
        return ResultHelper.getError(null);
    }

    /**
     * @Title: deleteGroupInfo
     * @Description: TODO(删除分组信息)
     * @param request 从前台入的分组ID
     * @return 成功/失败ResultBean
     * @author: suncf
     * @time:2016年12月10日 上午10:45:28
     * history:
     * 1、2016年12月10日 suncf 创建方法
     */
    @RequestMapping(value = "/ioa/deleteGroupInfo.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> deleteGroupInfo(HttpServletRequest request)
    {
        PmPersonnel pm = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String group_id = request.getParameter("group_id");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("group_id", group_id);
        String message = "";
        try
        {
            Map<String, Object> resmap = getModiConnection("IOA_OUT_014", map);
            Boolean bl = (Boolean) resmap.get("flag");

            if (bl)
            {
                return ResultHelper.getSuccess(null);
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
            message = e.getMessage();
        }
        ResultBean<Map<String, Object>> re = new ResultBean<Map<String, Object>>();
        re.setRet_msg(message);
        return ResultHelper.getError(null);
    }

    /**
     * @Title: selectGroupInfoList
     * @Description: TODO(查看分组信息)
     * @param request
     * @return 成功/失败ResultBean(包含分组信息)
     * @author: suncf
     * @time:2016年12月10日 上午10:47:30
     * history:
     * 1、2016年12月10日 suncf 创建方法
     */
    @RequestMapping(value = "/ioa/selectGroupInfoList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> selectGroupInfoList(HttpServletRequest request)
    {
        return taskV117MessageService.selectGroupInfoList(request);
    }

}
