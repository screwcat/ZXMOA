package com.zx.moa.ioa.Examination.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: ExaminationDao
 * 模块名称：MOA首页Dao
 * @Description: 内容摘要：
 * @author ZhaoWei
 * @date 2016年12月8日
 * @version V1.0
 * history:
 * 1、2016年12月8日 zhaowei 创建文件
 */
@MyBatisRepository
public interface ExaminationDao
{

    public List<Map<String, Object>> getApproveListByQuery(Map<String, Object> map);

    public List<Map<String, Object>> getApproveListByDaiQuery(Map<String, Object> map);

    public List<Map<String, Object>> getApproveListByQueryV114(Map<String, Object> map);
    
    public List<Map<String, Object>> getApproveListByDaiQueryV114(Map<String, Object> map);

    public List<Map<String, Object>> getApproveListByLoginUserNew(Map<String, Object> map);

    public int getApproveListByLoginUserCount(Map<String, Object> map);

    /**
     * @Title: getApproveListByLoginUserV119
     * @Description: MOA首页待我审批列表查询
     * @param map
     * @return 
     * @author: sunlq
     * @time:2016年12月30日 下午2:24:53
     * history:
     * 1、2016年12月30日 sunlq 创建方法
     */
    public List<Map<String, Object>> getApproveListByLoginUserV119(Map<String, Object> map);

    /**
     * @Title: getOtherListByLoginUserV119
     * @Description: MOA首页与我有关列表查询
     * @param map
     * @return 
     * @author: sunlq
     * @time:2016年12月30日 下午3:26:57
     * history:
     * 1、2016年12月30日 sunlq 创建方法
     */
    public List<Map<String, Object>> getOtherListByLoginUserV119(Map<String, Object> map);

}
