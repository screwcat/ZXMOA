package com.zx.moa.crm.dictionarymanage.persist;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zx.moa.crm.dictionarymanage.vo.SysDictData;
import com.zx.moa.util.mybatis.MyBatisRepository;
@MyBatisRepository
public interface DictionaryDao {
	/*
	 * 根据字典编码查询数据字典数据
	 */
	public List<SysDictData> selectDictByCode(Map<String,Object> map);
}
