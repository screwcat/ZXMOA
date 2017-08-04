package com.zx.moa.ioa.task.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zx.moa.ioa.task.vo.TaskListVO;
import com.zx.moa.ioa.task.vo.TaskMessageVO;
import com.zx.moa.ioa.task.vo.TaskVO;


/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: ITaskListService
 * 模块名称：
 * @Description: 内容摘要：任务Service
 * @author zhaowei
 * @date 2016年12月9日
 * @version V1.7.7
 * history:
 * 1、2016年12月9日 zhaowei 创建文件
 */
public interface ITaskListService {
	
    // 获取任务列表
	public List<Map<String, Object>> getTaskList(HttpServletRequest request,TaskListVO task);
	
    // 获取任务反馈列表
	public Map<String,Object> getTaskFeedbackList(HttpServletRequest request,TaskVO task); 
	
    // 保存发送消息
	public Map<String, Object> sendTaskFeedbackList(HttpServletRequest request,TaskMessageVO message);
	
    // 验收和重办
	public Map<String, Object> CheckAndAgain(HttpServletRequest request,Map<String, Object> paramsMap);
	
    // 自动验收
    public Map<String, Object> autoCheck(Map<String, Object> paramsMap);

    // 申请延期和确认延期
	public Map<String, Object> dealTaskFinishTime(HttpServletRequest request,Map<String, Object> paramsMap);

    // 转移，协同任务
	public Map<String, Object> changeTaskFeedback(HttpServletRequest request,Map<String, Object> paramsMap);
	
    // 获取任务反馈列表
    public Map<String,Object> getTaskFeedbackListV115(HttpServletRequest request,TaskVO task); 
	
    /**
     * 
    * @Title: which_tab 
    * @Description: 比较反馈与指派的数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:49:00
     */
    public Integer which_tab(TaskListVO bean);
	
    /**
     * 
    * @Title: doing_num 
    * @Description: 查询进行中的数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:48:24
     */
    public Integer doing_num(TaskListVO bean);
   
    /**
     * 
    * @Title: delay_num 
    * @Description: 查询延误数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:49:57
     */
    public Integer delay_num(TaskListVO bean);
    
    /**
     * 
    * @Title: check_num 
    * @Description: 查询待验收数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:50:45
     */
    public Integer check_num(TaskListVO bean);

    
    
    /**
     * @Title: taskIndexListSearch
     * @Description: 待办任务列表
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 上午10:31:13
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    public List<Map<String, Object>> taskIndexListSearch(HttpServletRequest request, Map<String, Object> map);
    
    /**
     * @Title: taskIndexListSearch
     * @Description: 待办任务列表总数
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 上午10:31:13
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    public int taskIndexListSearchCount(Map<String, Object> map);
}
