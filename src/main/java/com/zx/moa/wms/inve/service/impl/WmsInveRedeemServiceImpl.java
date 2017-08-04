package com.zx.moa.wms.inve.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.moa.wms.inve.service.IWmsInveRedeemService;
import com.zx.moa.wms.inve.vo.WmsInveDebtWorkFlowVO;
import com.zx.moa.wms.inve.vo.WmsInveRedeemVO;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * 
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: WmsInveRedeemServiceImpl
 * 模块名称：
 * @Description: 赎回相关操作
 * @author zhangyunfei
 * @date 2016年12月7日
 * @version V1.0
 * history:
 * 1、2016年12月7日 Administrator 创建文件
 */
@Service("wmsInveRedeemService")
public class WmsInveRedeemServiceImpl implements IWmsInveRedeemService {

    private static Logger log = LoggerFactory.getLogger(WmsInveRedeemServiceImpl.class);

    /**
     * 
     * @Title: getRedeemApplyinfoByPk
     * @Description: 通过主键查询单据的赎回详细信息和流程信息
     * @param wms_inve_redeem_id
     * @param personnel_id 当前登录的人员id
     * @return 
     * @author: zhangyunfei
     * @time:2016年12月7日 上午9:38:44
     * @see com.zx.moa.wms.inve.service.IWmsInveRedeemService#getRedeemApplyinfoByPk(java.lang.Integer, java.lang.Integer)
     * history:
     * 1、2016年12月7日 Administrator 创建方法
     * 2、2016年12月15日 DongHao 修改方法 添加方法参数personnel_id
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getRedeemApplyinfoByPk(String wms_inve_redeem_id, Integer personnel_id)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/getRedeemApplyInfoByPkMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("wms_inve_redeem_id", wms_inve_redeem_id);
        form.add("personnel_id", personnel_id.toString());

        return restTemplate.postForObject(url, form, Map.class);
    }

    /**
     * 
     * @Title: updateWmsInveRedeemDetail
     * @Description: 手机对赎回单据进行审批
     * @param wDebtWorkFlowVO
     * @param wmsInveRedeemVO
     * @return 
     * @author: zhangyunfei
     * @time:2016年12月9日 上午9:37:39
     * history:
     * 1、2016年12月9日 Administrator 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> updateWmsInveRedeemDetail(PmPersonnel personnel, WmsInveDebtWorkFlowVO wDebtWorkFlowVO, WmsInveRedeemVO wmsInveRedeemVO)
    {
        String pass = "true";
        Map<String, Object> map = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/updateWmsInveRedeemDetailMoa.do";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();

        form.add("wms_inve_redeem_id", wmsInveRedeemVO.getWms_inve_redeem_id());
        form.add("personnel_shortcode", personnel.getPersonnel_shortcode());
        form.add("taskId", wDebtWorkFlowVO.getTaskId());
        form.add("businessId", wmsInveRedeemVO.getWms_inve_redeem_id());

        if ("1".equals(wDebtWorkFlowVO.getPass()))
        {
            pass = "false";
        }

        form.add("pass", pass);
        form.add("advice", wDebtWorkFlowVO.getAdvice());

        map = restTemplate.postForObject(url, form, Map.class);

        return map;
    }

    /**
     * @Title: phoneGetPendingApprovalInfoCount
     * @Description: app端首页查询待办任务的数量
     * @param personnel_shortCode
     * @param searchInfo
     * @return 返回map集合
     * @author: DongHao
     * @time:2016年12月7日 下午1:33:09
     * @see com.zx.moa.wms.inve.service.IWmsInveRedeemService#phoneGetPendingApprovalInfoCount(java.lang.String, java.lang.String)
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> phoneGetPendingApprovalInfoCount(String personnel_shortCode, String searchInfo)
    {
        // 设置请求的服务的url
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/phoneGetPendingApprovalInfoCountWMS.do";
        // 设置需要的参数
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("personnel_shortCode", personnel_shortCode);
        form.add("searchInfo", searchInfo);
        // 请求服务接口
        Map<String, Object> map = requestService(url, form);
        // 定义返回结果的集合
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) map.get("Rows");
        if (resultList != null && resultList.size() > 0)
        {
            resultMap.put("count", resultList.size());
        }
        else
        {
            resultMap.put("count", 0);
        }
        return resultMap;
    }

    /**
     * @Title: phoneGetRedeemByQueryInfo
     * @Description: 根据查询条件进行查询赎回信息
     * @param personnel_shortCode app端登录人的短工号
     * @param searchInfo 查询条件(客户经理姓名/客户经理短工号/客户姓名)
     * @param query_type 查询的单据状态(001:全部;002:带我审批;003:与我相关;004:审批中;005:已完成;006:已撤销(已终止))
     * @param page
     * @param page_size
     * @param version 版本号
     * @return 
     * @author: DongHao
     * @time:2016年12月7日 下午1:39:49
     * @see com.zx.moa.wms.inve.service.IWmsInveRedeemService#phoneGetRedeemByQueryInfo(java.lang.String, java.lang.String, java.lang.String)
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> phoneGetRedeemByQueryInfo(String personnel_shortCode, String searchInfo, String query_type, int page, int page_size,String version)
    {
        String url = "";
        MultiValueMap<String, String> form = null;
        // 判断查询的单据状态不为空,如果为空则返回null
        if (query_type != null && !"".equals(query_type))
        {
            // 查询的状态为待我审批
            if ("002".equals(query_type))
            {
                if (page > 1)
                {
                    return new ArrayList<Map<String, Object>>();
                }
                form = new LinkedMultiValueMap<String, String>();
                url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/phoneGetPendingApprovalInfo.do";
                form.add("personnel_shortCode", personnel_shortCode);
                form.add("searchInfo", searchInfo);
                form.add("version", version);
                Map<String, Object> map = requestService(url, form);
                // 定义返回结果的集合
                return map.get("Rows") != null ? (List<Map<String, Object>>) map.get("Rows") : new ArrayList<Map<String, Object>>();
            }
            else
            {
                form = new LinkedMultiValueMap<String, String>();
                url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/phoneGetRedeemByQueryInfo.do";
                form.add("personnel_shortCode", personnel_shortCode);
                form.add("searchInfo", searchInfo);
                form.add("query_type", query_type);
                form.add("page", page + "");
                form.add("page_size", page_size + "");
                form.add("version", version);
                Map<String, Object> map = requestService(url, form);
                // 定义返回结果的集合
                return map.get("Rows") != null ? (List<Map<String, Object>>) map.get("Rows") : new ArrayList<Map<String, Object>>();
            }
        }
        return new ArrayList<Map<String, Object>>();
    }

    /**
     * @Title: phoneQueryType
     * @Description: 根据用户的短工号进行获取对应的数据
     * @param personnel_shortCode
     * @return 
     * @author: DongHao
     * @time:2016年12月8日 下午2:57:04
     * @see com.zx.moa.wms.inve.service.IWmsInveRedeemService#phoneQueryType(java.lang.String)
     * history:
     * 1、2016年12月8日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> phoneQueryType(String personnel_shortCode)
    {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        String url = PlatformGlobalVar.SYS_PROPERTIES.get("server.wmsUrl") + "/inve/phoneQueryTypeWms.do";
        form.add("personnel_shortCode", personnel_shortCode);
        Map<String, Object> map = requestService(url, form);
        // 定义返回结果的集合
        return map.get("Rows") != null ? (List<Map<String, Object>>) map.get("Rows") : new ArrayList<Map<String, Object>>();
    }

    /**
     * @Title: requestService
     * @Description: 调用指定的url的服务
     * @param url 服务接口url
     * @param map 参数
     * @return 返回map集合
     * @author: DongHao
     * @time:2016年12月7日 下午1:45:32
     * history:
     * 1、2016年12月7日 DongHao 创建方法
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> requestService(String url, MultiValueMap<String, String> map)
    {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, map, Map.class);
    }
}
