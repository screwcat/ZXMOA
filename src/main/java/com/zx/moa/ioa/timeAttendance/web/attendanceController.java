package com.zx.moa.ioa.timeAttendance.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.timeAttendance.service.IAttendanceService;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;

@Controller
public class attendanceController {
	
	@Autowired IAttendanceService iAttendanceService;
	
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    /**
    * 我的考勤记录
    * @param request
    * @param month_add (当月0(默认)，上月-1)
    * @return
    */
	@RequestMapping(value="ioa/getMyAttendancelList.do", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultBean<List<Map<String, Object>>> getAttendance(HttpServletRequest request,int month_add){
		if(month_add == 0 || month_add == -1){
	        return ResultHelper.getSuccess(iAttendanceService.getAttendanceList(request, month_add));
		}else{
            return ResultHelper.getError("请传入正确参数", null);
		}
	}
	
    /**
     * @Title: getAttendance
     * @Description:   MOA_OUT_003 读取申请表单
     * @param request
     * @param apply_type_id
     * @return 
     * @author: zhaowei
     * @time:2017年7月5日 下午2:58:13
     * history:
     * 1、2017年7月5日 zhaowei 创建方法
    */
    @RequestMapping(value = "ioa/getApplyMenu.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getOrderAppleFrom(HttpServletRequest request, int apply_type_id)
    {
        // 30请假申请、27补签申请、29调休申请、28.酬勤申请、52.出差申请、64.项目酬勤申请
        int[] arr = { 30, 27, 29, 28, 52, 64 };

        // 输入参数是否正确标示 默认false
        Boolean bool = false;
        for (int i = 0; i < arr.length; i++)
        {
            if (apply_type_id == arr[i])
            {
                // 正确置成ture
                bool = true;
            }
        }
        if (bool)
        {
            return ResultHelper.getSuccess(iAttendanceService.getOrderAppleFrom(request, apply_type_id));
        }
        else
        {
            return ResultHelper.getError("请传入正确参数", null);
        }
    }

    /**
     * @Title: getCountTime
     * @Description: TODO(MOA_OUT_005 获取总计时间和额度)
     * @param request
     * @param month_add  start_time end_time  类型为'2017-06-05 09:15'
     * @return 
     * @author: quwenrui
     * @time:2017年7月5日 上午10:33:09
     * history:
     * 1、2017年7月5日 quwenrui 创建方法
    */
    @RequestMapping(value = "ioa/getCountTime.do", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> getCountTime(HttpServletRequest request, int apply_type_id, String start_time, String end_time)
    {
        // apply_type_id:事假31、病假32、年假33、产假34、婚假35、丧假36、工伤假37、哺乳假38、护理假39、路程假54、27补签申请、29调休申请、28.酬勤申请、52.出差申请、64.项目酬勤申请
        return ResultHelper.getSuccess(iAttendanceService.getCountTime(request, apply_type_id, start_time, end_time));
    }

    /* // zw测试保存校验接口
     @RequestMapping(value = "ioa/testW.do", method = { RequestMethod.GET })
     @ResponseBody
     public String aaa(HttpServletRequest request)
     {
         return iAttendanceService.verificationSave(request, 34, "2017-09-01 10:00", "2018-01-10 18:00", 1055);
     }*/
    
    /**
     * 
     * @Title: getAttendanceApplyMenu
     * @Description: TODO(MOA_OUT_002 读取考勤申请菜单)
     * @param request
     * @return 
     * @author: quwenrui
     * @time:2017年7月11日 下午8:08:44
     * history:
     * 1、2017年7月11日 quwenrui 创建方法
     */
    @RequestMapping(value = "ioa/getAttendanceApplyMenu.do", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getAttendanceApplyMenu(HttpServletRequest request)
    {
        return ResultHelper.getSuccess(iAttendanceService.getAttendanceApplyMenu(request));
    }

    /**
     * 
     * @Title: getShiftInfoByFillCheckTime
     * @Description: TODO(MOA_OUT_004 根据补签日期查询班次)
     * @param request
     * @param fillcheck_time补签申请时间(yyyy-MM-dd)
     * @return 
     * @author: quwenrui
     * @time:2017年7月11日 下午8:05:52
     * history:
     * 1、2017年7月11日 quwenrui 创建方法
     */
    @RequestMapping(value = "ioa/getShiftInfoByFillCheckTime.do", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> getShiftInfoByFillCheckTime(HttpServletRequest request, String fillcheck_time)
    {

        return iAttendanceService.getShiftInfoByFillCheckTime(request, fillcheck_time);

    }

    /**
     * @Title: saveAttendanceApplyInfo
     * @Description: TODO(保存考勤申请信息)
     * @param request
     * @return 
     * @author: scf
     * @time:2017年7月6日 上午11:36:06
     * history:
     * 1、2017年7月6日 scf 创建方法
     */
    @RequestMapping(value = "ioa/saveAttendanceApplyInfo.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> saveAttendanceApplyInfo(HttpServletRequest request)
    {
        return iAttendanceService.saveAttendanceApplyInfo(request);
    }

    /**
     * @Title: revokeOrder
     * @Description: TODO(撤销单据)
     * @param request
     * @return 
     * @author: scf
     * @time:2017年7月6日 下午4:50:04
     * history:
     * 1、2017年7月6日 scf 创建方法
     */
    @RequestMapping(value = "ioa/revokeOrder.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> revokeOrder(HttpServletRequest request)
    {
        return iAttendanceService.revokeOrder(request);
    }

}
