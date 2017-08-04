package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.PmPersonnel;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;


@MyBatisRepository
public interface PmPersonnelDao extends BaseDao<PmPersonnel>
{

    List<Map<String, Object>> searchforlcjl(Map<String, Object> parameters);

    List<Map<String, Object>> searchforlczjl(Map<String, Object> parameters);

    List<Map<String, Object>> searchforlcfzjl(Map<String, Object> parameters);

    /**
     * 根据提供的短工号，查询相应是否存在部门
     */
    List<Map<String, Object>> searchforNodept(Map<String, Object> parameters);
    /**
     * 根据用户id查询用户职务信息===  职务编码=催缴主管的 CJZG
     */
    List<Map<String, Object>> getPostInfo(Integer personnel_id);
    
    List<Map<String, Object>> getListByEntity2(PmPersonnel queryInfo);
    /*
     * 获取部门上级信息
     */
    List<PmPersonnel> getByPost(Map<String, Object> parameters);

    /**
     * @Title: getPmPersonnelByIds 
     * @Description: 根据人员id集合查询人员
     * @param list
     * @return List<Map<String,Object>> 
     * @throws
     * @author lvtu 
     * @date 2015年9月17日 下午5:32:27
     */
	List<Map<String, Object>> getPmPersonnelByIds(List<Integer> list);
	/**
	 * 查询符合见习团队经理
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> searchforlcjxjl(Map<String, Object> parameters);
	 /**
     * 获取贷款申请菜单权限
     * @param dept_id
     * @return
     */
	List<Map<String, Object>> getApplyInfo(Integer dept_id);
	
	
	 /**
    * 获取贷款申请菜单权限--根据部门查询  1有权限 0无权限
    * @param dept_id
    * @return
    */
	int getApplyInfobyDept(Map<String, Object> parameters);

    /**
     * @Title: getJurisdictionInfo
     * @Description: (查询当前用户的菜单权限)
     * @param personnel_id
     * @return 
     * @author: baisong
     * @time:2017年2月22日 下午3:51:08
     * history:
     * 1、2017年2月22日baisong 创建方法
     */
    List<String> getJurisdictionInfo(Integer personnel_id);

    /**
     * @Title: getSpecialMenuInfosMoa
     * @Description: (查询菜单权限)
     * @param personnel_id
     * @return 
     * @author: baisong
     * @time:2017年3月20日 下午2:51:56
     * history:
     * 1、2017年3月20日 baisong 创建方法
     */
    List<Map<String, Object>> getSpecialMenuInfosMoa(Integer personnel_id);
    
    /**
     * 
     * @Title: getBizTeamAmountRanking
     * @Description: TODO(获取成员排名信息-按照金额排名)
     * @return 
     * @author: handongchun
     * @time:2017年3月21日 下午4:47:37
     * history:
     * 1、2017年3月21日 handongchun 创建方法
     */
    List<Map<String, Object>> getBizTeamAmountRanking(Map<String,Object> paramsMap);
    /**
     * 
     * @Title: getBizTeamNumberRanking
     * @Description: TODO(获取成员排名信息-按照件数排名)
     * @param paramsMap
     * @return 
     * @author: handongchun
     * @time:2017年3月21日 下午5:57:05
     * history:
     * 1、2017年3月21日 handongchun 创建方法
     */
    List<Map<String, Object>> getBizTeamNumberRanking(Map<String,Object> paramsMap);
    /**
     * 
     * @Title: calBillingNumber
     * @Description: TODO(获取成员排名信息-开单人数)
     * @param paramsMap
     * @return 
     * @author: handongchun
     * @time:2017年3月22日 上午9:17:13
     * history:
     * 1、2017年3月22日 handongchun 创建方法
     */
    Map<String, Object> calBillingNumber(Map<String,Object> paramsMap);

    /**
     * @Title: getBizDataAuthority
     * @Description: <!--getBizDataAuthority获取数据权限-->
     * @param paramMap
     * @return 
     * @author: baisong
     * @time:2017年3月22日 下午5:23:18
     * history:
     * 1、2017年3月22日baisong创建方法
     */
    String getBizDataAuthority(Map<String, Object> paramMap);
}