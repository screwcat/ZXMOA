package com.zx.moa.ioa.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.task.persist.TaskMessageDao;
import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.ioa.util.PushManage;
import com.zx.moa.ioa.util.QuartzJobFactory;
import com.zx.moa.ioa.util.QuartzJobManage;
import com.zx.moa.ioa.util.ScheduleJob;
import com.zx.moa.util.DateUtil;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.SMSUtil;
import com.zx.moa.util.bean.ResultBean;
import com.zx.platform.syscontext.util.StringUtil;

@Service("taskMessageService")
public class TaskMessageServiceImpl implements ITaskMessageService
{

    @Autowired
    private TaskMessageDao taskMessageDao;

    @Autowired
    private QuartzJobManage quartzJobManage;

    @SuppressWarnings("unchecked")
    @Override
    public ResultBean<Map<String, Object>> taskMessageRead(String task_id, String task_message_id, Integer personnel_id)
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_011"));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        nvps.add(new BasicNameValuePair("user_id", "0"));
        nvps.add(new BasicNameValuePair("personnel_id", personnel_id.toString()));
        if (StringUtil.isNotBlank(task_id))
        {
            nvps.add(new BasicNameValuePair("task_id", task_id));
        }
        if (StringUtil.isNotBlank(task_message_id))
        {
            nvps.add(new BasicNameValuePair("task_message_id", task_message_id));
        }
        try
        {
            String url = HttpClientUtil.getSysUrl("nozzleUrl");
            Map<String, Object> result = HttpClientUtil.post(url, nvps, Map.class);
            // Map<String, Object> result =
            // HttpClientUtil.post("http://127.0.0.1:8080/IOA/moa/taskMessageRead.do",
            // nvps, Map.class);
            Boolean flag = (Boolean) result.get("flag");
            if (flag == null || !flag)
            {
                return ResultHelper.getError((String) result.get("message"), null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResultHelper.getSuccess(null);
    }

    /**
     * @Title: unFreeback20
     * @Description: 20点发通知和短信
     * @return 
     * @author: sunlq
     * @time:2017年4月13日 下午4:26:39
     * @see com.zx.moa.ioa.task.service.ITaskMessageService#unFreeback20()
     * history:
     * 1、2017年4月13日 sunlq 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer unFreeback20()
    {
        List<Map<String, Object>> list = taskMessageDao.unFeedback();
        Integer count = 0;
        if (list != null)
        {
            int weekday = DateUtil.getDayOfWeek(null);
            // 周一到周五发通知和短信
            if (weekday >= 1 && weekday <= 5)
            {
                Map<String, Object> usres = getAppUserRecord(PushConstant.TASK_APP_NAME);
                for (Map<String, Object> map : list)
                {
                    Map<String, Object> extras = new HashMap<String, Object>();
                    extras.put("task_id", map.get("task_id"));
                    String accept_feedback_code = (String) map.get("accept_feedback_code");
                    Map<String, Object> publis = (Map<String, Object>) usres.get(accept_feedback_code);
                    Map<String, Object> msgMap = new HashMap<String, Object>();
                    msgMap.put("feedback_user_name", (String) map.get("feedback_user_name") + (String) map.get("feedback_user_code"));
                    if (publis != null)
                    {
                        try
                        {
                            PushManage.pushMessage((String) publis.get("alias"), PushConstant.TASK_PUBLISH_UNFEEDBACK_20, msgMap, extras);
                            count++;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    /**
                     * 判断被反馈人职务是否副总以上发短信
                     */
                    int accept_feedback_id = Integer.parseInt(map.get("accept_feedback_id").toString());
                    List<Integer> listInt = taskMessageDao.selPostIdById(accept_feedback_id);
                    if (listInt != null && listInt.size() > 0)
                    {
                        try
                        {
                            SMSUtil.sendUnfeedbackSMS(accept_feedback_code, PushConstant.SMS_PUBLISH_UNFEEDBACK_20, msgMap);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    String feedback_user_code = (String) map.get("feedback_user_code");
                    Map<String, Object> feedback = (Map<String, Object>) usres.get(feedback_user_code);
                    if (feedback != null)
                    {
                        try
                        {
                            PushManage.pushMessage((String) feedback.get("alias"), PushConstant.TASK_FEEDBACK_UNFEEDBACK_20, null, extras);
                            count++;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    /**
                     * 判断反馈人职务是否副总以上发短信
                     */
                    int feedback_user_id = Integer.parseInt(map.get("feedback_user_id").toString());
                    List<Integer> li = taskMessageDao.selPostIdById(feedback_user_id);
                    if (li != null && li.size() > 0)
                    {
                        try
                        {
                            SMSUtil.sendUnfeedbackSMS(feedback_user_code, PushConstant.SMS_FEEDBACK_UNFEEDBACK_20, null);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * @Title: unFreeback17
     * @Description: 17点发通知和短信
     * @return 
     * @author: sunlq
     * @time:2017年4月13日 下午4:27:02
     * @see com.zx.moa.ioa.task.service.ITaskMessageService#unFreeback17()
     * history:
     * 1、2017年4月13日 sunlq 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer unFreeback17()
    {
        List<Map<String, Object>> list = taskMessageDao.unFeedback();
        Integer count = 0;
        if (list != null)
        {
            int weekday = DateUtil.getDayOfWeek(null);
            // 周一到周五发通知和短信
            if (weekday >= 1 && weekday <= 5)
            {
                Map<String, Object> usres = getAppUserRecord(PushConstant.TASK_APP_NAME);
                for (Map<String, Object> map : list)
                {
                    Map<String, Object> extras = new HashMap<String, Object>();
                    extras.put("task_id", map.get("task_id"));
                    String feedback_user_code = (String) map.get("feedback_user_code");
                    Map<String, Object> feedback = (Map<String, Object>) usres.get(feedback_user_code);
                    if (feedback != null)
                    {
                        try
                        {
                            PushManage.pushMessage((String) feedback.get("alias"), PushConstant.TASK_FEEDBACK_UNFEEDBACK_17, null, extras);
                            count++;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    // 判断反馈人职务是否副总以上发短信
                    /* int feedback_user_id = Integer.parseInt(map.get("feedback_user_id").toString());
                     if (taskMessageDao.selPostIdById(feedback_user_id) != null)
                     {
                         try
                         {
                             SMSUtil.sendUnfeedbackSMS(feedback_user_code, PushConstant.SMS_FEEDBACK_UNFEEDBACK_17, null);
                         }
                         catch (Exception e)
                         {
                             e.printStackTrace();
                         }
                     }*/

                }
            }
        }
        return count;
    }

    @Override
    public Map<String, Object> getAppUserRecord(String appName)
    {

        List<Map<String, Object>> list = taskMessageDao.getAppUserRecord(appName);
        Map<String, Object> result = new HashMap<String, Object>();
        if (list != null)
        {
            for (Map<String, Object> map : list)
            {
                result.put((String) map.get("user_code"), map);
            }
        }
        return result;
    }

    @Override
    public void taskUnfinishedRemind(Map<String, Object> map)
    {
        String remind_time = (String) map.get("remind_time");
        String finish_time = (String) map.get("finish_time");
        if (finish_time.length() <= 16)
        {
            finish_time += ":00";
        }
        if ("-1".equals(remind_time))
        {
            return;
        }
        Integer task_id = (Integer) map.get("task_id");
        if (StringUtil.isNotBlank(finish_time) && StringUtil.isNotBlank(remind_time))
        {
            // PushMessage message = new PushMessage();
            // message.message.msg_content =
            // PushManage.getContMsg(PushConstant.TASK_FEEDBACK_END, map);
            ScheduleJob job = new ScheduleJob();
            job.setJobGroup("taskUnfinishedRemind");
            job.setDesc("任务未完成提醒");
            job.setJobId(task_id.toString());
            job.setJobName(task_id.toString());
            String cron = QuartzJobManage.CronExpressionHelper(remind_time, DateUtil.strDate(finish_time, DateUtil.DEFAULT_FORMAT_TIMESTAMP));
            if (StringUtil.isBlank(cron))
            {
                return;
            }
            job.setCronExpression(cron);
            job.setRunjob(QuartzJobFactory.getTaskUnfinishedJob(task_id));
            try
            {
                quartzJobManage.createJob(job);
            }
            catch (SchedulerException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Title: taskEndState
     * @Description: 删除定时任务，包括（提前提醒和自动验收）
     * @param map 
     * @author: sunlq
     * @time:2016年12月10日 上午10:37:56
     * @see com.zx.moa.ioa.task.service.ITaskMessageService#taskEndState(java.util.Map)
     * history:
     * 1、2016年12月10日 sunlq 创建方法
     */
    @Override
    public void taskEndState(Map<String, Object> map)
    {
        Integer task_id = (Integer) map.get("task_id");
        try
        {
            ScheduleJob job = new ScheduleJob();
            job.setJobGroup("taskUnfinishedRemind");
            job.setDesc("任务结束提醒");
            job.setJobId(task_id.toString());
            job.setJobName(task_id.toString());
            quartzJobManage.deleteJob(job);
        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }
        try
        {
            ScheduleJob job = new ScheduleJob();
            job.setJobGroup("taskUnfinishedOver");
            job.setDesc("自动验收任务结束提醒");
            job.setJobId("over" + task_id.toString());
            job.setJobName("over" + task_id.toString());
            quartzJobManage.deleteJob(job);
        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initTaskUnfinishedRemind()
    {
        List<Map<String, Object>> list = taskMessageDao.getAllUnfinishedTask();
        if (list != null)
        {
            for (Map<String, Object> m : list)
            {
                taskUnfinishedRemind(m);
            }
        }
    }

    @Override
    public Map<String, Object> findUnFinshedTaskByID(Integer task_id)
    {
        return taskMessageDao.findUnFinshedTaskByID(task_id);
    }

    @Override
    public String findAliasByCode(String app_name, String user_code)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isAllBlank(app_name, user_code))
        {
            return null;
        }
        map.put("app_name", app_name);
        map.put("user_code", user_code);
        return taskMessageDao.findAliasByCode(map);
    }

    @Override
    public Map<String, Object> taskListIsOnly(Integer personnel_id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Integer> publishList = taskMessageDao.getPublishTaskList(personnel_id);
        List<Integer> feedbackList = taskMessageDao.getFeedbackTaskList(personnel_id);
        if (publishList != null && publishList.size() == 1)
        {
            result.put("is_only_publish", true);
            result.put("publish_task_id", publishList.get(0));
        }
        else
        {
            result.put("is_only_publish", false);
        }
        if (feedbackList != null && feedbackList.size() == 1)
        {
            result.put("is_only_feedback", true);
            result.put("feedback_task_id", feedbackList.get(0));
        }
        else
        {
            result.put("is_only_feedback", false);
        }
        return result;
    }

    /**
     * ，判断不出是否是工作日 ture是 false不是
     * 
     * @param personnel_deptId
     * @return
     */
    public boolean GetBackSMSBool(int personnel_deptId)
    {
        boolean isContains = true;
        Map<String, Object> mm = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        String year = cal.get(Calendar.YEAR) + "";
        int month = cal.get(Calendar.MONTH);
        String motths[] = { "Jan_month", "Feb_month", "Mar_month", "Apr_month", "May_month", "Jun_month", "Jul_month", "Aug_month", "Sep_month", "Oct_month", "Nov_month", "Dec_month" };
        String mon = motths[month];
        String day = cal.get(Calendar.DATE) + "";
        mm.put("personnel_deptId", personnel_deptId);
        mm.put("year", year);
        Integer id = taskMessageDao.getBasicByPerDeptIdAndYear(mm);
        if (id != null)
        {// 如果日历取不到，判断不出是否是休息日，就当做工作日，发提醒
            mm.clear();
            mm.put("id", id);
            Integer basic = taskMessageDao.getVolumeById(mm);
            mm.clear();
            mm.put("basic", basic);
            mm.put("mon", mon);
            String str = taskMessageDao.getMonthBybasicId(mm);
            if (str != null && str != "")
            {
                String a[] = str.split(",");
                isContains = Arrays.asList(a).contains(day);
            }
        }
        return isContains;
    }

    /**
     * @Title: initTaskUnfinishedOver
     * @Description: 初始化未完成任务自动验收  
     * @author: sunlq
     * @time:2016年12月9日 下午4:09:12
     * @see com.zx.moa.ioa.task.service.ITaskMessageService#initTaskUnfinishedOver()
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    @Override
    public void initTaskUnfinishedOver()
    {
        List<Map<String, Object>> list = taskMessageDao.getAllUnfinishedTaskOver();
        if (list != null)
        {
            for (Map<String, Object> m : list)
            {
                taskUnfinishedOver(m);
            }
        }
    }

    /**
     * @Title: taskUnfinishedOver
     * @Description: 创建自动验收任务
     * @param map 
     * @author: sunlq
     * @time:2016年12月9日 下午5:00:14
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    @Override
    public void taskUnfinishedOver(Map<String, Object> map)
    {

        String finish_time = (String) map.get("finish_time");
        if (finish_time.length() <= 16)
        {
            finish_time += ":00";
        }

        Integer task_id = (Integer) map.get("task_id");
        if (StringUtil.isNotBlank(finish_time))
        {
            ScheduleJob job = new ScheduleJob();
            job.setJobGroup("taskUnfinishedOver");
            job.setDesc("任务自动验收");
            job.setJobId("over" + task_id.toString());
            job.setJobName("over" + task_id.toString());
            String now = DateUtil.getStrDateTime();
            String cron = null;
            // 截止时间小于等于当前时间
            if (finish_time.compareTo(now) <= 0)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 1);
                cron = new SimpleDateFormat("ss mm HH dd MM ? yyyy").format(calendar.getTime());
            }
            // 截止时间大于当前时间
            else
            {
                cron = QuartzJobManage.CronExpressionHelper("0", DateUtil.strDate(finish_time, DateUtil.DEFAULT_FORMAT_TIMESTAMP));
            }
            if (StringUtil.isBlank(cron))
            {
                return;
            }
            job.setCronExpression(cron);
            job.setRunjob(QuartzJobFactory.getTaskUnfinishedOverJob(task_id));
            try
            {
                quartzJobManage.createJob(job);
            }
            catch (SchedulerException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Title: findUnFinshedTaskOverByID
     * @Description: 查询自动验收任务
     * @param task_id
     * @return 
     * @author: sunlq
     * @time:2016年12月9日 下午5:32:28
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    @Override
    public Map<String, Object> findUnFinshedTaskOverByID(Integer task_id)
    {
        return taskMessageDao.findUnFinshedTaskOverByID(task_id);
    }

    /**
     * @Title: countFeedBackNumForAutoCheck
     * @Description: TODO(统计反馈任务反馈数量)
     * @param map
     * @return 
     * @author: sunlq
     * @time:2016年12月15日 上午10:24:20
     * @see com.zx.moa.ioa.task.service.ITaskMessageService#countFeedBackNumForAutoCheck(java.util.Map)
     * history:
     * 1、2016年12月15日 sunlq 创建方法
     */
    @Override
    public int countFeedBackNumForAutoCheck(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        return taskMessageDao.countFeedBackNumForAutoCheck(map);
    }
}
