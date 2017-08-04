package com.zx.moa.ioa.timingtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.ioa.timeAttendance.service.IAttendanceService;

/**
 * IOA任务管理模块定时任务
 * @author MATF
 *
 */
@Component
public class Task {

	@Autowired
	private ITaskMessageService taskMessageService;
	
    @Autowired
    private IAttendanceService IAttendanceService;

	/**
	 * 20点处理为反馈信息提醒
	 */
	@Scheduled(cron = "0 0 20 ? * *")
	public void unfreeback20(){
		taskMessageService.unFreeback20();
	}
	
	/**
	 * 17点处理为反馈信息提醒
	 */
	@Scheduled(cron = "0 0 17 ? * *")
	public void unfreeback17(){
		taskMessageService.unFreeback17();
	}

    /**
     * @Title: timeAttendance
     * @Description: 考勤异常通知
     * @author: sunlq
     * @throws InterruptedException 
     * @time:2017年7月5日 下午5:31:41
     * history:
     * 1、2017年7月5日 sunlq 创建方法
     */
    @Scheduled(cron = "0 0 9 ? * *")
    public void timeAttendance() throws InterruptedException
    {
        IAttendanceService.timeAttendance();
    }
}
