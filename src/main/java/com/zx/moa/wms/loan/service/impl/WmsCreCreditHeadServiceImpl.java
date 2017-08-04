package com.zx.moa.wms.loan.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zx.moa.util.SysUtil;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingFileInfo;
import com.zx.moa.wms.loan.persist.WmsCreCreditHeadDao;
import com.zx.moa.wms.loan.persist.WmsCreCreditRefuseDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingApplyAttDao;
import com.zx.moa.wms.loan.persist.WmsCreHousingFileInfoDao;
import com.zx.moa.wms.loan.persist.WmsSysDictDao;
import com.zx.moa.wms.loan.service.IWmsCreCreditHeadService;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsMoaHelp;
import com.zx.moa.wms.request.loan.util.WmsHelp;
import com.zx.platform.syscontext.PlatformGlobalVar;
import com.zx.platform.syscontext.util.StringUtil;

@Service("wmsCreCreditHeadService")
public class WmsCreCreditHeadServiceImpl implements IWmsCreCreditHeadService{

    @Autowired
    private WmsCreCreditHeadDao wmsCreCreditHeadDao;
    
    @Autowired
    private WmsCreHousingApplyAttDao wmsCreHousingApplyAttDao;
    
    @Autowired
    private WmsCreHousingFileInfoDao wmsCreHousingFileInfoDao;
    @Autowired
    private  WmsSysDictDao wmsSysDictDao;//字典表dao
    
    @Autowired
    private  WmsCreCreditRefuseDao wmsCreCreditRefuseDao;
    
