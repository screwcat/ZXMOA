package com.zx.moa.wms.request.loan.service.Impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.SysTools;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.SysDept;
import com.zx.moa.wms.loan.persist.IWmsCreCustomerChangeLineContactDao;
import com.zx.moa.wms.loan.persist.PmPersonnelDao;
import com.zx.moa.wms.loan.persist.SysConcurrentPostDao;
import com.zx.moa.wms.loan.persist.SysDeptDao;
import com.zx.moa.wms.loan.persist.SysNoticeHeadDao;
import com.zx.moa.wms.loan.persist.SysUserRoleDao;
import com.zx.moa.wms.loan.persist.WmsCreCreditHeadDao;
import com.zx.moa.wms.loan.persist.WmsCreCreditLineCustomerChangeHeadDao;
import com.zx.moa.wms.loan.persist.WmsCreCreditNotaryWarnDao;
import com.zx.moa.wms.loan.persist.WmsCreCreditNotificationMessageDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingApplyAttDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingApprovalInfoDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingFileInfoDao;
import com.zx.moa.wms.loan.persist.WmsSysDictDao;
import com.zx.moa.wms.loan.persist.WmsSysDictDataDao;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditNotaryWarnSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContact;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContactBeanVo;
import com.zx.moa.wms.request.loan.service.HouseLoanServiceFour;
import com.zx.moa.wms.request.loan.util.WmsHelp;
import com.zx.moa.wms.request.loan.vo.BizTeamRankingBeanVO;
import com.zx.moa.wms.request.loan.vo.SysNoticeHeadSearchBeanVO;
import com.zx.platform.syscontext.util.StringUtil;

@Service("houseloanservicefour")
public class HouseLoanServiceFourImpl implements HouseLoanServiceFour{

    @Autowired
    private WmsCreCreditHeadDao wmsCreCreditHeadDao;
    
    @Autowired
    private WmsCreHousingApplyAttDao wmsCreHousingApplyAttDao;
    
    @Autowired
    private WmsCreHousingFileInfoDao wmsCreHousingFileInfoDao;
    
    @Autowired
    private  WmsSysDictDao wmsSysDictDao;//字典表dao
    
    @Autowired
    private  WmsSysDictDataDao wmssysdictdataDao;
    @Autowired
    private  PmPersonnelDao  pmPersonnelDao;//人员表dao
    @Autowired
    private   SysDeptDao sysDeptDao;//部门dao
    @Autowired
    private   SysUserRoleDao sysUserRoleDao;//人员角色
    @Autowired
    private WmsCreHousingApprovalInfoDao wmsCreHousingApprovalInfoDao;//房贷——审批信息表
    @Autowired
    private SysConcurrentPostDao sysConcurrentPostDao;//兼职表
    @Autowired
    private WmsCreCreditNotificationMessageDao wmscrecreditnotificationmessageDao;
    @Autowired
    private IWmsCreCustomerChangeLineContactDao wmscrecustomerchangelinecontactDao;
    @Autowired
    private WmsCreCreditNotaryWarnDao wmscrecreditnotarywarnDao;
    @Autowired
    private WmsCreCreditLineCustomerChangeHeadDao wmsCreCreditLineCustomerChangeHeadDao;
    @Autowired
    private SysNoticeHeadDao sysNoticeHeadDao;// 公告信息获取
    
