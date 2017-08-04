package com.zx.moa.ioa.systemmanage.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.systemmanage.service.ILoginManageService;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.ioa.util.ZXSessionManage;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.SysUtil;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.util.StringUtil;

@Controller
public class LoginManageController
{

    @Autowired
    private ILoginManageService loginService;

    @RequestMapping("login.do")
    @ResponseBody
    public ResultBean<Map<String, Object>> login(HttpServletRequest request)
    {
        // com.zx.moa.ioa.util.PushManage.test();//测试推送
        return loginService.login(request);
    }

    @RequestMapping(value = "autoLogin.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> autoLogin(HttpServletRequest request)
    {
        return loginService.autoLogin(request);
    }

    @RequestMapping(value = "getUser.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getLoginUser(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        result.put("personnel_name", personnel.getPersonnel_name());
        result.put("personnel_shortcode", personnel.getPersonnel_shortcode());
        result.put("personnel_deptname", personnel.getPersonnel_deptname());
        if (StringUtil.isNotBlank(personnel.getParentDept()))
        {
            result.put("personnel_deptname", personnel.getParentDept() + "/" + personnel.getPersonnel_deptname());
        }
        String url = HttpClientUtil.getSysUrl("server.ioaFileUrl");
        String img_url=personnel.getHead_img_path();
        result.put("img_url", url + img_url);
        return ResultHelper.getSuccess(result);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("logout.do")
    @ResponseBody
    public ResultBean<Map<String, Object>> logout(HttpServletRequest request, HttpSession session)
    {
        String app_name = request.getHeader(SysUtil.SSO_MODULE) == null ? PushConstant.TASK_APP_NAME
                                                                       : request.getHeader(SysUtil.SSO_MODULE);
        Map<String, Object> result = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);

        if (request.getHeader(SysUtil.SSO_MODULE) != null)
        {
            try
            {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_012"));
                nvps.add(new BasicNameValuePair("sys_num", app_name));
                nvps.add(new BasicNameValuePair("app_name", app_name));
                nvps.add(new BasicNameValuePair("username", personnel.getPersonnel_shortcode()));
                nvps.add(new BasicNameValuePair("alias", ""));

                String url = HttpClientUtil.getSysUrl("nozzleUrl");
                result = HttpClientUtil.post(url, nvps, Map.class);
                Boolean flag = (Boolean) result.get("flag");
                if (flag)
                {
                    session.removeAttribute(GlobalVal.USER_SESSION);
                    ZXSessionManage.clearIsOut(session);
                    ZXSessionManage.getInstance().clearUser(personnel.getPersonnel_shortcode(), app_name);
                    session.invalidate();

                    result.put("ret_code", true);
                    result.put("message", "注销成功!");
                    return ResultHelper.getSuccess(result);
                }
                else
                {
                    return ResultHelper.getNetworkError(result);
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
            session.removeAttribute(GlobalVal.USER_SESSION);
            session.invalidate();

            result.put("ret_code", true);
            result.put("message", "注销成功!");
            return ResultHelper.getSuccess(result);
        }

    }

}
