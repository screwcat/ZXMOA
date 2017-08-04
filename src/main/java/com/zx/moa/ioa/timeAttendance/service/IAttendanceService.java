package com.zx.moa.ioa.timeAttendance.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zx.moa.util.bean.ResultBean;

/**
 * 我的考勤接口
 * @author zWei
 *
 */
public interface IAttendanceService {

    // 获取考勤列表
	public List<Map<String, Object>> getAttendanceList(HttpServletRequest request,int month_add);

    public void timeAttendance() throws InterruptedException;

    /**
     * @Title: getAttendanceList
     * @Description: MOA_OUT_003 读取申请表单
     * @param request
     * @param apply_type_id
     * @return 
     * @author: zhaowei
     * @time:2017年7月5日 下午3:35:26
     * history:
     * 1、2017年7月5日 zhaowei 创建方法
    */
    public Map<String, Object> getOrderAppleFrom(HttpServletRequest request, int apply_type_id);

    /**
     * @Title: saveAttendanceApplyInfo
     * @Description: TODO(保存考勤申请信息)
     * @param request
     * @return 
     * @author: scf
     * @time:2017年7月7日 下午2:02:36
     * history:
     * 1、2017年7月7日 scf 创建方法
     */
    public ResultBean<List<Map<String, Object>>> saveAttendanceApplyInfo(HttpServletRequest request);

    /**
     * @Title: revokeOrder
     * @Description: TODO(撤销单据)
     * @param request
     * @return 
     * @author: scf
     * @time:2017年7月7日 下午2:02:38
     * history:
     * 1、2017年7月7日 scf 创建方法
     */
    public ResultBean<List<Map<String, Object>>> revokeOrder(HttpServletRequest request);

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
    public Map<String, Object> getCountTime(HttpServletRequest request, int apply_type_id, String start_time, String end_time);

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
    public List<Map<String, Object>> getAttendanceApplyMenu(HttpServletRequest request);

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
    public ResultBean<Map<String, Object>> getShiftInfoByFillCheckTime(HttpServletRequest request, String fillcheck_time);

    public String verificationSave(HttpServletRequest request, int apply_type_id, String start_time, String end_time, int count_time);

}