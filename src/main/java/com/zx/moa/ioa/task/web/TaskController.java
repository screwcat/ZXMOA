package com.zx.moa.ioa.task.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zx.moa.ioa.task.persist.TaskListDao;
import com.zx.moa.ioa.task.service.ITaskListService;
import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.ioa.task.service.impl.TaskListServiceImpl;
import com.zx.moa.ioa.task.vo.TaskListVO;
import com.zx.moa.ioa.task.vo.TaskMessageVO;
import com.zx.moa.ioa.task.vo.TaskVO;
import com.zx.moa.ioa.util.TaskButtonUtil;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.SysUtil;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: TaskController
 * 模块名称：任务Controller
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2016年12月9日
 * @version V1.7.7
 * history:
 * 1、2016年12月9日 zhaowei 创建文件
 */
@Controller
public class TaskController
{

    @Autowired
    private ITaskListService taskListService;

    @Autowired
    private ITaskMessageService taskMessageService;

    @Autowired
    private TaskListDao taskListDao;

    // 任务列表
    @RequestMapping(value = "/ioa/taskList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getTaskList(HttpServletRequest request, TaskListVO taskvo)
    {
        Map<String, Object> resmap = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        taskvo.setUser_id(pm.getPersonnel_id());
        // type:显示是否全部或是未完成,user_id:当前登录人id
        if (taskvo.getType() != null && taskvo.getPage() != null && taskvo.getSize() != null && taskvo.getUser_id() != null && taskvo.getPage() > 0)
        {
            // resmap.put("reslist", TaskListService.getTaskList(request,
            // taskvo));
            return ResultHelper.getSuccess(taskListService.getTaskList(request, taskvo));
        }
        else
        {
            // resmap.put("errorInfo", "请传入正确参数");
            return ResultHelper.getError("请传入正确参数", null);
        }

    }

    // 反馈页面及按钮判断
    @RequestMapping(value = "/ioa/getTaskMessageInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> feedbackList(@RequestParam(required = false)
    String v, HttpServletRequest request, TaskVO task)
    {
        if (task.getTask_id() != null)
        {
            Map<String, Object> result = null;
            try
            {
                if (v == null)
                {
                    result = taskListService.getTaskFeedbackList(request, task);

                }
                // 此功能支持最低版本,在此下边加else if

                else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.5") >= 0)
                {
                    result = taskListService.getTaskFeedbackListV115(request, task);
                }
                else
                {
                    result = taskListService.getTaskFeedbackList(request, task);
                }

                return ResultHelper.getSuccess(result);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return ResultHelper.getError(e.getMessage(), null);
            }
        }
        else
        {
            return ResultHelper.getError("请输入正确参数", null);
        }
    }

    // 发送反馈信息
    @RequestMapping(value = "/ioa/sendMessage.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> sendFeedbackInfo(HttpServletRequest request, TaskMessageVO message)
    {
        Map<String, Object> resmap = new HashMap<String, Object>();
        // 如果message.getMessage_info参数为空，反回提示信息
        if (StringUtil.isBlank(message.getMessage_info()))
        {
            ResultHelper.getError("消息内容不能为空", null);
        }
        if (message.getTask_id() != null && message.getMessage_info_type() != null && message.getMessage_info() != null)
        {

            Map<String, Object> valmap = taskListDao.getTaskBaseInfo(message.getTask_id());
            int task_status = Integer.parseInt(valmap.get("task_status").toString());
            if (task_status != 1 && task_status != 2)
            {
                return ResultHelper.getError("当前任务状态为进行中或者待验收才能反馈", null);
            }

            Map<String, Object> map = taskListService.sendTaskFeedbackList(request, message);
            if (map == null)
            {
                return ResultHelper.getError(null);
            }
            else
            {
                return ResultHelper.getSuccess(map);
            }
        }

        return ResultHelper.getError("请输入正确参数", null);
    }

    /**
     * 
     * @Title: CheckAndAgain
     * @Description: TODO(验收,重办,提前结束,撤销,完成)
     * @param request
     * @return 
     * @author: 张明建
     * @time:2016年12月15日 上午10:26:28
     * history:
     * 1、2016年12月15日 张明建 修改方法
     */
    @RequestMapping(value = "/ioa/dealTask.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> CheckAndAgain(HttpServletRequest request)
    {
        int task_id = Integer.parseInt(request.getParameter("task_id"));
        int type = Integer.parseInt(request.getParameter("type"));
        if (request.getParameter("task_id") != null && request.getParameter("type") != null)
        {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("task_id", task_id);
            paramsMap.put("type", type);

            Map<String, Object> resmap = taskListService.CheckAndAgain(request, paramsMap);
            if (!(boolean) resmap.get("flag"))
            {
                return ResultHelper.getError(resmap.get("message").toString(), null);
            }

            // 返回按钮
            int task_status = Integer.parseInt(resmap.get("task_status").toString());
            String is_feedback = resmap.get("is_feedback").toString();
            int user = Integer.parseInt(resmap.get("user").toString());
            boolean isdeyal = (boolean) resmap.get("isdeyal");
            List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdeyal);
            resmap.put("buttons", buttons);
            resmap.put("task_status", task_status);
            resmap.remove("user");
            resmap.remove("is_feedback");
            return ResultHelper.getSuccess(resmap);
        }

        return ResultHelper.getError("请输入正确参数", null);
    }

    // 申请延期和确认延期
    @RequestMapping(value = "/ioa/dealTaskFinishTime.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> dealTaskFinishTime(HttpServletRequest request)
    {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        Map<String, Object> resmap = new HashMap<String, Object>();
        String finish_time = request.getParameter("finish_time");
        int task_id = Integer.parseInt(request.getParameter("task_id"));
        int type = Integer.parseInt(request.getParameter("type"));
        // 验证-申请延期的时间不能小于任务的截止时间
        if (type == 1)
        {
            Map<String, Object> valmap = taskListDao.getTaskBaseInfo(task_id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try
            {
                Date old_fintime = sdf.parse(valmap.get("finish_time").toString());
                Date new_fintime = sdf.parse(finish_time);
                boolean flag = old_fintime.after(new_fintime);
                if (flag)
                {
                    return ResultHelper.getError("申请的时间不能小于任务截止时间！", null);
                }
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (request.getParameter("finish_time") != null && request.getParameter("task_id") != null && request.getParameter("type") != null)
        {
            paramsMap.put("finish_time", finish_time);
            paramsMap.put("task_id", task_id);
            paramsMap.put("type", type);
            resmap = taskListService.dealTaskFinishTime(request, paramsMap);
            if (!(boolean) resmap.get("flag"))
            {
                return ResultHelper.getError(resmap.get("message").toString(), null);
            }
            else
            {
                // 返回按钮
                int task_status = Integer.parseInt(resmap.get("task_status").toString());
                String is_feedback = resmap.get("is_feedback").toString();
                int user = Integer.parseInt(resmap.get("user").toString());
                boolean isdelay = (boolean) resmap.get("isdelay");
                List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);
                resmap.put("buttons", buttons);
                return ResultHelper.getSuccess(resmap);
            }

        }

        return ResultHelper.getError("请输入正确参数", null);
    }

    // 协同，转移任务
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ioa/changeTaskFeedback.do", method = { RequestMethod.POST })
    @ResponseBody
    public synchronized ResultBean<Map<String, Object>> changeTaskFeedback(HttpServletRequest request) throws NumberFormatException, Exception
    {
        String version = request.getParameter("v");
        if (null == version)
        {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            Map<String, Object> resmap = new HashMap<String, Object>();
            if (request.getParameter("task_id") != null && request.getParameter("type") != null && request.getParameter("feedback_usres") != null)
            {
                int task_id = Integer.parseInt(request.getParameter("task_id"));
                int type = Integer.parseInt(request.getParameter("type"));

                String fb = request.getParameter("feedback_usres");
                List<Map<String, Object>> feedback_usres = TaskListServiceImpl.readValue(fb, List.class);
                paramsMap.put("task_id", task_id);
                paramsMap.put("type", type);
                paramsMap.put("feedback_usres", feedback_usres);

                // 开始验证
                Map<String, Object> valmap = taskListDao.getTaskBaseInfo(task_id);

                // 验证 发布人和反馈人不能为同一人
                int accept_feedback_id = Integer.parseInt(valmap.get("accept_feedback_id").toString());
                for (int i = 0; i < feedback_usres.size(); i++)
                {
                    int valid = Integer.parseInt(feedback_usres.get(i).get("feedback_user_id").toString());
                    if (accept_feedback_id == valid)
                    {
                        return ResultHelper.getError("被反馈人和反馈人不能为同一个人", null);
                    }

                }

                // 转移或者协同时，已选择的人不能有相关的转移/协同任务
                // if(type == 2){
                Map<String, Object> norepMap = new HashMap<String, Object>();
                List<Map<String, Object>> oldperlist = new ArrayList<Map<String, Object>>();
                norepMap.put("task_num", valmap.get("task_num").toString());
                List<Map<String, Object>> tidlist = taskListDao.getTaskByNum(norepMap);

                for (int i = 0; i < tidlist.size(); i++)
                {
                    // 根据当前任务已协同的任务id查询反馈人的id
                    int xtid = Integer.parseInt(tidlist.get(i).get("task_id").toString());
                    oldperlist.addAll(taskListDao.getfeedbackUserBytaskid(xtid));
                }
                // 判断是否人员是否重复
                String repeatName = "";

                for (int i = 0; i < oldperlist.size() - 1; i++)
                {
                    if (oldperlist.get(i).get("feedback_user_id").toString().equals(oldperlist.get(i + 1).get("feedback_user_id").toString()))
                    {
                        oldperlist.remove(oldperlist.get(i + 1));
                    }
                    ;
                }

                for (int i = 0; i < oldperlist.size(); i++)
                {
                    for (int j = 0; j < feedback_usres.size(); j++)
                    {
                        int oldperid = Integer.parseInt(oldperlist.get(i).get("feedback_user_id").toString());
                        int paramsid = Integer.parseInt(feedback_usres.get(j).get("feedback_user_id").toString());
                        if (oldperid == paramsid)
                        {
                            repeatName = repeatName + feedback_usres.get(j).get("feedback_user_name") + ",";
                            if ((i + 1) == oldperlist.size())
                            {
                                String resname = "重复选择的人为:" + repeatName;
                                String newReslutName = resname.substring(0, resname.length() - 1) + "。";
                                return ResultHelper.getError(newReslutName, null);
                            }
                        }
                    }
                }

                // }

                // 验证协同转移人不能为反馈人自己
                int feedback_user_id = Integer.parseInt(valmap.get("feedback_user_id").toString());
                for (int i = 0; i < feedback_usres.size(); i++)
                {
                    int valid = Integer.parseInt(feedback_usres.get(i).get("feedback_user_id").toString());
                    if (feedback_user_id == valid)
                    {
                        return ResultHelper.getError("您选择的人已经在执行此任务了！", null);
                    }
                }

                // 被协同转移人不能为任务发布人

                // 根据num取pid直到为0的任务
                int task_pid = Integer.parseInt(paramsMap.get("task_id").toString());
                Map<String, Object> pinjiemap = new HashMap<String, Object>();
                if (!(task_pid == 0))
                {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    while (true)
                    {
                        map1 = taskListDao.getTaskBaseInfo(task_pid);
                        task_pid = (int) map1.get("task_pid");
                        if (task_pid == 0)
                        {
                            break;
                        }
                    }
                    pinjiemap.put("task_id", map1.get("task_id").toString());
                }
                else
                {
                    pinjiemap.put("task_id", Integer.parseInt(paramsMap.get("task_id").toString()));

                }

                List<Map<String, Object>> pinjielist = taskListDao.getQuondamFeedbackUser(paramsMap);
                int publish_user_id = Integer.parseInt(pinjielist.get(0).get("publish_user_id").toString());
                for (int i = 0; i < feedback_usres.size(); i++)
                {
                    int valid = Integer.parseInt(feedback_usres.get(i).get("feedback_user_id").toString());
                    if (feedback_user_id == publish_user_id)
                    {
                        return ResultHelper.getError("您选择的人不能为任务的发布人！", null);
                    }
                }
                // 验证结束

                resmap = taskListService.changeTaskFeedback(request, paramsMap);
                if (!(boolean) resmap.get("flag"))
                {
                    return ResultHelper.getError(resmap.get("message").toString(), null);
                }
                else
                {
                    // 返回按钮
                    if (type == 1)
                    {

                        int task_status = Integer.parseInt(resmap.get("task_status").toString());
                        String is_feedback = resmap.get("is_feedback").toString();
                        int user = Integer.parseInt(resmap.get("user").toString());
                        boolean isdelay = (boolean) resmap.get("isdelay");
                        List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);
                        resmap.put("buttons", buttons);
                        resmap.remove("resButtonList");
                        return ResultHelper.getSuccess(resmap);
                    }
                    else if (type == 2)
                    {

                        int task_status = Integer.parseInt(resmap.get("task_status").toString());
                        int user = Integer.parseInt(resmap.get("user").toString());
                        String is_feedback = resmap.get("is_feedback").toString();
                        boolean isdelay = (boolean) resmap.get("isdelay");
                        List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);

                        resmap.put("buttons", buttons);
                        return ResultHelper.getSuccess(resmap);
                    }
                    else if (type == 3)
                    {
                        int task_status = Integer.parseInt(resmap.get("task_status").toString());
                        int user = Integer.parseInt(resmap.get("user").toString());
                        String is_feedback = resmap.get("is_feedback").toString();
                        boolean isdelay = (boolean) resmap.get("isdelay");
                        List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);

                        resmap.put("buttons", buttons);
                        return ResultHelper.getSuccess(resmap);
                    }

                }

            }

            return ResultHelper.getError("请输入正确参数", null);

        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(version, "1.1.7") >= 0)
        {
            // -----------------1.1.7新版本---------------------------------------------

            Map<String, Object> paramsMap = new HashMap<String, Object>();
            Map<String, Object> resmap = new HashMap<String, Object>();
            if (request.getParameter("task_id") != null && request.getParameter("type") != null && request.getParameter("feedback_usres") != null && request.getParameter("task_type_id") != null && request.getParameter("task_type_name") != null)
            {
                int task_id = Integer.parseInt(request.getParameter("task_id"));
                int type = Integer.parseInt(request.getParameter("type"));
                int task_type_id = Integer.parseInt(request.getParameter("task_type_id"));
                String task_type_name = request.getParameter("task_type_name").toString();
                String fb = request.getParameter("feedback_usres");
                List<Map<String, Object>> feedback_usres = TaskListServiceImpl.readValue(fb, List.class);
                paramsMap.put("task_id", task_id);
                paramsMap.put("type", type);
                paramsMap.put("feedback_usres", feedback_usres);
                paramsMap.put("task_type_id", task_type_id);
                paramsMap.put("task_type_name", task_type_name);
                // 开始验证
                Map<String, Object> valmap = taskListDao.getTaskBaseInfo(task_id);

                // 验证 发布人和反馈人不能为同一人
                int accept_feedback_id = Integer.parseInt(valmap.get("accept_feedback_id").toString());
                for (int i = 0; i < feedback_usres.size(); i++)
                {
                    int valid = Integer.parseInt(feedback_usres.get(i).get("feedback_user_id").toString());
                    if (accept_feedback_id == valid)
                    {
                        return ResultHelper.getError("被反馈人和反馈人不能为同一个人", null);
                    }

                }

                // 转移或者协同时，已选择的人不能有相关的转移/协同任务
                // if(type == 2){
                Map<String, Object> norepMap = new HashMap<String, Object>();
                List<Map<String, Object>> oldperlist = new ArrayList<Map<String, Object>>();
                norepMap.put("task_num", valmap.get("task_num").toString());
                List<Map<String, Object>> tidlist = taskListDao.getTaskByNum(norepMap);

                for (int i = 0; i < tidlist.size(); i++)
                {
                    // 根据当前任务已协同的任务id查询反馈人的id
                    int xtid = Integer.parseInt(tidlist.get(i).get("task_id").toString());
                    oldperlist.addAll(taskListDao.getfeedbackUserBytaskid(xtid));
                }
                // 判断是否人员是否重复
                String repeatName = "";

                for (int i = 0; i < oldperlist.size() - 1; i++)
                {
                    if (oldperlist.get(i).get("feedback_user_id").toString().equals(oldperlist.get(i + 1).get("feedback_user_id").toString()))
                    {
                        oldperlist.remove(oldperlist.get(i + 1));
                    }
                    ;
                }

                for (int i = 0; i < oldperlist.size(); i++)
                {
                    for (int j = 0; j < feedback_usres.size(); j++)
                    {
                        int oldperid = Integer.parseInt(oldperlist.get(i).get("feedback_user_id").toString());
                        int paramsid = Integer.parseInt(feedback_usres.get(j).get("feedback_user_id").toString());
                        if (oldperid == paramsid)
                        {
                            repeatName = repeatName + feedback_usres.get(j).get("feedback_user_name") + ",";
                            if ((i + 1) == oldperlist.size())
                            {
                                String resname = "重复选择的人为:" + repeatName;
                                String newReslutName = resname.substring(0, resname.length() - 1) + "。";
                                return ResultHelper.getError(newReslutName, null);
                            }
                        }
                    }
                }

                // }

                // 验证协同转移人不能为反馈人自己
                int feedback_user_id = Integer.parseInt(valmap.get("feedback_user_id").toString());
                for (int i = 0; i < feedback_usres.size(); i++)
                {
                    int valid = Integer.parseInt(feedback_usres.get(i).get("feedback_user_id").toString());
                    if (feedback_user_id == valid)
                    {
                        return ResultHelper.getError("您选择的人已经在执行此任务了！", null);
                    }
                }

                // 被协同转移人不能为任务发布人

                // 根据num取pid直到为0的任务
                int task_pid = Integer.parseInt(paramsMap.get("task_id").toString());
                Map<String, Object> pinjiemap = new HashMap<String, Object>();
                if (!(task_pid == 0))
                {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    while (true)
                    {
                        map1 = taskListDao.getTaskBaseInfo(task_pid);
                        task_pid = (int) map1.get("task_pid");
                        if (task_pid == 0)
                        {
                            break;
                        }
                    }
                    pinjiemap.put("task_id", map1.get("task_id").toString());
                }
                else
                {
                    pinjiemap.put("task_id", Integer.parseInt(paramsMap.get("task_id").toString()));

                }

                List<Map<String, Object>> pinjielist = taskListDao.getQuondamFeedbackUser(paramsMap);
                int publish_user_id = Integer.parseInt(pinjielist.get(0).get("publish_user_id").toString());
                for (int i = 0; i < feedback_usres.size(); i++)
                {
                    int valid = Integer.parseInt(feedback_usres.get(i).get("feedback_user_id").toString());
                    if (feedback_user_id == publish_user_id)
                    {
                        return ResultHelper.getError("您选择的人不能为任务的发布人！", null);
                    }
                }
                // 验证结束

                resmap = taskListService.changeTaskFeedback(request, paramsMap);
                if (!(boolean) resmap.get("flag"))
                {
                    return ResultHelper.getError(resmap.get("message").toString(), null);
                }
                else
                {
                    // 返回按钮
                    if (type == 1)
                    {

                        int task_status = Integer.parseInt(resmap.get("task_status").toString());
                        String is_feedback = resmap.get("is_feedback").toString();
                        int user = Integer.parseInt(resmap.get("user").toString());
                        boolean isdelay = (boolean) resmap.get("isdelay");
                        List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);
                        resmap.put("buttons", buttons);
                        resmap.remove("resButtonList");
                        return ResultHelper.getSuccess(resmap);
                    }
                    else if (type == 2)
                    {

                        int task_status = Integer.parseInt(resmap.get("task_status").toString());
                        int user = Integer.parseInt(resmap.get("user").toString());
                        String is_feedback = resmap.get("is_feedback").toString();
                        boolean isdelay = (boolean) resmap.get("isdelay");
                        List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);

                        resmap.put("buttons", buttons);
                        return ResultHelper.getSuccess(resmap);
                    }
                    else if (type == 3)
                    {
                        int task_status = Integer.parseInt(resmap.get("task_status").toString());
                        int user = Integer.parseInt(resmap.get("user").toString());
                        String is_feedback = resmap.get("is_feedback").toString();
                        boolean isdelay = (boolean) resmap.get("isdelay");
                        List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback, isdelay);

                        resmap.put("buttons", buttons);
                        return ResultHelper.getSuccess(resmap);
                    }

                }

            }

            return ResultHelper.getError("请输入正确参数", null);

            // -----------------1.1.7新版本---------------------------------------------
        }

