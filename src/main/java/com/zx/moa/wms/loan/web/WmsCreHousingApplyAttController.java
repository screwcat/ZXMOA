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
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.wms.loan.service.IWmsCreHousingApplyAttService;
import com.zx.moa.wms.loan.vo.WmsCreHousingApplyAttSearchBeanVO;

/*
 * @version 2.0
 * @author
 */

@Controller
public class WmsCreHousingApplyAttController {
	private static Logger log = LoggerFactory.getLogger(WmsCreHousingApplyAttController.class);
	
	@Autowired
	private IWmsCreHousingApplyAttService wmscrehousingapplyattService;

	/**
	 * Description :get record list records by vo queryInfo withnot paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	@RequestMapping(value = "/loan/wmscrehousingapplyattwithoutpaginglist.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getListWithoutPaging(WmsCreHousingApplyAttSearchBeanVO queryInfo) {
		return wmscrehousingapplyattService.getListWithoutPaging(queryInfo);
	}

	/**
	 * Description :get record list records by vo queryInfo with paging
	 * @param queryInfo
	 * @return record list
	 * @author auto_generator
	 */
	@RequestMapping(value = "/loan/wmscrehousingapplyattwithpaginglist.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getListWithPaging(WmsCreHousingApplyAttSearchBeanVO queryInfo) {
		return wmscrehousingapplyattService.getListWithPaging(queryInfo);
	}
	
	/**
	 * Description :get single-table information by primary key 
	 * @param primary key 
	 * @return WmsCreHousingApplyAttVO
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmscrehousingapplyattinfobypk.do", method = {RequestMethod.GET})
	@ResponseBody
	public WmsCreHousingApplyAtt getInfoByPK(Integer wms_cre_housing_apply_att_id) {
		return wmscrehousingapplyattService.getInfoByPK(wms_cre_housing_apply_att_id);
	}	

	/**
	 * Description :add method
	 * @param bean
	 * @return "success" or "error" or user defined
	 * @author auto_generator
	 */	
	@RequestMapping(value = "/loan/wmscrehousingapplyattsave.do", method = {RequestMethod.POST})
	@ResponseBody
	public String doSave(HttpServletRequest request, WmsCreHousingApplyAtt bean) {
		String result = "";
		HttpSession session = request.getSession();
		UserBean user = (UserBean)session.getAttribute(GlobalVal.USER_SESSION);
		try {
			result = wmscrehousingapplyattService.save(bean, user);
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
	@RequestMapping(value = "/loan/wmscrehousingapplyattupdate.do", method = {RequestMethod.POST})
	@ResponseBody
	public String doUpdate(HttpServletRequest request, WmsCreHousingApplyAtt bean) {
		String result = "";
		HttpSession session = request.getSession();
		UserBean user = (UserBean)session.getAttribute(GlobalVal.USER_SESSION);
		try {
			result = wmscrehousingapplyattService.update(bean, user);
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