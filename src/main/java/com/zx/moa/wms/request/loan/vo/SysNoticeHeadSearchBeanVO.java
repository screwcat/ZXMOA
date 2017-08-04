package com.zx.moa.wms.request.loan.vo;

import com.zx.platform.syscontext.vo.GridParamBean;

/**
 * 
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: SysNoticeHeadSearchBeanVO
 * 模块名称：
 * @Description: 内容摘要：公告类
 * @author baisong
 * @date 2017年3月28日
 * @version V1.0
 * history:
 * 1、2017年3月28日 baisong创建文件
 */
public class SysNoticeHeadSearchBeanVO extends GridParamBean {

	private static final long serialVersionUID = 1L;
    // app名称 例如 mis
    private String notice_app_type;
    // 是否需要分页(接口文档3.4 需要分页:1 不需要分页:0)
    private String is_need_paging;

    public String getNotice_app_type()
    {
        return notice_app_type;
    }

    public void setNotice_app_type(String notice_app_type)
    {
        this.notice_app_type = notice_app_type;
    }

    public String getIs_need_paging()
    {
        return is_need_paging;
    }

    public void setIs_need_paging(String is_need_paging)
    {
        this.is_need_paging = is_need_paging;
    }
}