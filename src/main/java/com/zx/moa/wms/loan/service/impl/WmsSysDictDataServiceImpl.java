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
import com.zx.moa.util.gen.entity.wms.WmsSysDictData;
import com.zx.moa.wms.loan.persist.WmsSysDictDataDao;
import com.zx.moa.wms.loan.service.IWmsSysDictDataService;
import com.zx.moa.wms.loan.vo.WmsSysDictDataSearchBeanVO;
import com.zx.platform.syscontext.vo.GridDataBean;

/*
 * @version 2.0
 */

@Service("wmssysdictdataService")
public class WmsSysDictDataServiceImpl implements IWmsSysDictDataService {
	private static Logger log = LoggerFactory.getLogger(WmsSysDictDataServiceImpl.class);

	@Autowired
	private WmsSysDictDataDao wmssysdictdataDao;

	@Override
	public Map<String, Object> getListWithoutPaging(WmsSysDictDataSearchBeanVO queryInfo) {
	 	Map<String,Object> paramMap = new HashMap<String, Object>();
	 	
	 	if(null != queryInfo.getP_wms_sys_dict_data_id()) {
           paramMap.put("p_wms_sys_dict_data_id", queryInfo.getP_wms_sys_dict_data_id());
        }
	 	if(null != queryInfo.getWms_sys_dict_id()) {
           paramMap.put("wms_sys_dict_id", queryInfo.getWms_sys_dict_id());
        }
	 	
	    paramMap.put("sortname", queryInfo.getSortname());
	    paramMap.put("sortorder", queryInfo.getSortorder());
	    List<Map<String,Object>>  list = wmssysdictdataDao.search(paramMap);
	    Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("Rows",list);
		return resMap;		
	}

	@Override
	public Map<String, Object> getListWithPaging(WmsSysDictDataSearchBeanVO queryInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sortname", queryInfo.getSortname());
        paramMap.put("sortorder", queryInfo.getSortorder());
        paramMap.put("pagesize", queryInfo.getPagesize());
        paramMap.put("offset", queryInfo.getOffset());
        List<Map<String,Object>> list = wmssysdictdataDao.search(paramMap); 
        GridDataBean<Map<String,Object>> bean = new 
                GridDataBean<Map<String,Object>>(queryInfo.getPage(), wmssysdictdataDao.findCount(paramMap),list);
		return bean.getGridData();			
	}

	@Override
	public WmsSysDictData getInfoByPK(Integer wms_sys_dict_data_id) {
		return wmssysdictdataDao.get(wms_sys_dict_data_id);
	}

	@Override	
	@Transactional
	public String save(WmsSysDictData bean, UserBean user) {
		String resStr = "success";
		int ret = 0;
		ret = wmssysdictdataDao.save(bean);
		if (ret == 0) {
			resStr = "error";
		}
		return resStr;
	}

	@Override
	@Transactional
	public String update(WmsSysDictData bean, UserBean user) {
		String resStr = "success";
		int ret = 0;
		ret = wmssysdictdataDao.update(bean); // update a record replace columns, nonsupport null val
		if (ret == 0) {
			resStr = "error";
		}
		return resStr;
	}	
	
	/**
	 * Description :check repeat by "and" method, union checking, need new WmsSysDictData() include check-params
	 * @param queryInfo
	 * @param isExludePKFlag, true is exclude primary key, false is include primary key
	 * @return "success" or "repeat"
	 * @author auto_generator
	 */
	private List<WmsSysDictData> getListByEntity(WmsSysDictData queryInfo, Boolean isExcludePKFlag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isExcludePKFlag", isExcludePKFlag);
		String resStr = "success";
		List<WmsSysDictData> beanList = wmssysdictdataDao.getListByEntity(queryInfo);
		return beanList;
	}
}
