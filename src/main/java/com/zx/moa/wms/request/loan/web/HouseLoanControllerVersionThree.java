package com.zx.moa.wms.request.loan.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.loan.service.IWmsCreCreditHeadService;
import com.zx.moa.wms.loan.service.IWmsCreCustomerChangeLineContactService;
import com.zx.moa.wms.loan.service.IWmsSysDictDataService;
import com.zx.moa.wms.loan.vo.TransmitValuesThreeVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContactBeanVo;
import com.zx.moa.wms.request.loan.service.HouseLoanService;
import com.zx.moa.wms.request.loan.service.IWmsCreCreditNotificationMessageService;
import com.zx.platform.syscontext.PlatformGlobalVar;


/**
 * MOA外部请求Controller
 * @version 2.0
 * @author wangyihan
 *
 */
@Controller
public class HouseLoanControllerVersionThree {

    private static Logger log = LoggerFactory.getLogger(HouseLoanControllerVersionThree.class);

    @Autowired
    private HouseLoanService houseLoanService;
    
    @Autowired
    private IWmsSysDictDataService wmssysdictdataService;

    @Autowired
    private IWmsCreCreditNotificationMessageService wmscrecreditnotificationmessageservice;
    
    @Autowired
    private IWmsCreCreditHeadService iwmscrecreditheadservice;
    
    @Autowired
    private IWmsCreCustomerChangeLineContactService iwmscrecustomerchangelinecontactservice;
    
  
    /**
     * Description : 3.34删除已经上传房贷资料图片等信息 
     * 
     * @param Integer
     * @return Map<String, Object>
     * @author jiaodelong
     */
    @RequestMapping(value = "/wms/houseLoanDeleteStepOne.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteWmsImageAndAboutInfo(String img_path,Integer wms_cre_credit_head_id, HttpServletRequest request) {
    	Map<String, Object> resMap = new HashMap<String, Object>();
    	if(img_path == null){
    		resMap.put("ret_code", ResultHelper.RET_ERROR);
   	        resMap.put("ret_msg", "参数为空!");
    		return resMap;
    	}
    	try {
    	      Gson gson = new Gson();
            List<String> attList = new ArrayList<>();
    	      if(!"".equals(img_path)&&img_path!=null){
    	    	  attList = gson.fromJson(img_path, 
      	                new TypeToken<List<String>>(){}.getType());
    	      } 
    	      if(attList == null || attList.size() == 0){
	    	    	  resMap.put("ret_code", ResultHelper.RET_SUCCESS);
	     		      resMap.put("ret_msg", "请求成功!");	
    	    	  return resMap;
    	      }
    	      //String imageString = gson.toJson(attList);
    		  RestTemplate restTemplate = new RestTemplate();
        	  MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
              form.add("file_address", img_path);
              String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsFileUrl") + "/util/deteteFile.do";
              resMap = restTemplate.postForObject(url, form,Map.class);
              
  			 if(resMap!=null&&resMap.get("result")!=null){
       			if("success".equals(resMap.get("result").toString())){
       				resMap.put("ret_code", ResultHelper.RET_SUCCESS);
       		        resMap.put("ret_msg", "请求成功!");	
       			}else{
       	   			resMap.put("ret_code", ResultHelper.RET_ERROR);
       	   	        resMap.put("ret_msg", "操作失败!");
       	   		}
       		 }else{
       			resMap.put("ret_code", ResultHelper.RET_ERROR);
       	        resMap.put("ret_msg", "操作失败!");
       		 }
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", e.getMessage());
		}
	    return resMap;
    }
    
    /**
     * Description :3.35	获取待授信确认单据信息列表 
     * @url /wms/getListInfoforCreditConfirm.do
     * @param queryInfo
     * @return Map
     * @author  jiaodelong
     * @date 2016-10-16
     */
    @RequestMapping(value = "/wms/getListInfoforCreditConfirm.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getListInfoforCreditConfirm(TransmitValuesThreeVO vo,HttpServletRequest request) {
    	Map<String, Object> res =new HashMap<>();
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try { 
	        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
	        vo.setUser_id(personnel.getPersonnel_id());
	        list = this.houseLoanService.getListInfoforCreditConfirm(vo);
        	res.put("ret_code", ResultHelper.RET_SUCCESS);
 	        res.put("ret_msg", "请求成功!");	
        } catch (Exception e) { 
        	res.put("ret_code", ResultHelper.RET_ERROR);
 		    res.put("ret_msg", e.getMessage());
 		    e.printStackTrace();
        } 
        res.put("ret_data", list);
        return res;
    }
    
