package com.zx.moa.wms.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.wms.loan.persist.IWmsCreCustomerChangeLineContactDao;
import com.zx.moa.wms.loan.persist.WmsSysDictDataDao;
import com.zx.moa.wms.loan.service.IWmsCreCustomerChangeLineContactService;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContact;

/*
 * @version 2.0
 */

@Service("IWmsCreCustomerChangeLineContactService")
public class WmsCreCustomerChangeLineContactServiceImpl implements IWmsCreCustomerChangeLineContactService
{
    private static Logger log = LoggerFactory.getLogger(WmsCreCustomerChangeLineContactServiceImpl.class);

    @Autowired
    private IWmsCreCustomerChangeLineContactDao wmscrecustomerchangelinecontactDao;
    @Autowired
    private WmsSysDictDataDao wmssysdictdataDao;
    
    /**
     * Description :3.36	获取信息完善初始化信息 
     * @url /wms/initCreditConfirmInfo.do
     * @param queryInfo
     * @return Map
     * @author  jiaodelong
     * @date 2016-10-17
     */
	@Override
	public Map<String, Object> initCreditConfirmInfo(Integer user_id,Integer wms_cre_credit_head_id) {
		Map<String, Object> Map = new HashMap<String, Object>();
		WmsCreCustomerChangeLineContact wmscrecustomerchangelinecontact = new WmsCreCustomerChangeLineContact();
		wmscrecustomerchangelinecontact.setWms_cre_credit_head_id(wms_cre_credit_head_id);
		List<WmsCreCustomerChangeLineContact> contactList = wmscrecustomerchangelinecontactDao.getListByEntity(wmscrecustomerchangelinecontact);
//		List<WmsSysDictData> dataList = wmssysdictdataDao.getDictDataByDictId();
//		Map.put("credit_confirm_result_list", dataList);
		Map.put("contacts_list", contactList);
		return Map;
	}


   

}
