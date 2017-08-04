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
import com.zx.moa.util.gen.entity.wms.WmsSysDict;
import com.zx.moa.wms.loan.persist.WmsSysDictDao;
import com.zx.moa.wms.loan.service.IWmsSysDictService;
import com.zx.moa.wms.loan.vo.WmsSysDictSearchBeanVO;
import com.zx.platform.syscontext.vo.GridDataBean;

/*
 * @version 2.0
 */

@Service("wmssysdictService")
public class WmsSysDictServiceImpl implements IWmsSysDictService {
	private static Logger log = LoggerFactory.getLogger(WmsSysDictServiceImpl.class);

	@Autowired
	private WmsSysDictDao wmssysdictDao;

	@Override
	public Map<String, Object> getListWithoutPaging(WmsSysDictSearchBeanVO queryInfo) {
	 	Map<String,Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("sortname", queryInfo.getSortname());
	    paramMap.put("sortorder", queryInfo.getSortorder());
	    List<Map<String,Object>>  list = wmssysdictDao.search(paramMap);
	    Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("Rows",list);
		return resMap;		
	}

	@Override
	public Map<String, Object> getListWithPaging(WmsSysDictSearchBeanVO queryInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sortname", queryInfo.getSortname());
        paramMap.put("sortorder", queryInfo.getSortorder());
        paramMap.put("pagesize", queryInfo.getPagesize());
        paramMap.put("offset", queryInfo.getOffset());
        List<Map<String,Object>> list = wmssysdictDao.search(paramMap); 
        GridDataBean<Map<String,Object>> bean = new 
                GridDataBean<Map<String,Object>>(queryInfo.getPage(), wmssysdictDao.findCount(paramMap),list);
		return bean.getGridData();			
	}

	@Override
	public WmsSysDict getInfoByPK(Integer wms_sys_dict_id) {
		return wmssysdictDao.get(wms_sys_dict_id);
	}

	@Override	
	@Transactional
	public String save(WmsSysDict bean, UserBean user) {
		String resStr = "success";
		int ret = 0;
		ret = wmssysdictDao.save(bean);
		if (ret == 0) {
			resStr = "error";
		}
		return resStr;
	}

	@Override
	@Transactional
	public String update(WmsSysDict bean, UserBean user) {
		String resStr = "success";
		int ret = 0;
		ret = wmssysdictDao.update(bean); // update a record replace columns, nonsupport null val
		if (ret == 0) {
			resStr = "error";
		}
		return resStr;
	}	
	
	/**
	 * Description :check repeat by "and" method, union checking, need new WmsSysDict() include check-params
	 * @param queryInfo
	 * @param isExludePKFlag, true is exclude primary key, false is include primary key
	 * @return "success" or "repeat"
	 * @author auto_generator
	 */
	private List<WmsSysDict> getListByEntity(WmsSysDict queryInfo, Boolean isExcludePKFlag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isExcludePKFlag", isExcludePKFlag);
		String resStr = "success";
		List<WmsSysDict> beanList = wmssysdictDao.getListByEntity(queryInfo);
		return beanList;
	}
}
