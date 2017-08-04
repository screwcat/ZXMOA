package com.zx.moa.ioa.Examination.servie;

import java.util.List;
import java.util.Map;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: IexaminationService
 * 模块名称：
 * @Description: 内容摘要：MOA首页Service
 * @author ZhaoWei
 * @date 2016年12月8日
 * @version V1.0
 * history:
 * 1、2016年12月8日 ZhaoWei 创建文件
 */
public interface IexaminationService
{

    public List<Map<String, Object>> getApproveListByLoginUser(Map<String, Object> map);

    public List<Map<String, Object>> getApproveListByLoginUserV114(Map<String, Object> map);

    public List<Map<String, Object>> getApproveListBydaiUser(Map<String, Object> map);
    
    public List<Map<String, Object>> getApproveListBydaiUserV114(Map<String, Object> map);

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
    public List<Map<String, Object>> getApproveListByLoginUserNew(Map<String, Object> map);

    /**
     * @Title: getApproveListByLoginUserNew
     * @Description: MOA首页待我审批列表查询总数量
     * @param map
     * @return 
     * @author: ZhaoWei
     * @time:2016年12月8日 下午4:15:38
     * history:
     * 1、2016年12月8日 zhaowei 创建方法
     */
    public int getApproveListByLoginUserCount(Map<String, Object> map);

    /**
     * @Title: getApproveListByLoginUserV119
     * @Description: MOA首页待我审批列表查询
     * @param map
     * @return 
     * @author: sunlq
     * @time:2016年12月30日 下午2:23:09
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
     * @time:2016年12月30日 下午3:26:11
     * history:
     * 1、2016年12月30日 sunlq 创建方法
     */
    List<Map<String, Object>> getOtherListByLoginUserV119(Map<String, Object> map);

}
