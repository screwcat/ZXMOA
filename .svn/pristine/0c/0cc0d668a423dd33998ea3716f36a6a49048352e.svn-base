package com.zx.moa.ioa.task.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.task.persist.TaskListDao;
import com.zx.moa.ioa.task.persist.TaskMessageDao;
import com.zx.moa.ioa.task.service.ITaskListService;
import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.ioa.task.vo.TaskListVO;
import com.zx.moa.ioa.task.vo.TaskMessageVO;
import com.zx.moa.ioa.task.vo.TaskVO;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.ioa.util.PushManage;
import com.zx.moa.ioa.util.QuartzJobManage;
import com.zx.moa.ioa.util.TaskButtonUtil;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: TaskListServiceImpl
 * 模块名称：任务ServiceImpl
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2016年12月9日
 * @version V1.7.7
 * history:
 * 1、2016年12月9日 zhaowei 创建文件
 */
@Service("taskListService")
public class TaskListServiceImpl implements ITaskListService {

	@Autowired
	private TaskListDao taskListDao;

	static ObjectMapper objectMapper;

	@Autowired
	private TaskMessageDao taskMessageDao;
	@Autowired
	private QuartzJobManage quartzJobManage;

	@Autowired
	private ITaskMessageService taskMessageService;

    // 获取任务列表
	@Override
	public List<Map<String, Object>> getTaskList(HttpServletRequest request,
			TaskListVO taskvo) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PmPersonnel pm = (PmPersonnel) session
				.getAttribute(GlobalVal.USER_SESSION);
		taskvo.setPersonnel_id(pm.getPersonnel_id());
		List<Map<String, Object>> reslist = null;
        // ------------------查询子任务-----------------
		if (taskvo.getTask_id() != null && taskvo.getTask_id() != 0) {
			reslist = taskListDao.getChildenTaskList(taskvo);
		} else {
			reslist = taskListDao.getTaskListByCondition(taskvo);
		}

