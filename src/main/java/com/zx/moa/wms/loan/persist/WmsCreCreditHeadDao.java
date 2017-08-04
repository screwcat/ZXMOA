package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zx.moa.util.gen.entity.wms.WmsCreCreditHead;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;


@MyBatisRepository
public interface WmsCreCreditHeadDao extends BaseDao<WmsCreCreditHead> {

    /**
     * 房产抵押列表查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> searchHouseLoanList(Map<String, Object> paramMap);
    
    /**
     * 房产抵押条数查询
     * @param paramMap
     * @return
     */
    int searchHouseLoanCount(Map<String, Object> paramMap);
    
    Map<String, Object> searchBeanByPkForMap(Map<String, Object> paramMap);
    
    
    /**
     * 房产代办件列表查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getwmsHousingCertificatesList(Map<String, Object> paramMap);
    
    /**
     * 房产代办件列表查询
     * @param paramMap
     * @return
     */
    int getwmsHousingCertificatesCount(Map<String, Object> paramMap);
    
    
    
    
    /**************************************** 外部接口调用开始****************************************/
    
    /**
     * 房产抵押列表查询(外部调用)
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> searchHouseLoanList_Request(Map<String, Object> paramMap);
    
    /**
     * 查询单据明细(外部调用)
     * @param paramMap
     * @return
     */
    Map<String, Object> searchBeanByPkForMap_Request(Map<String, Object> paramMap);
    
    /**
     * 房产代办件列表查询(外部调用)
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getwmsHousingCertificatesList_Request(Map<String, Object> paramMap);
    
    /**
     * 查询补件标识(0:不允许 1:允许)
     * @param paramMap
     * @return
     */
    Map<String, Object> getHousingCertificatesFlag_Request(Map<String, Object> paramMap);
    
    /**************************************** 外部接口调用结束****************************************/
    /*************************************移动端第二版本接口开始**********************************************/
    /**
     * 查询单据明细(外部调用)---移动端第二版本
     * @param paramMap
     * @return
     */
    Map<String, Object> searchBeanByPkForMapUp_Request(Map<String, Object> paramMap);
    
    /**
     * 待办事项查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getHomePageInfoUp_Request(Map<String, Object> paramMap);
    /**
     * 待办事项查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getHomePageInfoProUp_Request(Map<String, Object> paramMap);
    /*************************************移动端第二版本接口结束**********************************************/
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 获取补录初始化信息
     * @param paramMap
     * @return
     */
    Map<String, Object> dispMakeUpInfo(Map<String, Object> paramMap);
    
    /**
     * 提交信息前校验单据状态
     * @param paramMap
     * @return
     */
    Map<String, Object> isSureCertificateUp(Map<String, Object> paramMap);
    
    /**
     * 获取房产核查信息列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getWmsHousingCertificatesList_RequestUp(Map<String, Object> paramMap);
    
    /**
     * 获取房贷客户单据信息列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> searchHouseLoanListUp(Map<String, Object> paramMap);
    
    /**
     * 获取放款申请信息列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> searchLoanApplicationListUp(Map<String, Object> paramMap);
    
    /**
     * 获取放款初始化信息
     * @param paramMap
     * @return
     */
    Map<String, Object> getLoanApprovalInitiInfoUp(Map<String, Object> paramMap);
    
    /**
     * 
     * 获取放款审批信息列表
     * @param paramMap
     * @return
     */
	List<Map<String, Object>> getWmsMessageList(Map<String, Object> paramMap);
	
    /**
     * 
     * 获取放款审批信息详情
     * @param wms_cre_credit_head_id
     * @return
     */
	Map<String, Object> getLoanApprovalInfo(@Param("wms_cre_credit_head_id")Integer wms_cre_credit_head_id);

	/**
	 * 查询房贷单据审批详情中图片list
	 */
	List<Map<String, Object>> selectApprovalAttList(@Param("_wms_cre_credit_head_id")Integer wms_cre_credit_head_id);
	
	/**
	 * 根据单据id查询房产信息
	 */
	Map<String, Object> selectHouseInfoByWmsCreCreditHeadId(Map<String, Object> paramMap);
	
