package com.zx.moa.crm.customermanage.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zx.moa.crm.customermanage.service.ICustomerService;
import com.zx.moa.crm.customermanage.vo.CRMCustomerInfo;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.util.StringUtil;

@Controller
public class CustomerController
{

    @Autowired
    private ICustomerService customerService;

    /**
     * 
     * @Title: getInfoAll
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param request
     * @return 
     * @author: 张明建
     * @time:2017年2月6日 上午10:29:55
     * history:
     * 1、2017年2月6日 张明建 创建方法
     */
    @RequestMapping(value = "crm/getCustomerList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getInfoAll(HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        map.put("personnel_id", personnel.getPersonnel_id());
        String page = request.getParameter("page");
        String size = request.getParameter("size");
        String search = request.getParameter("search");
        String drop = request.getParameter("drop");
        if (StringUtils.isNotBlank(search))
        {
            map.put("search", search);
        }
        if (StringUtils.isNotBlank(drop))
        {
            map.put("drop", drop);
        }
        if (StringUtils.isNotBlank(page))
        {
            map.put("page", Integer.parseInt(page));
        }
        if (StringUtils.isNotBlank(size))
        {
            map.put("size", Integer.parseInt(size));
        }
        return ResultHelper.getSuccess(customerService.getInfoAll(map));
    }

