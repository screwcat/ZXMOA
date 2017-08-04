package com.zx.moa.wms.inve.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInvePhoneService;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: WmsInvePhoneController
 * 模块名称：
 * @Description: 内容摘要：
 * @author DongHao
 * @date 2016年12月30日
 * @version V1.0
 * history:
 * 1、2016年12月30日 DongHao 创建文件
 */
@Controller
public class WmsInvePhoneController
{

    @Autowired
    private IWmsInvePhoneService wmsInvePhoneService;

    /**
     * 
     * @Title: phoneGetDateInfos
     * @Description: 获取年月过滤项接口
     * @return 
     * @author: DongHao
     * @time:2016年12月30日 上午11:50:45
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneGetDateInfos.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> phoneGetDateInfos()
    {
        List<Map<String, Object>> list = wmsInvePhoneService.phoneGetDateInfos();
        return ResultHelper.getSuccess(list);
    }

    /**
     * 
     * @Title: phoneGetSpecialMenuInfos
     * @Description: 获取特殊功能菜单项
     * @param one_menu_code
     * @return 
     * @author: DongHao
     * @time:2016年12月30日 下午12:57:05
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneGetSpecialMenuInfos.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> phoneGetSpecialMenuInfos(String one_menu_code, HttpServletRequest request, String version)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        return ResultHelper.getSuccess(wmsInvePhoneService.phoneGetSpecialMenuInfos(one_menu_code, personnel.getPersonnel_id(), version));
    }

    /**
     * 
     * @Title: phoneGetSpecialTeamInfos
     * @Description: 获取团队信息
     * @param dept_id
     * @param menu_code
     * @param request
     * @return 
     * @author: DongHao
     * @time:2017年1月1日 下午5:01:59
     * history:
     * 1、2017年1月1日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneGetSpecialTeamInfos.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> phoneGetSpecialTeamInfos(String dept_id, String menu_code, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        return ResultHelper.getSuccess(wmsInvePhoneService.phoneGetSpecialTeamInfos(personnel.getPersonnel_id(), dept_id, menu_code));
    }

    /**
     * 
     * @Title: phoneGetSpecialDeptInfos
     * @Description: 获取数据权限下的部门清单
     * @param dept_id
     * @param menu_code
     * @return 
     * @author: DongHao
     * @time:2017年1月3日 上午9:08:19
     * history:
     * 1、2017年1月3日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneGetSpecialDeptInfos.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> phoneGetSpecialDeptInfos(String dept_id, String menu_code, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        return ResultHelper.getSuccess(wmsInvePhoneService.phoneGetSpecialDeptInfos(personnel.getPersonnel_id().toString(), dept_id, menu_code));
    }

    /**
     * 
     * @Title: syncMePerformanceInfos
     * @Description: 同步信息
     * @param personnel_id
     * @param query_type
     * @return 
     * @author: DongHao
     * @time:2017年1月6日 上午9:03:53
     * history:
     * 1、2017年1月6日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/phoneSyncMePerformanceInfos.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<Map<String, Object>> syncMePerformanceInfos(String personnel_id, String query_type, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        List<Map<String, Object>> list = wmsInvePhoneService.syncMePerformanceInfos(personnel.getPersonnel_id().toString(), query_type);
        return list == null ? new ArrayList<Map<String, Object>>() : list;
    }

    @RequestMapping(value = "//inve/phoneGetClerkData.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Object> getClerkData(HttpServletRequest request, String version)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> resultMap = wmsInvePhoneService.getClerkData(personnel.getPersonnel_id().toString());
        if(StringUtils.isNotEmpty(version))
        {
            return ResultHelper.getSuccess((Object) resultMap);
        }
        else
        {
            return ResultHelper.getSuccess(resultMap.get("clerk_data"));
        }
    }

    /**
     * 
     * @Title: achVicegelList
     * @Description: 获取数据权限下的副总清单
     * @param menu_code
     * @return 
     * @author: DongHao
     * @time:2017年1月6日 上午11:41:49
     * history:
     * 1、2017年1月6日 DongHao 创建方法
     */
    @RequestMapping(value = "/wms/mobpt/ach_vicegel_list_v197.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getAchVicegelList(String menu_code, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        return ResultHelper.getSuccess(wmsInvePhoneService.getAchVicegelList(menu_code, personnel.getPersonnel_id().toString()));
    }

