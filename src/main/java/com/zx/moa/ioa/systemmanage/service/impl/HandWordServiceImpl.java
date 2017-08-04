package com.zx.moa.ioa.systemmanage.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.systemmanage.service.IHandWordService;
import com.zx.moa.util.HttpClientUtil;


@Service("handWordService")
public class HandWordServiceImpl implements IHandWordService
{

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> handWordOpenOrClosed(HttpServletRequest request, Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        String url = HttpClientUtil.getSysUrl("nozzleUrl");
        String app_name = map.get("app_name").toString();
        String hand_word = map.get("hand_word") == null ? "" : map.get("hand_word").toString();
        String enable_flag = map.get("enable_flag").toString();
        String user_code = map.get("user_code").toString();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", "ESM_OUT_HandWord"));
        nvps.add(new BasicNameValuePair("sys_num", app_name));
        nvps.add(new BasicNameValuePair("app_name", app_name));
        nvps.add(new BasicNameValuePair("user_code", user_code));
        nvps.add(new BasicNameValuePair("hand_word", hand_word));
        nvps.add(new BasicNameValuePair("enable_flag", enable_flag));
        Map<String, Object> resmap = new HashMap<String, Object>();

        try
        {
            resmap = HttpClientUtil.post(url, nvps, Map.class);

            return resmap;
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
