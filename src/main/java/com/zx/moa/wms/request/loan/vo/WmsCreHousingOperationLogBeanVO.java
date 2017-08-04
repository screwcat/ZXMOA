package com.zx.moa.wms.request.loan.vo;

import com.zx.platform.syscontext.vo.GridParamBean;

/**
 * 版权所有：版权所有(C) 2016，卓信金控 系统名称：财富管理平台
 * 
 * @ClassName: WmsCreHousingOperationLogBeanVO 模块名称：
 * @Description: 内容摘要：
 * @author ZhangWei
 * @date 2017年6月1日
 * @version V1.0 history: 1、2017年6月1日 ZhangWei 创建文件
 */
public class WmsCreHousingOperationLogBeanVO extends GridParamBean {

	private Integer wms_cre_credit_head_id;// 单据主键

	private String oper_type;

	private String operation_reason;

	/**
	 * @return the wms_cre_credit_head_id
	 */
	public Integer getWms_cre_credit_head_id() {
		return wms_cre_credit_head_id;
	}

	/**
	 * @param wms_cre_credit_head_id
	 *            the wms_cre_credit_head_id to set
	 */
	public void setWms_cre_credit_head_id(Integer wms_cre_credit_head_id) {
		this.wms_cre_credit_head_id = wms_cre_credit_head_id;
	}

	/**
	 * @return the oper_type
	 */
	public String getOper_type() {
		return oper_type;
	}

	/**
	 * @param oper_type
	 *            the oper_type to set
	 */
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	/**
	 * @return the operation_reason
	 */
	public String getOperation_reason() {
		return operation_reason;
	}

	/**
	 * @param operation_reason
	 *            the operation_reason to set
	 */
	public void setOperation_reason(String operation_reason) {
		this.operation_reason = operation_reason;
	}
}