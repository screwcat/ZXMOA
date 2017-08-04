package com.zx.moa.crm.dictionarymanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.crm.dictionarymanage.persist.DictionaryDao;
import com.zx.moa.crm.dictionarymanage.service.IDictionaryService;
import com.zx.moa.crm.dictionarymanage.vo.SysDictData;
@Service
public class DictionaryServiceImpl implements IDictionaryService {
	@Autowired
	private DictionaryDao dictonarydao; 
	/*
	 * 根据字典编码查询数据字典数据
	 */
	@Override
	public 	List<Map<String,Object>> selectDictByCode(String dictid) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> json = new ArrayList<Map<String,Object>>();
		String dict [] = dictid.split(",");
		for (int i = 0; i < dict.length; i++) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("dict_code", dict[i]);
			List<SysDictData> list = dictonarydao.selectDictByCode(map);
			map.put("sys_dict_data", list);
			json.add(map);
		}
		
		return json;
	}

}
