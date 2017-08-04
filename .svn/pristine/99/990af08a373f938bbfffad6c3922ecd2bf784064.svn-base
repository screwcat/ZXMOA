package com.zx.moa.wms.inve.web;

import java.util.List;
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
import com.zx.moa.wms.inve.service.IWmsInveRedeemService;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: WmsInvePhoneRedeemController
 * 模块名称：
 * @Description: 内容摘要：
 * @author DongHao
 * @date 2016年12月7日
 * @version V1.0
 * history:
 * 1、2016年12月7日 DongHao 创建文件
 */
@Controller
public class WmsInvePhoneRedeemController
{

    @Autowired
    private IWmsInveRedeemService wmsInveRedeemService;

    /**
     * 
     * @Title: phoneGetPendingApprovalInfoCount
     * @Description: app首页查询当前登录人的待办任务的数量
     * @param personnel_shortCode 当前登录人的短工号
     * @param searchInfo 查询条件(客户经理姓名/客户经理短工号/客户姓名)
     * @return  
     * @author: DongHao
     * @time:2016年12月7日 下午1:22:01
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneGetPendingApprovalInfoCountMoa.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> phoneGetPendingApprovalInfoCount(String session_id, String searchInfo, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String personnel_shortCode = personnel.getPersonnel_shortcode();
        return ResultHelper.getSuccess(wmsInveRedeemService.phoneGetPendingApprovalInfoCount(personnel_shortCode, searchInfo));
    }

    /**
     * 
     * @Title: phoneGetRedeemByQueryInfo
     * @Description: 根据条件查询对应的赎回信息
     * @param personnel_shortCode app端登录人的短工号
     * @param searchInfo 查询条件(客户经理姓名/客户经理短工号/客户姓名)
     * @param query_type 查询的单据状态(1:全部;2:带我审批;3:与我相关;4:审批中;5:已完成;6:已撤销(已终止))
     * @param version 版本号
     * @return 返回结果的list
     * @author: DongHao
     * @time:2016年12月7日 下午1:25:33
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneGetRedeemByQueryInfoMoa.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> phoneGetRedeemByQueryInfo(String session_id, String searchInfo, String query_type, Integer page, Integer page_size,String version, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String personnel_shortCode = personnel.getPersonnel_shortcode();
        return ResultHelper.getSuccess(wmsInveRedeemService.phoneGetRedeemByQueryInfo(personnel_shortCode, searchInfo, query_type, page, page_size, version));
    }

    /**
     * 
     * @Title: phoneQueryType
     * @Description: 获取查询类别
     * @param personnel_shortCode
     * @return 
     * @author: sunlq
     * @time:2016年12月8日 下午1:46:13
     * history:
     * 1、2016年12月8日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneQueryType.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> phoneQueryType(String session_id, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String personnel_shortCode = personnel.getPersonnel_shortcode();
        return ResultHelper.getSuccess(wmsInveRedeemService.phoneQueryType(personnel_shortCode));
    }

}
