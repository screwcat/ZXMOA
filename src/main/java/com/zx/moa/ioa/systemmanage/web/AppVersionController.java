package com.zx.moa.ioa.systemmanage.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.systemmanage.service.IAppVersionService;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * APP自动更新控制层
 * @author MATF
 *
 */
@Controller
public class AppVersionController {

	
	@Autowired
	private IAppVersionService appVersionService;
	
	/**
	 * 根据APP名称和系统名称获取APP最新版本信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ioa/getAppVersion.do",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultBean<Map<String, Object>> getAppVersion(HttpServletRequest request){
		String app_name = request.getParameter("app_name");
		String app_system = request.getParameter("app_system");
		if(StringUtil.isBlank(app_name)){
			return ResultHelper.getError("APP名称不能为空", null);
		}
		if(StringUtil.isBlank(app_system)){
			return ResultHelper.getError("APP应用系统不能为空", null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("app_name", app_name);
		map.put("app_system", app_system);
		return appVersionService.getAppVersionInfo(map);
	}
}
