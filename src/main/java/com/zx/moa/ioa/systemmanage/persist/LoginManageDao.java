package com.zx.moa.ioa.systemmanage.persist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zx.moa.ioa.systemmanage.vo.PmPersonnelVO;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.util.mybatis.MyBatisRepository;

@MyBatisRepository
public interface LoginManageDao {

	public PmPersonnel findPersonnelByCode(@Param("code")String code);

	public PmPersonnel getPersonnelByEncryptionId(@Param("encryptionId")String encryptionId);
	
	public Map<String, Object> getParentDept(@Param("dept_id")Integer dept_id);
	
	public Integer autoLogin(Map<String, Object> map);
	
	public List<Map<String, Object>> getAppUserRecord();
}
