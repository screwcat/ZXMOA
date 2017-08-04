package com.zx.moa.wms.inve.service;

import java.util.List;
import java.util.Map;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.vo.WmsInveDebtWorkFlowVO;
import com.zx.moa.wms.inve.vo.WmsInveRedeemVO;

/*
 * @version 2.0
 */

public interface IWmsInveRedeemService
{

    /**
     * 
     * @Title: getRedeemApplyinfoByPk
     * @Description: 通过主键查询赎回单据的详细信息
     * @param wms_inve_redeem_id
     * @param personnel_id 当前登录的人员id
     * @return 
     * @author: zhangyunfei
     * @time:2016年12月7日 上午9:27:17
     * history:
     * 1、2016年12月7日 Administrator 创建方法
     * 2、2016年12月15日 DongHao 修改方法  添加参数personnel_id 
     */
    public Map<String, Object> getRedeemApplyinfoByPk(String wms_inve_redeem_id, Integer personnel_id);

    /**
     * @Title: updateWmsInveRedeemDetail
     * @Description: 手机对赎回单据进行审批
     * @param wDebtWorkFlowVO
     * @param wmsInveRedeemVO
     * @return 
     * @author: zhangyunfei
     * @param personnel 
     * @time:2016年12月9日 上午9:45:11
     * history:
     * 1、2016年12月9日 Administrator 创建方法
     */
    public Map<String, Object> updateWmsInveRedeemDetail(PmPersonnel personnel, WmsInveDebtWorkFlowVO wDebtWorkFlowVO, WmsInveRedeemVO wmsInveRedeemVO);

    /**
     * @Title: phoneGetPendingApprovalInfoCount
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param personnel_shortCode app端登录人的短工号
     * @param searchInfo 查询条件(客户经理姓名/客户经理短工号/客户姓名)
     * @return 
     * @author: DongHao
     * @time:2016年12月7日 下午1:33:05
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    Map<String, Object> phoneGetPendingApprovalInfoCount(String personnel_shortCode, String searchInfo);

    /**
     * @Title: phoneGetRedeemByQueryInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param personnel_shortCode app端登录人的短工号
     * @param searchInfo 查询条件(客户经理姓名/客户经理短工号/客户姓名)
     * @param query_type 查询的单据状态(1:全部;2:带我审批;3:与我相关;4:审批中;5:已完成;6:已撤销(已终止))
     * @param page
     * @param page_size
     * @param version 版本号
     * @return 
     * @author: DongHao
     * @time:2016年12月7日 下午1:39:44
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    List<Map<String, Object>> phoneGetRedeemByQueryInfo(String personnel_shortCode, String searchInfo, String query_type, int page, int page_size, String version);

    /**
     * @Title: phoneQueryType
     * @Description: 根据用户的短工号进行获取对应的信息
     * @param personnel_shortCode
     * @return 
     * @author: DongHao
     * @time:2016年12月8日 下午2:56:20
     * history:
     * 1、2016年12月8日 DongHao 创建方法
     */
    List<Map<String, Object>> phoneQueryType(String personnel_shortCode);
}