package com.zx.moa.wms.inve.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zx.moa.wms.inve.service.IWmsInveCusIncomeService;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：财富管理平台
 * @ClassName: WmsInveCusIncomeServiceImpl
 * 模块名称：客户收益service接口实现类
 * @Description: 内容摘要：客户收益相关查询汇总等方法实现
 * @author jinzhm
 * @date 2017年1月3日
 * @version V1.0
 * history:
 * 1、2017年1月3日 jinzhm 创建文件
 */
@Service("wmsInveCusIncomeService")
public class WmsInveCusIncomeServiceImpl implements IWmsInveCusIncomeService
{

    /**
     * @Title: getCusIncomeWithCustomerSummary
     * @Description: 通过客户经理id查询客户收益信息按客户进行汇总
     *      直接远程调用请求WMS系统获取相关数据
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param salesman_id 客户经理id
     * @param cus_name 客户姓名
     * @return 按客户维度汇总的某个客户经理的客户收益信息
     * @author: jinzhm
     * @time:2017年1月3日 下午2:51:59
     * @see com.zx.moa.wms.inve.service.IWmsInveCusIncomeService#getCusIncomeWithCustomerSummary(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer)
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
    */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCusIncomeWithCustomerSummary(Integer page, Integer page_size, String statics_month,
                                                               String salesman_id, String cus_name)
    {
        // 请求对象
        RestTemplate restTemplate = new RestTemplate();
        // 请求地址
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl")
                     + "/inve/getCusIncomeWithCustomerSummaryMoa.do";
        // 封装请求参数
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        if (page != null)
        {
            form.add("page", page + "");
        }
        if (page_size != null)
        {
            form.add("page_size", page_size + "");
        }
        form.add("statics_month", statics_month);
        form.add("salesman_id", salesman_id);
        form.add("cus_name", cus_name);
        // 请求
        return restTemplate.postForObject(url, form, Map.class);
    }

    /**
     * @Title: getCusIncomeWithTransaSummary
     * @Description: 远程调用请求WMS查询某一个客户在某一个月的单据收益信息集合
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param cus_id 客户id
     * @return 返回某一个客户在某一个月的单据收益信息集合
     * @author: jinzhm
     * @time:2017年1月3日 下午4:28:48
     * @see com.zx.moa.wms.inve.service.IWmsInveCusIncomeService#getCusIncomeWithTransaSummary(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
    */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCusIncomeWithTransaSummary(Integer page, Integer page_size, String statics_month,
                                                             String cus_id)
    {
        // 请求对象
        RestTemplate restTemplate = new RestTemplate();
        // 请求地址
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl")
                     + "/inve/getCusIncomeWithTransaSummaryMoa.do";
        // 封装请求参数
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        if (page != null)
        {
            form.add("page", page + "");
        }
        if (page_size != null)
        {
            form.add("page_size", page_size + "");
        }
        form.add("statics_month", statics_month);
        form.add("cus_id", cus_id);
        // 请求
        return restTemplate.postForObject(url, form, Map.class);
    }

    /**
     * @Title: getCusIncomeWithSalesmanSummary
     * @Description: 远程调用WMS查询某个月团队下所有客户经理维度的收益信息集合
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param team_ids 团队id（多个用英文逗号隔开）
     * @param salesman_name 客户经理姓名或短工号
     * @return 返回某个月团队下所有客户经理维度汇总的收益信息集合
     * @author: jinzhm
     * @time:2017年1月3日 下午5:32:14
     * @see com.zx.moa.wms.inve.service.IWmsInveCusIncomeService#getCusIncomeWithSalesmanSummary(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
    */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCusIncomeWithSalesmanSummary(Integer page, Integer page_size, String statics_month,
                                                               String team_ids, String salesman_name)
    {
        // 请求对象
        RestTemplate restTemplate = new RestTemplate();
        // 请求地址
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl")
                     + "/inve/getCusIncomeWithSalesmanSummaryMoa.do";
        // 封装请求参数
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        if (page != null)
        {
            form.add("page", page + "");
        }
        if (page_size != null)
        {
            form.add("page_size", page_size + "");
        }
        form.add("statics_month", statics_month);
        form.add("team_ids", team_ids);
        form.add("salesman_name", salesman_name);
        // 请求
        return restTemplate.postForObject(url, form, Map.class);
    }

}
