package com.zx.moa.crm.dictionarymanage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.crm.dictionarymanage.service.IDictionaryService;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;

@Controller
public class DictionaryController {
	@Autowired
	IDictionaryService dictionaryservice;
	
	
	@RequestMapping(value = "crm/getDictDatas.do", method = {RequestMethod.GET,RequestMethod.POST})
	
	@ResponseBody
	public 	ResultBean<List<Map<String,Object>>> getDictDatas(HttpServletRequest request){
		
		String dictid = "";
		if(request.getParameter("dict_code")!=null&&!request.getParameter("dict_code").equals("")){
			dictid = request.getParameter("dict_code");
		}
		
		
		
		
		return ResultHelper.getSuccess(dictionaryservice.selectDictByCode(dictid));
	}
	
}
