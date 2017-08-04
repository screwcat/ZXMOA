package com.zx.moa.ioa.systemmanage.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.systemmanage.persist.LoginManageDao;
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

@Service("loginService")
public class LoginManageServiceImpl implements ILoginManageService
{

    @Autowired
    private LoginManageDao loginDao;

    @SuppressWarnings("unchecked")
    @Override
    public ResultBean<Map<String, Object>> login(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password))
        {
            String app_name = request.getHeader(SysUtil.SSO_MODULE);
            String url = HttpClientUtil.getSysUrl("nozzleUrl");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "LG_OUT_001"));
            if (StringUtil.isNotBlank(app_name))
            {
                nvps.add(new BasicNameValuePair("sys_num", app_name));
            }
            else
            {
                nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
            }
            nvps.add(new BasicNameValuePair("user_id", "0"));
            nvps.add(new BasicNameValuePair("userCode", username));
            nvps.add(new BasicNameValuePair("userPasswd", password));
            try
            {
                result = HttpClientUtil.post(url, nvps, Map.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                // result.put("flag", false);
                // result.put("message", "请求错误!");
                return ResultHelper.getNetworkError(result);
            }
            String hand_word = null;
            Integer enable_flag = null;
            Boolean flag = (Boolean) result.get("flag");
            if (flag)
            {
                if (result.get("hand_word") != null)
                    hand_word = result.get("hand_word").toString();
                if (result.get("enable_flag") != null)
                    enable_flag = Integer.valueOf(result.get("enable_flag").toString());

                String encryptionId = (String) result.get(GlobalVal.PERSONNEL_ENCRYPTIONID);
                PmPersonnel personnel = loginDao.getPersonnelByEncryptionId(encryptionId);
                if (personnel != null && encryptionId.equals(personnel.getPersonnel_encryptionid()))
                {
                    HttpSession session = request.getSession();
                    session.setAttribute(GlobalVal.USER_SESSION, personnel);
                    Map<String, Object> parentDept = loginDao.getParentDept(personnel.getPersonnel_deptid());
                    if (parentDept != null && parentDept.get("dept_level") != null
                        && (Integer) parentDept.get("dept_level") != 0)
                    {
                        personnel.setParentDept((String) parentDept.get("dept_name"));
                    }
                    // result.put("flag", true);
                    // result.put("message","登录成功!");
                    result.clear();
                    if (StringUtil.isNotBlank(app_name))
                    {
                        session.setAttribute("app_name", app_name);

                        String alias = SysUtil.makeUUID();
                        nvps.clear();
                        nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_012"));
                        nvps.add(new BasicNameValuePair("sys_num", app_name));
                        nvps.add(new BasicNameValuePair("app_name", app_name));
                        nvps.add(new BasicNameValuePair("username", personnel.getPersonnel_shortcode()));
                        nvps.add(new BasicNameValuePair("alias", alias));
                        try
                        {
                            result = HttpClientUtil.post(url, nvps, Map.class);
                            // result =
                            // HttpClientUtil.post("http://localhost:7080/IOA/moa/appUser.do",
                            // nvps, Map.class);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            return ResultHelper.getNetworkError(result);
                        }
                        flag = (Boolean) result.get("flag");
                        if (!flag)
                        {
                            return ResultHelper.getError(null);
                        }
                        result.clear();
                      
                        ZXSessionManage.getInstance().register(personnel.getPersonnel_shortcode(), app_name, session);
                        result.put("alias", alias);
                    }
                    if (SysUtil.isSuperUser(personnel.getPersonnel_shortcode()))
                    {
                        result.put("superuser", "1");
                    }
                    else
                    {
                        result.put("superuser", "0");
                    }
                    result.put("personnel_id", personnel.getPersonnel_id());
                    result.put("personnel_sex", personnel.getPersonnel_sex());
                    result.put("personnel_deptname", personnel.getPersonnel_deptname());
                    result.put("personnel_postname", personnel.getPersonnel_postname());
                    result.put("parent_deptname", personnel.getParentDept());
                    result.put("hand_word", hand_word);
                    result.put("enable_flag", enable_flag);
                    return ResultHelper.getSuccess(result, "登录成功!");
                }
                else
                {
                    // result.put("flag", false);
                    // result.put("error", "员工不存在");
                    return new ResultBean<Map<String, Object>>(ResultHelper.RET_CODE_NOTFOUND, "此帐号不存在", result);
                }
            }
            else
            {
                String error_code = (String) result.get("err_code");
                String message = (String) result.get("message");
                if (StringUtils.isNotBlank(error_code))
                {
                    if ("100001".equals(error_code))
                    {
                        message = "请在ESM修改初始密码后登录";
                    }
                    else if ("100002".equals(error_code))
                    {
                        message = "密码已过期，请修改密码";
                    }
                }
                return new ResultBean<Map<String, Object>>(ResultHelper.RET_PWD_ERROR, message, result);
            }
        }
        else
        {
            if (StringUtils.isBlank(username))
            {
                return new ResultBean<Map<String, Object>>(ResultHelper.RET_CODE_BLANK, "登录帐号不能为空", result);
            }
            else
            {
                return new ResultBean<Map<String, Object>>(ResultHelper.RET_PWD_BLANK, "登录密码不能为空", result);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultBean<Map<String, Object>> autoLogin(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String alias = request.getParameter("alias");
        if (StringUtil.isBlank(username))
        {
            return new ResultBean<Map<String, Object>>(ResultHelper.RET_CODE_BLANK, "登录帐号不能为空", result);
        }
        if (StringUtil.isBlank(alias))
        {
            return new ResultBean<Map<String, Object>>(ResultHelper.RET_PWD_BLANK, "自动登录密码不能为空", result);
        }
        String app_name = request.getHeader(SysUtil.SSO_MODULE);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", "LG_OUT_003"));
        if (StringUtil.isNotBlank(app_name))
        {
            nvps.add(new BasicNameValuePair("sys_num", app_name));
        }
        else
        {
            nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        }
        nvps.add(new BasicNameValuePair("app_name", app_name));
        nvps.add(new BasicNameValuePair("username", username));
        nvps.add(new BasicNameValuePair("alias", alias));
        try
        {
            String url = HttpClientUtil.getSysUrl("nozzleUrl");
            result = HttpClientUtil.post(url, nvps, Map.class);
            // result =
            // HttpClientUtil.post("http://localhost:7080/IOA/moa/autoLogin.do",
            // nvps, Map.class);
            Boolean flag = (Boolean) result.get("flag");
            if (flag)
            {
                PmPersonnel personnel = loginDao.findPersonnelByCode(username);
                if (personnel == null)
                {
                    new ResultBean<Map<String, Object>>(ResultHelper.RET_CODE_NOTFOUND, "此帐号不存在", null);
                }
                Map<String, Object> parentDept = loginDao.getParentDept(personnel.getPersonnel_deptid());
                if (parentDept != null && parentDept.get("dept_level") != null
                    && (Integer) parentDept.get("dept_level") != 0)
                {
                    personnel.setParentDept((String) parentDept.get("dept_name"));
                }
                request.getSession().setAttribute(GlobalVal.USER_SESSION, personnel);
                if (StringUtil.isNotBlank(app_name))
                {
                    request.getSession().setAttribute("app_name", app_name);
                    ZXSessionManage.getInstance().register(personnel.getPersonnel_shortcode(), app_name,
                                                           request.getSession());
                }
                return ResultHelper.getSuccess(null);
            }
            else
            {
                if (result.get("ret_code") != null && result.get("ret_code").equals(ResultHelper.RET_OTHER_LOGIN))
                {
                    return ResultHelper.getOtherLoginError(result);
                }
                else
                {
                    return ResultHelper.getNetworkError(result);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ResultHelper.getNetworkError(result);
        }
    }
}
