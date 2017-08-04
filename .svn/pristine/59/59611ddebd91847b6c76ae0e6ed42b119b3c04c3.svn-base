package com.zx.moa.wms.inve.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.WmsProviderForAppService;
import com.zx.moa.wms.inve.vo.WmsProviderForAppVO;
import com.zx.platform.syscontext.PlatformGlobalVar;
/**
 * @ClassName: WmsProviderForAppServiceImpl
 * @Description: wms为MOA提供接口实现类,
 * 计算结果保留2位，四舍五入
 * @author WangShuai
 * @date 2017年1月3日
 * @version V1.0
 * history:
 * 1、2017年1月3日 WangShuai 创建文件
 */
@Service("wmsProviderForAppService")
public class WmsProviderForAppServiceImpl implements WmsProviderForAppService{

	/**
	 * @Title: getPersonnelcomm_dept_sta_v197
	 * @Description: 接口18，根据客户经理汇总
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月3日 下午4:00:44
	 * history:
	 * 1、2017年1月3日 WangShuai 创建方法
	*/
	@Override
	public Map<String,Object> getPersonnelcomm_dept_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
        Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/personnelcomm_dept_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("dept_ids", wmsProviderForAppVO.getDept_ids());
        form.add("personnel_info", wmsProviderForAppVO.getPersonnel_info());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}
	
	


	/**
	 * @Title: getPersonnelcomm_comm_item_sta_v197
	 * @Description: 接口19，自己的个人佣金或某一客户经理的个人佣金
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月4日 上午10:39:45
	 * history:
	 * 1、2017年1月4日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getPersonnelcomm_comm_item_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO,PmPersonnel pmPersonnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/personnelcomm_comm_item_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", pmPersonnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("personnel_ids", StringUtils.isEmpty(wmsProviderForAppVO.getPersonnel_ids())?pmPersonnel.getPersonnel_id().toString():wmsProviderForAppVO.getPersonnel_ids());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getPersonnel_comm_inve_sta_v197
	 * @Description: 接口20，用户查看每一单带来的佣金情况；
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月4日 上午11:11:27
	 * history:
	 * 1、2017年1月4日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getPersonnel_comm_inve_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/personnel_comm_inve_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("personnel_ids", StringUtils.isEmpty(wmsProviderForAppVO.getPersonnel_ids())?personnel.getPersonnel_id().toString():wmsProviderForAppVO.getPersonnel_ids());
        form.add("personnel_info", wmsProviderForAppVO.getPersonnel_info());
        form.add("comm_item_ids", wmsProviderForAppVO.getComm_item_ids());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getAch_center_sta_v197
	 * @Description: 接口21，用户查看各个中心的佣金情况
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月4日 下午2:42:47
	 * history:
	 * 1、2017年1月4日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getAch_center_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/ach_center_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("cener_ids", wmsProviderForAppVO.getCener_ids());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getAch_dept_sta_v197
	 * @Description: 接口22，按团队汇总业绩
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月4日 下午4:12:23
	 * history:
	 * 1、2017年1月4日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getAch_dept_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/ach_dept_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("dept_ids", wmsProviderForAppVO.getDept_ids());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getAch_team_sta_v197
	 * @Description: 接口23，按团队汇总业绩
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月4日 下午4:33:21
	 * history:
	 * 1、2017年1月4日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getAch_team_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/ach_team_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("dept_ids", wmsProviderForAppVO.getDept_ids());
        form.add("team_ids", wmsProviderForAppVO.getTeam_ids());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getAch_personnel_sta_v197
	 * @Description: 接口24，按个人汇总业绩
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月4日 下午5:27:27
	 * history:
	 * 1、2017年1月4日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getAch_personnel_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/ach_personnel_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("personnel_ids", StringUtils.isEmpty(wmsProviderForAppVO.getTeam_ids())?personnel.getPersonnel_id().toString():wmsProviderForAppVO.getPersonnel_ids());
        form.add("personnel_info", wmsProviderForAppVO.getPersonnel_info());
        form.add("team_ids", wmsProviderForAppVO.getTeam_ids());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getAch_inve_sta_v197
	 * @Description: 接口汇总，按单据展现信息，接口25
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月5日 上午10:42:13
	 * history:
	 * 1、2017年1月5日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getAch_inve_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/ach_inve_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("personnel_ids", StringUtils.isEmpty(wmsProviderForAppVO.getPersonnel_ids())?personnel.getPersonnel_id().toString():wmsProviderForAppVO.getPersonnel_ids());
        form.add("personnel_info", wmsProviderForAppVO.getPersonnel_info());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

	/**
	 * @Title: getAch_vice_sta_v197
	 * @Description: 副总统计，接口29
	 * @param wmsProviderForAppVO
	 * @return 
	 * @author: WangShuai
	 * @time:2017年1月5日 下午2:30:55
	 * history:
	 * 1、2017年1月5日 WangShuai 创建方法
	*/
	@Override
	public Map<String, Object> getAch_vice_sta_v197(
			WmsProviderForAppVO wmsProviderForAppVO, PmPersonnel personnel) {
		Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/mobpt/ach_vice_sta_v197MOA.do";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("sel_pid", personnel.getPersonnel_id().toString());
        form.add("statics_month", wmsProviderForAppVO.getStatics_month());
        form.add("vice_personnel_id", wmsProviderForAppVO.getVice_personnel_id());
        form.add("vice_team_id", wmsProviderForAppVO.getVice_team_id());
        form.add("page", wmsProviderForAppVO.getPage());
        form.add("page_size", wmsProviderForAppVO.getPage_size());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
	}

}
