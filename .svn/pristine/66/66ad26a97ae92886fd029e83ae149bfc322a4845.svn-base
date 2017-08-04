package com.zx.moa.ioa.documentsmanage.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.documentsmanage.service.IDocumentsService;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: DocumentsController
 * 模块名称：
 * @Description: 内容摘要：待审批单据和我的单据显示全部审批节点和签收节点
 * @author zhaowei
 * @date 2016年12月9日
 * @version V1.1.7
 * history:
 * 1、2016年12月9日 zhaowei 创建文件
 */
@Controller
public class DocumentsController
{

    @Autowired
    private IDocumentsService DocumentsService;

    /**
     * @Title: getDocById
     * @Description: 待我审批和我的单据流程显示待审批节点和签收节点
     * @param v
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 下午2:25:28
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    @RequestMapping(value = "/ioa/getOrderInfo.do", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getDocById(@RequestParam(required = false)
    String v, HttpServletRequest request)
    {

        Map<String, Object> result = null;
        try
        {
            if (v == null)
            {
                result = DocumentsService.getDocInfoById(request);
            }
            // 此功能支持最低版本,在此下边加else if
            else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.7") >= 0)
            {
                result = DocumentsService.getDocInfoByIdV117(request);
            }
            else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.5") >= 0)
            {
                result = DocumentsService.getDocInfoByIdV115(request);
            }
            else
            {
                result = DocumentsService.getDocInfoById(request);
            }

            return ResultHelper.getSuccess(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultHelper.getError(e.getMessage(), result);
        }

    }

    @RequestMapping(value = "/ioa/approveOrder.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> approvalDocuments(HttpServletRequest request)
    {
        String content = request.getParameter("content");
        Integer order_id = Integer.parseInt(request.getParameter("order_relation_id"));
        Integer approve_state = Integer.parseInt(request.getParameter("approve_state"));
        return DocumentsService.approvalDocuments(request, content, order_id, approve_state);

    }

    /**
     * 审批单据（理财离职）
     */
    @RequestMapping(value = "/ioa/approveLeaveOrder.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> approvalLeaveDocuments(HttpServletRequest request)
    {
        // 判断session是否过期
        if (request.getSession(false) == null)
        {
            ResultBean<Map<String, Object>> resultbean = new ResultBean<>();
            resultbean.setRet_code(ResultHelper.RET_SESSION_TIMEOUT);
            resultbean.setRet_msg("登录已失效，请重新登录！");
            return resultbean;
        }

        String content = request.getParameter("content");
        Integer order_id = Integer.parseInt(request.getParameter("order_relation_id"));
        Integer approve_state = Integer.parseInt(request.getParameter("approve_state"));
        String quit_agreeTime = request.getParameter("quit_agreeTime");// 同意离职日期
        Integer quitOrPart = Integer.parseInt(request.getParameter("quitOrPart"));// 申请状态,是否兼职(0兼职1离职)

        return DocumentsService.approvalLeaveDocuments(request, content, order_id, approve_state, quit_agreeTime,
                                                       quitOrPart);

    }

    @RequestMapping("/ioa/getFile.do")
    public void getImg(HttpServletRequest request, HttpServletResponse response)
    {
        String url = request.getParameter("url");
        url = HttpClientUtil.getSysUrl("server.ioaFileUrl") + "/" + url;
        HttpClientUtil.loadImg(url, response);
    }

    /*
     * 读取待审批单据详情（理财离职申请）
     */
    @RequestMapping(value = "ioa/getLeaveOrderInfo.do", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResultBean<Map<String, Object>> getLeaveOrderInfo(@RequestParam(required = false)
    String v, HttpServletRequest request)
    {

        Map<String, Object> result = null;
        try
        {
            if (v == null)
            {
                result = DocumentsService.getLeaveOrderInfo(request);
            }
            // 此功能支持最低版本,在此下边加else if

            else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.7") >= 0)
            {
                result = DocumentsService.getLeaveOrderInfoV117(request);
            }
            else if (com.zx.moa.ioa.util.StringUtil.compareVersion(v, "1.1.5") >= 0)
            {
                result = DocumentsService.getLeaveOrderInfoV115(request);
            }
            else
            {
                result = DocumentsService.getLeaveOrderInfo(request);
            }

            return ResultHelper.getSuccess(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultHelper.getError(e.getMessage(), result);
        }
    }
}
