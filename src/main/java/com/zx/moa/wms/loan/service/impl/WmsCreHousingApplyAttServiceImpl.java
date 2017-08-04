package com.zx.moa.wms.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zx.moa.util.bean.UserBean;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.wms.loan.persist.WmsCreHousingApplyAttDao;
import com.zx.moa.wms.loan.service.IWmsCreHousingApplyAttService;
import com.zx.moa.wms.loan.vo.WmsCreHousingApplyAttSearchBeanVO;
import com.zx.platform.syscontext.vo.GridDataBean;

/*
 * @version 2.0
 */

@Service("wmscrehousingapplyattService")
public class WmsCreHousingApplyAttServiceImpl implements IWmsCreHousingApplyAttService {
	private static Logger log = LoggerFactory.getLogger(WmsCreHousingApplyAttServiceImpl.class);

	@Autowired
	private WmsCreHousingApplyAttDao wmscrehousingapplyattDao;

	@Override
	public Map<String, Object> getListWithoutPaging(WmsCreHousingApplyAttSearchBeanVO queryInfo) {
	 	Map<String,Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("sortname", queryInfo.getSortname());
	    paramMap.put("sortorder", queryInfo.getSortorder());
	    List<Map<String,Object>>  list = wmscrehousingapplyattDao.search(paramMap);
	    Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("Rows",list);
		return resMap;		
	}

	@Override
	public Map<String, Object> getListWithPaging(WmsCreHousingApplyAttSearchBeanVO queryInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sortname", queryInfo.getSortname());
        paramMap.put("sortorder", queryInfo.getSortorder());
        paramMap.put("pagesize", queryInfo.getPagesize());
        paramMap.put("offset", queryInfo.getOffset());
        List<Map<String,Object>> list = wmscrehousingapplyattDao.search(paramMap); 
        GridDataBean<Map<String,Object>> bean = new 
                GridDataBean<Map<String,Object>>(queryInfo.getPage(), wmscrehousingapplyattDao.findCount(paramMap),list);
		return bean.getGridData();			
	}

	@Override
	public WmsCreHousingApplyAtt getInfoByPK(Integer wms_cre_housing_apply_att_id) {
		return wmscrehousingapplyattDao.get(wms_cre_housing_apply_att_id);
	}

	@Override	
	@Transactional
	public String save(WmsCreHousingApplyAtt bean, UserBean user) {
		String resStr = "success";
		int ret = 0;
		ret = wmscrehousingapplyattDao.save(bean);
		if (ret == 0) {
			resStr = "error";
		}
		return resStr;
	}

	@Override
	@Transactional
	public String update(WmsCreHousingApplyAtt bean, UserBean user) {
		String resStr = "success";
		int ret = 0;
		ret = wmscrehousingapplyattDao.update(bean); // update a record replace columns, nonsupport null val
		if (ret == 0) {
			resStr = "error";
		}
		return resStr;
	}	
	
	/**
	 * Description :check repeat by "and" method, union checking, need new WmsCreHousingApplyAtt() include check-params
	 * @param queryInfo
	 * @param isExludePKFlag, true is exclude primary key, false is include primary key
	 * @return "success" or "repeat"
	 * @author auto_generator
	 */
	private List<WmsCreHousingApplyAtt> getListByEntity(WmsCreHousingApplyAtt queryInfo, Boolean isExcludePKFlag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isExcludePKFlag", isExcludePKFlag);
		String resStr = "success";
		List<WmsCreHousingApplyAtt> beanList = wmscrehousingapplyattDao.getListByEntity(queryInfo);
		return beanList;
	}
}
