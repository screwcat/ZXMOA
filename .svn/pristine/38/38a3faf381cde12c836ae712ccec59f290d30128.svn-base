package com.zx.moa.ioa.timeAttendance.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zx.moa.ioa.timeAttendance.persist.AttendanceDao;
import com.zx.moa.ioa.timeAttendance.service.IAttendanceService;
import com.zx.moa.ioa.util.CheckingUtil;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.ioa.util.PushManage;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.SysDept;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：
 * @ClassName: IAttendanceServiceImpl
 * 模块名称：
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2017年9月2日
 * @version V1.0
 * history:
 * 1、2017年9月2日 zhaowei 创建文件
 */
@Service("IAttendanceService")
public class IAttendanceServiceImpl implements IAttendanceService
{

    @Autowired
    private AttendanceDao attendanceDao;

    private final String QUARTER[][] = new String[][] { { "10", "11", "12" }, { "01", "02", "03" }, { "04", "05", "06" }, { "07", "08", "09" } };

    private final String TYPE[] = new String[] { "weekend", "holiday", "weekendPO", "holidayPO", "nightTime" };

    private final Integer HOUR = 60;

    @Override
    public List<Map<String, Object>> getAttendanceList(HttpServletRequest request, int month_add)
    {
        List<Map<String, Object>> reslist = null;
        String date_start = "";
        String date_end = "";
        // 获取当前登录人
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (month_add == 0)
        {// 当月
         // 获取当前月第一天：
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, month_add);
            c.set(Calendar.DAY_OF_MONTH, 1);
            date_start = format.format(c.getTime());

            // 获取当前月最后一天
            Calendar ca = Calendar.getInstance();
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
            date_end = format.format(ca.getTime());
        }
        else
        {// 上月
         // 获取前月的第一天
            Calendar cal_1 = Calendar.getInstance();
            cal_1.add(Calendar.MONTH, month_add);
            cal_1.set(Calendar.DAY_OF_MONTH, 1);
            date_start = format.format(cal_1.getTime());
            // 获取前月的最后一天
            Calendar cale = Calendar.getInstance();
            cale.add(Calendar.MONTH, month_add + 1);
            cale.set(Calendar.DAY_OF_MONTH, 0);
            date_end = format.format(cale.getTime());
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("attendance_code", pm.getPersonnel_shortcode());
        map.put("date_start", date_start);
        map.put("date_end", date_end);

        reslist = attendanceDao.getAttendanceList(map);

        for (Map<String, Object> ma : reslist)
        {
            if (ma.get("attendance_workEnd") == null)
            {
                ma.put("attendance_workEnd", "");
            }

            if (ma.get("attendance_workStart") == null)
            {
                ma.put("attendance_workStart", "");
            }
        }

        for (Map<String, Object> m : reslist)
        {
            // 根据上班日期 往map放星期
            try
            {
                m.put("attendance_week", getWeekday(m.get("attendance_date").toString()));
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }

            /*switch (Integer.parseInt(m.get("attentdance_status").toString())) {
            case 0:
            case 3:
            m.put("attentdance_status", "03");
            m.put("attentdance_status_name", "正常");
            case 1:
            case 2:
            case 8:
            case 11:
            case '3,1':
            case '1,3':
            m.put("attentdance_status", "128");
            m.put("attentdance_status_name", "未打卡");
            case 9:
            case 14:
            case '3,9':
            case '9,3':
            }*/

            if ("0".equals(m.get("attentdance_status").toString()) || "3".equals(m.get("attentdance_status").toString()) || "5".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "03");
                m.put("attentdance_status_name", "正常");
            }
            else if ("1".equals(m.get("attentdance_status").toString()) || "2".equals(m.get("attentdance_status").toString()) || "8".equals(m.get("attentdance_status").toString()) || "11".equals(m.get("attentdance_status").toString()) || "3,1".equals(m.get("attentdance_status").toString()) || "1,3".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "128");
                m.put("attentdance_status_name", "未打卡");
            }
            else if ("9".equals(m.get("attentdance_status").toString()) || "14".equals(m.get("attentdance_status").toString()) || "3,9".equals(m.get("attentdance_status").toString()) || "9,3".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "9");
                m.put("attentdance_status_name", "迟到");
            }
            else if ("10,3".equals(m.get("attentdance_status").toString()) || "3,10".equals(m.get("attentdance_status").toString()) || "10".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "10");
                m.put("attentdance_status_name", "早退");
            }
            else if ("2,9".equals(m.get("attentdance_status").toString()) || "9,2".equals(m.get("attentdance_status").toString()) || "13".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "92");
                m.put("attentdance_status_name", "迟到、未打卡");
            }
            else if ("1,10".equals(m.get("attentdance_status").toString()) || "10,1".equals(m.get("attentdance_status").toString()) || "12".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "110");
                m.put("attentdance_status_name", "未打卡、早退");
            }
            else if ("9,10".equals(m.get("attentdance_status").toString()) || "10,9".equals(m.get("attentdance_status").toString()) || "9,10,3".equals(m.get("attentdance_status").toString()) || "10,9,3".equals(m.get("attentdance_status").toString()) || "15".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "910");
                m.put("attentdance_status_name", "迟到、早退");
            }
            else if ("4".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "4");
                m.put("attentdance_status_name", "请假");
            }
            else if ("6".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "6");
                m.put("attentdance_status_name", "调休");
            }
            else if ("7".equals(m.get("attentdance_status").toString()))
            {
                m.put("attentdance_status", "7");
                m.put("attentdance_status_name", "出差");
            }

        }
        return reslist;
    }

    /**
    * 指定日期 判断是星期几
    * @param date
    * @return
    */
    public static String getWeekday(String date)
    {// 必须yyyy-MM-dd

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat sdw = new SimpleDateFormat("E", Locale.CHINESE);

        java.util.Date d = null;

        try
        {

            d = sd.parse(date);

        }
        catch (ParseException e)
        {

            e.printStackTrace();

        }

        return sdw.format(d);

    }

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
    @SuppressWarnings("null")
    @Override
    public Map<String, Object> getOrderAppleFrom(HttpServletRequest request, int apply_type_id)
    {
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        String personnel_state = pm.getPersonnel_state();
        String deptCode = attendanceDao.getDept_code(pm.getPersonnel_deptid());
        Integer fillCheck_flag = -1;
        // 事假31、病假32、年假33、产假34、婚假35、丧假36、工伤假37、哺乳假38、护理假39、路程假54、27补签申请、29调休申请、28.酬勤申请、52.出差申请、64.项目酬勤申请
        // 请假限制集合
        List<Map<String, Object>> holiList = findVacateSetting(pm, apply_type_id);
        // 返回集合(一级)
        Map<String, Object> resMap = new HashMap<String, Object>();
        // 申请类型(二级)
        List<Map<String, Object>> apply_type_list = new ArrayList<Map<String, Object>>();

        // 班次信息(默认当天)
        List<Map<String, Object>> shiftMap = getVolumeInfo(CheckingUtil.getNow(), CheckingUtil.getNow(), pm.getPersonnel_id());
        // 没有班次 直接返回
        if (shiftMap != null && shiftMap.size() < 0)
        {
            return null;
        }
        String day_type = shiftMap.get(0).get("day_type").toString(); // 0工作1休息2节假
        String start = "";
        String off = "";
        String start1 = shiftMap.get(0).get("start1").toString();
        String start2 = shiftMap.get(0).get("start2") == null ? "" : shiftMap.get(0).get("start2").toString();
        String off1 = shiftMap.get(0).get("off1").toString();
        String off2 = shiftMap.get(0).get("off2") == null ? "" : shiftMap.get(0).get("off2").toString();
        String rest1 = shiftMap.get(0).get("rest1").toString();
        String rest2 = shiftMap.get(0).get("rest2").toString();
        // 总计时间
        Integer count_time = 0;
        // 是时间段 上班时间取后面的
        if (start2 == "" || start2 == null)
        {
            start = start1;
        }
        else
        {
            start = start2;
        }
        // 是时间段 下班时间取后面的
        if (off2 == "" || off2 == null)
        {
            off = off1;
        }
        else
        {
            off = off2;
        }

        // 请假类
        if (apply_type_id == 30)
        {
            // 试用员工 只有事假病假
            if ("0".equals(personnel_state) || "2".equals(personnel_state))
            {
                // 事假放入集合
                addResList(apply_type_list, holiList, 31, shiftMap, pm);
                // 病假放入集合
                addResList(apply_type_list, holiList, 32, shiftMap, pm);

            }
            else if ("1".equals(personnel_state))
            {
                // 男员工没有产假哺乳假期 女员工没有护理假
                String personnel_sex = pm.getPersonnel_sex();
                if ("0".equals(personnel_sex))
                {
                    // 男
                    addResList(apply_type_list, holiList, 31, shiftMap, pm);
                    addResList(apply_type_list, holiList, 32, shiftMap, pm);
                    // 年假只能只智能跟互联网才有
                    if (deptCode.substring(0, 3).equals("DZN") || deptCode.substring(0, 3).equals("DHL"))
                    {
                        addResList(apply_type_list, holiList, 33, shiftMap, pm);
                    }
                    addResList(apply_type_list, holiList, 35, shiftMap, pm);
                    addResList(apply_type_list, holiList, 36, shiftMap, pm);
                    addResList(apply_type_list, holiList, 37, shiftMap, pm);
                    addResList(apply_type_list, holiList, 39, shiftMap, pm);
                    addResList(apply_type_list, holiList, 54, shiftMap, pm);
                }
                else
                {
                    // 女
                    addResList(apply_type_list, holiList, 31, shiftMap, pm);
                    addResList(apply_type_list, holiList, 32, shiftMap, pm);
                    // 年假只能只智能跟互联网才有
                    if (deptCode.substring(0, 3).equals("DZN") || deptCode.substring(0, 3).equals("DHL"))
                    {
                        addResList(apply_type_list, holiList, 33, shiftMap, pm);
                    }
                    addResList(apply_type_list, holiList, 34, shiftMap, pm);
                    addResList(apply_type_list, holiList, 35, shiftMap, pm);
                    addResList(apply_type_list, holiList, 36, shiftMap, pm);
                    addResList(apply_type_list, holiList, 37, shiftMap, pm);
                    addResList(apply_type_list, holiList, 38, shiftMap, pm);
                    addResList(apply_type_list, holiList, 54, shiftMap, pm);
                }
            }

            // 限制集合
            Map<String, Object> thingMap = getSelectBase(holiList, 31);
            // 公休假标示1有效0无效(默认无效 下面获取)
            String general_holiday = "0";
            // 法定假标示1有效0无效(默认无效 下面获取)
            String legal_holiday = "0";
            // 如果公休假不是空 就获取限制中的配置
            if (thingMap.get("general_holiday") != null)
            {
                general_holiday = thingMap.get("general_holiday").toString();
            }
            // 如果法定假不是空 就获取限制中的配置
            if (thingMap.get("legal_holiday") != null)
            {
                legal_holiday = thingMap.get("legal_holiday").toString();
            }
            count_time = Integer.parseInt(getRealTime(start, off, rest1, rest2, general_holiday, legal_holiday, day_type, false) + "");
            resMap.put("fillCheck_flag", fillCheck_flag);
        }
        // 其余类型
        else
        {
            // 时间差
            Integer work = Integer.parseInt(getRealTime(start, off, rest1, rest2, "1", "1", day_type, true) + "");
            // 当前月份第一天跟最后一天
            Map<String, Object> MonthFeistAndFinal = CheckingUtil.getMonthFeistAndFinal();
            Integer holi_day = -1;
            Integer holi_count = -1;
            Map<String, Object> fillcheck = new HashMap<String, Object>();
            // 限制
            Map<String, Object> fillBase = new HashMap<String, Object>();
            if (holiList != null && holiList.size() > 0)
            {
                fillBase = holiList.get(0);
            }
            // 公休假标示1有效0无效(默认无效 下面获取)
            String general_holiday = "0";
            // 法定假标示1有效0无效(默认无效 下面获取)
            String legal_holiday = "0";
            // 如果公休假不是空 就获取限制中的配置
            if (fillBase.get("general_holiday") != null)
            {
                general_holiday = fillBase.get("general_holiday").toString();
            }
            // 如果法定假不是空 就获取限制中的配置
            if (fillBase.get("legal_holiday") != null)
            {
                legal_holiday = fillBase.get("legal_holiday").toString();
            }
            // 说明有限制
            if (fillBase != null)
            {
                // 额度限制
                if (fillBase.get("holi_day") != null)
                {
                    // 调休特殊处理
                    if (apply_type_id == 29)
                    {
                        Map<String, Object> md = findDaysoffHistory(pm.getPersonnel_shortcode());

                        String year = CheckingUtil.getNow().split("-")[0];
                        String month = CheckingUtil.getNow().split("-")[1];
                        double use = Double.parseDouble(md.get("calculateAll").toString());
                        double pass = Double.parseDouble(md.get("T" + year + "" + month).toString());
                        double Nopass = Double.parseDouble(md.get("N" + year + "" + month).toString());
                        double his = 0;
                        Map<String, Object> flowMap = (Map) md.get("YM" + year + "" + month);
                        his = Double.parseDouble(flowMap.get("total").toString());
                        his += pass;
                        double set = Double.parseDouble(fillBase.get("holi_day").toString()) * work;

                        // holi_day = parseInt((his + "").split("\\.")[0]) -
                        // parseInt((use + "").split("\\.")[0]);
                        holi_day = Integer.parseInt((Math.ceil(his - use) + "").split("\\.")[0]);
                        Integer no = Integer.parseInt((Math.ceil(set - Nopass) + "").split("\\.")[0]);
                        if (no > holi_day)
                        {
                            holi_day = holi_day;
                        }
                        else
                        {
                            holi_day = no;
                        }
                        holi_day = holi_day < 0 ? 0 : holi_day;

                        count_time = Integer.parseInt(getRealTime(start, off, rest1, rest2, general_holiday, legal_holiday, day_type, false) + "");
                    }
                }
                // 补卡count是永远无效的 day放的是次数 将错就错
                if (apply_type_id == 27)
                {
                    // 次数限制
                    if (fillBase.get("holi_day") != null)
                    {
                        // 总次数
                        holi_count = Integer.parseInt(fillBase.get("holi_day").toString());
                        holi_count -= findCountFillHistory(pm.getPersonnel_id() + "", CheckingUtil.getMonthFeistAndFinal().get("date_start").toString(), CheckingUtil.getMonthFeistAndFinal().get("date_end").toString());
                        if (holi_count < 0)
                        {
                            holi_count = 0;
                        }
                    }
                }
                else
                {
                    // 次数限制
                    if (fillBase.get("holi_count") != null)
                    {
                        // 总次数
                        holi_count = Integer.parseInt(fillBase.get("holi_count").toString());
                        holi_count -= findDaysoffCount(pm.getPersonnel_id());
                    }
                }
                // 出差 没有额度 次数
                if (apply_type_id == 52 || apply_type_id == 64 || apply_type_id == 28)
                {
                    holi_day = null;
                    holi_count = null;
                }

            }
            fillcheck.put("apply_type_id", apply_type_id);
            fillcheck.put("apply_type_name", CheckingUtil.getVacateMap().get(apply_type_id));
            fillcheck.put("holi_day", holi_day);
            fillcheck.put("holi_count", holi_count);
            fillcheck.put("apply_type_name", CheckingUtil.getVacateMap().get(apply_type_id));
            if (apply_type_id == 27)
            {

                // 补签是否可调
                fillCheck_flag = 1;
                int deptId = pm.getPersonnel_deptid();
                String dept_code = attendanceDao.getDept_code(deptId);
                if ("DHL".equals(dept_code.subSequence(0, 3)))
                {
                    fillCheck_flag = 2;
                }

            }
            apply_type_list.add(fillcheck);
            resMap.put("fillCheck_flag", fillCheck_flag);

            // 出差 默认天数
            if (apply_type_id == 52)
            {
                // 公休为0 不包含
                if ("1".equals(day_type) && "0".equals(general_holiday))
                {
                    count_time = 0;
                }
                // 法定假日 为0 不包含
                else if ("2".equals(day_type) && "0".equals(legal_holiday))
                {
                    count_time = 0;
                }
                // 工作日 公休为1法定假日 为1 都包含
                else
                {
                    count_time = 1;
                }
            }
        }

        // 获取当前登录人信息(二级)
        resMap.put("personnel_name", pm.getPersonnel_name());
        resMap.put("personnel_shortcode", pm.getPersonnel_shortcode());
        resMap.put("personnel_deptname", pm.getPersonnel_deptname());
        resMap.put("personnel_postname", pm.getPersonnel_postname());
        resMap.put("personnel_trialStartTime", pm.getPersonnel_trialstarttime());
        resMap.put("apply_type_list", apply_type_list);
        // 默认事假
        resMap.put("start_time", CheckingUtil.getNow() + " " + start);
        resMap.put("end_time", CheckingUtil.getNow() + " " + off);

        // 出差显示天数
        if (apply_type_id == 52)
        {
            resMap.put("count_time", 1);
        }
        else if (apply_type_id == 28 || apply_type_id == 64)
        {
            resMap.put("count_time", Integer.parseInt(getRealTime(start, off, rest1, rest2, "1", "1", day_type, true) + ""));
        }
        else if (apply_type_id == 29)
        {
            resMap.put("count_time", count_time);
        }
        else
        {
            resMap.put("count_time", count_time);
        }

        return resMap;
    }

    public void addResList(List<Map<String, Object>> apply_type_list, List<Map<String, Object>> holiList, Integer vacate_type, List<Map<String, Object>> shiftMap, PmPersonnel pm)
    {
        String day_type = shiftMap.get(0).get("day_type").toString(); // 0工作1休息2节假
        String start = "";
        String off = "";
        String start1 = shiftMap.get(0).get("start1").toString();
        String start2 = shiftMap.get(0).get("start2") == null ? "" : shiftMap.get(0).get("start2").toString();
        String off1 = shiftMap.get(0).get("off1").toString();
        String off2 = shiftMap.get(0).get("off2") == null ? "" : shiftMap.get(0).get("off2").toString();
        String rest1 = shiftMap.get(0).get("rest1").toString();
        String rest2 = shiftMap.get(0).get("rest2").toString();

        // 当前月份第一天跟最后一天
        Map<String, Object> MonthFeistAndFinal = CheckingUtil.getMonthFeistAndFinal();
        // 每个月的总额度 不扣除使用(请假没有申请次数 -- 无限制)
        Integer holi_dayAll = 0;
        // 剩余额度
        Integer holi_day = 0;
        // 公休假标示1有效0无效(默认无效 下面获取)
        String general_holiday = "0";
        // 法定假标示1有效0无效(默认无效 下面获取)
        String legal_holiday = "0";
        // 申请次数
        Integer hoil_count = 0;
        // 限制集合
        Map<String, Object> thingMap = getSelectBase(holiList, vacate_type);

        if (thingMap.get("general_holiday") != null)
        {
            general_holiday = thingMap.get("general_holiday").toString();
        }
        if (thingMap.get("legal_holiday") != null)
        {
            legal_holiday = thingMap.get("legal_holiday").toString();
        }
        // 是时间段 上班时间取后面的
        if (start2 == "" || start2 == null)
        {
            start = start1;
        }
        else
        {
            start = start2;
        }
        // 是时间段 下班时间取后面的
        if (off2 == "" || off2 == null)
        {
            off = off1;
        }
        else
        {
            off = off2;
        }
        // 工作时间(不区分周末与节假日 )
        long RealTime = getRealTime(start, off, rest1, rest2, general_holiday, legal_holiday, day_type, true);
        // 类型名称
        String vacateName = "";
        /* 年假特殊 按工龄区分
         * flow1={
            10: 15,
            5: 7,
            2: 4,
            1: 2
            },
         * */
        // 当前月份请假总时间(小时)
        Integer historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", MonthFeistAndFinal.get("date_start") + "", MonthFeistAndFinal.get("date_end") + "", vacate_type);
        historyTime = historyTime == null ? 0 : historyTime;
        vacateName = CheckingUtil.getVacateMap().get(vacate_type);
        if (vacate_type == 33)
        {
            // 司龄 key
            String personnel_seniority = pm.getPersonnel_seniority();
            // 向下取整 取完是0.0 2.0 格式
            personnel_seniority = Math.floor(Double.parseDouble(personnel_seniority)) + "";
            Integer sty = Integer.parseInt(personnel_seniority.split("\\.")[0]);
            if (sty > 10)
            {
                sty = 10;
            }
            String flow1 = thingMap.get("flow1") == null ? "{}" : thingMap.get("flow1").toString();
            // Map<String, Object> flowMap = JsonUtil.jsonstrToMap(flow1);
            Map<String, Object> flowMap = (Map) JSON.parse(flow1);
            personnel_seniority = sty + "";
            // 限制总时间
            if ("0".equals(personnel_seniority))
            {
                holi_day = null;
            }
            else
            {

                holi_dayAll = (int) (Integer.parseInt(flowMap.get(personnel_seniority).toString()) * RealTime);
                holi_day = holi_dayAll - historyTime;
            }
            hoil_count = null;
        }
        else
        {
            // 限制中的配置 请假总额度(取得是月的)
            if (thingMap.get("holi_day") != null)
            {

                // 如果返回null 说明没有请过此类型的假期
                if (historyTime == null)
                {
                    historyTime = 0;
                }
                holi_dayAll = Integer.parseInt(thingMap.get("holi_day").toString());
                holi_day = Integer.parseInt(holi_dayAll * RealTime + "") - historyTime;
            }
            else
            {
                holi_day = -1;
            }

            if (thingMap.get("hoil_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("hoil_count").toString());
                // 调休
                if (vacate_type == 29)
                {
                    Integer count = findDaysoffCount(pm.getPersonnel_id());
                    if (count == null)
                    {
                        count = 0;
                    }
                    hoil_count -= count;
                }
                // 补卡
                if (vacate_type == 27)
                {
                    Integer count = findFillcheckCount(pm.getPersonnel_id());
                    if (count == null)
                    {
                        count = 0;
                    }
                    hoil_count -= count;
                }
            }
            else
            {
                hoil_count = null;
            }
        }

        AddList(vacate_type, vacateName, holi_day, hoil_count, apply_type_list);
    }

    /**
     * @Title: getSelectBase
     * @Description: 获取对应请假类型的限制配置
     * @param holiList
     * @param holi_type
     * @return 
     * @author: zhaowei
     * @time:2017年7月6日 上午10:58:25
     * history:
     * 1、2017年7月6日 zhaowei 创建方法
    */
    public Map<String, Object> getSelectBase(List<Map<String, Object>> holiList, Integer holi_type)
    {
        Map<String, Object> selectBase = new HashMap<String, Object>();
        // 遍历获取所选择的请假类型 限制集合
        for (Map<String, Object> m : holiList)
        {
            if ((holi_type + "").equals(m.get("holi_type").toString()))
            {
                selectBase = m;
                break;
            }
        }
        return selectBase;
    }

    /**
     * @Title: AddList
     * @Description: 往list塞数据
     * @param apply_type_id
     * @param apply_type_name
     * @param holi_day
     * @param holi_count
     * @param list
     * @return 
     * @author: zhaowei
     * @time:2017年7月5日 下午5:19:18
     * history:
     * 1、2017年7月5日 zhaowei 创建方法
    */
    public List<Map<String, Object>> AddList(Integer apply_type_id, String apply_type_name, Integer holi_day, Integer holi_count, List<Map<String, Object>> list)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("apply_type_id", apply_type_id);
        map.put("apply_type_name", apply_type_name);
        map.put("holi_day", holi_day);
        map.put("holi_count", holi_count);
        list.add(map);
        return list;
    };

    /**
     * @Title: findVacateSetting
     * @Description: 获取请假时间次数限制
     * @param personnel_id
     * @return 
     * @author: zhaowei
     * @time:2017年7月5日 下午6:01:30
     * history:
     * 1、2017年7月5日 zhaowei 创建方法
    */
    public List<Map<String, Object>> findVacateSetting(PmPersonnel pmPersonnel, Integer apply_type_id)
    {
        // 30请假申请、27补签申请、29调休申请、28.酬勤申请、52.出差申请、64.项目酬勤申请
        String type = "";
        if (apply_type_id == 30)
        {
            type = "QJS%";
        }
        else if (apply_type_id == 27)
        {
            type = "BQS%";
        }
        else if (apply_type_id == 29)
        {
            type = "TXS%";
        }
        else if (apply_type_id == 52)
        {
            type = "CCS%";
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 人员为空 返回 null
        if (pmPersonnel == null)
        {
            return null;
        }
        List<Map<String, Object>> vacates = attendanceDao.findAllVacateSetting(type);
        Integer dept_id = pmPersonnel.getPersonnel_deptid();
        List<Map<String, Object>> deptMaps = attendanceDao.findAllDept();
        List<SysDept> depts = getSysDeptList(deptMaps);
        Map<Integer, List<Map<String, Object>>> vMap = doVacateSetting(vacates);
        List<Integer> vIds = new ArrayList<Integer>();
        while (dept_id != null && !dept_id.equals(0))
        {
            SysDept dept = findDeptByid(dept_id, depts);
            List<Map<String, Object>> list = vMap.get(dept_id);
            if (list != null)
            {
                for (Map<String, Object> map : list)
                {
                    Integer holi_type = (Integer) map.get("holi_type");
                    if (!vIds.contains(holi_type))
                    {
                        vIds.add(holi_type);
                        result.add(map);
                    }
                }
            }
            dept_id = dept.getDept_pid();
        }
        return result;
    }

    private List<SysDept> getSysDeptList(List<Map<String, Object>> list)
    {
        List<SysDept> result = new ArrayList<SysDept>();
        for (Map<String, Object> map : list)
        {
            try
            {
                SysDept dept = CheckingUtil.MapToBean(map, SysDept.class);
                result.add(dept);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 处理请假参考数据
     * 根据部门id进行分类
     * @param vacates
     * @return
     */
    private Map<Integer, List<Map<String, Object>>> doVacateSetting(List<Map<String, Object>> vacates)
    {
        Map<Integer, List<Map<String, Object>>> result = new HashMap<Integer, List<Map<String, Object>>>();
        for (Map<String, Object> map : vacates)
        {
            Integer deptid = (Integer) map.get("organization_id");
            List<Map<String, Object>> list = result.get(deptid);
            if (list == null)
            {
                list = new ArrayList<Map<String, Object>>();
                result.put(deptid, list);
            }
            list.add(map);
        }
        return result;
    }

    private SysDept findDeptByid(Integer id, List<SysDept> depts)
    {
        for (SysDept d : depts)
        {
            if (d.getDept_id().equals(id))
            {
                return d;
            }
        }
        return null;
    }

    /**
     * @Title: findTimeVacateHistory
     * @Description: 获取请假历史数据集合
     * @param personnel_id
     * @param vacateS
     * @param vacateE
     * @return 
     * @author: zhaowei
     * @time:2017年7月6日 上午11:06:24
     * history:
     * 1、2017年7月6日 zhaowei 创建方法
    */
    public Integer findTimeVacateHistory(String personnel_id, String vacateS, String vacateE, int hoil_type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("vacateS", vacateS);
        map.put("vacateE", vacateE);
        map.put("hoil_type", hoil_type);
        // 返回小时数
        Integer VacateHistory = attendanceDao.findTimeVacateHistory(map);
        return VacateHistory;
    }

    /**
     * 
     * @Title: findCountVacateHistory
     * @Description: TODO(获取请假历史数据数目)
     * @param personnel_id
     * @param vacateS
     * @param vacateE
     * @param hoil_type
     * @return 
     * @author: what
     * @time:2017年7月12日 下午1:49:38
     * history:
     * 1、2017年7月12日 what 创建方法
     */
    public Integer findCountVacateHistory(String personnel_id, String vacateS, String vacateE, int hoil_type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("vacateS", vacateS);
        map.put("vacateE", vacateE);
        map.put("hoil_type", hoil_type);
        // 返回小时数
        Integer historyCount = attendanceDao.findCountVacateHistory(map);
        return historyCount;
    }

    /**
     * @Title: getVolumeInfo
     * @Description: 获取这段时间内的个人排班
     * @param date_start
     * @param date_end
     * @return 
     * @author: zhaowei
     * @time:2017年7月6日 下午1:27:06
     * history:
     * 1、2017年7月6日 zhaowei 创建方法
    */
    public List<Map<String, Object>> getVolumeInfo(String date_start, String date_end, Integer personnel_id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date_start", date_start);
        map.put("date_end", date_end);
        map.put("personnel_id", personnel_id);
        return attendanceDao.getVolumeInfo(map);
    }

    /**
    * @Title: getRealTime
    * @Description: 算出一天工作小时数 也可以算出请假小时数
    * @param s 上班时间(请假开始时间) 格式09:00
    * @param e 下班时间(请假结束时间) 格式18:00
    * @param r1 午休开始时间  格式12:00
    * @param r2 午休结束时间  格式13:00
    * @return 向上取整
    * @author: zhaowei   
    * @time:2017年7月6日 下午1:45:01
    * history:
    * 1、2017年7月6日 zhaowei 创建方法
    */
    public static long getRealTime(String s, String e, String r1, String r2, String general_holiday, String legal_holiday, String day_type, Boolean bool)
    {
        long overTime = 0;
        double temp = 0.0;
        String start_work = s;
        String off_work = e;
        if (start_work.compareTo(r1) < 0)
        {
            if (off_work.compareTo(r1) > 0)
            {
                overTime += CheckingUtil.calculateTwoTime(r1, start_work);
                if (off_work.compareTo(r2) > 0)
                {
                    overTime += CheckingUtil.calculateTwoTime(off_work, r2);
                }
            }
            else
            {
                long ot = CheckingUtil.calculateTwoTime(off_work, start_work);
                overTime += ot < 0 ? 0 : ot;
            }
        }
        else if (start_work.compareTo(r2) < 0)
        {
            if (off_work.compareTo(r2) > 0)
            {
                overTime += CheckingUtil.calculateTwoTime(off_work, r2);
            }
        }
        else
        {
            overTime += CheckingUtil.calculateTwoTime(off_work, start_work);
        }

        // bool单纯的返回时间差 不判断周末 节假日
        if (bool)
        {

            return (long) Math.ceil((double) overTime / (1000 * 60 * 60));
        }

        if ("0".equals(day_type))
        {
            // 工作日 肯定有工作时间
            return (long) Math.ceil((double) overTime / (1000 * 60 * 60));
        }
        else if ("1".equals(day_type) && "1".equals(general_holiday))
        {
            // 休息日 看general_holiday g==0 正常上班有工作时间
            return (long) Math.ceil((double) overTime / (1000 * 60 * 60));
        }
        else if ("2".equals(day_type) && "1".equals(legal_holiday))
        {
            // 节假日 看legal_holiday l==0 正常上班有工作时间
            return (long) Math.ceil((double) overTime / (1000 * 60 * 60));
        }
        else
        {
            return 0;
        }
    }

    /**
     * @Title: getRealTime
     * @Description: 算出一天工作小时数 也可以算出请假小时数
     * @param s 上班时间(请假开始时间) 格式09:00
     * @param e 下班时间(请假结束时间) 格式18:00
     * @param r1 午休开始时间  格式12:00
     * @param r2 午休结束时间  格式13:00
     * @return 向下取整
     * @author: zhaowei
     * @time:2017年7月6日 下午1:45:01
     * history:
     * 1、2017年7月6日 zhaowei 创建方法
     */
    public static long getRealTime1(String s, String e, String r1, String r2, String general_holiday, String legal_holiday, String day_type, Boolean bool)
    {
        long overTime = 0;
        double temp = 0.0;
        String start_work = s;
        String off_work = e;
        if (start_work.compareTo(r1) < 0)
        {
            if (off_work.compareTo(r1) > 0)
            {
                overTime += CheckingUtil.calculateTwoTime(r1, start_work);
                if (off_work.compareTo(r2) > 0)
                {
                    overTime += CheckingUtil.calculateTwoTime(off_work, r2);
                }
            }
            else
            {
                long ot = CheckingUtil.calculateTwoTime(off_work, start_work);
                overTime += ot < 0 ? 0 : ot;
            }
        }
        else if (start_work.compareTo(r2) < 0)
        {
            if (off_work.compareTo(r2) > 0)
            {
                overTime += CheckingUtil.calculateTwoTime(off_work, r2);
            }
        }
        else
        {
            overTime += CheckingUtil.calculateTwoTime(off_work, start_work);
        }

        // bool单纯的返回时间差 不判断周末 节假日
        if (bool)
        {

            return (long) Math.floor((double) overTime / (1000 * 60 * 60));
        }

        if ("0".equals(day_type))
        {
            // 工作日 肯定有工作时间
            return (long) Math.floor((double) overTime / (1000 * 60 * 60));
        }
        else if ("1".equals(day_type) && "1".equals(general_holiday))
        {
            // 休息日 看general_holiday g==0 正常上班有工作时间
            return (long) Math.floor((double) overTime / (1000 * 60 * 60));
        }
        else if ("2".equals(day_type) && "1".equals(legal_holiday))
        {
            // 节假日 看legal_holiday l==0 正常上班有工作时间
            return (long) Math.floor((double) overTime / (1000 * 60 * 60));
        }
        else
        {
            return 0;
        }
    }

    /* 查询当月调休申请次数  
    * 
    * @return
    */
    public Integer findDaysoffCount(Integer personnel_id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("daysoff_begintime", CheckingUtil.getMonthFeistAndFinal().get("date_start"));
        map.put("daysoff_endtime", CheckingUtil.getMonthFeistAndFinal().get("date_end"));

        Integer count = attendanceDao.findDaysoffCount(map);
        Integer c = count;
        return c == null ? 0 : c;
    }

    /* 查询输入月份调休申请次数  
    * 
    * @return
    */
    public Integer findSomesoffCount(Integer personnel_id, String time)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("daysoff_begintime", CheckingUtil.getFirstAndFinalDay(time).get("date_start"));
        map.put("daysoff_endtime", CheckingUtil.getFirstAndFinalDay(time).get("date_end"));

        Integer count = attendanceDao.findDaysoffCount(map);
        Integer c = count;
        return c == null ? 0 : c;
    }

    public Integer findFillcheckCount(Integer personnel_id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("daysoff_begintime", CheckingUtil.getMonthFeistAndFinal().get("date_start"));
        map.put("daysoff_endtime", CheckingUtil.getMonthFeistAndFinal().get("date_end"));

        Integer count = attendanceDao.findFillcheckCount(map);
        Integer c = count;
        return c == null ? 0 : c;
    }

    /**
        * @Title: timeAttendance
        * @Description: 考勤异常提醒
        * @author: sunlq
        * @throws InterruptedException 
        * @time:2017年7月5日 下午5:34:48
        * @see com.zx.moa.ioa.timeAttendance.service.IAttendanceService#timeAttendance()
        * history:
        * 1、2017年7月5日 sunlq 创建方法
        */
    @Override
    public void timeAttendance() throws InterruptedException
    {
        new Thread()
        {
            @Override
            public void run()
            {
                List<Map<String, Object>> list = attendanceDao.getWarnAttendancePersonnelList();
                int i = 0;
                // 循环
                for (Map<String, Object> map : list)
                {
                    i++;
                    // 免费版本的每个Appkey的最高推送频率为600次/分钟。
                    if (i > 500)
                    {
                        try
                        {
                            // 休息2分钟
                            Thread.sleep(2 * 60 * 1000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    PushManage.pushMessageByMsgContext(PushManage.DEF_APP_NAME, "【提醒】考勤异常。", map.get("alias").toString(), "30101", map);
                }
            }
        }.start();
    }

    /**
     * 
     * @Title: saveAttendanceApplyInfo
     * @Description: TODO(保存考勤申请信息)
     * @param request
     * @return 
     * @author: scf
     * @time:2017年7月6日 上午9:26:36
     * @see com.zx.moa.ioa.timeAttendance.service.IAttendanceService#saveAttendanceApplyInfo(javax.servlet.http.HttpServletRequest)
     * history:
     * 1、2017年7月6日 scf 创建方法
     */
    @Override
    public ResultBean<List<Map<String, Object>>> saveAttendanceApplyInfo(HttpServletRequest request)
    {
        String apply_type_id = request.getParameter("apply_type_id");
        String start_time = request.getParameter("start_time");
        String end_time = request.getParameter("end_time");
        String count_time = request.getParameter("count_time");
        String content = request.getParameter("content");
        // 针对项目酬勤(酬勤人姓名+短工号)多人用逗号分隔
        String payoff_personnel_name = request.getParameter("payoff_personnel_name");
        if (payoff_personnel_name == null)
        {
            payoff_personnel_name = "";
        }
        String img_List = request.getParameter("img_List");
        if (img_List == null)
        {
            img_List = "";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("apply_type_id", apply_type_id);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        map.put("count_time", count_time);
        map.put("content", content);
        map.put("payoff_personnel_name", payoff_personnel_name);
        map.put("img_List", img_List);
        map.put("personnel", request.getSession().getAttribute(GlobalVal.USER_SESSION));
        Integer countTime = 0;
        if (StringUtil.isNotBlank(count_time))
        {
            countTime = Integer.parseInt(count_time);
        }
        if (start_time == null)
        {
            start_time = "";
        }
        if (end_time == null)
        {
            end_time = "";
        }
        String testRes = verificationSave(request, Integer.parseInt(apply_type_id), start_time, end_time, countTime);
        if (!testRes.equals("000"))
        {
            ResultBean rb = new ResultBean<>();
            rb.setRet_code("002");
            rb.setRet_msg(testRes);
            return rb;
        }
        try
        {
            Map<String, Object> resmap = getModiConnectionScc("IOA_OUT_SaveAttendanceApplyInfo", map);
            Boolean bl = (Boolean) resmap.get("flag");
            if (bl)
            {
                return ResultHelper.getSuccess(null, "申请成功");
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        return ResultHelper.getError("申请失败", null);
    }

    /**
     * 
     * @Title: getModiConnectionScc
     * @Description: TODO(调用接口平台)
     * @param interfaceNumber
     * @param map
     * @return
     * @throws ClientProtocolException 
     * @author: scf
     * @time:2017年7月6日 上午9:37:44
     * history:
     * 1、2017年7月6日 scf 创建方法
     */
    private Map<String, Object> getModiConnectionScc(String interfaceNumber, Map<String, Object> map) throws ClientProtocolException
    {

        String url = HttpClientUtil.getSysUrl("nozzleUrl");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", interfaceNumber));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        String releaseTask = JSONObject.toJSONString(map);
        nvps.add(new BasicNameValuePair("releaseTask", releaseTask));
        Map<String, Object> resmap = new HashMap<String, Object>();
        try
        {
            resmap = HttpClientUtil.post(url, nvps, Map.class);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            resmap.put("message", e.getMessage());
        }
        return resmap;
    }

    /**
     * @Title: revokeOrder
     * @Description: TODO(撤销单据)
     * @param request
     * @return 
     * @author: scf
     * @time:2017年7月6日 上午9:36:13
     * @see com.zx.moa.ioa.timeAttendance.service.IAttendanceService#revokeOrder(javax.servlet.http.HttpServletRequest)
     * history:
     * 1、2017年7月6日 scf 创建方法
     */
    @Override
    public ResultBean<List<Map<String, Object>>> revokeOrder(HttpServletRequest request)
    {
        String order_id = request.getParameter("order_id");
        String cancelreason = request.getParameter("cancelreason");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", order_id);
        map.put("cancelreason", cancelreason);
        map.put("personnel", request.getSession().getAttribute(GlobalVal.USER_SESSION));
        try
        {
            Map<String, Object> resmap = getModiConnectionScc("IOA_OUT_RevokeOrder", map);
            Boolean bl = (Boolean) resmap.get("flag");
            if (bl)
            {
                return ResultHelper.getSuccess(null);
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        return ResultHelper.getError(null);
    }

    /**
     * @Title: findDaysoffHistory
     * @Description: 调休额度
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2017年7月8日 上午10:09:54
     * history:
     * 1、2017年7月8日 zhaowei 创建方法
    */
    public Map<String, Object> findDaysoffHistory(String code)
    {
        return this.findDaysOffSetting(code);
    }

    public Map<String, Object> findDaysOffSetting(String code)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.add(Calendar.MONTH, -1);
        Double calculateAll = 0.0;
        for (int i = 0; i < 3; i++)
        {
            String date = new SimpleDateFormat("yyyy-MM").format(c1.getTime());
            String key = new SimpleDateFormat("yyyyMM").format(c1.getTime());
            result.put("YM" + key, findDaysOffSetting(date, code));
            List<Map<String, Object>> list = attendanceDao.findDaysoffHistory(code, date);
            result.put("D" + key, calculateHour(list));
            calculateAll += calculateHour(list);
            result.put("T" + key, calculateHourPass(list));
            result.put("N" + key, calculateHourNoPass(list));
            c1.add(Calendar.MONTH, 1);
        }
        result.put("calculateAll", calculateAll);
        return result;
    }

    public Map<String, Object> findSomeDaySetting(String code, String time)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try
        {
            c1.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.add(Calendar.MONTH, -1);
        Double calculateAll = 0.0;
        for (int i = 0; i < 3; i++)
        {
            String date = new SimpleDateFormat("yyyy-MM").format(c1.getTime());
            String key = new SimpleDateFormat("yyyyMM").format(c1.getTime());
            result.put("YM" + key, findDaysOffSetting(date, code));
            List<Map<String, Object>> list = attendanceDao.findDaysoffHistory(code, date);
            result.put("D" + key, calculateHour(list));
            calculateAll += calculateHour(list);
            result.put("T" + key, calculateHourPass(list));
            result.put("N" + key, calculateHourNoPass(list));
            c1.add(Calendar.MONTH, 1);
        }
        result.put("calculateAll", calculateAll);
        return result;
    }

    public Map<String, Object> findDaysOffSetting(String date, String code)
    {
        String dArray[] = date.split("-");
        String year = dArray[0];
        String month = dArray[1];
        Integer quarter = (Integer.parseInt(month) - 1) / 3;
        String[] quarterMonths = QUARTER[quarter];
        List<String> searchMonth = new ArrayList<String>();
        /**
        * 获取上一季度月份
        */
        if (quarter == 0)
        {
            for (int i = 0; i < quarterMonths.length; i++)
            {
                searchMonth.add((Integer.parseInt(year) - 1) + "-" + quarterMonths[i]);
            }
        }
        else
        {
            for (int i = 0; i < quarterMonths.length; i++)
            {
                searchMonth.add(year + "-" + quarterMonths[i]);
            }
        }
        /**
        * 获取上一月月份
        * 季初月没有上月份
        * 修改为：获取本月分本月之前季度
        */
        quarter = quarter == 3 ? 0 : (quarter + 1);
        quarterMonths = QUARTER[quarter];
        for (int i = 0; i < quarterMonths.length; i++)
        {
            if (quarterMonths[i].equals(month))
            {
                break;
            }
            searchMonth.add(year + "-" + quarterMonths[i]);
        }
        // Integer prevMonth = Integer.parseInt(month)%3!=
        // 1?(Integer.parseInt(month)-1):null;
        // if(prevMonth != null){
        // searchMonth.add(year+"-"+(prevMonth<10?("0"+prevMonth):prevMonth));
        // }
        /**
        * 增加本月份
        * 修改不能使用本月
        */
        // searchMonth.add(year+"-"+month);
        List<Map<String, Object>> list = attendanceDao.findPayoff(searchMonth, code);
        Integer rest = 0;
        Integer holi = 0;
        Integer wpo = 0;
        Integer hpo = 0;
        Integer night = 0;
        for (Map<String, Object> m : list)
        {
            String check_weekend_use = (String) m.get("check_weekend_use");
            String check_holiday_use = (String) m.get("check_holiday_use");
            String check_holiday_total = (String) m.get("check_holiday_total");
            String check_weekend_total = (String) m.get("check_weekend_total");

            String check_holidayPO_use = (String) m.get("check_holidayPO_use");
            String check_weekendPO_use = (String) m.get("check_weekendPO_use");
            String check_nightTime_use = (String) m.get("check_nightTime_use");
            String check_weekendPO_total = (String) m.get("check_weekendPO_total");
            String check_nightTime_total = (String) m.get("check_nightTime_total");
            String check_holidayPO_total = (String) m.get("check_holidayPO_total");

            int i = (getInt(check_holidayPO_total) / HOUR - getInt(check_holidayPO_use));
            int x = (getInt(check_weekendPO_total) / HOUR - getInt(check_weekendPO_use));
            int y = (getInt(check_nightTime_total) / HOUR - getInt(check_nightTime_use));
            int r = (getInt(check_weekend_total) / HOUR - getInt(check_weekend_use));
            int h = (getInt(check_holiday_total) / HOUR - getInt(check_holiday_use));
            // 项目节假日
            hpo += i > 0 ? i : 0;
            // 项目休息日
            wpo += x > 0 ? x : 0;
            // 通宵
            night += y > 0 ? y : 0;
            // 休息日加班
            rest += r > 0 ? r : 0;
            // 节假日
            holi += h > 0 ? h : 0;
        }
        // 通宵当月可用
        List<String> searchMonth1 = new ArrayList<String>();
        searchMonth1.add(year + "-" + month);
        List<Map<String, Object>> list1 = attendanceDao.findPayoff(searchMonth1, code);
        Integer night1 = 0;
        int y1 = 0;
        if (list1 != null && list1.size() > 0)
        {
            String check_nightTime_use1 = (String) list1.get(0).get("check_nightTime_use");
            String check_nightTime_total1 = (String) list1.get(0).get("check_nightTime_total");
            y1 = (getInt(check_nightTime_total1) / HOUR - getInt(check_nightTime_use1));
            night1 = y1 > 0 ? y1 : 0;
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rest + holi + wpo + night + hpo + y1);
        result.put("rest", rest);
        result.put("holi", holi);
        result.put("night", night);
        result.put("hpo", hpo);
        result.put("wpo", wpo);
        result.put("night1", night1);
        return result;
    }

    /* 字符串转int 
    * @param str
    * @return 空或非数字返回0
    */
    private Integer getInt(String str)
    {
        if (StringUtil.isEmpty(str))
        {
            return 0;
        }
        else
        {
            try
            {
                return Integer.parseInt(str);
            }
            catch (Exception e)
            {
                return 0;
            }
        }
    }

    /* 处理调休小时数
    * @param list 调休单据
    * @return
    */
    private Integer calculateHour(List<Map<String, Object>> list)
    {
        if (list == null || list.size() == 0)
        {
            return 0;
        }
        Integer result = 0;
        for (Map<String, Object> m : list)
        {
            Integer i = (Integer) m.get("daysoff_hour");
            result += i;
        }
        return result;
    }

    /**
     * 处理调休小时数(通过)
     * @param list 调休单据
     * @return
     */
    private Integer calculateHourPass(List<Map<String, Object>> list)
    {
        if (list == null || list.size() == 0)
        {
            return 0;
        }
        Integer result = 0;
        for (Map<String, Object> m : list)
        {
            if (Arrays.asList(new String[] { "2", "4", "5" }).contains(m.get("order_state")))
            {
                Integer i = (Integer) m.get("daysoff_hour");
                result += i;
            }
        }
        return result;
    }

    /**
    * @Title: calculateHourNoPass
    * @Description: 处理调休小时数(未通过)
    * @param list
    * @return 
    * @author: zw
    * @time:2017年7月3日 下午4:38:08
    * history:
    * 1、2017年7月3日 zw 创建方法
    */
    private Integer calculateHourNoPass(List<Map<String, Object>> list)
    {
        if (list == null || list.size() == 0)
        {
            return 0;
        }
        Integer result = 0;
        for (Map<String, Object> m : list)
        {
            if (Arrays.asList(new String[] { "1", "6" }).contains(m.get("order_state")))
            {
                Integer i = (Integer) m.get("daysoff_hour");
                result += i;
            }
        }
        return result;
    }

    /**
     * @Title: verificationSave
     * @Description: 保存通用校验方法
     * @param apply_type_id 申请类型
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @param deptId 提单人部门ID
     * @param count_time 时长
     * @return 
     * @author: zhaowei
     * @time:2017年7月10日 下午5:16:40
     * history:
     * 1、2017年7月10日 zhaowei 创建方法
    */
    public String verificationSave(HttpServletRequest request, int apply_type_id, String start_time, String end_time, int count_time)
    {

        // 获取当前登录人
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        // 返回信息(000正确可保存 其余有问题)
        String str = "";
        // 班次信息
        List<Map<String, Object>> shiftMap = new ArrayList<Map<String, Object>>();
        // 给补卡做特殊处理 一个时间为空取另一个
        if (start_time == "")
        {
            shiftMap = getVolumeInfo(end_time.split(" ")[0], end_time.split(" ")[0], pm.getPersonnel_id());
        }
        // 一个时间为空取另一个
        else if (end_time == "")
        {
            shiftMap = getVolumeInfo(start_time.split(" ")[0], start_time.split(" ")[0], pm.getPersonnel_id());
        }
        // 都不为空
        else
        {
            shiftMap = getVolumeInfo(start_time.split(" ")[0], end_time.split(" ")[0], pm.getPersonnel_id());
        }

        // 限制集合
        List<Map<String, Object>> holiList = new ArrayList<Map<String, Object>>();
        // 限制集合(真正)
        Map<String, Object> thingMap = new HashMap<String, Object>();

        Map<String, Object> YearMap = CheckingUtil.getYearFeistAndFinal();
        Map<String, Object> monthMap = CheckingUtil.getMonthFeistAndFinal();
        // 请假所有类型
        // 事假31、病假32、年假33、产假34、婚假35、丧假36、工伤假37、哺乳假38、护理假39、路程假54
        Integer intArr[] = { 31, 32, 33, 34, 35, 36, 37, 38, 39, 54 };
        // 遍历判断是否为请假类型
        for (Integer i : intArr)
        {
            // 请假类型 是集合
            if (i == apply_type_id)
            {
                holiList = findVacateSetting(pm, 30);
                // 限制集合
                thingMap = getSelectBase(holiList, apply_type_id);
                break;
            }
            else
            {
                holiList = findVacateSetting(pm, apply_type_id);
                // 可能有些就没有限制
                if (holiList != null && holiList.size() > 0)
                {
                    thingMap = holiList.get(0);
                    break;
                }
            }
        }
        // 根据 deptId 提单人ID 获取提单人部门CODE 校验使用
        String deptCode = attendanceDao.getDept_code(pm.getPersonnel_deptid());
        // 事假
        if (apply_type_id == 31)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }

            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 时间段为最晚时间段,请假开始时间早于上班班次时间，或请假结束时间晚于下班班次
            if (vacateAllRegular(shiftMap, start_time, end_time) != "")
            {
                return vacateAllRegular(shiftMap, start_time, end_time);
            }
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }

            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            // CF请病假事假 只能从上班开始时间 午休结束时间开始请 请到上班结束时间 可跨天
            if ("DCF".equals(deptCode.substring(0, 3)))
            {
                if (CFRegular(shiftMap, start_time, end_time) != "")
                {
                    return CFRegular(shiftMap, start_time, end_time);
                }
            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 31);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        else if (apply_type_id == 32)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 时间段为最晚时间段,请假开始时间早于上班班次时间，或请假结束时间晚于下班班次
            if (vacateAllRegular(shiftMap, start_time, end_time) != "")
            {
                return vacateAllRegular(shiftMap, start_time, end_time);
            }
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            // CF请病假事假 只能从上班开始时间 午休结束时间开始请 请到上班结束时间 可跨天
            if ("DCF".equals(deptCode.substring(0, 3)))
            {
                if (CFRegular(shiftMap, start_time, end_time) != "")
                {
                    return CFRegular(shiftMap, start_time, end_time);
                }
            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 32);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        else if (apply_type_id == 33)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(pm.getPersonnel_trialstarttime());
            String now = CheckingUtil.getNow();
            String dateing = now.split("-")[0] + "-" + dateString.split("-")[1] + "-" + dateString.split("-")[2];
            if (start_time.split(" ")[0].compareTo(dateing) < 0 && end_time.split(" ")[0].compareTo(dateing) > 0)
            {
                return "年假不能跨越年假区域!";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 申请次数
            Integer hoil_count = 0;
            String start1 = shiftMap.get(0).get("start1").toString();
            String of1 = shiftMap.get(0).get("off1").toString();
            String rest1 = shiftMap.get(0).get("rest1").toString();
            String rest2 = shiftMap.get(0).get("rest2").toString();
            Integer historyTime = 0;
            int cou = 0;
            hoil_count = thingMap.get("holi_count") == null ? 0 : Integer.parseInt(thingMap.get("holi_count").toString());

            if (now.compareTo(dateing) >= 0)
            {
                historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", CheckingUtil.getSpecifiedYearBefore(dateing, 0), CheckingUtil.getSpecifiedYearBefore(dateing, 1), apply_type_id);
                cou = findCountVacateHistory(pm.getPersonnel_id() + "", CheckingUtil.getSpecifiedYearBefore(dateing, 0), CheckingUtil.getSpecifiedYearBefore(dateing, 1), 33);
            }
            else
            {
                historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", CheckingUtil.getSpecifiedYearBefore(dateing, -1), CheckingUtil.getSpecifiedYearBefore(dateing, 0), apply_type_id);
                cou = findCountVacateHistory(pm.getPersonnel_id() + "", CheckingUtil.getSpecifiedYearBefore(dateing, -1), CheckingUtil.getSpecifiedYearBefore(dateing, 0), 33);
            }
            historyTime = historyTime == null ? 0 : historyTime;
            // 工作时间
            long RealTime = getRealTime(start1, of1, rest1, rest2, "1", "1", "0", true);
            // 剩余可用额度
            Integer holi_day = 0;
            // 司龄 key
            String personnel_seniority = pm.getPersonnel_seniority();
            // 向下取整 取完是0.0 2.0 格式
            personnel_seniority = Math.floor(Double.parseDouble(personnel_seniority)) + "";
            Integer sty = Integer.parseInt(personnel_seniority.split("\\.")[0]);
            if (sty > 10)
            {
                sty = 10;
            }
            String flow1 = thingMap.get("flow1") == null ? "{}" : thingMap.get("flow1").toString();
            Map<String, Object> flowMap = (Map) JSON.parse(flow1);
            personnel_seniority = sty + "";
            // 限制总时间
            if ("0".equals(personnel_seniority))
            {
                return "满一年才有年假";
            }
            else
            {
                Integer holi_dayAll = (int) (Integer.parseInt(flowMap.get(personnel_seniority).toString()) * RealTime);
                holi_day = holi_dayAll - historyTime;
            }
            if (count_time > holi_day)
            {
                return "超过请假额度";
            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            // 申请年假时，申请时长必须与申请日期班次总时长相等，不符合规定时，点击保存弹出提示：年假至少请1天；
            // 年假(请假开始日期跟当天上班时间对 跟 请假结束日期跟当天下班时间对)
            String work = "";
            String work1 = "";
            String off = "";
            String off1 = "";
            for (int i = 0; i < shiftMap.size(); i++)
            {
                // 请假开始日期跟当天上班时间
                if (shiftMap.get(i).get("info_data").toString().equals(start_time.split(" ")[0]))
                {
                    work = shiftMap.get(i).get("start1").toString();
                    work1 = shiftMap.get(i).get("start2") == null ? "" : shiftMap.get(i).get("start2").toString();
                }
                // 请假结束日期跟当天下班时间
                if (shiftMap.get(i).get("info_data").toString().equals(end_time.split(" ")[0]))
                {
                    off = shiftMap.get(i).get("off1").toString();
                    off1 = shiftMap.get(i).get("off2") == null ? "" : shiftMap.get(i).get("off2").toString();
                }

            }
            // 若是时间段 有一个符合即可申请
            boolean isb = (work.equals(start_time.split(" ")[1]) && off.equals(end_time.split(" ")[1]));
            boolean isc = (work1.equals(start_time.split(" ")[1]) && off1.equals(end_time.split(" ")[1]));
            if (isb || isc)
            {
                return "000";
            }
            else
            {
                return "年假至少请一天!";
            }
        }
        // 产假
        else if (apply_type_id == 34)
        {
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            // 没限制 直接过
            if (thingMap == null)
            {
                return "000";
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            else
            {
                if (thingMap.get("holi_day") != null)
                {
                    String start1 = shiftMap.get(0).get("start1").toString();
                    String of1 = shiftMap.get(0).get("off1").toString();
                    String rest1 = shiftMap.get(0).get("rest1").toString();
                    String rest2 = shiftMap.get(0).get("rest2").toString();
                    Integer historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", YearMap.get("year_start") + "", YearMap.get("year_end") + "", apply_type_id);
                    historyTime = historyTime == null ? 0 : historyTime;
                    // 工作时间
                    long RealTime = getRealTime(start1, of1, rest1, rest2, "1", "1", "0", true);
                    long time = Integer.parseInt(thingMap.get("holi_day").toString()) * RealTime;
                    // long maternityLeaveH =
                    // CheckingUtil.calculateTwoTime(start_time, end_time) /
                    // (1000 * 60 * 60);
                    if (count_time > time)
                    {
                        return "请假时间超过额度";
                    }
                    else
                    {
                        return "000";
                    }

                }
            }
        }
        // 婚假
        else if (apply_type_id == 35)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;

            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 35);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            // 婚假只能从周六请
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try
            {
                Date date = sdf.parse(start_time);
                if (!"星期六".equals(CheckingUtil.getWeek(date)))
                {
                    return "婚假需从周六开始请";
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return "000";
        }
        // 丧假
        else if (apply_type_id == 36)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }

            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 36);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        // 工伤假
        else if (apply_type_id == 37)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 37);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        // 哺乳假38
        else if (apply_type_id == 38)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 38);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        // 护理假39
        else if (apply_type_id == 39)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 39);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        // 路程假54
        else if (apply_type_id == 54)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            // 申请次数
            Integer hoil_count = 0;
            // 校验请假额度是否够使用
            String vaildTime = vaildHoilTime(thingMap, pm, YearMap, monthMap, count_time);
            if (vaildTime != "")
            {
                return vaildTime;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            // 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
            if ("DHL".equals(deptCode.substring(0, 3)))
            {
                if (vaildForTime(start_time, count_time, null) != "")
                {
                    return vaildForTime(start_time, count_time, null);
                }

            }
            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountVacateHistory(pm.getPersonnel_id() + "", start_time, end_time, 54);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "请假次数已到上限";
                }
            }
            return "000";
        }
        // 27补卡
        else if (apply_type_id == 27)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 10号以后不能提交上月单据
            if (start_time == "")
            {
                // 补卡上班时间
                if (tenApply(end_time, end_time) != "")
                {
                    return tenApply(end_time, end_time);
                }
            }
            else if (end_time == "")
            {
                // 补卡下班时间
                if (tenApply(start_time, start_time) != "")
                {
                    return tenApply(start_time, start_time);
                }
            }
            else
            {
                // 下下班都补卡
                if (tenApply(start_time, end_time) != "")
                {
                    return tenApply(start_time, end_time);
                }
            }

            // 补卡次数
            Integer hoil_count = 0;
            // 补卡下班
            if (start_time == "")
            {
                // 限制时间提单子
                String vaildBase = vaildBase(request, apply_type_id, end_time, end_time, thingMap);
                if (vaildBase != "")
                {
                    return vaildBase;
                }
            }
            else if (end_time == "")
            {
                // 补卡上班时间
                // 限制时间提单子
                String vaildBase = vaildBase(request, apply_type_id, start_time, start_time, thingMap);
                if (vaildBase != "")
                {
                    return vaildBase;
                }
            }
            else
            {
                String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
                if (vaildBase != "")
                {
                    return vaildBase;
                }
            }

            if (thingMap.get("holi_day") != null)
            {
                int cou = 0;
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_day").toString());
                if (start_time == "")
                {
                    cou = findCountFillHistory(pm.getPersonnel_id() + "", end_time.split(" ")[0], end_time.split(" ")[0]);
                }
                else if (end_time == "")
                {
                    cou = findCountFillHistory(pm.getPersonnel_id() + "", start_time.split(" ")[0], start_time.split(" ")[0]);
                }
                else
                {
                    cou = findCountFillHistory(pm.getPersonnel_id() + "", start_time.split(" ")[0], end_time.split(" ")[0]);
                }

                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "补卡次数已到上限";
                }
            }
            // 别的部门随便 Hl 9-18
            if ("DHL".equals(deptCode.subSequence(0, 3)))
            {
                String w = "";
                String o = "";
                if (shiftMap.get(0).get("start2") == null || shiftMap.get(0).get("start2") == "")
                {
                    w = shiftMap.get(0).get("start1").toString();
                }
                else
                {
                    w = shiftMap.get(0).get("start2").toString();
                }

                if (shiftMap.get(shiftMap.size() - 1).get("off2") == null || shiftMap.get(shiftMap.size() - 1).get("off2") == "")
                {
                    o = shiftMap.get(shiftMap.size() - 1).get("off1").toString();
                }
                else
                {
                    o = shiftMap.get(shiftMap.size() - 1).get("off2").toString();
                }
                if (start_time != null && start_time != "")
                {
                    if (start_time.split(" ")[1].compareTo(w) < 0)
                    {
                        return "补签开始时间不能早于上班时间";
                    }
                }

                if (end_time != null && end_time != "")
                {
                    if (end_time.split(" ")[1].compareTo(o) > 0)
                    {
                        return "补签结束时间不能晚于下班时间";
                    }
                }
                return "000";
            }
            return "000";
        }
        // 调休
        else if (apply_type_id == 29)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            if (vaildForTime(start_time, count_time, 29) != "")
            {
                return vaildForTime(start_time, count_time, 29);
            }

            // 调休只能申请前一个月后一个月 当前月
            List<String> l = CheckingUtil.getMonByTX();
            String mon = start_time.split("-")[0] + "-" + start_time.split("-")[1];
            boolean bool = false;
            // 遍历判断 调休月份是否在当前月份 上一个月 下一个月范围内
            for (int i = 0; i < l.size(); i++)
            {
                // 在范围内
                if (mon.equals(l.get(i)))
                {
                    bool = true;
                    break;
                }
            }
            // 不在当前月份 上一个月 下一个月范围内
            if (!bool)
            {
                return "不可调休当前月份";
            }
            // 调休次数
            Integer hoil_count = 0;
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time, end_time, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }

            if (thingMap.get("holi_count") != null)
            {
                // 总次数
                hoil_count = Integer.parseInt(thingMap.get("holi_count").toString());
                int cou = findCountDayoffHistory(pm.getPersonnel_id() + "", start_time, end_time);
                hoil_count -= cou;
                if (hoil_count <= 0)
                {
                    return "调休次数已到上限";
                }
            }
            if (thingMap.get("holi_day") != null)
            {
                String day_type = shiftMap.get(0).get("day_type").toString();
                String start = shiftMap.get(0).get("start1").toString();
                String off = shiftMap.get(0).get("off1").toString();
                String rest1 = shiftMap.get(0).get("rest1").toString();
                String rest2 = shiftMap.get(0).get("rest2").toString();
                // 时间差
                Integer work = Integer.parseInt(getRealTime(start, off, rest1, rest2, "1", "1", day_type, true) + "");
                Integer holi_day = 0;
                Map<String, Object> md = findDaysoffHistory(pm.getPersonnel_shortcode());

                String year = CheckingUtil.getNow().split("-")[0];
                String month = CheckingUtil.getNow().split("-")[1];
                double use = Double.parseDouble(md.get("calculateAll").toString());
                double pass = Double.parseDouble(md.get("T" + year + "" + month).toString());
                double Nopass = Double.parseDouble(md.get("N" + year + "" + month).toString());
                double his = 0;
                Map<String, Object> flowMap = (Map) md.get("YM" + year + "" + month);
                his = Double.parseDouble(flowMap.get("total").toString());
                his += pass;
                double set = Double.parseDouble(thingMap.get("holi_day").toString()) * work;

                holi_day = Integer.parseInt((Math.ceil(his - use) + "").split("\\.")[0]);
                Integer no = Integer.parseInt((Math.ceil(set - Nopass) + "").split("\\.")[0]);
                if (no > holi_day)
                {
                    holi_day = holi_day;
                }
                else
                {
                    holi_day = no;
                }
                holi_day = holi_day < 0 ? 0 : holi_day;
                if (count_time > holi_day)
                {
                    return "调休超过额度";
                }
            }
            return "000";
        }
        // 酬勤
        else if (apply_type_id == 28)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            Map<String, Object> m = shiftMap.get(0);
            // 加班开始时间
            String reTime = m.get("over").toString();
            String day_type = m.get("day_type").toString();
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }

            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }
            String now = CheckingUtil.getNowT();
            // 2017-01-01 2017-01-02
            // 酬勤需提前申请
            if (start_time.compareTo(now) < 0)
            {
                return "酬勤需提前提交";
            }
            if ("0".equals(day_type))
            {
                // 提示酬勤开始时间必须在18:00之后
                if (start_time.split(" ")[1].compareTo(reTime) < 0)
                {
                    return "酬勤开始时间必须在" + reTime + "之后";
                }
                if (count_time < 3)
                {
                    return "工作日项目酬勤申请需在3小时以上";
                }

            }
            else
            {
                if (count_time < 4)
                {
                    return "酬勤申请需在4小时以上";
                }
            }
            if (start_time.split(" ")[1].compareTo(reTime) == 0 && end_time.split(" ")[1].compareTo(reTime) == 0)
            {
                return "结束时间在次日6点之前";
            }
            return "000";

        }
        else if (apply_type_id == 64)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 申请时间为0 直接打回
            if (count_time == 0)
            {
                return "申请时间不能为0";
            }
            Map<String, Object> m = shiftMap.get(0);
            // 加班开始时间
            String reTime = m.get("over").toString();
            String day_type = m.get("day_type").toString();
            // 10号以后不能提交上月单据
            if (tenApply(start_time, end_time) != "")
            {
                return tenApply(start_time, end_time);
            }

            String now = CheckingUtil.getNowT();
            // 2017-01-01 2017-01-02
            // 酬勤需提前申请
            if (start_time.compareTo(now) < 0)
            {
                return "酬勤需提前提交";
            }
            if ("0".equals(day_type))
            {
                // 提示酬勤开始时间必须在18:00之后
                if (start_time.split(" ")[1].compareTo(reTime) < 0)
                {
                    return "酬勤开始时间必须在" + reTime + "之后";
                }
                if (count_time < 3)
                {
                    return "工作日项目酬勤申请需在3小时以上";
                }

            }
            else
            {

                if (count_time < 4)
                {
                    return "项目酬勤申请需在4小时以上";
                }
            }
            if (start_time.split(" ")[1].compareTo(reTime) == 0 && end_time.split(" ")[1].compareTo(reTime) == 0)
            {
                return "结束时间在次日6点之前";
            }
            return "000";
        }
        // 出差判定
        else if (apply_type_id == 52)
        {
            // 所选日期暂无班次配置，无法申请！
            if (shiftMap == null || shiftMap.size() == 0)
            {
                return "所选日期暂无班次配置，无法申请！";
            }
            // 返航日期不能大于起航日期
            if (start_time.compareTo(end_time) > 0)
            {
                return "返航日期不能晚于起航日期!";
            }
            String start1 = shiftMap.get(0).get("start1").toString();
            String start2 = shiftMap.get(0).get("start2") == null ? "" : shiftMap.get(0).get("start2").toString();
            String off1 = shiftMap.get(0).get("off1").toString();
            String off2 = shiftMap.get(0).get("off2") == null ? "" : shiftMap.get(0).get("off2").toString();
            String s = "";
            String e = "";
            if (start2 == "")
            {
                s = start1;
            }
            else
            {
                s = start2;
            }
            if (off2 == "")
            {
                e = off1;
            }
            else
            {
                e = off2;
            }
            // 限制时间提单子
            String vaildBase = vaildBase(request, apply_type_id, start_time + " " + s, end_time + " " + e, thingMap);
            if (vaildBase != "")
            {
                return vaildBase;
            }
            return "000";
        }
        return str;
    }

    // 每月10日之后不可提交上月前申请，否则保存时弹出提示：每月10日后，无法提交上月考勤申请
    public String tenApply(String s, String e)
    {
        String str = "";
        // 获取当前日期
        Calendar cl = Calendar.getInstance();
        int datenum = cl.get(Calendar.DATE);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        // 将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        c.set(Calendar.MINUTE, 0);
        // 将秒至0
        c.set(Calendar.SECOND, 0);
        // 将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        long now_time = c.getTimeInMillis();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 每月10日之后不可提交上月前申请，否则保存时弹出提示：每月10日后，无法提交上月考勤申请
        if (datenum >= 10)
        {
            if (s != null)
            {
                try
                {
                    Date start_time = sdf.parse(s.split(" ")[0]);

                    if (now_time > start_time.getTime())
                    {
                        str = "每月10日后，无法提交上月考勤申请";
                        return str;
                    }

                }
                catch (ParseException e1)
                {
                    e1.printStackTrace();
                }

            }
            if (e != null)
            {
                try
                {
                    Date end_time = sdf.parse(e.split(" ")[0]);

                    if (now_time > end_time.getTime())
                    {
                        str = "每月10日后，无法提交上月考勤申请";
                        return str;
                    }

                }
                catch (ParseException e1)
                {
                    e1.printStackTrace();
                }

            }

        }

        return str;
    }

    public Integer findCountFillHistory(String personnel_id, String vacateS, String vacateE)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("daysoff_begintime", vacateS);
        map.put("daysoff_endtime", vacateE);
        // 返回小时数
        Integer historyCount = attendanceDao.findFillcheckCount(map);
        return historyCount;
    }

    public Integer findCountDayoffHistory(String personnel_id, String vacateS, String vacateE)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        map.put("daysoff_begintime", vacateS);
        map.put("daysoff_endtime", vacateE);
        // 返回小时数
        Integer historyCount = attendanceDao.findDaysoffCount(map);
        return historyCount;
    }

    /**
     * @Title: vaildHoilTime
     * @Description: 校验请假额度是否够使用
     * @param thingMap 限制map
     * @param pm 人员
     * @param YearMap 一年第一天最后一天
     * @param monthMap 一月第一天最后一天
     * @param count_time 请假时间
     * @return 
     * @author: zhaowei
     * @time:2017年7月12日 上午9:26:09
     * history:
     * 1、2017年7月12日 zhaowei 创建方法
    */
    public String vaildHoilTime(Map<String, Object> thingMap, PmPersonnel pm, Map<String, Object> YearMap, Map<String, Object> monthMap, Integer count_time)
    {
        // 没限制 直接过
        if (thingMap == null)
        {
            return "000";
        }
        String str = "";
        // 年假全年限制
        if (thingMap.get("holi_repair") != null)
        {
            // 全年请假小时
            Integer yearHistoryTime = findTimeVacateHistory(pm.getPersonnel_id() + "", YearMap.get("year_start") + "", YearMap.get("year_end") + "", 31);
            yearHistoryTime = yearHistoryTime == null ? 0 : yearHistoryTime;
            // 年份小时额度限制
            Integer holi_repair = Integer.parseInt(thingMap.get("holi_repair").toString()) * 8;
            // 判断全年
            if (yearHistoryTime >= holi_repair)
            {
                return "全年请假额度已到上限";
            }
            else if (count_time > (holi_repair - yearHistoryTime))
            {
                return "请假额度超过年度上限";
            }

        }

        if (thingMap.get("holi_day") != null)
        {
            // 每月请假小时
            Integer monthHistoryTime = findTimeVacateHistory(pm.getPersonnel_id() + "", monthMap.get("date_start") + "", YearMap.get("date_end") + "", 31);
            monthHistoryTime = monthHistoryTime == null ? 0 : monthHistoryTime;
            // 每月小时额度限制
            Integer holi_day = Integer.parseInt(thingMap.get("holi_day").toString()) * 8;
            // 判断全年
            if (monthHistoryTime >= holi_day)
            {
                return "当月请假额度已到上限";
            }
            else if (count_time > (holi_day - monthHistoryTime))
            {
                return "请假额度超过当月上限";
            }
        }
        return str;
    }

    /*
     * 请假开始时间早于上班班次时间，或请假结束时间晚于下班班次
     */
    public String vacateAllRegular(List<Map<String, Object>> shiftMap, String vacate_startTime, String vacate_endTime)
    {
        String str = "";
        String w = "";
        String o = "";
        if (shiftMap.get(0).get("start2") == null || shiftMap.get(0).get("start2") == "")
        {
            w = shiftMap.get(0).get("start1").toString();
        }
        else
        {
            w = shiftMap.get(0).get("start2").toString();
        }

        if (shiftMap.get(shiftMap.size() - 1).get("off2") == null || shiftMap.get(shiftMap.size() - 1).get("off2") == "")
        {
            o = shiftMap.get(shiftMap.size() - 1).get("off1").toString();
        }
        else
        {
            o = shiftMap.get(shiftMap.size() - 1).get("off2").toString();
        }
        // 08:50 09:00不行
        if (vacate_startTime.split(" ")[1].compareTo(w) < 0)
        {
            return "请假开始时间不能早于上班时间";
        }
        if (vacate_endTime.split(" ")[1].compareTo(o) > 0)
        {
            return "请假结束时间不能晚于下班时间";
        }
        return str;
    }

    /**
     * @Title: CFRegular
     * @Description: //CF请病假事假 只能从上班开始时间 午休结束时间开始请  请到上班结束时间 可跨天
     * @param shiftMap
     * @return 
     * @author: zhaowei
     * @time:2017年7月11日 下午8:23:50
     * history:
     * 1、2017年7月11日 zhaowei 创建方法
    */
    public String CFRegular(List<Map<String, Object>> shiftMap, String vacate_startTime, String vacate_endTime)
    {
        String w = "";
        String o = "";
        if (shiftMap.get(0).get("start2") == null || shiftMap.get(0).get("start2") == "")
        {
            w = shiftMap.get(0).get("start1").toString();
        }
        else
        {
            w = shiftMap.get(0).get("start2").toString();
        }

        if (shiftMap.get(shiftMap.size() - 1).get("off2") == null || shiftMap.get(shiftMap.size() - 1).get("off2") == "")
        {
            o = shiftMap.get(shiftMap.size() - 1).get("off1").toString();
        }
        else
        {
            o = shiftMap.get(shiftMap.size() - 1).get("off2").toString();
        }

        String r1 = shiftMap.get(0).get("rest1").toString();
        String r2 = shiftMap.get(0).get("rest2").toString();
        if (w.equals(vacate_startTime.split(" ")[1]) && o.equals(vacate_endTime.split(" ")[1]))
        {
            return "";
        }
        else if (w.equals(vacate_startTime.split(" ")[1]) && r1.equals(vacate_endTime.split(" ")[1]))
        {
            return "";
        }
        else if (r2.equals(vacate_startTime.split(" ")[1]) && o.equals(vacate_endTime.split(" ")[1]))
        {
            return "";
        }
        else
        {
            return "请按理财请假规则提交申请!";
        }

    }

    /**
     * @Title: vaildForTime
     * @Description: 除年假外，其余请假类型提前申请可提交1天以上的申请，延后申请可提交4小时以上的申请(针对互联网)
     * @param start_time
     * @param count_time
     * @return 
     * @author: zhaowei
     * @time:2017年7月11日 下午6:12:19
     * history:
     * 1、2017年7月11日 zhaowei 创建方法
    */
    public String vaildForTime(String start_time, int count_time, Integer type)
    {
        String str = "";

        String beTime = CheckingUtil.getSpecifiedDayBefore(start_time.split(" ")[0]);
        String newDate = CheckingUtil.getNow();
        if (newDate.compareTo(beTime) > 0)
        {
            // 延后申请
            if (count_time < 4)
            {
                if (type == null)
                {
                    str = "请假时长需在4小时以上!";
                }
                else
                {
                    str = "调休时长需在4小时以上!";
                }
                return str;
            }
        }
        return str;
    }

    public String vaildBase(HttpServletRequest request, Integer apply_type_id, String start_time, String end_time, Map<String, Object> baseMap)
    {
        String str = "";
        Integer diff = 0;
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String now = format.format(date) + " 09:00";
        // 配置了请假时限
        if (baseMap != null && baseMap.get("seniority") != null)
        {
            // 如果是0 说明必须提前申请
            if (Integer.parseInt(baseMap.get("seniority").toString()) == 0)
            {
                // 提单时间>请假开始时间 精确到分钟
                if (now.compareTo(start_time) > 0)
                {
                    return "提单时间小于开始时间!";
                }
            }
            else
            {
                // 抛出节假日 休息日的请假小时数

                Integer hoilVil = Integer.parseInt(baseMap.get("seniority").toString()) * 8;
                String end = end_time.split(" ")[0] + " 18:00";
                // 出差算的是天数
                if (apply_type_id != 52)
                {
                    diff = Integer.parseInt(getCountTime(request, apply_type_id, end, now).get("count_time").toString());
                }
                else
                {
                    // 出差
                    diff = (Integer.parseInt(getCountTime(request, apply_type_id, end, now).get("count_time").toString()) - 2) * 8;
                }

                if (diff >= hoilVil)
                {

                    str = "超过提单时限!";
                    return str;
                }
            }

        }
        return str;
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
    @Override
    public Map<String, Object> getCountTime(HttpServletRequest request, int apply_type_id, String start_time, String end_time)
    {
        // 获取当前登录人
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        Integer personnel_id = pm.getPersonnel_id();
        Integer dept_id = pm.getPersonnel_deptid();
        Integer secondDeptId = getSecondDeptId(dept_id);
        Map<String, Object> mapSd = new HashMap<String, Object>();
        mapSd.put("apply_type_id", apply_type_id);
        mapSd.put("secondDeptId", secondDeptId);
        // holi_day每月额度',holi_repair每年额度,allow_holiday允许补请假(0:是,1:否)'
        // 其中事假31、病假32、年假33、产假34、婚假35、丧假36、工伤假37、哺乳假38、护理假39、路程假54、27补签申请、29调休申请、28.酬勤申请、52.出差申请、64.项目酬勤申请
        String[] start_timeStr = start_time.split("\\s+");
        String[] end_timeStr = end_time.split("\\s+");
        String start_timeDay = start_timeStr[0];
        String end_timeDay = end_timeStr[0];
        List<Map<String, Object>> shiftMap = getVolumeInfo(start_timeDay, end_timeDay, personnel_id);
        List<Map<String, Object>> shiftMap1 = getVolumeInfo(start_timeDay, start_timeDay, personnel_id);
        List<Map<String, Object>> shiftMap2 = getVolumeInfo(end_timeDay, end_timeDay, personnel_id);
        // 总计时间
        Integer count_time = 0;
        // 班次内的上班小时数
        Integer timeShift = 8;
        // 返回结果集合
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> rule = attendanceDao.getRule(mapSd);
        // 公休假标示1有效0无效(默认无效 下面获取)
        String general_holiday = "0";
        // 法定假标示1有效0无效(默认无效 下面获取)
        String legal_holiday = "0";
        if (rule != null)
        {
            if (rule.get("general_holiday") != null)
            {
                general_holiday = rule.get("general_holiday").toString();
            }
            if (rule.get("legal_holiday") != null)
            {
                legal_holiday = rule.get("legal_holiday").toString();
            }
        }
        String start = "";
        String off = "";
        String day_type = "";
        String rest1 = "";
        String rest2 = "";
        if (apply_type_id == 34)
        {
            Integer diff = Integer.parseInt(getDaySub(start_timeDay, end_timeDay) * 8 + "");
            Integer hous = Integer.parseInt(end_timeStr[1].split(":")[0]) - Integer.parseInt(start_timeStr[1].split(":")[0]) - 1;
            count_time = diff + hous;
        }
        else
        {
            for (int i = 0; i < shiftMap.size(); i++)
            {
                String start1 = shiftMap.get(i).get("start1").toString();
                String start2 = "";
                if (shiftMap.get(i).get("start2") != null)
                {
                    start2 = shiftMap.get(i).get("start2").toString();
                }

                String off1 = shiftMap.get(i).get("off1").toString();
                String off2 = "";
                if (shiftMap.get(i).get("off2") != null)
                {
                    off2 = shiftMap.get(i).get("off2").toString();
                }
                rest1 = shiftMap.get(i).get("rest1").toString();
                rest2 = shiftMap.get(i).get("rest2").toString();
                day_type = shiftMap.get(i).get("day_type").toString(); // 0工作1休息2节假
                String info_data = shiftMap.get(i).get("info_data").toString();// 获取遍历中的日期

                // 是时间段 上班时间取后面的
                if (start2 == "" || start2 == null)
                {
                    start = start1;
                }
                else
                {
                    start = start2;
                }
                // 是时间段 下班时间取后面的
                if (off2 == "" || off2 == null)
                {
                    off = off1;
                }
                else
                {
                    off = off2;
                }
                timeShift = (int) getRealTime(start, off, rest1, rest2, "1", "1", "0", true);
                if (apply_type_id != 52)
                {
                    if (i == 0)
                    {
                        start = start_timeStr[1];
                    }
                    if (i == shiftMap.size() - 1)
                    {
                        off = end_timeStr[1];
                    }
                }

                if (apply_type_id == 52)
                {
                    if ("0".equals(day_type))
                    {
                        count_time = count_time + 1;
                    }

                }
                else if (apply_type_id == 28 || apply_type_id == 64)
                {
                    count_time = count_time + (int) (Integer.parseInt(getRealTime1(start, off, rest1, rest2, "1", "1", day_type, true) + ""));
                }
                else if (apply_type_id == 29)
                {
                    count_time = count_time + (int) (Integer.parseInt(getRealTime(start, off, rest1, rest2, "0", "0", day_type, false) + ""));
                }
                else
                {
                    count_time = count_time + (int) getRealTime(start, off, rest1, rest2, general_holiday, legal_holiday, day_type, false);
                }
            }
        }

        if (count_time == null)
        {
            count_time = 0;
        }
        if (apply_type_id != 34)
        {
            // 任意一个没有班次 返回null
            if ((shiftMap1 == null || shiftMap1.size() == 0) || (shiftMap2 == null || shiftMap2.size() == 0))
            {
                count_time = null;// 没有班次返回null
            }
        }
        // 返回计算总时间,其中出差显示天数
        resMap.put("count_time", count_time);
        // 获取开始月份第一天跟最后一天
        Map<String, Object> MonthFeistAndFinal = CheckingUtil.getFirstAndFinalDay(start_timeDay);
        // 当前月份或者历史请假总时间(小时)
        Integer historyTime = null;
        if (apply_type_id == 33)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(pm.getPersonnel_trialstarttime());
            String now = CheckingUtil.getNow();
            String dateing = now.split("-")[0] + "-" + dateString.split("-")[1] + "-" + dateString.split("-")[2];
            if (now.compareTo(dateing) >= 0)
            {

                historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", CheckingUtil.getSpecifiedYearBefore(dateing, 0), CheckingUtil.getSpecifiedYearBefore(dateing, 1), apply_type_id);
            }
            else
            {
                historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", CheckingUtil.getSpecifiedYearBefore(dateing, -1), CheckingUtil.getSpecifiedYearBefore(dateing, 0), apply_type_id);
            }

            historyTime = historyTime == null ? 0 : historyTime;
        }
        else
        {
            historyTime = findTimeVacateHistory(pm.getPersonnel_id() + "", MonthFeistAndFinal.get("date_start") + "", MonthFeistAndFinal.get("date_end") + "", apply_type_id);
            historyTime = historyTime == null ? 0 : historyTime;
        }

        // 当前月份历史请假总次数
        Integer historyCount = findCountVacateHistory(pm.getPersonnel_id() + "", MonthFeistAndFinal.get("date_start") + "", MonthFeistAndFinal.get("date_end") + "", apply_type_id);
        // 补签历史次数
        Integer historyCount1 = findCountFillHistory(pm.getPersonnel_id() + "", MonthFeistAndFinal.get("date_start") + "", MonthFeistAndFinal.get("date_end") + "");
        // 每个月的总额度 不扣除使用(请假没有申请次数 -- 无限制)
        Integer holi_dayAll = 0;
        // 剩余额度
        Integer holi_day = null;
        // 申请次数
        Integer holi_count = null;
        // 限制集合
        Map<String, Object> thingMap = rule;

        if (apply_type_id == 33)
        {
            // 司龄 key
            String personnel_seniority = pm.getPersonnel_seniority();
            // 向下取整 取完是0.0 2.0 格式
            personnel_seniority = Math.floor(Double.parseDouble(personnel_seniority)) + "";
            Integer sty = Integer.parseInt(personnel_seniority.split("\\.")[0]);
            if (sty > 10)
            {
                sty = 10;
            }
            String flow1 = thingMap.get("flow1") == null ? "{}" : thingMap.get("flow1").toString();
            Map<String, Object> flowMap = (Map) JSON.parse(flow1);
            personnel_seniority = sty + "";
            // 限制总时间
            if ("0".equals(personnel_seniority))
            {
                holi_day = 0;
            }
            else
            {
                if (flowMap.get(personnel_seniority) != null)
                {
                    holi_dayAll = (int) (Integer.parseInt(flowMap.get(personnel_seniority).toString()) * timeShift);
                }

                holi_day = holi_dayAll - historyTime;
            }
            holi_count = null;
        }
        else
        {
            // 说明有限制
            if (rule != null)
            {
                // 额度限制
                if (rule.get("holi_day") != null)
                {
                    // 调休特殊处理
                    if (apply_type_id == 29)
                    {

                        Map<String, Object> md = findSomeDaySetting(pm.getPersonnel_shortcode(), start_timeDay);// 调休额度
                        String year = start_timeDay.split("-")[0];
                        String month = start_timeDay.split("-")[1];
                        double use = Double.parseDouble(md.get("calculateAll").toString());
                        double pass = Double.parseDouble(md.get("T" + year + "" + month).toString());
                        double Nopass = Double.parseDouble(md.get("N" + year + "" + month).toString());
                        double his = 0;
                        Map<String, Object> flowMap = (Map) md.get("YM" + year + "" + month);
                        his = Double.parseDouble(flowMap.get("total").toString());
                        his += pass;
                        double set = Double.parseDouble(rule.get("holi_day").toString()) * timeShift;// work

                        // holi_day = parseInt((his + "").split("\\.")[0]) -
                        // parseInt((use + "").split("\\.")[0]);
                        holi_day = Integer.parseInt((Math.ceil(his - use) + "").split("\\.")[0]);
                        Integer no = Integer.parseInt((Math.ceil(set - Nopass) + "").split("\\.")[0]);
                        if (no > holi_day)
                        {
                            holi_day = holi_day;
                        }
                        else
                        {
                            holi_day = no;
                        }
                        holi_day = holi_day < 0 ? 0 : holi_day;
                    }
                    else if (apply_type_id == 27)
                    {
                        holi_day = Integer.parseInt(rule.get("holi_day").toString()) - historyCount1;
                    }
                    else
                    {
                        holi_day = Integer.parseInt(rule.get("holi_day").toString()) * timeShift - historyTime;
                    }

                }
                // 次数限制
                if (rule.get("holi_count") != null)
                {
                    // 调休特殊处理
                    if (apply_type_id == 29)
                    {

                        // 总次数
                        holi_count = Integer.parseInt(rule.get("holi_count").toString());
                        holi_count -= findSomesoffCount(pm.getPersonnel_id(), start_timeDay);
                    }
                    else
                    {
                        holi_count = Integer.parseInt(rule.get("holi_count").toString()) - historyCount;
                    }
                }

            }
        }
        // 出差 酬勤 没有额度 次数
        if (apply_type_id == 52 || apply_type_id == 64 || apply_type_id == 28)
        {
            holi_day = null;
            holi_count = null;
        }
        if (apply_type_id == 31 || apply_type_id == 32 || apply_type_id == 33 || apply_type_id == 34 || apply_type_id == 35 || apply_type_id == 36 || apply_type_id == 37 || apply_type_id == 38 || apply_type_id == 39 || apply_type_id == 54)
        {
            holi_day = holi_day == null ? -1 : holi_day;
            holi_count = null;
        }
        if (apply_type_id == 27)
        {
            holi_count = holi_day;
            holi_count = holi_count == null ? -1 : holi_count;
            holi_day = null;

        }
        if (apply_type_id == 29)
        {
            holi_day = holi_day == null ? -1 : holi_day;
            holi_count = holi_count == null ? -1 : holi_count;
        }

        resMap.put("holi_day", holi_day);
        resMap.put("holi_count", holi_count);
        return resMap;

    }

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
    @Override
    public List<Map<String, Object>> getAttendanceApplyMenu(HttpServletRequest request)
    {
        List<Map<String, Object>> delist = new ArrayList<>();
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        Integer dept_id = pm.getPersonnel_deptid();
        String dept_code = attendanceDao.getDept_code(dept_id);
        String personnel_state = pm.getPersonnel_state();
        String stateOk = "0,1,2";
        if (!stateOk.contains(personnel_state))
        {
            return null;
        }
        if (dept_code != null && !"".equals(dept_code))
        {
            if ("DZX".equals(dept_code.substring(0, 3)))
            {
                return null;
            }
            // 只有互联网中心有调休和酬勤
            if ("DHL".equals(dept_code.substring(0, 3)))
            {// 30请假申请， 27补签申请、29调休申请、28.酬勤申请、52.出差申请 64.项目酬勤申请
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("apply_type_id", 30);
                map.put("apply_type_name", "请假申请");
                delist.add(0, map);
                map = new HashMap<String, Object>();
                map.put("apply_type_id", 27);
                map.put("apply_type_name", "补签申请");
                delist.add(1, map);
                map = new HashMap<String, Object>();
                map.put("apply_type_id", 29);
                map.put("apply_type_name", "调休申请");
                delist.add(2, map);
                map = new HashMap<String, Object>();
                map.put("apply_type_id", 28);
                map.put("apply_type_name", "酬勤申请");
                delist.add(3, map);
                map = new HashMap<String, Object>();
                map.put("apply_type_id", 52);
                map.put("apply_type_name", "出差申请");
                delist.add(4, map);
                // 判断有没有项目酬勤的权限
                String url = HttpClientUtil.getSysUrl("nozzleUrl");
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("interface_num", "ESM_OUT_001"));
                nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
                nvps.add(new BasicNameValuePair("encryptionId", pm.getPersonnel_encryptionid()));
                nvps.add(new BasicNameValuePair("modules_name", "IOA"));
                Map<String, Object> resmap = new HashMap<String, Object>();
                try
                {
                    resmap = HttpClientUtil.post(url, nvps, Map.class);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    resmap.put("message", e.getMessage());
                }
                String result = resmap.get("result").toString();

                if (result != null && !"".equals(result))
                {
                    // String listMenu = resmap.get("listMenu").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> messageMap = null;
                    try
                    {
                        messageMap = mapper.readValue(result, new TypeReference<Map<String, Object>>()
                        {
                        });
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }

                    String listMenu = messageMap.get("listMenu").toString();
                    if (listMenu.contains("项目酬勤申请"))
                    {
                        // 如果有项目酬勤权限就继续添加进list
                        // 用map把项目酬勤申请放进去list里 64.项目酬勤申请
                        map = new HashMap<String, Object>();
                        map.put("apply_type_id", 64);
                        map.put("apply_type_name", "项目酬勤申请");
                        delist.add(5, map);
                    }
                }
            }
            else
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("apply_type_id", 30);
                map.put("apply_type_name", "请假申请");
                delist.add(0, map);
                map = new HashMap<String, Object>();
                map.put("apply_type_id", 27);
                map.put("apply_type_name", "补签申请");
                delist.add(1, map);
                map = new HashMap<String, Object>();
                map.put("apply_type_id", 52);
                map.put("apply_type_name", "出差申请");
                delist.add(2, map);
                // 判断有没有项目酬勤的权限
                String url = HttpClientUtil.getSysUrl("nozzleUrl");
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("interface_num", "ESM_OUT_001"));
                nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
                nvps.add(new BasicNameValuePair("encryptionId", pm.getPersonnel_encryptionid()));
                nvps.add(new BasicNameValuePair("modules_name", "IOA"));
                Map<String, Object> resmap = new HashMap<String, Object>();
                try
                {
                    resmap = HttpClientUtil.post(url, nvps, Map.class);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    resmap.put("message", e.getMessage());
                }
                String result = resmap.get("result").toString();

                if (result != null && !"".equals(result))
                {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> messageMap = null;
                    try
                    {
                        messageMap = mapper.readValue(result, new TypeReference<Map<String, Object>>()
                        {
                        });
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }

                    String listMenu = messageMap.get("listMenu").toString();
                    if (listMenu.contains("项目酬勤申请"))
                    {
                        // 如果有项目酬勤权限就继续添加进list
                        // 用map把项目酬勤申请放进去list里 64.项目酬勤申请
                        map = new HashMap<String, Object>();
                        map.put("apply_type_id", 64);
                        map.put("apply_type_name", "项目酬勤申请");
                        delist.add(3, map);
                    }

                }
            }
            return delist;
        }
        else
        {
            return null;
        }
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
    @Override
    public ResultBean<Map<String, Object>> getShiftInfoByFillCheckTime(HttpServletRequest request, String fillcheck_time)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        Integer personnel_id = pm.getPersonnel_id();
        // 获取班次时间
        List<Map<String, Object>> shiftMap = getVolumeInfo(fillcheck_time, fillcheck_time, personnel_id);
        String start_time = "";
        String end_time = "";
        if (shiftMap == null || shiftMap.size() == 0)
        {
            resultMap.put("banci", 0);// 没有班次返回0
            start_time = null;
            end_time = null;
        }
        else
        {
            resultMap.put("banci", 1);// 有班次返回1

            String start1 = shiftMap.get(0).get("start1").toString();
            String start2 = "";
            if (shiftMap.get(0).get("start2") != null)
            {
                start2 = shiftMap.get(0).get("start2").toString();
            }
            String off1 = shiftMap.get(0).get("off1").toString();
            String off2 = "";
            if (shiftMap.get(0).get("off2") != null)
            {
                off2 = shiftMap.get(0).get("off2").toString();
            }
            // 取最晚时间点
            if (start2 == "" || start2 == null)
            {
                start_time = start1;
            }
            else
            {
                start_time = start2;
            }
            // 取最晚时间点
            if (off2 == "" || off2 == null)
            {
                end_time = off1;
            }
            else
            {
                end_time = off2;
            }
        }
        // 获取可申请次数
        Integer dept_id = pm.getPersonnel_deptid();
        Integer secondDeptId = getSecondDeptId(dept_id);
        Map<String, Object> mapSd = new HashMap<String, Object>();
        mapSd.put("apply_type_id", 27);
        mapSd.put("secondDeptId", secondDeptId);
        Map<String, Object> rule = attendanceDao.getRule(mapSd);
        // 获取开始月份第一天跟最后一天
        Map<String, Object> MonthFeistAndFinal = CheckingUtil.getFirstAndFinalDay(fillcheck_time);
        // 补签历史次数
        Integer historyCount1 = findCountFillHistory(pm.getPersonnel_id() + "", MonthFeistAndFinal.get("date_start") + "", MonthFeistAndFinal.get("date_end") + "");
        Integer holi_day = null;
        if (rule != null)
        {

            if (rule.get("holi_day") != null)
            {

                holi_day = Integer.parseInt(rule.get("holi_day").toString()) - historyCount1;
            }

        }
        holi_day = holi_day == null ? -1 : holi_day;
        resultMap.put("start_time", start_time);
        resultMap.put("end_time", end_time);
        resultMap.put("holi_count", holi_day);
        ResultBean resultBean = new ResultBean();

        if ("0".equals(resultMap.get("banci").toString()))
        {
            // 没有班次返回0
            resultBean.setRet_code("000");
            resultBean.setRet_data(resultMap);
            resultBean.setRet_msg("所选日期暂无班次配置，无法申请!");
        }
        else
        {
            // 有班次
            resultBean.setRet_code("000");
            resultBean.setRet_data(resultMap);
            resultBean.setRet_msg("操作成功");
        }
        return resultBean;
    }

    /**
     * 
     * @Title: getSecondDeptId
     * @Description: TODO(获取当前dept_id的二级部门id)
     * @return 
     * @author: quwenrui
     * @time:2017年7月10日 下午4:26:40
     * history:
     * 1、2017年7月10日 quwenrui 创建方法
     */
    public Integer getSecondDeptId(Integer dept_id)
    {
        String dept_code = attendanceDao.getDept_code(dept_id);
        String dept_codeStr = dept_code.substring(0, 3);
        if ("DHL".equals(dept_codeStr))
        {
            return 2;
        }
        if ("DPH".equals(dept_codeStr))
        {
            return 26;
        }
        if ("DCF".equals(dept_codeStr))
        {
            return 27;
        }
        if ("DZN".equals(dept_codeStr))
        {
            return 28;
        }
        return null;
    }

    public static long getDaySub(String beginDateStr, String endDateStr)
    {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            // System.out.println("相隔的天数=" + day);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return day;
    }

}
