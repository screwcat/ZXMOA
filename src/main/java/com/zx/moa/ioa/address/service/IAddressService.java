package com.zx.moa.ioa.address.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：
 * @ClassName: addressService
 * 模块名称：
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2017年7月26日
 * @version V1.0
 * history:
 * 1、2017年7月26日 zhaowei 创建文件
 */
public interface IAddressService
{
    public Map<String, List<Map<String, Object>>> getContactsAndHotLines(HttpServletRequest request);

    /**
     * @Title: getDeptsContact
     * @Description: 根据部门获取联系人及子部门
     * @param request
     * @return 
     * @author: Libo
     * @time:2017年7月27日 下午2:12:06
     * history:
     * 1、2017年7月27日 Libo 创建方法
    */
    public Map<String, List<Map<String, Object>>> getDeptsContact(HttpServletRequest request);

    /**
     * @Title: uploadTopContacts
     * @Description: 保存常用联系人
     * @param request
     * @return 
     * @author: Libo
     * @time:2017年7月28日 上午10:24:30
     * history:
     * 1、2017年7月28日 Libo 创建方法
    */
    public List<Map<String, Object>> uploadTopContacts(HttpServletRequest request);

    /**
     * @Title: getHostLines
     * @Description: 获取热线电话
     * @param request
     * @return 
     * @author: Libo
     * @time:2017年7月28日 下午3:01:16
     * history:
     * 1、2017年7月28日 Libo 创建方法
    */
    public List<Map<String, Object>> getHostLines(HttpServletRequest request);

    /**
     * @Title: getContactDetail
     * @Description: 获取人员详细信息
     * @param request
     * @return 
     * @author: Libo
     * @time:2017年7月28日 下午3:55:11
     * history:
     * 1、2017年7月28日 Libo 创建方法
    */
    public Map<String, Object> getContactDetail(HttpServletRequest request);

}
