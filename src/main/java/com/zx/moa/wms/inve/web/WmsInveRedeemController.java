package com.zx.moa.wms.inve.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInveRedeemService;
import com.zx.moa.wms.inve.vo.WmsInveDebtWorkFlowVO;
import com.zx.moa.wms.inve.vo.WmsInveRedeemVO;

@Controller
public class WmsInveRedeemController
{

    private static Logger log = LoggerFactory.getLogger(WmsInveRedeemController.class);

    @Autowired
    private IWmsInveRedeemService wmsinveredeemService;

    /**
     * 
     * @Title: getRedeemApplyInfoByPk
     * @Description: 通过主键查询赎回单据详细信息和流程信息
     * @param wms_inve_redeem_id
     * @param request
     * @return 
     * @author: zhangyunfei
     * @time:2016年12月7日 上午9:16:31
     * history:
     * 1、2016年12月7日 Administrator 创建方法
     */
    @RequestMapping(value = "/inve/getRedeemApplyInfoByPk.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getRedeemApplyInfoByPk(HttpServletRequest request, String wms_inve_redeem_id)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        return ResultHelper.getSuccess(wmsinveredeemService.getRedeemApplyinfoByPk(wms_inve_redeem_id, personnel.getPersonnel_id()));
    }

    /**
     * 
     * @Title: updateWmsInveRedeemDetail
     * @Description: 手机对赎回单据进行审批
     * @param request
     * @param wDebtWorkFlowVO
     * @param wmsInveRedeemVO
     * @return 
     * @author: zhangyunfei
     * @time:2016年12月9日 上午9:37:47
     * history:
     * 1、2016年12月9日 Administrator 创建方法
     */
    @RequestMapping(value = "/inve/updateWmsInveRedeemDetail.do", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> updateWmsInveRedeemDetail(HttpServletRequest request, WmsInveDebtWorkFlowVO wDebtWorkFlowVO, WmsInveRedeemVO wmsInveRedeemVO)
    {
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> map = wmsinveredeemService.updateWmsInveRedeemDetail(personnel, wDebtWorkFlowVO, wmsInveRedeemVO);
        if (map.get("ret_code").equals("000"))
        {
            return ResultHelper.getSuccess(map);
        }
        else
        {
            return ResultHelper.getError(map);
        }
    }
}
