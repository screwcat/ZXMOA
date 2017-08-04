package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.wms.SysUserRole;
import com.zx.moa.util.mybatis.BaseDao;
import com.zx.moa.util.mybatis.MyBatisRepository;



@MyBatisRepository
public interface SysUserRoleDao extends BaseDao<SysUserRole>
{
    /**
     * @Title: 根据流程角色id获取拥有当前角色的人 并且当前人需要拥有对应菜单权限 
     * @param map  map中需要传递role_value 参数 是流程角色 例如：htzy为合同专员
     * @return list  map 中会返回拥有当前权限的人员id
     * @author  baisong
     */
    List<Map<String,Object>> getRoleListUser(Map<String,Object> map);
    /**
     * @Title: 根据流程角色id获取拥有当前角色的人 并且当前人需要拥有对应菜单权限 
     * @param map  map中需要传递user_id 参数 是id 
     * @return list  map 中会返回拥有当前权限的人员权限
     * @author  baisong
     */
    List<Map<String,Object>> getUserListRole(Map<String,Object> map);
    /**
     * @Title: 根据人员id获取客户经理团队经理门店经理的信息
     * @param map  
     * @return list  map 中会返回拥有当前权限的人员id
     * @author  baisong
     */
    List<Map<String,Object>> getSuperiorAndoneself(Map<String,Object> map);
    
    /**
     * @Title:<!-- 获取人员的所有角色 需要同时拥有流程角色和菜单角色 -->
     * @param map  map中需要传递user_id 参数 是id 
     * @return list  map 中会返回拥有当前权限的人员权限
     * @author  baisong
     */
    List<Map<String,Object>> getUserListAllRole(Map<String,Object> map);
    
}
