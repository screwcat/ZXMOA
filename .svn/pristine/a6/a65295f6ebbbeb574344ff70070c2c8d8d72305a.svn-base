package com.zx.moa.ioa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.zx.platform.syscontext.util.StringUtil;

/**
 * 调度管理工具类
 * @author MATF
 *
 */
public class QuartzJobManage{
	
	Logger log = LoggerFactory.getLogger(QuartzJobManage.class);
	
	public static final String JOBDATAMAP_JOB_KEY = "scheduleJob";

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * 创建任务如果存在更新原任务
	 * @param job
	 * @throws SchedulerException
	 */
	public void createJob(ScheduleJob job) throws SchedulerException {
		
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());
		// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			log.debug("create job->"+job.getJobGroup()+"-"+job.getJobName()+"-"+job.getCronExpression());
			JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
					.withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put(JOBDATAMAP_JOB_KEY, job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			log.debug("update job->"+job.getJobGroup()+"-"+job.getJobName()+"-"+job.getCronExpression());
			// Trigger已存在，那么更新相应的定时设置
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	/**
	 * 暂停任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException{
		log.debug("pause job->"+scheduleJob.getJobGroup()+"-"+scheduleJob.getJobName()+"-"+scheduleJob.getCronExpression());
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}
	
	/**
	 * 恢复任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException{
		log.debug("resume job->"+scheduleJob.getJobGroup()+"-"+scheduleJob.getJobName()+"-"+scheduleJob.getCronExpression());
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * 删除任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException{
		log.debug("delete job->"+scheduleJob.getJobGroup()+"-"+scheduleJob.getJobName()+"-"+scheduleJob.getCronExpression());
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}
	
	/**
	 * 立即执行任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void execJob(ScheduleJob scheduleJob) throws SchedulerException{
		log.debug("exec job->"+scheduleJob.getJobGroup()+"-"+scheduleJob.getJobName()+"-"+scheduleJob.getCronExpression());
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 更新任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJob(ScheduleJob scheduleJob) throws SchedulerException{
		log.debug("update job->"+scheduleJob.getJobGroup()+"-"+scheduleJob.getJobName()+"-"+scheduleJob.getCronExpression());
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(),
		    scheduleJob.getJobGroup());
		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
		    .getCronExpression());
		//按新的cronExpression表达式重新构建trigger
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
		    .withSchedule(scheduleBuilder).build();
		//按新的trigger重新设置job执行
		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	/**
	 * Schedule时间表达式生成
	 * @param advance 时间差值
	 * @param base 基础时间
	 * @return
	 */
	public static String CronExpressionHelper(String advance,Date base){
		if(StringUtil.isEmpty(advance)){
		}
		if(base != null){
		}
		int sign = -1;
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(base);
		if("-1".equals(advance)){
			return null;
		}else if(advance.indexOf(".")>0){
			Double m = new Double(advance);
			int min = ((Double)(m*60)).intValue();
			calendar.add(Calendar.MINUTE, sign*min);
		}else{
			Integer hour = Integer.parseInt(advance);
			calendar.add(Calendar.HOUR_OF_DAY, sign*hour);
		}
		if(calendar.getTimeInMillis()<=new Date().getTime()){
			return null;
		}
        String cron = new SimpleDateFormat("ss mm HH dd MM ? yyyy").format(calendar.getTime());
//		System.out.println(cron);
		return cron;
	}
	
	public static void main(String[] args){
		CronExpressionHelper("24",new Date());
	}

	
}
