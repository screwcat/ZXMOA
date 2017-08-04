package com.zx.moa.wms.inve.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInveSalaryPreTotalService;

@Controller
public class WmsInveSalaryPreTotalController {
	private static Logger log = LoggerFactory.getLogger(WmsInveSalaryPreTotalController.class);
	
	@Autowired
    private IWmsInveSalaryPreTotalService wmsInveSalaryPreTotalService;

    /**
     * @Title: getSalarySetProcessInfos
     * @Description: 获取绩效工资审核单据详情
     * @param wms_inve_salary_pre_total_ids 单据主键集合 以逗号分隔
     * @param bill_attr
     * @return 
     * @author: zhangyunfei
     * @time:2017年4月19日 上午10:29:43
     * history:
     * 1、2017年4月19日 Administrator 创建方法
     */
    @RequestMapping(value = "/inve/getPerformanceSalaryApproveInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPerformanceSalaryApproveInfos(String wms_inve_salary_pre_total_ids, String bill_attr)
    {
        
        Map<String, Object> map = wmsInveSalaryPreTotalService.getPerformanceSalaryApproveInfos(wms_inve_salary_pre_total_ids, bill_attr);

        return map;
    }

    /**
     * 
     * @Title: approvePerformanceSalaryPre
     * @Description: 手持审批绩效工资
     * @param advice(审批意见)
     * @param pass(审核结果)
     * @param dept_ids(团队ids)
     * @return 
     * @author: zhangyunfei
     * @time:2017年4月24日 上午10:33:35
     * history:
     * 1、2017年4月10日 Administrator 创建方法
     */
    @RequestMapping(value = "/inve/approvePerformanceSalaryPre.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> approvePerformanceSalaryPre(HttpServletRequest request, String advice, String pass, String dept_ids)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

        Map<String, Object> map = wmsInveSalaryPreTotalService.approvePerformanceSalaryPre(personnel, advice, pass, dept_ids);

        return map;
    }

}