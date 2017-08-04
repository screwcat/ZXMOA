package com.zx.moa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.zx.moa.ioa.systemmanage.persist.LoginManageDao;
import com.zx.moa.ioa.util.InitUnfinishedRemind;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 发送短信
 * 
 * @author MATF
 */
public class SMSUtil
{

    /**
     * 超时时间
     */
    public static Integer timeOut = 10000;

    private static final boolean IS_UNFEEDBACK_SEND = true;

    public static String sendUnfeedbackSMS(String code, String tpl_id, Map<String, Object> msgMap) throws Exception
    {
        if (IS_UNFEEDBACK_SEND)
        {
            LoginManageDao loginDao = (LoginManageDao) InitUnfinishedRemind.ac.getBean(LoginManageDao.class);
            PmPersonnel personnel = loginDao.findPersonnelByCode(code);
            if (personnel != null)
            {
                String tpl_value = "";
                if (msgMap != null)
                {
                    tpl_value = new ObjectMapper().writeValueAsString(msgMap);
                }
                return sendSMS(personnel.getPersonnel_contacttel(), tpl_id, tpl_value);
            }
        }
        return null;
    }

    /**
     * 发送短信
     * 
     * @param tel 电话号码
     * @param telMessage 短信信息
     * @throws Exception 发送错误
     */
    public static String sendSMS(String tel, String tpl_id, String tpl_value) throws Exception
    {
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("nozzleUrl");
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", "SMS_OUT_003"));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        nvps.add(new BasicNameValuePair("mobile", tel));
        nvps.add(new BasicNameValuePair("tpl_id", tpl_id));
        nvps.add(new BasicNameValuePair("tpl_value", tpl_value));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        /**
         * 设置超时(连接与传输)10S
         */
        RequestConfig rc = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
        httpPost.setConfig(rc);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try
        {
            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                return str;
            }
            else
            {
                return null;
            }
        }
        finally
        {
            response.close();
            httpclient.close();
        }
    }
}
