package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.WmsCreCreditLineCustomerChangeHead;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContactBeanVo;

/**
 * 
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：财富管理平台
 * @ClassName: WmsCreCreditLineCustomerChangeHeadDao
 * 模块名称：客户变更主表
 * @Description: 内容摘要：
 * @author baisong
 * @date 2016年12月27日
 * @version V1.0
 * history:
 * 1、2016年12月27日 baisong 创建文件
 */
@MyBatisRepository
public interface WmsCreCreditLineCustomerChangeHeadDao extends BaseDao<WmsCreCreditLineCustomerChangeHead>
{

    /**
     * 根据贷款主表ID获取主贷�?
     * 
     * @param wms_cre_credit_head_id 贷款主表ID
     * @return
     */
    WmsCreCreditLineCustomerChangeHead getMainByFk(Integer wms_cre_credit_head_id);

    
    /**
     * 根据客户id删除所有客户信息(逻辑删除)
     * @author administrator
     */
    void wmsCustomerAllInfoDelete(Integer wms_cre_credit_line_customer_change_head_id);
    
    /**
     * 更新客户变更主表
     * @param paramMap
     * @return
     */
    public int updateforBLTwo(WmsCreCreditLineCustomerChangeHead wmscrecreditlinecustomerchangehead);
    
    int saveInfo(WmsCreCreditLineCustomerChangeHead wmscrecreditlinecustomerchangehead);

	WmsCreCreditLineCustomerChangeHead getChangeHeadId(
			Integer wms_cre_credit_head_id);

    /**
     * 
     * @Title: getCopyInfo
     * @Description: TODO(查询当前表的所有信息--用于复制)
     * @param wms_cre_credit_head_id
     * @return 
     * @author: baisong
     * @time:2016年12月23日 下午5:18:59
     * history:
     * 1、2016年12月23日 baisong 创建方法
     */
    List<WmsCreCreditLineCustomerChangeHead> getCopyInfo(Integer wms_cre_credit_head_id);

    /**
     * 
     * @Title: getCopyInfo
     * @Description: TODO(查询表)
     * @param wms_cre_credit_head_id
     * @return 
     * @author: baisong
     * @time:2016年12月23日 下午5:18:59
     * history:
     * 1、2016年12月23日 baisong 创建方法
     */
    List<WmsCreCreditLineCustomerChangeHead> getInfoByHeadId(Map<String, Object> map);

    /**
     * @Title: getChangeHeadIdForFour
     * @Description: TODO(查询表)
     * @param wms_cre_credit_head_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 下午5:03:17
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
    */
    WmsCreCreditLineCustomerChangeHead getChangeHeadIdForFour(Integer wms_cre_credit_head_id);


    /**
     * @Title: getContactHeadInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param vo
     * @return 
     * @author: 张明建
     * @time:2017年3月21日 下午5:17:45
     * history:
     * 1、2017年3月21日 张明建 创建方法
     */
    Map<String, Object> getContactHeadInfo(WmsCreCustomerChangeLineContactBeanVo vo);
}