	/**
	 * 根据人的id和菜单id获取当前人对当前菜单的权限
	 * @param salesman_id
	 * @param menu_id
	 * baisong 2016-8-23
	 */
	Map<String, Object>	queryChildrenDeptInfo(Map<String, Object> paramMap);
/**
 * 3.35	获取待授信确认单据信息列表 
 * @param paramMap
 * @return jiaodelong
 */
	List<Map<String, Object>> getListInfoforCreditConfirm(
			Map<String, Object> paramMap);
    /**
     * Description :  3.42获取房产核查页面信息。 
     * @url /wms/getHouseAssessmentState.do
     * @param queryInfo
     * @return Map
     * @author  baisong
     * @date 2016-10-12
     */
	List<Map<String, Object>> getHouseAssessmentState(Integer wms_cre_credit_head_id);
	
	/**
	 * 
	 * @Title: getHousingSpprovalInfo
	 * @Description: 根据人的贷款主表id查看房产评估单填写状态
	 * @param wms_cre_credit_head_id
	 * @return 
	 * @author: ZhangWei
	 * @time:2017年4月12日 上午11:20:19
	 * history:
	 * 1、2017年4月12日 ZhangWei 创建方法
	 */
	List<Map<String, Object>> getHousingSpprovalInfo(Integer wms_cre_credit_head_id);
	
	
	/**
     * Description : 获取房产评估单基本信息初始化信息(接口文档3.38)
     * 
     * @param queryInfo
     * @return map
     * @author wangyihan
     */
    public Map<String, Object> initHouseAssessmentBasicInfoOne(Map<String, Object> paramMap);
    
    /**
     * Description : 获取房产评估单房屋信息初始化信息(接口文档3.39)
     * 
     * @param queryInfo
     * @return map
     * @author wangyihan
     */
    public Map<String, Object> initHouseAssessmentBasicInfoTwo(Map<String, Object> paramMap);

    /**
     * @Title: getBizProgressDocuments
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param user_id
     * @param page
     * @param pagesize
     * @return 
     * @author: jiaodelong
     * @time:2017年3月21日 上午9:55:40
     * history:
     * 1、2017年3月21日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getBizProgressDocuments(Map<String, Object> paramMap);

    /**
     * @Title: getBizProgressNumber
     * @Description: TODO(3.2.5     进度查询提示数据)
     * @param user_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月22日 上午10:13:40
     * history:
     * 1、2017年3月22日 jiaodelong 创建方法
     */
    Integer getBizProgressNumber(@Param("user_id")Integer user_id);
	
    /**
     * 
     * @Title: getBizBillDetails
     * @Description: TODO(获取成员单据详细信息)
     * @param paramsMap
     * @return 
     * @author: handongchun
     * @time:2017年3月22日 下午2:04:08
     * history:
     * 1、2017年3月22日 handongchun 创建方法
     */
    List<Map<String,Object>> getBizBillDetails(Map<String,Object> paramsMap);
    
    
    
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
    Map<String, Object> getBizHomePagePerformance(Map<String, Object> paramMap);
    
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
    List<Map<String, Object>> getBizPerformanceRanking(Map<String, Object> paramMap);
    
    /**
     * 接口编号: WMS_OUT_022
     * @Title: getBizResultsStatisticalByMonth
     * @Description: 获取月份业绩统计信息（我的业绩、团队、门店、公司）
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    List<Map<String, Object>> getBizResultsStatisticalByMonth(Map<String, Object> paramMap);
    
    /**
     * 接口编号: WMS_OUT_022
     * @Title: getBizResultsStatisticalByYear
     * @Description: 获取月份业绩统计信息（我的业绩、团队、门店、公司）
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
    List<Map<String, Object>> getBizResultsStatisticalByYear(Map<String, Object> paramMap);
    
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
    List<Map<String, Object>> getBizMonthMembers(Map<String, Object> paramMap);
    
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
    List<Map<String, Object>> getBizMembersProgressStatistical(Map<String, Object> paramMap);
    
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
    List<Map<String, Object>> getBizStoreResultsStatistics(Map<String, Object> paramMap);
    
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
    List<Map<String, Object>> getBizCompanyProgressStatistical(Map<String, Object> paramMap);
    
    /**
     * @Title: getBizHouseLoanListUp
     * @Description: TODO(3.2.14   WMS_OUT_014 获取进度/历史查询单据信息)
     * @param paramMap
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 下午1:18:02
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getBizHouseLoanListUp(Map<String, Object> paramMap);

    /**
     * @Title: getBizDetailsDocuments
     * @Description: TODO(获取单据详细信息)
     * @param wms_cre_credit_head_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 下午4:01:15
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    Map<String, Object> getBizDetailsDocuments(Integer wms_cre_credit_head_id);

    /**
     * @Title: selectApprovalAttListForFour
     * @Description: TODO(获取全部图片)
     * @param wms_cre_credit_head_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 下午4:02:09
     * history:
     * 1、2017年3月23日jiaodelong 创建方法
     */
    List<Map<String, Object>> selectApprovalAttListForFour(Integer wms_cre_credit_head_id);

