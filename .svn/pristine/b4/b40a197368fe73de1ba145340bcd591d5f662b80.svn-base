package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContact;


/*
 * @version 2.0
 */
@MyBatisRepository
public interface IWmsCreCustomerChangeLineContactDao extends BaseDao<WmsCreCustomerChangeLineContact>
{

	List<WmsCreCustomerChangeLineContact> getListByEntity(WmsCreCustomerChangeLineContact wmscrecustomerchangelinecontact);

    /**
     * @Title: getBizContactInformation
     * @Description: TODO(3.2.17        获取联系人信息初始化)
     * @param wmscrecustomerchangelinecontact
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 上午11:22:38
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    List<Map<String, Object>> getBizContactInformation(WmsCreCustomerChangeLineContact wmscrecustomerchangelinecontact);

    /**
     * @Title: getBizContactInformationDetails
     * @Description: 单据详细信息联系人显示
     * @param lc
     * @return 
     * @author: baisong
     * @time:2017年4月17日 下午1:59:29
     * history:
     * 1、2017年4月17日 baisong 创建方法
     */
    List<Map<String, Object>> getBizContactInformationDetails(WmsCreCustomerChangeLineContact lc);
   
}