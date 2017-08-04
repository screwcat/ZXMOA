package com.zx.moa.wms.loan.service;

import java.util.List;
import java.util.Map;












import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.WmsCreCreditHead;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingFileInfo;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;

public interface IWmsCreCreditHeadService {

    /**
     * 房产抵押列表查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> searchHouseLoanList(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
    /**
     * 房产代办件列表查询
     * @param paramMap
     * @return
     * @author jiaodelong
     */
    List<Map<String, Object>> getwmsHousingCertificatesList(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel user);
  
    
    /**
     * 房产抵押单据明细查询
     * @param paramMap
     * @return
     */
    Map<String, Object> searchHouseLoanInfo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
    
    /**
     * 房产抵押相关文件上传
     * @param paramMap
     * @return
     */
    Map<String, Object> houseLoanUpload(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
    
    /**
     * 获取wms_cre_housing_file_info
     */
    public WmsCreHousingFileInfo getWmsCreHousingFileInfo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
    
    /**
     * 存入图片记录
     */
    public WmsCreHousingApplyAtt saveWmsCreHousingApplyAtt(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel);
	/**
	 * 
	 * 获取放款审批信息列表
	 * @param wmscrecredithead
	 * @return
	 */
    List<Map<String, Object>> getWmsMessageList(WmsCreCreditHeadSearchBeanVO wmscrecredithead,PmPersonnel personnel);
    /**
     * 获取放款审批信息详情
     * @param wms_cre_credit_head_id
     * @return
     */
	Map<String, Object> getLoanApprovalInfo(Integer wms_cre_credit_head_id);
    /**
     * @Title: getBizDetailsDocuments
     * @Description: TODO(3.2.15    WMS_OUT_015 获取单据详细信息)
     * @param wms_cre_credit_head_id
     * @return Map<String, Object>
     * @author: jiaodelong
     * @time:2017年3月23日 下午3:58:54
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    Map<String, Object> getBizDetailsDocuments(Integer wms_cre_credit_head_id);
    /**
     * @Title: getBizDetailsDocuments
     * @Description: TODO(3.2.10    WMS_OUT_010 获取客户验证信息)
     * @param vo
     * @return List<Map<String, Object>>
     * @author: jiaodelong
     * @time:2017年3月23日 下午5:08:17
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getBizDetailsDocuments(WmsCreCreditHeadFourSearchBeanVO vo);
    /**
     * @Title: getRefuseloan
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param vo
     * @return int
     * @author: jiaodelong
     * @time:2017年3月28日 上午10:45:41
     * history:
     * 1、2017年3月28日jiaodelong 创建方法
     */
    Integer getRefuseloan(WmsCreCreditHeadFourSearchBeanVO vo);
}
