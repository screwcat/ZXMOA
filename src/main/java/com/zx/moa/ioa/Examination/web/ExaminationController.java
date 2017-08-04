package com.zx.moa.ioa.Examination.web;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.Examination.servie.IexaminationService;
import com.zx.moa.ioa.util.StringUtil;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInvePhoneService;
import com.zx.moa.wms.inve.service.IWmsInveRedeemService;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: ExaminationController
 * 模块名称：MOA首页Controller
 * @Description: 内容摘要：MOA首页
 * @author zhaowei
 * @date 2016年12月8日
 * @version V1.7.7
 * history:
 * 1、2016年12月8日 ZhaoWei 创建文件
 */
@Controller
public class ExaminationController
{

    @Autowired
    private IWmsInveRedeemService wmsInveRedeemService;

    @Autowired
    private IexaminationService iexaminationService;

    @Autowired
    private IWmsInvePhoneService wmsInvePhoneService;

    @RequestMapping(value = "/ioa/getApproveList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getApproveList(@RequestParam(required = false)
    String v, HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        map.put("personnel_id", personnel.getPersonnel_id());
        String page = request.getParameter("page");
        String size = request.getParameter("size");

        String drop = request.getParameter("drop");
        String search = request.getParameter("search");

        if (StringUtils.isNotBlank(page))
        {
            map.put("page", Integer.parseInt(page));
        }
        if (StringUtils.isNotBlank(size))
        {
            map.put("size", Integer.parseInt(size));
        }
        if (StringUtils.isNotBlank(search))
        {
            map.put("search", search);
        }
        if (StringUtils.isNotBlank(drop))
        {
            map.put("drop", drop);
        }
        else
        {
            map.put("drop", "0");
        }

        List<Map<String, Object>> result = null;
        try
        {
            if (v == null)
            {
                result = iexaminationService.getApproveListByLoginUser(map);
            }
            // 此功能支持最低版本,在此下边加else if

            else if (StringUtil.compareVersion(v, "1.1.4") >= 0)
            {
                // 此功能支持最低版本
                result = iexaminationService.getApproveListByLoginUserV114(map);
            }
            else
            {
                result = iexaminationService.getApproveListByLoginUser(map);
            }

            return ResultHelper.getSuccess(result);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultHelper.getError(result);
        }

    }

    @RequestMapping(value = "/ioa/getOrderList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> approveOrder(@RequestParam(required = false)
    String v, HttpServletRequest request)
    {

        Map<String, Object> map = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        map.put("personnel_id", personnel.getPersonnel_id());
        map.put("personnel_shortcode", personnel.getPersonnel_shortcode());
        String page = request.getParameter("page");
        String size = request.getParameter("size");

        if (StringUtils.isNotBlank(page))
        {
            map.put("page", Integer.parseInt(page));
        }
        if (StringUtils.isNotBlank(size))
        {
            map.put("size", Integer.parseInt(size));
        }
        String drop = request.getParameter("drop");
        String search = request.getParameter("search");
        if (StringUtils.isNotBlank(search))
        {
            map.put("search", search);
        }
        if (StringUtils.isNotBlank(drop))
        {
            map.put("drop", drop);
        }
        map.put("isApp", true);

        List<Map<String, Object>> result = null;
        try
        {
            if (v == null)
            {
                result = iexaminationService.getApproveListBydaiUser(map);
            }
            // 此功能支持最低版本,在此下边加else if

            else if (StringUtil.compareVersion(v, "1.1.4") >= 0)
            {
                result = iexaminationService.getApproveListBydaiUserV114(map);
            }
            else
            {
                result = iexaminationService.getApproveListBydaiUser(map);
            }

            return ResultHelper.getSuccess(result);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultHelper.getError(result);
        }

    }