    /**
     * 房产抵押列表查询
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> searchHouseLoanList(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //区分新旧流程数据参数 根据版本发布时间区分
        paramMap.put("hprocess_time", WmsMoaHelp.PERFECTHOUSINGLOANPROCESS_TIME);
        // 获取该用户的角色信息
        List<String> roleList = wmsSysDictDao.getUserRoleNameList(personnel.getPersonnel_id());
        for (String role : roleList)
        {
            // 判断该用户的角色
            if (role.equals("贷前办件复核员")) {
                //是办件人员
                paramMap.put("is_bj", "1");
            }
        }
        paramMap.put("create_user_id", personnel.getPersonnel_id());
        
        //单据编码
        if (StringUtil.isNotBlank(queryInfo.getBill_code())) {
            paramMap.put("bill_code", "%" + queryInfo.getBill_code() + "%");
        }
        //客户名称
        if (StringUtil.isNotBlank(queryInfo.getCustomer_name())) {
            paramMap.put("customer_name", "%" + queryInfo.getCustomer_name() + "%");
        }
        //身份证号
        if (StringUtil.isNotBlank(queryInfo.getId_card())) {
            paramMap.put("id_card", "%" + queryInfo.getId_card() + "%");
        }
        //手机号码
        if (StringUtil.isNotBlank(queryInfo.getMobile_telephone())) {
            paramMap.put("mobile_telephone", "%" + queryInfo.getMobile_telephone() + "%");
        }
        //单据状态
        if (StringUtil.isNotBlank(queryInfo.getBill_status())) {
            paramMap.put("bill_status", queryInfo.getBill_status());
        }
        //多个字段like
        if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
            paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
        }
        //时间段(近...个月的单据)
        if (StringUtils.isNotBlank(queryInfo.getPeriod()) ) {
            paramMap.put("period", queryInfo.getPeriod());
        }
        
        paramMap.put("sortname", queryInfo.getSortname());
        paramMap.put("sortorder", queryInfo.getSortorder());
        paramMap.put("offset", queryInfo.getOffset());
        paramMap.put("pagesize", queryInfo.getPagesize());

        List<Map<String, Object>> list = wmsCreCreditHeadDao.searchHouseLoanList(paramMap);
        
        //添加taskId
//        list = houseCreditWorkFlowService.addTaskIDHouse(list, (List<Integer>) paramMap.get("idList"), 
//                (List<String>) paramMap.get("taskIdList"));
//        GridDataBean<Map<String, Object>> bean = new GridDataBean<Map<String, Object>>(queryInfo.getPage(), 
//                wmsCreCreditHeadDao.searchHouseLoanCount(paramMap), list);

        return list;
    }
    
    @Override
	public List<Map<String, Object>> getwmsHousingCertificatesList(
			WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel user) {
		  Map<String, Object> paramMap = new HashMap<String, Object>();
		  boolean roleval= false;
	      // 获取该用户的角色信息
          List<String> roleList = wmsSysDictDao.getUserRoleNameList(user.getPersonnel_id());
          for (String role : roleList)
          {
            // 判断该用户的角色
            if (role.equals("贷前办件复核员")) {
                //是办件人员
            	roleval= true;
            }
          }
          if(!roleval){
        	 return  new ArrayList<>();
          }
		  //流程获得idList
		  Map<String, Object> map = new HashMap<String, Object>();
		  RestTemplate restTemplate = new RestTemplate();
	      String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/cremanage/getHosuingIdListMoa.do";
	      MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
	      form.add("personnel_id", user.getPersonnel_id().toString());
	      map = restTemplate.postForObject(url, form,Map.class);
	      if(map == null||map.get("idList") == null||((List<String>)map.get("idList")).size() == 0) {
	    	 return new ArrayList<Map<String, Object>>();
	      }else{
	    	  paramMap.put("idList",map.get("idList")); 
	      }
	     
	      //单据编码
	      if (StringUtil.isNotBlank(queryInfo.getBill_code())) {
	          paramMap.put("bill_code", "%" + queryInfo.getBill_code() + "%");
	      }
	      //客户名称
	      if (StringUtil.isNotBlank(queryInfo.getCustomer_name())) {
	          paramMap.put("customer_name", "%" + queryInfo.getCustomer_name() + "%");
	      }
	      //身份证号
	      if (StringUtil.isNotBlank(queryInfo.getId_card())) {
	          paramMap.put("id_card", "%" + queryInfo.getId_card() + "%");
	      }
	      //手机号码
	      if (StringUtil.isNotBlank(queryInfo.getMobile_telephone())) {
	          paramMap.put("mobile_telephone", "%" + queryInfo.getMobile_telephone() + "%");
	      }
	      //单据状态
	      if (StringUtil.isNotBlank(queryInfo.getBill_status())) {
	          paramMap.put("bill_status", queryInfo.getBill_status());
	      }
	      //多个字段like
	      if (StringUtil.isNotBlank(queryInfo.getMany_column_like())) {
	          paramMap.put("many_column_like", "%" + queryInfo.getMany_column_like() + "%");
	      }
	      paramMap.put("create_user_city_code", user.getPersonnel_regionnumber());//城市编码
	      paramMap.put("sortname", queryInfo.getSortname());
	      paramMap.put("sortorder", queryInfo.getSortorder());
	      paramMap.put("offset", queryInfo.getOffset());
	      paramMap.put("pagesize", queryInfo.getPagesize());

	      List<Map<String, Object>> list = wmsCreCreditHeadDao.getwmsHousingCertificatesList(paramMap);
      
	      return list;
	}
    
    /**
     * 房产抵押单据明细查询
     * @param paramMap
     * @return
     */
    public Map<String, Object> searchHouseLoanInfo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        //reqMap.put("hprocess_time", "2016/2/24");
        reqMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
        Map<String, Object> bean = wmsCreCreditHeadDao.searchBeanByPkForMap(reqMap);
        reqMap.put("data_type_name", queryInfo.getData_type_name());
        reqMap.put("sortname", queryInfo.getSortname());
        reqMap.put("sortorder", queryInfo.getSortorder());
        List<Map<String, Object>>  attlist= wmsCreHousingApplyAttDao.search(reqMap);//附件
        
