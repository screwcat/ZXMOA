package com.zx.moa.util.bean;

/**
 * 列表返回实体类
 * @author MATF
 *
 * @param <T>
 */
public class ResultList<T> extends ResultBean<T>{

	private static final long serialVersionUID = 64824797483087670L;
	
	private Integer count;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public ResultList(){}
	public ResultList(ResultBean<T> bean,Integer count){
		this.count = count;
	}
	public ResultList(String ret_code, String ret_msg, T ret_data,Integer count) {
		super(ret_code,ret_msg,ret_data);
		this.count = count;
	}
}
