package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.SysPersonnelDeptConcern;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;


@MyBatisRepository
public interface SysPersonnelDeptConcernDao extends BaseDao<SysPersonnelDeptConcern> {
	/**
	 * 删除所有信息
	 */
	void deleteF();
	
	//批量更新 2016-8-4 baisong
	void  updateBatch(List<SysPersonnelDeptConcern> sysPersonnelDeptConcernList);
		
	//批量保存 2016-8-4 baisong
	int saveBatch(List<SysPersonnelDeptConcern> sysPersonnelDeptConcernList);
	
	/**
	 * 根据条件获取数据权限
	 * @param retMap
	 * @return
	 */
	List<Map<String, Object>> getDeptInfoList(Map<String, Object> retMap);
}