package com.zx.moa.util.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zx.moa.ioa.util.ZXSessionManage;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.platform.syscontext.util.StringUtil;


/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: LoginFilter
 * 模块名称：登录
 * @Description: 内容摘要：登录过滤
 * @author sunlq
 * @date 2016年12月2日
 * @version V1.0
 * history:
 * 1、2016年12月2日 Administrator 创建文件
 */
public class LoginFilter implements Filter
{
    private static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
    }

    /**
     * @Title: doFilter
     * @Description: 登录过滤
     * @param req
     * @param resp
     * @param chain
     * @throws IOException
     * @throws ServletException 
     * @author: sunlq
     * @time:2016年12月2日 下午2:58:06
     * history:
     * 1、2016年12月2日 sunlq 创建方法
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
                                                                                     ServletException
    {
        // 判断是否登录，访问系统.do必须登录后才能访问
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String url = request.getRequestURI();
        log.debug("REST CALL>>" + url);

        HttpSession session = request.getSession(false);

        // 如果是登录则不检查 login.do,也不检查修改默认密码changedefaultpasswd.do,
        // 也不检查验证码loginclearkaptcha.do
        // authorizedStub.do的时候会根据cookie设置情况处理自动登录问题，因此此处也跳过
        // 然后判断session，没有session则出错
        // 存在session则判断权限按钮
        Map<String, String> map = new HashMap<String, String>();
        // 不用登录，即可访问地址配置
        if (url.endsWith("login.do") || url.contains("getImg.do") || url.contains("getFile.do")
            || url.endsWith("autoLogin.do") || url.contains("push/") || url.contains("pushManageForWMS")
            || url.contains("ioa/getAppVersion.do") || url.startsWith("/MOA/nozzle/"))
        {
            chain.doFilter(req, resp);
        }
        // 需登录，session未过期
        else if (session != null && session.getAttribute(GlobalVal.USER_SESSION) != null)
        {
            if ("-1".equals(request.getParameter("_filterParms")))
            {
                // return empty array for ligerui grid
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("Rows", new ArrayList<Map<String, Object>>());
                response.getWriter().print(new Gson().toJson(tempMap));
            }
            else
            {
                // 手持端登录过，但被其他设备登录了
                if (ZXSessionManage.isCheck(request) && ZXSessionManage.isOut(session))
                {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = null;
                    try
                    {
                        out = response.getWriter();
                        out.append(JSON.toJSONString(ResultHelper.getOtherLoginError(null)));
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        if (out != null)
                        {
                            out.close();
                        }
                    }
                }
                // 登录状态，正常调用
                else
                {
                    chain.doFilter(req, resp);
                }
                // UserBean user = (UserBean)
                // session.getAttribute(GlobalVal.USER_SESSION);
                // 如果全部的功能点中没有该功能点那么直接跳过
                /*if(!hasTheFunction(getUrlStr(url))){
                	chain.doFilter(req, resp);
                }else{
                	if (user.hasFuncName(getUrlStr(url))) {
                		chain.doFilter(req, resp);
                	} else {
                		map.put("error", "100091");
                		response.getWriter().print(new Gson().toJson(map)); 
                	}
                }*/
            }
        }
        // 需登录，session已过期
        else
        {
            // logout or session timeout or unauthority user
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try
            {
                out = response.getWriter();
                out.append(JSON.toJSONString(new ResultBean<String>(ResultHelper.RET_SESSION_TIMEOUT, "登录超时,请重新登录!")));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                if (out != null)
                {
                    out.close();
                }
            }

        }
    }

    @Override
    public void destroy()
    {
    }

    // 得到除了qasp外其他部分的url
    @SuppressWarnings("unused")
    private String getUrlStr(String url)
    {
        String[] arrUrl = url.split("/");
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (String string : arrUrl)
        {
            if (StringUtil.isNotBlank(string))
            {
                index++;
            }
            if (index > 1)
            {
                sb.append("/").append(string);
            }
        }
        return sb.toString();
    }
}
