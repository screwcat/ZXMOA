package com.zx.moa.wms.loan.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.bean.UserBean;
import com.zx.moa.util.gen.entity.wms.WmsSysDictData;
import com.zx.moa.wms.loan.service.IWmsSysDictDataService;
import com.zx.moa.wms.loan.vo.WmsSysDictDataSearchBeanVO;

/*
 * @version 2.0
 * @author
 */

@Controller
public class WmsSysDictDataController {
	private static Logger log = LoggerFactory.getLogger(WmsSysDictDataController.class);
	
	@Autowired
	private IWmsSysDictDataService wmssysdictdataService;

	/**
	 * Description :get record list records by vo queryInfo withnot paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	@RequestMapping(value = "/loan/wmssysdictdatawithoutpaginglist.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getListWithoutPaging(WmsSysDictDataSearchBeanVO queryInfo) {
		return wmssysdictdataService.getListWithoutPaging(queryInfo);
	}

	/**
	 * Description :get record list records by vo queryInfo with paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	@RequestMapping(value = "/loan/wmssysdictdatawithpaginglist.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getListWithPaging(WmsSysDictDataSearchBeanVO queryInfo) {
		return wmssysdictdataService.getListWithPaging(queryInfo);
	}
	
	/**
	 * Description :get single-table information by primary key 
	 * @param primary key 
	 * @return WmsSysDictDataVO
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmssysdictdatainfobypk.do", method = {RequestMethod.GET})
	@ResponseBody
	public WmsSysDictData getInfoByPK(Integer wms_sys_dict_data_id) {
		return wmssysdictdataService.getInfoByPK(wms_sys_dict_data_id);
	}	

	/**
	 * Description :add method
	 * @param bean
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmssysdictdatasave.do", method = {RequestMethod.POST})
	@ResponseBody
	public String doSave(HttpServletRequest request, WmsSysDictData bean) {
		String result = "";
		HttpSession session = request.getSession();
		UserBean user = (UserBean)session.getAttribute(GlobalVal.USER_SESSION);
		try {
			result = wmssysdictdataService.save(bean, user);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "error";
		}
		/*
		// record log	
		if("success".equals(result)){
			String msg = "log content";
			SysTools.saveLog(request, msg); // record log method
		}
		*/
		return result;
	}

	/**
	 * Description :update method
	 * @param bean
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmssysdictdataupdate.do", method = {RequestMethod.POST})
	@ResponseBody
	public String doUpdate(HttpServletRequest request, WmsSysDictData bean) {
		String result = "";
		HttpSession session = request.getSession();
		UserBean user = (UserBean)session.getAttribute(GlobalVal.USER_SESSION);
		try {
			result = wmssysdictdataService.update(bean, user);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "error";
		}
		/*			
		// record log	
		if("success".equals(result)){
			String msg = "log content";
			SysTools.saveLog(request, msg); // record log method
		}
		*/
		return result;
	}	
}