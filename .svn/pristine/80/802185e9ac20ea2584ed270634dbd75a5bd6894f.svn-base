package com.zx.moa.wms.inve.service;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: IWmsInvePhoneService
 * 模块名称：
 * @Description: 内容摘要：
 * @author DongHao
 * @date 2016年12月30日
 * @version V1.0
 * history:
 * 1、2016年12月30日 DongHao 创建文件
 */
public interface IWmsInvePhoneService
{

    /**
     * @Title: phoneGetDateInfos
     * @Description: 获取日期年月过滤项
     * @return 
     * @author: DongHao
     * @time:2016年12月30日 上午11:52:40
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    List<Map<String, Object>> phoneGetDateInfos();

    /**
     * @Title: phoneGetSpecialMenuInfos
     * @Description: 获取特殊功能菜单项
     * @param one_menu_code
     * @param personnel_id
     * @return 
     * @author: DongHao
     * @time:2016年12月30日 下午2:09:59
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    List<Map<String, Object>> phoneGetSpecialMenuInfos(String one_menu_code, Integer personnel_id, String verson);

    /**
     * @Title: phoneGetSpecialTeamInfos
     * @Description: 获取团队信息
     * @param personnel_id
     * @param dept_id
     * @param menu_code
     * @return 
     * @author: DongHao
     * @time:2017年1月1日 下午5:03:05
     * history:
     * 1、2017年1月1日 DongHao 创建方法
     */
    List<Map<String, Object>> phoneGetSpecialTeamInfos(Integer personnel_id, String dept_id, String menu_code);

    /**
     * @Title: phoneGetSpecialDeptInfos
     * @Description: 获取数据权限下的部门清单
     * @param personnel_id
     * @param dept_id
     * @param menu_code
     * @return 
     * @author: DongHao
     * @time:2017年1月3日 上午9:09:21
     * history:
     * 1、2017年1月3日 DongHao 创建方法
     */
    List<Map<String, Object>> phoneGetSpecialDeptInfos(String personnel_id, String dept_id, String menu_code);

    /**
     * @Title: syncMePerformanceInfos
     * @Description: 同步与我有关的业绩信息
     * @param personnel_id
     * @param query_type
     * @return 
     * @author: DongHao
     * @time:2017年1月6日 上午9:04:50
     * history:
     * 1、2017年1月6日 DongHao 创建方法
     */
    List<Map<String, Object>> syncMePerformanceInfos(String personnel_id, String query_type);

    /**
     * @Title: getAchVicegelList
     * @Description: 获取数据权限下的副总清单
     * @param menu_code
     * @param personnel_id
     * @return 
     * @author: DongHao
     * @time:2017年1月6日 上午11:42:40
     * history:
     * 1、2017年1月6日 DongHao 创建方法
     */
    List<Map<String, Object>> getAchVicegelList(String menu_code, String personnel_id);

    /**
     * @Title: getClerkData
     * @Description:获取业务员客户排队列表
     * @param string
     * @return 
     * @author: sunlq
     * @time:2017年2月16日 下午2:03:26
     * history:
     * 1、2017年2月16日 Guanxu 创建方法
     */
    Map<String, Object> getClerkData(String string);

    /**
     * @Title: getClerkDataCount
     * @Description: 获取手持端的排队总人数
     * @param personnel_id
     * @return 
     * @author: sunlq
     * @time:2017年3月1日 下午2:10:54
     * history:
     * 1、2017年3月1日 Guanxu 创建方法
     */
    Map<String, Object> getClerkDataCount(String personnel_id);

    /**
     * @Title: getTeamCommMonByDeptManager
     * @Description: 获取管理佣金按部门经理进行汇总(接口 33)
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 统计月份
     * @param dept_ids 团队id(多个团队id以","分隔)
     * @param personnel_info 部门经理姓名或者部门经理短工号
     * @param request 请求信息
     * @return 返回数据集合
     * @author: DongHao
     * @time:2017年3月10日 下午3:56:25
     * history:
     * 1、2017年3月10日 DongHao 创建方法
     */
    List<Map<String, Object>> getTeamCommMonByDeptManager(Integer page, Integer page_size, String statics_month, String dept_ids,
                                                          String personnel_info, PmPersonnel personnel);

    /**
     * @Title: getTeamCommMonBySaleman
     * @Description: 获取管理佣金按客户经理汇总
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 统计月份
     * @param dept_ids 团队id值(多个团队id以","分隔)
     * @param personnel_id 部门经理人员id
     * @param personnel_info 客户经理姓名或客户经理短工号
     * @param personnel 当前登录moa人员信息对象
     * @return 返回数据集合
     * @author: DongHao
     * @time:2017年3月13日 上午9:41:01
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    List<Map<String, Object>> getTeamCommMonBySaleman(Integer page, Integer page_size, String statics_month, String dept_ids, Integer personnel_id,
                                                      String personnel_info, PmPersonnel personnel);

    /**
     * @Title: getTeamCommMonByItem
     * @Description: 获取管理佣金按佣金项汇总
     * @param statics_month 佣金统计月份
     * @param personnel_id 客户经理人员id
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:14:48
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    List<Map<String, Object>> getTeamCommMonByItem(String statics_month, String personnel_id);

    /**
     * @Title: getTeamCommMonByManager
     * @Description: 获取管理佣金按客户经理汇总For副总/中分总/总经理/后线领导
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 佣金统计月份
     * @param personnel_info 客户经理姓名或客户经理的短工号
     * @param personnel 当前登录moa系统的人员信息对象
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:39:15
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    Map<String, Object> getTeamCommMonByManager(Integer page, Integer page_size, String statics_month, String personnel_info,
                                                      PmPersonnel personnel);

    /**
     * @Title: getTeamCommMonByData
     * @Description: 获取管理佣金按单据展现
     * @param page 当前页
     * @param page_size 每页显示的记录数
     * @param statics_month 佣金统计月份
     * @param personnel_id 人员id
     * @param comm_item_ids 佣金项id(01表示老佣金,02表示新佣金)
     * @param personnel_info 客户姓名
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:54:21
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    List<Map<String, Object>> getTeamCommMonByData(Integer page, Integer page_size, String statics_month, Integer personnel_id, String comm_item_ids,
                                                   String personnel_info);

}
