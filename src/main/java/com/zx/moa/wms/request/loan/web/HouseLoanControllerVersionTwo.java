package com.zx.moa.wms.request.loan.web;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.wms.loan.persist.WmsCreCreditHeadDao;
import com.zx.moa.wms.loan.service.IWmsCreCreditHeadService;
import com.zx.moa.wms.loan.service.IWmsSysDictDataService;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.request.loan.service.HouseLoanService;
import com.zx.moa.wms.request.loan.service.IWmsCreCreditNotificationMessageService;
import com.zx.moa.wms.request.loan.util.WmsHelp;
import com.zx.moa.wms.request.loan.vo.WmsApplyInfoSearchBeanVO;
import com.zx.moa.wms.request.loan.vo.WmsCreHousingOperationLogBeanVO;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 
 * 版权所有：版权所有(C) 2016，卓信金控 系统名称：MOA移动办公系统
 * 
 * @ClassName: MOA外部请求Controller 模块名称：
 * @Description: 内容摘要：
 * @author wangyihan
 * @date 2016年12月20日
 * @version V2.0 history: 1、2016年9月20日 wangyihan 创建文件
 */
@Controller
public class HouseLoanControllerVersionTwo {

	private static Logger log = LoggerFactory.getLogger(HouseLoanControllerVersionTwo.class);

	@Autowired
	private HouseLoanService houseLoanService;

	@Autowired
	private IWmsSysDictDataService wmssysdictdataService;

	@Autowired
	private IWmsCreCreditNotificationMessageService wmscrecreditnotificationmessageservice;

	@Autowired
	private IWmsCreCreditHeadService iwmscrecreditheadservice;

	@Autowired
	private WmsCreCreditHeadDao wmsCreCreditHeadDao;// 贷款主表

