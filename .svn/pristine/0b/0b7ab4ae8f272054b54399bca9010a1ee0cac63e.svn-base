package com.zx.moa.ioa.address.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：
 * @ClassName: AddressDao
 * 模块名称：
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2017年7月26日
 * @version V1.0
 * history:
 * 1、2017年7月26日 zhaowei 创建文件
 */
@MyBatisRepository
public interface AddressDao
{
    public List<Map<String, Object>> getContacts(Integer personnel_id);

    public List<Map<String, Object>> getHot();

    public List<Map<String, Object>> getDeptAndPost(String personnel_shortcode);

    public List<Map<String, Object>> getDManageByDeptId(Integer deptId);

    public List<Map<String, Object>> getPerEqual(Integer deptId);


    /**
     * @Title: getPersonLevel2Dept
     * @Description: 查询员工上级部门
     * @param search_deptId
     * @return 
     * @author: Libo
     * @time:2017年7月27日 下午4:40:15
     * history:
     * 1、2017年7月27日 Libo 创建方法
    */
    public String getPersonLevel2Dept(int search_deptId);

    /**
     * @Title: getDeptInfo
     * @Description: 获取部门信息
     * @return 
     * @author: Libo
     * @time:2017年7月27日 下午5:00:03
     * history:
     * 1、2017年7月27日 Libo 创建方法
    */
    public List<Map<String, Object>> getDeptInfo(Map<String, Object> map);

    /**
     * @Title: getHotLine
     * @Description: 获取所有热线电话
     * @return 
     * @author: Libo
     * @time:2017年7月27日 下午5:25:26
     * history:
     * 1、2017年7月27日 Libo 创建方法
    */
    public List<Map<String, Object>> getHotLine();

    /**
     * @Title: getPersonForList
     * @Description: 验证常用联系人状态
     * @param param
     * @return 
     * @author: Libo
     * @time:2017年7月28日 上午11:38:36
     * history:
     * 1、2017年7月28日 Libo 创建方法
    */
    public List<Map<String, Object>> getPersonForList(Map<String, Object> param);

    /**
     * @Title: getContactDetail
     * @Description: 获取人员详细信息
     * @return 
     * @author: Libo
     * @param integer 
     * @time:2017年7月28日 下午3:56:50
     * history:
     * 1、2017年7月28日 Libo 创建方法
    */
    public Map<String, Object> getContactDetail(Integer integer);

    public String getParentsDeptIds(Integer deptId);
    
    public Map<String, Object> getPersonnel(Integer personnel_id);

    public Integer getDeptPerson(Integer deptId);
    
    public String queryChildrenDeptInfo(Integer deptId);

    public List<Map<String, Object>> getFenBypersonId(Integer personnel_id);

    public Integer getPentDeptId(Integer deptId);

    public String getpentDeptName(Integer deptPid);
    
    public List<Map<String, Object>> getdeptIdByMenuid(Map<String, Object> map);

}
