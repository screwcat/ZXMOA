package com.zx.moa.wms.inve.service;

import java.util.Map;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：财富管理平台
 * @ClassName: IWmsInveSalaryPreTotalService
 * 模块名称：
 * @Description: 内容摘要：
 * @author zhangyunfei
 * @date 2017年4月19日
 * @version V1.0
 * history:
 * 1、2017年4月19日 Administrator 创建文件
 */
public interface IWmsInveSalaryPreTotalService
{

    /**
     * @Title: getPerformanceSalaryApproveInfos
     * @Description: 获取绩效工资审核单据详情
     * @param wms_inve_salary_pre_total_ids 单据主键集合 以逗号分隔
     * @param bill_type_code
     * @return 
     * @author: zhangyunfei
     * @time:2017年4月19日 上午10:28:23
     * history:
     * 1、2017年4月19日 Administrator 创建方法
    */
    public Map<String, Object> getPerformanceSalaryApproveInfos(String wms_inve_salary_pre_total_ids, String bill_type_code);

    /**
     * @Title: approvePerformanceSalaryPre
     * @Description: 手持审批绩效工资
     * @param advice(审批意见)
     * @param is_pay(审核结果)
     * @param dept_ids(团队ids)
     * @return 
     * @author: zhangyunfei
     * @time:2017年4月24日 上午10:33:35
     * history:
     * 1、2017年4月10日 Administrator 创建方法
    */
    public Map<String, Object> approvePerformanceSalaryPre(PmPersonnel personnel, String advice, String is_pay, String dept_ids);

}
