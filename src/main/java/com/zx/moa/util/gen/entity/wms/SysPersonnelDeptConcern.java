package com.zx.moa.util.gen.entity.wms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.math.BigDecimal;

import com.zx.moa.util.mybatis.BaseEntity;



/*
 * @version 2.0
 */

public class SysPersonnelDeptConcern implements BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer personnel_dept_concern_id;
	
	private Integer personnel_id;
	
	private Integer dept_id;
	
	private String dept_type;
	
	private Integer create_userid;
	
	private java.sql.Timestamp create_date;
	
	private String create_date_str;
	
	private String deptId;
	
	private Integer permissions_id;
	
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
	  	"personnel_dept_concern_id"
	};
	
	private static String[] columnNameArr = {
		"personnel_dept_concern_id",
		"personnel_id",
		"dept_id",
		"dept_type",
		"create_userid",
		"create_date",
		"create_date_str",
		"deptId",
		"permissions_id",
		"isExcludePKFlag"
	};
  
	public Integer getPersonnel_dept_concern_id () {
		return personnel_dept_concern_id;
	}
	
	public void setPersonnel_dept_concern_id (Integer obj) {
		personnel_dept_concern_id = obj;
	}
	
	public Integer getPersonnel_id () {
		return personnel_id;
	}
	
	public void setPersonnel_id (Integer obj) {
		personnel_id = obj;
	}
	
	public Integer getDept_id () {
		return dept_id;
	}
	
	public void setDept_id (Integer obj) {
		dept_id = obj;
	}
	
	public String getDept_type () {
		return dept_type;
	}
	
	public void setDept_type (String obj) {
		dept_type = obj;
	}
	
	public Integer getCreate_userid () {
		return create_userid;
	}
	
	public void setCreate_userid (Integer obj) {
		create_userid = obj;
	}
	
	public java.sql.Timestamp getCreate_date () {
		return create_date;
	}
	
	public void setCreate_date (java.sql.Timestamp obj) {
		create_date = obj;
	}
	
	public String getCreate_date_str () {
		return create_date_str;
	}
	
	public void setCreate_date_str (String obj) {
		create_date_str = obj;
	}
	
	public Integer getPermissions_id() {
		return permissions_id;
	}

	public void setPermissions_id(Integer permissions_id) {
		this.permissions_id = permissions_id;
	}

	public boolean getgetIsExcludePKFlag () {
		return isExcludePKFlag;
	}
	
	public void setgetIsExcludePKFlag (boolean obj) {
		isExcludePKFlag = obj;
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	* put all columns into a map
	*/
	public void putInMap(Map<String, Object> paramMap) {
		paramMap.put("personnel_dept_concern_id", this.personnel_dept_concern_id);
		paramMap.put("personnel_id", this.personnel_id);
		paramMap.put("dept_id", this.dept_id);
		paramMap.put("dept_type", this.dept_type);
		paramMap.put("create_userid", this.create_userid);
		paramMap.put("create_date", this.create_date);
		paramMap.put("create_date_str", this.create_date_str);
		paramMap.put("deptId", this.deptId);
		paramMap.put("permissions_id", this.permissions_id);
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