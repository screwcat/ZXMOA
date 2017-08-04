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
import com.zx.moa.util.gen.entity.wms.WmsCreHousingFileInfo;
import com.zx.moa.wms.loan.service.IWmsCreHousingFileInfoService;
import com.zx.moa.wms.loan.vo.WmsCreHousingFileInfoSearchBeanVO;

/*
 * @version 2.0
 * @author
 */

@Controller
public class WmsCreHousingFileInfoController {
	private static Logger log = LoggerFactory.getLogger(WmsCreHousingFileInfoController.class);
	
	@Autowired
	private IWmsCreHousingFileInfoService wmscrehousingfileinfoService;

	/**
	 * Description :get record list records by vo queryInfo withnot paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	@RequestMapping(value = "/loan/wmscrehousingfileinfowithoutpaginglist.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getListWithoutPaging(WmsCreHousingFileInfoSearchBeanVO queryInfo) {
		return wmscrehousingfileinfoService.getListWithoutPaging(queryInfo);
	}

	/**
	 * Description :get record list records by vo queryInfo with paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	@RequestMapping(value = "/loan/wmscrehousingfileinfowithpaginglist.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getListWithPaging(WmsCreHousingFileInfoSearchBeanVO queryInfo) {
		return wmscrehousingfileinfoService.getListWithPaging(queryInfo);
	}
	
	/**
	 * Description :get single-table information by primary key 
	 * @param primary key 
	 * @return WmsCreHousingFileInfoVO
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmscrehousingfileinfoinfobypk.do", method = {RequestMethod.GET})
	@ResponseBody
	public WmsCreHousingFileInfo getInfoByPK(Integer wms_cre_housing_file_info_id) {
		return wmscrehousingfileinfoService.getInfoByPK(wms_cre_housing_file_info_id);
	}	

	/**
	 * Description :add method
	 * @param bean
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmscrehousingfileinfosave.do", method = {RequestMethod.POST})
	@ResponseBody
	public String doSave(HttpServletRequest request, WmsCreHousingFileInfo bean) {
		String result = "";
		HttpSession session = request.getSession();
		UserBean user = (UserBean)session.getAttribute(GlobalVal.USER_SESSION);
		try {
			result = wmscrehousingfileinfoService.save(bean, user);
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
	@RequestMapping(value = "/loan/wmscrehousingfileinfoupdate.do", method = {RequestMethod.POST})
	@ResponseBody
	public String doUpdate(HttpServletRequest request, WmsCreHousingFileInfo bean) {
		String result = "";
		HttpSession session = request.getSession();
		UserBean user = (UserBean)session.getAttribute(GlobalVal.USER_SESSION);
		try {
			result = wmscrehousingfileinfoService.update(bean, user);
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