    /**
     * @Title: getApproveIndexList
     * @Description: 用户查看列表中行政审批单据列表
     * @param request
     * @return 
     * @author: ZhaoWei
     * @time:2016年12月8日 下午2:22:09
     * history:
     * 1、2016年12月8日 ZhaoWei 创建方法
     * 2、2016年12月30日 sunlq 修改方法 v1.1.9 
     */
    @RequestMapping(value = "/ioa/getApproveIndexList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getApproveIndexList(@RequestParam(required = false)
    String v, HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        map.put("personnel_id", personnel.getPersonnel_id());
        List<Map<String, Object>> result = null;
        Map<String, Object> m = new HashMap<String, Object>();
        int order_total_number = 0;
        int business_total_number = 0;
        int custom_total_number = 0;

        if (v == null)
        {
            String page = request.getParameter("page");
            String size = request.getParameter("size");

            if (StringUtils.isNotBlank(page))
            {
                map.put("page", Integer.parseInt(page));
            }
            if (StringUtils.isNotBlank(size))
            {
                map.put("size", Integer.parseInt(size));
            }

            // 这个问WMS要方法 特别注意下
            try
            {
                Map<String, Object> mm = wmsInveRedeemService.phoneGetPendingApprovalInfoCount(personnel.getPersonnel_shortcode(), "");
                if (mm != null)
                {
                    business_total_number = Integer.parseInt(mm.get("count").toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                result = iexaminationService.getApproveListByLoginUserNew(map);
                order_total_number = iexaminationService.getApproveListByLoginUserCount(map);
                m.put("applyList", result);
                m.put("order_total_number", order_total_number);
                m.put("business_total_number", business_total_number);
                return ResultHelper.getSuccess(m);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                m.put("applyList", result);
                m.put("order_total_number", order_total_number);
                m.put("business_total_number", business_total_number);
                return ResultHelper.getError(m);
            }
        }
        else
        {
            int max = 20;
            try
            {
                List<Map<String, Object>> applyList = new LinkedList<Map<String, Object>>();
                // 此功能支持最低版本,在此下边加else if
                if (StringUtil.compareVersion(v, "1.1.9") >= 0)
                {
                    /**
                     * 取业务待办，返回全部
                     * */
                    try
                    {
                        result = wmsInvePhoneService.syncMePerformanceInfos(personnel.getPersonnel_id().toString(), "1");
                        if (result != null && result.size() > 0)
                        {
                            business_total_number = result.size();
                            applyList.addAll(result);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    /**
                     * 取行政待办，返回全部
                     * */
                    try
                    {
                        result = iexaminationService.getApproveListByLoginUserV119(map);
                        if (result != null && result.size() > 0)
                        {
                            order_total_number = result.size();
                            if (applyList.size() < max)
                            {
                                int s = max - applyList.size();
                                s = s > result.size() ? result.size() : s;
                                List<Map<String, Object>> subList = result.subList(0, s);
                                applyList.addAll(subList);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    /**
                     * 取业务与我相关，最多返回20条
                     * */
                    try
                    {
                        if (applyList.size() < max)
                        {
                            result = wmsInvePhoneService.syncMePerformanceInfos(personnel.getPersonnel_id().toString(), "2");
                            if (result != null && result.size() > 0)
                            {
                                int s = max - applyList.size();
                                s = s > result.size() ? result.size() : s;
                                List<Map<String, Object>> subList = result.subList(0, s);
                                applyList.addAll(subList);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    /**
                     * 取行政与我相关
                     * */
                    try
                    {
                        if (applyList.size() < max)
                        {
                            int s = max - applyList.size();
                            map.put("size", s);
                            result = iexaminationService.getOtherListByLoginUserV119(map);
                            applyList.addAll(result);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    /**
                     * 取排队数量
                     * */
                    try
                    {
                        Map<String, Object> temp = wmsInvePhoneService.getClerkDataCount(personnel.getPersonnel_id() + "");
                        if (temp != null && temp.get("count") != null)
                        {
                            custom_total_number = Integer.parseInt(temp.get("count").toString());
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                m.put("applyList", applyList);
                m.put("order_total_number", order_total_number);
                m.put("business_total_number", business_total_number);
                m.put("custom_total_number", custom_total_number);
                return ResultHelper.getSuccess(m);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResultHelper.getError(null);
            }
        }
    }

}
