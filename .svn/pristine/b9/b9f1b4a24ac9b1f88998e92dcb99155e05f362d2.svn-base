package com.zx.moa.ioa.systemmanage.persist;

import java.util.Map;

import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * APP自动更新数据层
 * @author MATF
 *
 */
@MyBatisRepository
public interface AppVersionDao {

	/**
	 * 获取APP最新版本
	 * @param map：app_name APP名称、app_system：系统名称（1 android、2 IOS）
	 * @return
	 */
	public Map<String, Object> getAppVersionInfo(Map<String, Object> map);
}
