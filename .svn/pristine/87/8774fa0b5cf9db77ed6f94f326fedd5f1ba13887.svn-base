package com.zx.moa.crm.customermanage.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.crm.customermanage.vo.CRMCustomerInfo;
import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * 获取CRM客户信息数据层接口
 * @author MATF
 *
 */
@MyBatisRepository
public interface CustomerDao {
	            /**
    * 获取客户信息
    */
	public List<Map<String, Object>> getInfoAll(Map<String, Object> map);
	public Integer countInfoAll(Map<String ,Object> parmap);
	
	            /**
    * 根据客户costomer_id获取客户信息
    * @param costomer_id 
    * @return
    */
	public Map<String,Object> selectCustomerById(Map<String,Object> map);

    /**
     * @Title: selectCustomerByIdV121
     * @Description: TODO(根据客户costomer_id获取客户信息 V1.2.1版)
     * @param map
     * @return 
     * @author: suncf
     * @time:2017年2月7日 下午1:28:35
     * history:
     * 1、2017年2月7日 suncf 创建方法
     */
    public Map<String, Object> selectCustomerByIdV121(Map<String, Object> map);
	
    /**
     * 
     * @Title: whetherConcatNumberRepeat
     * @Description: TODO(验证电话是否重复 )
     * @return 
     * @author: 张明建
     * @time:2017年2月7日 上午9:51:22
     * history:
     * 1、2017年2月7日 张明建 创建方法
     */
	public Integer whetherConcatNumberRepeat(CRMCustomerInfo bean);
	
    /**
     * 
     * @Title: whetherIdCardRepeat
     * @Description: TODO(验证身份证号是否重复)
     * @return 
     * @author: 张明建
     * @time:2017年2月7日 上午9:52:27
     * history:
     * 1、2017年2月7日 张明建 创建方法
     */
	public Integer whetherIdCardRepeat(CRMCustomerInfo bean);
	
	
	/**
	 * 
	 * @Title: getTelAbandonTimeOut
	 * @Description: TODO(验证放弃客户)
	 * @param map
	 * @return 
	 * @author: 张明建
	 * @time:2017年2月7日 下午2:57:10
	 * history:
	 * 1、2017年2月7日 张明建 创建方法
	 */
	
	public String getTelAbandonTimeOut(Map<String,Object> map);
	
	
	
}
