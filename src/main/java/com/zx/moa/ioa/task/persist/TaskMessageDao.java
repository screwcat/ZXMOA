package com.zx.moa.ioa.task.persist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zx.moa.util.mybatis.MyBatisRepository;

@MyBatisRepository
public interface TaskMessageDao {

	/**
	 * 获取应该反馈但未反馈任务
	 * @return
	 */
	public List<Map<String, Object>> unFeedback();
	
	/**
	 * 获取所有推送人员信息
	 * @return
	 */
	public List<Map<String, Object>> getAppUserRecord(@Param("app_name")String app_name);
	
	/**
	 * 获取所有未完成任务
	 * @return
	 */
	public List<Map<String, Object>> getAllUnfinishedTask();
	
	/**
	 * 根据任务ID获取未完成任务
	 * @param task_id
	 * @return
	 */
	public Map<String, Object> findUnFinshedTaskByID(@Param("task_id")Integer task_id);
	
	/**
	 * 根据APP名称和短工号获取推送别名
	 * @param app_name APP名称
	 * @param user_code 员工短工号
	 * @return 推送别名
	 */
	public String findAliasByCode(Map<String, Object> map);
	
	/**
	 * 获取发布列表的前两条的ID
	 * @param personnel_id
	 * @return
	 */
	public List<Integer> getPublishTaskList(@Param("personnel_id")Integer personnel_id);
	
	/**
	 * 获取反馈列表的前两条的ID
	 * @param personnel_id
	 * @return
	 */
	public List<Integer> getFeedbackTaskList(@Param("personnel_id")Integer personnel_id);
	
	/**
	 * 根据人员部门id和年份 获取日历id(调用数据库的findVolumnByDeptId函数)
	 * @param basic
	 * @param year
	 * @return
	 */
	public int getBasicByPerDeptIdAndYear(Map<String, Object> map);
	
	/**根据time_volme主键查找basci_Id
	 * @param map
	 * @return
	 */
	public int getVolumeById(Map<String, Object> map);
	
	/**
	 * 获取本月所有工作日 
	 * @param map
	 * @return
	 */
	public String getMonthBybasicId(Map<String, Object> map);
	
	public List<Integer> selPostIdById(int id);

    /**
     * @Title: getAllUnfinishedTaskOver
     * @Description: 初始化未完成任务自动验收
     * @return 
     * @author: sunlq
     * @time:2016年12月9日 下午4:12:35
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    public List<Map<String, Object>> getAllUnfinishedTaskOver();

    /**
     * @Title: findUnFinshedTaskOverByID
     * @Description: findUnFinshedTaskOverByID
     * @param task_id
     * @return 
     * @author: sunlq
     * @time:2016年12月9日 下午5:41:39
     * history:
     * 1、2016年12月9日 sunlq 创建方法
     */
    public Map<String, Object> findUnFinshedTaskOverByID(Integer task_id);

    /**
     * @Title: countFeedBackNumForAutoCheck
     * @Description: TODO(统计任务反馈数量)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月15日 上午10:35:06
     * history:
     * 1、2016年12月15日 Lixiaolong 创建方法
     */
    public int countFeedBackNumForAutoCheck(Map<String, Object> map);
}
