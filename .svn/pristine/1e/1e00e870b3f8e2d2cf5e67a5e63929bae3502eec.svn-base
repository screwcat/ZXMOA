package com.zx.moa.ioa.systemmanage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.systemmanage.persist.AppVersionDao;
import com.zx.moa.ioa.systemmanage.service.IAppVersionService;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;

@Service("appVersionService")
public class AppVersionServiceImpl implements IAppVersionService{

	@Autowired
	private AppVersionDao appVersionDao;
	
	@Override
	public ResultBean<Map<String, Object>> getAppVersionInfo(Map<String, Object> map) {
		Map<String, Object> result = appVersionDao.getAppVersionInfo(map);
		if(result == null || result.get("app_name") == null){
			return ResultHelper.getError("没有版本信息!", null);
		}
		return ResultHelper.getSuccess(result);
	}

}