	/**
	 * Description : 房产图片上传(第二步:保存图片信息)
	 * 当前方法图片上传还用原来的方法/wms/houseLoanUploadStepOne.do （接口文档3.1.2）
	 * 
	 * @param queryInfo
	 * @return record
	 * @author baisong
	 */
	@RequestMapping(value = "/wms/houseLoanUploadStepTwoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> houseLoanUpload(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request, WmsApplyInfoSearchBeanVO wmsApplyInfoSearchBeanVO) {

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Gson gson = new Gson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		/*
		 * if(StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCity())||
		 * StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCustomer_name())||
		 * StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCustomer_age())||
		 * StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCommunity_name())||
		 * StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getHouses_number())||
		 * StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getHouse_type())||
		 * StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getHouse_address_more())
		 * ){ resMap.put("message", "您有未填写的项！"); return
		 * ResultHelper.getError(resMap.get("message").toString(),resMap.get(
		 * "message").toString()); }
		 * if(wmsApplyInfoSearchBeanVO.getCredit_limit()<=0.0||
		 * wmsApplyInfoSearchBeanVO.getMax_repayment_time_limit()==null||
		 * wmsApplyInfoSearchBeanVO.getHouse_building_area()<=0.0){
		 * resMap.put("message", "您有未填写或填写不正确的项！"); return
		 * ResultHelper.getError(resMap.get("message").toString(),resMap.get(
		 * "message").toString()); }
		 */

		List<WmsCreHousingApplyAtt> attList = gson.fromJson(queryInfo.getFile_info_json(), new TypeToken<List<WmsCreHousingApplyAtt>>() {
		}.getType());
		if (attList != null) {
			// 获取扩展名与文件新名称
			for (WmsCreHousingApplyAtt bean : attList) {
				try {
					bean.setAttachment_new_name(bean.getAttachment_address().substring(0, bean.getAttachment_address().lastIndexOf(".")));
					bean.setAttachment_type(bean.getAttachment_address().substring(bean.getAttachment_address().lastIndexOf(".") + 1));
				} catch (Exception e) {
					return ResultHelper.getError(bean.getAttachment_address() + "格式不正确");
				}
			}
		}
		/*
		 * MultiValueMap<String, Object> form = new LinkedMultiValueMap<String,
		 * Object>(); form.add("list", new Gson().toJson(attList));
		 * form.add("personnel_id", personnel.getPersonnel_id());
		 * form.add("wms_cre_credit_head_id",
		 * queryInfo.getWms_cre_credit_head_id()); form.add("falg",
		 * queryInfo.getSave_type().toString());
		 * 
		 * form.add("interface_num", "WMS_OUT_houseLoanUploadStepTwoUp");
		 * form.add("sys_num", "MIF"); //Map<String, Object> map=new
		 * HashMap<>(); //beanToMap(wmsApplyInfoSearchBeanVO,map);
		 * //form.add("customer_info", gson.toJson(wmsApplyInfoSearchBeanVO));
		 * try{ form.add("customer_info", new
		 * ObjectMapper().writeValueAsString(wmsApplyInfoSearchBeanVO));
		 * }catch(Exception e){ e.printStackTrace(); } Map<String, Object>
		 * resMap =
		 * restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get(
		 * "nozzleUrl"), form, Map.class);
		 */

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_houseLoanUploadStepTwoUp"));
		nvps.add(new BasicNameValuePair("sys_num", "MIF"));
		// String releaseTask = JSONObject.toJSONString(map);

		nvps.add(new BasicNameValuePair("list", new Gson().toJson(attList)));
		nvps.add(new BasicNameValuePair("personnel_id", personnel.getPersonnel_id().toString()));
		nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString()));
		nvps.add(new BasicNameValuePair("falg", queryInfo.getSave_type().toString()));
		nvps.add(new BasicNameValuePair("user_id", "0"));
		try {
			nvps.add(new BasicNameValuePair("customer_info", gson.toJson(wmsApplyInfoSearchBeanVO)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
		} catch (ConnectException e) {
			e.printStackTrace();
			resMap.put("message", "WMS服务器拒绝链接，请联系客服！");
			return ResultHelper.getNetworkError(resMap.get("message").toString());
		} catch (IOException e) {
			e.printStackTrace();
			resMap.put("message", WmsHelp.RET_ERROR_VALUE);
			return ResultHelper.getError(resMap.get("message").toString());
		}

		return ResultHelper.getSuccess(resMap.get("message").toString());
	}

	/**
	 * Description : 房产抵押单据查看明细
	 * 
	 * @param queryInfo
	 * @return record
	 * @author baisong
	 */
	@RequestMapping(value = "/wms/searchHouseLoanInfoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> searchHouseLoanInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		// log.info(new Gson().toJson(queryInfo));
		Map<String, Object> res = new HashMap<>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			res = houseLoanService.searchHouseLoanInfoUp_Request(queryInfo, personnel);
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Description : 获取贷款单据筛选信息 （接口文档3.5）
	 * 
	 * @param queryInfo
	 * @return record
	 * @author baisong
	 */
	@RequestMapping(value = "/wms/searchHouseLoanInfoListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> searchHouseLoanListUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		// log.info(new Gson().toJson(queryInfo));
		Map<String, Object> res = new HashMap<>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			res = houseLoanService.searchHouseLoanListUp_Request(queryInfo, personnel);
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Description : 新通知标识
	 * 
	 * @param
	 * @return
	 * @author jiaodelong
	 */
	@RequestMapping(value = "/wms/getMessageUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getMessageUp(Integer user_id, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			String appName = request.getHeader(GlobalVal.SSO_MODULE);
			if (user_id == null) {
				user_id = personnel.getPersonnel_id();
			}
			String notify_state = "0";
			int n = wmscrecreditnotificationmessageservice.getMessage(user_id, appName);
			if (n > 0) {
				notify_state = "1";
			}
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
			resMap.put("notify_state", notify_state);
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return resMap;
	}

	/**
	 * Description : 信息通知中心列表
	 * 
	 * @param Integer
	 * @return Map<String, Object>
	 * @author jiaodelong
	 */
	@RequestMapping(value = "/wms/getMessageListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getWmsMessageList(Integer user_id, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> resMap1 = new HashMap<String, Object>();
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		String appName = request.getHeader(GlobalVal.SSO_MODULE);
		try {
			// MultiValueMap<String, Object> form = new
			// LinkedMultiValueMap<String, Object>();
			// RestTemplate restTemplate = new RestTemplate();
			List<Map<String, Object>> list = wmscrecreditnotificationmessageservice.getWmsMessageList(personnel.getPersonnel_id(), appName);
			resMap.put("ret_data", list);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
			// form.add("user_id", personnel.getPersonnel_id());
			// form.add("interface_num",
			// "WMS_OUT_POSTupdateMessageStatusForIsread");
			// form.add("sys_num", "MIF");
			// resMap=restTemplate.postForObject(
			// PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"),
			// form, Map.class);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTupdateMessageStatusForIsread"));
			nvps.add(new BasicNameValuePair("sys_num", "MIF"));
			nvps.add(new BasicNameValuePair("user_id", personnel.getPersonnel_id().toString()));
			nvps.add(new BasicNameValuePair("app_name", appName));
			try {
				resMap1 = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
			} catch (ConnectException e) {
				e.printStackTrace();
				resMap.put("ret_msg", "WMS服务器拒绝链接，请联系客服！");
				resMap.put("ret_code", ResultHelper.RET_NETWORK_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
				resMap.put("ret_code", ResultHelper.RET_ERROR);
			}
			if (resMap1 != null && resMap1.get("result") != null) {
				if ("success".equals(resMap1.get("result").toString())) {
					resMap.put("ret_code", ResultHelper.RET_SUCCESS);
					resMap.put("ret_msg", "请求成功!");
				} else {
					resMap.put("ret_code", ResultHelper.RET_ERROR);
					resMap.put("ret_msg", "操作失败!");
				}
			} else {
				resMap.put("ret_code", ResultHelper.RET_ERROR);
				resMap.put("ret_msg", "操作失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}

		return resMap;
	}

	/**
	 * Description : 信息通知中心删除
	 * 
	 * @param Integer
	 * @return Map<String, Object>
	 * @author jiaodelong
	 */
	@RequestMapping(value = "/wms/deleteMessageUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> deleteWmsMessage(String msg_key_list, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		// List<String> messageList =
		// JsonUtil.jsonArrayToList(msg_key_list,String.class);
		try {
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
			form.add("msg_key_list", msg_key_list);
			form.add("interface_num", "WMS_OUT_POSTdeleteMessageUp");
			form.add("sys_num", "MIF");
			resMap = restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), form, Map.class);
			if (resMap != null && resMap.get("result") != null) {
				if ("success".equals(resMap.get("result").toString())) {
					resMap.put("ret_code", ResultHelper.RET_SUCCESS);
					resMap.put("ret_msg", "请求成功!");
				} else {
					resMap.put("ret_code", ResultHelper.RET_ERROR);
					resMap.put("ret_msg", "操作失败!");
				}
			} else {
				resMap.put("ret_code", ResultHelper.RET_ERROR);
				resMap.put("ret_msg", "操作失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}
		return resMap;
	}

	/**
	 * Description : 单据作废
	 * 
	 * @param Integer
	 * @return Map<String, Object>
	 * @author jiaodelong 流程节点标识 debtkey 房产核查为4放款申请为9放款审核为10
	 */
	@RequestMapping(value = "/wms/setDocumentVoidUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> deleteWmsMessage(String advice, Integer wms_cre_credit_head_id, String pass, String debtkey, HttpServletRequest request) {
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			// RestTemplate restTemplate = new RestTemplate();
			// MultiValueMap<String, Object> form = new
			// LinkedMultiValueMap<String, Object>();
			// form.add("advice", advice);
			// form.add("wms_cre_credit_head_id", wms_cre_credit_head_id);
			// form.add("personnelId", personnel.getPersonnel_id());
			// form.add("pass", pass);
			// form.add("debtkey", debtkey);
			// form.add("interface_num", "WMS_OUT_POSTsetDocumentVoidUp");
			// form.add("sys_num", "MIF");
			// resMap = restTemplate.postForObject(
			// PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"),
			// form, Map.class);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("advice", advice));
			nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", wms_cre_credit_head_id.toString()));
			nvps.add(new BasicNameValuePair("personnelId", personnel.getPersonnel_id().toString()));
			nvps.add(new BasicNameValuePair("pass", pass));
			nvps.add(new BasicNameValuePair("debtkey", debtkey));
			nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTsetDocumentVoidUp"));
			nvps.add(new BasicNameValuePair("sys_num", "MIF"));
			try {
				resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
			} catch (ConnectException e) {
				e.printStackTrace();
				resMap.put("ret_code", "WMS服务器拒绝链接，请联系客服！");
				resMap.put("ret_code", ResultHelper.RET_NETWORK_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
				resMap.put("ret_code", ResultHelper.RET_ERROR);
			}
			if (resMap != null && resMap.get("result") != null) {
				if ("success".equals(resMap.get("result").toString())) {
					resMap.put("ret_code", ResultHelper.RET_SUCCESS);
					resMap.put("ret_msg", "请求成功!");
				}
				// 老数据不允许此操作
				else if ("taskerror".equals(resMap.get("result").toString())) {
					resMap.put("ret_code", ResultHelper.RET_ERROR);
					resMap.put("ret_msg", "此单据不允许此操作!");
				} else {
					resMap.put("ret_code", ResultHelper.RET_ERROR);
					resMap.put("ret_msg", "操作失败!");
				}
			} else {
				resMap.put("ret_code", ResultHelper.RET_ERROR);
				resMap.put("ret_msg", "操作失败!");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}
		return resMap;
	}

	/**
	 * Description : 获取放款审批信息列表(接口文档3.18)
	 * 
	 * @param Integer
	 * @return Map<String, Object>
	 * @author jiaodelong
	 */
	@RequestMapping(value = "/wms/searchLoanApprovalListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> searchLoanApprovalList(WmsCreCreditHeadSearchBeanVO wmscrecredithead, HttpServletRequest request) {
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = iwmscrecreditheadservice.getWmsMessageList(wmscrecredithead, personnel);
			resMap.put("ret_data", list);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}
		return resMap;
	}

	/**
	 * Description : 获取放款审批信息详情(接口文档3.19)
	 * 
	 * @param Integer
	 * @return Map<String, Object>
	 * @author jiaodelong
	 */
	@RequestMapping(value = "/wms/getLoanApprovalInfoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getLoanApprovalInfo(Integer wms_cre_credit_head_id, HttpServletRequest request) {
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Map<String, Object> MAP = iwmscrecreditheadservice.getLoanApprovalInfo(wms_cre_credit_head_id);
			resMap.put("ret_data", MAP);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}
		return resMap;
	}

	/**
	 * Description : 放款审批结果(接口文档3.20)
	 * 
	 * @param Integer
	 * @return Map<String, Object>
	 * @author jiaodelong
	 */
	@RequestMapping(value = "/wms/sendResultsLoanApprovalUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> sendResultsLoanApprovalUp(String pass, String advice, Integer wms_cre_credit_head_id, HttpServletRequest request) {
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTsendResultsLoanApprovalUp"));
			nvps.add(new BasicNameValuePair("sys_num", "MIF"));
			nvps.add(new BasicNameValuePair("pass", pass));
			nvps.add(new BasicNameValuePair("advice", advice));
			nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", wms_cre_credit_head_id.toString()));
			nvps.add(new BasicNameValuePair("userId", personnel.getPersonnel_id().toString()));
			nvps.add(new BasicNameValuePair("userName", personnel.getPersonnel_name()));
			try {
				resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
			} catch (ConnectException e) {
				e.printStackTrace();
				resMap.put("ret_msg", "WMS服务器拒绝链接，请联系客服！");
				resMap.put("ret_code", ResultHelper.RET_NETWORK_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
				resMap.put("ret_code", ResultHelper.RET_ERROR);
			}
			if (resMap != null && resMap.get("result") != null) {
				if ("success".equals(resMap.get("result").toString())) {
					resMap.put("ret_code", ResultHelper.RET_SUCCESS);
					resMap.put("ret_msg", "请求成功!");
				} else {
					resMap.put("ret_code", ResultHelper.RET_ERROR);
					resMap.put("ret_msg", "操作失败!");
				}
			} else {
				resMap.put("ret_code", ResultHelper.RET_ERROR);
				resMap.put("ret_msg", "操作失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}
		return resMap;
	}

	/**
	 * Description : 房产核查列表查询(接口文档3.2)
	 * 
	 * @param queryInfo
	 * @return record
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/wmsHousingCertificatesListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultBean<List<Map<String, Object>>> getwmsHousingCertificatesList(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		return ResultHelper.getSuccess(houseLoanService.getWmsHousingCertificatesList_RequestUp(queryInfo, personnel));
	}

	/**
	 * Description : 待房产核查单据核查结果(接口文档3.3)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/savePropertyVerificationInfoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> savePropertyVerificationInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 获取扩展名与文件新名称
		try {
			this.houseLoanService.savePropertyVerificationInfoUp(queryInfo, personnel);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}

		return resMap;
	}

	/**
	 * Description : 获取房贷客户单据信息列表(接口文档3.4)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/searchHouseLoanListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> SearchHouseLoanListUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		Map<String, Object> resMap = new HashMap<String, Object>();

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = this.houseLoanService.searchHouseLoanListUp(queryInfo, personnel);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		resMap.put("ret_data", list);

		return resMap;
	}

	/**
	 * Description : 获取补录初始化信息和数据字典表(接口文档3.8)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/getDataDictUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getDataDictUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		Map<String, Object> resMap = new HashMap<String, Object>();

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

		try {

			resMap = this.houseLoanService.getDataDictUp(queryInfo, personnel);

			if (queryInfo.getWms_cre_credit_head_id() != null) {
				Map<String, Object> isSureFlagMap = this.houseLoanService.isSureCertificateUp(queryInfo, personnel);
				resMap.put("is_sure_certificate_flag", isSureFlagMap.get("is_sure_certificate_flag"));
				resMap.put("is_sure_handle_certificates_flag", isSureFlagMap.get("is_sure_handle_certificates_flag"));
			} else {
				resMap.put("is_sure_certificate_flag", "1");
				resMap.put("is_sure_handle_certificates_flag", "1");
			}

			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}

		return resMap;
	}

	/**
	 * 
	 * @Title: savePropertyVerificationInfoUp
	 * @Description: 发送房产核查领用状态
	 * @param queryInfo
	 * @param request
	 * @return
	 * @author: ZhangWei
	 * @time:2017年5月31日 下午5:17:31 history: 1、2017年5月31日 ZhangWei 创建方法
	 */
	@RequestMapping(value = "/wms/sendClaimOperUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> sendClaimOperUp(WmsCreHousingOperationLogBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		Map<String, Object> resMap = new HashMap<String, Object>();

		try {
			resMap = this.houseLoanService.sendClaimOperUp(queryInfo, personnel);
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
		}

		return resMap;
	}

	/**
	 * Description : 提交信息前校验单据状态(接口文档3.9)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/isSureCertificateUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> isSureCertificateUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		Map<String, Object> resMap = new HashMap<String, Object>();

		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

		try {
			resMap = this.houseLoanService.isSureCertificateUp(queryInfo, personnel);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}

		return resMap;
	}

	/**
	 * Description : 获取放款申请信息列表(接口文档3.14)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/searchLoanApplicationListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> searchLoanApplicationListUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		Map<String, Object> resMap = new HashMap<String, Object>();
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = this.houseLoanService.searchLoanApplicationListUp(queryInfo, personnel);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		resMap.put("ret_data", list);

		return resMap;
	}

	/**
	 * Description : 获取放款初始化信息(接口文档3.15)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/getLoanApprovalInitiInfoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getLoanApprovalInitiInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		// log.info(new Gson().toJson(queryInfo));

		Map<String, Object> resMap = new HashMap<String, Object>();
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

		try {
			Map<String, Object> retDataMap = this.houseLoanService.getLoanApprovalInitiInfoUp(queryInfo, personnel);
			resMap.put("ret_data", retDataMap);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}

		return resMap;
	}

	/**
	 * Description : 发送放款申请详细信息(接口文档3.16)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/sendLoanApprovalInfoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> sendLoanApprovalInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

		try {
			resMap = this.houseLoanService.sendLoanApprovalInfoUp(queryInfo, personnel);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}

		return resMap;
	}

	/**
	 * Description : 获取放款申请确认信息(接口文档3.17)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@RequestMapping(value = "/wms/getLoanApprovalConfirmUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getLoanApprovalConfirmUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		log.info(new Gson().toJson(queryInfo));

		Map<String, Object> resMap = new HashMap<String, Object>();
		PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

		try {
			Map<String, Object> confirmMap = this.houseLoanService.getLoanApprovalConfirmUp(queryInfo, personnel);
			resMap.put("ret_data", confirmMap);
			resMap.put("ret_code", ResultHelper.RET_SUCCESS);
			resMap.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			resMap.put("ret_data", null);
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}

		return resMap;
	}

	/**
	 * Description :APP首页信息展示--用于展示待办事项，和进展中的数据信息
	 * 
	 * @url /wms/getHomePageInfoUp.do
	 * @param queryInfo
	 * @return Map
	 * @author baisong
	 * @date 2016-6-6
	 */
	@RequestMapping(value = "/wms/getHomePageInfoUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getHomePageInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			res = houseLoanService.getHomePageInfoUp_Request(queryInfo, personnel);
			if ("success".equals(res.get("result"))) {
				res.put("ret_code", ResultHelper.RET_SUCCESS);
				res.put("ret_msg", "请求成功!");
			} else if ("connecterror".equals(res.get("result"))) {// WMS链接错误！
				res.put("ret_code", ResultHelper.RET_SUCCESS);
				res.put("ret_msg", "WMS链接错误!进展中数据获取成功，待办事项获取失败！");
			} else {
				res.put("ret_code", ResultHelper.RET_ERROR);
				res.put("ret_msg", "未知错误！");
			}
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Description :获取系统权限
	 * 
	 * @url /wms/getAuthorityUp.do
	 * @param queryInfo
	 * @return Map
	 * @author baisong
	 * @date 2016-6-6
	 */
	@RequestMapping(value = "/wms/getAuthorityUp.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getAuthorityUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			res = houseLoanService.getAuthorityUp_Request(queryInfo, personnel);
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	// /**
	// * Description :获取系统权限
	// * @url /wms/getAuthorityUp.do
	// * @param queryInfo
	// * @return Map
	// * @author baisong
	// * @date 2016-6-6
	// */
	// @RequestMapping(value = "/wms/getUserAndSendInfo.do", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// @ResponseBody
	// public Map<String, Object>
	// getUserAndSendInfo(WmsCreCreditHeadSearchBeanVO queryInfo,
	// HttpServletRequest request) {
	// Map<String, Object> res =new HashMap<>();
	// try {
	// PmPersonnel personnel = (PmPersonnel)
	// request.getSession().getAttribute(GlobalVal.USER_SESSION);
	// res = houseLoanService.getAuthorityUp_Request(queryInfo, personnel);
	// res.put("ret_code", ResultHelper.RET_SUCCESS);
	// res.put("ret_msg", "请求成功!");
	// } catch (Exception e) {
	// res.put("ret_code", ResultHelper.RET_ERROR);
	// res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
	// }
	// return res;
	// }
	/**
	 * Description :用于发送极光消息 供wms调用
	 * 
	 * @url /wms/pushManageForWMS.do
	 * @param queryInfo
	 * @return Map
	 * @author baisong
	 * @date 2016-6-12
	 */
	@RequestMapping(value = "/wms/pushManageForWMS.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> pushManageForWMS(String sysSendInfoVO, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		try {
			log.info("极光推送消息开始" + sysSendInfoVO);
			res = houseLoanService.getUserAndSendInfo_Request(sysSendInfoVO);
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
			log.info("极光推送消息结束");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
			log.info("极光推送消息失败");
		}

		return res;
	}

	/**
	 * Description :获取上传成功的图片数量
	 * 
	 * @url /wms/getPictureQuantity.do
	 * @param queryInfo
	 * @return Map
	 * @author baisong
	 * @date 2016-6-6
	 */
	@RequestMapping(value = "/wms/getPictureQuantity.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getPictureQuantity(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			res = houseLoanService.getPictureQuantity_Request(queryInfo, personnel);
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Description :获取限制上传图片的数量
	 * 
	 * @url /wms/getlimitNumber.do
	 * @param queryInfo
	 * @return Map
	 * @author jiaodelong
	 * @date 2016-7-12
	 */
	@RequestMapping(value = "/wms/getlimitNumber.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getlimitNumber(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		try {
			res = houseLoanService.getlimitNumber();
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	/******* v1.9.0版本接口开始 ***********/
	/**
	 * Description : 3.42获取房产核查页面信息。
	 * 
	 * @url /wms/getHouseAssessmentState.do
	 * @param queryInfo
	 * @return Map
	 * @author baisong
	 * @date 2016-10-12
	 */
	@RequestMapping(value = "/wms/getHouseAssessmentState.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> getHouseAssessmentState(HttpServletRequest request, WmsCreCreditHeadSearchBeanVO queryInfo) {
		Map<String, Object> res = new HashMap<>();
		try {
			PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
			res = houseLoanService.getHouseAssessmentState(queryInfo, personnel);
			res.put("ret_code", ResultHelper.RET_SUCCESS);
			res.put("ret_msg", "请求成功!");
		} catch (Exception e) {
			res.put("ret_code", ResultHelper.RET_ERROR);
			res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
			e.printStackTrace();
		}
		return res;
	}

	/******** v1.9.0版本接口结束 **********/
}