package com.zx.moa.ioa.task.persist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zx.moa.ioa.task.vo.TaskListVO;
import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: TaskListDao
 * 模块名称：任务Dao
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2016年12月9日
 * @version V1.7.7
 * history:
 * 1、2016年12月9日 zhaowei 创建文件
 */
@MyBatisRepository
public interface TaskListDao {
	
	public List<Map<String, Object>>  getTaskListByCondition(TaskListVO task);
	
	public List<Map<String, Object>>  getChildenTaskList(TaskListVO task);
	
	public Map<String, Object> getTaskBaseInfo(@Param(value="task_id") Integer task_id);
	
	public List<Map<String, Object>> getTaskInfo(Map<String, Object> map);
	
	public List<Map<String, Object>> getFeedbackInfo(@Param(value="task_id") Integer task_id);
	public List<Map<String, Object>> getFeedbackInfo115(@Param(value="task_id") Integer task_id,@Param(value="max_id") Integer max_id);

    // 获取参数
	public Map<String, Object> getTaskInfoById(Map<String, Object> map);
	
    // 查询任务是否已经有延期申请
	public Map<String, Object> getTaskIsDelay(Map<String, Object> paramsMap);
	
    // 通过task_num查询任务
	public List<Map<String, Object>> getTaskByNum(Map<String, Object> paramsMap);
	
    // 根据当前任务已协同的任务id查询反馈人的id
	public List<Map<String, Object>> getfeedbackUserBytaskid(@Param(value="xtid")int xtid);
	
    // 查询反馈页面原反馈人--拼接用
	public List<Map<String, Object>> getQuondamFeedbackUser(Map<String,Object> parmap);
	
	public List<Map<String, Object>> getTaskinfoByNumOrderbyCreatetime(Map<String, Object> map);
	 
    /**
    * 
    * @Title: countAcceptFeedback 
    * @Description: 查询反馈任务的数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:49:00
    */
    public int countAcceptFeedback(TaskListVO bean);
    
    /**
     * 
    * @Title: countFeedbackUser 
    * @Description: 查询指派的数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:49:00
     */
    public int countFeedbackUser(TaskListVO bean);
    
	                        /**
    * 
    * @Title: countDoing_num 
    * @Description: 查询进行中的数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:48:24
    */
    public int countDoing_num(TaskListVO bean);
   
    /**
     * 
    * @Title: CountDelay_num 
    * @Description: 查询延误数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:49:57
     */
    public int CountDelay_num(TaskListVO bean);
    
    /**
     * 
    * @Title: CountCheck_num 
    * @Description: 查询待验收数量
    * @return    
    * Integer     
    * @throws 
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:50:45
     */
    public int CountCheck_num(TaskListVO bean );
    
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
    public List<Map<String, Object>> taskIndexListSearch(Map<String, Object> map);

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

    /**
     * @Title: getFeedBackUserInfo
     * @Description: TODO(获取反馈人信息)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月13日 上午10:29:01
     * history:
     * 1、2016年12月13日 Lixiaolong 创建方法
     */
    public Map<String, Object> getFeedBackUserInfo(Map<String, Object> map);

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
    public List<Map<String, Object>> taskIndexListSearcho(Map<String, Object> map);

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
    public int taskIndexListSearchCounto(Map<String, Object> map);
}
