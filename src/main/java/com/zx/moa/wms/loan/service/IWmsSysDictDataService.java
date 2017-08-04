package com.zx.moa.wms.loan.service;

import java.util.Map;

import com.zx.moa.util.bean.UserBean;
import com.zx.moa.util.gen.entity.wms.WmsSysDictData;
import com.zx.moa.wms.loan.vo.WmsSysDictDataSearchBeanVO;

/*
 * @version 2.0
 */

public interface IWmsSysDictDataService {
	/**
	 * Description :get record list records by vo queryInfo withnot paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	public Map<String, Object> getListWithoutPaging(WmsSysDictDataSearchBeanVO queryInfo);
	
	/**
	 * Description :get record list records by vo queryInfo with paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	public Map<String, Object> getListWithPaging(WmsSysDictDataSearchBeanVO queryInfo);
	
	/**
	 * Description :get single-table information by primary key 
	 * @param primary key 
	 * @return WmsSysDictDataVO
	 * @author auto_generator
	 */	
	public WmsSysDictData getInfoByPK(Integer wms_sys_dict_data_id);	
	
	/**
	 * Description :add method
	 * @param bean
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */
	public String save(WmsSysDictData bean, UserBean user);
	
	/**
	 * Description :update method
	 * @param bean contains pk at least
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */
	public String update(WmsSysDictData bean, UserBean user);
}