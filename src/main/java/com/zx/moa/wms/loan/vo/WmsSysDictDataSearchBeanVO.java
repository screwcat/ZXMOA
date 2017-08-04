package com.zx.moa.wms.loan.vo;

import com.zx.platform.syscontext.vo.GridParamBean;

/*
 * @version 2.0
 */

public class WmsSysDictDataSearchBeanVO extends GridParamBean {

	private static final long serialVersionUID = 1L;

	private Integer wms_sys_dict_data_id;
    
    private Integer p_wms_sys_dict_data_id;
    
    private Integer wms_sys_dict_id;

    private String sort_order;


    public Integer getWms_sys_dict_data_id() {
        return wms_sys_dict_data_id;
    }

    public void setWms_sys_dict_data_id(Integer wms_sys_dict_data_id) {
        this.wms_sys_dict_data_id = wms_sys_dict_data_id;
    }

    public Integer getP_wms_sys_dict_data_id() {
        return p_wms_sys_dict_data_id;
    }

    public void setP_wms_sys_dict_data_id(Integer p_wms_sys_dict_data_id) {
        this.p_wms_sys_dict_data_id = p_wms_sys_dict_data_id;
    }

    public Integer getWms_sys_dict_id() {
        return wms_sys_dict_id;
    }

    public void setWms_sys_dict_id(Integer wms_sys_dict_id) {
        this.wms_sys_dict_id = wms_sys_dict_id;
    }

    public String getSort_order()
    {
        return sort_order;
    }

    public void setSort_order(String sort_order)
    {
        this.sort_order = sort_order;
    }
}