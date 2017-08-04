package com.zx.moa.wms.inve.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInveCusIncomeService;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：财富管理平台
 * @ClassName: WmsInveCusIncomeController
 * 模块名称：客户收益控制器
 * @Description: 内容摘要：按团队查找客户收益，按业务员查找客户收益等
 * @author jinzhm
 * @date 2017年1月3日
 * @version V1.0
 * history:
 * 1、2017年1月3日 jinzhm 创建文件
 */
@Controller
public class WmsInveCusIncomeController
{

    @Autowired
    private IWmsInveCusIncomeService wmsInveCusIncomeService;

    /**
     * @Title: getCusIncomeWithCustomerSummary
     * @Description: 获取客户收益按客户汇总（查询我的客户收益或部门经理查询团队下某个业务员的客户收益）
     *      关联菜单项：团队业绩-客户收益/我的业绩-客户收益；
     * @param request 请求信息
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param salesman_id 客户经理id
     * @param cus_name 要查询的客户名称（精确查询）
     * @return 按客户维度汇总的某个客户经理的客户收益信息
     * @author: jinzhm
     * @time:2017年1月3日 下午2:44:37
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
     */
    @RequestMapping(value = "/inve/getCusIncomeWithCustomerSummary.do", method = { RequestMethod.GET,
                                                                                  RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getCusIncomeWithCustomerSummary(HttpServletRequest request, Integer page,
                                                                           Integer page_size, String statics_month,
                                                                           String salesman_id, String cus_name)
    {
        // 如果传过来的客户经理id是null，就取当前登录用户信息的id
        if (StringUtil.isEmpty(salesman_id))
        {
            // 获得当前登录用户id
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            salesman_id = personnel.getPersonnel_id() + "";
        }
        return ResultHelper.getSuccess(wmsInveCusIncomeService.getCusIncomeWithCustomerSummary(page, page_size,
                                                                                               statics_month,
                                                                                               salesman_id, cus_name));
    }

    /**
     * @Title: getCusIncomeWithTransaSummary
     * @Description: 查询客户收益在每一单上的收益信息
     * @param request 请求信息
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param cus_id 客户id
     * @return 返回一个客户在某一个月的单据收益信息
     * @author: jinzhm
     * @time:2017年1月3日 下午4:25:00
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
     */
    @RequestMapping(value = "/inve/getCusIncomeWithTransaSummary.do", method = { RequestMethod.GET,
                                                                                  RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getCusIncomeWithTransaSummary(HttpServletRequest request, Integer page,
                                                                         Integer page_size, String statics_month,
                                                                         String cus_id)
    {
        return ResultHelper.getSuccess(wmsInveCusIncomeService.getCusIncomeWithTransaSummary(page, page_size,
                                                                                             statics_month, cus_id));
    }

    /**
     * @Title: getCusIncomeWithSalesmanSummary
     * @Description: 根据团队查询某个月团队下按客户经理维度汇总的收益信息
     * @param request 请求信息
     * @param page 要查询页数
     * @param page_size 每页显示计数数
     * @param statics_month 查询月份
     * @param team_id 团队id（多个用英文逗号隔开）
     * @param salesman_name 客户经理姓名或短工号，支持精确查询
     * @return 返回某个月团队下按客户经理维度汇总的收益信息
     * @author: jinzhm
     * @time:2017年1月3日 下午5:25:22
     * history:
     * 1、2017年1月3日 jinzhm 创建方法
     */
    @RequestMapping(value = "/inve/getCusIncomeWithSalesmanSummary.do", method = { RequestMethod.GET,
                                                                                  RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getCusIncomeWithSalesmanSummary(HttpServletRequest request, Integer page,
                                                                           Integer page_size, String statics_month,
                                                                           String team_ids, String salesman_name)
    {
        return ResultHelper.getSuccess(wmsInveCusIncomeService.getCusIncomeWithSalesmanSummary(page, page_size,
                                                                                               statics_month, team_ids,
                                                                                               salesman_name));
    }
}
