package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.SysDept;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;



@MyBatisRepository
public interface SysDeptDao extends BaseDao<SysDept>
{
    /**
     * 根据父部门ID删除部门
     * 
     * @param p_dept_id 父部门ID
     */
    void deleteByP_dept_id(Integer p_dept_id);

    /**
     * 根据dept_id获得子部门dept_id
     * 
     * @param dept_id
     * @return
     */
    List<Integer> getDeptId(Integer dept_id);
    
    List<SysDept> getListByEntityCity(SysDept sysDept);
    
    /**
     * @Title: getFatherDeptInfo 
     * @Description: 根据部门ID查询上级部门信息
     * @param dept_id
     * @return SysDept 
     * @throws
     * @author lvtu 
     * @date 2015年9月7日 下午1:33:37
     */
    public SysDept getFatherDeptInfo(Integer dept_id);
    /**
     * 根据主键获取部门信息返回map
     * @param dept_id
     * @return
     */
    Map<String,Object> getDeptInfo(Integer dept_id);
    /**
     * 根据主键获取下级部门信息返回map
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getLDeptInfo(Integer dept_id);
    /**
     * 根据主键获取上级部门信息返回map
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getPDeptInfo(Integer dept_id);
    
    /**
     * 根据主键获取下级部门信息返回map给app使用--获取业务组
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getLDeptInfoforApp(Integer dept_id);
    /**
     * 根据主键获取下级部门信息返回map给app使用获取门店
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getLDeptInfoforDApp(Integer dept_id);
    /**
     * 根据主键获取上级部门信息返回map给app使用
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getPDeptInfoforApp(Integer dept_id);
    /**
     * 根据主键获取部门信息返回map-app使用
     * @param dept_id
     * @return
     */
    Map<String,Object> getDeptInfoforApp(Integer dept_id);
    
    /**
     * 根据主键获取上级部门信息返回map给app使用返回下级所有部门
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getLAllDeptInfoforApp(Integer dept_id);
    /**
     * 判断是否是有贷款单据权限
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getDeptlv(Integer dept_id);
    /**
     * 获取五级部门
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getDept5(Map<String,Object> map);
    /**
     * 获取六级部门
     * @param dept_id
     * @return
     */
    List<Map<String,Object>> getDept6(Map<String,Object> map);

    /**
     * @Title: getCDeptInfobyCode
     * @Description: 3.2.24 WMS_OUT_024 初始化单位筛选条件
     * @param retMap
     * @return 
     * @author: baisong
     * @time:2017年3月21日 下午3:25:31
     * history:
     * 1、2017年3月21日 baisong 创建方法
     */
    List<Map<String, Object>> getCDeptInfobyCode(Map<String, Object> retMap);

    /**
     * @Title: getDeptInfobyCodeLevel
     * @Description: <!-- 根据权限获取不同等级部门信息 公共方法请勿修改 -->
     * @param paraMap
     * @return 
     * @author: baisong
     * @time:2017年3月21日 下午3:40:54
     * history:
     * 1、2017年3月21日 baisong 创建方法
     */
    List<Map<String, Object>> getDeptInfobyCodeLevel(Map<String, Object> paraMap);

    /**
     * @Title: getDeptInfobyMenu
     * @Description: <!-- 根据菜单和人获取当前人的部门权限 *********公共方法请勿修改*******-->
     * @param paraMap
     * @return 
     * @author: baisong
     * @time:2017年3月21日 下午4:40:59
     * history:
     * 1、2017年3月21日 baisong创建方法
     */
    String getDeptInfobyMenu(Map<String, Object> paraMap);

    /**
     * @Title: queryChildrenDeptInfoByUrl
     * @Description: 根据菜单url和人员id获取权限
     * @param paramMap
     * @return 
     * @author: baisong
     * @time:2017年4月12日 下午4:44:36
     * history:
     * 1、2017年4月12日 baisong 创建方法
     */
    Map<String, Object> queryChildrenDeptInfoByUrl(Map<String, Object> paramMap);
}