    /**
     * @Title: getMessage
     * @Description: TODO(3.2.2     新通知标识)
     * @param userId
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 上午9:39:08
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getMessage(java.lang.Integer)
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    @Override
	public Integer getMessageForFour(Integer user_id, String app_name)
    {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("app_name", app_name);
		return wmscrecreditnotificationmessageDao.getMessageForFour(map);
    }



    /**
     * @Title: getWmsMessageList
     * @Description: TODO(3.2.3     通知中心列表)
     * @param personnel_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 上午10:05:38
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getWmsMessageList(java.lang.Integer)
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    @Override
    public List<Map<String, Object>> getWmsMessageListForFour(Integer personnel_id,String app_name)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user_id", personnel_id);
        map.put("app_name", app_name);
        return wmscrecreditnotificationmessageDao.searchMessageListForFour(map);
    }



    /**
     * @Title: getBizContactInformation
     * @Description: TODO(3.2.17        获取联系人信息初始化)
     * @param userId
     * @param wms_cre_credit_head_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 上午11:09:03
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizContactInformation(java.lang.Integer, java.lang.Integer)
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    @Override
    public List<Map<String,Object>> getBizContactInformation(WmsCreCustomerChangeLineContactBeanVo vo)
    {
        Map<String, Object> Map = new HashMap<String, Object>();
        WmsCreCustomerChangeLineContact wmscrecustomerchangelinecontact = new WmsCreCustomerChangeLineContact();
        wmscrecustomerchangelinecontact.setWms_cre_credit_head_id(vo.getWms_cre_credit_head_id());
        List<Map<String,Object>> contactList = wmscrecustomerchangelinecontactDao.getBizContactInformation(wmscrecustomerchangelinecontact);
        return contactList;
    }



    /**
     * @Title: getBizSignedCustomer
     * @Description: TODO(3.2.9     获取签单客户信息)
     * @param sort_code
     * @param bill_type_code
     * @param customer_name
     * @param page
     * @param size
     * @param is_need_paging
     * @return Map<String, Object>
     * @author: jiaodelong
     * @time:2017年3月20日 上午11:21:57
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizSignedCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
     * history:
     * 1、2017年3月20日 jiaodelong 创建方法
     */
    @Override
    public Map<String, Object> getBizSignedCustomer(WmsCreCreditHeadFourSearchBeanVO vo, PmPersonnel personnel)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtil.isNotBlank(vo.getBill_type_code()) && !vo.getBill_type_code().equals("0"))
        {
            paramMap.put("bill_type", vo.getBill_type_code());
        }
        if (StringUtil.isNotBlank(vo.getCustomer_name()))
        {
            paramMap.put("customer_name", com.zx.moa.util.SysTools.getSqlLikeParam(vo.getCustomer_name()));
        }
        if(StringUtils.isNotEmpty(vo.getIs_need_paging())) {
            if(vo.getIs_need_paging().toString().equals("0")){
                paramMap.put("pagesize", null);
                paramMap.put("offset", null);
            } else {
                paramMap.put("pagesize", vo.getPagesize());
                paramMap.put("offset", vo.getOffset());
            }        
        }
        // //1为签单日期
        // if(vo.getSort_code().equals("1")){
        // paramMap.put("sortname", "protocol_date");
        // }
        // //2为放款金额
        // if(vo.getSort_code().equals("2")){
        // paramMap.put("sortname", "loan_amount");
        // }
        if (StringUtil.isNotBlank(vo.getSort_code()))
        {
            paramMap.put("sortname", vo.getSort_code());
        }
        else
        {
            paramMap.put("sortname", "protocol_date");
        }
        paramMap.put("salesman_shortcode", personnel.getPersonnel_shortcode());
        paramMap.put("sortorder", "desc");
        List<Map<String, Object>> list = wmscrecreditnotarywarnDao.getBizSignedCustomer(paramMap);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("customersList", list);
        return resMap;
    }


    /**
     * @Title: getBizProgressDocuments
     * @Description: TODO(3.2.6     首页进展中单据)
     * @param user_id
     * @param page
     * @param pagesize
     * @param page2
     * @param is_need_paging
     * @return 
     * @author: jiaodelong
     * @time:2017年3月20日 下午2:28:52
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizProgressDocuments(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
     * history:
     * 1、2017年3月20日 jiaodelong 创建方法
     */
    @Override
    public List<Map<String, Object>> getBizProgressDocuments(WmsCreCreditHeadFourSearchBeanVO vo)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", vo.getUser_id());
        if(StringUtils.isNotEmpty(vo.getIs_need_paging()) && vo.getIs_need_paging().equals("0")) {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        } else {
            paramMap.put("pagesize", vo.getPagesize());
            paramMap.put("offset", vo.getOffset());
        }
        List<Map<String, Object>> list = wmsCreCreditHeadDao.getBizProgressDocuments(paramMap);
        return list;
    }

    /**
     * @Title: getBizAuthorityUp
     * @Description: (获取权限)
     * @param queryInfo
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月20日 下午6:07:14
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizAuthorityUp(com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月20日 baisong 创建方法
     */
    @Override
    public Map<String, Object> getBizAuthorityUp(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> reqMap = new HashMap<String, Object>();

        List<Map<String, Object>> list = pmPersonnelDao.getSpecialMenuInfosMoa(personnel.getPersonnel_id());// 当前登陆人的菜单权限
        // 菜单
        reqMap.put("ret_data", list);

        return reqMap;
    }

    /**
     * @Title: getBizSortingState
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param queryInfo
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月21日 上午8:58:31
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizSortingState(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月21日baisong 创建方法
     */
    @Override
    public Map<String, Object> getBizSortingState(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> retMap = new HashMap<String, Object>();
        // 1：签单客户使用筛选状态（客户管理《签单客户）
        // 2：常用单据状态（进度查询）
        // 3：全部单据状态（申请日期排序）(单据统计)
        // 4、常用单据状态（历史单据查询）
        if ("1".equals(queryInfo.getStats_type()))
        {
            // -- 签单客户条件排序
            paramMap.put("wms_sys_dict_id", "133");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> sortList = wmssysdictdataDao.search(paramMap);

            // -- 业务状态筛选
            paramMap.put("wms_sys_dict_id", "124");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> billtypeList = wmssysdictdataDao.search(paramMap);
            Map<String, Object> pMap = new HashMap<String, Object>();
            pMap.put("value_code", "");
            pMap.put("value_meaning", "全部");
            billtypeList.add(0, pMap);
            retMap.put("sortList", sortList);
            retMap.put("billtypeList", billtypeList);
            return retMap;
        }
        // 1：签单客户使用筛选状态（客户管理《签单客户）
        // 2：常用单据状态（进度查询）
        // 3：全部单据状态（申请日期排序）(单据统计)
        // 4、常用单据状态（历史单据查询）
        if ("2".equals(queryInfo.getStats_type()))
        {
            // -- 常用单据状态（进度查询）
            paramMap.put("wms_sys_dict_id", "134");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> billstatusList = wmssysdictdataDao.search(paramMap);
            retMap.put("billstatusList", billstatusList);
            return retMap;
        }
        // 1：签单客户使用筛选状态（客户管理《签单客户）
        // 2：常用单据状态（进度查询）
        // 3：全部单据状态（申请日期排序）(单据统计)
        // 4、常用单据状态（历史单据查询）
        if ("3".equals(queryInfo.getStats_type()))
        {
            // -- 全部单据状态（申请日期排序）(单据统计)
            paramMap.put("wms_sys_dict_id", "131");
            paramMap.put("value_code_type", "1");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> flowbillstatusList = wmssysdictdataDao.searchSpecial(paramMap);
            Map<String, Object> pMap = new HashMap<String, Object>();
            pMap.put("value_code", "");
            pMap.put("value_meaning", "全部");
            flowbillstatusList.add(0, pMap);
            retMap.put("flowbillstatusList", flowbillstatusList);
            // -- 全部单据状态（申请日期排序）(单据统计)
            paramMap.put("wms_sys_dict_id", "131");
            paramMap.put("value_code_type", "2");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> backbillstatusList = wmssysdictdataDao.searchSpecial(paramMap);
            retMap.put("backbillstatusList", backbillstatusList);
            // -- 全部单据状态（申请日期排序）(单据统计)
            paramMap.put("wms_sys_dict_id", "131");
            paramMap.put("value_code_type", "3");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> unbillstatusList = wmssysdictdataDao.searchSpecial(paramMap);
            retMap.put("unbillstatusList", unbillstatusList);

            // --申请日期排序
            paramMap.put("wms_sys_dict_id", "143");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> sortcodeList = wmssysdictdataDao.search(paramMap);
            retMap.put("sortcodeList", sortcodeList);
            return retMap;
        }
        // 1：签单客户使用筛选状态（客户管理《签单客户）
        // 2：常用单据状态（进度查询）
        // 3：全部单据状态（申请日期排序）(单据统计)
        // 4、常用单据状态（历史单据查询）
        if ("4".equals(queryInfo.getStats_type()))
        {
            // -- 全部单据状态（申请日期排序）(单据统计)
            paramMap.put("wms_sys_dict_id", "131");
            paramMap.put("value_code_type", "4");
            paramMap.put("sortname", "value_code = 'W' desc,sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> billstatusList = wmssysdictdataDao.searchSpecial(paramMap);
            Map<String, Object> pMap = new HashMap<String, Object>();
            pMap.put("value_code", "");
            pMap.put("value_meaning", "全部");
            billstatusList.add(0, pMap);
            retMap.put("billstatusList", billstatusList);
            // --申请日期排序
            paramMap.put("wms_sys_dict_id", "143");
            paramMap.put("sortname", "sort_order");
            paramMap.put("sortorder", "asc");
            List<Map<String, Object>> sortcodeList = wmssysdictdataDao.search(paramMap);
            retMap.put("sortcodeList", sortcodeList);

            return retMap;
        }
        return retMap;
    }

    /**
     * @Title: getBizTimeScreening
     * @Description: 3.2.23 WMS_OUT_023 初始化时间筛选条件
     * @param queryInfo
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月21日 上午11:58:18
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizTimeScreening(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月21日 baisong创建方法
     */
    @Override
    public Map<String, Object> getBizTimeScreening(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> retMap = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat bartDateFormatM = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat bartDateFormatC = new SimpleDateFormat("yyyy年MM月");
        Calendar calendar = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        for (int i = 0; i < 12; i++)
        {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("value_code", bartDateFormatM.format(calendar.getTime()));
            paramMap.put("value_meaning", bartDateFormatC.format(calendar.getTime()));
            if (i == 0)
            {
                paramMap.put("is_default", 1);
            }
            else
            {
                paramMap.put("is_default", 0);
            }
            list.add(paramMap);
            calendar.add(Calendar.MONTH, -1);
        }
        retMap.put("ret_data", list);
        return retMap;

    }
    /**
     * @Title: getBizDepartmentScreening
     * @Description: 3.2.24 WMS_OUT_024 初始化单位筛选条件
     * @param queryInfo
     * @param personnel
     * @return 
     * @author:baisong
     * @time:2017年3月21日 下午2:01:28
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizDepartmentScreening(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月21日baisong 创建方法
     */
    @Override
    public Map<String, Object> getBizDepartmentScreening(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> retMap = new HashMap<String, Object>();
        // 判断部门信息是否为空 部门编码部门等级
        if (queryInfo.getDept_info() != null && !"".equals(queryInfo.getDept_info()))
        {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("dept_code", queryInfo.getDept_info());
            List<Map<String, Object>> list = sysDeptDao.getCDeptInfobyCode(paraMap);
            // 判断是否为空
            if (list != null && list.size() > 0)
            {
                boolean is_cz = false;
                 for (int i = 0; i < list.size(); i++)
                 {
                    Map<String, Object> map = list.get(i);
                    /* if (map.get("dept_id") != null && personnel.getPersonnel_id().equals(map.get("dept_id").toString()))
                    {
                        map.put("is_default", 1);
                        is_cz = true;
                    }
                    else
                    {*/
                    map.put("is_default", 0);
                    /* }
                     // 判断是否是最后一个并且还没有找到符合的默认值则默认给第一个
                     if (i == list.size() - 1 && !is_cz)
                     {
                         list.get(0).put("is_default", "1");
                     }*/
                }
                Map<String, Object> mapAll = new HashMap<>();
                mapAll.put("dept_id", "");
                mapAll.put("dept_code", queryInfo.getDept_info());
                mapAll.put("dept_name", "全部");
                mapAll.put("dept_level", queryInfo.getDept_level());
                mapAll.put("is_default", "1");
                list.add(0, mapAll);

            }

            retMap.put("ret_data", list);
            return retMap;
        }
        // 菜单编码
        else if (queryInfo.getMenu_code() != null && !"".equals(queryInfo.getMenu_code()))
        {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("menu_code", queryInfo.getMenu_code());
            paraMap.put("personnel_id", personnel.getPersonnel_id());
            String dept_all = sysDeptDao.getDeptInfobyMenu(paraMap);
            if ("".equals(dept_all) || dept_all == null)
            {
                retMap.put("ret_data", "[]");
                return retMap;
            }
            paraMap.put("dept_all", dept_all);
            // 判断菜单--D0305门店管理--D0305门店管理 --公司战略
            if (WmsHelp.MENU_CODE_TDGL.equals(queryInfo.getMenu_code()))
            {
                paraMap.put("dept_level", "6");
            }
            else if (WmsHelp.MENU_CODE_MDGL.equals(queryInfo.getMenu_code()) || WmsHelp.MENU_CODE_GSZL.equals(queryInfo.getMenu_code()))
            {
                paraMap.put("dept_level", "5");
            }
            paraMap.put("dept_code", WmsHelp.TOP_DEPT_CODE);// 贷款端DPHCPGLBXXX001产品管理部id=31
            List<Map<String, Object>> list = sysDeptDao.getDeptInfobyCodeLevel(paraMap);
            // 判断是否为空
            if (list != null && list.size() > 0)
            {
                // 标示是否需要下拉全部选项 is_addall String 否 0为不需要全部 1和空为需要全部
                boolean is_addall=false;
                if("0".equals(queryInfo.getIs_addall())){
                    is_addall=false;
                }else{
                    is_addall=true;
                }
                boolean is_cz = false;
                for (int i = 0; i < list.size(); i++)
                {
                    Map<String, Object> map = list.get(i);
                    // 如果不需要全部
                    if (!is_addall)
                    {
                        // 判断部门
                        if (map.get("dept_id") != null && personnel.getPersonnel_id().equals(map.get("dept_id").toString()))
                        {
                            map.put("is_default", 1);
                            is_cz = true;
                        }
                        else
                        {
                            map.put("is_default", 0);
                        }
                        // 判断是否是最后一个并且还没有找到符合的默认值则默认给第一个
                        if (i == list.size() - 1 && !is_cz)
                        {
                            list.get(0).put("is_default", "1");
                        }
                    }
                    else
                    {
                        map.put("is_default", 0);
                    }
                }
                if (is_addall)
                {
                    Map<String, Object> mapAll = new HashMap<>();
                    mapAll.put("dept_id", "");
                    if (StringUtils.isNotEmpty(queryInfo.getDept_info()))
                    {
                        mapAll.put("dept_code", queryInfo.getDept_info());
                    }
                    else
                    {
                        mapAll.put("dept_code", "");
                    }
                    mapAll.put("dept_name", "全部");
                    if (StringUtils.isNotEmpty(queryInfo.getDept_level()))
                    {
                        mapAll.put("dept_level", queryInfo.getDept_level());
                    }
                    else
                    {
                        mapAll.put("dept_level", "");
                    }
                    mapAll.put("is_default", "1");
                    list.add(0, mapAll);
                }
            }
            retMap.put("ret_data", list);
            return retMap;
        }
        return new HashMap<String, Object>();
    }

    /**
     * @Title: getBizApplyInitialize
     * @Description: 3.2.11 WMS_OUT_011 贷款申请初始化信息
     * @param queryInfo
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月22日 下午3:01:57
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizApplyInitialize(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月22日baisong 创建方法
     */
    @Override
    public Map<String, Object> getBizApplyInitialize(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> retMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 客户年龄
        paramMap.put("wms_sys_dict_id", "130");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        List<Map<String, Object>> sysDictDataAgeList = wmssysdictdataDao.search(paramMap);
        // 照片张数
        paramMap.put("wms_sys_dict_id", "135");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        List<Map<String, Object>> sysDictDataNumList = wmssysdictdataDao.search(paramMap);
        if(sysDictDataNumList!=null&&sysDictDataNumList.size()>0){
            retMap.put("picture_num", sysDictDataNumList.get(0).get("value_code"));
        }
        paramMap.clear();
        // 抵押形式
        paramMap.put("wms_sys_dict_id", "146");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        paramMap.put("value_code_xiao", "100");
        List<Map<String, Object>> sysDictDataMortList = wmssysdictdataDao.search(paramMap);

        paramMap.clear();
        // 图片
        paramMap.put("wms_sys_dict_id", "92");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        paramMap.put("weighted_value", "%1%");// 获取权重值字段不为空的项
        List<Map<String, Object>> sysDictDataAttList1 = wmssysdictdataDao.search(paramMap);
        if (sysDictDataAttList1 != null && sysDictDataAttList1.size() > 0)
        {
            for (Map<String, Object> map : sysDictDataAttList1)
            {
                map.put("data_type_name", map.get("p_wms_sys_dict_data_id"));
                map.put("data_detail_name", map.get("wms_sys_dict_data_id"));
                map.put("img_name", map.get("value_meaning"));
                // 房产证需要特殊处理
                if (map.get("wms_sys_dict_data_id") != null && "846".equals(map.get("wms_sys_dict_data_id").toString()))
                {
                    map.put("img_name", "房产证");
                }
            }
        }
        paramMap.put("weighted_value", "%2%");// 获取权重值字段不为空的项
        List<Map<String, Object>> sysDictDataAttList2 = wmssysdictdataDao.search(paramMap);
        if (sysDictDataAttList2 != null && sysDictDataAttList2.size() > 0)
        {
            for (Map<String, Object> map : sysDictDataAttList2)
            {
                map.put("data_type_name", map.get("p_wms_sys_dict_data_id"));
                map.put("data_detail_name", map.get("wms_sys_dict_data_id"));
                map.put("img_name", map.get("value_meaning"));
                // 房产证需要特殊处理
                if (map.get("wms_sys_dict_data_id") != null && "846".equals(map.get("wms_sys_dict_data_id").toString()))
                {
                    map.put("img_name", "房产证");
                }
            }
        }
        // 城市
        if (retMap.get("city") == null)
        {
            try{
                Map<String, Object> pMap = new HashMap<String, Object>();
                pMap.put("personnel_regionNumber", personnel.getPersonnel_regionnumber());
                Map<String, Object> cityMap = wmssysdictdataDao.getCityInfo(pMap);
                // 新增城市编码替换
                retMap.put("city", cityMap.get("city"));  
            }
            catch (Exception e)
            {
                e.printStackTrace();
                retMap.put("city", "");    
            }
        }
        retMap.put("ageList", sysDictDataAgeList);// 年龄
        retMap.put("mort_flag_list", sysDictDataMortList);// 抵押形式
        Map<String, Object> mortMap = new HashMap<>();
        mortMap.put("1", sysDictDataAttList1);
        mortMap.put("2", sysDictDataAttList2);
        retMap.put("img_info", mortMap);// 图片
        return retMap;

    }



    /**
     * @Title: getContactHeadInfo
     * @Description: TODO(客户信息初始化)
     * @param userId
     * @param wms_cre_credit_head_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月21日 下午5:02:02
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getContactHeadInfo(java.lang.Integer, java.lang.Integer)
     * history:
     * 1、2017年3月21日 jiaodelong 创建方法
     */
    @Override
    public Map<String, Object> getContactHeadInfo(WmsCreCustomerChangeLineContactBeanVo vo)
    {
        return wmsCreCreditLineCustomerChangeHeadDao.getContactHeadInfo(vo);
    }



    /**
     * @Title: getRelationList
     * @Description: TODO(查询字典表)
     * @param i
     * @return 
     * @author: jiaodelong
     * @time:2017年3月22日 上午9:16:26
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getRelationList(int)
     * history:
     * 1、2017年3月22日 jiaodelong 创建方法
     */
    @Override
    public List<Map<String, Object>> getRelationList(Integer i)
    {
        return wmssysdictdataDao.getAllForWmsSysDictId(i);
    }



    /**
     * @Title: getBizProgressNumber
     * @Description: TODO(3.2.5     进度查询提示数据)
     * @param user_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月22日 上午9:55:53
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizProgressNumber(java.lang.Integer)
     * history:
     * 1、2017年3月22日 jiaodelong 创建方法
     */
    @Override
    public Integer getBizProgressNumber(Integer user_id)
    {
        return wmsCreCreditHeadDao.getBizProgressNumber(user_id);
    }

    
    /**
     * 
     * @Title: getBizMyResults
     * @Description: TODO(获取我的业绩统计信息)
     * @param personnel
     * @return 
     * @author: handongchun
     * @time:2017年3月21日 上午11:35:51
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizMyResults(com.zx.moa.util.gen.entity.wms.PmPersonnel)
     * history:
     * 1、2017年3月21日 handongchun 创建方法
     */
    @Override
    public Map<String, Object> getBizMyResults(PmPersonnel personnel)
    {
        String salesman_shortcode = personnel.getPersonnel_shortcode();
        List<Map<String,Object>> bizMyResults = wmscrecreditnotarywarnDao.getBizMyResults(salesman_shortcode);
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("resultsList", bizMyResults);
        return retMap;
    }
    
    /**
     * 
     * @Title: getBizBillDetailed
     * @Description: TODO(获取客户业绩详单信息)
     * @param paramsMap
     * @return 
     * @author: handongchun
     * @time:2017年3月21日 下午3:06:41
     * history:
     * 1、2017年3月21日 handongchun 创建方法
     */
    @Override
    public Map<String, Object> getBizBillDetailed(PmPersonnel personnel,WmsCreCreditNotaryWarnSearchBeanVO vo)
    {   
        Map<String,Object> paramsMap = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();
        String customer_name = vo.getCustomer_name();
        if(customer_name!=null&&!"".equals(customer_name)){
            paramsMap.put("customer_name", com.zx.moa.util.SysTools.getSqlLikeParam(vo.getCustomer_name()));
        }
        Integer is_need_paging = vo.getIs_need_paging();
        if(is_need_paging!=null&&is_need_paging==1){
            paramsMap.put("pagesize", vo.getPagesize());
            paramsMap.put("offset", vo.getOffset());
        }
        
        String salesman_shortcode = personnel.getPersonnel_shortcode();
        //测试 101676
        paramsMap.put("salesman_shortcode", salesman_shortcode);
        paramsMap.put("month_num", vo.getMonth_num());
        List<Map<String,Object>> noticeList  = wmscrecreditnotarywarnDao.getBizBillDetailed(paramsMap);
        retMap.put("noticeList", noticeList);
        return retMap;
    }
    
    /**
     * 
     * @Title: getBizTeamRanking
     * @Description: TODO(获取成员排名信息)
     * @param personnel
     * @param vo
     * @return 
     * @author: handongchun
     * @throws Exception 
     * @time:2017年3月21日 下午4:58:11
     * history:
     * 1、2017年3月21日 handongchun 创建方法
     */
    @Override
    public Map<String, Object> getBizTeamRanking(PmPersonnel personnel,BizTeamRankingBeanVO vo) throws Exception
    {
        Map<String,Object> paramsMap = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();
        String date_info = vo.getDate_info();
        //判断日期格式
        String date_info_regex ="^20(\\d{2})-(\\d{2})$";
        java.util.regex.Pattern pattern =java.util.regex.Pattern.compile(date_info_regex);
        java.util.regex.Matcher matcher = pattern.matcher(date_info);
        if(date_info==null||!matcher.matches()){
            throw new Exception("日期格式不正确");
            //paramsMap.put("date_info", com.zx.moa.util.DateUtil.date2String(new java.util.Date(),"YYYY-MM"));
        }
        paramsMap.put("date_info", date_info);
        //判断范围 1：团队 2：门店 3：公司
        String is_range = vo.getIs_range();
        paramsMap.put("is_range", is_range);
        String dept_info = null;
        if(!"3".equals(is_range)){
            dept_info = vo.getDept_info();
            String dept_info_regex = null;
            if("2".equals(is_range)){
                dept_info_regex  ="^DPH([A-Z]+)YY([A-Z]{1,2})BXX(\\d{3,})$";
            } 
            if("1".equals(is_range)){
                dept_info_regex ="^DPHYW([A-Z]+)(B|Z)XXXX(\\d{3,})$";
            }
            java.util.regex.Pattern dept_info_pattern =java.util.regex.Pattern.compile(dept_info_regex);
            java.util.regex.Matcher dept_info_matcher = dept_info_pattern.matcher(dept_info);
            if(!dept_info_matcher.matches()){
                 throw new Exception("部门编码与范围不匹配");
            }
            paramsMap.put("dept_info", dept_info);
        }
        //权限
        else
        {
            WmsCreCreditHeadFourSearchBeanVO queryInfo = new WmsCreCreditHeadFourSearchBeanVO();
            queryInfo.setMenu_code(WmsHelp.MENU_CODE_GSZL);
            Map<String,Object> dataAuthority = this.getBizDataAuthority(queryInfo, personnel);
            paramsMap.put("dataAuthority",dataAuthority.get("dataAuthority"));
        }
        //前三项
        String is_three_show = vo.getIs_three_show();
        if("1".equals(is_three_show)){
            paramsMap.put("pagesize", 3);
            paramsMap.put("offset", 0);
            paramsMap.put("x", 0);
        }else{
            paramsMap.put("pagesize", vo.getPagesize());
            paramsMap.put("offset", vo.getOffset());
            //设置排名起始位置 
            if(vo.getPage() <= 1){
                paramsMap.put("x", 0);
            }else{
                paramsMap.put("x", vo.getOffset());
            }
        }
        String is_type = vo.getIs_type();
      
        switch (is_type){
            //全部
            case "1":
                retMap.put("amount_ranking", pmPersonnelDao.getBizTeamAmountRanking(paramsMap));
                retMap.put("number_ranking", pmPersonnelDao.getBizTeamNumberRanking(paramsMap));
                break;
            //金额
            case "2":
                retMap.put("amount_ranking", pmPersonnelDao.getBizTeamAmountRanking(paramsMap));
                retMap.put("number_ranking", null);
                break;
            //件数
            case "3":
                retMap.put("number_ranking", pmPersonnelDao.getBizTeamNumberRanking(paramsMap));
                retMap.put("amount_ranking", null);
                break;
        }
        Map<String,Object> billing_number = pmPersonnelDao.calBillingNumber(paramsMap);
        retMap.put("billing_number", billing_number.get("billing_number"));
        retMap.put("billing_number_total", billing_number.get("billing_number_total"));
        return retMap;
    }


    /**
     * 
     * @Title: getBizProgressStatistical
     * @Description: TODO(获取团队、门店、公司单据统计信息)
     * @param personnel
     * @param date_info
     * @param dept_info
     * @param is_type
     * @return
     * @throws Exception 
     * @author: handongchun
     * @time:2017年3月22日 上午11:50:59
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizProgressStatistical(com.zx.moa.util.gen.entity.ioa.PmPersonnel, java.lang.String, java.lang.String, java.lang.String)
     * history:
     * 1、2017年3月22日 handongchun 创建方法
     */
    @Override
    public Map<String, Object> getBizProgressStatistical(PmPersonnel personnel, String date_info, String dept_info, String is_type) throws Exception
    {
        
        Map<String,Object> paramsMap = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();
        //判断日期格式
        String date_info_regex ="^20(\\d{2})-(\\d{2})$";
        java.util.regex.Pattern pattern =java.util.regex.Pattern.compile(date_info_regex);
        java.util.regex.Matcher matcher = pattern.matcher(date_info);
        if("".equals(date_info)||!matcher.matches()){
            throw new Exception("日期格式不正确");
            //paramsMap.put("date_info", com.zx.moa.util.DateUtil.date2String(new java.util.Date(),"YYYY-MM"));
        }
        paramsMap.put("date_info", date_info);
        paramsMap.put("is_type", is_type);
        if(!"3".equals(is_type)){
            if("".equals(dept_info)){
                throw new Exception("部门编码不能为空");
            }
            String dept_info_regex = null;
            if("2".equals(is_type)){
                dept_info_regex  ="^DPH([A-Z]+)YY([A-Z]{1,2})BXX(\\d{3,})$";
            } 
            if("1".equals(is_type)){
                dept_info_regex ="^DPHYW([A-Z]+)(B|Z)XXXX(\\d{3,})$";
            }
            java.util.regex.Pattern dept_info_pattern =java.util.regex.Pattern.compile(dept_info_regex);
            java.util.regex.Matcher dept_info_matcher = dept_info_pattern.matcher(dept_info);
            if(!dept_info_matcher.matches()){
                 throw new Exception("部门编码与范围不匹配");
            }
            paramsMap.put("dept_info", dept_info);
        }
        //权限控制
        else
        {
            WmsCreCreditHeadFourSearchBeanVO queryInfo = new WmsCreCreditHeadFourSearchBeanVO();
            queryInfo.setMenu_code(WmsHelp.MENU_CODE_GSZL);
            Map<String,Object> dataAuthority = this.getBizDataAuthority(queryInfo, personnel);
            paramsMap.put("dataAuthority",dataAuthority.get("dataAuthority"));
        }
        retMap.put("progressList", wmsCreHousingApprovalInfoDao.getBizProgressStatistical(paramsMap));
        return retMap;
    }


    /**
     * 
     * @Title: getBizBillDetails
     * @Description: TODO(获取成员单据详细信息)
     * @param personnel
     * @param vo
     * @return 
     * @author: handongchun
     * @throws Exception 
     * @time:2017年3月22日 下午2:11:24
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizBillDetails(com.zx.moa.util.gen.entity.ioa.PmPersonnel, com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO)
     * history:
     * 1、2017年3月22日 handongchun 创建方法
     */
    @Override
    public Map<String, Object> getBizBillDetails(PmPersonnel personnel, WmsCreCreditHeadFourSearchBeanVO vo) throws Exception
    {
        Map<String,Object> paramsMap = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();
        String customer_name = vo.getCustomer_name();
        if(customer_name!=null&&!"".equals(customer_name)){
            paramsMap.put("customer_name", com.zx.moa.util.SysTools.getSqlLikeParam(vo.getCustomer_name()));
        }
        String is_need_paging = vo.getIs_need_paging();
        if(is_need_paging!=null&&"1".equals(is_need_paging)){
            paramsMap.put("pagesize", vo.getPagesize());
            paramsMap.put("offset", vo.getOffset());
        }
        //业务员姓名工号 侯琳琳201041
        String salesman_name = vo.getSalesman_name();
        
        paramsMap.put("salesman_name", salesman_name);
        String date_info = vo.getDate_info();
        //判断日期格式
        String date_info_regex ="^20(\\d{2})-(\\d{2})$";
        java.util.regex.Pattern pattern1 =java.util.regex.Pattern.compile(date_info_regex);
        java.util.regex.Matcher matcher1 = pattern1.matcher(date_info);
        
        String date_info_day_regex ="^20(\\d{2})-(\\d{2})-(\\d{2})$";
        java.util.regex.Pattern pattern2 =java.util.regex.Pattern.compile(date_info_day_regex);
        java.util.regex.Matcher matcher2 = pattern2.matcher(date_info);
        
        if(matcher1.matches()){
            paramsMap.put("has_day", 0);
        }else if(matcher2.matches()){
            paramsMap.put("has_day", 1);
        }else{
            throw new Exception("日期格式不正确");
        }
        paramsMap.put("date_info", date_info);
        paramsMap.put("status_info", vo.getStatus_info());
        List<Map<String,Object>> progressList  = wmsCreCreditHeadDao.getBizBillDetails(paramsMap);
        retMap.put("progressList", progressList);
        retMap.put("salesman_name", salesman_name);
        return retMap;
    }


    /**
     * 
     * @Title: getBizStoreRanking
     * @Description: TODO(获取团队、门店排名信息)
     * @param personnel
     * @param vo
     * @return
     * @throws Exception 
     * @author: handongchun
     * @time:2017年3月22日 下午4:08:20
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizStoreRanking(com.zx.moa.util.gen.entity.ioa.PmPersonnel, com.zx.moa.wms.loan.vo.WmsCreCreditNotaryWarnSearchBeanVO)
     * history:
     * 1、2017年3月22日 handongchun 创建方法
     */
    @Override
    public Map<String, Object> getBizStoreRanking(PmPersonnel personnel, WmsCreCreditNotaryWarnSearchBeanVO vo) throws Exception
    {
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();
        String date_info = vo.getDate_info();
        // 判断日期格式
        String date_info_regex = "^20(\\d{2})-(\\d{2})$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(date_info_regex);
        java.util.regex.Matcher matcher = pattern.matcher(date_info);
        if (matcher.matches())
        {
            paramsMap.put("date_info", date_info);
        }
        else
        {
            throw new Exception("日期格式不正确");
        }
        
        String menu_code = vo.getMenu_code();
        if (WmsHelp.MENU_CODE_MDGL.equals(menu_code) || WmsHelp.MENU_CODE_GSZL.equals(menu_code))
        {
            paramsMap.put("menu_code", vo.getMenu_code());
            if(WmsHelp.MENU_CODE_GSZL.equals(menu_code))
            {
                //权限控制
                WmsCreCreditHeadFourSearchBeanVO queryInfo = new WmsCreCreditHeadFourSearchBeanVO();
                queryInfo.setMenu_code(vo.getMenu_code());
                Map<String,Object> dataAuthority = this.getBizDataAuthority(queryInfo, personnel);
                paramsMap.put("dataAuthority",dataAuthority.get("dataAuthority"));
            }
        }
        else
        {
            throw new Exception("菜单编码不正确");
        }
        String is_range = vo.getIs_range();
        
        if (WmsHelp.MENU_CODE_MDGL.equals(menu_code) && "2".equals(is_range))
        {
            throw new Exception("菜单编码与标识同级，无法获取数据");
        }
        if("1".equals(is_range) || "2".equals(is_range))
        {
            paramsMap.put("is_range", is_range);
        }
        else
        {
            throw new Exception("无此标识");
        }
        
        if ("1".equals(is_range) && WmsHelp.MENU_CODE_MDGL.equals(menu_code))
        {
            String dept_info = vo.getDept_info();
            if(dept_info == null||"".equals(dept_info))
            {
                throw new Exception("门店下团队排名，部门编码不能为空");
            }
            String dept_info_regex  = "^DPH([A-Z]+)YY([A-Z]{1,2})BXX(\\d{3,})$";
            java.util.regex.Pattern dept_info_pattern =java.util.regex.Pattern.compile(dept_info_regex);
            java.util.regex.Matcher dept_info_matcher = dept_info_pattern.matcher(dept_info);
            if(!dept_info_matcher.matches()){
                 throw new Exception("部门编码有误");
            }
            paramsMap.put("dept_info", dept_info);
        }
        
        // 前三项
        String is_three_show = vo.getIs_three_show();
        if ("1".equals(is_three_show))
        {
            paramsMap.put("pagesize", 3);
            paramsMap.put("offset", 0);
        }
        
        String is_type = vo.getIs_type();
        switch (is_type)
        {
            // 全部
            case "1":
                retMap.put("amount_ranking", wmscrecreditnotarywarnDao.getBizStoreAmountRanking(paramsMap));
                retMap.put("number_ranking", wmscrecreditnotarywarnDao.getBizStoreNumberRanking(paramsMap));
                break;
            // 金额
            case "2":
                retMap.put("amount_ranking", wmscrecreditnotarywarnDao.getBizStoreAmountRanking(paramsMap));
                retMap.put("number_ranking", null);
                break;
            // 件数
            case "3":
                retMap.put("number_ranking", wmscrecreditnotarywarnDao.getBizStoreNumberRanking(paramsMap));
                retMap.put("amount_ranking", null);
                break;
        }
        return retMap;
    }

    /**
     * @Title: getBizUploadCustomerInfo
     * @Description: 3.2.16 WMS_OUT_016 贷款申请草稿、补录初始化
     * @param queryInfo
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月22日 下午3:53:22
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizUploadCustomerInfo(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月22日 baisong 创建方法
     */
    @Override
    public Map<String, Object> getBizUploadCustomerInfo(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        Map<String, Object> resMap = new HashMap<String, Object>();

        paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
        resMap = wmsCreCreditHeadDao.dispMakeUpInfo(paramMap);
        // 客户年龄
        paramMap.put("wms_sys_dict_id", "130");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        List<Map<String, Object>> sysDictDataAgeList = wmssysdictdataDao.search(paramMap);
        // 照片张数
        paramMap.put("wms_sys_dict_id", "135");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        List<Map<String, Object>> sysDictDataNumList = wmssysdictdataDao.search(paramMap);
        if (sysDictDataNumList != null && sysDictDataNumList.size() > 0)
        {
            Map<String, Object> map = sysDictDataNumList.get(0);
            resMap.put("picture_num", map.get("value_code"));
        }
        paramMap.clear();
        // 抵押形式
        paramMap.put("wms_sys_dict_id", "146");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        paramMap.put("value_code_xiao", "100");
        List<Map<String, Object>> sysDictDataMortList = wmssysdictdataDao.search(paramMap);
        paramMap.clear();
        // 抵押形式
        paramMap.put("wms_sys_dict_id", "92");
        paramMap.put("sortname", "sort_order");
        paramMap.put("sortorder", "asc");
        paramMap.put("weighted_value", "%1%");// 获取权重值字段不为空的项
        List<Map<String, Object>> sysDictDataAttList1 = wmssysdictdataDao.search(paramMap);
        if (sysDictDataAttList1 != null && sysDictDataAttList1.size() > 0)
        {
            for (Map<String, Object> map : sysDictDataAttList1)
            {
                map.put("data_type_name", map.get("p_wms_sys_dict_data_id"));
                map.put("data_detail_name", map.get("wms_sys_dict_data_id"));
                map.put("img_name", map.get("value_meaning"));
                // 房产证需要特殊处理
                if (map.get("wms_sys_dict_data_id") != null && "846".equals(map.get("wms_sys_dict_data_id").toString()))
                {
                    map.put("img_name", "房产证");
                }
            }
        }
        paramMap.put("weighted_value", "%2%");// 获取权重值字段不为空的项
        List<Map<String, Object>> sysDictDataAttList2 = wmssysdictdataDao.search(paramMap);
        if (sysDictDataAttList2 != null && sysDictDataAttList2.size() > 0)
        {
            for (Map<String, Object> map : sysDictDataAttList2)
            {
                map.put("data_type_name", map.get("p_wms_sys_dict_data_id"));
                map.put("data_detail_name", map.get("wms_sys_dict_data_id"));
                map.put("img_name", map.get("value_meaning"));
                // 房产证需要特殊处理
                if (map.get("wms_sys_dict_data_id") != null && "846".equals(map.get("wms_sys_dict_data_id").toString()))
                {
                    map.put("img_name", "房产证");
                }
            }
        }
        // 城市
        if (resMap.get("city") == null)
        {
            resMap.put("city", personnel.getPersonnel_account());
        }
        resMap.put("ageList", sysDictDataAgeList);
        resMap.put("mort_flag_list", sysDictDataMortList);// 抵押形式
        Map<String, Object> mortMap = new HashMap<>();
        mortMap.put("1", sysDictDataAttList1);
        mortMap.put("2", sysDictDataAttList2);
        resMap.put("img_info", mortMap);// 图片
        return resMap;
    }

    /**
     * 
     * @Title: getBizDataAuthority
     * @Description: 获取数据权限
     * @param queryInfo
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月22日 下午4:13:27
     * history:
     * 1、2017年3月22日 baisong 创建方法
     * 
     * （1）如果是公司战略需要传递公司战略菜单编码（参数 WmsCreCreditHeadFourSearchBeanVO.menu_code） WmsHelp.MENU_CODE_GSZL 
     *      并且传递人员id（参数PmPersonnel.personnel_id ） 如果需要返回特定的部门等级则需要传递部门等级 参数（WmsCreCreditHeadFourSearchBeanVO.dept_level）
     *      如果没有传递部门等级则返回当前人所有有权限的部门id  如果传递了部门等级则返回当前人对应等级的部门id
     * （2）如果是首页则传递一个人员id（参数PmPersonnel.personnel_id） 返回当前人所有有权限的部门id
     */
    @Override
    public Map<String, Object> getBizDataAuthority(WmsCreCreditHeadFourSearchBeanVO queryInfo, PmPersonnel personnel)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        Map<String, Object> resMap = new HashMap<String, Object>();
        // 判断是否是公司战略
        if (WmsHelp.MENU_CODE_GSZL.equals(queryInfo.getMenu_code()))
        {
            paramMap.put("personnel_id", personnel.getPersonnel_id());// 人员id
            paramMap.put("menu_code", WmsHelp.MENU_CODE_GSZL);// 菜单code
            paramMap.put("dept_level", queryInfo.getDept_level());// 需要的部门等级
            String dataAuthority = pmPersonnelDao.getBizDataAuthority(paramMap);
            resMap.put("dataAuthority", dataAuthority);
            resMap.put("dept_level", queryInfo.getDept_level());
        }
        // 如果不是公司战略 那就是首页了
        else
        {
            String top_menu_code = "";
            String all_menu_code = "";
            List<Map<String, Object>> list = pmPersonnelDao.getSpecialMenuInfosMoa(personnel.getPersonnel_id());// 当前登陆人的菜单权限
            // 循环菜单list
            for (Map<String, Object> map : list)
            {
                // 判断公司战略菜单权限
                if (WmsHelp.MENU_CODE_GSZL.equals(map.get("menu_code").toString()) && "1".equals(map.get("menu_is_show").toString()))
                {
                    all_menu_code += "," + WmsHelp.MENU_CODE_GSZL;
                    top_menu_code = WmsHelp.MENU_CODE_GSZL;
                }
                // 判断门店管理菜单权限
                if (WmsHelp.MENU_CODE_MDGL.equals(map.get("menu_code").toString()) && "1".equals(map.get("menu_is_show").toString()))
                {
                    all_menu_code += "," + WmsHelp.MENU_CODE_MDGL;
                    // 如果不是公司战略
                    if (!WmsHelp.MENU_CODE_GSZL.equals(top_menu_code))
                    {
                        top_menu_code = WmsHelp.MENU_CODE_MDGL;
                    }
                }
                // 判断团队管理菜单权限
                if (WmsHelp.MENU_CODE_TDGL.equals(map.get("menu_code").toString()) && "1".equals(map.get("menu_is_show").toString()))
                {
                    all_menu_code += "," + WmsHelp.MENU_CODE_TDGL;
                    // 如果不是公司战略和门店
                    if (!WmsHelp.MENU_CODE_GSZL.equals(top_menu_code) && !WmsHelp.MENU_CODE_MDGL.equals(top_menu_code))
                    {
                        top_menu_code = WmsHelp.MENU_CODE_TDGL;
                    }
                }
            }
            if(StringUtils.isEmpty(top_menu_code)) {
                // 是否是业务员权限 1是0不是
                resMap.put("is_salesman", "1");
                return resMap;
            }
            resMap.put("is_salesman", "0");
            paramMap.put("personnel_id", personnel.getPersonnel_id());// 人员id
            // 去掉多余的，
            if (all_menu_code.indexOf(",") == 0)
            {
                all_menu_code = all_menu_code.substring(1, all_menu_code.length());
            }
            if (all_menu_code.lastIndexOf(",") == all_menu_code.length() - 1)
            {
                all_menu_code = all_menu_code.substring(0, all_menu_code.length() - 1);
            }
            paramMap.put("menu_code", all_menu_code);// 菜单code
            // paramMap.put("dept_level", queryInfo.getDept_level());// 需要的部门等级
            String dataAuthority = pmPersonnelDao.getBizDataAuthority(paramMap);
            resMap.put("dataAuthority", dataAuthority);
            resMap.put("menu_code", top_menu_code);
        }

        return resMap;
    }
    
    
    /**
     * 接口编号: WMS_OUT_007
     * @Title: getBizHomePagePerformance
     * @Description: 首页、个人、团队等业绩统计
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizHomePagePerformance(WmsCreCreditHeadSearchBeanVO queryInfo) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String now_month = format.format(new Date());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        PmPersonnel pmPersonnel = queryInfo.getPersonnel();
        WmsCreCreditHeadFourSearchBeanVO vo = new WmsCreCreditHeadFourSearchBeanVO();
        if(queryInfo.getIs_type().equals("3")) {
            vo.setMenu_code(WmsHelp.MENU_CODE_GSZL);
        }
        Map<String, Object> dataAuthority = this.getBizDataAuthority(vo, pmPersonnel);
        //is_type：0:个人 1:团队 2:门店 3:公司
        if(StringUtils.isNotEmpty(queryInfo.getIs_type())) {
            //首页业绩统计(个人)或是公司战略
            if(queryInfo.getIs_type().equals("0") || queryInfo.getIs_type().equals("3")) {
                //业务员
                if(dataAuthority.containsKey("is_salesman") 
                        && dataAuthority.get("is_salesman") != null
                        && dataAuthority.get("is_salesman").toString().equals("1")) {
                    paramMap.put("salesman_shortcode", pmPersonnel.getPersonnel_shortcode());
                    paramMap.put("salesman_id", pmPersonnel.getPersonnel_id());
                    paramMap.put("is_type", 1);
                } 
                //非业务员
                else {
                    if(dataAuthority.containsKey("dataAuthority") 
                            && dataAuthority.get("dataAuthority") != null
                            && !dataAuthority.get("dataAuthority").toString().equals("")) {
                        paramMap.put("dept_in", "(" + dataAuthority.get("dataAuthority") + ")");
                        //副总
                        if(queryInfo.getIs_type().equals("3")) {
                            paramMap.put("is_type", 4);
                        } 
                        if(dataAuthority.get("menu_code") != null) {
                            //副总
                            if (dataAuthority.get("menu_code").toString().equals(WmsHelp.MENU_CODE_GSZL))
                            {
                                paramMap.put("is_type", 4);
                            }
                            //门店经理
                            if (dataAuthority.get("menu_code").toString().equals(WmsHelp.MENU_CODE_MDGL))
                            {
                                paramMap.put("is_type", 3);
                            } 
                            //团队经理
                            if (dataAuthority.get("menu_code").toString().equals(WmsHelp.MENU_CODE_TDGL))
                            {
                                paramMap.put("is_type", 2);
                            }
                        }
                    }
                }
            }
            // 团队管理、门店管理
            else if(queryInfo.getIs_type().equals("1") || queryInfo.getIs_type().equals("2")) {
                if(StringUtils.isNotEmpty(queryInfo.getDept_info())) {
                    paramMap.put("dept_info", queryInfo.getDept_info());
                }
                //团队管理
                if(queryInfo.getIs_type().equals("1")) {
                    paramMap.put("is_type", 2);
                }
                //门店管理
                else if(queryInfo.getIs_type().equals("2")) {
                    paramMap.put("is_type", 3);
                }
            }
        }
        //月份
        if(StringUtils.isNotBlank(queryInfo.getDate_info())) {
            paramMap.put("date_info", queryInfo.getDate_info());
            paramMap.put("date_info_like", SysTools.getSqlLikeParam(queryInfo.getDate_info()));
        } else {
            paramMap.put("date_info", now_month);
            paramMap.put("date_info_like", SysTools.getSqlLikeParam(now_month));
        }
        
        Map<String, Object> map = this.wmsCreCreditHeadDao.getBizHomePagePerformance(paramMap);
        map.put("refused_num", "");// 拒件件数
        map.put("amount_stock", "");// 存量金额
        map.put("amount_team_stock", "");// 团队存量金额
        map.put("store_amount_stock", "");// 门店存量金额
        map.put("amount_annual_loan", "");// 年度放款金额
        map.put("company_amount_stock", "");// 公司存量金额
        queryInfo.setResMap(map);
        return queryInfo;
    }
    
    /**
     * 接口编号: WMS_OUT_019
     * @Title: getBizPerformanceRanking
     * @Description: 获取我的业绩排名信息
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizPerformanceRanking(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> rankingmap = new HashMap<String, Object>();
        //房贷排名map
        Map<String, Object> house_rangking = new HashMap<String, Object>();
        //信贷排名map
        Map<String, Object> credibilit_rangking = new HashMap<String, Object>();
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        PmPersonnel pmPersonnel = queryInfo.getPersonnel();
        //获取登录人部门id
        Integer dept_id = pmPersonnel.getPersonnel_deptid();
        //获取所在部门信息
        SysDept sysDept = this.sysDeptDao.get(dept_id);
        String dept_level_coding = sysDept.getDept_level_coding();
        //是产品管理部下面的人
        if(dept_level_coding.length() >= WmsHelp.TOP_DEPT_LEVEL_CODING.length() && 
                dept_level_coding.substring(0, WmsHelp.TOP_DEPT_LEVEL_CODING.length()).equals(WmsHelp.TOP_DEPT_LEVEL_CODING)) {
            //产品管理部级的人(普惠排名)
            if(dept_level_coding.equals(WmsHelp.TOP_DEPT_LEVEL_CODING)) {
                //查询房贷排名
                paramMap.put("dept_level_coding", dept_level_coding);
                paramMap.put("dept_level_coding_like", dept_level_coding + "%");
                paramMap.put("cre_type", "2");
                List<Map<String, Object>> map_ph = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                Integer c_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_ph);
                Integer c_total_number = map_ph.size();
                //排名占比（展示）
                house_rangking.put("rangking_show", 
                    new BigDecimal(1).subtract(
                    new BigDecimal(c_current_ranking).
                    divide(new BigDecimal(c_total_number), 2, BigDecimal.ROUND_HALF_UP)).
                    multiply(new BigDecimal(100)).toString());
                //排名占比：1 - 普惠当前排名 / 普惠总人数
                house_rangking.put("rangking_ratio", house_rangking.get("rangking_show").toString().replace(".00", "") + "%");
                
                //普惠当前排名
                house_rangking.put("c_current_ranking", c_current_ranking.toString());
                //普惠总人数
                house_rangking.put("c_total_number", c_total_number.toString());
                //公司人数占比
                house_rangking.put("c_rangking_ratio", 
                    new BigDecimal(c_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(c_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                //团队当前排名
                house_rangking.put("t_current_ranking", "-");
                //团队总人数
                house_rangking.put("t_total_number", "-");
                //团队人数占比
                house_rangking.put("t_rangking_ratio", "0");
                
                //门店当前排名
                house_rangking.put("s_current_ranking", "-");
                //门店总人数
                house_rangking.put("s_total_number", "-");
                //门店人数占比
                house_rangking.put("s_rangking_ratio", "0");
                
                //查询信贷排名
                paramMap.put("dept_level_coding", dept_level_coding);
                paramMap.put("dept_level_coding_like", dept_level_coding + "%");
                paramMap.put("cre_type", "1");
                map_ph = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                c_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_ph);
                c_total_number = map_ph.size();
                //排名占比（展示）
                credibilit_rangking.put("rangking_show", 
                    new BigDecimal(1).subtract(
                    new BigDecimal(c_current_ranking).
                    divide(new BigDecimal(c_total_number), 2, BigDecimal.ROUND_HALF_UP)).
                    multiply(new BigDecimal(100)).toString());
                //排名占比：1 - 普惠当前排名 / 普惠总人数
                credibilit_rangking.put("rangking_ratio", credibilit_rangking.get("rangking_show").toString().replace(".00", "") + "%");
                //普惠当前排名
                credibilit_rangking.put("c_current_ranking", c_current_ranking.toString());
                //普惠总人数
                credibilit_rangking.put("c_total_number", c_total_number.toString());
                //公司人数占比
                credibilit_rangking.put("c_rangking_ratio", 
                    new BigDecimal(c_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(c_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                //团队当前排名
                credibilit_rangking.put("t_current_ranking", "-");
                //团队总人数
                credibilit_rangking.put("t_total_number", "-");
                //团队人数占比
                credibilit_rangking.put("t_rangking_ratio", "0");
                
                //门店当前排名
                credibilit_rangking.put("s_current_ranking", "-");
                //门店总人数
                credibilit_rangking.put("s_total_number", "-");
                //门店人数占比
                credibilit_rangking.put("s_rangking_ratio", "0");
            } 
            //营业部级的人(门店排名、普惠排名)
            else if(dept_level_coding.length() == (WmsHelp.TOP_DEPT_LEVEL_CODING.length() + 3)) {
                //查询房贷排名
                paramMap.put("dept_level_coding", dept_level_coding.substring(0, dept_level_coding.length() - 3));
                paramMap.put("dept_level_coding_like", dept_level_coding.substring(0, dept_level_coding.length() - 3) + "%");
                paramMap.put("cre_type", "2");
                List<Map<String, Object>> map_ph = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                Integer c_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_ph);
                Integer c_total_number = map_ph.size();
                //排名占比（展示）
                house_rangking.put("rangking_show", 
                    new BigDecimal(1).subtract(
                    new BigDecimal(c_current_ranking).
                    divide(new BigDecimal(c_total_number), 2, BigDecimal.ROUND_HALF_UP)).
                    multiply(new BigDecimal(100)).toString());
                //排名占比：1 - 普惠当前排名 / 普惠总人数
                house_rangking.put("rangking_ratio", house_rangking.get("rangking_show").toString().replace(".00", "") + "%");
                //普惠当前排名
                house_rangking.put("c_current_ranking", c_current_ranking.toString());
                //普惠总人数
                house_rangking.put("c_total_number", c_total_number.toString());
                //公司人数占比
                house_rangking.put("c_rangking_ratio", 
                    new BigDecimal(c_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(c_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                paramMap.put("dept_level_coding", dept_level_coding);
                paramMap.put("dept_level_coding_like", dept_level_coding + "%");
                List<Map<String, Object>> map_md = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                Integer s_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_md);
                Integer s_total_number = map_md.size();
                //门店当前排名
                house_rangking.put("s_current_ranking", s_current_ranking.toString());
                //门店总人数
                house_rangking.put("s_total_number", s_total_number.toString());
                //门店人数占比
                house_rangking.put("s_rangking_ratio", 
                    new BigDecimal(s_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(s_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                //团队当前排名
                house_rangking.put("t_current_ranking", "-");
                //团队总人数
                house_rangking.put("t_total_number", "-");
                //团队人数占比
                house_rangking.put("t_rangking_ratio", "0");
                
                //查询信贷排名
                paramMap.put("dept_level_coding", dept_level_coding.substring(0, dept_level_coding.length() - 3));
                paramMap.put("dept_level_coding_like", dept_level_coding.substring(0, dept_level_coding.length() - 3) + "%");
                paramMap.put("cre_type", "1");
                map_ph = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                c_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_ph);
                c_total_number = map_ph.size();
                //排名占比（展示）
                credibilit_rangking.put("rangking_show", 
                    new BigDecimal(1).subtract(
                    new BigDecimal(c_current_ranking).
                    divide(new BigDecimal(c_total_number), 2, BigDecimal.ROUND_HALF_UP)).
                    multiply(new BigDecimal(100)).toString());
                //排名占比：1 - 普惠当前排名 / 普惠总人数
                credibilit_rangking.put("rangking_ratio", credibilit_rangking.get("rangking_show").toString().replace(".00", "") + "%");
                //普惠当前排名
                credibilit_rangking.put("c_current_ranking", c_current_ranking.toString());
                //普惠总人数
                credibilit_rangking.put("c_total_number", c_total_number.toString());
                //公司人数占比
                credibilit_rangking.put("c_rangking_ratio", 
                    new BigDecimal(c_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(c_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                paramMap.put("dept_level_coding", dept_level_coding);
                paramMap.put("dept_level_coding_like", dept_level_coding + "%");
                map_md = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                s_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_md);
                s_total_number = map_md.size();
                //门店当前排名
                credibilit_rangking.put("s_current_ranking", s_current_ranking.toString());
                //门店总人数
                credibilit_rangking.put("s_total_number", s_total_number.toString());
                //门店人数占比
                credibilit_rangking.put("s_rangking_ratio", 
                    new BigDecimal(s_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(s_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                //团队当前排名
                credibilit_rangking.put("t_current_ranking", "-");
                //团队总人数
                credibilit_rangking.put("t_total_number", "-");
                //团队人数占比
                credibilit_rangking.put("t_rangking_ratio", "0");
            }
            //团队级的人(团队排名、门店排名、普惠排名)
            else {
                //查询房贷排名
                paramMap.put("dept_level_coding", dept_level_coding.substring(0, dept_level_coding.length() - 6));
                paramMap.put("dept_level_coding_like", dept_level_coding.substring(0, dept_level_coding.length() - 6) + "%");
                paramMap.put("cre_type", "2");
                List<Map<String, Object>> map_ph = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                Integer c_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_ph);
                Integer c_total_number = map_ph.size();
                //排名占比（展示）
                house_rangking.put("rangking_show", 
                    new BigDecimal(1).subtract(
                    new BigDecimal(c_current_ranking).
                    divide(new BigDecimal(c_total_number), 2, BigDecimal.ROUND_HALF_UP)).
                    multiply(new BigDecimal(100)).toString());
                //排名占比：1 - 普惠当前排名 / 普惠总人数
                house_rangking.put("rangking_ratio", house_rangking.get("rangking_show").toString().replace(".00", "") + "%");
                //普惠当前排名
                house_rangking.put("c_current_ranking", c_current_ranking.toString());
                //普惠总人数
                house_rangking.put("c_total_number", c_total_number.toString());
                //公司人数占比
                house_rangking.put("c_rangking_ratio", 
                    new BigDecimal(c_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(c_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                paramMap.put("dept_level_coding", dept_level_coding.substring(0, dept_level_coding.length() - 3));
                paramMap.put("dept_level_coding_like", dept_level_coding.substring(0, dept_level_coding.length() - 3) + "%");
                List<Map<String, Object>> map_md = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                Integer s_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_md);
                Integer s_total_number = map_md.size();
                //门店当前排名
                house_rangking.put("s_current_ranking", s_current_ranking.toString());
                //门店总人数
                house_rangking.put("s_total_number", s_total_number.toString());
                //门店人数占比
                house_rangking.put("s_rangking_ratio", 
                    new BigDecimal(s_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(s_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                paramMap.put("dept_level_coding", dept_level_coding);
                paramMap.put("dept_level_coding_like", dept_level_coding + "%");
                List<Map<String, Object>> map_td = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                Integer t_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_td);
                Integer t_total_number = map_td.size();
                
                //团队当前排名
                house_rangking.put("t_current_ranking", t_current_ranking.toString());
                //团队总人数
                house_rangking.put("t_total_number", t_total_number.toString());
                //团队人数占比
                if(t_total_number != 1) {
                    house_rangking.put("t_rangking_ratio", 
                       new BigDecimal(t_current_ranking).
                       subtract(new BigDecimal(1)).
                       divide(new BigDecimal(t_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                       multiply(new BigDecimal(100)).toString());
                } else {
                    house_rangking.put("t_rangking_ratio", "0");
                }
                
                
                //查询信贷排名
                paramMap.put("dept_level_coding", dept_level_coding.substring(0, dept_level_coding.length() - 6));
                paramMap.put("dept_level_coding_like", dept_level_coding.substring(0, dept_level_coding.length() - 6) + "%");
                paramMap.put("cre_type", "1");
                map_ph = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                c_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_ph);
                c_total_number = map_ph.size();
                //排名占比（展示）
                credibilit_rangking.put("rangking_show", 
                    new BigDecimal(1).subtract(
                    new BigDecimal(c_current_ranking).
                    divide(new BigDecimal(c_total_number), 2, BigDecimal.ROUND_HALF_UP)).
                    multiply(new BigDecimal(100)).toString());
                //排名占比：1 - 普惠当前排名 / 普惠总人数
                credibilit_rangking.put("rangking_ratio", credibilit_rangking.get("rangking_show").toString().replace(".00", "") + "%");
                //普惠当前排名
                credibilit_rangking.put("c_current_ranking", c_current_ranking.toString());
                //普惠总人数
                credibilit_rangking.put("c_total_number", c_total_number.toString());
                //公司人数占比
                credibilit_rangking.put("c_rangking_ratio", 
                    new BigDecimal(c_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(c_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                paramMap.put("dept_level_coding", dept_level_coding.substring(0, dept_level_coding.length() - 3));
                paramMap.put("dept_level_coding_like", dept_level_coding.substring(0, dept_level_coding.length() - 3) + "%");
                map_md = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                s_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_md);
                s_total_number = map_md.size();
                //门店当前排名
                credibilit_rangking.put("s_current_ranking", s_current_ranking.toString());
                //门店总人数
                credibilit_rangking.put("s_total_number", s_total_number.toString());
                //门店人数占比
                credibilit_rangking.put("s_rangking_ratio", 
                    new BigDecimal(s_current_ranking).
                    subtract(new BigDecimal(1)).
                    divide(new BigDecimal(s_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).toString());
                
                paramMap.put("dept_level_coding", dept_level_coding);
                paramMap.put("dept_level_coding_like", dept_level_coding + "%");
                map_td = this.wmsCreCreditHeadDao.getBizPerformanceRanking(paramMap);
                t_current_ranking = getRankingInList(pmPersonnel.getPersonnel_id(), map_td);
                t_total_number = map_td.size();
                
                //团队当前排名
                credibilit_rangking.put("t_current_ranking", t_current_ranking.toString());
                //团队总人数
                credibilit_rangking.put("t_total_number", t_total_number.toString());
                //团队人数占比
                if(t_total_number != 1) {
                    credibilit_rangking.put("t_rangking_ratio", 
                        new BigDecimal(t_current_ranking).
                        subtract(new BigDecimal(1)).
                        divide(new BigDecimal(t_total_number).subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP).
                        multiply(new BigDecimal(100)).toString());
                    
                } else {
                    credibilit_rangking.put("t_rangking_ratio", "0");
                }
            }
        } 
        //产品管理部以外的人
        else {
            //房贷
            //排名占比（展示）
            house_rangking.put("rangking_show", "0");
            //排名占比
            house_rangking.put("rangking_ratio", "-%");
            
            //团队当前排名
            house_rangking.put("t_current_ranking", "-");
            //团队总人数
            house_rangking.put("t_total_number", "-");
            //团队人数占比
            house_rangking.put("t_rangking_ratio", "0");
            
            //门店当前排名
            house_rangking.put("s_current_ranking", "-");
            //门店总人数
            house_rangking.put("s_total_number", "-");
            //门店人数占比
            house_rangking.put("s_rangking_ratio", "0");
            
            //普惠当前排名
            house_rangking.put("c_current_ranking", "-");
            //普惠总人数
            house_rangking.put("c_total_number", "-");
            //普惠人数占比
            house_rangking.put("c_rangking_ratio", "0");
            
            //信贷
            //排名占比（展示）
            credibilit_rangking.put("rangking_show", "0");
            //排名占比
            credibilit_rangking.put("rangking_ratio", "-%");
            
            //团队当前排名
            credibilit_rangking.put("t_current_ranking", "-");
            //团队总人数
            credibilit_rangking.put("t_total_number", "-");
            //团队人数占比
            credibilit_rangking.put("t_rangking_ratio", "0");
            
            //门店当前排名
            credibilit_rangking.put("s_current_ranking", "-");
            //门店总人数
            credibilit_rangking.put("s_total_number", "-");
            //门店人数占比
            credibilit_rangking.put("s_rangking_ratio", "0");
            
            //普惠当前排名
            credibilit_rangking.put("c_current_ranking", "-");
            //普惠总人数
            credibilit_rangking.put("c_total_number", "-");
            //普惠人数占比
            credibilit_rangking.put("c_rangking_ratio", "0");
        }
        rankingmap.put("house_rangking", house_rangking);
        rankingmap.put("credibilit_rangking", credibilit_rangking);
        queryInfo.setResMap(rankingmap);
        return queryInfo;
    }
    
    //获取登录人在业绩中的排名
    private static Integer getRankingInList(Integer personnel_id, List<Map<String, Object>> map) {
        for(Map<String, Object> tmpMap : map) {
            if(tmpMap.get("personnel_id").toString().equals(personnel_id.toString())) {
                //如果上单金额为0 则排最后一名
                if(!tmpMap.get("total_appro_limit").toString().equals("0.0")) {
                    return ((Long) tmpMap.get("num")).intValue();
                } else {
                    return map.size();
                }
            }
        }
        return null;
    }
    
    /**
     * 接口编号: WMS_OUT_022
     * @Title: getBizResultsStatistical
     * @Description: 获取月份业绩统计信息（我的业绩、团队、门店、公司）
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizResultsStatistical(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String title_name = "";
        //0：个人 1：部门 2：门店 3：公司
        if(StringUtils.isNotBlank(queryInfo.getIs_type())) {
            //0：个人 
            if("0".equals(queryInfo.getIs_type())) {
                paramMap.put("personnel_id", queryInfo.getPersonnel().getPersonnel_id());
                title_name = "我的业绩详情";
            }
            //1：部门 2：门店 3：公司
            else {
                if("1".equals(queryInfo.getIs_type()) || "2".equals(queryInfo.getIs_type())) {
                    SysDept sysDept = new SysDept();
                    sysDept.setDept_code(queryInfo.getDept_info());
                    List<SysDept> sysDeptList = this.sysDeptDao.getListByEntity(sysDept);
                    title_name = sysDeptList.get(0).getDept_name() + "业绩统计";
                    //部门编码
                    if(StringUtils.isNotBlank(queryInfo.getDept_info())) {
                        paramMap.put("dept_info", queryInfo.getDept_info());
                    }
                } else if("3".equals(queryInfo.getIs_type())) {
                    WmsCreCreditHeadFourSearchBeanVO vo = new WmsCreCreditHeadFourSearchBeanVO();
                    PmPersonnel pmPersonnel = queryInfo.getPersonnel();
                    vo.setMenu_code(WmsHelp.MENU_CODE_GSZL);
                    Map<String, Object> dataAuthority = this.getBizDataAuthority(vo, pmPersonnel);
                    if(dataAuthority.containsKey("dataAuthority") 
                            && dataAuthority.get("dataAuthority") != null
                            && !dataAuthority.get("dataAuthority").toString().equals("")) {
                        paramMap.put("dept_in", "(" + dataAuthority.get("dataAuthority") + ")");
                    } else {
                        paramMap.put("dept_info", WmsHelp.TOP_DEPT_CODE);
                    }
                    title_name = "业绩统计";
                }
            }
            
        }
        
        List<Map<String, Object>> list = this.wmsCreCreditHeadDao.getBizResultsStatisticalByMonth(paramMap);
        List<Map<String, Object>> list1 = this.wmsCreCreditHeadDao.getBizResultsStatisticalByYear(paramMap);
        
        //聚合数据：将月份对应添加到年份的list里
        for(Map<String, Object> tmpMap1 : list1) {
            for(Map<String, Object> tmpMap : list) {
                if(tmpMap.get("month_num").toString().contains(tmpMap1.get("year_num").toString())) {
                    List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
                    if(tmpMap1.containsKey("monthList")) {
                        tmpList = (List<Map<String, Object>>) tmpMap1.get("monthList"); 
                    }
                    tmpList.add(tmpMap);
                    tmpMap1.put("monthList", tmpList);
                }
            }
        }
        
        //添加年
        for(Map<String, Object> tmpMap1 : list1) {
            tmpMap1.put("year_num", tmpMap1.get("year_num").toString() + "年");
        }
        
        queryInfo.setTitle_name(title_name);
        queryInfo.setList(list1);
        
        return queryInfo;
    }
    
    /**
     * 接口编号: WMS_OUT_026
     * @Title: getBizMonthMembers
     * @Description: 获取团队X月成员业绩统计信息
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizMonthMembers(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //月份
        if(StringUtils.isNotBlank(queryInfo.getDate_info())) {
            paramMap.put("date_info", queryInfo.getDate_info());
            paramMap.put("date_info_like", SysTools.getSqlLikeParam(queryInfo.getDate_info()));
        }
        //部门编码
        if(StringUtils.isNotBlank(queryInfo.getDept_info())) {
            paramMap.put("dept_info", queryInfo.getDept_info());
        }
        //模糊查询员工姓名与工号
        if(StringUtils.isNotBlank(queryInfo.getSalesman_info())) {
            paramMap.put("salesman_info", SysTools.getSqlLikeParam(queryInfo.getSalesman_info()));
        }
        if(queryInfo.getIs_need_paging() != null) {
            if(queryInfo.getIs_need_paging() == 1) {
                paramMap.put("pagesize", queryInfo.getPagesize());
                paramMap.put("offset", queryInfo.getOffset());
            } else {
                paramMap.put("pagesize", null);
                paramMap.put("offset", null);
            }
        } else {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        }
        List<Map<String, Object>> list = this.wmsCreCreditHeadDao.getBizMonthMembers(paramMap);
        queryInfo.setList(list);
        return queryInfo;
    }
    
    /**
     * 接口编号: WMS_OUT_028
     * @Title: getBizMembersProgressStatistical
     * @Description: 获取成员单据统计信息
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizMembersProgressStatistical(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //月份
        if(StringUtils.isNotBlank(queryInfo.getDate_info())) {
            paramMap.put("date_info", queryInfo.getDate_info());
            paramMap.put("date_info_like", SysTools.getSqlLikeParam(queryInfo.getDate_info()));
        }
        //部门编码
        if(StringUtils.isNotBlank(queryInfo.getDept_info())) {
            paramMap.put("dept_info", queryInfo.getDept_info());
        }
        //模糊查询员工姓名与工号
        if(StringUtils.isNotBlank(queryInfo.getFilter_employees())) {
            paramMap.put("filter_employees", SysTools.getSqlLikeParam(queryInfo.getFilter_employees()));
        }
        if(queryInfo.getIs_need_paging() != null) {
            if(queryInfo.getIs_need_paging() == 1) {
                paramMap.put("pagesize", queryInfo.getPagesize());
                paramMap.put("offset", queryInfo.getOffset());
            } else {
                paramMap.put("pagesize", null);
                paramMap.put("offset", null);
            }
        } else {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        }
        List<Map<String, Object>> list = this.wmsCreCreditHeadDao.getBizMembersProgressStatistical(paramMap);
        queryInfo.setList(list);
        return queryInfo;
    }
    
    /**
     * 接口编号: WMS_OUT_031
     * @Title: getBizStoreResultsStatistics
     * @Description: 获取团队、门店X月业绩统计信息
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizStoreResultsStatistics(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Integer dept_level = 0;
        Integer dept_id = 0;
        SysDept sysDept = new SysDept();
        if(StringUtils.isNotEmpty(queryInfo.getDept_info())) {
            sysDept.setDept_code(queryInfo.getDept_info());
            List<SysDept> sysDeptList = this.sysDeptDao.getListByEntity(sysDept);
            sysDept = sysDeptList.get(0);
            dept_level = sysDept.getDept_level();
            dept_id = sysDept.getDept_id();
        }
        
        //查询标识 1：门店 2：公司
        if(StringUtils.isNotBlank(queryInfo.getIs_type())) {
            paramMap.put("is_type", queryInfo.getIs_type());
            //统计公司时部门编码写死：产品管理部（营业部的上一级）
            if("2".equals(queryInfo.getIs_type())) {
                paramMap.put("dept_info", WmsHelp.TOP_DEPT_CODE);
                //统计公司时查全部
                if(dept_level == 0) {
                    WmsCreCreditHeadFourSearchBeanVO vo = new WmsCreCreditHeadFourSearchBeanVO();
                    PmPersonnel pmPersonnel = queryInfo.getPersonnel();
                    vo.setMenu_code(WmsHelp.MENU_CODE_GSZL);
                    Map<String, Object> dataAuthority = this.getBizDataAuthority(vo, pmPersonnel);
                    if(dataAuthority.containsKey("dataAuthority") 
                            && dataAuthority.get("dataAuthority") != null
                            && !dataAuthority.get("dataAuthority").toString().equals("")) {
                        paramMap.put("dept_in", "(" + dataAuthority.get("dataAuthority") + ")");
                    }
                }
                //统计公司时查单个营业部
                else if(dept_level == 5) {
                    paramMap.put("dept_in", "(" + dept_id + ")");
                }
            } else {
                //统计门店时查全部
                if(dept_level == 5) {
                    paramMap.put("dept_info", queryInfo.getDept_info());
                }
                //统计门店时查单个团队
                else if(dept_level == 6) {
                    sysDept = sysDeptDao.get(sysDept.getDept_pid());
                    paramMap.put("dept_info", sysDept.getDept_code());
                    paramMap.put("dept_in", "(" + dept_id + ")");
                }
            }
        }
        //月份
        if(StringUtils.isNotBlank(queryInfo.getDate_info())) {
            paramMap.put("date_info", queryInfo.getDate_info());
            paramMap.put("date_info_like", SysTools.getSqlLikeParam(queryInfo.getDate_info()));
        }
        if(queryInfo.getIs_need_paging() != null) {
            if(queryInfo.getIs_need_paging() == 1) {
                paramMap.put("pagesize", queryInfo.getPagesize());
                paramMap.put("offset", queryInfo.getOffset());
            } else {
                paramMap.put("pagesize", null);
                paramMap.put("offset", null);
            }
        } else {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        }
        List<Map<String, Object>> list = this.wmsCreCreditHeadDao.getBizStoreResultsStatistics(paramMap);
        queryInfo.setList(list);
        return queryInfo;
    }
    
    /**
     * 接口编号: WMS_OUT_032
     * @Title: getBizCompanyProgressStatistical
     * @Description: 获取单据统计信息
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    public WmsCreCreditHeadSearchBeanVO getBizCompanyProgressStatistical(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Integer dept_level = 0;
        Integer dept_id = 0;
        SysDept sysDept = new SysDept();
        if(StringUtils.isNotEmpty(queryInfo.getDept_info())) {
            sysDept.setDept_code(queryInfo.getDept_info());
            List<SysDept> sysDeptList = this.sysDeptDao.getListByEntity(sysDept);
            sysDept = sysDeptList.get(0);
            dept_level = sysDept.getDept_level();
            dept_id = sysDept.getDept_id();
        }
        
        //查询标识 1：门店 2：公司
        if(StringUtils.isNotBlank(queryInfo.getIs_type())) {
            paramMap.put("is_type", queryInfo.getIs_type());
            //统计公司时部门编码写死：产品管理部（营业部的上一级）
            if("2".equals(queryInfo.getIs_type())) {
                paramMap.put("dept_info", WmsHelp.TOP_DEPT_CODE);
                //统计公司时查全部
                if(dept_level == 0) {
                    WmsCreCreditHeadFourSearchBeanVO vo = new WmsCreCreditHeadFourSearchBeanVO();
                    PmPersonnel pmPersonnel = queryInfo.getPersonnel();
                    vo.setMenu_code(WmsHelp.MENU_CODE_GSZL);
                    Map<String, Object> dataAuthority = this.getBizDataAuthority(vo, pmPersonnel);
                    if(dataAuthority.containsKey("dataAuthority") 
                            && dataAuthority.get("dataAuthority") != null
                            && !dataAuthority.get("dataAuthority").toString().equals("")) {
                        paramMap.put("dept_in", "(" + dataAuthority.get("dataAuthority") + ")");
                    }
                }
                //统计公司时查单个营业部
                else if(dept_level == 5) {
                    paramMap.put("dept_in", "(" + dept_id + ")");
                }
            } else {
                //统计门店时查全部
                if(dept_level == 5) {
                    paramMap.put("dept_info", queryInfo.getDept_info());
                }
                //统计门店时查单个团队
                else if(dept_level == 6) {
                    sysDept = sysDeptDao.get(sysDept.getDept_pid());
                    paramMap.put("dept_info", sysDept.getDept_code());
                    paramMap.put("dept_in", "(" + dept_id + ")");
                }
            }
        }
        //月份
        if(StringUtils.isNotBlank(queryInfo.getDate_info())) {
            paramMap.put("date_info", queryInfo.getDate_info());
            paramMap.put("date_info_like", SysTools.getSqlLikeParam(queryInfo.getDate_info()));
        }
        if(queryInfo.getIs_need_paging() != null) {
            if(queryInfo.getIs_need_paging() == 1) {
                paramMap.put("pagesize", queryInfo.getPagesize());
                paramMap.put("offset", queryInfo.getOffset());
            } else {
                paramMap.put("pagesize", null);
                paramMap.put("offset", null);
            }
        } else {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        }
        List<Map<String, Object>> list = this.wmsCreCreditHeadDao.getBizCompanyProgressStatistical(paramMap);
        queryInfo.setList(list);
        return queryInfo;
    }
    
    /**
     * @Title: getBizHouseLoanListUp
     * @Description: TODO(3.2.14   WMS_OUT_014 获取进度/历史查询单据信息)
     * @param vo
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 上午11:52:13
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizHouseLoanListUp(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO)
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    @Override
    public List<Map<String, Object>> getBizHouseLoanListUp(WmsCreCreditHeadFourSearchBeanVO vo, PmPersonnel personnel)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", vo.getUser_id());
        if(StringUtils.isNotEmpty(vo.getIs_need_paging()) && vo.getIs_need_paging().equals("0")) {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        } else {
            paramMap.put("pagesize", vo.getPagesize());
            paramMap.put("offset", vo.getOffset());
        }

        if(StringUtils.isNotEmpty(vo.getBill_status_code())){
            paramMap.put("bill_status_code", vo.getBill_status_code());
        }
        if (StringUtils.isNotEmpty(vo.getMany_column_like()))
        {
            paramMap.put("many_column_like", com.zx.moa.util.SysTools.getSqlLikeParam(vo.getMany_column_like()));
        }
        if (StringUtils.isNotEmpty(vo.getIs_type()))
        {
            paramMap.put("is_type", vo.getIs_type());
            if ("1".equals(vo.getIs_type()))
            {
                paramMap.put("sortname", "t1.last_update_timestamp");
            }
            else
            {
                paramMap.put("sortname", "t1.create_timestamp");
            }
        }
        if (StringUtils.isNotEmpty(vo.getSortname()))
        {
            paramMap.put("sortorder", vo.getSortname());
        }
        else
        {
            paramMap.put("sortorder", "desc");
        }
        paramMap.put("salesman_id", personnel.getPersonnel_id());
        List<Map<String, Object>> list = wmsCreCreditHeadDao.getBizHouseLoanListUp(paramMap);
        /*//查询贷款主表等相关表的信息
          Map<String, Object> bean = wmsCreCreditHeadDao.searchBeanByPkForMapUp_Request(reqMap);*/
        if(null != list) {
            //添加图片完整地址(拼接接口名)
            for(Map<String, Object> map_bean : list) {
                map_bean.put("attachment_address", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
            }
        }
        return list;
    }

    /**
     * @Title: getBizDetailsDocuments
     * @Description: (3.2.15   WMS_OUT_015 获取单据详细信息)
     * @param bean
     * @return 
     * @author: baisong
     * @time:2017年3月23日 下午4:36:54
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getBizDetailsDocuments(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO)
     * history:
     * 1、2017年3月23日 baisong 创建方法
     */
    @Override
    public Map<String, Object> getBizDetailsDocuments(WmsCreCreditHeadFourSearchBeanVO bean, PmPersonnel personnel)
    {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("wms_cre_credit_head_id", bean.getWms_cre_credit_head_id());
        // 查询贷款主表等相关表的信息
        Map<String, Object> mapHead = wmsCreCreditHeadDao.getBizDetailsDocumentsAll(reqMap);
        if (null != mapHead)
        {
            // 添加图片完整地址(拼接接口名) 当前地址是给前面使用的
            mapHead.put("attachment_address_complete", "wms/getImg.do?url=" + mapHead.get("attachment_address"));  
        }
        reqMap.put("sortname", "wms_cre_housing_apply_att_id");
        reqMap.put("sortorder", "asc");
        reqMap.put("data_detail_name", WmsHelp.DATA_ID_ATT_LIST);// 房产图片
        List<Map<String, Object>> attlist = wmsCreHousingApplyAttDao.search(reqMap);// 附件
        if (null != attlist)
        {
            // 添加图片完整地址(拼接接口名)
            for (Map<String, Object> map_bean : attlist)
            {
                map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wms_cre_credit_head_id", bean.getWms_cre_credit_head_id());
        map.put("sortname", "approval_time");
        map.put("sortorder", "asc");
        List<Map<String, Object>> worksList = wmsCreHousingApprovalInfoDao.searchAllInfo(map);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("ret_data", mapHead);
        resMap.put("attlist", attlist);
        if (worksList != null)
        {// 流程历程
            resMap.put("works", worksList);
        }
        // 联系人
        WmsCreCustomerChangeLineContact lc = new WmsCreCustomerChangeLineContact();
        lc.setWms_cre_credit_head_id(bean.getWms_cre_credit_head_id());
        List<Map<String, Object>> contacts_list = wmscrecustomerchangelinecontactDao.getBizContactInformationDetails(lc);
        // 联系人是否为空
        if (contacts_list != null)
        {
            resMap.put("contacts_list", contacts_list);
        }
        return resMap;
    }

    /**
     * @Title: getHomePageNotice
     * @Description: (获取首页公告)
     * @param bean
     * @param personnel
     * @return 
     * @author: baisong
     * @time:2017年3月28日 下午3:08:03
     * @see com.zx.moa.wms.request.loan.service.HouseLoanServiceFour#getHomePageNotice(com.zx.moa.wms.request.loan.vo.SysNoticeHeadSearchBeanVO, com.zx.moa.util.gen.entity.ioa.PmPersonnel)
     * history:
     * 1、2017年3月28日 baisong 创建方法
     */
    @Override
    public Map<String, Object> getHomePageNotice(SysNoticeHeadSearchBeanVO bean, PmPersonnel personnel)
    {

        Map<String, Object> paramMap = new HashMap<>();
        // app名称 例如mis
        paramMap.put("notice_app_type", bean.getNotice_app_type());
        paramMap.put("enable_flag", "1");
        if (bean.getIs_need_paging() != null && StringUtils.isNotEmpty(bean.getIs_need_paging().toString()) && bean.getIs_need_paging().equals("0"))
        {
            paramMap.put("pagesize", null);
            paramMap.put("offset", null);
        }
        else
        {
            paramMap.put("pagesize", bean.getPagesize());
            paramMap.put("offset", bean.getOffset());
        }
        List<Map<String, Object>> listMap = sysNoticeHeadDao.search(paramMap);
        // 获取公告url
        String url1 = HttpClientUtil.getSysUrl("moaUrl");
        for (int i = 0; i < listMap.size(); i++)
        {
            // 如果是内部url需要拼 如果是外部url不需要拼
            if (listMap.get(i).get("notice_url_type") != null && "1".equals(listMap.get(i).get("notice_url_type").toString()))
            {
                String url2 = listMap.get(i).get("notice_url").toString();
                listMap.get(i).put("notice_url", url1 + url2);
            }
        }
        paramMap.clear();
        paramMap.put("ret_data", listMap);
        return paramMap;
    }
    
    /**
     * 
     * @Title: statusStatisticsReportUp
     * @Description: 单据统计（年、状态节点统计）
     * @param request
     * @param bean
     * @return 
     * @author: wangyihan
     * @time:2017年7月24日 下午3:06:28
     * history:
     * 1、2017年7月24日 wangyihan 创建方法
     */
    @Override
    public WmsCreCreditHeadSearchBeanVO statusStatisticsReportUp(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("effective_time", WmsHelp.SYS_BILL_STATUS_STATISTICS_EFFECTIVE_TIME);
        //件数统计
        List<Map<String, Object>> time_statistics = this.wmsCreCreditHeadDao.searchBillStatusStaticsByYear(paramMap);
        //上单总件数
        Map<String, Object> cumulative_statistics = this.wmsCreCreditHeadDao.loansStatisticsAll(paramMap);
        cumulative_statistics.put("status_name", "累计上单数");
        cumulative_statistics.put("sum", "—");
        //单据状态统计
        List<Map<String, Object>> status_statistics = this.wmsCreCreditHeadDao.loansStatistics(paramMap);
        
        resMap.put("time_statistics", time_statistics);
        resMap.put("cumulative_statistics", cumulative_statistics);
        resMap.put("status_statistics", status_statistics);
        
        queryInfo.setResMap(resMap);
        
        return queryInfo;
    }
    
    /**
     * 
     * @Title: detailedStatisticsUp
     * @Description: 状态报表状态节点统计（月、日）
     * @param request
     * @param bean
     * @return 
     * @author: wangyihan
     * @time:2017年7月24日 下午3:06:28
     * history:
     * 1、2017年7月24日 wangyihan 创建方法
     */
    @Override
    public WmsCreCreditHeadSearchBeanVO detailedStatisticsUp(WmsCreCreditHeadSearchBeanVO queryInfo) {
        Date now_time_date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String statistics_time = queryInfo.getStatistics_time();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("effective_time", WmsHelp.SYS_BILL_STATUS_STATISTICS_EFFECTIVE_TIME);
        paramMap.put("statistics_time", queryInfo.getStatistics_time());
        List<Map<String, Object>> res_list = new ArrayList<Map<String, Object>>();
        //按年统计，例如2017年的所有月份
        if(statistics_time.length() == 4) {
            List<Map<String, Object>> list = this.wmsCreCreditHeadDao.searchBillStatusStaticsByMonth(paramMap);
            List<String> time_list = new ArrayList<String>();
            String tmp_time = "";
            for(Integer i = 12; i >= 1; i--) {
                tmp_time = i < 10 ? (statistics_time + "-0" + i.toString()) : statistics_time + "-" + i.toString();
                try
                {
                    //判断是否大于当前时间
                    if(format1.parse(tmp_time).getTime() 
                            > now_time_date.getTime()) {
                        continue;
                    }
                    //判断是否小于统计开始时间
                    if(format1.parse(tmp_time).getTime() 
                        < format1.parse(WmsHelp.SYS_BILL_STATUS_STATISTICS_EFFECTIVE_TIME.substring(0, 7)).getTime()) {
                        break;
                    }
                    time_list.add(tmp_time);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            Map<String, Object> map_obj = new HashMap<String, Object>();
            for(String s : time_list) {
                map_obj = new HashMap<String, Object>();
                map_obj.put("time", Integer.parseInt(s.substring(6, 7)) + "月");
                map_obj.put("real_time", s);
                for(Map<String, Object> tmp_map : list) {
                    if(s.equals(tmp_map.get("time").toString())) {
                        if(tmp_map.get("type").equals("1")) {
                            map_obj.put("count1", tmp_map.get("data"));
                        } else if(tmp_map.get("type").equals("2")) {
                            map_obj.put("count2", tmp_map.get("data"));
                        } else if(tmp_map.get("type").equals("3")) {
                            map_obj.put("sum", tmp_map.get("data"));
                        }
                    }
                }
                if(!map_obj.containsKey("count1")) {
                    map_obj.put("count1", 0);
                }
                if(!map_obj.containsKey("count2")) {
                    map_obj.put("count2", 0);
                }
                if(!map_obj.containsKey("sum")) {
                    map_obj.put("sum", 0);
                }
                res_list.add(map_obj);
            }
        }
        //按月统计，例如2017年5月的所有天
        else if(statistics_time.length() == 7) {
            List<Map<String, Object>> list = this.wmsCreCreditHeadDao.searchBillStatusStaticsByDay(paramMap);
            List<String> time_list = new ArrayList<String>();
            
            Calendar cal = Calendar.getInstance();  
            try
            {
                cal.setTime(format1.parse(statistics_time));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            int year = cal.get(Calendar.YEAR);  
            int m = cal.get(Calendar.MONTH);  
            int dayNumOfMonth = getDaysByYearMonth(year, m);  
            cal.set(Calendar.DAY_OF_MONTH, dayNumOfMonth);// 从最后一天开始  
          
            String tmp_time = "";
            for (int i = dayNumOfMonth; i > 0; i--, cal.add(Calendar.DATE, -1)) {  
                Date d = cal.getTime();  
                try
                {
                    //判断是否大于当前时间
                    if(d.getTime() 
                            > now_time_date.getTime()) {
                        continue;
                    }
                    //判断是否小于统计开始时间
                    if(d.getTime() 
                        < format2.parse(WmsHelp.SYS_BILL_STATUS_STATISTICS_EFFECTIVE_TIME).getTime()) {
                        break;
                    }
                    tmp_time = format2.format(d);
                    time_list.add(tmp_time);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                
            }  
            
            Map<String, Object> map_obj = new HashMap<String, Object>();
            for(String s : time_list) {
                map_obj = new HashMap<String, Object>();
                map_obj.put("time", Integer.parseInt(s.substring(8, 10)) + "日");
                map_obj.put("real_time", s);
                for(Map<String, Object> tmp_map : list) {
                    if(s.equals(tmp_map.get("time").toString())) {
                        if(tmp_map.get("type").equals("1")) {
                            map_obj.put("count1", tmp_map.get("data"));
                        } else if(tmp_map.get("type").equals("2")) {
                            map_obj.put("count2", tmp_map.get("data"));
                        } else if(tmp_map.get("type").equals("3")) {
                            map_obj.put("sum", tmp_map.get("data"));
                        }
                    }
                }
                if(!map_obj.containsKey("count1")) {
                    map_obj.put("count1", 0);
                }
                if(!map_obj.containsKey("count2")) {
                    map_obj.put("count2", 0);
                }
                if(!map_obj.containsKey("sum")) {
                    map_obj.put("sum", 0);
                }
                res_list.add(map_obj);
            }
        }
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("time_statistics", res_list);
        queryInfo.setResMap(resMap);
        return queryInfo;
    }
    
    //获取指定月份的天数  
    public static int getDaysByYearMonth(int year, int month) {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    } 
    
    
    
}