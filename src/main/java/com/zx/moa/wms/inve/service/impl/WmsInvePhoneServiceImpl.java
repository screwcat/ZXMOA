package com.zx.moa.wms.inve.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInvePhoneService;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: WmsInvePhoneServiceImpl
 * 模块名称：
 * @Description: 内容摘要：
 * @author DongHao
 * @date 2016年12月30日
 * @version V1.0
 * history:
 * 1、2016年12月30日 DongHao 创建文件
 */
@Service("wmsInvePhoneService")
public class WmsInvePhoneServiceImpl implements IWmsInvePhoneService
{

    private final Logger log = LoggerFactory.getLogger(WmsInvePhoneServiceImpl.class);

    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");

    private SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月");

    /**
     * @Title: phoneGetDateInfos
     * @Description: 获取年月过滤项
     * @return 
     * @author: DongHao
     * @time:2016年12月30日 上午11:53:02
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#phoneGetDateInfos()
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    @Override
    public List<Map<String, Object>> phoneGetDateInfos()
    {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 结果集合

        try
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            String date_code = format1.format(new Date(calendar.getTimeInMillis()));
            list = getMonthDesc(null, new Date());// 获取日期(当前系统时间到截止日期为'2016-10'的日期按照降序排序)
            for(Map<String, Object> map : list)
            {
                if (map.get("date_code").toString().equals(date_code))
                {
                    map.put("date_init_value", 1);
                }
            }
            
        }
        catch (Exception e)
        {
            log.error("获取年月过滤项报错:" + e.getMessage());
        }

        return list;
    }

    /**
     * 
     * @Title: getMonthDesc
     * @Description: 获取年月过滤项
     * @param list 存放结果的集合
     * @param nowDate 系统当前时间
     * @return
     * @throws Exception 
     * @author: DongHao
     * @time:2016年12月30日 上午11:59:26
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    private List<Map<String, Object>> getMonthDesc(List<Map<String, Object>> list, Date nowDate) throws Exception
    {
        String endDate = "2016-10";// 截止日期

        // 判断集合是否为null,为空则创建list对象
        if (list == null)
        {
            list = new ArrayList<Map<String, Object>>();
        }

        // 定义存放日期的map集合
        Map<String, Object> dateMap = new HashMap<String, Object>();
        dateMap.put("date_msg_str", format2.format(nowDate));
        dateMap.put("date_code", format1.format(nowDate));

        dateMap.put("date_init_value", 0);

        // 向结果集合中添加map集合
        list.add(dateMap);

        // 判断日期是否与截止日期相等,如果相等则返回结果集,不继续进行递归获取日期,否则进行递归继续获取日期
        if (format1.format(nowDate).equals(endDate))
        {
            return list;
        }
        else
        {
            // 将传入的日期进行每次递减一个月
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.MONTH, -1);
            return getMonthDesc(list, new Date(calendar.getTimeInMillis()));
        }
    }

    /**
     * @Title: phoneGetSpecialMenuInfos
     * @Description: 获取特殊功能菜单项
     * @param one_menu_code
     * @param personnel_id
     * @return 
     * @author: DongHao
     * @time:2016年12月30日 下午2:10:28
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#phoneGetSpecialMenuInfos(java.lang.String)
     * history:
     * 1、2016年12月30日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> phoneGetSpecialMenuInfos(String one_menu_code, Integer personnel_id, String version)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getSpecialMenuInfosMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("one_menu_code", one_menu_code);
        form.add("personnel_id", personnel_id.toString());
        form.add("version", version);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (resultMap != null)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;

    }

    /**
     * @Title: phoneGetSpecialTeamInfos
     * @Description: 获取团队信息
     * @param personnel_id
     * @param dept_id
     * @param menu_code
     * @return 
     * @author: DongHao
     * @time:2017年1月1日 下午5:03:33
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#phoneGetSpecialTeamInfos(java.lang.Integer, java.lang.String, java.lang.String)
     * history:
     * 1、2017年1月1日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> phoneGetSpecialTeamInfos(Integer personnel_id, String dept_id, String menu_code)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getSpecialTeamInfosMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("personnel_id", personnel_id.toString());
        form.add("dept_id", dept_id);
        form.add("menu_code", menu_code);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (resultMap != null)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

    /**
     * @Title: phoneGetSpecialDeptInfos
     * @Description: 获取数据权限下的部门清单
     * @param personnel_id
     * @param dept_id
     * @param menu_code
     * @return 
     * @author: DongHao
     * @time:2017年1月3日 上午9:09:52
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#phoneGetSpecialDeptInfos(java.lang.String, java.lang.String)
     * history:
     * 1、2017年1月3日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> phoneGetSpecialDeptInfos(String personnel_id, String dept_id, String menu_code)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getSpecialDeptInfosMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("personnel_id", personnel_id);
        form.add("dept_id", dept_id);
        form.add("menu_code", menu_code);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (resultMap != null && resultMap.size() > 0)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

    /**
     * 
     * @Title: syncMePerformanceInfos
     * @Description: 同步与我有关的业绩信息
     * @param personnel_id 人员id
     * @param query_type 查询数据范围(1:表示待我处理数据,2:与我相关数据)
     * @return 
     * @author: DongHao
     * @time:2017年1月5日 下午2:45:33
     * history:
     * 1、2017年1月5日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> syncMePerformanceInfos(String personnel_id, String query_type)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getSyncMePerformanceInfosMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("personnel_id", personnel_id);
        form.add("query_type", query_type);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
        if(resultMap != null && resultMap.size() > 0)
        {
            if(resultMap.get("list") != null)
            {
                resultList = (List<Map<String, Object>>) resultMap.get("list");
            }
        }
         
        return resultList;
    }

    /**
     * @Title: getAchVicegelList
     * @Description: 获取数据权限下的副总清单
     * @param menu_code
     * @param personnel_id
     * @return 
     * @author: DongHao
     * @time:2017年1月6日 上午11:43:15
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getAchVicegelList(java.lang.String)
     * history:
     * 1、2017年1月6日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getAchVicegelList(String menu_code, String personnel_id)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getAchVicegelListMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("menu_code", menu_code);
        form.add("personnel_id", personnel_id);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (resultMap != null && resultMap.size() > 0)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

    /**
     * @Title: getClerkData
     * @Description:获取业务员客户排队列表
     * @param string
     * @return 
     * @author: sunlq
     * @time:2017年2月16日 下午2:03:53
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getClerkData(java.lang.String)
     * history:
     * 1、2017年2月16日 Guanxu 创建方法
     */
    @Override
    public Map<String, Object> getClerkData(String personnel_id)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/phoneGetClerkDataMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("personnel_id", personnel_id);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        return resultMap;
    }

    @Override
    public Map<String, Object> getClerkDataCount(String personnel_id)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/phoneGetClerkDataCountMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("personnel_id", personnel_id);
        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);
        return resultMap;
    }

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
     * @time:2017年3月10日 下午3:57:19
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getTeamCommMonByDeptManager(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月10日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getTeamCommMonByDeptManager(Integer page, Integer page_size, String statics_month, String dept_ids,
                                                                 String personnel_info, PmPersonnel personnel)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getTeamCommMonByDeptManagerMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("page", page.toString());
        form.add("page_size", page_size.toString());
        form.add("statics_month", statics_month);
        form.add("dept_ids", dept_ids);
        form.add("personnel_info", personnel_info);
        form.add("personnel_id", personnel.getPersonnel_id().toString());

        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (resultMap != null && resultMap.size() > 0)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

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
     * @time:2017年3月13日 上午9:41:37
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getTeamCommMonBySaleman(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getTeamCommMonBySaleman(Integer page, Integer page_size, String statics_month, String dept_ids,
                                                             Integer personnel_id, String personnel_info, PmPersonnel personnel)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getTeamCommMonBySalemanMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("page", page.toString());
        form.add("page_size", page_size.toString());
        form.add("statics_month", statics_month);
        form.add("dept_ids", dept_ids);
        form.add("personnel_info", personnel_info);
        form.add("personnel_id", personnel_id == null ? "" : personnel_id.toString());
        form.add("user_id", personnel.getPersonnel_id().toString());

        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (resultMap != null && resultMap.size() > 0)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

    /**
     * @Title: getTeamCommMonByItem
     * @Description: 获取管理佣金按佣金项汇总
     * @param statics_month 佣金统计月份
     * @param personnel_id 客户经理人员id
     * @return 返回结果集合
     * @author: DongHao
     * @time:2017年3月13日 上午11:15:12
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getTeamCommMonByItem(java.lang.String, java.lang.String)
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getTeamCommMonByItem(String statics_month, String personnel_id)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getTeamCommMonByItemMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("statics_month", statics_month);
        form.add("personnel_id", personnel_id);

        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (resultMap != null && resultMap.size() > 0)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

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
     * @time:2017年3月13日 上午11:39:53
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getTeamCommMonByManager(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getTeamCommMonByManager(Integer page, Integer page_size, String statics_month, String personnel_info,
                                                             PmPersonnel personnel)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getTeamCommMonByManagerMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("page", page.toString());
        form.add("page_size", page_size.toString());
        form.add("personnel_info", personnel_info);
        form.add("statics_month", statics_month);
        form.add("personnel_id", personnel.getPersonnel_id().toString());

        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);

        if (resultMap == null)
        {
            resultMap = new HashMap<>();
        }
        return resultMap;
    }

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
     * @time:2017年3月13日 上午11:54:45
     * @see com.zx.moa.wms.inve.service.IWmsInvePhoneService#getTeamCommMonByData(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
     * history:
     * 1、2017年3月13日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getTeamCommMonByData(Integer page, Integer page_size, String statics_month, Integer personnel_id,
                                                          String comm_item_ids, String personnel_info)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getTeamCommMonByDataMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("page", page.toString());
        form.add("page_size", page_size.toString());
        form.add("personnel_info", personnel_info);
        form.add("statics_month", statics_month);
        form.add("personnel_id", personnel_id != null ? personnel_id.toString() : null);
        form.add("comm_item_ids", comm_item_ids);

        Map<String, Object> resultMap = restTemplate.postForObject(url, form, Map.class);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (resultMap != null && resultMap.size() > 0)
        {
            resultList = (List<Map<String, Object>>) resultMap.get("Rows");
        }
        return resultList;
    }

}
