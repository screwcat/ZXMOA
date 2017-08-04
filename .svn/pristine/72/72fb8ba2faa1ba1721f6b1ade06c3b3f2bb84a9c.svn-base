package com.zx.moa.wms.inve.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInveSalaryPreTotalService;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：财富管理平台
 * @ClassName: WmsInveSalaryPreTotalServiceImpl
 * 模块名称：
 * @Description: 绩效工资相关业务操作类
 * @author zhangyunfei
 * @date 2017年4月19日
 * @version V1.0
 * history:
 * 1、2017年4月19日 Administrator 创建文件
 */
@Service("wmsInveSalaryPreTotalService")
public class WmsInveSalaryPreTotalServiceImpl implements IWmsInveSalaryPreTotalService
{

    private static Logger log = LoggerFactory.getLogger(WmsInveSalaryPreTotalServiceImpl.class);

    /**
     * @Title: getPerformanceSalaryApproveInfos
     * @Description: 获取绩效工资审核单据详情
     * @param wms_inve_salary_pre_total_ids 单据主键集合 以逗号分隔
     * @param bill_type_code
     * @return 
     * @author: zhangyunfei
     * @time:2017年4月19日 上午10:34:22
     * @see com.zx.moa.wms.inve.service.IWmsInveSalaryPreTotalService#getPerformanceSalaryApproveInfos(java.lang.String, java.lang.String)
     * history:
     * 1、2017年4月19日 Administrator 创建方法
    */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getPerformanceSalaryApproveInfos(String wms_inve_salary_pre_total_ids, String bill_attr)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getPerformanceSalaryApproveInfosMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("wms_inve_salary_pre_total_ids", wms_inve_salary_pre_total_ids);
        form.add("bill_attr", bill_attr);

        return restTemplate.postForObject(url, form, Map.class);
    }

    /**
     * @Title: approvePerformanceSalaryPre
     * @Description: 手持审批绩效工资
     * @param advice(审批意见)
     * @param pass(审核结果)
     * @param dept_ids(团队ids)
     * @return 
     * @author: zhangyunfei
     * @time:2017年4月24日 上午10:34:38
     * @see com.zx.moa.wms.inve.service.IWmsInveSalaryPreTotalService#approvePerformanceSalaryPre(java.lang.String, java.lang.String, java.lang.String)
     * history:
     * 1、2017年4月24日 Administrator 创建方法
    */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> approvePerformanceSalaryPre(PmPersonnel personnel, String advice, String pass, String dept_ids)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/approvePerformanceSalaryPreMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("advice", advice);
        form.add("pass", pass);
        form.add("dept_ids", dept_ids);
        form.add("personnel_shortcode", personnel.getPersonnel_shortcode());
        return restTemplate.postForObject(url, form, Map.class);
    }

}
