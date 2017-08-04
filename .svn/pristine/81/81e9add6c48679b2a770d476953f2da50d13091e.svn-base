package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.WmsSysDictData;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;

@MyBatisRepository
public interface WmsSysDictDataDao extends BaseDao<WmsSysDictData> {
    /**
     * 
     * search:根据传人的条件动态生成sql语句，如需分页需要在sql中加入offset、pagesize变量 <br/>   
     * @author Administrator   
     * @param parameters
     * @return   
     * @since JDK 1.6
     */
    List<Map<String,Object>> searchforApp(Map<String, Object> parameters);

	List<WmsSysDictData> getDictDataByDictId();
	 /**
	  * 根据主键 只返回value_code
	  * @param wms_sys_dict_id
	  * @return List<String>
	  * @author baisong
	  * @date 2016-10-11
	  */
    public List<String> getCodeByDictId(Integer wms_sys_dict_id);

    /**
     * @Title: searchSpecial
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param paramMap
     * @return 
     * @author: 张明建
     * @time:2017年3月21日 上午11:06:32
     * history:
     * 1、2017年3月21日 张明建 创建方法
     */
    List<Map<String, Object>> searchSpecial(Map<String, Object> paramMap);

    /**
     * @Title: getAllForWmsSysDictId
     * @Description: TODO(查询字典表)
     * @param i
     * @return 
     * @author: 张明建
     * @time:2017年3月22日 上午9:22:19
     * history:
     * 1、2017年3月22日 张明建 创建方法
     */
    List<Map<String, Object>> getAllForWmsSysDictId(Integer i);

    /**
     * @Title: getCityInfo
     * @Description: 获区域
     * @param pMap
     * @return 
     * @author: baisong
     * @time:2017年4月7日 下午2:10:25
     * history:
     * 1、2017年4月7日 baisong 创建方法
     */
    Map<String, Object> getCityInfo(Map<String, Object> pMap);



}