    /**
     * 
     * @Title: getTeamCommMonByDeptManager
     * @Description: 33接口 获取管理佣金按部门经理进行汇总(接口 33)
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 统计月份
     * @param dept_ids 团队id(多个团队id以","分隔)
     * @param personnel_info 部门经理姓名或者部门经理短工号
     * @param request 请求信息
     * @return 返回数据集合
     * @author: DongHao
     * @time:2017年3月10日 下午3:52:45
     * history:
     * 1、2017年3月10日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/getTeamCommMonByDeptManager.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getTeamCommMonByDeptManager(Integer page, Integer page_size, String statics_month, String dept_ids,
                                                                             String personnel_info, HttpServletRequest request)
    {
        // 获取当前moa登录人信息
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

        return ResultHelper.getSuccess(wmsInvePhoneService.getTeamCommMonByDeptManager(page, page_size, statics_month, dept_ids, personnel_info,
                                                                                       personnel));
    }

    /**
     * 
     * @Title: getTeamCommMonBySaleman
     * @Description:34接口 获取管理佣金按客户经理汇总
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 统计月份
     * @param dept_ids 团队id值(多个团队id以","分隔)
     * @param personnel_id 部门经理人员id
     * @param personnel_info 客户经理姓名或客户经理短工号
     * @param request 请求信息
     * @return 返回数据集合
     * @author: DongHao
     * @time:2017年3月13日 上午9:37:27
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/getTeamCommMonBySaleman.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getTeamCommMonBySaleman(Integer page, Integer page_size, String statics_month, String dept_ids,
                                                                         Integer personnel_id, String personnel_info, HttpServletRequest request)
    {
        // 获取当前moa登录人信息
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

        return ResultHelper.getSuccess(wmsInvePhoneService.getTeamCommMonBySaleman(page, page_size, statics_month, dept_ids, personnel_id,
                                                                                   personnel_info, personnel));
    }

    /**
     * 
     * @Title: getTeamCommMonByItem
     * @Description:35接口 获取管理佣金按佣金项汇总
     * @param statics_month 佣金统计月份
     * @param personnel_id 客户经理人员id
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:13:55
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/getTeamCommMonByItem.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getTeamCommMonByItem(String statics_month, String personnel_id, HttpServletRequest request)
    {
        // 获取当前moa登录人信息
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        // 判断人员id是否为空,如果为空则赋值为当前登录moa人员id
        if (personnel_id == null || "".equals(personnel_id))
        {
            personnel_id = personnel.getPersonnel_id().toString();
        }
        return ResultHelper.getSuccess(wmsInvePhoneService.getTeamCommMonByItem(statics_month, personnel_id));
    }

    /**
     * 
     * @Title: getTeamCommMonByData
     * @Description: 36接口 获取管理佣金按单据展现
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 佣金统计月份
     * @param personnel_id 人员id
     * @param comm_item_ids 佣金项id(01表示老佣金,02表示新佣金)
     * @param personnel_info 客户姓名
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:51:42
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/getTeamCommMonByData.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getTeamCommMonByData(Integer page, Integer page_size, String statics_month, Integer personnel_id,
                                                                      String comm_item_ids, String personnel_info, HttpServletRequest request)
    {
        // 获取当前moa登录人信息
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

        if (personnel_id == null || "".equals(personnel_id))
        {
            personnel_id = personnel.getPersonnel_id();
        }
        return ResultHelper.getSuccess(wmsInvePhoneService.getTeamCommMonByData(page, page_size, statics_month, personnel_id, comm_item_ids,
                                                                                personnel_info));
    }

    /**
     * 
     * @Title: getTeamCommMonByManager
     * @Description:37接口 获取管理佣金按客户经理汇总For副总/中分总/总经理/后线领导
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 佣金统计月份
     * @param personnel_info 客户经理姓名或客户经理的短工号
     * @param request 请求信息
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:37:32
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @RequestMapping(value = "/inve/getTeamCommMonByManager.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getTeamCommMonByManager(Integer page, Integer page_size, String statics_month,
                                                                         String personnel_info, HttpServletRequest request)
    {
        // 获取当前moa登录人信息
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

        return ResultHelper.getSuccess(wmsInvePhoneService.getTeamCommMonByManager(page, page_size, statics_month, personnel_info, personnel));
    }
}
