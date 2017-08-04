package com.zx.moa.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * @ClassName: HttpClientUtil
 * @Description: 内容摘要：http请求帮助类
 * @author sunlq
 * @date 2016年11月25日
 * @version V1.0
 * history:
 * 1、2016年11月25日 sunlq 创建文件
 */
public class HttpClientUtil {
	
    private final static Integer TIMEOUT = 5 * 60 * 1000;
	
	public static String post(String url,List<NameValuePair> nvps) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		if(nvps == null){
			nvps = new ArrayList<NameValuePair>();
		}
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig rc = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).build();
        httpPost.setConfig(rc);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            if(response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
                return str;
            }else{
            }
        } finally {
            response.close();
            httpclient.close();
        }
        return "";
	}
	
	public static <T> T post(String url,List<NameValuePair> nvps,Class<T> clazz) throws ClientProtocolException, IOException{
		String result = post(url, nvps);
		return JSON.parseObject(result, clazz);
	}
	
	public static void loadImg(String url,HttpServletResponse res){
		HttpGet get = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig rc = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(5000).build();
        get.setConfig(rc);
        CloseableHttpResponse response;
		try {
			response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				res.setContentType(getFileContentType(url)+";charset=utf-8");
				if(!isImg(url)){
					res.setHeader("Content-Disposition", "attachment;filename="+ 
							new String((getFileName(url)).getBytes(), "iso-8859-1"));
				}
				res.getOutputStream().write(EntityUtils.toByteArray(entity));
			} finally {
				response.close();
				httpclient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getFileName(String url){
		if(url.indexOf("/")>=0){
			url = url.substring(url.lastIndexOf("/")+1);
		}
		return url;
	}
	
	public static String getFileContentType(String url){
		String type = "";
		if(url.indexOf(".")>=0){
			type = url.substring(url.lastIndexOf(".")+1);
		}
		if(isImg(url)){
			return "image/"+type;
		}else if("xls,xlsx".indexOf(type)>=0){
			return "application/vnd.ms-excel";
		}else if("doc,docx".indexOf(type)>=0){
			return "application/msword";
		}else if("txt".indexOf(type)>=0){
			return "text/plain";
		}else{
			return "application/octet-stream";
		}
	}
	
	public static boolean isImg(String url){
		String type = "";
		if(url.indexOf(".")>=0){
			type = url.substring(url.lastIndexOf(".")+1);
		}
		return "jpg,png,jpeg,gif,bmp,wbmp".indexOf(type)>=0;
	}

    /**
     * @Title: getSysUrl
     * @Description: 获取sys.properties里配置
     * @param val
     * @return 
     * @author: sunlq
     * @time:2016年11月25日 下午2:57:54
     * history:
     * 1、2016年11月25日 sunlq 创建方法
     */
	public static String getSysUrl(String val){
        // moa文件地址，返回https地址
        if (val.equals("moaUrl"))
        {
            if (RequestContextHolder.getRequestAttributes() != null)
            {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                if (request.getHeader("UPLOAD_URL") != null)
                {

                    return request.getHeader("UPLOAD_URL");
                }
                else
                {
                    return PlatformGlobalVar.SYS_PROPERTIES.get(val);
                }
            }
            else
            {
                return PlatformGlobalVar.SYS_PROPERTIES.get(val);
            }
        }
        // 返回http地址
        else
        {
            return PlatformGlobalVar.SYS_PROPERTIES.get(val);
        }
	}
}
