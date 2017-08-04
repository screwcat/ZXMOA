package com.zx.moa.ioa.systemmanage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.systemmanage.service.IDictDataService;
import com.zx.moa.util.bean.ResultBean;

/**
 * IOA系统数据字典控制层
 * @author MATF
 *
 */
@Controller
public class DictDataManageController {
	
	@Autowired
	private IDictDataService ioaDictData;


	/**
	 * 根据字典编码获取数据字典详细信息（code、meaning）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ioa/getDictData.do",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultBean<List<Map<String, Object>>> getDictData(HttpServletRequest request){
		String dict_code = request.getParameter("dict_code");
		return ioaDictData.getDictDatas(dict_code);
	}
}
