package com.zx.moa.ioa.systemmanage.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.systemmanage.service.IHandWordService;
import com.zx.moa.ioa.systemmanage.vo.HandWordVO;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.SysUtil;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

@Controller
public class HankWordManageController
{

    @Autowired
    private IHandWordService handWordService;

    @RequestMapping(value = "ioa/handWord.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> handWordOpenOrClosed(HttpServletRequest request, HandWordVO handwordVO)
    {
        if (handwordVO.getEnable_flag() == null)
        {
            return ResultHelper.getError("请输入正确参数", null);
        }

        HttpSession session = request.getSession();
        PmPersonnel pm = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> resmap = new HashMap<String, Object>();
        String app_name = request.getHeader(SysUtil.SSO_MODULE) == null ? PushConstant.TASK_APP_NAME
                                                                       : request.getHeader(SysUtil.SSO_MODULE);
        resmap.put("app_name", app_name);
        resmap.put("hand_word", handwordVO.getHand_word());
        resmap.put("enable_flag", handwordVO.getEnable_flag());
        resmap.put("user_code", pm.getPersonnel_shortcode());

        Map<String, Object> result = handWordService.handWordOpenOrClosed(request, resmap);
        if (result == null)
        {
            return ResultHelper.getError(result);
        }
        else
        {
            Boolean flag = (Boolean)result.get("flag");
            if(flag){
                return ResultHelper.getSuccess(result);
            }else{
                return ResultHelper.getError(result);
            }
        }
    }

}
