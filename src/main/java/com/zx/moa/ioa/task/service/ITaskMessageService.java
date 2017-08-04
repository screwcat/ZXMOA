package com.zx.moa.ioa.task.service;

import java.util.Map;

import com.zx.moa.util.bean.ResultBean;

public interface ITaskMessageService
{

    public ResultBean<Map<String, Object>> taskMessageRead(String task_id, String task_message_id, Integer personnel_id);

    /**
     * 处理未反馈任务
     * 发布人、反馈人都推送消息
     * @return
     */
    public Integer unFreeback20();

    /**
     * 处理未反馈任务
     * 反馈人都推送消息
     * @return
     */
    public Integer unFreeback17();

    /**
     * 根据APP名称获取推送人员信息
     * @param appName
     * @return
     */
    public Map<String, Object> getAppUserRecord(String appName);

    /**
     * 未完成任务提醒
     */
    public void taskUnfinishedRemind(Map<String, Object> map);

    /**
     * 初始化未完成任务提醒
     */
    public void initTaskUnfinishedRemind();

    /**
     * 根据任务ID获取未完成任务
     * @param task_id
     * @return
     */
    public Map<String, Object> findUnFinshedTaskByID(Integer task_id);

    /**
     * 根据APP名称和短工号获取推送别名
     * @param app_name APP名称
     * @param user_code 员工短工号
     * @return 推送别名
     */
    public String findAliasByCode(String app_name, String user_code);

    /**
     * 任务列表（反馈、发布）是否只有一条数据信息
     * @param personnel_id
     * @return
     */
    public Map<String, Object> taskListIsOnly(Integer personnel_id);

    /**
     * 任务状态结束提醒
     * @param map
     * @return
     */
    public void taskEndState(Map<String, Object> map);

    /**
     * @Title: initTaskUnfinishedRemind
     * @Description: 初始化未完成任务自动验收 
     * @author: sunlq
     * @time:2016年12月9日 下午4:07:57
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    public void initTaskUnfinishedOver();

    /**
     * @Title: taskUnfinishedOver
     * @Description: 创建自动验收任务 
     * @param map 
     * @author: sunlq
     * @time:2016年12月9日 下午5:01:48
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    public void taskUnfinishedOver(Map<String, Object> map);

    /**
     * @Title: findUnFinshedTaskOverByID
     * @Description: 根据任务ID获取可验收任务
     * @param task_id
     * @return 
     * @author: sunlq
     * @time:2016年12月9日 下午5:33:39
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    public Map<String, Object> findUnFinshedTaskOverByID(Integer task_id);

    /**
     * @Title: countFeedBackNum
     * @Description: TODO(统计任务反馈数量)
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月15日 上午10:20:15
     * history:
     * 1、2016年12月15日 Lixiaolong 创建方法
     */
    public int countFeedBackNumForAutoCheck(Map<String, Object> map);
}
