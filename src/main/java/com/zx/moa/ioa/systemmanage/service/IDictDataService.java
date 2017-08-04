package com.zx.moa.ioa.systemmanage.service;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.bean.ResultBean;

/**
 * IOA系统数据字典控制层接口
 * @author MATF
 *
 */
public interface IDictDataService {
	
	/**
	 * 根据字典编号（多个用“,”分隔）获取数据字典信息
	 * @param dictData
	 * @return
	 */
	public ResultBean<List<Map<String, Object>>> getDictDatas(String dictData);

}