        return null;
    }

    @RequestMapping(value = "ioa/taskMessageRead.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> taskMessageRead(HttpServletRequest request)
    {
        PmPersonnel personnel = SysUtil.getLoginUser(request);
        String task_id = request.getParameter("task_id");
        String task_message_id = request.getParameter("task_message_id");
        if (StringUtil.isBlank(task_id) && StringUtil.isBlank(task_message_id))
        {
            return ResultHelper.getError("任务ID和消息ID不能都为空", null);
        }
        try
        {
            return taskMessageService.taskMessageRead(task_id, task_message_id, personnel.getPersonnel_id());
        }
        catch (Exception e)
        {
            return ResultHelper.getError("任务ID或消息ID错误", null);
        }
    }

    @RequestMapping(value = "ioa/taskListIsOnly.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> taskListIsOnly(HttpServletRequest request)
    {
        PmPersonnel personnel = SysUtil.getLoginUser(request);
        return ResultHelper.getSuccess(taskMessageService.taskListIsOnly(personnel.getPersonnel_id()));
    }

    /**
     * 
     * @Title: getTaskListFirst
     * @Description: TODO(MOA_OUT_34任务列表（首次进入）)
     * @param request
     * @param attr
     * @return 
     * @throws Exception 
     * @author: sunlq
     * @time:2016年12月8日 下午1:38:41
     * history:
     * 1、2016年12月8日 zhangingjian 修改方法
     */
    @RequestMapping(value = "/ioa/taskListFirst.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getTaskListFirst(HttpServletRequest request, RedirectAttributes attr) throws Exception
    {

        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        Integer personnel_id = pm.getPersonnel_id();

        Integer size = request.getParameter("size") == null ? 10 : Integer.parseInt(request.getParameter("size"));
        // 获取版本号
        String v = request.getParameter("v");
        TaskListVO taskvo = null;
        // 如果v是空就按原来版本代码执行，如果有版本号就执行最新版本号代码
        if (StringUtils.isBlank(v))
        {
            taskvo = new TaskListVO();
            taskvo.setPersonnel_id(personnel_id);
            // 判断数量方法
            Integer which_tab = taskListService.which_tab(taskvo);
            taskvo.setWhich_tab(which_tab);
            taskvo.setPage(1);
            taskvo.setSize(size);
            taskvo.setType("2");
            return getTaskListSearch(request, taskvo);
        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.7") >= 0)
        {
            taskvo = new TaskListVO();
            taskvo.setPersonnel_id(personnel_id);
            Integer which_tab = taskListService.which_tab(taskvo);
            Map<String, Object> map117 = new HashMap<>();
            map117.put("which_tab", which_tab);
            return ResultHelper.getSuccess(map117);
        }
        return null;
    }

    // MOA_OUT_35任务列表(区分需反馈和已指派任务)
    @RequestMapping(value = "/ioa/taskListSearch.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getTaskListSearch(HttpServletRequest request, TaskListVO taskvo) throws Exception
    {
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        // 获取版本号
        String v = request.getParameter("v");
        Integer personnel_id = pm.getPersonnel_id();
        taskvo.setPersonnel_id(personnel_id);
        taskvo.setUser_id(personnel_id);
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(v))
        {
            taskvo.setVersion_number(1);
            result = countInfo(taskvo);
            result.put("ret_data", taskListService.getTaskList(request, taskvo));
            result.put("which_tab", taskvo.getWhich_tab());
            return ResultHelper.getSuccess(result);
        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.7") >= 0)
        {
            // 版本标识
            taskvo.setVersion_number(2);
            result.put("ret_data", taskListService.getTaskList(request, taskvo));
            result.put("which_tab", taskvo.getWhich_tab());
            return ResultHelper.getSuccess(result);
        }

        return null;
    }

    /**
     * 
    * @Title: countInfo 
    * @Description: 统计任务进行中、延误数量、待验收的数量
    * @return    
    * Map<String,Object>     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 下午2:30:28
     */
    private Map<String, Object> countInfo(TaskListVO taskvo)
    {
        Map<String, Object> resultmap = new HashMap<>();
        // 进行中数量
        Integer doing_num = taskListService.doing_num(taskvo);
        // 延误数量
        Integer delay_num = taskListService.delay_num(taskvo);
        // 待验收数量
        Integer check_num = taskListService.check_num(taskvo);

        resultmap.put("doing_num", doing_num);
        resultmap.put("delay_num", delay_num);
        resultmap.put("check_num", check_num);

        return resultmap;
    }

    // 任务列表
    /**
     * @Title: taskIndexListSearch
     * @Description: 首页待办任务列表
     * @param request
     * @param taskvo
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 上午9:17:37
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    @RequestMapping(value = "ioa/taskIndexListSearch.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> taskIndexListSearch(HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> m = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        map.put("user_id", pm.getPersonnel_id());
        map.put("personnel_id", pm.getPersonnel_id());
        String page = request.getParameter("page");
        String size = request.getParameter("size");
        if (StringUtils.isNotBlank(page))
        {
            map.put("page", Integer.parseInt(page));
        }
        if (StringUtils.isNotBlank(size))
        {
            map.put("size", Integer.parseInt(size));
        }
        // 需反馈进行中的任务 (反馈人是当前登录人=需反馈)
        if (page != null && size != null)
        {
            List<Map<String, Object>> list = taskListService.taskIndexListSearch(request, map);
            int task_total_number = taskListService.taskIndexListSearchCount(map);
            for (Map<String, Object> map2 : list)
            {
                map2.put("which_tab", 1);
            }
            m.put("applyList", list);
            m.put("task_total_number", task_total_number);
            return ResultHelper.getSuccess(m);
        }
        else
        {
            return ResultHelper.getError("请输入正确参数", null);
        }
    }
}
