package com.zx.moa.wms.request.loan.service.Impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.moa.ioa.util.PushManage;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.JsonUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.SysConcurrentPost;
import com.zx.moa.util.gen.entity.wms.WmsCreCreditHead;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.util.gen.entity.wms.WmsSysDictData;
import com.zx.moa.wms.loan.persist.PmPersonnelDao;
import com.zx.moa.wms.loan.persist.SysConcurrentPostDao;
import com.zx.moa.wms.loan.persist.SysDeptDao;
import com.zx.moa.wms.loan.persist.SysUserRoleDao;
import com.zx.moa.wms.loan.persist.WmsCreCreditHeadDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingApplyAttDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingApprovalInfoDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingFileInfoDao;
import com.zx.moa.wms.loan.persist.WmsSysDictDao;
import com.zx.moa.wms.loan.persist.WmsSysDictDataDao;
import com.zx.moa.wms.loan.vo.SysSendInfoVO;
import com.zx.moa.wms.loan.vo.TransmitValuesThreeVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsMoaHelp;
import com.zx.moa.wms.loan.vo.WmsSysDictDataSearchBeanVO;
import com.zx.moa.wms.request.loan.service.HouseLoanService;
import com.zx.moa.wms.request.loan.util.WmsHelp;
import com.zx.moa.wms.request.loan.vo.WmsCreHousingOperationLogBeanVO;
import com.zx.platform.syscontext.PlatformGlobalVar;
import com.zx.platform.syscontext.util.StringUtil;

@Service("houseLoanService")
public class HouseLoanServiceImpl implements HouseLoanService {

	@Autowired
	private WmsCreCreditHeadDao wmsCreCreditHeadDao;

	@Autowired
	private WmsCreHousingApplyAttDao wmsCreHousingApplyAttDao;

	@Autowired
	private WmsCreHousingFileInfoDao wmsCreHousingFileInfoDao;

	@Autowired
	private WmsSysDictDao wmsSysDictDao;// 字典表dao

	@Autowired
	private WmsSysDictDataDao wmssysdictdataDao;
	@Autowired
	private PmPersonnelDao pmPersonnelDao;// 人员表dao
	@Autowired
	private SysDeptDao sysDeptDao;// 部门dao
	@Autowired
	private SysUserRoleDao sysUserRoleDao;// 人员角色
	@Autowired
	private WmsCreHousingApprovalInfoDao wmsCreHousingApprovalInfoDao;// 房贷——审批信息表
	@Autowired
	private SysConcurrentPostDao sysConcurrentPostDao;// 兼职表

	/**
	 * 房产抵押列表查询
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> searchHouseLoanList_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 区分新旧流程数据参数 根据版本发布时间区分
		paramMap.put("hprocess_time", WmsMoaHelp.PERFECTHOUSINGLOANPROCESS_TIME);
		// 获取该用户的角色信息
		List<String> roleList = wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id());
		for (String role : roleList) {
			// 判断该用户的角色
			if (role.equals("贷前办件复核员")) {
				// 是办件人员
				paramMap.put("is_bj", "1");
			}
		}
		paramMap.put("create_user_id", personnel.getPersonnel_id());

		// 单据编码
		if (StringUtil.isNotBlank(queryInfo.getBill_code())) {
			paramMap.put("bill_code", "%" + queryInfo.getBill_code() + "%");
		}
		// 客户名称
		if (StringUtil.isNotBlank(queryInfo.getCustomer_name())) {
			paramMap.put("customer_name", "%" + queryInfo.getCustomer_name() + "%");
		}
		// 身份证号
		if (StringUtil.isNotBlank(queryInfo.getId_card())) {
			paramMap.put("id_card", "%" + queryInfo.getId_card() + "%");
		}
		// 手机号码
		if (StringUtil.isNotBlank(queryInfo.getMobile_telephone())) {
			paramMap.put("mobile_telephone", "%" + queryInfo.getMobile_telephone() + "%");
		}
		// 单据状态
		if (StringUtil.isNotBlank(queryInfo.getBill_status())) {
			if (queryInfo.getBill_status().equals("1") || queryInfo.getBill_status().equals("3")) {// 近...个月单据
				paramMap.put("bill_status", null);
				paramMap.put("period", queryInfo.getBill_status());
			} else {
				paramMap.put("period", null);
				paramMap.put("bill_status", queryInfo.getBill_status());
			}
		}
		// 多个字段like
		if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
			paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
		}

		if (StringUtils.isNotEmpty(queryInfo.getSortname())) {
			paramMap.put("sortname", "create_timestamp_order");
			if ("0".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "asc");
			} else if ("1".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "desc");
			}
		} else {
			paramMap.put("sortname", "create_timestamp_order");
			paramMap.put("sortorder", "desc");
		}

		if (queryInfo.getIs_need_paging() != null) {
			if (queryInfo.getIs_need_paging() == 1) {
				paramMap.put("offset", queryInfo.getOffset());
				paramMap.put("pagesize", queryInfo.getPagesize());
			} else {
				paramMap.put("offset", null);
				paramMap.put("pagesize", null);
			}
		} else {
			paramMap.put("offset", queryInfo.getOffset());
			paramMap.put("pagesize", queryInfo.getPagesize());
		}

		List<Map<String, Object>> list = wmsCreCreditHeadDao.searchHouseLoanList_Request(paramMap);

		if (null != list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		// 流程获得idList和退件原因
		Map<String, Object> map = new HashMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();
		String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/cremanage/getHosuingWorkInfoMoa.do";
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		map = restTemplate.postForObject(url, form, Map.class);

		if (map != null && map.get("idList") != null && ((List<String>) map.get("idList")).size() != 0) {
			list = addTaskIDHouse(list, (List<Integer>) map.get("idList"), (List<String>) map.get("taskIdList"), (List<String>) map.get("approvesGroups"), (List<String>) map.get("approveAdvices"), (List<String>) map.get("approveTimes"));
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getwmsHousingCertificatesList_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 区分新旧流程数据参数 根据版本发布时间区分
		paramMap.put("hprocess_time", WmsMoaHelp.PERFECTHOUSINGLOANPROCESS_TIME);

		/*
		 * boolean roleval = false; // 获取该用户的角色信息 List<String> roleList =
		 * wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id()); for
		 * (String role : roleList) { // 判断该用户的角色 if (role.equals("贷前办件复核员")) {
		 * //是办件人员 roleval= true; } } if(!roleval) { return new ArrayList<>(); }
		 * //流程获得idList Map<String, Object> map = new HashMap<String, Object>();
		 * RestTemplate restTemplate = new RestTemplate(); String url =
		 * PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") +
		 * "/cremanage/getHosuingIdListMoa.do"; MultiValueMap<String, String>
		 * form = new LinkedMultiValueMap<String, String>();
		 * form.add("personnel_id", personnel.getPersonnel_id().toString()); map
		 * = restTemplate.postForObject(url, form, Map.class); if(map == null ||
		 * map.get("idList") == null|| ((List<String>)map.get("idList")).size()
		 * == 0) { return new ArrayList<Map<String, Object>>(); } else {
		 * paramMap.put("idList", map.get("idList")); }
		 */
		// 单据编码
		if (StringUtil.isNotBlank(queryInfo.getBill_code())) {
			paramMap.put("bill_code", "%" + queryInfo.getBill_code() + "%");
		}
		// 客户名称
		if (StringUtil.isNotBlank(queryInfo.getCustomer_name())) {
			paramMap.put("customer_name", "%" + queryInfo.getCustomer_name() + "%");
		}
		// 身份证号
		if (StringUtil.isNotBlank(queryInfo.getId_card())) {
			paramMap.put("id_card", "%" + queryInfo.getId_card() + "%");
		}
		// 手机号码
		if (StringUtil.isNotBlank(queryInfo.getMobile_telephone())) {
			paramMap.put("mobile_telephone", "%" + queryInfo.getMobile_telephone() + "%");
		}
		// 单据状态(待房产核查)
		paramMap.put("bill_status", "D");

