package com.zx.moa.ioa.util;

/**
 * 推送常量类
 * @author MATF
 *
 */
public interface PushConstant {
	
	/**
	 * 是否推送消息
	 */
	final boolean ISPUSH = true;
	
	/**
	 * 是否发送短息
	 */
	final boolean ISSENDSMS = true;
	
	final String TASK_APP_NAME = "MOA";
	
    final long TASK_UNFINISHED_REMIND_ERROR = 60 * 1000;
	
	/**
	 * 任务消息模版地址
	 */
	public final static String[] FREEMAKERTEMPLATE = new String[] {
 "ioaOrderManage.ftl", "ioaTaskManage.ftl", "ioaAttendanceManage.ftl" };

	/**
	 * 审批人提醒：单据待审批
	 */
	final String ORDER_APPROVE = "10101";
	/**
	 * 申请人提醒：单据审批通过
	 */
	final String ORDER_AGREED  = "10201";
	/**
	 * 申请人提醒：单据审批驳回
	 */
	final String ORDER_DISAGREE = "10202";
	
	/**
	 * 发布人提醒：任务20点未进行反馈
	 */
	final String TASK_PUBLISH_UNFEEDBACK_20 = "20101";
	final String SMS_PUBLISH_UNFEEDBACK_20 = "1639";
	/**
	 * 发布人提醒：任务消息
	 */
	final String TASK_PUBLISH_MESSAGE = "20102";
	/**
	 * 发布人提醒：任务待验收
	 */
	final String TASK_PUBLISH_WAIT_CONFIRM = "20103";
	/**
	 * 发布人提醒：任务协同
	 */
	final String TASK_PUBLISH_SYNERGY = "20104";
	/**
	 * 发布人提醒：任务转移
	 */
	final String TASK_PUBLISH_TRANSFER = "20105";
	/**
	 * 发布人提醒：任务申请延期
	 */
	final String TASK_PUBLISH_APPLY_DELAY = "20106";
	
	/**
	 * 反馈人提醒：指派新任务
	 */
	final String TASK_FEEDBACK_NEW = "20201";


	/**
	 * 反馈人提醒：协同任务
	 */
	final String TASK_FEEDBACK_SYNERGY = "20202";
	/**
	 * 反馈人提醒：转移任务
	 */
	final String TASK_FEEDBACK_TRANSFER = "20203";
	/**
	 * 反馈人提醒：17点未反馈
	 */
	final String TASK_FEEDBACK_UNFEEDBACK_17 = "20204";
	final String SMS_FEEDBACK_UNFEEDBACK_17 = "1638";
	/**
	 * 反馈人提醒：20点未反馈
	 */
	final String TASK_FEEDBACK_UNFEEDBACK_20 = "20205";
	final String SMS_FEEDBACK_UNFEEDBACK_20 = "1638";
	/**
	 * 反馈人提醒：任务到截止时间
	 */
	final String TASK_FEEDBACK_END = "20206";
	/**
	 * 反馈人提醒：任务消息
	 */
	final String TASK_FEEDBACK_MESSAGE = "20207";
	/**
	 * 反馈人提醒：任务验收通过
	 */
	final String TASK_FEEDBACK_PASS = "20208";
	/**
	 * 反馈人提醒：任务重办
	 */
	final String TASK_FEEDBACK_REDO = "20209";
	/**
	 * 反馈人提醒：任务截止时间修改
	 */
	final String TASK_FEEDBACK_DELAY = "20210";
	
	/**
	 * 反馈人提醒：任务提前结束
	 */
	final String TASK_ADVANCE = "20211";
	/**
	 * 反馈人提醒：任务撤销
	 * 
	 */
	final String TASK_REVOKE = "20212";
	/**
	 * 接受反馈人提醒：指派新任务
	 */
	final String TASK_accept_feedback_NEW = "20213";
}