    /**
     * 根据客户costomer_id获取客户信息
     * @param costomer_id 
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "crm/getCustomerInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getCustomerInfo(HttpServletRequest request) throws Exception
    {
        String version = request.getParameter("v");
        if (null == version)
        {
            Integer costomer_id = 0;
            if (StringUtil.isNotBlank(request.getParameter("costomer_id")))
            {
                costomer_id = Integer.parseInt(request.getParameter("costomer_id"));
            }
            return ResultHelper.getSuccess(customerService.getCustomerById(costomer_id));
        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(version, "1.2.1") >= 0)
        {
            Integer costomer_id = 0;
            if (StringUtil.isNotBlank(request.getParameter("costomer_id")))
            {
                costomer_id = Integer.parseInt(request.getParameter("costomer_id"));
            }
            Map<String, Object> map = null;
            List<Map<String, Object>> list = null;
            try
            {
                map = customerService.getCustomerByIdV121(costomer_id);
                if (map != null)
                {
                    if (map.get("customer_level") == null)
                    {
                        map.put("customer_level", "");
                    }
                    if (map.get("customer_state") == null)
                    {
                        map.put("customer_state", "");
                    }
                    if (map.get("customer_sources") == null)
                    {
                        map.put("customer_sources", "");
                    }
                    if (map.get("sign_check") != null )
                    {
                        // 是否签单 签单则查看收益卡信息
                        if ((map.get("sign_check").toString()).equals("1"))
                        {
                            list = getModiConnection("MWF_OUT_GETCustomerBankcardInformation", map);
                        }

                    }

                    if (map.get("certificate_type") != null)
                    {
                        if (!(map.get("certificate_type").toString()).equals("1142"))
                        {
                            map.put("id_card_number", "");
                        }
                    }
                    if (map.get("sign_check").equals(""))
                    {
                        map.put("sign_check", 0);
                    }
                }
                else
                {
                    map = new HashMap<>();
                    ResultBean<Map<String, Object>> rb = new ResultBean<>();
                    rb.setRet_code("002");
                    rb.setRet_msg("此客户不存在!");
                    return rb;
                }

            }
            catch (ClientProtocolException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (list != null && list.size() > 0)
            {
                map.put("bankcard_list", list);
            }
            else
            {
                map.put("bankcard_list", new ArrayList<>());
            }
            return ResultHelper.getSuccess(map);
        }
        return null;
    }

    /**
     * @Title: getModiConnection
     * @Description: TODO(调用接口获取信息)
     * @param interfaceNumber
     * @param map
     * @return
     * @throws ClientProtocolException 
     * @author: suncf
     * @time:2017年2月6日 下午4:47:30
     * history:
     * 1、2017年2月6日 suncf 创建方法
     */
    private List<Map<String, Object>> getModiConnection(String interfaceNumber, Map<String, Object> map) throws ClientProtocolException
    {

        String url = HttpClientUtil.getSysUrl("nozzleUrl");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", interfaceNumber));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        nvps.add(new BasicNameValuePair("customer_name", ""));
        nvps.add(new BasicNameValuePair("id_card_number", map.get("id_card_number").toString()));
        if (map.get("create_user_id") != null)
        {
            nvps.add(new BasicNameValuePair("user_id", map.get("create_user_id").toString()));
        }
        else
        {
            nvps.add(new BasicNameValuePair("user_id", "0"));
        }

        Map<String, Object> resmap = new HashMap<String, Object>();
        List<Map<String, Object>> messageList = null;
        try
        {
            resmap = HttpClientUtil.post(url, nvps, Map.class);

            if (resmap.get("flag").equals(true))
            {
                Object str = resmap.get("data_list");
                // 将返回数据转换为List
                ObjectMapper mapper = new ObjectMapper();
                messageList = mapper.readValue(str.toString(), new TypeReference<List<Map<String, Object>>>()
                {
                });
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            resmap.put("message", e.getMessage());
        }
        // 判断返回数据是否为空 不为空则处理银行卡号
        if (messageList != null && messageList.size() > 0)
        {
            for (int i = 0; i < messageList.size(); i++)
            {
                if (StringUtils.isNotBlank((String) messageList.get(i).get("card_no")))
                {
                    String card_no = messageList.get(i).get("card_no").toString();
                    int length = card_no.length();
                    String star = "";
                    for (int j = 0; j < length - 4; j++)
                    {
                        star += "X";
                    }
                    if (length > 4)
                    {
                        String card = card_no.substring(length - 4, length);
                        messageList.get(i).put("card_no", star + card);
                    }
                    else
                    {
                        messageList.get(i).put("card_no", card_no);
                    }
                }
                else
                {
                    messageList.get(i).put("card_no", "");
                }
            }
        }
        return messageList;
    }


        /**
    * 
    * @Title: dealCustomerInfo
    * @Description: TODO(手机app新增和修改客户)
    * @param queryInfo
    * @param request
    * @return 
    * @author: 张明建
    * @time:2016年12月14日 下午3:55:10
    * history:
    * 1、2016年12月14日 张明建 创建方法
    */
    @RequestMapping(value = "/crm/dealCustomerInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String, Object>> dealCustomerInfo(CRMCustomerInfo queryInfo, HttpServletRequest request)
    {
        String v = request.getParameter("v");
        int num = 0;
        try
        {
            if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.2.1") >= 0)
            {
                num = 1;
            }
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (num == 0)
        {

            PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
            ResultBean<Map<String, Object>> rmap = new ResultBean<Map<String, Object>>();
            Map<String, Object> result = new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("customer_name", queryInfo.getCustomer_name());
            if (queryInfo.getContact_number() == null)
            {
                map.put("contact_number", "");
            }
            else
            {
                map.put("contact_number", queryInfo.getContact_number());
            }
            if (queryInfo.getOther_contact_way() == null)
            {
                map.put("other_contact_way", "");
            }
            else
            {
                map.put("other_contact_way", queryInfo.getOther_contact_way());
            }

            map.put("customer_level", queryInfo.getCustomer_level());
            map.put("customer_state", queryInfo.getCustomer_state());
            map.put("customer_sources", queryInfo.getCustomer_sources());
            map.put("remark", queryInfo.getRemark());
            map.put("costomer_id", queryInfo.getCostomer_id());
            map.put("personnel_id", personnel.getPersonnel_id());
            if (queryInfo.getCostomer_id() != null)
            {
                if (queryInfo.getOther_contact_way() == null && queryInfo.getContact_number() == null)
                {
                    rmap.setRet_code("002");
                    rmap.setRet_msg("手机号码和固定电话不能都为空");
                    return rmap;
                }
            }
            if (StringUtils.isNotBlank(queryInfo.getContact_number()))
            {
                if (queryInfo.getContact_number().length() != 11)
                {
                    rmap.setRet_code("002");
                    rmap.setRet_msg("手机号码长度应为11位");
                    return rmap;
                }
                if (!StringUtils.isNumeric(queryInfo.getContact_number()))
                {
                    rmap.setRet_code("002");
                    rmap.setRet_msg("手机号码格式错误");
                    return rmap;
                }
            }

            String url = HttpClientUtil.getSysUrl("nozzleUrl");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "CRM_OUT_UpdateCustomerInfo"));
            nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
            String customer = JSONObject.toJSONString(map);
            nvps.add(new BasicNameValuePair("customer", customer));
            nvps.add(new BasicNameValuePair("user_id", "0"));
            try
            {
                result = HttpClientUtil.post(url, nvps, Map.class);
                if (result.get("flag").toString().equals("false"))
                {

                    if (result.get("re_code") != null)
                    {
                        if (result.get("re_code").toString().equals("500"))
                        {
                            rmap.setRet_code("500");
                            rmap.setRet_msg(result.get("message").toString());
                        }
                        if (result.get("re_code").toString().equals("501"))
                        {
                            rmap.setRet_code("501");
                            rmap.setRet_msg(result.get("message").toString());
                        }
                    }
                    return rmap;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return ResultHelper.getNetworkError(result);
            }
            return ResultHelper.getSuccess(null);
        }
        else if(num==1)
        {
            return dealCustomerInfo_V121(queryInfo, request);
        }else{
            return null;
        }
    }

    /**
     * 
     * @Title: dealCustomerInfo_V121
     * @Description: TODO(手机app新增和修改客户(1.2.1版本))
     * @param queryInfo
     * @param request
     * @return 
     * @author: 张明建
     * @time:2017年2月6日 下午2:02:43
     * history:
     * 1、2017年2月6日 张明建 创建方法
     */
    private ResultBean<Map<String, Object>> dealCustomerInfo_V121(CRMCustomerInfo queryInfo, HttpServletRequest request)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        queryInfo.setPersonnel_id(personnel.getPersonnel_id());
        queryInfo.setPersonnel_name(personnel.getPersonnel_name());
        queryInfo.setPersonnel_shortcode(personnel.getPersonnel_shortcode());
        String v = request.getParameter("v");
        int num = 0;
            try
            {
                if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.2.1") >= 0)
                {
                    num = 1;
                }
            }
            catch (Exception e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        
        ResultBean<Map<String, Object>> rmap = new ResultBean<Map<String, Object>>();
        // 数据验证
        String val = customerService.verifyMethod(queryInfo);
        if (val.equals("true"))
        {
           
            Map<String, Object> result = new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("customer_name", queryInfo.getCustomer_name());
            if (queryInfo.getContact_number() == null)
            {
                map.put("contact_number", "");
            }
            else
            {
                map.put("contact_number", queryInfo.getContact_number());
            }
            if (queryInfo.getOther_contact_way() == null)
            {
                map.put("other_contact_way", "");
            }
            else
            {
                map.put("other_contact_way", queryInfo.getOther_contact_way());
            }

            map.put("customer_level", queryInfo.getCustomer_level());
            map.put("customer_state", queryInfo.getCustomer_state());
            map.put("customer_sources", queryInfo.getCustomer_sources());
            map.put("remark", queryInfo.getRemark());
            map.put("costomer_id", queryInfo.getCostomer_id());
            map.put("personnel_id", personnel.getPersonnel_id());
            map.put("id_card_number", queryInfo.getId_card_number());
            map.put("domicile_place", queryInfo.getDomicile_place());
            // 版本号
            map.put("v", num);

          String url = HttpClientUtil.getSysUrl("nozzleUrl");
     
            
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "CRM_OUT_UpdateCustomerInfo"));
            nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
            String customer = JSONObject.toJSONString(map);
            nvps.add(new BasicNameValuePair("customer", customer));
            nvps.add(new BasicNameValuePair("user_id", "0"));
            try
            {
                result = HttpClientUtil.post(url, nvps, Map.class);
                if (Boolean.parseBoolean(result.get("flag").toString()))
                {
                    return ResultHelper.getSuccess(result);
                }
                else
                {
                    return ResultHelper.getError(result.get("message").toString(), result);
                }
               
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return ResultHelper.getNetworkError(result);
            }


        }
        else
        {
            rmap.setRet_code("002");
            rmap.setRet_msg(val);
            return rmap;
        }
    }

}
