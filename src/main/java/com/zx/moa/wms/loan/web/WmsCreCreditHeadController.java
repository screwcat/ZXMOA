package com.zx.moa.wms.loan.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.zx.moa.util.DateUtil;
import com.zx.moa.util.GlobalFileUtil;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingApplyAtt;
import com.zx.moa.util.gen.entity.wms.WmsCreHousingFileInfo;
import com.zx.moa.wms.loan.service.IWmsCreCreditHeadService;
import com.zx.moa.wms.loan.vo.WmsCreCreditHeadSearchBeanVO;
import com.zx.platform.syscontext.PlatformGlobalVar;

@Controller
public class WmsCreCreditHeadController {

    private static Logger log = LoggerFactory.getLogger(WmsCreCreditHeadController.class);

    @Autowired
    private IWmsCreCreditHeadService wmsCreCreDitHeadService;
    
    /**
     * Description : 房产抵押列表查询
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/loan/searchHouseLoanList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> searchHouseLoanList(WmsCreCreditHeadSearchBeanVO queryInfo,
                                                         HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        map.put("personnel_id", personnel.getPersonnel_id());
        
        return ResultHelper.getSuccess(wmsCreCreDitHeadService.searchHouseLoanList(queryInfo, personnel));
    }
    
    /**
     * Description : 房产代办件列表查询
     * 
     * @param queryInfo
     * @return record
     * @author jiaodelong
     */
    @RequestMapping(value = "/loan/wmsHousingCertificatesList.do", method = { RequestMethod.GET,
                                                                                              RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getwmsHousingCertificatesList(WmsCreCreditHeadSearchBeanVO queryInfo,
                                                         HttpServletRequest request) {
    	 Map<String, Object> map = new HashMap<String, Object>();
         PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
         map.put("personnel_id", personnel.getPersonnel_id());
//       return wmsCreCreDitHeadService.getwmsHousingCertificatesList(queryInfo, personnel);
         return ResultHelper.getSuccess(wmsCreCreDitHeadService.getwmsHousingCertificatesList(queryInfo, personnel));
    }
    
    /**
     * Description : 房产抵押单据查看明细
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/loan/searchHouseLoanInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> searchHouseLoanInfo(WmsCreCreditHeadSearchBeanVO queryInfo,
                                                         HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        map.put("personnel_id", personnel.getPersonnel_id());
        
        return wmsCreCreDitHeadService.searchHouseLoanInfo(queryInfo, personnel);
    }
    
    /**
     * Description : 房产图片上传
     * 
     * @param queryInfo
     * @return record
     * @author wangyihan
     */
    @RequestMapping(value = "/loan/houseLoanUpload.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ModelAndView houseLoanUpload(@RequestParam(value = "imgFile") MultipartFile[] imgFile, WmsCreCreditHeadSearchBeanVO queryInfo,
                                                         HttpServletRequest request) {
        
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        queryInfo.setImgFile(imgFile);
        
        Map<String, Object> ret_map = new HashMap<String,Object>();
        RestTemplate restTemplate = new RestTemplate();
        String temp = null;
        String result = "1";
        String catalog = DateUtil.date2String(new Date(), "yyyyMM");
        
        List<WmsCreHousingApplyAtt> attList = new ArrayList<WmsCreHousingApplyAtt>();
        int count = 0;
        for(int i = 0; i < queryInfo.getImgCounts().length; i++) {
            for(int j = 0; j < queryInfo.getImgCounts()[i]; j++) {
                try {
                        MultipartFile mf = imgFile[count];
                        long fileSize = mf.getSize();
                        if (fileSize > 60 * 1024 * 1024)
                        {
                            result = "操作失败，请联系客服！";
                        }
                        String srcFileName = mf.getOriginalFilename();
                        String postfix = "";
                        if (srcFileName.lastIndexOf(".") > -1)
                        {
                            postfix = srcFileName.substring(srcFileName.lastIndexOf(".") + 1).toLowerCase();
                            if ("exe".equals(postfix) || "cmd".equals(postfix) || "bat".equals(postfix))
                            {
                                ret_map.put("ret_code", "002");
                                ret_map.put("ret_msg", "操作失败，请联系客服");
                                ret_map.put("result", "filetypeerror");
                            }
                        }
                        //文件相对路径
                        String relative_path = GlobalFileUtil.saveUploadFileForWMS(mf.getInputStream(), personnel.getPersonnel_id().toString(), mf.getOriginalFilename(), catalog);
                        //文件绝对路径
                        String absolute_path = PlatformGlobalVar.SYS_PROPERTIES.get("realUploadCatalog") + relative_path;
                        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
                        File file = new File(absolute_path);
                        FileSystemResource resource = new FileSystemResource(file); 
                        form.add("jarFile", resource);
                        form.add("user_id", personnel.getPersonnel_id().toString());
                        form.add("catalog", catalog);
                        restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsFileUrl") + "/util/saveFileForRestTemplate.do", form, String.class);
                        file.delete();
                        
                        queryInfo.setAttachment_type(
                                queryInfo.getImgFile()[count].getOriginalFilename().substring(queryInfo.getImgFile()[count].getOriginalFilename().lastIndexOf(".") + 1));
                        queryInfo.setAttachment_new_name(relative_path.substring(0, relative_path.lastIndexOf(".")));
                        queryInfo.setAttachment_address(relative_path);
                        queryInfo.setAttachment_old_name(queryInfo.getImgFile()[count].getOriginalFilename());
                        queryInfo.setData_type_name(queryInfo.getP_wms_sys_dict_data_ids()[i].toString());//大类别
                        queryInfo.setData_detail_name(queryInfo.getWms_sys_dict_data_ids()[i].toString());//小类别
                        queryInfo.setWms_cre_credit_head_id(queryInfo.getWms_cre_credit_head_id());
                        
                        attList.add(wmsCreCreDitHeadService.saveWmsCreHousingApplyAtt(queryInfo, personnel));
                        
                        count++;
                }catch(Exception e) {
                    ret_map.put("ret_code", "002");
                    ret_map.put("ret_msg", "操作失败，请联系客服");
                    e.printStackTrace();
                    result = "0";
                }
            }
        }
    
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("list", new Gson().toJson(attList));
        form.add("personnel_id", personnel.getPersonnel_id().toString());
        form.add("wms_cre_credit_head_id", queryInfo.getWms_cre_credit_head_id().toString());
        form.add("falg", queryInfo.getSave_type().toString());
        
        form.add("interface_num", "WMS_OUT_POSTwmsMoaSave");
        form.add("sys_num", "MIF");
        Map<String, Object> resMap = restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl"), form, Map.class);
        //Map<String, Object> resMap = restTemplate.postForObject(PlatformGlobalVar.SYS_PROPERTIES.get("server.telInfoPlatFormName") + "/restful/simple", form, Map.class);

        request.getSession().setAttribute("message", resMap.get("message"));
        return new ModelAndView("redirect:http://" + queryInfo.getHost() + "/MOA/resources/html/ajaxfileUpload.jsp");
    }
    
}
