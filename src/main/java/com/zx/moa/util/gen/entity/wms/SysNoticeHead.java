package com.zx.moa.util.gen.entity.wms;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zx.moa.util.mybatis.BaseEntity;



/*
 * @version 2.0
 */

public class SysNoticeHead implements BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer notice_id;
	
	private String notice_title;
	
	private java.sql.Timestamp notice_date;
	
	private String notice_date_str;
	
	private String notice_url;
	
	private String notice_url_type;
	
	private String notice_type;
	
	private String notice_app_type;
	
	private Integer notice_publisher;
	
	private String enable_flag;
	
	private boolean isExcludePKFlag;
	
	
	/**
	* default val cols name array
	*/	
	private static String[] defaultValColArr = {
	};
	
	/**
	* pk cols name array
	*/	
	private static String[] pkColArr = {
	  	"notice_id"
	};
	
	private static String[] columnNameArr = {
		"notice_id",
		"notice_title",
		"notice_date",
		"notice_date_str",
		"notice_url",
		"notice_url_type",
		"notice_type",
		"notice_app_type",
		"notice_publisher",
		"enable_flag",
		"isExcludePKFlag"
	};
  
	public Integer getNotice_id () {
		return notice_id;
	}
	
	public void setNotice_id (Integer obj) {
		notice_id = obj;
	}
	
	public String getNotice_title () {
		return notice_title;
	}
	
	public void setNotice_title (String obj) {
		notice_title = obj;
	}
	
	public java.sql.Timestamp getNotice_date () {
		return notice_date;
	}
	
	public void setNotice_date (java.sql.Timestamp obj) {
		notice_date = obj;
	}
	
	public String getNotice_date_str () {
		return notice_date_str;
	}
	
	public void setNotice_date_str (String obj) {
		notice_date_str = obj;
	}
	
	public String getNotice_url () {
		return notice_url;
	}
	
	public void setNotice_url (String obj) {
		notice_url = obj;
	}
	
	public String getNotice_url_type () {
		return notice_url_type;
	}
	
	public void setNotice_url_type (String obj) {
		notice_url_type = obj;
	}
	
	public String getNotice_type () {
		return notice_type;
	}
	
	public void setNotice_type (String obj) {
		notice_type = obj;
	}
	
	public String getNotice_app_type () {
		return notice_app_type;
	}
	
	public void setNotice_app_type (String obj) {
		notice_app_type = obj;
	}
	
	public Integer getNotice_publisher () {
		return notice_publisher;
	}
	
	public void setNotice_publisher (Integer obj) {
		notice_publisher = obj;
	}
	
	public String getEnable_flag () {
		return enable_flag;
	}
	
	public void setEnable_flag (String obj) {
		enable_flag = obj;
	}
	
	public boolean getgetIsExcludePKFlag () {
		return isExcludePKFlag;
	}
	
	public void setgetIsExcludePKFlag (boolean obj) {
		isExcludePKFlag = obj;
	}
	
	
	/**
	* put all columns into a map
	*/
	public void putInMap(Map<String, Object> paramMap) {
		paramMap.put("notice_id", this.notice_id);
		paramMap.put("notice_title", this.notice_title);
		paramMap.put("notice_date", this.notice_date);
		paramMap.put("notice_date_str", this.notice_date_str);
		paramMap.put("notice_url", this.notice_url);
		paramMap.put("notice_url_type", this.notice_url_type);
		paramMap.put("notice_type", this.notice_type);
		paramMap.put("notice_app_type", this.notice_app_type);
		paramMap.put("notice_publisher", this.notice_publisher);
		paramMap.put("enable_flag", this.enable_flag);
		paramMap.put("isExcludePKFlag", this.isExcludePKFlag);
	}
	
	/**
	* return the columns map
	*/
	public Map<String, Object> getInfoMap() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.putInMap(paramMap);
		return paramMap;
	}
	
	/**
	* remove default value and pk if it is null
	*/
	private Map<String, Object> dealWithMap(Map<String, Object> paramMap) {
		Set<String> set = new HashSet<String>();
		for (String colName : defaultValColArr) {
			set.add(colName);
		}
		for (String colName : pkColArr) {
			set.add(colName);
		}
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String colName = iterator.next();
			if(paramMap.get(colName) == null) {
				paramMap.remove(colName);
			}
		}
		return paramMap;
	}
}