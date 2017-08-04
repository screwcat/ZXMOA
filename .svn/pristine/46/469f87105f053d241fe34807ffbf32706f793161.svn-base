package com.zx.moa.ioa.systemmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.systemmanage.persist.DictDataDao;
import com.zx.moa.ioa.systemmanage.service.IDictDataService;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.platform.syscontext.util.StringUtil;

@Service("ioaDictData")
public class DictDataServiceImpl implements IDictDataService{
	
	@Autowired
	private DictDataDao dictDataDao;

	@Override
	public ResultBean<List<Map<String, Object>>> getDictDatas(String dict_code) {
		if(StringUtil.isBlank(dict_code)){
			return ResultHelper.getError("参数不能为空!", null);
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String[] codes = dict_code.split(",");
		for(String code : codes){
			List<Map<String, Object>> l = dictDataDao.getDataDict(code);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", code);
			map.put("datas", l);
			list.add(map);
		}
		return ResultHelper.getSuccess(list);
	}

}
