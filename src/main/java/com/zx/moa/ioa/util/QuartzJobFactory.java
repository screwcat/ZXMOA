package com.zx.moa.ioa.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zx.moa.ioa.task.service.ITaskListService;
import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.util.DateUtil;
import com.zx.moa.util.GlobalVal;

/**
 * 自定义调度工厂类
 * @author MATF
 *
 */
public class QuartzJobFactory implements Job
{

    Logger log = LoggerFactory.getLogger(QuartzJobFactory.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(QuartzJobManage.JOBDATAMAP_JOB_KEY);
        log.debug("job start->" + scheduleJob.getJobName());
        if (scheduleJob.getRunjob() != null)
        {
            scheduleJob.getRunjob().exec(scheduleJob);
        }
        log.debug("job end->" + scheduleJob.getJobName());
    }

    /**
     * 获取消息推送JOB
     * @param message
     * @return
     */
    public static RunJob getPushJob(PushMessage message)
    {
        return new PushRunJob(message);
    }

    /**
     * 获取任务未完成提醒JOB
     * @param task_id
     * @return
     */
    public static RunJob getTaskUnfinishedJob(Integer task_id)
    {
        return new TaskUnFinishedRunJob(task_id);
    }

    /**
     * 调度器执行接口
     * @author MATF
     *
     */
    public interface RunJob
    {
        public void exec(ScheduleJob scheduleJob);
    }

    /**
     * 推送消息执行类
     * @author MATF
     *
     */
    public static class PushRunJob implements RunJob
    {

        private PushMessage message;

        public PushRunJob(PushMessage message)
        {
            this.message = message;
        }

        @Override
        public void exec(ScheduleJob scheduleJob)
        {
            PushManage.pushMessage(message, PushManage.DEF_APP_NAME);
        }

    }

    /**
     * 未完成任务提醒执行类
     * @author MATF
     *
     */
    public static class TaskUnFinishedRunJob implements RunJob
    {

        private Integer task_id;

        public TaskUnFinishedRunJob(Integer task_id)
        {
            this.task_id = task_id;
        }

        @Override
        public void exec(ScheduleJob scheduleJob)
        {
            ITaskMessageService taskMessageService = (ITaskMessageService) GlobalVal.ctx.getBean("taskMessageService");
            Map<String, Object> task = taskMessageService.findUnFinshedTaskByID(task_id);
            if (task != null)
            {
                String finish_time = (String) task.get("finish_time");
                String remind_time = (String) task.get("remind_time");
                String feedback_user_code = (String) task.get("feedback_user_code");
                int sign = -1;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.strDate(finish_time, DateUtil.DEFAULT_FORMAT_TIMESTAMP));
                if ("-1".equals(remind_time))
                {
                    return;
                }
                else if (remind_time.indexOf(".") > 0)
                {
                    Double m = new Double(remind_time);
                    int min = ((Double) (m * 60)).intValue();
                    calendar.add(Calendar.MINUTE, sign * min);
                }
                else
                {
                    Integer hour = Integer.parseInt(remind_time);
                    calendar.add(Calendar.HOUR_OF_DAY, sign * hour);
                }
                long result = calendar.getTimeInMillis() - new Date().getTime();
                if (Math.abs(result) <= PushConstant.TASK_UNFINISHED_REMIND_ERROR)
                {
                    Map<String, Object> extras = new HashMap<String, Object>();
                    extras.put("task_id", task_id);
                    PushManage.pushMessageByCode(feedback_user_code, PushConstant.TASK_FEEDBACK_END, task, extras);
                }
            }
        }

    }

    /**
     * @Title: getTaskUnfinishedOverJob
     * @Description: 自动验收
     * @param task_id
     * @return 
     * @author: sunlq
     * @time:2016年12月9日 下午5:19:34
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    public static RunJob getTaskUnfinishedOverJob(Integer task_id)
    {
        return new TaskUnFinishedRunOverJob(task_id);
    }

    /**
     * 版权所有：版权所有(C) 2016，卓信金控
     * 系统名称：MOA移动办公系统
     * @ClassName: TaskUnFinishedRunOverJob
     * 模块名称：自动验收
     * @Description: 内容摘要：自动验收
     * @author sunlq
     * @date 2016年12月9日
     * @version V1.0
     * history:
     * 1、2016年12月9日 sunlq 创建文件
     */
    public static class TaskUnFinishedRunOverJob implements RunJob
    {

        private Integer task_id;

        public TaskUnFinishedRunOverJob(Integer task_id)
        {
            this.task_id = task_id;
        }

        @Override
        public void exec(ScheduleJob scheduleJob)
        {

            ITaskMessageService taskMessageService = (ITaskMessageService) GlobalVal.ctx.getBean("taskMessageService");
            Map<String, Object> task = taskMessageService.findUnFinshedTaskOverByID(task_id);
            if (task != null)
            {
                /*String finish_time = (String) task.get("finish_time");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.strDate(finish_time, DateUtil.DEFAULT_FORMAT_TIMESTAMP));
                long result = calendar.getTimeInMillis() - new Date().getTime();
                if (Math.abs(result) <= PushConstant.TASK_UNFINISHED_REMIND_ERROR)
                 {*/
                    ITaskListService taskListService = (ITaskListService) GlobalVal.ctx.getBean("taskListService");
                    // 判断任务状态 待验收的自动验收 ，进行中的判断是否反馈
                   int task_status =  Integer.parseInt(task.get("task_status").toString());
                   int is_feedback = Integer.parseInt(task.get("is_feedback").toString());
                   //1.进行中 2.待验收
                   if(task_status == 1){
                    // 查询是否有反馈消息
                    Integer fbnum = taskMessageService.countFeedBackNumForAutoCheck(task);
                    if (fbnum > 0)
                        {
                            Map<String, Object> paramsMap = new HashMap<String, Object>();
                            paramsMap.put("task_id", task_id);
                            paramsMap.put("type", 6);
                        paramsMap.put("feedback_user_id", task.get("accept_feedback_id").toString()); // 返回被反馈人的按钮
                            paramsMap.put("finish_time", task.get("finish_time").toString());
                            paramsMap.put("accept_feedback_id", task.get("accept_feedback_id").toString());
                            paramsMap.put("accept_feedback_name", task.get("accept_feedback_name").toString());
                            paramsMap.put("accept_feedback_code", task.get("accept_feedback_code").toString());
                        paramsMap.put("pid", task.get("accept_feedback_id").toString());
                            Map<String, Object> resmap = taskListService.autoCheck(paramsMap);
                        }
                   }else{
                       Map<String, Object> paramsMap = new HashMap<String, Object>();
                       paramsMap.put("task_id", task_id);
                       paramsMap.put("type", 6);
                    paramsMap.put("feedback_user_id", task.get("accept_feedback_id").toString()); // 返回被反馈人的按钮
                       paramsMap.put("finish_time", task.get("finish_time").toString());
                        paramsMap.put("accept_feedback_id", task.get("accept_feedback_id").toString());
                        paramsMap.put("accept_feedback_name", task.get("accept_feedback_name").toString());
                        paramsMap.put("accept_feedback_code", task.get("accept_feedback_code").toString());
                    paramsMap.put("pid", task.get("accept_feedback_id").toString());
                        Map<String, Object> resmap = taskListService.autoCheck(paramsMap);
                   }
                  


                // }
            }
        }
    }

}