		return reslist;
	}

    // 获取任务反馈详情
	@Override
	public Map<String, Object> getTaskFeedbackList(HttpServletRequest request,
			TaskVO task) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		PmPersonnel pm = (PmPersonnel) session
				.getAttribute(GlobalVal.USER_SESSION);
        // 更新已读
		String tid = task.getTask_id().toString();
		taskMessageService.taskMessageRead(tid, null, pm.getPersonnel_id());

        // 基本信息
		resMap = taskListDao.getTaskBaseInfo(task.getTask_id());
		resMap.put("user_id", pm.getPersonnel_id());
		resMap.put("personnel_name", pm.getPersonnel_name());
        // 详细信息
		List<Map<String, Object>> info = taskListDao.getTaskInfo(resMap);
        for (Map<String, Object> map : info)
        { // 拼接绝对路径
			int type = Integer.parseInt(map.get("info_type").toString());
			if (type != 1) {
				String temp = map.get("task_info").toString();
				String newtemp = HttpClientUtil.getSysUrl("moaUrl").toString()
						+ temp;
				map.put("task_info", newtemp);
				map.put("task_info", map.get("task_info").toString());

			}
			if(map.get("info_date")==null){
			    map.put("info_date", "");
			}
		}

        // 反馈信息
		List<Map<String, Object>> message = taskListDao.getFeedbackInfo(task
				.getTask_id());
        for (Map<String, Object> map : message)
        {// 拼接绝对路径
			int type = Integer
					.parseInt(map.get("message_info_type").toString());
			if (type == 2 || type == 3) {
				String temp = map.get("message_info").toString();
				String newtemp = HttpClientUtil.getSysUrl("moaUrl").toString()
						+ temp;
				map.put("message_info", newtemp);
			}
		}

		int user = 0;
		int task_status = Integer
				.parseInt(resMap.get("task_status").toString());
		String is_feedback = resMap.get("is_feedback").toString();
		int accept_feedback_id = Integer.parseInt(resMap.get(
				"accept_feedback_id").toString());
		int Personnel_id = pm.getPersonnel_id();
		int feedback_user_id = Integer.parseInt(resMap.get("feedback_user_id")
				.toString());
		Integer create_id = Integer.parseInt(resMap.get("create_id")
		                                     .toString());
        // 判断登录人是否为反馈人id 返回1 其余返回3
        // 如果当前登录人id等于接受反馈人ID，user等于被反馈人id （2），否则为反馈人（1）,都不等于返回3
		if (accept_feedback_id == Personnel_id) {
			user = 2;
		} else if (feedback_user_id == Personnel_id) {
			user = 1;
		}
		else if (accept_feedback_id != Personnel_id
				&& feedback_user_id != Personnel_id) {
			user = 3;
		}
		boolean isdelay;
        // 按钮信息
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("task_id", tid);
		Map<String, Object> rmap = taskListDao.getTaskIsDelay(pmap);
		int delay = Integer.parseInt(rmap.get("delay").toString());
		if (delay > 0) {
			isdelay = true;
		} else {
			isdelay = false;
		}

		List<Map<String, Object>> buttons;
        // 是否查看子任务界面
		int is_readonly = 0;
		if (task.getIs_readonly() == null) {
			is_readonly = 0;
		} else {
			is_readonly = task.getIs_readonly();
		}
		buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback,
				isdelay, is_readonly);

        // task_appoint（String）XX指派给XX
		String task_appoint = resMap.get("publish_title").toString();

		
		
		
		resMap.put("info", info);
		resMap.put("message", message);
		resMap.put("buttons", buttons);
		resMap.put("task_appoint", task_appoint);

        // 删除多余信息
		resMap.remove("task_num");
		resMap.remove("is_feedback");
		resMap.remove("feedback_type");
		resMap.remove("feedback_user_name");
		return resMap;
	}
	
	                                            /**
    * 
    * @Title: getMiniUrl
    * @Description: 处理缩略图url
    * @param url
    * @return String
    * @throws
    * @author zhangmingjian
    * @date 2016年9月22日 上午9:38:34
    */
    private String getMiniUrl(String[] url) {
        String old_url = url[0];
        String[] parameter = StringUtils.split(url[1], "=");
        String[] miniurl = StringUtils.split(old_url, "/");
        // 缩略图片地址
        miniurl[0] = parameter[1];
        String resultUrl = "/";
        for (int i = 0; i < miniurl.length; i++) {
            resultUrl = resultUrl + miniurl[i] + "/";
        }
        resultUrl = resultUrl.substring(0, resultUrl.length() - 1);
        return resultUrl;
    }

    // 发送消息
	@Override
	public Map<String, Object> sendTaskFeedbackList(HttpServletRequest request,
			TaskMessageVO message) {
		int message_info_type = message.getMessage_info_type();
		String message_info = message.getMessage_info();
		if(message_info_type==3){
		    int index = message_info.indexOf("?");
            if (index != -1) {
                String[] url = StringUtils.split(message_info, "?");
                message_info = message_info.substring(0,index);
                message.setMessage_info(message_info);
                message.setMessage_info_mini(getMiniUrl(url));
            } 
		}
		 
		Map<String, Object> result = null;
		Map<String, Object> sendMessageParam = new HashMap<String, Object>();
		Map<String, Object> msgMap = new HashMap<String, Object>();
		Map<String, Object> extras = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		PmPersonnel pm = (PmPersonnel) session
				.getAttribute(GlobalVal.USER_SESSION);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		sendMessageParam.put("task_id", message.getTask_id());
		sendMessageParam.put("message_info_type",
				message.getMessage_info_type());
		sendMessageParam.put("message_info", message.getMessage_info());
		sendMessageParam.put("message_date", message.getMessage_date());
		sendMessageParam.put("create_id", pm.getPersonnel_id());
		sendMessageParam.put("create_user_name", pm.getPersonnel_name());
		sendMessageParam.put("create_user_code", pm.getPersonnel_shortcode());
		sendMessageParam.put("create_datetime", time);
		sendMessageParam.put("message_type", 1);
		sendMessageParam.put("message_info_mini", message.getMessage_info_mini());
        // 发送消息自动验收的标识,如果符合条件，则ioa会进行自动验收，并且结束时间为当前时间
        sendMessageParam.put("autoCheckType", 1);
		String sendMessageParams;
		sendMessageParams = toJSon(sendMessageParam);
		try {

			result = getModiConnection("IOA_OUT_007", sendMessageParams);
            // 推送
			String shortCode;
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("task_id", message.getTask_id());
			Map<String, Object> pushpar = taskListDao
					.getTaskInfoById(paramsMap);
			extras.put("task_id", message.getTask_id());

			extras.put("task_message_id", result.get("task_message_id")
					.toString());
			extras.put("message_type", 1);
			extras.put("message_info_type", message.getMessage_info_type());
			extras.put("create_datetime", time);
			if (message.getMessage_info_type() == 2
					|| message.getMessage_info_type() == 3) {
				String newmessage = HttpClientUtil.getSysUrl("moaUrl")
						.toString() + message.getMessage_info();
				extras.put("message_info", newmessage);
				if(message.getMessage_info_type() == 3){
				    extras.put("message_info_mini", HttpClientUtil.getSysUrl("moaUrl")
		                        .toString() + message.getMessage_info_mini());
				}else{
				    extras.put("message_info_mini", "");
				}
			} else {
				extras.put("message_info", message.getMessage_info());
				extras.put("message_info_mini", "");
			}

			extras.put("message_date", message.getMessage_date());
			extras.put("create_id", pm.getPersonnel_id());
			extras.put("create_user_name", pm.getPersonnel_name());
			int accept_feedback_id = Integer.parseInt(pushpar.get(
					"accept_feedback_id").toString());
			String accept_feedback_name = pushpar.get("accept_feedback_name")
					.toString();

			int feedback_user_id = Integer.parseInt(pushpar.get(
					"feedback_user_id").toString());
			String feedback_user_name = pushpar.get("feedback_user_name")
					.toString();
			msgMap.put("personnel_name", pm.getPersonnel_name());
			msgMap.put("message_info_type", message.getMessage_info_type());
			msgMap.put("message_info", message.getMessage_info());
			msgMap.put("personnel_shortCode", pm.getPersonnel_shortcode());
			if (pm.getPersonnel_id() == accept_feedback_id) {
                // 给反馈人推送消息
				shortCode = pushpar.get("feedback_user_code").toString();
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_FEEDBACK_MESSAGE, msgMap, extras);
			} else if (pm.getPersonnel_id() == feedback_user_id) {
                // 给被反馈人推送消息
				shortCode = pushpar.get("accept_feedback_code").toString();
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_PUBLISH_MESSAGE, msgMap, extras);
			}
			if (message.getMessage_info_type() == 2
					|| message.getMessage_info_type() == 11) {
				result.put("message_date", message.getMessage_date());
			} else {
				result.put("message_date", null);
			}
			result.put("create_datetime", time);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

    // 发送modi请求
	private Map<String, Object> getModiConnection(String interfaceNumber,
			String params) throws ClientProtocolException {
		String url = HttpClientUtil.getSysUrl("nozzleUrl");
		// String url =
		// "http://192.168.1.52:7080/IOA/moa/sendFeedbackMessage.do";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("interface_num", interfaceNumber));
		nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
		nvps.add(new BasicNameValuePair("sendMessageParams", params));
		nvps.add(new BasicNameValuePair("user_id", "0"));

		Map<String, Object> resmap = new HashMap<String, Object>();

		try {
			resmap = HttpClientUtil.post(url, nvps, Map.class);
			return resmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

    // 验收，重办，提前结束，撤销，完成
	@Override
	public Map<String, Object> CheckAndAgain(HttpServletRequest request,
			Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		paramsMap.put("update_user",
				pm.getPersonnel_name() + pm.getPersonnel_shortcode());
		paramsMap.put("update_datetime", time);
        // 发送消息参数
		paramsMap.put("create_id", pm.getPersonnel_id());
		paramsMap.put("create_user_name", pm.getPersonnel_name());
		paramsMap.put("create_user_code", pm.getPersonnel_shortcode());
		paramsMap.put("create_datetime", time);
		paramsMap.put("pid", pm.getPersonnel_id());

		Map<String, Object> msgMap = new HashMap<String, Object>();
		Map<String, Object> extras = new HashMap<String, Object>();
		Map<String, Object> jobMap = new HashMap<String, Object>();
		Integer type = (Integer) paramsMap.get("type");
		Integer task_id = (Integer) paramsMap.get("task_id");
        // 完成时验收标识，传到IOA后符合条件的会自动验收，并且此标识不为空的时候，结束时间等于当前时间
        paramsMap.put("autoCheckType", 1);

		String checkAndAgainParams;
		// checkAndAgainParams = objectMapper.writeValueAsString(paramsMap);
		checkAndAgainParams = toJSon(paramsMap);

        String url = HttpClientUtil.getSysUrl("nozzleUrl");
        // String url = "http://10.10.2.33:7080/IOA/moa/checkAndAgain.do";

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_008"));
		nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
		nvps.add(new BasicNameValuePair("checkAndAgainParams",
				checkAndAgainParams));
		nvps.add(new BasicNameValuePair("user_id", "0"));
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			resmap = HttpClientUtil.post(url, nvps, Map.class);
			if (!(boolean) resmap.get("flag")) {
				return resmap;
			}
			Map<String, Object> pushpar = taskListDao
					.getTaskInfoById(paramsMap);
            // 判断登陆人身份，如果是创建人personnelflag = false
			Boolean personnelflag = true;
			if(!pushpar.get("feedback_user_code").equals(pm.getPersonnel_shortcode()) &&
			        !pushpar.get("accept_feedback_code").equals(pm.getPersonnel_shortcode())&&
			        pushpar.get("create_user_code").equals(pm.getPersonnel_shortcode())
			        ){
			    personnelflag = false;
			}
			
			
            // 推送消息
            // 1验收，2重办，3提前结束，4撤销，5完成
			if (type == 1) {
				// job
				String remind_time = pushpar.get("remind_time").toString();
				String finish_time = pushpar.get("finish_time").toString();
				jobMap.put("remind_time", remind_time);
				jobMap.put("finish_time", finish_time);
				jobMap.put("task_id", task_id);
				taskMessageService.taskEndState(jobMap);

                // 给反馈人推送消息
				extras.put("task_id", task_id);

                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);
                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				// int user = Integer.parseInt(resmap.get("user").toString());
				int user = 1;
				boolean isdeyal = (boolean) resmap.get("isdeyal");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdeyal);
                // 推送附加提示短语
				Map<String, Object> pinjie = taskListDao
						.getTaskBaseInfo(task_id);

				String reminder = pinjie.get("accept_feedback_name").toString()
						+ pinjie.get("accept_feedback_code") + ":"
 + "恭喜您，此任务已成功被验收";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);
				String shortCode = pushpar.get("feedback_user_code").toString();
				msgMap.put("personnel_name", pinjie.get("accept_feedback_name")
						.toString() + pinjie.get("accept_feedback_code"));
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_FEEDBACK_PASS, msgMap, extras);

				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
			} else if (type == 2) {
                // 推送
				extras.put("task_id", task_id);
                // 附加消息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);
                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				int user = 1;
				boolean isdeyal = (boolean) resmap.get("isdeyal");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdeyal);
                // 推送附加提示短语
				Map<String, Object> pinjie = taskListDao
						.getTaskBaseInfo(task_id);
				String reminder = pinjie.get("accept_feedback_name").toString()
						+ pinjie.get("accept_feedback_code").toString() + ":"
 + "非常抱歉，此任务需要继续执行，请您再接再厉";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);
				String shortCode = pushpar.get("feedback_user_code").toString();
				msgMap.put("personnel_name", pinjie.get("accept_feedback_name")
						.toString()
						+ pinjie.get("accept_feedback_code").toString());
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_FEEDBACK_REDO, msgMap, extras);
				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
			} else if (type == 5) {
                // 推送
				extras.put("task_id", task_id);
				String shortCode = pushpar.get("accept_feedback_code")
						.toString();
				String personnel_name = pushpar.get("feedback_user_name")
						.toString();
				msgMap.put("personnel_name",
						personnel_name
								+ pushpar.get("feedback_user_code").toString());
                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);
                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				int user = 2;
				boolean isdeyal = (boolean) resmap.get("isdeyal");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdeyal);
                // 推送附加提示短语
				String name = resmap.get("fbname").toString();
				String code = resmap.get("fbcode").toString();
                String reminder = name + code + "的任务需要您验收";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_PUBLISH_WAIT_CONFIRM, msgMap, extras);

				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
			} else if (type == 3) {
				// job
				String remind_time = pushpar.get("remind_time").toString();
				String finish_time = pushpar.get("finish_time").toString();
				jobMap.put("remind_time", remind_time);
				jobMap.put("finish_time", finish_time);
				jobMap.put("task_id", task_id);
				taskMessageService.taskEndState(jobMap);

                // 推送
				extras.put("task_id", task_id);
				String shortCode = pushpar.get("feedback_user_code").toString();
				String personnel_name = pushpar.get("accept_feedback_name")
						.toString();
				msgMap.put("personnel_name",
						personnel_name
								+ pushpar.get("accept_feedback_code")
										.toString());

                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);

                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				int user = 1;
				boolean isdeyal = (boolean) resmap.get("isdeyal");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdeyal);
                // 推送附加提示短语
				String name = resmap.get("publish_user_name").toString();
				String code = resmap.get("publish_user_code").toString();
                String reminder = name + code + ":" + "提前结束任务";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);
                // 如果登陆人为创建人，不推送消息
				   if(personnelflag){
				       PushManage.pushMessageByCode(shortCode,
				                                    PushConstant.TASK_ADVANCE, msgMap, extras);
				   }

				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
			} else if (type == 4) {
				// job
				String remind_time = pushpar.get("remind_time").toString();
				String finish_time = pushpar.get("finish_time").toString();
				jobMap.put("remind_time", remind_time);
				jobMap.put("finish_time", finish_time);
				jobMap.put("task_id", task_id);
				taskMessageService.taskEndState(jobMap);

                // 推送
				extras.put("task_id", task_id);
				String shortCode = pushpar.get("feedback_user_code").toString();
				String personnel_name = pushpar.get("accept_feedback_name")
						.toString();
				msgMap.put("personnel_name",
						personnel_name
								+ pushpar.get("accept_feedback_code")
										.toString());

                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);
                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				// int user = Integer.parseInt(resmap.get("user").toString());
				int user = 1;
				boolean isdeyal = (boolean) resmap.get("isdeyal");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdeyal);
                // 推送附加提示短语
				String name = resmap.get("publish_user_name").toString();
				String code = resmap.get("publish_user_code").toString();
                String reminder = name + code + ":" + "撤销任务";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);
                // 如果登陆人为创建人，不推送消息
				 if(personnelflag){
				     PushManage.pushMessageByCode(shortCode,
				                                  PushConstant.TASK_REVOKE, msgMap, extras);
				 }

				// resmap.put("task_message_id",
				// Integer.parseInt(resmap.get("task_message_id").toString()));
				// resmap.put("message_info",
				// resmap.get("message_info").toString());
				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
			}
			// try {
			// resmap.remove("user");
			// resmap.remove("is_feedback");
			// } catch (Exception e) {
			// // TODO: handle exception
			// }
			return resmap;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

    // ------------------------------------

    /**
     * @Title: autoCheck
     * @Description: TODO(自动验收)
     * @param paramsMap
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月10日 下午12:58:33
     * @see com.zx.moa.ioa.task.service.ITaskListService#autoCheck(java.util.Map)
     * history:
     * 1、2016年12月10日 Lixiaolong 创建方法
     */
    @Override
    public Map<String, Object> autoCheck(Map<String, Object> paramsMap)
    {
        // TODO Auto-generated method stub
        // HttpSession session = request.getSession();
        // PmPersonnel pm = (PmPersonnel)
        // session.getAttribute(GlobalVal.USER_SESSION);
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        paramsMap.put("update_user", null);
        paramsMap.put("update_datetime", time);
        // 发送消息参数
        paramsMap.put("create_id", null);
        paramsMap.put("create_user_name", null);
        paramsMap.put("create_user_code", null);
        paramsMap.put("create_datetime", time);

        Map<String, Object> msgMap = new HashMap<String, Object>();
        Map<String, Object> extras = new HashMap<String, Object>();
        Map<String, Object> jobMap = new HashMap<String, Object>();
        Integer type = (Integer) paramsMap.get("type");
        Integer task_id = (Integer) paramsMap.get("task_id");
        String checkAndAgainParams;
        // checkAndAgainParams = objectMapper.writeValueAsString(paramsMap);
        checkAndAgainParams = toJSon(paramsMap);

        String url = HttpClientUtil.getSysUrl("nozzleUrl");
        // String url = "http://192.168.1.52:7080/IOA/moa/checkAndAgain.do";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_008"));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        nvps.add(new BasicNameValuePair("checkAndAgainParams", checkAndAgainParams));
        nvps.add(new BasicNameValuePair("user_id", "0"));
        Map<String, Object> resmap = new HashMap<String, Object>();
        try
        {
            resmap = HttpClientUtil.post(url, nvps, Map.class);
            if (!(boolean) resmap.get("flag"))
            {
                return resmap;
            }
            Map<String, Object> pushpar = taskListDao.getTaskInfoById(paramsMap);
            // 推送消息 暂时取消
            // 6.自动验收
            // if (type == 1)
            // {
            // // job
            // String remind_time = pushpar.get("remind_time").toString();
            // String finish_time = pushpar.get("finish_time").toString();
            // jobMap.put("remind_time", remind_time);
            // jobMap.put("finish_time", finish_time);
            // jobMap.put("task_id", task_id);
            // taskMessageService.taskEndState(jobMap);
            //
            // // 给反馈人推送消息
            // extras.put("task_id", task_id);
            //
            // // 附加信息
            // extras.put("task_message_id",
            // Integer.parseInt(resmap.get("task_message_id").toString()));
            // extras.put("message_info",
            // resmap.get("message_info").toString());
            // extras.put("message_type", 2);
            // extras.put("message_info_type", 1);
            // extras.put("message_date", null);
            // extras.put("create_id", pm.getPersonnel_id());
            // extras.put("create_user_name", pm.getPersonnel_name());
            // extras.put("create_user_code", pm.getPersonnel_shortcode());
            // extras.put("create_datetime", time);
            // // 推送附加按钮信息
            // int task_status =
            // Integer.parseInt(resmap.get("task_status").toString());
            // String is_feedback = resmap.get("is_feedback").toString();
            // // int user = Integer.parseInt(resmap.get("user").toString());
            // int user = 1;
            // boolean isdeyal = (boolean) resmap.get("isdeyal");
            // List<Map<String, Object>> buttons =
            // TaskButtonUtil.getButtons(task_status, user, is_feedback,
            // isdeyal);
            // // 推送附加提示短语
            // Map<String, Object> pinjie =
            // taskListDao.getTaskBaseInfo(task_id);
            //
            // String reminder = pinjie.get("accept_feedback_name").toString() +
            // pinjie.get("accept_feedback_code") + ":" + "恭喜您，此任务已成功被验收";
            // extras.put("buttons", buttons);
            // extras.put("reminder", reminder);
            // String shortCode = pushpar.get("feedback_user_code").toString();
            // msgMap.put("personnel_name",
            // pinjie.get("accept_feedback_name").toString() +
            // pinjie.get("accept_feedback_code"));
            // PushManage.pushMessageByCode(shortCode,
            // PushConstant.TASK_FEEDBACK_PASS, msgMap, extras);
            //
            // // resmap.put("task_message_id",
            // // Integer.parseInt(resmap.get("task_message_id").toString()));
            // // resmap.put("message_info",
            // // resmap.get("message_info").toString());
            // resmap.put("message_type", 2);
            // resmap.put("message_info_type", 1);
            // resmap.put("message_date", null);
            // resmap.put("create_id", pm.getPersonnel_id());
            // resmap.put("create_user_name", pm.getPersonnel_name());
            // resmap.put("create_user_code", pm.getPersonnel_shortcode());
            // resmap.put("create_datetime", time);
            // }

            // try {
            // resmap.remove("user");
            // resmap.remove("is_feedback");
            // } catch (Exception e) {
            // // TODO: handle exception
            // }
            return resmap;

        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    // ------------------------------------

    // 申请延期和确认延期
	@Override
	public Map<String, Object> dealTaskFinishTime(HttpServletRequest request,
			Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PmPersonnel pm = (PmPersonnel) session
				.getAttribute(GlobalVal.USER_SESSION);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		paramsMap.put("create_id", pm.getPersonnel_id());
		paramsMap.put("create_user_name", pm.getPersonnel_name());
		paramsMap.put("create_user_code", pm.getPersonnel_shortcode());
		paramsMap.put("create_datetime", time);
		paramsMap.put("nameAndCode",
				pm.getPersonnel_name() + pm.getPersonnel_shortcode());
		paramsMap.put("pid", pm.getPersonnel_id());

		int type = Integer.parseInt(paramsMap.get("type").toString());
		Map<String, Object> jobMap = new HashMap<String, Object>();
		Map<String, Object> msgMap = new HashMap<String, Object>();
		Map<String, Object> extras = new HashMap<String, Object>();
		String dealTaskParams;
		dealTaskParams = toJSon(paramsMap);

		String url = HttpClientUtil.getSysUrl("nozzleUrl");
		// String url = "http://192.168.1.52:7080/IOA/moa/applyDelayTask.do";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_009"));
		nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
		nvps.add(new BasicNameValuePair("dealTaskParams", dealTaskParams));
		nvps.add(new BasicNameValuePair("user_id", "0"));
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			resmap = HttpClientUtil.post(url, nvps, Map.class);
			if (!(boolean) resmap.get("flag")) {
				return resmap;
			}
            // 推送消息
            // 判断是否为确认延期

			Map<String, Object> pushpar = taskListDao
					.getTaskInfoById(paramsMap);
            int task_type_id = Integer.parseInt(pushpar.get("task_type_id") == null ? "1" : pushpar.get("task_type_id").toString());
			if (type == 1) {

				String shortCode = pushpar.get("accept_feedback_code")
						.toString();
				String finish_time = paramsMap.get("finish_time").toString();
				msgMap.put("personnel_name",
						pm.getPersonnel_name() + pm.getPersonnel_shortcode());
				msgMap.put("finish_time", finish_time);
				Integer task_id = (Integer) pushpar.get("task_id");
				extras.put("task_id", task_id);
                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 11);
				extras.put("message_date", finish_time);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);
                // 推送附加提示短语
				String name = pm.getPersonnel_name();
				String code = pm.getPersonnel_shortcode();
				String messagetime = paramsMap.get("finish_time").toString();
                String reminder = name + code + "申请延期截止时间到" + messagetime + "。";
				extras.put("reminder", reminder);

				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_PUBLISH_APPLY_DELAY, msgMap, extras);
				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
				resmap.put("message_date", finish_time);
			} else if (type == 2) {

				String remind_time = pushpar.get("remind_time").toString();
				String finish_time = pushpar.get("finish_time").toString();
				Integer task_id = (Integer) pushpar.get("task_id");
				jobMap.put("remind_time", remind_time);
				jobMap.put("finish_time", finish_time);
				jobMap.put("task_id", task_id);
                if (task_type_id == 1)
                {
				taskMessageService.taskUnfinishedRemind(jobMap);
                }
                if (task_type_id == 2 || task_type_id == 3)
                {
                taskMessageService.taskUnfinishedOver(jobMap);
                }
                // 被推送人短工号
				String shortCode = pushpar.get("feedback_user_code").toString();
				msgMap.put("personnel_name", resmap.get("publish_user_name")
						.toString()
						+ resmap.get("publish_user_code").toString());
				msgMap.put("finish_time", finish_time);
				extras.put("task_id", task_id);
                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", finish_time);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);

                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				// int user = Integer.parseInt(resmap.get("user").toString());
				int user = 1;
				boolean isdelay = (boolean) resmap.get("isdelay");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdelay);
                // 推送附加提示短语
				String name = resmap.get("publish_user_name").toString();
				String code = resmap.get("publish_user_code").toString();
				String messagetime = paramsMap.get("finish_time").toString();
                String reminder = name + code + "任务的截止时间已经更改为" + messagetime + "。";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);

				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_FEEDBACK_DELAY, msgMap, extras);

				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
				resmap.put("message_date", finish_time);
			} else if (type == 3) {

				String remind_time = pushpar.get("remind_time").toString();
				String finish_time = pushpar.get("finish_time").toString();
				Integer task_id = (Integer) pushpar.get("task_id");
				jobMap.put("remind_time", remind_time);
				jobMap.put("finish_time", finish_time);
				jobMap.put("task_id", task_id);
                if (task_type_id == 1)
                {
                    taskMessageService.taskUnfinishedRemind(jobMap);
                }
                if (task_type_id == 2 || task_type_id == 3)
                {
                taskMessageService.taskUnfinishedOver(jobMap);
                }
                // 被推送人短工号
				String shortCode = pushpar.get("feedback_user_code").toString();
				msgMap.put("personnel_name", resmap.get("publish_user_name")
						.toString()
						+ resmap.get("publish_user_code").toString());
				msgMap.put("finish_time", finish_time);
				extras.put("task_id", task_id);
                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", finish_time);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);

                // 推送附加按钮信息
				int task_status = Integer.parseInt(resmap.get("task_status")
						.toString());
				String is_feedback = resmap.get("is_feedback").toString();
				// int user = Integer.parseInt(resmap.get("user").toString());
				int user = 1;
				boolean isdelay = (boolean) resmap.get("isdelay");
				List<Map<String, Object>> buttons = TaskButtonUtil.getButtons(
						task_status, user, is_feedback, isdelay);
                // 推送附加提示短语
				String name = resmap.get("publish_user_name").toString();
				String code = resmap.get("publish_user_code").toString();
				String messagetime = paramsMap.get("finish_time").toString();
                String reminder = name + code + "任务的截止时间已经更改为" + messagetime + "。";
				extras.put("buttons", buttons);
				extras.put("reminder", reminder);

				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_FEEDBACK_DELAY, msgMap, extras);

				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
				resmap.put("message_date", finish_time);
			}
			return resmap;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

    // 转移协同任务
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> changeTaskFeedback(HttpServletRequest request,
			Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PmPersonnel pm = (PmPersonnel) session
				.getAttribute(GlobalVal.USER_SESSION);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		paramsMap.put("create_id", pm.getPersonnel_id());
		paramsMap.put("create_user_name", pm.getPersonnel_name());
		paramsMap.put("create_user_code", pm.getPersonnel_shortcode());
		paramsMap.put("nameAndCode",
				pm.getPersonnel_name() + pm.getPersonnel_shortcode());
		paramsMap.put("create_datetime", time);
		paramsMap.put("pid", pm.getPersonnel_id());
		Map<String, Object> jobMap = new HashMap<String, Object>();
		Map<String, Object> msgMap = new HashMap<String, Object>();
		Map<String, Object> extras = new HashMap<String, Object>();
		List<Map<String, Object>> resMessageList = new ArrayList<Map<String, Object>>();
		;
		Integer type = (Integer) paramsMap.get("type");
		Integer task_id = (Integer) paramsMap.get("task_id");
        // put原任务id
		paramsMap.put("oldTask_id", task_id);
		List<Map<String, Object>> list = (List<Map<String, Object>>) paramsMap
				.get("feedback_usres");
		Map<String, Object> pushpar = taskListDao.getTaskInfoById(paramsMap);
        // 原任务反馈人
		String personnel_name = pushpar.get("feedback_user_name").toString();
		String dealTaskParams;
		dealTaskParams = toJSon(paramsMap);
		String url = HttpClientUtil.getSysUrl("nozzleUrl");
		// String url =
		// "http://192.168.1.52:7080/IOA/moa/transferAndCollaborative.do";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_010"));
		nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
		nvps.add(new BasicNameValuePair("dealTaskParams", dealTaskParams));
		nvps.add(new BasicNameValuePair("user_id", "0"));
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			resmap = HttpClientUtil.post(url, nvps, Map.class);
			if (!(boolean) resmap.get("flag")) {
				return resmap;
			}
            // 给被转移人推送消息
            if (type == 1)
            { // 转移
				extras.put("task_id", task_id);
                // 附加信息
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);

				String shortCode = list.get(0).get("feedback_user_code")
						.toString();
				String name = list.get(0).get("feedback_user_name").toString();

				// --------------------------------------------------------------
				String accept_feedback_name = null;
                // 根据num取pid直到为0的任务
				int task_pid = Integer.parseInt(paramsMap.get("task_id")
						.toString());
				Map<String, Object> pinjiemap = new HashMap<String, Object>();
				if (!(task_pid == 0)) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					while (true) {
						map1 = taskListDao.getTaskBaseInfo(task_pid);
						task_pid = (int) map1.get("task_pid");
						if (task_pid == 0) {
							break;
						}
					}
					pinjiemap.put("task_id", map1.get("task_id").toString());
				} else {
					pinjiemap.put("task_id", Integer.parseInt(paramsMap.get(
							"task_id").toString()));

				}
				List<Map<String, Object>> pinjielist = taskListDao
						.getQuondamFeedbackUser(pinjiemap);

				accept_feedback_name = pinjielist.get(0)
						.get("accept_feedback_name").toString();

				msgMap.put("personnel_name",
						personnel_name
								+ pushpar.get("feedback_user_code").toString());
				msgMap.put("accept_feedback_name", accept_feedback_name
						+ pinjielist.get(0).get("accept_feedback_code")
								.toString());

				// ---------------------------------------------------------------

                // 推送附加提示短语
				String publish_user_name = resmap.get("publish_user_name")
						.toString();
				String publish_user_code = resmap.get("publish_user_code")
						.toString();
				String reminder = pm.getPersonnel_name()
 + pm.getPersonnel_shortcode() + "将" + publish_user_name + publish_user_code + "发布的任务转移给您。";
				extras.put("reminder", reminder);

				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_FEEDBACK_TRANSFER, msgMap, extras);

                // 给任务发布人推送消息
				shortCode = pinjielist.get(0).get("accept_feedback_code")
						.toString();
				msgMap.clear();
				List<Map<String, Object>> listmap = (List<Map<String, Object>>) paramsMap
						.get("feedback_usres");
				msgMap.put("personnel_name",
						pm.getPersonnel_name() + pm.getPersonnel_shortcode());
				msgMap.put("name", listmap.get(0).get("feedback_user_name")
						.toString()
						+ listmap.get(0).get("feedback_user_code").toString());

				extras.put("task_id", task_id);
				extras.put("task_message_id", Integer.parseInt(resmap.get(
						"task_message_id").toString()));
				extras.put("message_info", resmap.get("message_info")
						.toString());
				extras.put("message_type", 2);
				extras.put("message_info_type", 1);
				extras.put("message_date", null);
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", time);
				reminder = pm.getPersonnel_name() + pm.getPersonnel_shortcode()
 + "将任务转移给"
						+ listmap.get(0).get("feedback_user_name").toString()
						+ listmap.get(0).get("feedback_user_code").toString();
				extras.put("reminder", reminder);
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_PUBLISH_TRANSFER, msgMap, extras);

				// ------------------------------------------------------

				resmap.put("message_type", 2);
				resmap.put("message_info_type", 1);
				resmap.put("message_date", null);
				resmap.put("create_id", pm.getPersonnel_id());
				resmap.put("create_user_name", pm.getPersonnel_name());
				resmap.put("create_user_code", pm.getPersonnel_shortcode());
				resmap.put("create_datetime", time);
            }
            else if (type == 2)
            { // 协同
              // 获取发布人信息，用于拼接提示语

                // 根据num取pid直到为0的任务
				int task_pid = Integer.parseInt(paramsMap.get("task_id")
						.toString());
				Map<String, Object> pinjiemap = new HashMap<String, Object>();
				if (!(task_pid == 0)) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					while (true) {
						map1 = taskListDao.getTaskBaseInfo(task_pid);
						task_pid = (int) map1.get("task_pid");
						if (task_pid == 0) {
							break;
						}
					}
					pinjiemap.put("task_id", map1.get("task_id").toString());
				} else {
					pinjiemap.put("task_id", Integer.parseInt(paramsMap.get(
							"task_id").toString()));

				}
				List<Map<String, Object>> pinjielist = taskListDao
						.getQuondamFeedbackUser(pinjiemap);

				String accept_feedback_name = pinjielist.get(0)
						.get("accept_feedback_name").toString();
				String accept_feedback_code = pinjielist.get(0)
						.get("accept_feedback_code").toString();

				// extras.put("task_id",task_id);
				extras.put("task_message_id", resmap.get("task_message_id"));
				extras.put("message_info", resmap.get("message_info"));
				extras.put("message_type", resmap.get("message_type"));
				extras.put("message_info_type", resmap.get("message_info_type"));
				extras.put("message_date", resmap.get("message_date"));
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", resmap.get("create_datetime"));
                // 推送附加提示短语
				String publish_user_name = resmap.get("publish_user_name")
						.toString();
				String publish_user_code = resmap.get("publish_user_code")
						.toString();
				// String reminder =
                // pm.getPersonnel_name()+pm.getPersonnel_shortcode()+"将"+accept_feedback_name+accept_feedback_code
                // +"发布的任务转移给您。" ;
				String reminder = pm.getPersonnel_name()
 + pm.getPersonnel_shortcode() + "邀请您协同" + accept_feedback_name + accept_feedback_code + "发布的任务";
				extras.put("reminder", reminder);

				msgMap.put("personnel_name",
						pm.getPersonnel_name() + pm.getPersonnel_shortcode());
				msgMap.put("accept_feedback_name", accept_feedback_name
						+ accept_feedback_code);
				for (int i = 0; i < list.size(); i++) {
					String shortCode = list.get(i).get("feedback_user_code")
							.toString();
					List<String> ids = (List<String>) resmap.get("ids");
					extras.put("task_id", ids.get(i));
					PushManage.pushMessageByCode(shortCode,
							PushConstant.TASK_FEEDBACK_SYNERGY, msgMap, extras);
				}

                // 给发布人推送信息
				String shortCode = accept_feedback_code;
				msgMap.clear();

				String name = "";
				List<Map<String, Object>> listmap = (List<Map<String, Object>>) paramsMap
						.get("feedback_usres");
				for (int i = 0; i < listmap.size(); i++) {
					if ((i + 1) == listmap.size()) {
						name += listmap.get(i).get("feedback_user_name")
								.toString()
								+ listmap.get(i).get("feedback_user_code")
.toString() + "。";
						break;
					}
					name += listmap.get(i).get("feedback_user_name").toString()
							+ listmap.get(i).get("feedback_user_code")
									.toString() + ",";
				}
				msgMap.put("personnel_name",
						pm.getPersonnel_name() + pm.getPersonnel_shortcode());
				msgMap.put("name", name);
				extras.put("task_id", task_id);
				extras.put("task_message_id", resmap.get("task_message_id"));
				extras.put("message_info", resmap.get("message_info"));
				extras.put("message_type", resmap.get("message_type"));
				extras.put("message_info_type", resmap.get("message_info_type"));
				extras.put("message_date", resmap.get("message_date"));
				extras.put("create_id", pm.getPersonnel_id());
				extras.put("create_user_name", pm.getPersonnel_name());
				extras.put("create_user_code", pm.getPersonnel_shortcode());
				extras.put("create_datetime", resmap.get("create_datetime"));
				extras.put("reminder",
						pm.getPersonnel_name() + pm.getPersonnel_shortcode()
 + "将任务协同给" + name);
				PushManage.pushMessageByCode(shortCode,
						PushConstant.TASK_PUBLISH_SYNERGY, msgMap, extras);

			}
			return resmap;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	                                            /**
    * @param content
    *            要转换的JavaBean类型
    * @param valueType
    *            原始json字符串数据
    * @return JavaBean对象
    */
	public static <T> T readValue(String content, Class<T> valueType) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	                                            /**
    * 把JavaBean转换为json字符串 (1)普通对象转换：toJson(Student) (2)List转换：toJson(List)
    * (3)Map转换:toJson(Map)
    * 
    * @param object
    *            JavaBean对象
    * @return json字符串
    */
	public static String toJSon(Object object) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

    // 获取任务反馈详情
	@Override
	public Map<String, Object> getTaskFeedbackListV115(
			HttpServletRequest request, TaskVO task) {
        // 在这里写

		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		PmPersonnel pm = (PmPersonnel) session
				.getAttribute(GlobalVal.USER_SESSION);
        // 更新已读
		String tid = task.getTask_id().toString();
		taskMessageService.taskMessageRead(tid, null, pm.getPersonnel_id());

        // 基本信息
		resMap = taskListDao.getTaskBaseInfo(task.getTask_id());
		resMap.put("user_id", pm.getPersonnel_id());
		resMap.put("personnel_name", pm.getPersonnel_name());
        // 详细信息
		List<Map<String, Object>> info = taskListDao.getTaskInfo(resMap);
        for (Map<String, Object> map : info)
        { // 拼接绝对路径
			int type = Integer.parseInt(map.get("info_type").toString());
			if (type != 1) {
				String temp = map.get("task_info").toString();
				String newtemp = HttpClientUtil.getSysUrl("moaUrl").toString() ;
				map.put("task_info", newtemp+temp);
                // 返回缩图图
				if(type==3){
					if( map.get("task_info_mini")!=null){
						temp =  map.get("task_info_mini")+"";
						map.put("task_info_mini", newtemp+temp);
				}else{
					map.put("task_info_mini", "");
				}
				}
			}
			if(map.get("info_date")==null){
                map.put("info_date", "");
            }
		}
        // 反馈信息
		List<Map<String, Object>> message = taskListDao.getFeedbackInfo115(task.getTask_id(),task.getMax_id());
        for (Map<String, Object> map : message)
        {// 拼接绝对路径
			int type = Integer
					.parseInt(map.get("message_info_type").toString());
			if (type == 2 || type == 3) {
				String temp = map.get("message_info").toString();
				String newtemp = HttpClientUtil.getSysUrl("moaUrl").toString()
						+ temp;
				map.put("message_info", newtemp);
				if(type==3){
					map.put("message_info_mini", HttpClientUtil.getSysUrl("moaUrl").toString()
							+ map.get("message_info_mini"));
				}else{
					map.put("message_info_mini","");
				}
			}
		}

		int user = 0;
		int task_status = Integer
				.parseInt(resMap.get("task_status").toString());
		String is_feedback = resMap.get("is_feedback").toString();
		int accept_feedback_id = Integer.parseInt(resMap.get(
				"accept_feedback_id").toString());
		int Personnel_id = pm.getPersonnel_id();
		int feedback_user_id = Integer.parseInt(resMap.get("feedback_user_id")
				.toString());
        // 判断登录人是否为反馈人id 返回1 其余返回3
        // 如果当前登录人id等于接受反馈人ID，user等于被反馈人id （2），否则为反馈人（1）,都不等于返回3
		if (accept_feedback_id == Personnel_id) {
			user = 2;
		} else if (feedback_user_id == Personnel_id) {
			user = 1;
		} else if (accept_feedback_id != Personnel_id
				&& feedback_user_id != Personnel_id) {
			user = 3;
		}
		
        // 如果是我创建列表，返回按键为:任务详情和查看子任务
		if(task.getWhich_tab()!=null&&task.getWhich_tab()==3){
		    user =4;
		}
		boolean isdelay;
        // 按钮信息
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("task_id", tid);
		Map<String, Object> rmap = taskListDao.getTaskIsDelay(pmap);
		int delay = Integer.parseInt(rmap.get("delay").toString());
		if (delay > 0) {
			isdelay = true;
		} else {
			isdelay = false;
		}

		List<Map<String, Object>> buttons;
        // 是否查看子任务界面
		int is_readonly = 0;
		if (task.getIs_readonly() == null) {
			is_readonly = 0;
		} else {
			is_readonly = task.getIs_readonly();
		}
		buttons = TaskButtonUtil.getButtons(task_status, user, is_feedback,
				isdelay, is_readonly);

        // task_appoint（String）XX指派给XX
		String task_appoint = "";
		if(resMap.get("publish_title") != null){
			 task_appoint = resMap.get("publish_title").toString();
		}

		resMap.put("info", info);
		resMap.put("message", message);
		resMap.put("buttons", buttons);
		resMap.put("task_appoint", task_appoint);

		if(task.getMax_id()>0){
			resMap.remove("task_id");
			resMap.remove("task_title");
			resMap.remove("info");
			resMap.remove("finish_time");
			resMap.remove("create_id");
			resMap.remove("create_user_name");
			resMap.remove("create_user_code");
			resMap.remove("create_datetime");
			resMap.remove("task_appoint");
		}
			
        // 删除多余信息
			resMap.remove("task_num");
			resMap.remove("is_feedback");
			resMap.remove("feedback_type");
			resMap.remove("feedback_user_name");
			return resMap;
		
		
		
		
	}

	                                            /**
    * 
    * <p>
    * Title: which_tab
    * </p>
    * <p>
    * Description:比较反馈和指派数量
    * </p>
    * 
    * @return
    * @see com.zx.moa.ioa.task.service.ITaskListService#which_tab()
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:52:11
    */
	@Override
	public Integer which_tab(TaskListVO bean) {
        // 任务反馈人数量
		Integer  accept = taskListDao.countFeedbackUser(bean);
        // 任务指派人数量
		Integer feedback = taskListDao.countAcceptFeedback(bean);
		int num =  feedback>accept?1:2;
		return num;
	}

	                                            /**
    * 
    * <p>
    * Title: doing_num
    * </p>
    * <p>
    * Description: 進行中數量
    * </p>
    * 
    * @return
    * @see com.zx.moa.ioa.task.service.ITaskListService#doing_num()
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:52:16
    */
	@Override
	public Integer doing_num(TaskListVO bean) {
		// TODO Auto-generated method stub
		return taskListDao.countDoing_num(bean);
	}

	                                            /**
    * 
    * <p>
    * Title: delay_num
    * </p>
    * <p>
    * Description: 延误数量
    * </p>
    * 
    * @return
    * @see com.zx.moa.ioa.task.service.ITaskListService#delay_num()
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:52:21
    */
	@Override
	public Integer delay_num(TaskListVO bean) {
		// TODO Auto-generated method stub
		return taskListDao.CountDelay_num(bean);
	}

	                                            /**
    * 
    * <p>
    * Title: check_num
    * </p>
    * <p>
    * Description: 待验收数量
    * </p>
    * 
    * @return
    * @see com.zx.moa.ioa.task.service.ITaskListService#check_num()
    * @author zhangmingjian
    * @date 2016年9月19日 上午9:52:25
    */
	@Override
	public Integer check_num(TaskListVO bean) {
		// TODO Auto-generated method stub
		return taskListDao.CountCheck_num(bean);
	}

    /**
     * @Title: taskIndexListSearch
     * @Description: TODO待办任务列表
     * @param request
     * @param map
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 上午10:33:54
     * @see com.zx.moa.ioa.task.service.ITaskListService#taskIndexListSearch(javax.servlet.http.HttpServletRequest, java.util.Map)
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    @Override
    public List<Map<String, Object>> taskIndexListSearch(HttpServletRequest request, Map<String, Object> map)
    {
        List<Map<String, Object>> reslist = null;
        reslist = taskListDao.taskIndexListSearcho(map);
        return reslist;
    }

    /**
     * @Title: taskIndexListSearchCount
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param map
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 上午10:52:03
     * @see com.zx.moa.ioa.task.service.ITaskListService#taskIndexListSearchCount(java.util.Map)
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    @Override
    public int taskIndexListSearchCount(Map<String, Object> map)
    {
        return taskListDao.taskIndexListSearchCounto(map);
    }


}
