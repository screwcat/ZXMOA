package com.zx.moa.ioa.documentsmanage.vo;

public class DocumentsConstant {
	/**
	 * 请假单据前缀
	 */
	public static final String PREFIX_VACATE = "QJ";
	/**
	 * 出差单据前缀
	 */
	public static final String PREFIX_BusinessTravel = "CC";
	
	/**
	 * 补签单据前缀
	 */
	public static final String PREFIX_FILLCHECK = "BQ";
	
	/**
	 * 调休单据前缀
	 */
	public static final String PREFIX_DAYSOFF = "TX";
	
	/**
	 * 酬勤单据前缀
	 */
	public static final String PREFIX_PAYOFF = "CQ";
	
	/**
	 * 自定义单据前缀
	 */
	public static final String PREFIX_DF = "DF";
	
	/**
	 * 查询请假设置信息常量
	 */
	public static final String PREFIX_SET_QJ = "QJS";
	
	/**
	 * 查询调休设置信息常量
	 */
	public static final String PREFIX_SET_TX = "TXS";
	
	/**
	 * 查询补签设置信息常量
	 */
	public static final String PREFIX_SET_BQ = "BQS";

	/**
	 * 月份英文缩写
	 */
	public static final String MONTHS[] = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	/**
	 * 考勤申请申请类查询pid（补签、调休、酬勤、请假）
	 */
	public static final Integer APPLY_TYPE_CUSTOMFLOW = 26;
	
	/**
	 * 考勤申请请假类查询pid（请假类型）
	 */
	public static final Integer APPLY_TYPE_VACATE = 30;
	
	/**
	 * 补签插入考勤机器号码
	 */
	public static final String FILLCHECK = "FILLCHECK";
	
	/**
	 * 单据通过
	 */
	public static int TYPE_PASS = 1;
	
	/**
	 * 单据撤销
	 */
	public static int TYPE_UNDO = 2;
	
	int TIME_CHECKINOUT_USERID = 0;
}
