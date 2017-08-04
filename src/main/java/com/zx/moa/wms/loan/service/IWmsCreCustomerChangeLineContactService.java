package com.zx.moa.wms.loan.service;

import java.util.Map;



/*
 * @version 2.0
 */

public interface IWmsCreCustomerChangeLineContactService
{
/**
 * 3.36	获取授信确认初始化信息 
 * @param user_id
 * @param wms_cre_credit_head_id
 * @return jiaodelong
 */
	Map<String, Object> initCreditConfirmInfo(Integer user_id,
			Integer wms_cre_credit_head_id);
}