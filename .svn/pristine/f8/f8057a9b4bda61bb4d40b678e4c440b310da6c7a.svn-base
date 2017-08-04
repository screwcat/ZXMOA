package com.zx.moa.wms.loan.service;

import java.util.Map;

import com.zx.moa.util.bean.UserBean;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.wms.loan.vo.WmsCreHousingApplyAttSearchBeanVO;

/*
 * @version 2.0
 */

public interface IWmsCreHousingApplyAttService {
	/**
	 * Description :get record list records by vo queryInfo withnot paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	public Map<String, Object> getListWithoutPaging(WmsCreHousingApplyAttSearchBeanVO queryInfo);
	
	/**
	 * Description :get record list records by vo queryInfo with paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	public Map<String, Object> getListWithPaging(WmsCreHousingApplyAttSearchBeanVO queryInfo);
	
	/**
	 * Description :get single-table information by primary key 
	 * @param primary key 
	 * @return WmsCreHousingApplyAttVO
	 * @author auto_generator
	 */	
	public WmsCreHousingApplyAtt getInfoByPK(Integer wms_cre_housing_apply_att_id);	
	
	/**
	 * Description :add method
	 * @param bean
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */
	public String save(WmsCreHousingApplyAtt bean, UserBean user);
	
	/**
	 * Description :update method
	 * @param bean contains pk at least
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */
	public String update(WmsCreHousingApplyAtt bean, UserBean user);
}