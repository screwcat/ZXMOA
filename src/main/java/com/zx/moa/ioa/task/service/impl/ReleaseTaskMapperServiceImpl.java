package com.zx.moa.ioa.task.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.ioa.task.persist.ReleaseTaskDao;
import com.zx.moa.ioa.task.service.IReleaseTaskMapperService;
import com.zx.moa.util.HttpClientUtil;

/**
 * @ClassName: ReleaseTaskMapperServiceImpl
 * @Description: 内容摘要：任务相关
 * @author sunlq
 * @date 2016年11月25日
 * @version V1.0
 * history:
 * 1、2016年11月25日 sunlq 创建文件
 */
@Service("releaseTaskMapperServiceImpl")
public class ReleaseTaskMapperServiceImpl implements IReleaseTaskMapperService {
	@Autowired
	private ReleaseTaskDao releaseTaskDao;

	/*
	 * 查询所属部门下的人员
	 */
	@Override
	public List<Map<String, Object>> selectPersonnelInfoByDept(
			Map<String, Object> map) {
		return releaseTaskDao.selectPersonnelInfoByDept(map);
	}

	/*
	 * 查询人员信息
	 */

	@Override
	public List<Map<String, Object>> selectPersonnelInfoByNameAndCode(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return releaseTaskDao.selectPersonnelInfoByNameAndCode(map);
	}

	/*
	 * 查询：1默认发布人、2常用发布人、3常用反馈人、4常用转移人、5常用协同人
	 */

	@Override
	public List<Map<String, Object>> selectReleasePersonnelById(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return releaseTaskDao.selectDefaultReleasePersonnelById(map);
	}


    /**
     * @Title: selectTaskById
     * @Description: 任务详情
     * @param map
     * @return 
     * @author: zhangmingjian
     * @time:2016年11月25日 下午2:45:48
     * history:
     * 1、2016年11月25日 zhangmingjian 创建方法
     */
	@Override
	public Map<String, Object> selectTaskById(Map<String, Object> map) {
		Map<String, Object> resmap = releaseTaskDao.selectTaskById(map);
		String finish_time =  resmap.get("finish_time").toString();
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = (Date) sd.parse(finish_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resmap.put("finish_time",sd.format(date));

		List<Map<String, Object>> list = releaseTaskDao
				.selectTaskInfoById(resmap);
		for (Map<String, Object> map2 : list) {
			if(3==Integer.parseInt(map2.get("info_type").toString())||2==Integer.parseInt(map2.get("info_type").toString())	
					){
				map2.put("task_info_relative",map2.get("task_info"));
				//获取属性文件配置路径
                String path = HttpClientUtil.getSysUrl("moaUrl");

				map2.put("task_info", path+ map2.get("task_info"));
				
				if(3==Integer.parseInt(map2.get("info_type").toString())){
					map2.put("task_info_mini", path+ map2.get("task_info_mini"));
				}
				
			}
			if(map2.get("info_date")==null){
			    map2.put("info_date", "");
			}
			
		}
		resmap.put("info", list);
		return resmap;
	}

    /**
    * @Title: selectTaskByIdfor117
    * @Description: TODO(1.1.7版本获取任务详情)
    * @param map
    * @return Map<String, Object>
    * @author: Lixiaolong
    * @time:2016年12月9日 下午5:10:19
    * @see com.zx.moa.ioa.task.service.IReleaseTaskMapperService#selectTaskByIdfor117(java.util.Map)
    * history:
    * 1、2016年12月9日 Lixiaolong 创建方法
    */
    @Override
    public Map<String, Object> selectTaskByIdfor117(Map<String, Object> map)
    {
        Map<String, Object> resmap = releaseTaskDao.selectTaskByIdfor117(map);
        String finish_time = resmap.get("finish_time").toString();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try
        {
            date = (Date) sd.parse(finish_time);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        resmap.put("finish_time", sd.format(date));

        List<Map<String, Object>> list = releaseTaskDao.selectTaskInfoById(resmap);
        for (Map<String, Object> map2 : list)
        {
            if (3 == Integer.parseInt(map2.get("info_type").toString()) || 2 == Integer.parseInt(map2.get("info_type").toString()))
            {
                map2.put("task_info_relative", map2.get("task_info"));
                // 获取属性文件配置路径
                String path = HttpClientUtil.getSysUrl("moaUrl");

                map2.put("task_info", path + map2.get("task_info"));

                if (3 == Integer.parseInt(map2.get("info_type").toString()))
                {
                    map2.put("task_info_mini", path + map2.get("task_info_mini"));
                }

            }
            if(map2.get("info_date")==null){
                map2.put("info_date", "");
            }
            
        }
        resmap.put("info", list);
        return resmap;
    }

    /**
     * @Title: getGroupInfo
     * @Description: TODO(获取分组信息)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月9日 下午3:03:10
     * @see com.zx.moa.ioa.task.service.IReleaseTaskMapperService#getGroupInfo(java.util.Map)
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    @Override
    public List<Map<String, Object>> getGroupInfo(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        // 根据当前登录人id获取分组信息
        return releaseTaskDao.getGroupInfo(map);
    }

    /**
     * @Title: getPersonnelInfoByGroup
     * @Description: TODO(查询分组人员信息)
     * @param map
     * @return 
     * @author: Lixiaolong
     * @time:2016年12月9日 下午6:33:16
     * @see com.zx.moa.ioa.task.service.IReleaseTaskMapperService#getPersonnelInfoByGroup(java.util.Map)
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    @Override
    public Map<String, Object> getPersonnelInfoByGroup(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<>();
        // 查询分组信息
        Map<String, Object> groupinfo = releaseTaskDao.getGroupname(map);
        List<Map<String, Object>> list = releaseTaskDao.getPersonnelInfoByGroup(map);
        for (int i = 0; i < list.size(); i++)
        {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("dept_id", list.get(i).get("dept_pid").toString());
            Map<String, Object> deptname = releaseTaskDao.getDeptnameforGroup(temp);
            if (deptname == null)
            {
                list.get(i).put("parent_deptname", "");
            }
            else
            {
                list.get(i).put("parent_deptname", deptname.get("dept_name") == null ? "" : deptname.get("dept_name").toString());
            }

        }
        result.put("personnelList", list);
        result.put("group_id", groupinfo.get("group_id"));
        result.put("group_name", groupinfo.get("group_name"));
        return result;
    }

}
