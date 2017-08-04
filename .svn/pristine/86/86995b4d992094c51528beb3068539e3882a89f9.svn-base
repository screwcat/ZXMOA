package com.zx.moa.wms.request.loan.web;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.moa.util.DateUtil;
import com.zx.moa.util.GlobalFileUtil;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.wms.loan.persist.WmsCreCreditHeadDao;
import com.zx.moa.wms.loan.service.IWmsCreCreditHeadService;
import com.zx.moa.wms.loan.service.IWmsSysDictDataService;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadFourSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCreditNotaryWarnSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsCreCustomerChangeLineContactBeanVo;
import com.zx.moa.wms.request.loan.service.HouseLoanServiceFour;
import com.zx.moa.wms.request.loan.service.IWmsCreCreditNotificationMessageService;
import com.zx.moa.wms.request.loan.util.WmsHelp;
import com.zx.moa.wms.request.loan.vo.BizTeamRankingBeanVO;
import com.zx.moa.wms.request.loan.vo.SysNoticeHeadSearchBeanVO;
import com.zx.moa.wms.request.loan.vo.WmsApplyInfoSearchBeanVO;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 
 * 版权所有：版权所有(C) 2017，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: MOA外部请求Controller
 * 模块名称：
 * @Description: 内容摘要：
 * @author jiaodelong
 * @date 2016年12月20日
 * @version V2.0
 * history:
 * 1、2017年3月17日 jiaodelong 创建文件
 */
@Controller
public class HouseLoanControllerVersionFour {

    private static Logger log = LoggerFactory.getLogger(HouseLoanControllerVersionFour.class);
    
    @Autowired
    private IWmsSysDictDataService wmssysdictdataService;

    @Autowired
    private IWmsCreCreditNotificationMessageService wmscrecreditnotificationmessageservice;
    
    @Autowired
    private IWmsCreCreditHeadService iwmscrecreditheadservice;

    @Autowired
    private WmsCreCreditHeadDao wmsCreCreditHeadDao;// 贷款主表
    
    @Autowired
    private HouseLoanServiceFour houseLoanServiceFour;
    
