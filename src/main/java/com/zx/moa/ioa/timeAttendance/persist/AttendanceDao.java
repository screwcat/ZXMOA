package com.zx.moa.ioa.timeAttendance.persist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * 获取IOA我的考勤数据层接口
 * @author zWei
 *
 */
@MyBatisRepository
public interface AttendanceDao
{
    public List<Map<String, Object>> getAttendanceList(Map<String, Object> map);

    /**
     * @Title: findAllVacateSetting
     * @Description: 查询所有请假设置信息
     * @return 
     * @author: zhaowei
     * @time:2017年7月5日 下午6:06:47
     * history:
     * 1、2017年7月5日 zhaowei 创建方法
    */
    public List<Map<String, Object>> findAllVacateSetting(String type);

    /**
     * @Title: findAllDept
     * @Description: 查询部门
     * @return 
     * @author: zhaowei
     * @time:2017年7月5日 下午6:10:24
     * history:
     * 1、2017年7月5日 zhaowei 创建方法
    */
    public List<Map<String, Object>> findAllDept();

    /**
     * @Title: findTimeVacateHistory
     * @Description: 获取请假历史集合
     * @param personnel_id
     * @return 
     * @author: zhaowei
     * @time:2017年7月6日 上午11:00:11
     * history:
     * 1、2017年7月6日 zhaowei 创建方法
    */
    public Integer findTimeVacateHistory(Map<String, Object> map);

    /**
     * 
     * @Title: findTimeVacateHistory
     * @Description: TODO(获取历史请假条数)
     * @param map
     * @return 
     * @author: what
     * @time:2017年7月12日 下午1:51:25
     * history:
     * 1、2017年7月12日 what 创建方法
     */
    public Integer findCountVacateHistory(Map<String, Object> map);

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
    public List<Map<String, Object>> getVolumeInfo(Map<String, Object> map);

    /**
     * @Title: findDaysoffCount
     * @Description: 调休使用次数(一个月)
     * @param map
     * @return 
     * @author: zhaowei
     * @time:2017年7月7日 上午10:54:39
     * history:
     * 1、2017年7月7日 zhaowei 创建方法
    */
    public Integer findDaysoffCount(Map<String, Object> map);

    /**
     * @Title: findFillcheckCount
     * @Description: 补卡使用次数(一个月)
     * @param map
     * @return 
     * @author: zhaowei
     * @time:2017年7月7日 上午11:19:33
     * history:
     * 1、2017年7月7日 zhaowei 创建方法
    */
    public Integer findFillcheckCount(Map<String, Object> map);


    /**
    * @Title: getWarnAttendancePersonnelList
    * @Description: 得前一天考勤有问题需发通知的人
    * @return 
    * @author: sunlq
    * @time:2017年7月5日 下午6:33:48
    * history:
    * 1、2017年7月5日 sunlq 创建方法
    */
    public List<Map<String, Object>> getWarnAttendancePersonnelList();

    /**
     * 查询员工 酬勤数据
     * @param datas 查询年月集合
     * @param code 员工考勤编号
     * @return
     */
    List<Map<String, Object>> findPayoff(@Param("datas")
    List<String> datas, @Param("code")
    String code);
    
    /* 
     * 查询调休记录
    * @param code
    * @param date
    * @return
    */
    public List<Map<String, Object>> findDaysoffHistory(@Param("code")
    String code, @Param("date")
    String date);

    /**
     * 
     * @Title: getDept_code
     * @Description: TODO(查询)
     * @param dept_id
     * @return 
     * @author: what
     * @time:2017年7月10日 下午3:12:27
     * history:
     * 1、2017年7月10日 what 创建方法
     */
    public String getDept_code(Integer dept_id);

    public Map<String, Object> getRule(Map<String, Object> map);

}
