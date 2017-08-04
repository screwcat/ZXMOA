package com.zx.moa.ioa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.zx.moa.ioa.task.service.ITaskMessageService;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: InitUnfinishedRemind
 * 模块名称：任务初始化
 * @Description: 内容摘要：任务初始化
 * @author sunlq
 * @date 2016年12月9日
 * @version V1.0
 * history:
 * 1、2016年12月9日 sunlq 创建文件
 */
public class InitUnfinishedRemind implements ApplicationListener<ContextRefreshedEvent>
{

    Logger log = LoggerFactory.getLogger(InitUnfinishedRemind.class);

    @Autowired
    private ITaskMessageService taskMessageService;

    public static ApplicationContext ac;

    /**
     * @Title: onApplicationEvent
     * @Description: 初始化执行方法
     * @param event 
     * @author: sunlq
     * @time:2016年12月9日 下午4:00:17
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        // 可能包含多层容器、在最外层容器初始化完成时执行操作
        if (event.getApplicationContext().getParent() == null && "Root WebApplicationContext".equals(event.getApplicationContext().getDisplayName()))
        {
            log.debug("spring Root WebApplicationContext 容器加载完成！");
            log.debug("初始化 ：未完成任务提醒!");
            ac = event.getApplicationContext();
            taskMessageService.initTaskUnfinishedRemind();
            log.debug("初始化结束!");

            log.debug("初始化 ：新闻公告,会议纪要未完成任务自动验收!");
            taskMessageService.initTaskUnfinishedOver();
            log.debug("初始化结束!");
        }
    }

}
