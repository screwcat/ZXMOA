package com.zx.moa.wms.request.loan.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.loan.vo.TransmitValuesThreeVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsSysDictDataSearchBeanVO;
import com.zx.moa.wms.request.loan.vo.WmsCreHousingOperationLogBeanVO;

public interface HouseLoanService {

    /**
     * 房产抵押列表查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> searchHouseLoanList_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
    /**
     * 房产代办件列表查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getwmsHousingCertificatesList_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
  
    /**
     * 房产抵押单据明细查询
     * @param paramMap
     * @return
     */
    Map<String, Object> searchHouseLoanInfo_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);

    
    public List<Map<String, Object>> getDataDict(WmsSysDictDataSearchBeanVO queryInfo);
    
    /**
     * 用来实现贷前退回时列表数据的获取以及显示
     * 
     * @param list
     * @param idLists
     * @param taskIdLists
     * @param approvesGroups
     * @param approveAdvices
     * @param approveTimes
     * @return
     */
    public abstract List<Map<String, Object>> addTaskIDHouse(List<Map<String, Object>> list, List<Integer> idLists,
                                                             List<String> taskIdLists, List<String> approvesGroups,
                                                             List<String> approveAdvices, List<String> approveTimes);
    
   /**
    * 查询单据是否可以补件与办件
    * @param paramMap
    * @return
    */
   Map<String, Object> getHousingCertificatesFlag_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 获取贷款单据筛选信息
    * 
    * @param queryInfo
    * @return record
    * @author  baisong
    */
   Map<String, Object> searchHouseLoanListUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
 
   /**
    * Description : 获取房产核查列表(接口文档3.2)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public List<Map<String, Object>> getWmsHousingCertificatesList_RequestUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 待房产核查单据核查结果(接口文档3.3)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public Map<String, Object> savePropertyVerificationInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   
   /**
    * 
    * @Title: sendClaimOperUp
    * @Description: 发送房产核查领用状态
    * @param queryInfo
    * @param personnel
    * @return 
    * @author: ZhangWei
    * @time:2017年5月31日 下午5:27:21
    * history:
    * 1、2017年5月31日 ZhangWei 创建方法
    */
   public Map<String, Object> sendClaimOperUp(WmsCreHousingOperationLogBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 获取房贷客户单据信息列表(接口文档3.4)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public List<Map<String, Object>> searchHouseLoanListUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 获取补录初始化信息和数据字典表(接口文档3.8)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public Map<String, Object> getDataDictUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 提交信息前校验单据状态(接口文档3.9)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public Map<String, Object> isSureCertificateUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 获取放款申请信息列表(接口文档3.14)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public List<Map<String, Object>> searchLoanApplicationListUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 获取放款初始化信息(接口文档3.15)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public Map<String, Object> getLoanApprovalInitiInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 发送放款申请详细信息(接口文档3.16)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public Map<String, Object> sendLoanApprovalInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description : 获取放款申请确认信息(接口文档3.17)
    * 
    * @param queryInfo
    * @return map
    * @author wangyihan
    */
   public Map<String, Object> getLoanApprovalConfirmUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   
   /**
    * Description :APP首页信息展示--用于展示待办事项，和进展中的数据信息
    * @url /wms/getHomePageInfoUp.do
    * @param queryInfo
    * @return Map
    * @author  baisong
    * @date 2016-6-6
    */
   Map<String, Object> getHomePageInfoUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   /**
    * Description :获取系统权限
    * @url /wms/getAuthorityUp.do
    * @param queryInfo
    * @return Map
    * @author  baisong
    * @date 2016-6-6
    */
   Map<String, Object> getAuthorityUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
	/**
    * 方法用途：用于发送消息 ，消息中心消息，短信息，极光推送消息
    * @param Map<String,Object> map 当前参数中会传递多个数据 标示发送消息的情况和内容 
    * @param SysSendInfoVO中其他参数自定义
    * @return Map<String, Object> map  成功、失败
    * @author baisong
    */
   Map<String, Object> getUserAndSendInfo_Request(String sysSendInfoVO);
   /*
    *moa项目中mif项目调用的发送极光方法
    *baisong 
    */
   Map<String, Object> getUserAndSendInfoMOA_Request(Map<String, Object> map);
   
   /**
    * 房产抵押单据明细查询 第二版本
    * @param paramMap
    * @return
    * baisong
    */
   Map<String, Object> searchHouseLoanInfoUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   /**
    * Description :获取上传成功的图片数量
    * @url /wms/getPictureQuantity.do
    * @param queryInfo
    * @return Map
    * @author  baisong
    * @date 2016-6-6
    */
   Map<String, Object> getPictureQuantity_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
   /**
    * Description :获取限制上传图片的数量
    * @url /wms/getlimitNumber.do
    * @param queryInfo
    * @return Map
    * @author  jiaodelong
    * @date 2016-7-12
    */
   	Map<String, Object> getlimitNumber();
	/**
	 * 3.35	获取待授信确认单据信息列表 
	 * @param many_column_like
	 * @param sortname
	 * @param user_id
	 * @param page
	 * @param pagesize
	 * @param is_need_paging
	 * @return jiaodelong
	 */
	List<Map<String, Object>> getListInfoforCreditConfirm(TransmitValuesThreeVO vo);

	/**
	 * Description :  3.42获取房产核查页面信息。 
	 * @url /wms/getHouseAssessmentState.do
	 * @param queryInfo
	 * @return Map
	 * @author  baisong
	 * @date 2016-10-12
	 */
	Map<String, Object> getHouseAssessmentState(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
		
	/**
	 * Description : 获取房产评估单基本信息初始化信息(接口文档3.38)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public WmsCreCreditHeadSearchBeanVO initHouseAssessmentBasicInfoOne(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
	
	/**
	 * Description : 获取房产评估单房屋信息初始化信息(接口文档3.39)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public WmsCreCreditHeadSearchBeanVO initHouseAssessmentBasicInfoTwo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
	
	/**
	 * Description : 发送房产评估单基本信息详细信息(接口文档3.40)
	 * 
	 * @param queryInfo
	 * @return WmsCreCreditHeadSearchBeanVO
	 * @author wangyihan
	 */
	public WmsCreCreditHeadSearchBeanVO sendHouseAssessmentBasicInfoOne(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) throws ClientProtocolException, IOException;
	
	/**
	 * Description : 发送房产评估单房屋信息详细信息(接口文档3.41)
	 * 
	 * @param queryInfo
	 * @return WmsCreCreditHeadSearchBeanVO
	 * @author wangyihan
	 */
	public WmsCreCreditHeadSearchBeanVO sendHouseAssessmentBasicInfoTwo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) throws ClientProtocolException, IOException;

	
	
}
