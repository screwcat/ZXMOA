package com.zx.moa.crm.customermanage.service;

import java.util.List;
import java.util.Map;

import com.zx.moa.crm.customermanage.vo.CRMCustomerInfo;

/**
 * 获取CRM客户信息业务层接口
 * @author MATF
 *
 */
public interface ICustomerService {

	            /**
    * 查询客户信息
    */
	public List<Map<String, Object>> getInfoAll(Map<String, Object> map);
	
    /**
     * 根据客户costomer_id获取客户信息
     * @param costomer_id 
     * @return
     */
	public Map<String,Object> getCustomerById(Integer id);
	 
    /**
    * 
    * @Title: verifyMethod
    * @Description: TODO(检验数据是否正确)
    * @param queryInfo
    * @return 
    * @author: 张明建
    * @time:2017年2月6日 下午2:05:42
    * history:
    * 1、2017年2月6日 张明建 创建方法
    */
    public  String verifyMethod(CRMCustomerInfo queryInfo);
    
    /**
     * @Title: getCustomerByIdV121
     * @Description: TODO(查询客户信息)
     * @param costomer_id
     * @return 
     * @author: suncf
     * @time:2017年2月6日 下午4:48:07
     * history:
     * 1、2017年2月6日 suncf 创建方法
     */
    public Map<String, Object> getCustomerByIdV121(Integer costomer_id);
	
}