    /**
     * 
     * @Title: getBizNewMessageUp
     * @Description: TODO(3.2.2 新通知标识)
     * @param user_id
     * @param request
     * @return 
     * @author: 焦德龙
     * @time:2017年3月17日 上午9:30:02
     * history:
     * 1、2017年3月17日 焦德龙 创建方法
     */
    @RequestMapping(value = "/wms/getBizNewMessageUp.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMessageUp(Integer user_id, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            String appName = request.getHeader(GlobalVal.SSO_MODULE); 
            Map<String, Object> map = new HashMap<String, Object>();
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            if(user_id==null){
                user_id=personnel.getPersonnel_id();
            }
            String notify_state = "0";
            int n = houseLoanServiceFour.getMessageForFour(user_id,appName);
            if(n > 0){
                notify_state = "1";
            }
            map.put("notify_state", notify_state);
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
            resMap.put("ret_data", map);
        } catch (Exception e) {
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             e.printStackTrace();
        }
        return resMap;
    }
    
   /**
    * 
    * @Title: /wms/getBizMessageListUp.do
    * @Description: TODO(3.2.3      通知中心列表)
    * @param user_id
    * @param request
    * @return 
    * @author: jiaodelong
    * @time:2017年3月17日 上午9:59:45
    * history:
    * 1、2017年3月17日 jiaodelong 创建方法
    */
    @RequestMapping(value = "/wms/getBizMessageListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getWmsMessageList(Integer user_id, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> resMap1 = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        try {
            String appName = request.getHeader(GlobalVal.SSO_MODULE);  
            List<Map<String, Object>> list = houseLoanServiceFour.getWmsMessageListForFour(personnel.getPersonnel_id(),appName);
            resMap.put("ret_data", list);
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
            
            //WMSdo：/wms/updateMessageStatusForIsreadFour.do
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTupdateMessageStatusForIsreadFour"));
            nvps.add(new BasicNameValuePair("sys_num", "MIS"));
            nvps.add(new BasicNameValuePair("user_id", personnel.getPersonnel_id().toString()));
            nvps.add(new BasicNameValuePair("app_name", appName));
            try {
                resMap1 = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
            } catch (ConnectException e) {
                e.printStackTrace();
                resMap.put("ret_msg", "WMS服务器链接错误，请联系客服！");
                resMap.put("ret_code", ResultHelper.RET_NETWORK_ERROR);
            }catch (IOException e) {
                e.printStackTrace();
                resMap.put("ret_msg", e.getMessage());
                resMap.put("ret_code", ResultHelper.RET_ERROR);
            }
            if(resMap1!=null&&resMap1.get("result")!=null){
                if("success".equals(resMap1.get("result").toString())){
                    resMap.put("ret_code", ResultHelper.RET_SUCCESS);
                    resMap.put("ret_msg", "请求成功!"); 
                }else{
                    resMap.put("ret_code", ResultHelper.RET_ERROR);
                    resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
                }
            }else{
                resMap.put("ret_code", ResultHelper.RET_ERROR);
                resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
        }
        return resMap;
    }
    
    /**
     * 
     * @Title: getBizDeleteMessageUp
     * @Description: TODO(3.2.4     删除通知中心信息)
     * @param msg_key_list
     * @param request
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 上午10:25:51
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    @RequestMapping(value = "/wms/getBizDeleteMessageUp.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteWmsMessage(String msg_key_list, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            //WMSdo:/wms/getBizDeleteMessageUp.do
              RestTemplate restTemplate = new RestTemplate();
              MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
              form.add("msg_key_list", msg_key_list);
              form.add("interface_num", "WMS_OUT_POSTgetBizDeleteMessageUp");
              form.add("sys_num", "MIS");
              resMap = restTemplate.postForObject(
                      PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"),
                      form, Map.class);
             if(resMap!=null&&resMap.get("result")!=null){
                if("success".equals(resMap.get("result").toString())){
                    resMap.put("ret_code", ResultHelper.RET_SUCCESS);
                    resMap.put("ret_msg", "请求成功!"); 
                }else{
                    resMap.put("ret_code", ResultHelper.RET_ERROR);
                    resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
                }
             }else{
                resMap.put("ret_code", ResultHelper.RET_ERROR);
                resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             }
        } catch (Exception e) {
            log.error(e.getMessage());
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
        }
        return resMap;
    }
    
    
    
    
    /**
     * 
     * @Title: houseLoanUpload
     * @Description: TODO(3.2.13 上传房贷图片信息等     第一步:仅保存图片)
     * @param imgFile
     * @param host
     * @param port
     * @param request
     * @return 
     * @author: jiaodelong
     * @time:2017年3月20日 上午9:33:12
     * history:
     * 1、2017年3月20日 jiaodelong 创建方法
     */
    @RequestMapping(value = "/wms/WMSUploadMortgagePhoto.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> houseLoanUpload(@RequestParam(value = "imgFile") MultipartFile imgFile, 
            String host, String port, HttpServletRequest request) {
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        
        if (null == personnel) {
            log.error("未获取到登录人");
            return ResultHelper.getError("未获取到登录人");
        }
        
        RestTemplate restTemplate = new RestTemplate();
        String catalog = DateUtil.date2String(new Date(), "yyyyMM");
        String relative_path = ""; //保存后返回路径
        Integer user_id = personnel.getPersonnel_id();
        
        try {
            long fileSize = imgFile.getSize();
            if (fileSize > 60 * 1024 * 1024) {
                log.error(fileSize + "");
                return ResultHelper.getError("文件超出长度限制:最大为60 * 1024 * 1024");
            }
            String srcFileName = imgFile.getOriginalFilename();
            String postfix = "";
            if (srcFileName.lastIndexOf(".") > -1) {
                postfix = srcFileName.substring(srcFileName.lastIndexOf(".") + 1).toLowerCase();
                if ("exe".equals(postfix) || "cmd".equals(postfix) || "bat".equals(postfix)) {
                    log.error(postfix);
                    return ResultHelper.getError("文件格式不正确:后缀名不能为exe cmd bat");
                }
            }else {
                return ResultHelper.getError("文件格式不正确:未知文件(无后缀名)");
            }
            
            //文件相对路径
            relative_path = GlobalFileUtil.saveUploadFileForWMS(imgFile.getInputStream(), user_id.toString(), srcFileName, catalog);
            //文件绝对路径
            String absolute_path = PlatformGlobalVar.SYS_PROPERTIES.get("realUploadCatalog") + relative_path;
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
            File file = new File(absolute_path);
            FileSystemResource resource = new FileSystemResource(file); 
            form.add("jarFile", resource);
            form.add("user_id", user_id);
            form.add("catalog", catalog);
            restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsFileUrl") + 
                "/util/saveFileForRestTemplate.do", form, String.class);
            file.delete();
                
        }catch(Exception e) {
            log.error(e.getMessage());
            return ResultHelper.getError(WmsHelp.RET_ERROR_VALUE);
        }
    
        return ResultHelper.getSuccess(relative_path);
    }
    
    
    
    
    /**
     * 
     * @Title: initCreditConfirmInfo
     * @Description: TODO(3.2.17        获取联系人信息初始化)
     * @param user_id
     * @param wms_cre_credit_head_id
     * @param request
     * @return 
     * @author: jiaodelong
     * @time:2017年3月17日 上午11:05:30
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    @RequestMapping(value = "/wms/getBizContactInformation.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> initCreditConfirmInfo(WmsCreCustomerChangeLineContactBeanVo vo,HttpServletRequest request) {
        Map<String, Object> res =new HashMap<>();
        Map<String, Object> contactHeadMap =new HashMap<>();
        List<Map<String,Object>> Map = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> relationList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> directRelationList = new ArrayList<Map<String,Object>>();
        try { 
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            Integer userId = personnel.getPersonnel_id();
            Map = this.houseLoanServiceFour.getBizContactInformation(vo);
            contactHeadMap = houseLoanServiceFour.getContactHeadInfo(vo);
            relationList = houseLoanServiceFour.getRelationList(141);
            directRelationList = houseLoanServiceFour.getRelationList(140);
            res.put("ret_code", ResultHelper.RET_SUCCESS);
            res.put("ret_msg", "请求成功!");    
        } catch (Exception e) { 
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            e.printStackTrace();
        } 
        res.put("contacts_list", Map);//联系人信息
        res.put("ret_data", contactHeadMap);//客户信息
        res.put("relationList", relationList);//非直系关系
        res.put("directRelationList", directRelationList);//直系亲属关系
        return res;
    }
    
    
   /**
    * 
    * @Title: sendCreditConfirmInfo
    * @Description: TODO(3.2.18     客户联系人信息保存)
    * @param vo
    * @param request
    * @return 
    * @author: jiaodelong
    * @time:2017年3月17日 下午1:32:12
    * history:
    * 1、2017年3月17日jiaodelong 创建方法
    */
    @RequestMapping(value = "/wms/getBizSavePerfectInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> sendCreditConfirmInfo(WmsCreCustomerChangeLineContactBeanVo vo,HttpServletRequest request) {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        vo.setUser_id(personnel.getPersonnel_id());
        Map<String, Object> resMap =new HashMap<>();
        try {
          RestTemplate restTemplate = new RestTemplate();
          MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
          Gson gson = new Gson();
           if("".equals(vo.getContacts_list())||vo.getContacts_list()==null){
               vo.setContacts_list("[]");
           }
           //WMSdo：/wms/getBizSavePerfectInfo.do
            form.add("wmsCreCustomerChangeLineContactBeanVoJson", gson.toJson(vo));
            form.add("wmsCreRevAttListJson", vo.getSave_type());
            form.add("interface_num", "WMS_OUT_POSTgetBizSavePerfectInfo");
            form.add("sys_num", "MIS");
            resMap = restTemplate.postForObject(
                  PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"),
                  form, Map.class);
             if(resMap!=null&&resMap.get("result")!=null){
                if("success".equals(resMap.get("result").toString())){
                    resMap.put("ret_code", ResultHelper.RET_SUCCESS);
                    resMap.put("ret_msg", "请求成功!"); 
                }else{
                    resMap.put("ret_code", WmsHelp.RET_WARN_110);
                    resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
                }
             }else{
                resMap.put("ret_code", WmsHelp.RET_WARN_110);
                resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             }
        } catch (Exception e) {
            log.error(e.getMessage());
            resMap.put("ret_code", WmsHelp.RET_WARN_110);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
        }
        return resMap;
    }
    
    
    /**
     * 
     * @Title: getBizSignedCustomer
     * @Description: TODO(3.2.9     获取签单客户信息)
     * @param user_id
     * @param wms_cre_credit_head_id
     * @param request
     * @return 
     * @author: jiaodelong
     * @time:2017年3月20日 上午10:58:53
     * history:
     * 1、2017年3月20日jiaodelong 创建方法
     */
    @RequestMapping(value = "/wms/getBizSignedCustomer.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBizSignedCustomer(WmsCreCreditHeadFourSearchBeanVO vo,HttpServletRequest request) {
        Map<String, Object> res =new HashMap<>();
        Map<String, Object> map = new HashMap<String, Object>();
        try { 
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            map = this.houseLoanServiceFour.getBizSignedCustomer(vo, personnel);
            res.put("ret_data", map);
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
     * 
     * @Title: getBizProgressDocuments
     * @Description: TODO(3.2.6     首页进展中单据)
     * @param user_id
     * @param page
     * @param pagesize
     * @param is_need_paging
     * @param request
     * @return 
     * @author: jiaodelong
     * @time:2017年3月20日 下午2:28:01
     * history:
     * 1、2017年3月20日 jiaodelong 创建方法
     */
    @RequestMapping(value = "/wms/getBizProgressDocuments.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBizProgressDocuments(WmsCreCreditHeadFourSearchBeanVO vo,
                                                    HttpServletRequest request) {
        Map<String, Object> res =new HashMap<>();
        List<Map<String, Object>> Map = new ArrayList<Map<String, Object>>();
        try { 
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            Integer userId = personnel.getPersonnel_id();
            vo.setUser_id(userId.toString());
            Map = houseLoanServiceFour.getBizProgressDocuments(vo);
            res.put("ret_code", ResultHelper.RET_SUCCESS);
            res.put("ret_msg", "请求成功!");    
        } catch (Exception e) { 
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            e.printStackTrace();
        } 
        res.put("ret_data", Map);
        return res;
    }


    /**
     * Description : 3.2.1 WMS_OUT_001 菜单权限
     * @url /wms/getBizAuthorityUp.do
     * @param queryInfo
     * @return Map
     * @author  baisong
     * @date 2016-6-6
     */
    @RequestMapping(value = "/wms/getBizAuthorityUp.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBizAuthorityUp(WmsCreCreditHeadFourSearchBeanVO queryInfo, HttpServletRequest request)
    {
        Map<String, Object> res = new HashMap<>();
        try
        {
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            res = houseLoanServiceFour.getBizAuthorityUp(queryInfo, personnel);
            res.put("ret_code", ResultHelper.RET_SUCCESS);
            res.put("ret_msg", "请求成功!");
        }
        catch (Exception e)
        {
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Description :3.2.8 WMS_OUT_008 初始化排序、状态筛选条件
     * @url /wms/getBizSortingState.do
     * @param queryInfo
     * @return Map
     * @author  baisong
     * @date 2016-6-6
     */
    @RequestMapping(value = "/wms/getBizSortingState.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBizSortingState(WmsCreCreditHeadFourSearchBeanVO queryInfo, HttpServletRequest request)
    {
        Map<String, Object> res = new HashMap<>();
        try
        {
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            res = houseLoanServiceFour.getBizSortingState(queryInfo, personnel);
            res.put("ret_code", ResultHelper.RET_SUCCESS);
            res.put("ret_msg", "请求成功!");
        }
        catch (Exception e)
        {
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 
     * @Title: getBizTimeScreening
     * @Description: 3.2.23 WMS_OUT_023 初始化时间筛选条件
     * @param queryInfo
     * @param request
     * @return 
     * @author: baisong
     * @time:2017年3月21日 上午11:56:57
     * history:
     * 1、2017年3月21日baisong 创建方法
     */
    @RequestMapping(value = "/wms/getBizTimeScreening.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBizTimeScreening(WmsCreCreditHeadFourSearchBeanVO queryInfo, HttpServletRequest request)
    {
        Map<String, Object> res = new HashMap<>();
        try
        {
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            res = houseLoanServiceFour.getBizTimeScreening(queryInfo, personnel);
            res.put("ret_code", ResultHelper.RET_SUCCESS);
            res.put("ret_msg", "请求成功!");
        }
        catch (Exception e)
        {
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            e.printStackTrace();
        }
        return res;
    }

    
    
    
    /**
     * 
     * @Title: getBizProgressNumber
     * @Description: TODO(3.2.5     进度查询提示数据)
     * @param user_id
     * @param request
     * @return 
     * @author: jiaodleong
     * @time:2017年3月22日 上午9:54:36
     * history:
     * 1、2017年3月22日jiaodleong 创建方法
     */
    @RequestMapping(value = "/wms/getBizProgressNumber.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBizProgressNumber(Integer user_id, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> resMap1 = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            if(user_id==null){
                user_id=personnel.getPersonnel_id();
            }
            int n = houseLoanServiceFour.getBizProgressNumber(user_id);
            map.put("progress_num", n);
            map.put("menu_code", WmsHelp.MENU_CODE_JDCX);
            resMap.put("ret_data", map);
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
            
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTupdateLastUpdateTime"));
            nvps.add(new BasicNameValuePair("sys_num", "MIS"));
            nvps.add(new BasicNameValuePair("user_id", personnel.getPersonnel_id().toString()));
            //WMS方法：loanappro/updateLastUpdateTime.do
            try {
                resMap1 = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
            } catch (ConnectException e) {
                e.printStackTrace();
                resMap.put("ret_msg", "WMS服务器链接错误，请联系客服！");
                resMap.put("ret_code", ResultHelper.RET_NETWORK_ERROR);
            }catch (IOException e) {
                e.printStackTrace();
                resMap.put("ret_msg", e.getMessage());
                resMap.put("ret_code", ResultHelper.RET_ERROR);
            }
            if(resMap1!=null&&resMap1.get("result")!=null){
                if("success".equals(resMap1.get("result").toString())){
                    resMap.put("ret_code", ResultHelper.RET_SUCCESS);
                    resMap.put("ret_msg", "请求成功!"); 
                }else{
                    resMap.put("ret_code", ResultHelper.RET_ERROR);
                    resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
                }
            }else{
                resMap.put("ret_code", ResultHelper.RET_ERROR);
                resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            }
            
            
        } catch (Exception e) {
             log.error(e.getMessage());
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             e.printStackTrace();
        }
        return resMap;
    }
    
    /**
     * 
     * @Title: getBizMyResults
     * @Description: TODO(3.2.20 WMS_OUT_020 获取我的业绩统计信息)
     * @param request
     * @return 
     * @author: handongchun
     * @time:2017年3月21日 下午3:18:56
     * history:
     * 1、2017年3月21日 handongchun 创建方法
     */
     @RequestMapping(value = "/wms/getBizMyResults.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizMyResults(HttpServletRequest request)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             Map<String, Object> ret = new HashMap<>();
             ret = houseLoanServiceFour.getBizMyResults(personnel);
             res.put("ret_data", ret);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
         }
         return res;
     }
     
     /**
      * 
      * @Title: getBizBillDetailed
      * @Description: TODO(3.2.21 WMS_OUT_021 获取客户业绩详单信息)
      * @param request
      * @return 
      * @author: handongchun
      * @time:2017年3月21日 下午3:18:38
      * history:
      * 1、2017年3月21日 handongchun 创建方法
      */
     @RequestMapping(value = "/wms/getBizBillDetailed.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizBillDetailed(HttpServletRequest request,
                                                   WmsCreCreditNotaryWarnSearchBeanVO vo)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             res = houseLoanServiceFour.getBizBillDetailed(personnel,vo);
             res.put("month_num", vo.getMonth_num());
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
         }
         return res;
     }
     /**
      * 
      * @Title: getBizTeamRanking
      * @Description: TODO(3.2.25 WMS_OUT_025 获取成员排名信息)
      * @param request
      * @param vo
      * @return 
      * @author: handongchun
      * @time:2017年3月22日 上午11:02:34
      * history:
      * 1、2017年3月22日 handongchun 创建方法
      */
     @RequestMapping(value = "/wms/getBizTeamRanking.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizTeamRanking(HttpServletRequest request,
                                                   BizTeamRankingBeanVO vo)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             res = houseLoanServiceFour.getBizTeamRanking(personnel,vo);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
         }
         return res;
     }
     
     /**
      * 
      * @Title: getBizProgressStatistical
      * @Description: TODO(3.2.27 WMS_OUT_027 获取团队、门店、公司单据统计信息)
      * @param request
      * @param vo
      * @return 
      * @author: handongchun
      * @time:2017年3月22日 上午11:58:35
      * history:
      * 1、2017年3月22日 handongchun 创建方法
      */
     @RequestMapping(value = "/wms/getBizProgressStatistical.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizProgressStatistical(HttpServletRequest request,
                                                   @RequestParam(value="date_info",defaultValue="",required=true) String date_info,
                                                   @RequestParam(value="dept_info",defaultValue="",required=true) String dept_info,
                                                   @RequestParam(value="is_type",defaultValue="",required=true) String is_type)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             res = houseLoanServiceFour.getBizProgressStatistical(personnel, date_info, dept_info, is_type);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
         }
         return res;
     }
     
     /**
      * 
      * @Title: getBizBillDetails
      * @Description: TODO(3.2.29 WMS_OUT_029 获取成员单据详细信息)
      * @param request
      * @param vo
      * @return 
      * @author: handongchun
      * @time:2017年3月22日 下午4:33:25
      * history:
      * 1、2017年3月22日 handongchun 创建方法
      */
     @RequestMapping(value = "/wms/getBizBillDetails.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizBillDetails(HttpServletRequest request,
                                                  WmsCreCreditHeadFourSearchBeanVO vo)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             res = houseLoanServiceFour.getBizBillDetails(personnel,vo);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
         }
         return res;
     }
     
     /**
      * 
      * @Title: getBizStoreRanking
      * @Description: TODO(3.2.30 WMS_OUT_030 获取团队、门店排名信息)
      * @param request
      * @param vo
      * @return 
      * @author: handongchun
      * @time:2017年3月22日 下午4:34:52
      * history:
      * 1、2017年3月22日 handongchun 创建方法
      */
     @RequestMapping(value = "/wms/getBizStoreRanking.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizStoreRanking(HttpServletRequest request,
                                                   WmsCreCreditNotaryWarnSearchBeanVO vo)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             res = houseLoanServiceFour.getBizStoreRanking(personnel,vo);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
             res.put("ret_msg", e.getMessage());
         }
         return res;
     }
     /**
      * 
      * @Title: getBizDepartmentScreening
      * @Description: 3.2.24 WMS_OUT_024 初始化单位筛选条件
      * @param queryInfo
      * @param request
      * @return 
      * @author: baisong
      * @time:2017年3月21日 上午11:56:57
      * history:
      * 1、2017年3月21日baisong 创建方法
      */
     @RequestMapping(value = "/wms/getBizDepartmentScreening.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizDepartmentScreening(WmsCreCreditHeadFourSearchBeanVO queryInfo, HttpServletRequest request)
     {
         Map<String, Object> res = new HashMap<>();
         try
         {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             res = houseLoanServiceFour.getBizDepartmentScreening(queryInfo, personnel);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             e.printStackTrace();
         }
         return res;
     }

    /**
     * 
     * @Title: houseLoanUploadStepTwoUp
     * @Description: 3.2.12 WMS_OUT_012 贷款申请保存单据
     * @param queryInfo
     * @param request
     * @param wmsApplyInfoSearchBeanVO
     * @return 
     * @author:baisong
     * @time:2017年4月11日 上午11:55:06
     * history:
     * 1、2017年4月11日 baisong 创建方法
     */
     @RequestMapping(value = "/wms/getBizHouseLoanUp.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public ResultBean<String> houseLoanUploadStepTwoUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request, WmsApplyInfoSearchBeanVO wmsApplyInfoSearchBeanVO)
     {

         PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
         Gson gson = new Gson();
         Map<String, Object> resMap = new HashMap<String, Object>();
         //1：申请提交2:退回重新提交  3：暂存4:信息补录
         if ("1".equals(queryInfo.getSave_type().toString()) || "2".equals(queryInfo.getSave_type().toString()))
         {

            // 姓名
            if (StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCustomer_name()))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_100, "客户姓名不允许为空!", "客户姓名不允许为空!");
            }
            // 年龄
            if (StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCustomer_age()))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_101, "年龄不允许为空!", "年龄不允许为空!");
            }
             // 城市
            if (StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCity()))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_102, "城市不允许为空!", "城市不允许为空!");
            }
            // 小区名称
            if (StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getCommunity_name()))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_106, "小区名称不允许为空!", "小区名称不允许为空!");
            }
            // 房屋类型
            if (StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getHouse_type()))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_104, "房产类型不允许为空!", "房产类型不允许为空!");
            }
            // 抵押房产地址
            if (StringUtils.isBlank(wmsApplyInfoSearchBeanVO.getHouse_address_more()))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_107, "抵押房产地址不允许为空!", "抵押房产地址不允许为空!");
            }
             // 贷款金额
            if (wmsApplyInfoSearchBeanVO.getCredit_limit() <= 0.0)
             {
                return ResultHelper.getError(WmsHelp.RET_WARN_103, "贷款金额不允许为空!", "贷款金额不允许为空!");
             }
            // 建筑面积
            if (wmsApplyInfoSearchBeanVO.getHouse_building_area() <= 0.0)
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_105, "建筑面积不允许为空!", "建筑面积不允许为空!");
            }
            // 图片
            if (queryInfo.getWms_cre_credit_head_id() == null && (queryInfo.getFile_info_json() == null || "".equals(queryInfo.getFile_info_json()) || "[]".equals(queryInfo.getFile_info_json())))
            {
                return ResultHelper.getError(WmsHelp.RET_WARN_108, "房产证图片不允许为空!", "房产证图片不允许为空!");
            }
         }
         List<WmsCreHousingApplyAtt> attList = gson.fromJson(queryInfo.getFile_info_json(), new TypeToken<List<WmsCreHousingApplyAtt>>()
         {
         }.getType());
         if (attList != null)
         {
             // 获取扩展名与文件新名称
             for (WmsCreHousingApplyAtt bean : attList)
             {
                 try
                 {
                     bean.setAttachment_new_name(bean.getAttachment_address().substring(0, bean.getAttachment_address().lastIndexOf(".")));
                     bean.setAttachment_type(bean.getAttachment_address().substring(bean.getAttachment_address().lastIndexOf(".") + 1));
                 }
                 catch (Exception e)
                 {
                     return ResultHelper.getError(bean.getAttachment_address() + "格式不正确");
                 }
             }
         }

         List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTwmsBizMoaSaveUp"));
        nvps.add(new BasicNameValuePair("sys_num", "MIS"));

         nvps.add(new BasicNameValuePair("list", new Gson().toJson(attList)));
         nvps.add(new BasicNameValuePair("personnel_id", personnel.getPersonnel_id().toString()));
        if (queryInfo.getWms_cre_credit_head_id() != null)
        {
            nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString()));
        }else{
            nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", ""));
        }
         nvps.add(new BasicNameValuePair("falg", queryInfo.getSave_type().toString()));
         nvps.add(new BasicNameValuePair("user_id", "0"));
         try
         {
             nvps.add(new BasicNameValuePair("customer_info", gson.toJson(wmsApplyInfoSearchBeanVO)));
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

         try
         {
             resMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
         }
         catch (ConnectException e)
         {
             e.printStackTrace();
            resMap.put("message", "WMS服务器链接错误，请联系客服！");
             return ResultHelper.getNetworkError(resMap.get("message").toString());
         }
        catch (Exception e)
         {
             e.printStackTrace();
            return ResultHelper.getError(WmsHelp.RET_WARN_109, resMap.get("message").toString(), "");
         }

         return ResultHelper.getSuccess(resMap.get("message").toString());
     }

     /**
      * 
      * @Title: getBizApplyInitialize
      * @Description: 3.2.11 WMS_OUT_011 贷款申请初始化信息
      * @param queryInfo
      * @param request
      * @return 
      * @author: baisong
      * @time:2017年3月22日 下午2:40:36
      * history:
      * 1、2017年3月22日 baisong 创建方法
      */
     @RequestMapping(value = "/wms/getBizApplyInitialize.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizApplyInitialize(WmsCreCreditHeadFourSearchBeanVO queryInfo, HttpServletRequest request)
     {
         log.info(new Gson().toJson(queryInfo));

         Map<String, Object> resMap = new HashMap<String, Object>();

         PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

         try
         {
             resMap = this.houseLoanServiceFour.getBizApplyInitialize(queryInfo, personnel);
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             e.printStackTrace();
         }

         return resMap;
     }

     /**
      * 
      * @Title: getBizUploadCustomerInfo
      * @Description: 3.2.16 WMS_OUT_016 贷款申请草稿、补录初始化
      * @param queryInfo
      * @param request
      * @return 
      * @author: baisong
      * @time:2017年3月22日 下午2:40:36
      * history:
      * 1、2017年3月22日 baisong 创建方法
      */
     @RequestMapping(value = "/wms/getBizUploadCustomerInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizUploadCustomerInfo(WmsCreCreditHeadFourSearchBeanVO queryInfo, HttpServletRequest request)
     {
         log.info(new Gson().toJson(queryInfo));

         Map<String, Object> resMap = new HashMap<String, Object>();

         PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);

         try
         {
             resMap = this.houseLoanServiceFour.getBizUploadCustomerInfo(queryInfo, personnel);
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             e.printStackTrace();
         }

         return resMap;
     }
 
     
     /**
      * 
      * @Title: getBizHouseLoanListUp
      * @Description: TODO(3.2.14   WMS_OUT_014 获取进度/历史查询单据信息)
      * @param vo
      * @param request
      * @return 
      * @author: jiaodelong
      * @time:2017年3月23日 上午11:47:28
      * history:
      * 1、2017年3月23日 jiaodelong 创建方法
      */
     @RequestMapping(value = "/wms/getBizHouseLoanListUp.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizHouseLoanListUp(WmsCreCreditHeadFourSearchBeanVO vo,
                                                     HttpServletRequest request) {
         Map<String, Object> res =new HashMap<>();
         List<Map<String, Object>> Map = new ArrayList<Map<String, Object>>();
         try { 
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             if(vo.getUser_id()==null){
                 vo.setUser_id(personnel.getPersonnel_id().toString());
             }
            Map = houseLoanServiceFour.getBizHouseLoanListUp(vo, personnel);
             res.put("ret_code", ResultHelper.RET_SUCCESS);
             res.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             e.printStackTrace();
         } 
         res.put("ret_data", Map);
         return res;
     }
    
     /**
      * 接口编号: WMS_OUT_007
      * @Title: getBizHomePagePerformance
      * @Description: 首页、个人、团队等业绩统计
      * @param queryInfo
      * @param request
      * @return 
      * @author: 王毅晗
      * @time:2017年3月20日 上午11:23:00
      * history:
      * 1、2017年3月20日 wangyihan 创建方法
      */
     @RequestMapping(value = "/wms/getBizHomePagePerformance.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizHomePagePerformance(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try { 
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             queryInfo.setPersonnel(personnel);
             queryInfo = this.houseLoanServiceFour.getBizHomePagePerformance(queryInfo);
             
             resMap.put("ret_data", queryInfo.getResMap());
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             log.error(e.getMessage());
         } 
         
         return resMap;
     }
     
     /**
      * 接口编号: WMS_OUT_019
      * @Title: getBizPerformanceRanking
      * @Description: 获取我的业绩排名信息
      * @param queryInfo
      * @param request
      * @return 
      * @author: 王毅晗
      * @time:2017年3月20日 上午11:23:00
      * history:
      * 1、2017年3月20日 wangyihan 创建方法
      */
     @RequestMapping(value = "/wms/getBizPerformanceRanking.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizPerformanceRanking(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try { 
             SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
             String now_date = format.format(new Date());
             
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             queryInfo.setPersonnel(personnel);
             queryInfo = this.houseLoanServiceFour.getBizPerformanceRanking(queryInfo);
             
             resMap.put("month_num", now_date);
             resMap.put("rankingmap", queryInfo.getResMap());
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             log.error(e.getMessage());
         } 
         
         return resMap;
     }
     
     /**
      * 接口编号: WMS_OUT_022
      * @Title: getBizResultsStatistical
      * @Description: 获取月份业绩统计信息（我的业绩、团队、门店、公司）
      * @param queryInfo
      * @param request
      * @return 
      * @author: 王毅晗
      * @time:2017年3月20日 上午11:23:00
      * history:
      * 1、2017年3月20日 wangyihan 创建方法
      */
     @RequestMapping(value = "/wms/getBizResultsStatistical.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizResultsStatistical(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try { 
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             queryInfo.setPersonnel(personnel);
             queryInfo = this.houseLoanServiceFour.getBizResultsStatistical(queryInfo);
             
             resMap.put("title_name", queryInfo.getTitle_name());
             resMap.put("yearList", queryInfo.getList());
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             log.error(e.getMessage());
         } 
         
         return resMap;
     }
     
     /**
      * 接口编号: WMS_OUT_026
      * @Title: getBizMonthMembers
      * @Description: 获取团队X月成员业绩统计信息
      * @param queryInfo
      * @param request
      * @return 
      * @author: 王毅晗
      * @time:2017年3月20日 上午11:23:00
      * history:
      * 1、2017年3月20日 wangyihan 创建方法
      */
     @RequestMapping(value = "/wms/getBizMonthMembers.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizMonthMembers(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try { 
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             queryInfo.setPersonnel(personnel);
             queryInfo = this.houseLoanServiceFour.getBizMonthMembers(queryInfo);
             
             SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
             Date date = format.parse(queryInfo.getDate_info());
             Calendar c = Calendar.getInstance();
             c.setTime(date);
             
             resMap.put("title_name", (c.get(Calendar.MONTH) + 1) + "月成员业绩统计"); 
             resMap.put("performanceList", queryInfo.getList());
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             log.error(e.getMessage());
         } 
         
         return resMap;
     }
     
     /**
     * 接口编号: WMS_OUT_028  3.2.28
     * @Title: getBizMembersProgressStatistical
     * @Description: 获取成员单据统计信息
     * @param queryInfo
     * @param request
     * @return 
     * @author: 王毅晗
     * @time:2017年3月20日 上午11:23:00
     * history:
     * 1、2017年3月20日 wangyihan 创建方法
     */
     @RequestMapping(value = "/wms/getBizMembersProgressStatistical.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizMembersProgressStatistical(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
         try {
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             queryInfo.setPersonnel(personnel);
             queryInfo = this.houseLoanServiceFour.getBizMembersProgressStatistical(queryInfo);
            map.put("progressList", queryInfo.getList());
            resMap.put("ret_data", map);
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             log.error(e.getMessage());
         } 
         
         return resMap;
     }
     
     /**
      * 接口编号: WMS_OUT_031
      * @Title: getBizStoreResultsStatistics
      * @Description: 获取团队、门店X月业绩统计信息
      * @param queryInfo
      * @param request
      * @return 
      * @author: 王毅晗
      * @time:2017年3月20日 上午11:23:00
      * history:
      * 1、2017年3月20日 wangyihan 创建方法
      */
     @RequestMapping(value = "/wms/getBizStoreResultsStatistics.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizStoreResultsStatistics(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try { 
             PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
             queryInfo.setPersonnel(personnel);
             queryInfo = this.houseLoanServiceFour.getBizStoreResultsStatistics(queryInfo);
             
             resMap.put("month_num", queryInfo.getDate_info());
             resMap.put("performance_statistics", queryInfo.getList());
             resMap.put("ret_code", ResultHelper.RET_SUCCESS);
             resMap.put("ret_msg", "请求成功!");    
         } catch (Exception e) { 
             resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
             log.error(e.getMessage());
         } 
         
         return resMap;
     }
     
     /**
    * 接口编号: WMS_OUT_032   3.2.32
    * @Title: getBizCompanyProgressStatistical
    * @Description: 获取单据统计信息
    * @param queryInfo
    * @param request
    * @return 
    * @author: 王毅晗
    * @time:2017年3月20日 上午11:23:00
    * history:
    * 1、2017年3月20日 wangyihan 创建方法
    */
     @RequestMapping(value = "/wms/getBizCompanyProgressStatistical.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizCompanyProgressStatistical(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try { 
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            queryInfo.setPersonnel(personnel);
            queryInfo = this.houseLoanServiceFour.getBizCompanyProgressStatistical(queryInfo);
            resMap.put("progressList", queryInfo.getList());
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
         } catch (Exception e) { 
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            log.error(e.getMessage());
         } 
         return resMap;
     }        
     
     /**
     * 
     * @Title: getLoanApprovalInfo
     * @Description: (3.2.15   WMS_OUT_015 获取单据详细信息)
     * @param wms_cre_credit_head_id
     * @param request
     * @return 
     * @author: jiaodelong
     * @time:2017年3月23日 下午3:57:55
     * history:
     * 1、2017年3月23日 jiaodelong 创建方法
     */
     @RequestMapping(value = "/wms/getBizDetailsDocuments.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
    public Map<String, Object> getBizDetailsDocuments(WmsCreCreditHeadFourSearchBeanVO bean, HttpServletRequest request)
    {
         Map<String, Object> resMap = new HashMap<String, Object>();
         try {
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            resMap = houseLoanServiceFour.getBizDetailsDocuments(bean, personnel);
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
      * 
      * @Title: getBizCustomerVerification
      * @Description: TODO(3.2.10   WMS_OUT_010 获取客户验证信息)
      * @param vo
      * @param request
      * @return 
      * @author: jiaodelong
      * @time:2017年3月23日 下午5:08:20
      * history:
      * 1、2017年3月23日 jiaodelong 创建方法
      */
     @RequestMapping(value = "/wms/getBizCustomerVerification.do", method = { RequestMethod.GET, RequestMethod.POST })
     @ResponseBody
     public Map<String, Object> getBizCustomerVerification(WmsCreCreditHeadFourSearchBeanVO vo, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<Map<String, Object>> retlistMap = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        int m = 0;
        try
        {
            retlistMap = iwmscrecreditheadservice.getBizDetailsDocuments(vo);
            m = iwmscrecreditheadservice.getRefuseloan(vo);
            // 判断页数是不是第一页
            if (vo.getPage() == 1)
            {
                listMap = retlistMap;
            }
            else
            {
                // 如果不是第一页那就需要重新获取第一页数据判断是否存在客户
                vo.setPage(1);
                listMap = iwmscrecreditheadservice.getBizDetailsDocuments(vo);
            }
            // 判断客户情况开始
            if (m < 1 && listMap.size() == 0)
            {
                resMap.put("return_value", "未查到该客户相关信息!");
            }
            else if (m > 0)
            {
                resMap.put("return_value", "此客户已被拒贷!");
            }
            else
            {
                resMap.put("return_value", "此客户存在未结清的贷款记录!");
            }

            if (m < 1 && listMap.size() == 0)
            {
                resMap.put("results_state", 1);
            }
            else if (m < 1 && listMap.size() > 0)
            {
                resMap.put("results_state", 2);
            }
            else if (m > 0 && listMap.size() == 0)
            {
                resMap.put("results_state", 3);
            }
            else if (m > 0 && listMap.size() > 0)
            {
                resMap.put("results_state", 4);
            }

            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
        }
        // resMap.put("loanList", retlistMap);// 贷款集合
        return resMap;
     }

     /**
      * 
      * @Title: getHomePageNotice
      * @Description: TODO(获取首页公告)
      * @param request
      * @param bean
      * @return 
      * @author: baisong
      * @time:2017年3月28日 下午3:06:28
      * history:
      * 1、2017年3月28日 baisong 创建方法
      */
     @RequestMapping(value = "/wms/GetHomePageNotice.do", method = { RequestMethod.POST, RequestMethod.GET })
     @ResponseBody
     public Map<String, Object> getHomePageNotice(HttpServletRequest request, SysNoticeHeadSearchBeanVO bean)
     {
         Map<String, Object> retMap = new HashMap<>();
         HttpSession session = request.getSession();
         PmPersonnel personnel = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        String appName = request.getHeader(GlobalVal.SSO_MODULE);

         try
         {
             if (bean.getNotice_app_type() == null || "".equals(bean.getNotice_app_type()))
             {
                 // app别名 如mis
                 bean.setNotice_app_type(appName);
             }
             retMap = houseLoanServiceFour.getHomePageNotice(bean, personnel);
             retMap.put("ret_code", ResultHelper.RET_SUCCESS);
             retMap.put("ret_msg", "请求成功!");
         }
         catch (Exception e)
         {
             log.error(e.getMessage());
             retMap.put("ret_code", ResultHelper.RET_ERROR);
            retMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
         }
         return retMap;
     }

    /**
     * 
     * @Title: postBizDeleteBill
     * @Description:手机端贷款单据删除(3.2.35 手机端贷款单据删除)
     * @param request
     * @param bean
     * @return 
     * @author: baisong
     * @time:2017年3月28日 下午3:06:28
     * history:
     * 1、2017年3月28日 baisong 创建方法
     */
    @RequestMapping(value = "/wms/postBizDeleteBill.do", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> postBizDeleteBill(HttpServletRequest request, WmsCreCreditHeadSearchBeanVO queryInfo)
    {
        Map<String, Object> retMap = new HashMap<>();
        HttpSession session = request.getSession();
        PmPersonnel personnel = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        String appName = request.getHeader(GlobalVal.SSO_MODULE);
        try
        {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "WMS_OUT_POSTbizDeleteBill"));
            nvps.add(new BasicNameValuePair("sys_num", "MIS"));

            nvps.add(new BasicNameValuePair("personnel_id", personnel.getPersonnel_id().toString()));
            if (queryInfo.getWms_cre_credit_head_id() != null)
            {
                nvps.add(new BasicNameValuePair("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString()));
            }
            else
            {
                retMap.put("ret_code", ResultHelper.RET_ERROR);
                retMap.put("ret_msg", "参数错误！");
                return retMap;
            }
            nvps.add(new BasicNameValuePair("user_id", "0"));
            try
            {
                retMap = HttpClientUtil.post(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), nvps, Map.class);
            }
            catch (ConnectException e)
            {
                retMap.put("ret_code", ResultHelper.RET_ERROR);
                retMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
                return retMap;
            }
            catch (Exception e)
            {
                retMap.put("ret_code", ResultHelper.RET_ERROR);
                retMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
                return retMap;
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            retMap.put("ret_code", ResultHelper.RET_ERROR);
            retMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            return retMap;
        }
        retMap.put("ret_code", ResultHelper.RET_SUCCESS);
        retMap.put("ret_msg", "操作成功!");
        return retMap;
    }
    
    
    /**
     * 接口编号: WMS_OUT_025
     * @Title: statusStatisticsReportUp
     * @Description: 单据统计（年、状态节点统计）
     * @param queryInfo
     * @param request
     * @return 
     * @author: wangyihan
     * @time:2017年7月24日 上午11:23:00
     * history:
     * 1、2017年7月24日 wangyihan 创建方法
     */
    @RequestMapping(value = "/wms/statusStatisticsReportUp.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> statusStatisticsReportUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try { 
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            queryInfo.setPersonnel(personnel);
            queryInfo = this.houseLoanServiceFour.statusStatisticsReportUp(queryInfo);
            
            resMap.put("ret_data", queryInfo.getResMap());
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");    
        } catch (Exception e) { 
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            log.error(e.getMessage());
        } 
        
        return resMap;
    }
    
    /**
     * 接口编号: WMS_OUT_025
     * @Title: detailedStatisticsUp
     * @Description: 状态报表状态节点统计（月、日）
     * @param queryInfo
     * @param request
     * @return 
     * @author: wangyihan
     * @time:2017年7月24日 上午11:23:00
     * history:
     * 1、2017年7月24日 wangyihan 创建方法
     */
    @RequestMapping(value = "/wms/detailedStatisticsUp.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> detailedStatisticsUp(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try { 
            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            queryInfo.setPersonnel(personnel);
            queryInfo = this.houseLoanServiceFour.detailedStatisticsUp(queryInfo);
            
            resMap.put("ret_data", queryInfo.getResMap());
            resMap.put("ret_code", ResultHelper.RET_SUCCESS);
            resMap.put("ret_msg", "请求成功!");    
        } catch (Exception e) { 
            resMap.put("ret_code", ResultHelper.RET_ERROR);
            resMap.put("ret_msg", WmsHelp.RET_ERROR_VALUE);
            log.error(e.getMessage());
        } 
        
        return resMap;
    }

    
    
}
