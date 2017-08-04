package com.zx.moa.util.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zx.moa.util.SysTools;
import com.zx.moa.util.SysUtil;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 输出系统日志过滤器
 * 
 * @author Administrator
 */
public class LoggerFilter implements Filter
{
    private static Logger log = LoggerFactory.getLogger(LoggerFilter.class);

    public static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
                                                                                     ServletException
    {
        String isSaveLog = PlatformGlobalVar.SYS_PROPERTIES.get("isSaveLog");
        if ("true".equals(isSaveLog))
        {
            String respStr = "";
            long startTime = System.currentTimeMillis();
            try
            {
                HttpServletResponse response = (HttpServletResponse) resp;
                ResponseWrapper responseWrapper = new ResponseWrapper(response);
                chain.doFilter(req, responseWrapper);
                byte[] data = responseWrapper.getResponseData();
                if (resp.getContentType() != null && resp.getContentType().contains("json"))
                {
                    respStr = new String(data,responseWrapper.getCharacterEncoding());
                }
                resp.setContentLength(-1);
                resp.getOutputStream().write(data);
            }
            catch (Throwable e)
            {
                log.error("chain error:", e);
            }
            long endTime = System.currentTimeMillis();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> names = req.getParameterNames();
            String name;
            String[] values;
            while (names.hasMoreElements())
            {
                name = names.nextElement();
                values = req.getParameterValues(name);
                if (values.length == 1)
                {
                    paramMap.put(name, values[0]);
                }
                else
                {
                    paramMap.put(name, values);
                }
            }
            try{
                HttpServletRequest request = (HttpServletRequest) req;
                if(request.getHeader(SysUtil.SSO_MODULE)!=null){
                    paramMap.put(SysUtil.SSO_MODULE, request.getHeader(SysUtil.SSO_MODULE));
                }
            }catch(Exception e){
                
            }
           
            // Map<String, String[]> paramMap = req.getParameterMap();
            String paramStr = mapper.writeValueAsString(paramMap);
            HttpServletRequest request = (HttpServletRequest) req;
            String url = request.getRequestURI();
            long handleTime = endTime - startTime;
            String ip = SysUtil.getIP(request);
            PmPersonnel user = SysUtil.getUser(request);
            String userAgent = SysUtil.getUserAgent(request);
            String sessionId = null;
            if(request.getSession(false)!=null){
                sessionId = request.getSession(false).getId();
            }
            SysTools.saveLogToFile(user, url, ip, paramStr, handleTime, userAgent, respStr, resp.getContentType(),sessionId);
        }
        else
        {
            try
            {
                chain.doFilter(req, resp);
            }
            catch (Exception e)
            {
                log.error("chain error:", e);
            }
        }

    }

    @Override
    public void destroy()
    {
    }
}
