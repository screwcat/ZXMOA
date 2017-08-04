package com.zx.moa.ioa.task.persist;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.mybatis.MyBatisRepository;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: TaskV117MessageDao
 * 模块名称：管理分组信息DAO层
 * @Description: 内容摘要：查看分组信息
 * @author suncf
 * @date 2016年12月8日
 * @version V1.1.7
 * history:
 * 1、2016年12月8日 suncf 创建文件
 */
@MyBatisRepository
public interface TaskV117MessageDao
{

    /**
     * @Title: selectGroupInfoList
     * @Description: TODO(查看分组信息)
     * @param map
     * @return 分组信息List
     * @author: suncf
     * @time:2016年12月10日 上午10:52:05
     * history:
     * 1、2016年12月10日 Administrator 创建方法
     */
    public List<Map<String, Object>> selectGroupInfoList(Map<String, Object> map);
}