        Map<String, Object> map = new HashMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/loancheck/houseCreditWorkFlowViewMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString());
        map = restTemplate.postForObject(url, form,Map.class);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("bean", bean);
        resMap.put("attlist", attlist);
        if(map!=null){//流程历程
        	 resMap.put("works",map.get("Rows"));
        }
        return resMap;
    }

    /**
     * 获取wms_cre_housing_file_info
     */
    @Override
    public WmsCreHousingFileInfo getWmsCreHousingFileInfo(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
        WmsCreHousingFileInfo wmsCreHousingFileInfo = new WmsCreHousingFileInfo();
        Integer wmsCreHousingFileInfoId = null;
        if(queryInfo.getSave_type().equals("1")) {//房贷申请(需要往wms_cre_housing_file_info插入一条记录)
            wmsCreHousingFileInfo.setCreate_timestamp(queryInfo.getNow_time_timestamp());
            wmsCreHousingFileInfo.setCreate_user_id(personnel.getPersonnel_id());
            wmsCreHousingFileInfo.setCreate_user_name(personnel.getPersonnel_name());
            wmsCreHousingFileInfo.setEnable_flag("1");
            wmsCreHousingFileInfo.setWms_cre_credit_head_id(queryInfo.getWms_cre_credit_head_id());
            wmsCreHousingFileInfoDao.save(wmsCreHousingFileInfo);
            
            //调用WMS房贷流程新增接口(开启流程)
            
            
        }else {//其他(需查询出之前新增过的ID)
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
            paramMap.put("sortname", "");
            paramMap.put("sortorder", "");
            List<Map<String, Object>> list = wmsCreHousingFileInfoDao.search(paramMap);
            if(null != list && list.size() > 0) {
                wmsCreHousingFileInfoId = Integer.parseInt(list.get(0).get("wms_cre_housing_file_info_id").toString());
                wmsCreHousingFileInfo.setWms_cre_housing_file_info_id(wmsCreHousingFileInfoId);
                wmsCreHousingFileInfo.setLast_update_timestamp(queryInfo.getNow_time_timestamp());
                wmsCreHousingFileInfo.setLast_update_user_id(personnel.getPersonnel_id());
                wmsCreHousingFileInfoDao.update(wmsCreHousingFileInfo);
            }
        }
        return wmsCreHousingFileInfo;
    }
    
    /**
     * 存入图片记录
     */
    @Override
    public WmsCreHousingApplyAtt saveWmsCreHousingApplyAtt(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
        WmsCreHousingApplyAtt wmsCreHousingApplyAtt = new WmsCreHousingApplyAtt();
        wmsCreHousingApplyAtt = new WmsCreHousingApplyAtt();
        wmsCreHousingApplyAtt.setAttachment_type(queryInfo.getAttachment_type());
        wmsCreHousingApplyAtt.setAttachment_new_name(queryInfo.getAttachment_new_name());
        wmsCreHousingApplyAtt.setAttachment_address(queryInfo.getAttachment_address());
        wmsCreHousingApplyAtt.setAttachment_old_name(queryInfo.getAttachment_old_name());
        wmsCreHousingApplyAtt.setData_type_name(queryInfo.getData_type_name());//大类别
        wmsCreHousingApplyAtt.setData_detail_name(queryInfo.getData_detail_name());//小类别
        wmsCreHousingApplyAtt.setWms_cre_credit_head_id(queryInfo.getWms_cre_credit_head_id());
        wmsCreHousingApplyAtt.setWms_cre_housing_file_info_id(queryInfo.getWms_cre_housing_file_info_id());
        return wmsCreHousingApplyAtt;
    }
    
    /**
     * 房产补件图片上传
     * @param queryInfo
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> houseLoanUpload(WmsCreCreditHeadSearchBeanVO queryInfo, PmPersonnel personnel) {
        Integer wmsCreHousingFileInfoId = null;
        
        Date date = new Date(System.currentTimeMillis());
        Timestamp now = new Timestamp(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date_format = format.format(date);
        
        WmsCreHousingFileInfo wmsCreHousingFileInfo = new WmsCreHousingFileInfo();
        if(queryInfo.getSave_type().equals("1")) {//房贷申请(需要往wms_cre_housing_file_info插入一条记录)
            wmsCreHousingFileInfo.setCreate_timestamp(now);
            wmsCreHousingFileInfo.setCreate_user_id(personnel.getPersonnel_id());
            wmsCreHousingFileInfo.setCreate_user_name(personnel.getPersonnel_name());
            wmsCreHousingFileInfo.setEnable_flag("1");
            wmsCreHousingFileInfo.setWms_cre_credit_head_id(queryInfo.getWms_cre_credit_head_id());
            wmsCreHousingFileInfoDao.save(wmsCreHousingFileInfo);
            wmsCreHousingFileInfoId = wmsCreHousingFileInfo.getWms_cre_housing_file_info_id();
        }else {//其他(需查询出之前新增过的ID)
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
            paramMap.put("sortname", "");
            paramMap.put("sortorder", "");
            List<Map<String, Object>> list = wmsCreHousingFileInfoDao.search(paramMap);
            if(null != list && list.size() > 0) {
                wmsCreHousingFileInfoId = Integer.parseInt(list.get(0).get("wms_cre_housing_file_info_id").toString());
                wmsCreHousingFileInfo.setWms_cre_housing_file_info_id(wmsCreHousingFileInfoId);
                wmsCreHousingFileInfo.setLast_update_timestamp(now);
                wmsCreHousingFileInfo.setLast_update_user_id(personnel.getPersonnel_id());
                wmsCreHousingFileInfoDao.update(wmsCreHousingFileInfo);
            }
        }
        
        if(null != wmsCreHousingFileInfoId) {
            WmsCreHousingApplyAtt WmsCreHousingApplyAtt = new WmsCreHousingApplyAtt();
            if(null != queryInfo.getImgFile()) {
                int count = 0;
                for(int i = 0; i < queryInfo.getImgCounts().length; i++) {
                    for(int j = 0; j < queryInfo.getImgCounts()[j]; j++) {
                        WmsCreHousingApplyAtt = new WmsCreHousingApplyAtt();
                        WmsCreHousingApplyAtt.setAttachment_type(
                                queryInfo.getImgFile()[count].getOriginalFilename().substring(queryInfo.getImgFile()[count].getOriginalFilename().lastIndexOf(".") + 1));
                        WmsCreHousingApplyAtt.setAttachment_new_name("/" + date_format + "/" + date.getTime());
                        WmsCreHousingApplyAtt.setAttachment_address(
                                WmsCreHousingApplyAtt.getAttachment_new_name() + "." + WmsCreHousingApplyAtt.getAttachment_type());
                        WmsCreHousingApplyAtt.setAttachment_old_name(queryInfo.getImgFile()[count].getName());
                        WmsCreHousingApplyAtt.setData_type_name(queryInfo.getP_wms_sys_dict_data_ids()[i].toString());//大类别
                        WmsCreHousingApplyAtt.setData_detail_name(queryInfo.getWms_sys_dict_data_ids()[i].toString());//小类别
                        WmsCreHousingApplyAtt.setWms_cre_credit_head_id(queryInfo.getWms_cre_credit_head_id());
                        WmsCreHousingApplyAtt.setWms_cre_housing_file_info_id(wmsCreHousingFileInfoId);
                        wmsCreHousingApplyAttDao.save(WmsCreHousingApplyAtt);
                        count++;
                    }
                }
            }
        }
        return null;
    }

	@Override
	public List<Map<String, Object>> getWmsMessageList(WmsCreCreditHeadSearchBeanVO wmscrecredithead,PmPersonnel personnel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//多个字段like
        if (StringUtil.isNotBlank(wmscrecredithead.getMany_column_like())) {
            paramMap.put("many_column_like", SysUtil.getSqlLikeParam(wmscrecredithead.getMany_column_like()));
        }
		
        paramMap.put("sortname", "create_timestamp");
        if(wmscrecredithead.getSortname().equals("1")){
        	 paramMap.put("sortorder", "desc");
        }else if(wmscrecredithead.getSortname().equals("0")){
        	 paramMap.put("sortorder", "asc");
        }
        
        if(wmscrecredithead.getIs_need_paging() != null) {
            if(wmscrecredithead.getIs_need_paging() == 1) {
                paramMap.put("offset", wmscrecredithead.getOffset());
                paramMap.put("pagesize", wmscrecredithead.getPagesize());
            } else {
                paramMap.put("offset", null);
                paramMap.put("pagesize", null);
            }
        } else {
            paramMap.put("offset", wmscrecredithead.getOffset());
            paramMap.put("pagesize", wmscrecredithead.getPagesize());
        }
        paramMap.put("salesman_id", personnel.getPersonnel_id());// 登陆人id
        paramMap.put("salesman_dept_id", personnel.getPersonnel_deptid());// 登陆人部门id
        paramMap.put("menu_id", WmsHelp.MENU_ID_FKSP_LIST); // 菜单id
        // 子部门信息
        Map<String, Object> childrenDeptMap = wmsCreCreditHeadDao.queryChildrenDeptInfo(paramMap);
        paramMap.put("childrendept", childrenDeptMap.get("childrendept"));

        List<Map<String, Object>> list = wmsCreCreditHeadDao.getWmsMessageList(paramMap);
        
        if(null != list) {
            //添加图片完整地址(拼接接口名)
            for(Map<String, Object> map_bean : list) {
                map_bean.put("attachment_address_complete", "wms/getImg.do?url=" + map_bean.get("attachment_address"));
            }
        }
        
		return list;
	}

	@Override
	public Map<String, Object> getLoanApprovalInfo(Integer wms_cre_credit_head_id) {
	    //获取单据审批信息
		Map<String, Object> resMap = wmsCreCreditHeadDao.getLoanApprovalInfo(wms_cre_credit_head_id);

		List<Map<String, Object>> attList = wmsCreCreditHeadDao.selectApprovalAttList(wms_cre_credit_head_id);
		
		if(resMap == null) {
		    resMap = new HashMap<String, Object>();
		}
		
		resMap.put("attachment_address_complete", "wms/getImg.do?url=" + resMap.get("attachment_address"));
		//获取图片信息list
		resMap.put("attList", attList);
		
		return resMap;
	}

    /**
     * @Title: getBizDetailsDocuments
     * @Description: TODO(3.2.15   WMS_OUT_015 获取单据详细信息)
     * @param wms_cre_credit_head_id
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 下午3:59:20
     * @see com.zx.moa.wms.loan.service.IWmsCreCreditHeadService#getBizDetailsDocuments(java.lang.Integer)
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    @Override
    public Map<String, Object> getBizDetailsDocuments(Integer wms_cre_credit_head_id)
    {
        Map<String, Object> resMap = wmsCreCreditHeadDao.getBizDetailsDocuments(wms_cre_credit_head_id);

        List<Map<String, Object>> attList = wmsCreCreditHeadDao.selectApprovalAttListForFour(wms_cre_credit_head_id);
        
        if(resMap == null) {
            resMap = new HashMap<String, Object>();
        }
        
        resMap.put("attachment_address_complete", "wms/getImg.do?url=" + resMap.get("attachment_address"));
        //获取图片信息list
        resMap.put("attList", attList);
        
        return resMap;
    }

    /**
     * @Title: getBizDetailsDocuments
     * @Description: TODO(3.2.10   WMS_OUT_010 获取客户验证信息)
     * @param vo
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 下午5:09:04
     * @see com.zx.moa.wms.loan.service.IWmsCreCreditHeadService#getBizDetailsDocuments(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO)
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
    @Override
    public List<Map<String, Object>> getBizDetailsDocuments(WmsCreCreditHeadFourSearchBeanVO vo)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Map<String, Object>> resMap = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> headMap = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(vo.getId_card())){
            paramMap.put("id_card", vo.getId_card().replace(" ", ""));
        }
        if(StringUtils.isNotEmpty(vo.getCredit_type())){
            paramMap.put("credit_type", vo.getCredit_type());
        }
        if(StringUtils.isNotEmpty(vo.getCustomer_name())){
            paramMap.put("customer_name", vo.getCustomer_name().replace(" ", ""));
        }
        if(StringUtils.isNotEmpty(vo.getHouse_address_more())){
            paramMap.put("house_address_more", vo.getHouse_address_more().replace(" ", ""));
        }
        if(StringUtils.isNotEmpty(vo.getIs_need_paging())) {
            if(vo.getIs_need_paging().equals("1")) {
                paramMap.put("offset", vo.getOffset());
                paramMap.put("pagesize", vo.getPagesize());
            } else {
                paramMap.put("offset", null);
                paramMap.put("pagesize", null);
            }
        } else {
            paramMap.put("offset", vo.getOffset());
            paramMap.put("pagesize", vo.getPagesize());
        }
        resMap = wmsCreCreditHeadDao.getNotaryWarnInfo(paramMap);
        /*//如果还款表没有数据
        if(resMap.size() == 0){
            resMap = wmsCreCreditHeadDao.getHeadInfo(paramMap);
        } else{
           
            for(int i = 0;i<=resMap.size();i++){
                //查询主表
                if(resMap.get(i).get("wms_cre_credit_head_id") == null){
                    listMap = wmsCreCreditHeadDao.getHeadInfo(paramMap);
                    resMap.addAll(listMap);
                }
                //如果还款表有headID根据headID查询head表信息
                if(resMap.get(i).get("wms_cre_credit_head_id") != null){
                    headMap = wmsCreCreditHeadDao.getHeadInfoForId(resMap.get(i).get("wms_cre_credit_head_id"));
                    resMap.addAll(headMap);
                }
            }
        }*/
        return resMap;
    }

    /**
     * @Title: getRefuseloan
     * @Description: TODO(根据条件查询拒贷表是否有数据)
     * @param vo
     * @return 
     * @author: jiaodelong
     * @time:2017年3月28日 上午10:46:05
     * @see com.zx.moa.wms.loan.service.IWmsCreCreditHeadService#getRefuseloan(com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO)
     * history:
     * 1、2017年3月28日 jiaodelong 创建方法
     */
    @Override
    public Integer getRefuseloan(WmsCreCreditHeadFourSearchBeanVO vo)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(vo.getCredit_type())){
            map.put("credit_type",vo.getCredit_type());
        }
        if(StringUtils.isNotEmpty(vo.getId_card())){
            map.put("id_card",vo.getId_card().replace(" ", ""));
        }
        if(StringUtils.isNotEmpty(vo.getCustomer_name())){
            map.put("customer_name",vo.getCustomer_name().replace(" ", ""));
        }
        if(StringUtils.isNotEmpty(vo.getHouse_address_more())){
            map.put("house_address_more",vo.getHouse_address_more().replace(" ", ""));
        }
        //判断拒贷表是否有拒贷数据
        Integer n  = wmsCreCreditRefuseDao.getWmsCreCreditRefuseInfo(map);
        //如果没有去主编查
        if(n<1){
            n = wmsCreCreditHeadDao.getRefuseloan(map);
        }
        return n;
    }
}
