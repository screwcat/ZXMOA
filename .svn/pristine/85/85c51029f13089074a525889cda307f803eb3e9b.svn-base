package com.zx.moa.util.bean;

import java.io.Serializable;

/**
 * 返回结果类
 * @author MATF
 *
 * @param <T> 返回数据类型
 */
public class ResultBean<T> implements Serializable{
	
	private static final long serialVersionUID = 64824797483087670L;

	/**
	 * 返回编码
	 */
	private String ret_code;
	
	/**
	 * 返回信息
	 */
	private String ret_msg;
	
	/**
	 * 返回数据
	 */
	private T ret_data;
	
	public ResultBean(){
		super();
	}

	public ResultBean(String ret_code, String ret_msg) {
		super();
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
	}

	public ResultBean(String ret_code, String ret_msg, T ret_data) {
		super();
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
		this.ret_data = ret_data;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public T getRet_data() {
		return ret_data;
	}

	public void setRet_data(T ret_data) {
		this.ret_data = ret_data;
	}
	
	
}
