package com.zx.moa.ioa.task.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.mybatis.MyBatisRepository;
@MyBatisRepository
public interface ReleaseTaskDao {
	
	/*
	 * 查询所属部门下的人员
	 */
	
	public List<Map<String, Object>> selectPersonnelInfoByDept(Map<String, Object> map);
	
	
	/*
	 * 查询人员信息
	 */
	
	public List<Map<String, Object>> selectPersonnelInfoByNameAndCode(Map<String, Object> map);
	
	
	/*
	 * 查询常用人
	 */
	public List<Map<String, Object>> selectDefaultReleasePersonnelById(Map<String,Object> map);
	/*
	 * 任务详情
	 */
	public Map<String,Object> selectTaskById(Map<String,Object> map);
	
    /**
     * @Title: selectTaskByIdfor117
     * @Description: TODO(1.1.7版本查询任务详情，新增任务类型)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月9日 下午5:12:37
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    public Map<String, Object> selectTaskByIdfor117(Map<String, Object> map);

	public List<Map<String,Object>> selectTaskInfoById(Map<String,Object> map);
	
	/**
	 * 查询父任务的接受反馈人
	 */
	public Map<String,Object> selectTaskByPid(Map<String,Object> map);

    /**
     * @Title: getGroupInfo
     * @Description: TODO(获取分组信息)
     * @param map
     * @return List<Map<String, Object>>
     * @author: Lixiaolong
     * @time:2016年12月9日 下午3:04:38
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    public List<Map<String, Object>> getGroupInfo(Map<String, Object> map);

    /**
     * @Title: getPersonnelInfoByGroup
     * @Description: TODO(查询分组人员)
     * @return List<Map<String, Object>>
     * @author: Lixiaolong
     * @time:2016年12月9日 下午6:35:17
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    public List<Map<String, Object>> getPersonnelInfoByGroup(Map<String, Object> map);

    /**
     * @Title: getGroupname
     * @Description: TODO(查询分组名)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月10日 下午2:27:45
     * history:
     * 1、2016年12月10日 Lixiaolong 创建方法
     */
    public Map<String, Object> getGroupname(Map<String, Object> map);
    
    /**
     * @Title: getDeptnameforGroup
     * @Description: TODO(查询部门信息)
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月10日 下午2:49:29
     * history:
     * 1、2016年12月10日 Lixiaolong 创建方法
     */
    public Map<String, Object> getDeptnameforGroup(Map<String, Object> map);
}
