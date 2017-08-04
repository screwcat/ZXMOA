package com.zx.moa.ioa.pushmanage.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.ioa.util.PushManage;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 推送管理控制层
 * @author MATF
 *
 */
@Controller
public class PushManageController {

	@Autowired
	private ITaskMessageService taskMessageService;
	
	/**
	 * 推送消息给单个用户
	 * @param request
	 * 		user_code : 推送员工编号（用于通过APP名称获取 推送别名）
	 * 		msg_context : 推送消息
	 * 		extras ： 附加信息（用于客户端处理使用的数据信息）
	 * 		msg_code : 客户端识别编码（会附属在extras）
	 * 		app_name : 要推送给的APP名称
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "push/pushMessage.do",method ={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> pushMessage(HttpServletRequest request) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		String user_code = request.getParameter("user_code");
		String msg_context = request.getParameter("msg_context");
		String extras = request.getParameter("extras");
		Map<String, Object> map = null;
//		Map<String, Object> msg_map = null;
		ObjectMapper mapper = new ObjectMapper();
		if(StringUtil.isNotBlank(extras)){
			map = mapper.readValue(extras, Map.class);
			map.put("accept_code", user_code);
		}
//		if(StringUtil.isNotBlank(msg_context)){
//			msg_map = mapper.readValue(msg_context, Map.class);
//		}
//		String msg_code = request.getParameter("msg_code");
		String app_name = request.getParameter("app_name");
		String alias = taskMessageService.findAliasByCode(app_name, user_code);
		if(StringUtil.isBlank(alias)){
			result.put("flag", false);
			result.put("message", "该用户没有推送名称");
		}else{
			PushManage.pushMessageByMsgContext(app_name,msg_context, alias, null, map);
//			PushManage.pushMessage(alias, msg_code, msg_map, map);
			result.put("flag", true);
		}
		return result;
	}
}
