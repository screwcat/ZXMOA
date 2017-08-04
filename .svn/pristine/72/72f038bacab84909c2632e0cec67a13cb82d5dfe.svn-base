package com.zx.moa.ioa.documentsmanage.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zx.moa.util.bean.ResultBean;

/**
 * 单据业务接口
 * @author 李晓龙
 *
 */
public interface IDocumentsService {
	
	public Map<String, Object> getDocInfoById(HttpServletRequest request);
	
	public ResultBean<Map<String, Object>>  approvalDocuments(HttpServletRequest request,String content,Integer order_id, Integer approve_state);

	
    // 读取待审批单据详情（理财离职申请）
	public Map<String, Object> getLeaveOrderInfo(HttpServletRequest request);

	
	public ResultBean<Map<String, Object>>  approvalLeaveDocuments(HttpServletRequest request,String content,Integer order_id, Integer approve_state,String quit_agreeTime,Integer quitOrPart);

	public Map<String, Object> getDocInfoByIdV115(HttpServletRequest request);
	
    // 读取待审批单据详情（理财离职申请）
    public Map<String, Object> getLeaveOrderInfoV115(HttpServletRequest request);

    /**
     * @Title: getDocInfoByIdV117
     * @Description: 待我审批和我的单据流程显示待审批节点和签收节点
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 下午2:26:33
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    public Map<String, Object> getDocInfoByIdV117(HttpServletRequest request);

    /**
     * @Title: getLeaveOrderInfoV117
     * @Description: 读取待审批单据详情（理财离职申请）
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 下午3:32:43
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    public Map<String, Object> getLeaveOrderInfoV117(HttpServletRequest request);
}