    /**
     * @Title: getNotaryWarnInfo
     * @Description: TODO(查询还款表客户信息)
     * @param paramMap
     * @return Map<String, Object>
     * @author: jiaodelong
     * @time:2017年3月23日 下午5:24:03
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getNotaryWarnInfo(Map<String, Object> paramMap);

    /**
     * @Title: getHeadInfo
     * @Description: TODO(查询主编的客户信息)
     * @param paramMap
     * @return 
     * @author: paramMap
     * @time:2017年3月23日 下午5:30:31
     * history:
     * 1、2017年3月23日 paramMap 创建方法
     */
//    Map<String, Object> getHeadInfo(Map<String, Object> paramMap);
    

    /**
     * @Title: getBizDetailsDocuments
     * @Description: 3.2.15   WMS_OUT_015 获取单据详细信息
     * @param reqMap
     * @return 
     * @author: baisong
     * @time:2017年3月23日 下午5:08:38
     * history:
     * 1、2017年3月23日 baisong 创建方法
     */
    Map<String, Object> getBizDetailsDocumentsAll(Map<String, Object> reqMap);

    /**
     * @Title: getRefuseloan
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param map
     * @return int
     * @author: jiaodelong
     * @time:2017年3月28日 上午11:27:18
     * history:
     * 1、2017年3月28日 jiaodelong 创建方法
     */
    Integer getRefuseloan(Map<String, Object> map);

    /**
     * @Title: getHeadInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return 
     * @author: jiaodelong
     * @time:2017年3月28日 下午3:03:56
     * history:
     * 1、2017年3月28日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getHeadInfo(Map<String, Object> map);

    /**
     * @Title: getHeadInfoForId
     * @Description: TODO(根据headID查询主编信息)
     * @param object
     * @return 
     * @author: jiaodelong
     * @time:2017年3月28日 下午3:30:15
     * history:
     * 1、2017年3月28日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getHeadInfoForId(Object object);
    
    
    /**
     * 
     * @Title: billStatusStaticsDisp
     * @Description: 上单、放款总件数、金额汇总
     * @param request
     * @param queryInfo
     * @return 
     * @author: wangyihan
     * @time:2017年7月6日 下午1:15:55
     * history:
     * 1、2017年7月6日 wangyihan 创建方法
     */
    Map<String, Object> loansStatisticsAll(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title: billStatusStaticsDisp
     * @Description: 单据状态统计
     * @param request
     * @param queryInfo
     * @return 
     * @author: wangyihan
     * @time:2017年7月6日 下午1:15:55
     * history:
     * 1、2017年7月6日 wangyihan 创建方法
     */
    List<Map<String, Object>> loansStatistics(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title: searchBillStatusStaticsByYear
     * @Description: 上单、放款数汇总(年)
     * @param request
     * @param queryInfo
     * @return 
     * @author: wangyihan
     * @time:2017年7月6日 下午1:15:55
     * history:
     * 1、2017年7月6日 wangyihan 创建方法
     */
    List<Map<String, Object>> searchBillStatusStaticsByYear(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title: searchBillStatusStaticsByMonth
     * @Description: 上单、放款数汇总(月)
     * @param request
     * @param queryInfo
     * @return 
     * @author: wangyihan
     * @time:2017年7月6日 下午1:15:55
     * history:
     * 1、2017年7月6日 wangyihan 创建方法
     */
    List<Map<String, Object>> searchBillStatusStaticsByMonth(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title: searchBillStatusStaticsByDay
     * @Description: 上单、放款数汇总(日)
     * @param request
     * @param queryInfo
     * @return 
     * @author: wangyihan
     * @time:2017年7月6日 下午1:15:55
     * history:
     * 1、2017年7月6日 wangyihan 创建方法
     */
    List<Map<String, Object>> searchBillStatusStaticsByDay(Map<String, Object> paramMap);
    
    
    
}
