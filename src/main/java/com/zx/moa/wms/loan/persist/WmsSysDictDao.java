package com.zx.moa.wms.loan.persist;

import java.util.List;

import com.zx.moa.util.gen.entity.wms.WmsSysDict;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;

@MyBatisRepository
public interface WmsSysDictDao extends BaseDao<WmsSysDict> {
	/**
	 * 根据人员id获取角色
	 * @param userId
	 * @return
	 */
    List<String> getUserRoleNameList(int userId);
}