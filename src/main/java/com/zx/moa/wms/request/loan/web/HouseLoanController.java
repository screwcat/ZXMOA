package com.zx.moa.wms.request.loan.web;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zx.moa.wms.loan.service.IWmsSysDictDataService;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.moa.wms.loan.vo.WmsSysDictDataSearchBeanVO;
import com.zx.moa.wms.request.loan.service.HouseLoanService;
import com.zx.platform.syscontext.PlatformGlobalVar;


/**
 * MOA外部请求类
 * @author wangyihan
 *
 */

@Controller
public class HouseLoanController {

    private static Logger log = LoggerFactory.getLogger(HouseLoanController.class);

    @Autowired
    private HouseLoanService houseLoanService;
    
    @Autowired
    private IWmsSysDictDataService wmssysdictdataService;
    
    /**
     * Description : 房产图片上传(第一步:仅保存图片)
     * 
     * @param File imgFile, Integer user_id, String host, String port
     * @return ResultBean<String>
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/houseLoanUploadStepOne.do", method = { RequestMethod.GET, RequestMethod.POST })
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
            return ResultHelper.getError(e.getMessage());
        }
    
        return ResultHelper.getSuccess(relative_path);
    }
    
    /**
     * Description : 房产图片上传(第二步:保存图片信息)
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/houseLoanUploadStepTwo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> houseLoanUpload(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        
        log.info(new Gson().toJson(queryInfo));
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        List<WmsCreHousingApplyAtt> attList = gson.fromJson(queryInfo.getFile_info_json(), 
                new TypeToken<List<WmsCreHousingApplyAtt>>(){}.getType());
        
        //获取扩展名与文件新名称
        for(WmsCreHousingApplyAtt bean : attList) {
            try {
                bean.setAttachment_new_name(bean.getAttachment_address().substring(0, bean.getAttachment_address().lastIndexOf(".")));
                bean.setAttachment_type(bean.getAttachment_address().substring(bean.getAttachment_address().lastIndexOf(".") + 1));
                
            } catch (Exception e) {
                return ResultHelper.getError(bean.getAttachment_address() + "格式不正确");
            }
            
        }
        
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("list", new Gson().toJson(attList));
        form.add("personnel_id", personnel.getPersonnel_id());
        form.add("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id());
        form.add("falg", queryInfo.getSave_type().toString());
        
        form.add("interface_num", "WMS_OUT_POSTwmsMoaSave");
        form.add("sys_num", "MIF");
        Map<String, Object> resMap = restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), form, Map.class);

        return ResultHelper.getSuccess(resMap.get("message").toString());
    }
    
    /**
     * Description : 房产抵押列表查询
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/searchHouseLoanList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> searchHouseLoanList(WmsCreCreditHeadSearchBeanVO queryInfo,
            HttpServletRequest request) {
        log.info(new Gson().toJson(queryInfo));
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        return ResultHelper.getSuccess(houseLoanService.searchHouseLoanList_Request(queryInfo, personnel));
    }
    
    /**
     * Description : 房产代办件列表查询
     * 
     * @param queryInfo
     * @return record
     * @author jiaodelong
     */
    @RequestMapping(value = "/wms/wmsHousingCertificatesList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getwmsHousingCertificatesList(WmsCreCreditHeadSearchBeanVO queryInfo, 
            HttpServletRequest request) {
         log.info(new Gson().toJson(queryInfo));

         PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
         return ResultHelper.getSuccess(houseLoanService.getwmsHousingCertificatesList_Request(queryInfo, personnel));
    }
    
    /**
     * Description : 房产抵押单据查看明细
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/searchHouseLoanInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> searchHouseLoanInfo(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request) {
        log.info(new Gson().toJson(queryInfo));
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> res = houseLoanService.searchHouseLoanInfo_Request(queryInfo, personnel);
        res.put("ret_code", ResultHelper.RET_SUCCESS);
        res.put("ret_msg", "请求成功!");
        
        return res;
    }
    
    /**
     * Description : 查看图片
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/wms/viewImg.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public void getImg(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request, HttpServletResponse response) {
        log.info(new Gson().toJson(queryInfo));
        
        String url = HttpClientUtil.getSysUrl("server.wmsGetFileUrl") + queryInfo.getUrl();
        
        HttpClientUtil.loadImg(url, response);
    }
    
    /**
     * Description :get record list records by vo queryInfo withnot paging
     * @param queryInfo
     * @return record list
     * @author auto_generator
     */
    @RequestMapping(value = "/wms/getDataDict.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getListWithoutPaging(WmsSysDictDataSearchBeanVO queryInfo, HttpServletRequest request, HttpServletResponse response) {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            
            List<Map<String, Object>> dataDictList = houseLoanService.getDataDict(queryInfo);

            //用于判断补件后是否可以继续补件(房贷单据页面不即时刷新)
            if(null != request.getParameter("wms_cre_credit_head_id")) {
                WmsCreCreditHeadSearchBeanVO queryInfo1 = new WmsCreCreditHeadSearchBeanVO();
                queryInfo1.setWms_cre_credit_head_id(Integer.parseInt(request.getParameter("wms_cre_credit_head_id").toString()));
                Map<String, Object> certificate_flag = houseLoanService.getHousingCertificatesFlag_Request(queryInfo1, personnel);
                res.put("is_sure_certificate_flag", certificate_flag.get("is_sure_certificate_flag"));
                res.put("is_sure_handle_certificates_flag", certificate_flag.get("is_sure_handle_certificates_flag"));
            }
            
            res.put("ret_data", dataDictList);
            res.put("ret_code", ResultHelper.RET_SUCCESS);
            res.put("ret_msg", "请求成功!");
        } catch(Exception e) {
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", e.getMessage());
            return res;
        }
        
        return res;
    }
    
    /**
     * Description : 判断单据是否可以办件/补件
     * @param queryInfo
     * @return record list
     * @author auto_generator
     */
    @RequestMapping(value = "/wms/isSureCertificate.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> isSureCertificate(WmsCreCreditHeadSearchBeanVO queryInfo, HttpServletRequest request, HttpServletResponse response) {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            //用于判断补件后是否可以继续补件(房贷单据页面不即时刷新)
            if(null != request.getParameter("wms_cre_credit_head_id")) {
                res.put("ret_data", houseLoanService.getHousingCertificatesFlag_Request(queryInfo, personnel));
                res.put("ret_code", ResultHelper.RET_SUCCESS);
                res.put("ret_msg", "请求成功!");
            } else {
                res.put("ret_data", new HashMap<String, Object>());
                res.put("ret_code", ResultHelper.RET_ERROR);
                res.put("ret_msg", "wms_cre_credit_head_id为空!");
            }
            
        } catch(Exception e) {
            res.put("ret_code", ResultHelper.RET_ERROR);
            res.put("ret_msg", e.getMessage());
            return res;
        }
        
        return res;
    }
    
}