    /**
     * Description :3.36	信息完善初始化信息 
     * @url /wms/initCreditConfirmInfo.do
     * @param queryInfo
     * @return Map
     * @author  jiaodelong
     * @date 2016-10-17
     * @update 2017-2-24 11:00
     */
    @RequestMapping(value = "/wms/initCreditConfirmInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> initCreditConfirmInfo(Integer user_id,Integer wms_cre_credit_head_id,HttpServletRequest request) {
    	Map<String, Object> res =new HashMap<>();
    	Map<String, Object> Map = new HashMap<String, Object>();
        try { 
	        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
	        Integer userId = personnel.getPersonnel_id();
	        Map = this.iwmscrecustomerchangelinecontactservice.initCreditConfirmInfo(userId,wms_cre_credit_head_id);
        	res.put("ret_code", ResultHelper.RET_SUCCESS);
 	        res.put("ret_msg", "请求成功!");	
        } catch (Exception e) { 
        	res.put("ret_code", ResultHelper.RET_ERROR);
 		    res.put("ret_msg", e.getMessage());
 		    e.printStackTrace();
        } 
        res.put("ret_data", Map);
        return res;
    }
    
    
    /**
     * Description :3.37	信息完善 
     * @url /wms/sendCreditConfirmInfo.do
     * @param queryInfo
     * @return Map
     * @author  jiaodelong
     * @date 2016-10-18
     * @update 2017-2-24 11:00
     */
    @RequestMapping(value = "/wms/sendCreditConfirmInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> sendCreditConfirmInfo(WmsCreCustomerChangeLineContactBeanVo vo,HttpServletRequest request) {
    	PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
    	vo.setUser_id(personnel.getPersonnel_id());
    	Map<String, Object> resMap =new HashMap<>();
        try {
  		  RestTemplate restTemplate = new RestTemplate();
      	  MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
      	  Gson gson = new Gson();
//      	  List<WmsCreRevAtt> attList = new ArrayList<>(); 
//     	  if(!"".equals(vo.getFile_info_json())&&vo.getFile_info_json()!=null){
//      		attList = gson.fromJson(vo.getFile_info_json(), 
//                    new TypeToken<List<WmsCreRevAtt>>(){}.getType());  
//      	  }
//          if(attList!=null){
//	   	        //获取扩展名与文件新名称
//	   	        for(WmsCreRevAtt bean : attList) {
//	   	            try {
//	   	                bean.setAttachment_new_name(bean.getAttachment_address().substring(0, bean.getAttachment_address().lastIndexOf(".")));
//	   	                bean.setAttachment_type(bean.getAttachment_address().substring(bean.getAttachment_address().lastIndexOf(".") + 1));
//	   	            } catch (Exception e) {
//	   	            	resMap.put("ret_code", ResultHelper.getError(bean.getAttachment_address() + "格式不正确"));
//	   	            	resMap.put("ret_msg", e.getMessage());
//	   	            }
//	   	        }  
//           } 
            //ObjectMapper mapper = new ObjectMapper();
           if("".equals(vo.getContacts_list())||vo.getContacts_list()==null){
        	   vo.setContacts_list("[]");
           }
            form.add("wmsCreCustomerChangeLineContactBeanVoJson", gson.toJson(vo));
            form.add("wmsCreRevAttListJson", vo.getSave_type());
            form.add("interface_num", "WMS_OUT_POSTSendCreditConfirmInfo");
            form.add("sys_num", "MIF");
            resMap = restTemplate.postForObject(
          		  PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"),
          		  form, Map.class);
			 if(resMap!=null&&resMap.get("result")!=null){
     			if("success".equals(resMap.get("result").toString())){
     				resMap.put("ret_code", ResultHelper.RET_SUCCESS);
     		        resMap.put("ret_msg", "请求成功!");	
     			}else{
     	   			resMap.put("ret_code", ResultHelper.RET_ERROR);
     	   	        resMap.put("ret_msg", "操作失败!");
     	   		}
     		 }else{
     			resMap.put("ret_code", ResultHelper.RET_ERROR);
     	        resMap.put("ret_msg", "操作失败!");
     		 }
		} catch (Exception e) {
			log.error(e.getMessage());
			resMap.put("ret_code", ResultHelper.RET_ERROR);
			resMap.put("ret_msg", e.getMessage());
		}
        return resMap;
    }
    
    /**
     * Description : 获取房产评估单基本信息初始化信息(接口文档3.38)
     * 
     * @param queryInfo
     * @return map
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/initHouseAssessmentBasicInfoOne.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> initHouseAssessmentBasicInfoOne(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         log.info(new Gson().toJson(queryInfo));

         PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
         Map<String, Object> resMap = new HashMap<String, Object>();
         
         try {
        	 queryInfo = this.houseLoanService.initHouseAssessmentBasicInfoOne(queryInfo, personnel);
        	 resMap.put("ret_data", queryInfo.getResMap());
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");
         } catch (Exception e) {
             resMap.put("ret_code", ResultHelper.RET_ERROR);
             resMap.put("ret_msg", e.getMessage());
         }
         
         return resMap;
    }
    
    /**
     * Description : 获取房产评估单房屋信息初始化信息(接口文档3.39)
     * 
     * @param queryInfo
     * @return map
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/initHouseAssessmentBasicInfoTwo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> initHouseAssessmentBasicInfoTwo(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        log.info(new Gson().toJson(queryInfo));
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
       	 	queryInfo = this.houseLoanService.initHouseAssessmentBasicInfoTwo(queryInfo, personnel);
       	 	resMap.put("ret_data", queryInfo.getResMap());
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
        } catch (Exception e) {
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", e.getMessage());
        }
        
        return resMap;
    }
    
    /**
     * Description : 发送房产评估单基本信息详细信息(接口文档3.40)
     * 
     * @param queryInfo
     * @return map
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/sendHouseAssessmentBasicInfoOne.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> sendHouseAssessmentBasicInfoOne(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        log.info(new Gson().toJson(queryInfo));
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
       	 	queryInfo = this.houseLoanService.sendHouseAssessmentBasicInfoOne(queryInfo, personnel);
       	 	resMap.put("ret_data", queryInfo.getResMap());
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
        } catch (Exception e) {
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", e.getMessage());
        }
        
        return resMap;
    }
    
    /**
     * Description : 发送房产评估单房屋信息详细信息(接口文档3.41)
     * 
     * @param queryInfo
     * @return map
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/sendHouseAssessmentBasicInfoTwo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> sendHouseAssessmentBasicInfoTwo(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        log.info(new Gson().toJson(queryInfo));
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
       	 	queryInfo = this.houseLoanService.sendHouseAssessmentBasicInfoTwo(queryInfo, personnel);
       	 	resMap.put("ret_data", queryInfo.getResMap());
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
        } catch (Exception e) {
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", e.getMessage());
        }
        
        return resMap;
    }    
}
