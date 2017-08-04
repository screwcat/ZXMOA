package com.zx.moa.ioa.util;

import com.zx.moa.ioa.util.QuartzJobFactory.RunJob;

/**
 * 自定义任务实体类
 * @author MATF
 *
 */
public class ScheduleJob {

	 /** 任务id */
    private String jobId;
    /** 任务名称 */
    private String jobName;
    /** 任务分组 */
    private String jobGroup;
    /** 任务状态 0禁用 1启用 2删除*/
    private String jobStatus;
    /** 任务运行时间表达式 */
    private String cronExpression;
    /** 任务描述 */
    private String desc;
    
    /**
     * 执行接口这个很重要、可以自己实现接口也可以通过工厂类获取默认的
     */
    private RunJob runjob;
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public RunJob getRunjob() {
		return runjob;
	}
	public void setRunjob(RunJob runjob) {
		this.runjob = runjob;
	}
    
    
}
