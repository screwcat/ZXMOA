package com.zx.moa.ioa.Examination.servie.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.Examination.persist.ExaminationDao;
import com.zx.moa.ioa.Examination.servie.IexaminationService;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: ExaminationServiceImpl
 * 模块名称：
 * @Description: 内容摘要：
 * @author ZhaoWei
 * @date 2016年12月8日
 * @version V1.0
 * history:
 * 1、2016年12月8日 ZhaoWei 创建文件
 */
@Service("iexaminationService")
public class ExaminationServiceImpl implements IexaminationService
{
    @Autowired
    ExaminationDao examinationDao;

    @Override
    public List<Map<String, Object>> getApproveListByLoginUser(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getApproveListByQuery(map);

        return resultMaps;

    }

    @Override
    public List<Map<String, Object>> getApproveListBydaiUser(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getApproveListByDaiQuery(map);

        return resultMaps;

    }

    @Override
    public List<Map<String, Object>> getApproveListByLoginUserV114(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getApproveListByQueryV114(map);

        return resultMaps;
    }
    
    @Override
    public List<Map<String, Object>> getApproveListBydaiUserV114(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getApproveListByDaiQueryV114(map);

        return resultMaps;

    }

    /**
     * @Title: getApproveListByLoginUserNew
     * @Description: MOA首页待我审批列表查询
     * @param map
     * @return 
     * @author: ZhaoWei
     * @time:2016年12月8日 下午4:15:38
     * history:
     * 1、2016年12月8日 zhaowei 创建方法
     */
    @Override
    public List<Map<String, Object>> getApproveListByLoginUserNew(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getApproveListByLoginUserNew(map);

        return resultMaps;

    }

    /**
     * @Title: getApproveListByLoginUserV119
     * @Description: MOA首页待我审批列表查询
     * @param map
     * @return 
     * @author: sunlq
     * @time:2016年12月30日 下午2:22:31
     * history:
     * 1、2016年12月30日 sunlq 创建方法
     */
    @Override
    public List<Map<String, Object>> getApproveListByLoginUserV119(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getApproveListByLoginUserV119(map);

        return resultMaps;

    }

    /**
     * @Title: getOtherListByLoginUserV119
     * @Description: MOA首页与我有关列表查询
     * @param map
     * @return 
     * @author: sunlq
     * @time:2016年12月30日 下午3:25:30
     * history:
     * 1、2016年12月30日 sunlq 创建方法
     */
    @Override
    public List<Map<String, Object>> getOtherListByLoginUserV119(Map<String, Object> map)
    {
        List<Map<String, Object>> resultMaps = examinationDao.getOtherListByLoginUserV119(map);

        return resultMaps;

    }

    /**
     * @Title: getApproveListByLoginUserCount
     * @Description: MOA首页待我审批列表查询总数量
     * @param map
     * @return 
     * @author: ZhaoWei
     * @time:2016年12月8日 下午4:16:45
     * @see com.zx.moa.ioa.Examination.servie.IexaminationService#getApproveListByLoginUserCount(java.util.Map)
     * history:
     * 1、2016年12月8日 ZhaoWei 创建方法
     */
    @Override
    public int getApproveListByLoginUserCount(Map<String, Object> map)
    {
        return examinationDao.getApproveListByLoginUserCount(map);
    }

}
