package com.zx.moa.wms.loan.vo;

import com.zx.platform.syscontext.vo.GridParamBean;

/*
 * @version 2.0
 */

public class TransmitValuesThreeVO extends GridParamBean
{
	private String many_column_like;
	private String sortname;
	private Integer user_id;
	private Integer is_need_paging;
	public String getMany_column_like() {
		return many_column_like;
	}
	public String getSortname() {
		return sortname;
	}
	public Integer getUser_id() {
		return user_id;
	}

	public Integer getIs_need_paging() {
		return is_need_paging;
	}
	public void setMany_column_like(String many_column_like) {
		this.many_column_like = many_column_like;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public void setIs_need_paging(Integer is_need_paging) {
		this.is_need_paging = is_need_paging;
	}
	
   
	
}