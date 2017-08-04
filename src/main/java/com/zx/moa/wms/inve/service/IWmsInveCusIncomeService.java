package com.zx.moa.wms.inve.service;

import java.util.Map;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：财富管理平台
 * @ClassName: IWmsInveCusIncomeService
 * 模块名称：客户收益service接口
 * @Description: 内容摘要：客户收益相关查询汇总等方法定义
 * @author jinzhm
 * @date 2017年1月3日
 * @version V1.0
 * history:
 * 1、2017年1月3日 jinzhm 创建文件
 */
public interface IWmsInveCusIncomeService
{
    /**
     * @Title: getCusIncomeWithCustomerSummary
     * @Description: 获取客户收益按客户汇总
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param salesman_id 客户经理id
     * @param cus_name 客户姓名
     * @return 按客户维度汇总的某个客户经理的客户收益信息
     * @author: jinzhm
     * @time:2017年1月3日 下午2:51:05
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
     */
    public Map<String, Object> getCusIncomeWithCustomerSummary(Integer page, Integer page_size, String statics_month,
                                                               String salesman_id, String cus_name);

    /**
     * @Title: getCusIncomeWithTransaSummary
     * @Description: 查询某一个客户在某一个月的单据收益信息集合
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param cus_id 客户id
     * @return 返回某一个客户在某一个月的单据收益信息集合
     * @author: jinzhm
     * @time:2017年1月3日 下午4:28:04
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
     */
    public Map<String, Object> getCusIncomeWithTransaSummary(Integer page, Integer page_size, String statics_month,
                                                             String cus_id);

    /**
     * @Title: getCusIncomeWithSalesmanSummary
     * @Description: 查询某个月团队下所有客户经理维度汇总的收益信息集合
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param team_ids 团队id（多个用英文逗号隔开）
     * @param salesman_name 客户经理姓名或短工号
     * @return 返回某个月团队下所有客户经理维度汇总的收益信息集合
     * @author: jinzhm
     * @time:2017年1月3日 下午5:30:30
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
     */
    public Map<String, Object> getCusIncomeWithSalesmanSummary(Integer page, Integer page_size, String statics_month,
                                                               String team_ids, String salesman_name);

}
