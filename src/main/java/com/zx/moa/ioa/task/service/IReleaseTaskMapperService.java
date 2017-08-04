package com.zx.moa.ioa.task.service;

import java.util.List;
import java.util.Map;

public interface IReleaseTaskMapperService {
	/**
	 * 查询所属部门下的人员
	 */
	public  List<Map<String,Object>> selectPersonnelInfoByDept(Map<String,Object> map);
	
	/**
	 * 查询：1默认发布人、2常用发布人、3常用反馈人、4常用转移人、5常用协同人
	 */
	public List<Map<String,Object>> selectReleasePersonnelById(Map<String,Object> map);
	/**
	 * 查询人员信息
	 */
	public List<Map<String, Object>> selectPersonnelInfoByNameAndCode(Map<String, Object> map);

	/**
	 * 任务详情
	 */
	
	public Map<String,Object> selectTaskById(Map<String,Object> map);

    /**
     * @Title: getGroupInfo
     * @Description: TODO(获取分组信息)
     * @return List<Map<String,Object>>
     * @author: LiXiaoLong
     * @time:2016年12月9日 下午2:22:24
     * history:
     * 1、2016年12月9日 LiXiaoLong 创建方法
     */
    public List<Map<String, Object>> getGroupInfo(Map<String, Object> map);

    /**
     * @Title: selectTaskByIdfor117
     * @Description: TODO(1.1.7版本获取任务详情)
     * @param map
     * @return map
     * @author: Lixiaolong
     * @time:2016年12月9日 下午5:09:10
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    public Map<String, Object> selectTaskByIdfor117(Map<String, Object> map);

    /**
     * @Title: getPersonnelInfoByGroup
     * @Description: TODO(查询分组人员信息)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月9日 下午6:31:03
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    public Map<String, Object> getPersonnelInfoByGroup(Map<String, Object> map);


}