		// 多个字段like
		if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
			paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
		}
		paramMap.put("create_user_city_code", personnel.getPersonnel_regionnumber());// 城市编码

		if (StringUtils.isNotEmpty(queryInfo.getSortname())) {
			paramMap.put("sortname", "create_timestamp_order");
			if ("0".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "asc");
			} else if ("1".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "desc");
			}
		} else {
			paramMap.put("sortname", "create_timestamp_order");
			paramMap.put("sortorder", "desc");
		}

		if (queryInfo.getIs_need_paging() != null) {
			if (queryInfo.getIs_need_paging() == 1) {
				paramMap.put("offset", queryInfo.getOffset());
				paramMap.put("pagesize", queryInfo.getPagesize());
			} else {
				paramMap.put("offset", null);
				paramMap.put("pagesize", null);
			}
		} else {
			paramMap.put("offset", queryInfo.getOffset());
			paramMap.put("pagesize", queryInfo.getPagesize());
		}
		paramMap.put("salesman_id", personnel.getPersonnel_id());// 登陆人id
		paramMap.put("salesman_dept_id", personnel.getPersonnel_deptid());// 登陆人部门id
		paramMap.put("menu_id", WmsHelp.MENU_ID_FCHC_LIST); // 菜单id

		// 子部门信息
		Map<String, Object> childrenDeptMap = wmsCreCreditHeadDao.queryChildrenDeptInfo(paramMap);
		paramMap.put("childrendept", childrenDeptMap.get("childrendept"));

		List<Map<String, Object>> list = wmsCreCreditHeadDao.getwmsHousingCertificatesList_Request(paramMap);

		if (null != list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		return list;
	}

	/**
	 * 房产抵押单据明细查询
	 * 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> searchHouseLoanInfo_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// reqMap.put("hprocess_time", "2016/2/24");
		reqMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		Map<String, Object> bean = wmsCreCreditHeadDao.searchBeanByPkForMap_Request(reqMap);

		if (null != bean) {
			// 添加图片完整地址(拼接接口名)
			bean.put("attachment_address_complete", "wms/getImg.do?url=" + bean.get("attachment_address"));
		}

		reqMap.put("data_type_name", queryInfo.getData_type_name());
		reqMap.put("sortname", queryInfo.getSortname());
		reqMap.put("sortorder", queryInfo.getSortorder());

		/*
		 * boolean is_salesman = false; List<Map<String, Object>> applyList =
		 * pmPersonnelDao.getApplyInfo(personnel.getPersonnel_id());
		 * if(applyList != null && applyList.size() > 0) { //是业务员 is_salesman =
		 * true; }
		 */
		/*
		 * if(is_salesman) {//业务员不能看到核查的验房单与验房评估信息只能看到房屋照片
		 * reqMap.put("is_salesman", true); }
		 */

		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("dept_id", personnel.getPersonnel_deptid());
		parmMap.put("top_dept_code", WmsHelp.TOP_DEPT_CODE);// 产品管理部门编码
		int apply_menu = pmPersonnelDao.getApplyInfobyDept(parmMap);// 贷款申请菜单
		// 兼职表
		SysConcurrentPost sysConcurrentPost = new SysConcurrentPost();
		sysConcurrentPost.setPersonnel_id(personnel.getPersonnel_id());
		List<SysConcurrentPost> sPostlist = sysConcurrentPostDao.getListByEntity(sysConcurrentPost);
		boolean spost_apply_menu = false;
		for (int i = 0; i < sPostlist.size(); i++) {
			parmMap.put("dept_id", sPostlist.get(i).getDept_id());
			int s_apply_menu = pmPersonnelDao.getApplyInfobyDept(parmMap);// 贷款申请菜单
			if (s_apply_menu == 1) {
				spost_apply_menu = true;
				break;
			}
		}
		if (apply_menu == 1 || spost_apply_menu) {// 业务员不能看到核查的验房单与验房评估信息只能看到房屋照片
			reqMap.put("is_salesman", true);
		}

		List<Map<String, Object>> attlist = wmsCreHousingApplyAttDao.search(reqMap);// 附件

		if (null != attlist) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : attlist) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();
		String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/loancheck/houseCreditWorkFlowViewMoa.do";
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString());
		map = restTemplate.postForObject(url, form, Map.class);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("ret_data", bean);
		resMap.put("attlist", attlist);
		if (map != null) {// 流程历程
			resMap.put("works", map.get("Rows"));
		}
		return resMap;
	}

	@Override
	public List<Map<String, Object>> getDataDict(WmsSysDictDataSearchBeanVO queryInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (null != queryInfo.getP_wms_sys_dict_data_id()) {
			paramMap.put("p_wms_sys_dict_data_id", queryInfo.getP_wms_sys_dict_data_id());
		}
		if (null != queryInfo.getWms_sys_dict_id()) {
			paramMap.put("wms_sys_dict_id", queryInfo.getWms_sys_dict_id());
		}
		if (null != queryInfo.getWms_sys_dict_data_id()) {
			paramMap.put("wms_sys_dict_data_id", queryInfo.getWms_sys_dict_data_id());
		}
		if (null != queryInfo.getSort_order()) {
			paramMap.put("sort_order", queryInfo.getSort_order());
		}

		paramMap.put("sortname", queryInfo.getSortname());
		paramMap.put("sortorder", queryInfo.getSortorder());
		List<Map<String, Object>> list = wmssysdictdataDao.search(paramMap);
		return list;
	}

	@Override
	public java.util.List<java.util.Map<String, Object>> addTaskIDHouse(java.util.List<java.util.Map<String, Object>> list, java.util.List<Integer> idLists, java.util.List<String> taskIdLists, java.util.List<String> approvesGroups, java.util.List<String> approveAdvices, java.util.List<String> approveTimes) {
		if (idLists != null) {
			for (Map<String, Object> map : list) {
				Integer wms_cre_credit_head_id = (Integer) map.get("wms_cre_credit_head_id");
				for (int i = 0; i < idLists.size(); i++) {
					if (idLists.get(i).compareTo(wms_cre_credit_head_id) == 0) {
						map.put("taskId", taskIdLists.get(i));
						map.put("check_name", approvesGroups.get(i));
						if (StringUtils.isNotEmpty(approveAdvices.get(i))) {
							map.put("complete_idea", approveAdvices.get(i));
							map.put("return_reason", approveAdvices.get(i));
						} else {
							map.put("complete_idea", "无");
							map.put("return_reason", "无");
						}
						map.put("back_timestamp", approveTimes.get(i));
						break;
					}
				}
			}
		} else {
			list = null;
		}

		return list;
	}

	/**
	 * 查询单据是否可以补件与办件
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> getHousingCertificatesFlag_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		return this.wmsCreCreditHeadDao.getHousingCertificatesFlag_Request(paramMap);
	}

	/**
	 * 房产抵押单据明细查询移动端第二版本
	 * 
	 * @param WmsCreCreditHeadSearchBeanVO
	 *            queryInfo
	 * @param PmPersonnel
	 *            personnel
	 * @return map
	 * @author baisong
	 */
	public Map<String, Object> searchHouseLoanInfoUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// reqMap.put("hprocess_time", "2016/2/24");
		reqMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		// 查询贷款主表等相关表的信息
		Map<String, Object> bean = wmsCreCreditHeadDao.searchBeanByPkForMapUp_Request(reqMap);
		if (null != bean) {
			// 添加图片完整地址(拼接接口名) 当前地址是给前面使用的
			bean.put("attachment_address_complete", "wms/getImg.do?url=" + bean.get("attachment_address"));
		}
		// 2016-6-2 因需求要求显示申请和办件房产核查的图片所以去掉当前限制
		// reqMap.put("data_type_name", queryInfo.getData_type_name());

		/*
		 * boolean is_salesman = false; List<Map<String, Object>> applyList =
		 * pmPersonnelDao.getApplyInfo(personnel.getPersonnel_id());
		 * if(applyList != null && applyList.size() > 0) { //是业务员 is_salesman =
		 * true; } if(is_salesman) {//业务员不能看到核查的验房单与验房评估信息只能看到房屋照片
		 * reqMap.put("is_salesman", true); }
		 */
		int apply_menu = 0;
		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("user_id", personnel.getPersonnel_id());
		List<Map<String, Object>> listRole = sysUserRoleDao.getUserListAllRole(parmMap);
		if (listRole != null && listRole.size() == 0) {
			parmMap.put("dept_id", personnel.getPersonnel_deptid());
			parmMap.put("top_dept_code", WmsHelp.TOP_DEPT_CODE);// 产品管理部门编码
			apply_menu = pmPersonnelDao.getApplyInfobyDept(parmMap);// 贷款申请菜单
		}
		/*
		 * //兼职表 SysConcurrentPost sysConcurrentPost=new SysConcurrentPost();
		 * sysConcurrentPost.setPersonnel_id(personnel.getPersonnel_deptid());
		 * List<SysConcurrentPost> sPostlist =
		 * sysConcurrentPostDao.getListByEntity(sysConcurrentPost); boolean
		 * spost_apply_menu=false; for(int i=0;i<sPostlist.size();i++){
		 * parmMap.put("dept_id", sPostlist.get(i).getDept_id()); int
		 * s_apply_menu= pmPersonnelDao.getApplyInfobyDept(parmMap);//贷款申请菜单
		 * if(s_apply_menu==1){ spost_apply_menu=true; break; } }
		 */
		if (apply_menu == 1) {// 业务员不能看到核查的验房单与验房评估信息只能看到房屋照片
			reqMap.put("is_salesman", true);
		}

		reqMap.put("sortname", queryInfo.getSortname());
		reqMap.put("sortorder", queryInfo.getSortorder());
		if (queryInfo.getData_type_name() != null && "843".equals(queryInfo.getData_type_name())) {
            reqMap.put("data_detail_name", WmsHelp.DATA_ID_ATT_LIST);
		}
		List<Map<String, Object>> attlist = wmsCreHousingApplyAttDao.search(reqMap);// 附件

		if (null != attlist) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : attlist) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * RestTemplate restTemplate = new RestTemplate(); String url =
		 * PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") +
		 * "/loancheck/houseCreditWorkFlowViewMoa.do"; MultiValueMap<String,
		 * String> form = new LinkedMultiValueMap<String, String>();
		 * form.add("wms_cre_credit_head_id",
		 * queryInfo.getWms_cre_credit_head_id().toString()); map =
		 * restTemplate.postForObject(url, form,Map.class);
		 */
		map.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		map.put("sortname", "approval_time");
		map.put("sortorder", "asc");
		List<Map<String, Object>> worksList = wmsCreHousingApprovalInfoDao.searchAllInfo(map);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("ret_data", bean);
		resMap.put("attlist", attlist);
		if (worksList != null) {// 流程历程
			resMap.put("works", worksList);
		}
		return resMap;
	}

	/**
	 * Description : 获取贷款单据筛选信息
	 * 
	 * @param queryInfo
	 * @return record
	 * @author baisong
	 */
	public Map<String, Object> searchHouseLoanListUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 获取初始化单据状态
		// 本表字典表id
		if (null != queryInfo.getP_wms_sys_dict_data_id()) {
			reqMap.put("p_wms_sys_dict_data_id", queryInfo.getP_wms_sys_dict_data_id());
		}
		// 父表字典表id
		if (null != queryInfo.getWms_sys_dict_id()) {
			reqMap.put("wms_sys_dict_id", queryInfo.getWms_sys_dict_id());
		}
		// 排序字段
		if (null != queryInfo.getSortname() && !"".equals(queryInfo.getSortname())) {
			reqMap.put("sortname", queryInfo.getSortname());
		} else {// 如果没有排序字段则默认排序
			reqMap.put("sortname", "sort_order");
		}
		// 排序方式
		if (null != queryInfo.getSortorder() && !"".equals(queryInfo.getSortorder())) {
			reqMap.put("sortorder", queryInfo.getSortorder());
		} else {// 如果没有排序则默认排序
			reqMap.put("sortorder", "asc");
		}
		// 移动端-房贷管理-申请时间-第二版本
		reqMap.put("wms_sys_dict_id", "98");
		// 申请时间
		List<Map<String, Object>> application_time = wmssysdictdataDao.searchforApp(reqMap);
		// 移动端-房贷管理-单据状态-第二版本
		reqMap.put("wms_sys_dict_id", "131");
		// 单据状态
		List<Map<String, Object>> bill_status = wmssysdictdataDao.searchforApp(reqMap);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("menu_url", WmsHelp.MENU_URL_FCHC_LIST);// 菜单url
		paramMap.put("personnel_id", personnel.getPersonnel_id());
		// 获取当前登陆人的菜单权限
		Map<String, Object> childrenDeptMapFCHC = sysDeptDao.queryChildrenDeptInfoByUrl(paramMap);
		paramMap.put("menu_url", WmsHelp.MENU_URL_FKSP_LIST);// 菜单url
		// 获取当前登陆人的菜单权限
		Map<String, Object> childrenDeptMapFKSP = sysDeptDao.queryChildrenDeptInfoByUrl(paramMap);

		StringBuffer sb = new StringBuffer();
		// 判断权限是否为空
		if (childrenDeptMapFCHC != null && childrenDeptMapFCHC.get("childrendept") != null && !"".equals(childrenDeptMapFCHC.get("childrendept").toString())) {
			sb.append(childrenDeptMapFCHC.get("childrendept").toString());
		}
		// 判断权限是否为空
		if (childrenDeptMapFKSP != null && childrenDeptMapFKSP.get("childrendept") != null && !"".equals(childrenDeptMapFKSP.get("childrendept").toString())) {
			// 判断字符串是否为空
			if (sb != null && !"".equals(sb.toString())) {
				sb.append(",");
			}
			sb.append(childrenDeptMapFKSP.get("childrendept").toString());
		}
		// 记录当前初始化数据
		List<Map<String, Object>> listDept = new ArrayList<>();
		// 判断是否为空
		if (sb != null && !"".equals(sb.toString())) {

			Map<String, Object> map = sysDeptDao.getDeptInfo(personnel.getPersonnel_deptid());
			if (map == null || map.get("dept_level") == null) {
				retMap.put("", "error");
				return reqMap;
			}
			Map<String, Object> deptMapqb = new HashMap<>();
			deptMapqb.put("dept_name", "全部");// 部门名称--门店
			deptMapqb.put("dept_id", "");// 部门id--门店
			deptMapqb.put("is_current", "0");// 登陆人是否输入当前部门
			deptMapqb.put("business_group", new ArrayList<>());// 当前门店下面的业务组
			listDept.add(deptMapqb);
			// 获取登录人能负责的五级部门 例如营业一部
			Map<String, Object> deptMap5 = new HashMap<>();
			deptMap5.put("create_user_id", personnel.getPersonnel_id());
			deptMap5.put("dept_all", sb.toString());// 权限信息
			deptMap5.put("dept_level", WmsHelp.DEPT_LEVEL_5);// 5级部门等级
			deptMap5.put("dept_code", WmsHelp.DEPT_CODE_5);// 5级编码
			List<Map<String, Object>> listDept5 = sysDeptDao.getDept5(deptMap5);
			Map<String, Object> deptMap6 = new HashMap<>();
			deptMap6.put("create_user_id", personnel.getPersonnel_id());
			deptMap6.put("dept_all", sb.toString());
			deptMap6.put("dept_level", WmsHelp.DEPT_LEVEL_6);// 5级部门等级
			deptMap6.put("dept_code", WmsHelp.DEPT_CODE_6);// 5级编码
			List<Map<String, Object>> listDept6 = sysDeptDao.getDept6(deptMap6);
			if (listDept5 != null && listDept6 != null && listDept6.size() > 0) {
				// 部门集合
				Map<String, List<Map<String, Object>>> deptMap = new HashMap<String, List<Map<String, Object>>>();
				// 处理六级部门
				for (int i = 0; i < listDept6.size(); i++) {
					Map<String, Object> map6 = listDept6.get(i);
					String dept_pid = map6.get("dept_pid").toString();// 父部门id
					String dept_id = map6.get("dept_id").toString();// 部门id
					if (dept_id.equals(map.get("dept_id").toString())) {
						map6.put("is_current", "1");// 登陆人是当前部门
					}
					// 分组 根据上级部门进行分组
					if (deptMap.containsKey(dept_pid)) {
						deptMap.get(dept_pid).add(map6);
					} else {
						List<Map<String, Object>> deptList = new ArrayList<Map<String, Object>>();
						deptList.add(map6);
						deptMap.put(dept_pid, deptList);
					}
				}
				// 处理五级部门
				for (int i = 0; i < listDept5.size(); i++) {
					Map<String, Object> map5 = listDept5.get(i);
					String dept_id = map5.get("dept_id").toString();// 部门id
					if (dept_id.equals(map.get("dept_id").toString()) || dept_id.equals(map.get("dept_pid").toString())) {// 判断是否当前部门
						map5.put("is_current", "1");// 登陆人是当前部门
					}
					if (deptMap.containsKey(dept_id)) {
						List<Map<String, Object>> business_group = deptMap.get(dept_id);
						map5.put("business_group", business_group);// 添加业务组
						deptMap.remove(dept_id);// 移除已经添加的业务组
					}
				}
				if (deptMap != null && deptMap.size() > 0) {// 如果还有剩余没分配出去的部门则需要特殊处理
					for (Map.Entry<String, List<Map<String, Object>>> entry : deptMap.entrySet()) {
						String dept_id = entry.getKey();
						List<Map<String, Object>> deptList = entry.getValue();
						Map<String, Object> dept = sysDeptDao.getDeptInfoforApp(Integer.valueOf(dept_id));
						if (dept_id.equals(map.get("dept_id").toString())) {// 判断是否当前部门
							dept.put("is_current", "1");// 登陆人是当前部门
						}
						dept.put("business_group", deptList);// 添加业务组
						listDept5.add(dept);
					}
				}
				listDept.addAll(listDept5);
			}
		}
		// List<Map<String,Object>> listTeam=new ArrayList<>();
		// if(level==
		// Integer.valueOf(map.get("dept_level").toString())){//当前登陆人正好是门店经理
		// //当前人为门店经理则需要获取门店下面的所有业务组
		// List<Map<String,Object>>
		// listTeam=sysDeptDao.getLDeptInfoforApp(personnel.getPersonnel_deptid());
		// Map<String,Object> deptMap=new HashMap<>();
		// deptMap.put("dept_name", map.get("dept_name"));//部门名称--门店
		// deptMap.put("dept_id", map.get("dept_id"));//部门id--门店
		// deptMap.put("is_current", "1");//登陆人是否输入当前部门
		// deptMap.put("business_group", listTeam);//当前门店下面的业务组
		// listDept.add(deptMap);
		// }else
		// if(level<Integer.valueOf(map.get("dept_level").toString())){//当前登陆人职位小于门店经理
		// //当前人为门店经理则需要获取门店下面的所有业务组
		// listDept=sysDeptDao.getPDeptInfoforApp(personnel.getPersonnel_deptid());
		// if(listDept!=null&&listDept.size()>0){
		// List<Map<String,Object>> listTeam=new ArrayList<>();
		// //当前人为门店经理则需要获取门店下面的所有业务组
		// Map<String,Object>
		// mapTeam=sysDeptDao.getDeptInfoforApp(personnel.getPersonnel_deptid());
		// listTeam.add(mapTeam);
		// listDept.get(0).put("business_group", listTeam);//当前门店下面的业务组
		// listDept.get(0).put("is_current", "1");//登陆人是否输入当前业务组
		// }
		// }else
		// if(level>Integer.valueOf(map.get("dept_level").toString())){//当前登录人大于门店经理
		// if(levelcp==Integer.valueOf(map.get("dept_level").toString())){//如果当前登陆人是公司负责人
		// //公司负责人所在公司下面的门店
		// listDept=sysDeptDao.getLDeptInfoforDApp(personnel.getPersonnel_deptid());
		// if(listDept!=null&&listDept.size()>0){
		// for(int i=0;i<listDept.size();i++){
		// //当前人为门店经理则需要获取门店下面的所有业务组
		// List<Map<String,Object>>
		// listTeam=listTeam=sysDeptDao.getLDeptInfoforApp(Integer.valueOf(listDept.get(i).get("dept_id").toString()));
		// listDept.get(i).put("business_group", listTeam);
		// }
		// }
		// }else
		// if(levelcp>Integer.valueOf(map.get("dept_level").toString())){//当前登陆人为中心负责人或者高于负责人
		// //获取所有公司
		// List<Map<String,Object>>
		// listCp=sysDeptDao.getLAllDeptInfoforApp(87);//87为贷款城市业务部心主键/--获取各个分公司
		// List<Map<String,Object>> listdeptAll=new ArrayList<>();
		// Map<String,Object> deptMapqbAll=new HashMap<>();
		// deptMapqbAll.put("dept_name", "全部");//部门名称--门店
		// deptMapqbAll.put("dept_id", "");//部门id--门店
		// deptMapqbAll.put("is_current", "0");//登陆人是否输入当前部门
		// deptMapqbAll.put("business_group", new ArrayList<>());//当前门店下面的业务组
		// listdeptAll.add(deptMapqb);
		// for(int i=0;i<listCp.size();i++){
		// //当前人为门店经理则需要获取门店
		// listDept=sysDeptDao.getLDeptInfoforDApp(Integer.valueOf(listCp.get(i).get("dept_id").toString()));
		// for(int j=0;j<listDept.size();j++){
		// //当前人为门店经理则需要获取门店下面的所有业务组
		// List<Map<String,Object>>
		// listTeam=listTeam=sysDeptDao.getLDeptInfoforApp(Integer.valueOf(listDept.get(j).get("dept_id").toString()));
		// listDept.get(j).put("business_group", listTeam);//当前门店下面的业务组
		//
		// }
		// listdeptAll.addAll(listDept);
		// }
		// listDept=listdeptAll;
		// }
		// }
		retMap.put("application_time", application_time);// 时间
		retMap.put("bill_status", bill_status);// 状态
		retMap.put("store_value", listDept);// 门店信息
		return retMap;
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
	public Map<String, Object> getHomePageInfoUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		Map<String, Object> reqMapInfo = new HashMap<String, Object>();
		List<String> list = pmPersonnelDao.getJurisdictionInfo(personnel.getPersonnel_id());// 当前登陆人的菜单权限
		reqMap.put("salesman_id", personnel.getPersonnel_id());// 登陆人id
		reqMap.put("salesman_dept_id", personnel.getPersonnel_deptid());// 登陆人部门id
		boolean is_fchc = false;// 是否有房产核查菜单权限 false为否
		boolean is_fksp = false;// 是否有放款审批菜单权限 false为否
		// 判断是否有房产核查权限
		if (list.contains(WmsHelp.MENU_ID_FCHC_LIST)) {
			// 房产核查权限
			reqMap.put("menu_id", WmsHelp.MENU_ID_FCHC_LIST); // 菜单id
			Map<String, Object> FCHCchildrenDeptMap = wmsCreCreditHeadDao.queryChildrenDeptInfo(reqMap);
			reqMap.put("childrendeptfchc", FCHCchildrenDeptMap.get("childrendept"));
			reqMap.put("is_fchc", "1");
			is_fchc = true;
		} else {
			reqMap.put("childrendeptfchc", "");
		}
		// 判断是否有房产放款审批权限
		if (list.contains(WmsHelp.MENU_ID_FKSP_LIST)) {
			// 放款审批权限
			reqMap.put("menu_id", WmsHelp.MENU_ID_FKSP_LIST); // 菜单id
			Map<String, Object> FKSPchildrenDeptMap = wmsCreCreditHeadDao.queryChildrenDeptInfo(reqMap);
			reqMap.put("childrendeptfksp", FKSPchildrenDeptMap.get("childrendept"));
			reqMap.put("is_fksp", "1");
			is_fksp = true;
		} else {
			reqMap.put("childrendeptfksp", "");
		}

		// 排序字段
		if (null != queryInfo.getSortname() && !"".equals(queryInfo.getSortname())) {
			reqMap.put("sortname", queryInfo.getSortname());
		} else {// 如果没有排序字段则默认排序
			reqMap.put("sortname", "create_timestamp_str");
			reqMap.put("sortorder", "desc");
		}

		// **********待办事项****************//
		// 查询表内数据--待办事项LIST
		List<Map<String, Object>> todo_list = new ArrayList<>();
		todo_list = wmsCreCreditHeadDao.getHomePageInfoUp_Request(reqMap);

		// **********进展中的单据****************//
		reqMap.put("salesman_id", personnel.getPersonnel_id());// 登陆人id
		reqMap.put("salesman_dept_id", personnel.getPersonnel_deptid());// 登陆人部门id
		reqMap.put("hprocess_time", WmsMoaHelp.PERFECTHOUSINGLOANPROCESS_TIME);
		reqMap.put("menu_id", WmsHelp.MENU_ID_PROGRESS_LIST); // 菜单id
		// 查询表内进展中的单据LIST
		if (queryInfo.getIs_need_paging() != null) {
			if (queryInfo.getIs_need_paging() == 1) {
				reqMap.put("offset", queryInfo.getOffset());
				reqMap.put("pagesize", queryInfo.getPagesize());
			} else {
				reqMap.put("offset", null);
				reqMap.put("pagesize", null);
			}
		} else {
			reqMap.put("offset", queryInfo.getOffset());
			reqMap.put("pagesize", queryInfo.getPagesize());
		}
		// 子部门信息
		/*
		 * Map<String, Object> childrenDeptMap=
		 * wmsCreCreditHeadDao.queryChildrenDeptInfo(reqMap);
		 * reqMap.put("childrendept",childrenDeptMap.get("childrendept"));
		 */
		Map<String, Object> paramMap = new HashMap<>();
		Map<String, Object> childrenDeptMapFCHC = new HashMap<>();
		Map<String, Object> childrenDeptMapFKSP = new HashMap<>();
		if (is_fchc) {
			paramMap.put("menu_url", WmsHelp.MENU_URL_FCHC_LIST);// 菜单url
			paramMap.put("personnel_id", personnel.getPersonnel_id());
			// 获取当前登陆人的菜单权限
			childrenDeptMapFCHC = sysDeptDao.queryChildrenDeptInfoByUrl(paramMap);
		}
		if (is_fksp) {
			paramMap.put("menu_url", WmsHelp.MENU_URL_FKSP_LIST);// 菜单url
			paramMap.put("personnel_id", personnel.getPersonnel_id());
			// 获取当前登陆人的菜单权限
			childrenDeptMapFKSP = sysDeptDao.queryChildrenDeptInfoByUrl(paramMap);
		}
		StringBuffer sb = new StringBuffer();
		// 判断权限是否为空
		if (is_fchc && childrenDeptMapFCHC != null && childrenDeptMapFCHC.get("childrendept") != null && !"".equals(childrenDeptMapFCHC.get("childrendept").toString())) {
			sb.append(childrenDeptMapFCHC.get("childrendept").toString());
		}
		// 判断权限是否为空
		if (is_fksp && childrenDeptMapFKSP != null && childrenDeptMapFKSP.get("childrendept") != null && !"".equals(childrenDeptMapFKSP.get("childrendept").toString())) {
			// 判断字符串是否为空
			if (sb != null && !"".equals(sb.toString())) {
				sb.append(",");
			}
			sb.append(childrenDeptMapFKSP.get("childrendept").toString());
		}
		reqMap.put("childrendept", sb.toString());
		reqMap.put("create_user_id", personnel.getPersonnel_id());
		List<Map<String, Object>> in_progress_list = wmsCreCreditHeadDao.getHomePageInfoProUp_Request(reqMap);
		if (null != in_progress_list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : in_progress_list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		reqMapInfo.put("todo_list", todo_list);
		reqMapInfo.put("in_progress_list", in_progress_list);
		if (reqMapInfo.get("result") == null || "".equals(reqMapInfo.get("result"))) {//
			reqMapInfo.put("result", "success");//
		}
		return reqMapInfo;
	}

	/**
	 * Description : 获取房产核查列表(接口文档3.2)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@Override
	public List<Map<String, Object>> getWmsHousingCertificatesList_RequestUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		/*
		 * //区分新旧流程数据参数 根据版本发布时间区分 paramMap.put("hprocess_time",
		 * WmsMoaHelp.PERFECTHOUSINGLOANPROCESS_TIME);
		 * 
		 * boolean roleval = false; // 获取该用户的角色信息 List<String> roleList =
		 * wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id()); for
		 * (String role : roleList) { // 判断该用户的角色 if (role.equals("贷前办件复核员")) {
		 * //是办件人员 roleval= true; } } if(!roleval) { return new ArrayList<>(); }
		 */
		// 单据编码
		if (StringUtil.isNotBlank(queryInfo.getBill_code())) {
			paramMap.put("bill_code", "%" + queryInfo.getBill_code() + "%");
		}
		// 客户名称
		if (StringUtil.isNotBlank(queryInfo.getCustomer_name())) {
			paramMap.put("customer_name", "%" + queryInfo.getCustomer_name() + "%");
		}
		// 身份证号
		if (StringUtil.isNotBlank(queryInfo.getId_card())) {
			paramMap.put("id_card", "%" + queryInfo.getId_card() + "%");
		}
		// 手机号码
		if (StringUtil.isNotBlank(queryInfo.getMobile_telephone())) {
			paramMap.put("mobile_telephone", "%" + queryInfo.getMobile_telephone() + "%");
		}
		// 单据状态(待办件)
		paramMap.put("bill_status", "D");
		paramMap.put("house_appro_result", "1");
		// 多个字段like
		if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
			paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
		}

		if (StringUtils.isNotEmpty(queryInfo.getSortname())) {
			paramMap.put("sortname", "create_timestamp_order");
			if ("0".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "asc");
			} else if ("1".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "desc");
			}
		} else {
			paramMap.put("sortname", "create_timestamp_order");
			paramMap.put("sortorder", "desc");
		}

		if (queryInfo.getIs_need_paging() != null) {
			if (queryInfo.getIs_need_paging() == 1) {
				paramMap.put("offset", queryInfo.getOffset());
				paramMap.put("pagesize", queryInfo.getPagesize());
			} else {
				paramMap.put("offset", null);
				paramMap.put("pagesize", null);
			}
		} else {
			paramMap.put("offset", queryInfo.getOffset());
			paramMap.put("pagesize", queryInfo.getPagesize());
		}
		paramMap.put("salesman_id", personnel.getPersonnel_id());// 登陆人id
		paramMap.put("salesman_dept_id", personnel.getPersonnel_deptid());// 登陆人部门id
		paramMap.put("menu_id", WmsHelp.MENU_ID_FCHC_LIST); // 菜单id
		// 子部门信息
		Map<String, Object> childrenDeptMap = wmsCreCreditHeadDao.queryChildrenDeptInfo(paramMap);
		paramMap.put("childrendept", childrenDeptMap.get("childrendept"));
		// v2.1.0新增 认领状态
		paramMap.put("operation_type", queryInfo.getOperation_type());
		List<Map<String, Object>> list = wmsCreCreditHeadDao.getWmsHousingCertificatesList_RequestUp(paramMap);

		if (null != list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		return list;
	}

	/**
	 * Description : 待房产核查单据核查结果(接口文档3.3)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public Map<String, Object> savePropertyVerificationInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {

		// RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> resMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		List<WmsCreHousingApplyAtt> attList = new ArrayList<>();
		if (queryInfo.getFile_info_json() != null && !"".equals(queryInfo.getFile_info_json()) && !"null".equals(queryInfo.getFile_info_json()) && !"[]".equals(queryInfo.getFile_info_json())) {
			attList = gson.fromJson(queryInfo.getFile_info_json(), new TypeToken<List<WmsCreHousingApplyAtt>>() {
			}.getType());
			if (null != attList) {
				for (WmsCreHousingApplyAtt bean : attList) {
					bean.setAttachment_new_name(bean.getAttachment_address().substring(0, bean.getAttachment_address().lastIndexOf(".")));
					bean.setAttachment_type(bean.getAttachment_address().substring(bean.getAttachment_address().lastIndexOf(".") + 1));
				}
			}
		}
		// MultiValueMap<String, Object> form = new LinkedMultiValueMap<String,
		// Object>();
		// form.add("file_info_json", gson.toJson(attList));
		// form.add("personnel_id", personnel.getPersonnel_id().toString());
		// form.add("wms_cre_credit_head_id",
		// queryInfo.getWms_cre_credit_head_id().toString());
		// form.add("pass", queryInfo.getPass());
		// form.add("advice", queryInfo.getAdvice());
		//
		// form.add("interface_num",
		// "WMS_OUT_POSTsavePropertyVerificationInfoUp");
		// form.add("sys_num", "MIF");
		// resMap = restTemplate.postForObject(
		// PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"),
		// form, Map.class);
		//

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("file_info_json", gson.toJson(attList)));
		nvps.add(new BasicNameValuePair("personnel_id", personnel.getPersonnel_id().toString()));
		nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString()));
		nvps.add(new BasicNameValuePair("pass", queryInfo.getPass()));
		nvps.add(new BasicNameValuePair("advice", queryInfo.getAdvice()));
		nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTsavePropertyVerificationInfoUp"));
		nvps.add(new BasicNameValuePair("sys_num", "MIF"));
		nvps.add(new BasicNameValuePair("is_save", queryInfo.getIs_save()));

		try {
			resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", e.getMessage());
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
		return resMap;
	}

	/**
	 * 
	 * @Title: sendClaimOperUp
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param queryInfo
	 * @param personnel
	 * @return
	 * @author: ZhangWei
	 * @time:2017年6月1日 上午10:33:56 history: 1、2017年6月1日 ZhangWei 创建方法
	 */
	public Map<String, Object> sendClaimOperUp(WmsCreHousingOperationLogBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString()));
		nvps.add(new BasicNameValuePair("operation_user_id", personnel.getPersonnel_id().toString()));
		nvps.add(new BasicNameValuePair("operation_user_deptid", personnel.getPersonnel_deptid().toString()));
		nvps.add(new BasicNameValuePair("operation_type", queryInfo.getOper_type()));
		nvps.add(new BasicNameValuePair("operation_reason", queryInfo.getOperation_reason()));
		nvps.add(new BasicNameValuePair("sys_num", "MIF"));
		nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTClaimOperUp"));
		try {
			resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
			resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", "操作失败!");
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
	public List<Map<String, Object>> searchHouseLoanListUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 区分新旧流程数据参数 根据版本发布时间区分
		paramMap.put("hprocess_time", WmsMoaHelp.PERFECTHOUSINGLOANPROCESS_TIME);
		paramMap.put("create_user_id", personnel.getPersonnel_id());
		paramMap.put("salesman_dept_id", personnel.getPersonnel_deptid());

		paramMap.put("is_readonly", "0");// 设置可以修改

		List<String> listPm = pmPersonnelDao.getJurisdictionInfo(personnel.getPersonnel_id());// 当前登陆人的菜单权限
		// 判断是否有房产核查权限
		if (listPm.contains(WmsHelp.MENU_ID_FCHC_LIST)) {
			paramMap.put("is_fchc", "1");//
		}
		// 判断是否有房产审核权限
		if (listPm.contains(WmsHelp.MENU_ID_FKSP_LIST)) {
			paramMap.put("is_fksp", "1");//
		}

		/*
		 * // 获取该用户的角色信息 List<String> roleList =
		 * wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id()); for
		 * (String role : roleList) { // 判断该用户的角色 if (role.equals("贷前办件复核员")) {
		 * //是办件人员 paramMap.put("is_readonly", "1");//设置不可以修改 } }
		 */
		// 多个字段like
		if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
			paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
		}
		// 申请时间
		if (StringUtils.isNotBlank(queryInfo.getApplication_time())) {
			paramMap.put("application_time", queryInfo.getApplication_time());
		}
		// 单据状态
		if (StringUtil.isNotBlank(queryInfo.getBill_status())) {
			paramMap.put("bill_status", queryInfo.getBill_status());
		}
		// 业务组
		if (StringUtils.isNotBlank(queryInfo.getTeam_id())) {
			paramMap.put("team_id", queryInfo.getTeam_id());
		}
		// 门店
		if (StringUtils.isNotBlank(queryInfo.getDept_id())) {
			paramMap.put("dept_id", queryInfo.getDept_id());
		}
		// 排序
		if (StringUtils.isNotEmpty(queryInfo.getSortname())) {
			paramMap.put("sortname", "create_timestamp_order");
			if ("0".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "asc");
			} else if ("1".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "desc");
			}
		} else {
			paramMap.put("sortname", "create_timestamp_order");
			paramMap.put("sortorder", "desc");
		}

		if (queryInfo.getIs_need_paging() != null) {
			if (queryInfo.getIs_need_paging() == 1) {
				paramMap.put("offset", queryInfo.getOffset());
				paramMap.put("pagesize", queryInfo.getPagesize());
			} else {
				paramMap.put("offset", null);
				paramMap.put("pagesize", null);
			}
		} else {
			paramMap.put("offset", queryInfo.getOffset());
			paramMap.put("pagesize", queryInfo.getPagesize());
		}

		StringBuffer sb = new StringBuffer();
        paramMap.put("personnel_id", personnel.getPersonnel_id());
		// 房产核查
		if (paramMap.get("is_fchc") != null && "1".equals(paramMap.get("is_fchc").toString())) {
			paramMap.put("menu_url", WmsHelp.MENU_URL_FCHC_LIST);// 菜单url
			// paramMap.put("personnel_id", paramMap.get("create_user_id"));

			// 获取当前登陆人的菜单权限
			Map<String, Object> childrenDeptMapFCHC = sysDeptDao.queryChildrenDeptInfoByUrl(paramMap);
			// 判断权限是否为空
			if (childrenDeptMapFCHC != null && childrenDeptMapFCHC.get("childrendept") != null && !"".equals(childrenDeptMapFCHC.get("childrendept").toString())) {
				sb.append(childrenDeptMapFCHC.get("childrendept").toString());
			}
		}
		// 放款审核
		if (paramMap.get("is_fksp") != null && "1".equals(paramMap.get("is_fksp").toString())) {
			paramMap.put("menu_url", WmsHelp.MENU_URL_FKSP_LIST);// 菜单url
			// 获取当前登陆人的菜单权限
			Map<String, Object> childrenDeptMapFKSP = sysDeptDao.queryChildrenDeptInfoByUrl(paramMap);
			// 判断权限是否为空
			if (childrenDeptMapFKSP != null && childrenDeptMapFKSP.get("childrendept") != null && !"".equals(childrenDeptMapFKSP.get("childrendept").toString())) {
				// 判断字符串是否为空
				if (sb != null && !"".equals(sb.toString())) {
					sb.append(",");
				}
				sb.append(childrenDeptMapFKSP.get("childrendept").toString());
			}
		}
		paramMap.put("childrendept", sb.toString());
		List<Map<String, Object>> list = wmsCreCreditHeadDao.searchHouseLoanListUp(paramMap);

		if (null != list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}
		return list;
	}

	/**
	 * Description : 获取补录初始化信息和数据字典表(接口文档3.8)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public Map<String, Object> getDataDictUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		Map<String, Object> resMap = new HashMap<String, Object>();

		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		resMap = wmsCreCreditHeadDao.dispMakeUpInfo(paramMap);

		WmsSysDictDataSearchBeanVO wmsSysDictDataSearchBeanVO = new WmsSysDictDataSearchBeanVO();
		if (null != queryInfo.getP_wms_sys_dict_data_id()) {
			wmsSysDictDataSearchBeanVO.setP_wms_sys_dict_data_id(queryInfo.getP_wms_sys_dict_data_id());
		}
		if (null != queryInfo.getWms_sys_dict_id()) {
			wmsSysDictDataSearchBeanVO.setWms_sys_dict_id(queryInfo.getWms_sys_dict_id());
		}
		// 如果是贷款申请只需要查询出房产证
		if (queryInfo.getP_wms_sys_dict_data_id() != null && queryInfo.getP_wms_sys_dict_data_id() == 843) {
			wmsSysDictDataSearchBeanVO.setWms_sys_dict_data_id(846);// 房产证
		}
		List<Map<String, Object>> sysDictDataList = this.getDataDict(wmsSysDictDataSearchBeanVO);
		// 查询客户年龄list
		wmsSysDictDataSearchBeanVO.setP_wms_sys_dict_data_id(null);
		wmsSysDictDataSearchBeanVO.setWms_sys_dict_id(130);
		wmsSysDictDataSearchBeanVO.setWms_sys_dict_data_id(null);// 房产证
		List<Map<String, Object>> sysDictDataAgeList = this.getDataDict(wmsSysDictDataSearchBeanVO);

		if (null == resMap) {
			resMap = new HashMap<String, Object>();
		}
		resMap.put("is_readonly", "0");// 设置可以修改
		if (resMap != null && resMap.get("salesman_id") != null && !personnel.getPersonnel_id().toString().equals(resMap.get("salesman_id").toString())) {
			List<String> list = pmPersonnelDao.getJurisdictionInfo(personnel.getPersonnel_id());// 当前登陆人的菜单权限
			// 判断是否有房产核查权限
			if (list.contains(WmsHelp.MENU_ID_FCHC_LIST)) {
				resMap.put("is_readonly", "1");// 设置不可以修改
			}
		}

		/*
		 * // 获取该用户的角色信息 List<String> roleList =
		 * wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id()); for
		 * (String role : roleList) { // 判断该用户的角色 if (role.equals("贷前办件复核员")) {
		 * //是办件人员 resMap.put("is_readonly", "1");//设置不可以修改 } }
		 */
		resMap.put("ret_data", sysDictDataList);
		resMap.put("customer_age_list", sysDictDataAgeList);// 客户年龄list下拉框值
		if (resMap.get("city") == null) {
			// resMap.put("city", personnel.getPersonnel_account());
			try {
				Map<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("personnel_regionNumber", personnel.getPersonnel_regionnumber());
				Map<String, Object> cityMap = wmssysdictdataDao.getCityInfo(pMap);
				// 新增城市编码替换
				resMap.put("city", cityMap.get("city"));
			} catch (Exception e) {
				e.printStackTrace();
				resMap.put("city", "");
			}
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
	public Map<String, Object> isSureCertificateUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		paramMap.put("create_user_id", personnel.getPersonnel_id());// 设置可以修改
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("is_readonly", "0");// 设置可以修改
		paramMap.put("is_readonly", "0");// 设置不可以修改

		List<String> list = pmPersonnelDao.getJurisdictionInfo(personnel.getPersonnel_id());// 当前登陆人的菜单权限
		// 判断是否有房产核查权限
		if (list.contains(WmsHelp.MENU_ID_FCHC_LIST)) {
			// 是办件人员
			resMap.put("is_readonly", "1");// 设置不可以修改
			paramMap.put("is_readonly", "1");// 设置不可以修改
		}
		/*
		 * // 获取该用户的角色信息 List<String> roleList =
		 * wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id()); for
		 * (String role : roleList) { // 判断该用户的角色 if (role.equals("贷前办件复核员")) {
		 * //是办件人员 resMap.put("is_readonly", "1");//设置不可以修改
		 * paramMap.put("is_readonly", "1");//设置不可以修改 } }
		 */
		resMap = this.wmsCreCreditHeadDao.isSureCertificateUp(paramMap);
		return resMap;
	}

	/**
	 * Description : 获取放款申请信息列表(接口文档3.14)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public List<Map<String, Object>> searchLoanApplicationListUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		/*
		 * Map<String, Object> map = new HashMap<String, Object>(); RestTemplate
		 * restTemplate = new RestTemplate(); String url =
		 * PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") +
		 * "/cremanage/getHosuingIdListTwoMoa.do"; MultiValueMap<String, String>
		 * form = new LinkedMultiValueMap<String, String>();
		 * form.add("personnel_id", personnel.getPersonnel_id().toString());
		 * form.add("invekey", "10"); map = restTemplate.postForObject(url,
		 * form, Map.class); if(map == null || map.get("idList") == null||
		 * ((List<String>)map.get("idList")).size() == 0) { return new
		 * ArrayList<Map<String, Object>>(); } else { paramMap.put("idList",
		 * map.get("idList")); }
		 * 
		 * // 获取该用户的角色信息 List<String> roleList =
		 * wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id()); for
		 * (String role : roleList) {
		 * 
		 * }
		 */
		paramMap.put("create_user_id", personnel.getPersonnel_id());
		// 多个字段like
		if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
			paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
		}
		// 单据状态
		if (StringUtil.isNotBlank(queryInfo.getBill_status())) {
			paramMap.put("bill_status", queryInfo.getBill_status());
		}
		if (StringUtil.isNotBlank(personnel.getPersonnel_regionnumber())) {// 区域编码
			paramMap.put("create_user_city_code", personnel.getPersonnel_regionnumber());
		}
		// 排序
		if (StringUtils.isNotEmpty(queryInfo.getSortname())) {
			paramMap.put("sortname", "create_timestamp_order");
			if ("0".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "asc");
			} else if ("1".equals(queryInfo.getSortname())) {
				paramMap.put("sortorder", "desc");
			}
		} else {
			paramMap.put("sortname", "create_timestamp_order");
			paramMap.put("sortorder", "desc");
		}

		if (queryInfo.getIs_need_paging() != null) {
			if (queryInfo.getIs_need_paging() == 1) {
				paramMap.put("offset", queryInfo.getOffset());
				paramMap.put("pagesize", queryInfo.getPagesize());
			} else {
				paramMap.put("offset", null);
				paramMap.put("pagesize", null);
			}
		} else {
			paramMap.put("offset", queryInfo.getOffset());
			paramMap.put("pagesize", queryInfo.getPagesize());
		}

		List<Map<String, Object>> list = wmsCreCreditHeadDao.searchLoanApplicationListUp(paramMap);

		if (null != list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		return list;
	}

	/**
	 * Description : 获取放款初始化信息(接口文档3.15)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public Map<String, Object> getLoanApprovalInitiInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());

		resMap = this.wmsCreCreditHeadDao.getLoanApprovalInitiInfoUp(paramMap);

		if (resMap == null) {
			resMap = new HashMap<String, Object>();
		}

		// 借款期数
		Map<String, Object> borrow_deadlineMap = new HashMap<String, Object>();
		// 借款利率
		Map<String, Object> borrow_interestMap = new HashMap<String, Object>();

		borrow_deadlineMap.put("1", Arrays.asList(12, 24, 36, 48, "其他"));
		borrow_deadlineMap.put("2", Arrays.asList(3, 6, 12, "其他"));

		borrow_interestMap.put("1", Arrays.asList("1.39", "1.49", "1.59", "1.69", "其他"));
		borrow_interestMap.put("2", Arrays.asList("1.49", "1.79", "2.09", "2.49", "2.39", "1.51", "2.00", "其他"));

		resMap.put("borrow_deadlineMap", borrow_deadlineMap);
		resMap.put("borrow_interestMap", borrow_interestMap);

		if (resMap.get("remark") == null || resMap.get("remark").equals("")) {
			resMap.put("remark", "先息后本：转账金额=合同金额-手续费-他项费用-加急费用-一个月月息\n等额本息：转账金额=借款本金-手续费-他项费用-加急费用");
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
	public Map<String, Object> sendLoanApprovalInfoUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Gson gson = new Gson();

		resMap.put("house_building_area", queryInfo.getHouse_building_area());// 房产面积
		resMap.put("payment_contract_type", queryInfo.getPayment_contract_type());// 还款方式
		if (queryInfo.getBorrow_deadline() == null || StringUtils.isEmpty(queryInfo.getBorrow_deadline().toString())) {
			resMap.put("borrow_deadline", 0);// 借款期数
		} else {
			resMap.put("borrow_deadline", new Integer(queryInfo.getBorrow_deadline().toString()));// 借款期数
		}
		resMap.put("borrow_interest", queryInfo.getBorrow_interest());// 借款利率
		resMap.put("liquidated_damages", queryInfo.getLiquidated_damages());// 日滞纳金
		resMap.put("fees", queryInfo.getFees());// 手续费率
		resMap.put("it_cost_fee", queryInfo.getIt_cost_fee());// 他项费
		resMap.put("expedited_fee", queryInfo.getExpedited_fee());// 加急费
		resMap.put("borrow_begin_date", queryInfo.getBorrow_begin_date());// 起始日期
		resMap.put("borrow_end_date", queryInfo.getBorrow_end_date());// 终止日期
		resMap.put("deduction_of_interest", queryInfo.getDeduction_of_interest());// 扣除利息
		resMap.put("principal_lower", queryInfo.getPrincipal_lower().multiply(new BigDecimal(10000)));// 合同金额
		resMap.put("loan_amount", queryInfo.getLoan_amount().multiply(new BigDecimal(10000)));// 转账金额
		resMap.put("notary_is_finish", queryInfo.getNotary_is_finish());// 公正
		resMap.put("he_is_finish", queryInfo.getHe_is_finish());// 他项
		resMap.put("remark", queryInfo.getRemark());// 备注
		resMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());// 贷款表主键
		resMap.put("user_id", personnel.getPersonnel_id());// 登录人id
		resMap.put("user_name", personnel.getPersonnel_name());// 登录人姓名

		List<NameValuePair> form = new ArrayList<NameValuePair>();
		form.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTsendLoanApprovalInfoUp"));
		form.add(new BasicNameValuePair("sys_num", "MIF"));

		form.add(new BasicNameValuePair("personnel_id", personnel.getPersonnel_id().toString()));
		form.add(new BasicNameValuePair("loanApprovalInfoJson", gson.toJson(resMap)));
		form.add(new BasicNameValuePair("Id_card_list", queryInfo.getId_card_list()));
		form.add(new BasicNameValuePair("bank_list", queryInfo.getBank_list()));
		form.add(new BasicNameValuePair("transfer_list", queryInfo.getTransfer_list()));
		form.add(new BasicNameValuePair("protocol_list", queryInfo.getProtocol_list()));

		try {
			resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), form, Map.class);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		return resMap;
	}

	/**
	 * Description : 获取放款申请确认信息(接口文档3.17)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	public Map<String, Object> getLoanApprovalConfirmUp(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 获取终审额度(查询贷款主表)
		WmsCreCreditHead wmsCreCreditHead = this.wmsCreCreditHeadDao.get(queryInfo.getWms_cre_credit_head_id());

		BigDecimal appro_limit = wmsCreCreditHead.getAppro_limit();// 终审额度
		Object house_building_area = queryInfo.getHouse_building_area();// 房产面积
		String payment_contract_type = queryInfo.getPayment_contract_type();// 还款方式
		Object borrow_deadline = queryInfo.getBorrow_deadline();// 借款期数
		BigDecimal borrow_interest = queryInfo.getBorrow_interest() == null ? new BigDecimal(0) : queryInfo.getBorrow_interest();// 借款利率
		BigDecimal liquidated_damages = queryInfo.getLiquidated_damages() == null ? new BigDecimal(0) : queryInfo.getLiquidated_damages();// 日滞纳金
		BigDecimal fees = queryInfo.getFees() == null ? new BigDecimal(0) : queryInfo.getFees();// 手续费率
		BigDecimal it_cost_fee = queryInfo.getIt_cost_fee() == null ? new BigDecimal(0) : queryInfo.getIt_cost_fee();// 他项费
		BigDecimal expedited_fee = queryInfo.getExpedited_fee() == null ? new BigDecimal(0) : queryInfo.getExpedited_fee();// 加急费
		String borrow_begin_date = queryInfo.getBorrow_begin_date();// 起始日期
		String borrow_end_date = queryInfo.getBorrow_end_date();// 终止日期
		BigDecimal deduction_of_interest = queryInfo.getDeduction_of_interest() == null ? new BigDecimal(0) : queryInfo.getDeduction_of_interest();// 扣除利息
		BigDecimal principal_lower = queryInfo.getPrincipal_lower() == null ? new BigDecimal(0) : queryInfo.getPrincipal_lower();// 合同金额
		BigDecimal loan_amount = queryInfo.getLoan_amount() == null ? new BigDecimal(0) : queryInfo.getLoan_amount();// 转账金额
		Integer notary_is_finish = queryInfo.getNotary_is_finish();// 公正
		Integer he_is_finish = queryInfo.getHe_is_finish();// 他项
		// 如果期数为空则变0
		if (borrow_deadline == null || StringUtils.isEmpty(borrow_deadline.toString())) {
			borrow_deadline = 0;
		}
		if (house_building_area == null || StringUtils.isEmpty(house_building_area.toString())) {
			house_building_area = 0;
		}

		java.text.DecimalFormat format_point_two = new java.text.DecimalFormat("0.00");
		java.text.DecimalFormat format_point_four = new java.text.DecimalFormat("0.0000");

		if ("1".equals(payment_contract_type)) {// 等额本息
			// 等额本息:合同金额=本金(终审额度)*利率*期数+本金
			principal_lower = appro_limit.multiply(new BigDecimal(10000)).multiply(borrow_interest).divide(new BigDecimal(100)).multiply(new BigDecimal(borrow_deadline.toString())).add(wmsCreCreditHead.getAppro_limit().multiply(new BigDecimal(10000))).divide(new BigDecimal(10000));
			// 转账金额=借款本金-手续费-他项费用-加急费用
			loan_amount = appro_limit.multiply(new BigDecimal(10000)).subtract(appro_limit.multiply(new BigDecimal(10000)).multiply(fees).divide(new BigDecimal(100))).subtract(it_cost_fee).subtract(expedited_fee).divide(new BigDecimal(10000));
		} else if ("2".equals(payment_contract_type)) {// 先息后本
			// 先息后本:本金=合同金额
			principal_lower = wmsCreCreditHead.getAppro_limit();
			// 转账金额=合同金额(就是终审额度)-手续费-他项费用-加急费用-一个月月息
			loan_amount = appro_limit.multiply(new BigDecimal(10000)).subtract(appro_limit.multiply(new BigDecimal(10000)).multiply(fees).divide(new BigDecimal(100))).subtract(it_cost_fee).subtract(expedited_fee).subtract(appro_limit.multiply(new BigDecimal(10000)).multiply(borrow_interest.divide(new BigDecimal(100)))).divide(new BigDecimal(10000));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		paramMap = this.wmsCreCreditHeadDao.getLoanApprovalInitiInfoUp(paramMap);
		if (paramMap != null && paramMap.get("deduction_of_interest") != null && queryInfo.getDeduction_of_interest() != null) {
			if (new BigDecimal(paramMap.get("deduction_of_interest").toString()).compareTo(queryInfo.getDeduction_of_interest()) == 0) {
				// 扣除利息=本金(终审额度(万元))*借款利率(不带百分号)
				deduction_of_interest = appro_limit.multiply(new BigDecimal(10000)).multiply(borrow_interest.divide(new BigDecimal(100)));
			} else {
				deduction_of_interest = queryInfo.getDeduction_of_interest();
			}
		} else {
			// 扣除利息=本金(终审额度(万元))*借款利率(不带百分号)
			deduction_of_interest = appro_limit.multiply(new BigDecimal(10000)).multiply(borrow_interest.divide(new BigDecimal(100)));
		}

		paramMap.get("deduction_of_interest");

		resMap.put("appro_limit", appro_limit);

		resMap.put("house_building_area", format_point_two.format(new Double(house_building_area.toString())));
		resMap.put("payment_contract_type", payment_contract_type);
		resMap.put("borrow_deadline", borrow_deadline);
		resMap.put("borrow_interest", borrow_interest);
		if (liquidated_damages != null) {
			resMap.put("liquidated_damages", format_point_two.format(liquidated_damages));
		}
		resMap.put("fees", fees);
		resMap.put("it_cost_fee", it_cost_fee);
		resMap.put("expedited_fee", expedited_fee);
		resMap.put("borrow_begin_date", borrow_begin_date);
		resMap.put("borrow_end_date", borrow_end_date);
		if (deduction_of_interest != null) {
			resMap.put("deduction_of_interest", format_point_two.format(deduction_of_interest));
		}
		if (principal_lower != null) {
			resMap.put("principal_lower", format_point_four.format(principal_lower));
		}
		if (loan_amount != null) {
			resMap.put("loan_amount", format_point_four.format(loan_amount));
		}
		resMap.put("notary_is_finish", notary_is_finish);
		resMap.put("he_is_finish", he_is_finish);
		resMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());

		return resMap;
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
	public Map<String, Object> getAuthorityUp_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> reqMap = new HashMap<String, Object>();

		List<String> list = pmPersonnelDao.getJurisdictionInfo(personnel.getPersonnel_id());// 当前登陆人的菜单权限
		// 贷款单据列表菜单
		reqMap.put("documents_menu", "0");
		// 判断是否有房产核查权限
		if (list.contains(WmsHelp.MENU_ID_FCHC_LIST)) {
			reqMap.put("check_menu", "1");
			// 贷款单据列表菜单
			reqMap.put("documents_menu", "1");
		} else {
			reqMap.put("check_menu", "0");
		}
		// 判断是否有房产放款审批权限
		if (list.contains(WmsHelp.MENU_ID_FKSP_LIST)) {
			reqMap.put("loan_appro_menu", "1");
			// 贷款单据列表菜单
			reqMap.put("documents_menu", "1");
		} else {
			reqMap.put("loan_appro_menu", "0");
		}
        // 判断是否有单据状态报表权限
        if (list.contains(WmsHelp.MENU_ID_ZTBB_LIST))
        {
            reqMap.put("status_form_menu", "1");
        }
        else
        {
            reqMap.put("status_form_menu", "0");
        }
        /*// 放款申请 --默认无权限
        reqMap.put("loan_appli_menu", "0");
        // 贷款申请菜单
        reqMap.put("apply_menu", "1");
        // 信息完善菜单
        reqMap.put("credit_confirm_menu", "1");*/

		return reqMap;
	}

	@Override
	public Map<String, Object> getUserAndSendInfo_Request(String sysSendInfoVO) {
		Map<String, Object> resMap = new HashMap<>();
		String reuslt = "success";
		// SysSendInfoVO sysSendInfo =JsonUtil.jsonStringToBean(sysSendInfoVO,
		// SysSendInfoVO.class);
		// List<String> sysSendInfolist =JsonUtil.jsonArrayToList(sysSendInfoVO,
		// String.class);
		SysSendInfoVO sysSendInfo = JsonUtil.jsonStringToBean(sysSendInfoVO, SysSendInfoVO.class);
		// 测试使用
		/*
		 * SysSendInfoVO sysSendInfo = new SysSendInfoVO(); List<String>
		 * user_code=new ArrayList<>(); user_code.add("201134");
		 * sysSendInfo.setUser_code(user_code);
		 * sysSendInfo.setMsg_code("20010"); Map<String,String> map=new
		 * HashMap<>(); map.put("bill_code", "FD160321000001");
		 * sysSendInfo.setMap(map); Map<String,Object> extras=new HashMap<>();
		 * extras.put("wms_cre_credit_head_id", 999);
		 * sysSendInfo.setExtras(extras);
		 */
		for (int i = 0; i < sysSendInfo.getUser_code().size(); i++) {
			// 如果是mis或者是mif需要同步下user_code
			if (sysSendInfo.getApp_name() != null && ("MIF".equals(sysSendInfo.getApp_name()) || "MIS".equals(sysSendInfo.getApp_name()))) {
				sysSendInfo.getExtras().put("user_code", sysSendInfo.getUser_code().get(i));
			}
			PushManage.pushMessageByCodeForWMS(sysSendInfo.getUser_code().get(i), sysSendInfo.getMsg_code(), sysSendInfo.getMap(), sysSendInfo.getExtras(), sysSendInfo.getApp_name());
		}
		resMap.put("reuslt", reuslt);
		return resMap;
	}

	/**
	 * 方法用途：用于发送消息极光推送消息
	 * 
	 * @param Map<String,Object>
	 *            map 当前参数中会传递多个数据 标示发送消息的情况和内容
	 * @param map中
	 *            role_value 传递节点角色名 各个节点名称 请查看WorkflowRoleHelp类
	 * @param map中
	 *            role_outside 如果是1则需要获取获取门店或者团队经理 除了流程节点角色以外的人需要发送消息
	 *            如：客户经理所在团队的团队经理、所在门店的门店负责人、客户经理本人
	 * @param map中post_number_list
	 *            是一个list 里面参数是标识查询客户经理 团队经理 门店经理的 'KHJL','TDJL','MDJL'
	 * @param map中msg_code
	 *            消息编码
	 * @param map中is_dis_area
	 *            如果传递1则将人员进行区域划分按照区域编码区分
	 * @param map中其他参数自定义
	 * @param 极光推送参数
	 *            msg_map 消息内容参数
	 * @param 极光推送参数
	 *            msg_extras 消息附加参数
	 * @return Map<String, Object> map 返回值中有一个reuslt标示成功失败其他值自定义
	 * @author baisong
	 */
	@Override
	public Map<String, Object> getUserAndSendInfoMOA_Request(Map<String, Object> map) {
		Map<String, Object> resMap = new HashMap<>();
		String reuslt = "success";
		if (map == null) {
			reuslt = "error";
			resMap.put("reuslt", reuslt);
			return resMap;
		}

		// 获取传进来的流程角色编码来查询当前角色下有哪些人
		List<Map<String, Object>> list = sysUserRoleDao.getRoleListUser(map);
		if (list == null) {// ||list.size()==0
			reuslt = "error";
			resMap.put("reuslt", reuslt);
			return resMap;
		}
		List<Map<String, Object>> listuser = new ArrayList<>();
		if ("1".equals(map.get("role_outside"))) {
			// 获取传进来的人员id获取客户经理团队经理门店经理 发送短信息时候 需要判断人员职务 'KHJL','TDJL','MDJL'
			listuser = sysUserRoleDao.getSuperiorAndoneself(map);
			if (list == null) {
				reuslt = "error";
				resMap.put("reuslt", reuslt);
				return resMap;
			}
			// 根据控制获取对应的人 并添加到角色的list中
			if (map.get("post_number_list") != null && ((List) map.get("post_number_list")).size() > 0) {
				List<String> listpost = (List) map.get("post_number_list");
				for (int i = 0; i < listpost.size(); i++) {
					for (int j = 0; j < listuser.size(); j++) {
						if (listpost.get(i).equals(listuser.get(j).get("post_number"))) {
							list.add(listuser.get(j));
						}
					}
				}
			}
		}
		boolean is_dis_area = true;
		if ("1".equals(map.get("is_dis_area"))) {// 是否需要区分地区
			is_dis_area = false;
			// 判断区域编码
			for (int i = 0; i < list.size(); i++) {
				if (is_dis_area || list.get(i).get("regionNumber").equals(map.get("regionNumber"))) {
					list.remove(i);// 移除不对应的人员
					i--;
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> mapInfo = list.get(i);
			PushManage.pushMessageByCodeForWMS(mapInfo.get("personnel_shortCode").toString(), map.get("msg_code").toString(), (Map) map.get("msg_map"), (Map) map.get("msg_extras"));
		}
		return resMap;
	}

	@Override
	public Map<String, Object> getPictureQuantity_Request(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paraMap = new HashMap<>();
		Map<String, Object> resMap = new HashMap<>();
		paraMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
		paraMap.put("data_type_name", queryInfo.getData_type_name());
		List<Map<String, Object>> list = wmsCreHousingApplyAttDao.getPictureQuantity(paraMap);
		resMap.put("result", list);
		return resMap;
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
	@Override
	public Map<String, Object> getlimitNumber() {
		Map<String, Object> res = new HashMap<>();
		WmsSysDictData wmssysdictdata = new WmsSysDictData();
		wmssysdictdata.setWms_sys_dict_id(101);
		List<WmsSysDictData> list = wmssysdictdataDao.getListByEntity(wmssysdictdata);
		if (list != null && list.size() > 0) {
			for (WmsSysDictData data : list) {
				res.put(data.getValue_meaning(), data.getValue_code());
			}
		}
		return res;
	}

	/**
	 * Description :3.35 获取待授信确认单据信息列表
	 * 
	 * @url /wms/getListInfoforCreditConfirm.do
	 * @param queryInfo
	 * @return Map
	 * @author jiaodelong
	 * @date 2016-10-16
	 */
	@Override
	public List<Map<String, Object>> getListInfoforCreditConfirm(TransmitValuesThreeVO vo) {
		Map<String, Object> paramMap = new HashMap<>();
		String sortorder = "desc";
		if (StringUtil.isNotBlank(vo.getSortname())) {

			if (vo.getSortname().equals("0")) {
				sortorder = "asc";
			} else {
				sortorder = "desc";
			}
		}
		paramMap.put("sortorder", sortorder);
		paramMap.put("sortname", "create_timestamp");
		paramMap.put("user_id", vo.getUser_id());
		if (vo.getIs_need_paging() != null && vo.getIs_need_paging() == 1) {
			paramMap.put("offset", vo.getOffset());
			paramMap.put("pagesize", vo.getPagesize());
		}
		if (vo.getMany_column_like() != null && !vo.getMany_column_like().equals("")) {
			paramMap.put("many_column_like", com.zx.moa.util.SysTools.getSqlLikeParam(vo.getMany_column_like()));
		}
		List<Map<String, Object>> list = wmsCreCreditHeadDao.getListInfoforCreditConfirm(paramMap);

		if (null != list) {
			// 添加图片完整地址(拼接接口名)
			for (Map<String, Object> map_bean : list) {
				map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
			}
		}

		return list;
	}

	/**
	 * Description : 3.42获取房产核查页面信息。
	 * 
	 * @url /wms/getHouseAssessmentState.do
	 * @param queryInfo
	 * @return Map
	 * @author baisong
	 * @date 2016-10-12
	 */
	@Override
	public Map<String, Object> getHouseAssessmentState(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> retMap = new HashMap<>();
		List<Map<String, Object>> list = wmsCreCreditHeadDao.getHouseAssessmentState(queryInfo.getWms_cre_credit_head_id());
		if (list != null && list.size() > 0) {
			retMap.put("assessment_sheet_state", 1);// 0 信息填写，1 信息填写（已填）
		} else {
			retMap.put("assessment_sheet_state", 0);// 0 信息填写，1 信息填写（已填)
		}

		if (queryInfo.getIs_collection() != null && queryInfo.getIs_collection().equals("1")) { // 是否补录初始化,0否，1是
			List<Map<String, Object>> listSpproval = wmsCreCreditHeadDao.getHousingSpprovalInfo(queryInfo.getWms_cre_credit_head_id());
			for (Map<String, Object> map_bean : listSpproval) {
				if ("notaccord".equals(map_bean.get("approval_result").toString())) {
					retMap.put("pass", 0); // 不符合进件标准
				} else if ("accord".equals(map_bean.get("approval_result").toString())) {
					retMap.put("pass", 1); // 符合进件标准（默认值）
				}
				retMap.put("advice", map_bean.get("approval_advice")); // 核查信息
			}
		}

		return retMap;
	}

	/**
	 * Description : 获取房产评估单基本信息初始化信息(接口文档3.38)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@Override
	public WmsCreCreditHeadSearchBeanVO initHouseAssessmentBasicInfoOne(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.putAll(this.wmsCreCreditHeadDao.initHouseAssessmentBasicInfoOne(paramMap));

		paramMap = new HashMap<String, Object>();
		paramMap.put("sortname", "wms_sys_dict_data_id");
		paramMap.put("sortorder", "asc");
		paramMap.put("offset", null);
		paramMap.put("pagesize", null);

		// 是否抵押
		paramMap.put("wms_sys_dict_id", 119);
		resMap.put("is_mortgage_list", this.wmssysdictdataDao.search(paramMap));
		// 采光
		paramMap.put("wms_sys_dict_id", 105);
		resMap.put("house_lighting_list", this.wmssysdictdataDao.search(paramMap));
		// 房屋类别
		paramMap.put("wms_sys_dict_id", 104);
		resMap.put("housing_category_list", this.wmssysdictdataDao.search(paramMap));
		// 装修状况
		paramMap.put("wms_sys_dict_id", 106);
		resMap.put("decoration_Standard_list", this.wmssysdictdataDao.search(paramMap));
		// 房屋格局
		paramMap.put("wms_sys_dict_id", 118);
		resMap.put("housing_pattern_list", this.wmssysdictdataDao.search(paramMap));
		// 房屋用途
		paramMap.put("wms_sys_dict_id", 107);
		resMap.put("house_usage_list", this.wmssysdictdataDao.search(paramMap));
		// 入住率
		paramMap.put("wms_sys_dict_id", 115);
		resMap.put("house_occupancy_rate_list", this.wmssysdictdataDao.search(paramMap));
		// 成交率
		paramMap.put("wms_sys_dict_id", 116);
		resMap.put("is_active_list", this.wmssysdictdataDao.search(paramMap));
		// 婚姻状况
		paramMap.put("wms_sys_dict_id", 18);
		resMap.put("marital_status_list", this.wmssysdictdataDao.search(paramMap));
		// 网上报价
		paramMap.put("wms_sys_dict_id", 117);
		resMap.put("online_fold_list", this.wmssysdictdataDao.search(paramMap));

		queryInfo.setResMap(resMap);
		return queryInfo;
	}

	/**
	 * Description : 获取房产评估单房屋信息初始化信息(接口文档3.39)
	 * 
	 * @param queryInfo
	 * @return map
	 * @author wangyihan
	 */
	@Override
	public WmsCreCreditHeadSearchBeanVO initHouseAssessmentBasicInfoTwo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.putAll(this.wmsCreCreditHeadDao.initHouseAssessmentBasicInfoTwo(paramMap));

		paramMap = new HashMap<String, Object>();
		paramMap.put("sortname", "wms_sys_dict_data_id");
		paramMap.put("sortorder", "asc");
		paramMap.put("offset", null);
		paramMap.put("pagesize", null);

		// 房屋朝向
		paramMap.put("wms_sys_dict_id", 108);
		resMap.put("housing_towards_list", this.wmssysdictdataDao.search(paramMap));
		// 园区管理
		paramMap.put("wms_sys_dict_id", 109);
		resMap.put("residential_manage_list", this.wmssysdictdataDao.search(paramMap));
		// 配套设施
		paramMap.put("wms_sys_dict_id", 110);
		resMap.put("facilities_list", this.wmssysdictdataDao.search(paramMap));
		// 共同居住
		paramMap.put("wms_sys_dict_id", 111);
		resMap.put("co_habitation_list", this.wmssysdictdataDao.search(paramMap));
		// 小区卫生
		paramMap.put("wms_sys_dict_id", 112);
		resMap.put("residential_environ_list", this.wmssysdictdataDao.search(paramMap));
		// 房屋洁净
		paramMap.put("wms_sys_dict_id", 113);
		resMap.put("house_cleanliness_list", this.wmssysdictdataDao.search(paramMap));

		queryInfo.setResMap(resMap);
		return queryInfo;
	}

	/**
	 * Description : 发送房产评估单基本信息详细信息(接口文档3.40)
	 * 
	 * @param queryInfo
	 * @return WmsCreCreditHeadSearchBeanVO
	 * @author wangyihan
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@Override
	public WmsCreCreditHeadSearchBeanVO sendHouseAssessmentBasicInfoOne(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) throws ClientProtocolException, IOException {
		Map<String, String> resMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		resMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString());// 单据id

		resMap.put("house_address_more", queryInfo.getHouse_address_more());// 房屋地址
		if (queryInfo.getHouse_building_area() != null) {
			resMap.put("house_building_area", queryInfo.getHouse_building_area().toString());// 房屋面积
		} else {
			resMap.put("house_building_area", "");// 房屋面积
		}
		resMap.put("community_name", queryInfo.getCommunity_name());// 小区名称

		resMap.put("total_floor", queryInfo.getTotal_floor());// 总楼层数
		resMap.put("house_layer", queryInfo.getHouse_layer());// 所在楼层
		resMap.put("is_mortgage", queryInfo.getIs_mortgage());// 是否抵押
		resMap.put("house_lighting", queryInfo.getHouse_lighting());// 采光
		resMap.put("housing_category", queryInfo.getHousing_category());// 房屋类别
		resMap.put("decoration_Standard", queryInfo.getDecoration_Standard());// 装修状况
		resMap.put("housing_pattern", queryInfo.getHousing_pattern());// 房屋格局
		resMap.put("house_usage", queryInfo.getHouse_usage());// 房屋用途
		resMap.put("house_occupancy_rate", queryInfo.getHouse_occupancy_rate());// 入住率
		resMap.put("is_active", queryInfo.getIs_active());// 成交率
		resMap.put("marital_status", queryInfo.getMarital_status());// 婚姻状况
		resMap.put("online_fold", queryInfo.getOnline_fold());// 网上报价
		resMap.put("rental_price", queryInfo.getRental_price());// 出租价格
		resMap.put("house_transaction_price", queryInfo.getHouse_transaction_price());// 成交价
		resMap.put("user_id", personnel.getPersonnel_id().toString());// 登录人id
		resMap.put("user_name", personnel.getPersonnel_name());// 登录人姓名

		resMap.put("house_buy_date", queryInfo.getHouse_buy_date());// 购买日期
		resMap.put("house_age", queryInfo.getBuilding_age());// 房屋年龄
		
		List<NameValuePair> form = new ArrayList<NameValuePair>();
		form.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTsendHouseAssessmentBasicInfoOne"));
		form.add(new BasicNameValuePair("sys_num", "MIF"));
		form.add(new BasicNameValuePair("sendHouseAssessmentBasicInfoOneJson", mapper.writeValueAsString(resMap)));
		// 调用平台
		resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), form, Map.class);

		queryInfo.setResMap1(resMap);
		return queryInfo;
	}

	/**
	 * Description : 发送房产评估单房屋信息详细信息(接口文档3.41)
	 * 
	 * @param queryInfo
	 * @return WmsCreCreditHeadSearchBeanVO
	 * @author wangyihan
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@Override
	public WmsCreCreditHeadSearchBeanVO sendHouseAssessmentBasicInfoTwo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) throws ClientProtocolException, IOException {
		Map<String, String> resMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		resMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString());// 单据id
		resMap.put("housing_towards", queryInfo.getHousing_towards());// 房屋朝向
		resMap.put("residential_manage", queryInfo.getResidential_manage());// 园区管理
		resMap.put("facilities", queryInfo.getFacilities());// 配套设施
		resMap.put("co_habitation", queryInfo.getCo_habitation());// 共同居住
		resMap.put("residential_environ", queryInfo.getResidential_environ());// 小区卫生
		resMap.put("house_cleanliness", queryInfo.getHouse_cleanliness());// 房屋洁净
		resMap.put("remark", queryInfo.getRemark());// 备注
		resMap.put("user_id", personnel.getPersonnel_id().toString());// 登录人id
		resMap.put("user_name", personnel.getPersonnel_name());// 登录人姓名

		List<NameValuePair> form = new ArrayList<NameValuePair>();
		form.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTsendHouseAssessmentBasicInfoTwo"));
		form.add(new BasicNameValuePair("sys_num", "MIF"));
		form.add(new BasicNameValuePair("sendHouseAssessmentBasicInfoTwoJson", mapper.writeValueAsString(resMap)));
		// 调用平台
		resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), form, Map.class);

		queryInfo.setResMap1(resMap);
		return queryInfo;
	}

}
