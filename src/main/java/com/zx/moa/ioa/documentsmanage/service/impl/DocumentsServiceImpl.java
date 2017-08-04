package com.zx.moa.ioa.documentsmanage.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zx.moa.ioa.documentsmanage.persist.DocumentsDao;
import com.zx.moa.ioa.documentsmanage.service.IDocumentsService;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.SysUtil;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: DocumentsServiceImpl
 * 模块名称：待我审批
 * @Description: 内容摘要：IOA单据相关
 * @author sunlq
 * @date 2016年12月2日
 * @version V1.1.5
 * history:
 * 1、2016年12月2日 Administrator 创建文件
 */
@Service("DocumentsService")
public class DocumentsServiceImpl implements IDocumentsService
{

    @Autowired
    private DocumentsDao DocumentsDao;

    private final String FILE_URL = "ioa/getFile.do?url=";

    @Override
    public Map<String, Object> getDocInfoById(HttpServletRequest request)
    {
        // TODO Auto-generated method stub

        Map<String, Object> resmap = new HashMap<String, Object>();
        int count = 0;
        String order_id = request.getParameter("order_id");
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("order_id", order_id);
        resmap = DocumentsDao.getDocInfoById(parMap);
        List<Map<String, Object>> applist = DocumentsDao.getApproveInfoById(parMap);
        dealFilePath(resmap);
        resmap.put("history", applist);
        return resmap;
    }

    /**
     * @Title: dealFilePath
     * @Description: 处理附件路径，转为调用附件方法
     * @param map 
     * @author: sunlq
     * @time:2016年12月2日 下午2:50:48
     * history:
     * 1、2016年12月2日 sunlq 创建方法
     */
    private void dealFilePath(Map<String, Object> map)
    {
        String img_path = (String) map.get("img_path");
        // 附件非空
        if (StringUtil.isNotBlank(img_path))
        {
            JSONArray ja = JSONArray.parseArray(img_path);
            for (int i = 0; i < ja.size(); i++)
            {
                JSONObject jo = ja.getJSONObject(i);
                String attachment_address = jo.getString("attachment_address");
                if (StringUtil.isNotBlank(attachment_address))
                {
                    jo.put("full_address", FILE_URL + attachment_address);
                }
                ja.set(i, jo);
            }
            map.put("img_path", ja.toJSONString());
        }
        // 附件为空
        else
        {
            map.put("img_path", "[]");
        }
    }

    // 审批单据
    @SuppressWarnings("unchecked")
    @Override
    public ResultBean<Map<String, Object>> approvalDocuments(HttpServletRequest request, String content, Integer order_id, Integer approve_state)
    {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String user_id = (personnel.getPersonnel_id()).toString();

        if (order_id != null && approve_state != null)
        {
            String url = HttpClientUtil.getSysUrl("nozzleUrl");
            // String url = "http://192.168.1.228:4080/modi/restful/simple";
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_003"));
            nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
            nvps.add(new BasicNameValuePair("user_id", user_id));
            nvps.add(new BasicNameValuePair("content", content));
            nvps.add(new BasicNameValuePair("order_relation_id", (order_id).toString()));
            nvps.add(new BasicNameValuePair("approve_state", (approve_state).toString()));
            try
            {
                result = HttpClientUtil.post(url, nvps, Map.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return ResultHelper.getNetworkError(result);
            }
            Boolean flag = (Boolean) result.get("flag");
            if (flag)
            {
                return ResultHelper.getSuccess(result);
            }
            else
            {
                Object err_code = result.get("err_code");
                if (err_code == null)
                {
                    err_code = ResultHelper.RET_ERROR;
                }
                return new ResultBean<Map<String, Object>>(err_code.toString(), (String) result.get("message"));
            }

        }
        else
        {
            return new ResultBean<Map<String, Object>>(ResultHelper.RET_ERROR, "操作失败，请联系客服!", result);
        }
    }

    // 审批单据
    @SuppressWarnings("unchecked")
    @Override
    public ResultBean<Map<String, Object>> approvalLeaveDocuments(HttpServletRequest request, String content, Integer order_id, Integer approve_state, String quit_agreeTime, Integer quitOrPart)
    {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        PmPersonnel personnel = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        String user_id = (personnel.getPersonnel_id()).toString();

        if (order_id != null && approve_state != null)
        {
            try
            {
                String url = HttpClientUtil.getSysUrl("nozzleUrl");
                // String url = "http://192.168.1.228:4080/modi/restful/simple";
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("interface_num", "IOA_OUT_003"));
                nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
                nvps.add(new BasicNameValuePair("user_id", user_id));
                nvps.add(new BasicNameValuePair("content", content));
                nvps.add(new BasicNameValuePair("order_relation_id", (order_id).toString()));
                nvps.add(new BasicNameValuePair("approve_state", (approve_state).toString()));
                if (approve_state == 1)
                {// 同意
                    Map<String, Object> temp = new HashMap<String, Object>();
                    temp.put("quit_agreeTime", quit_agreeTime);
                    temp.put("quitOrPart", quitOrPart);
                    nvps.add(new BasicNameValuePair("ext", new ObjectMapper().writeValueAsString(temp)));
                }

                result = HttpClientUtil.post(url, nvps, Map.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return ResultHelper.getNetworkError(result);
            }
            Boolean flag = (Boolean) result.get("flag");
            if (flag)
            {
                return ResultHelper.getSuccess(result);
            }
            else
            {
                Object err_code = result.get("err_code");
                if (err_code == null)
                {
                    err_code = ResultHelper.RET_ERROR;
                }
                return new ResultBean<Map<String, Object>>(err_code.toString(), (String) result.get("message"));
            }

        }
        else
        {
            return new ResultBean<Map<String, Object>>(ResultHelper.RET_ERROR, "操作失败，请联系客服!", result);
        }
    }

    @Override
    public Map<String, Object> getLeaveOrderInfo(HttpServletRequest request)
    {
        // TODO Auto-generated method stub
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("order_id", request.getParameter("order_id"));
        // 获取离职单据信息
        Map<String, Object> result = DocumentsDao.getLeaveInfoByOrderId(parMap);
        // 获取审批历史流程
        List<Map<String, Object>> history = DocumentsDao.getLeaveHistory(parMap);
        result.put("history", history);
        // 附件信息
        // if(null != result.get("img_path")){
        // String str = result.get("img_path").toString();
        // List<Map<String, Object>> fujian = new ArrayList<>();
        // fujian = readValue(str, List.class);
        // if(fujian.size()!=0){
        // result.put("attachment_old_name",fujian.get(0).get("attachment_old_name")
        // ); //附件原名称
        // result.put("attachment_address",
        // fujian.get(0).get("attachment_address")); //服务器存储地址
        // result.put("full_address",
        // FILE_URL+fujian.get(0).get("attachment_address"));//MOA请求地址(附件MOA请求全路径IOS使用)
        //
        // }
        // }
        return result;
    }

    /**
    * @param content 原始json字符串数据
    * @param valueType 要转换的JavaBean类型
    * @return JavaBean对象
    */
    // public static <T> T readValue(String content, Class<T> valueType) {
    // if (objectMapper == null) {
    // objectMapper = new ObjectMapper();
    // }
    // try {
    // return objectMapper.readValue(content, valueType);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // return null;
    // }

    @Override
    public Map<String, Object> getDocInfoByIdV115(HttpServletRequest request)
    {
        Map<String, Object> resmap = new HashMap<String, Object>();
        String order_id = request.getParameter("order_id");
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("order_id", order_id);
        // 主表记录
        resmap = DocumentsDao.getDocInfoById(parMap);
        // 审批历史流程
        List<Map<String, Object>> applist = DocumentsDao.getApproveInfoById(parMap);
        // 待审批流程
        List<Map<String, Object>> appNolist = DocumentsDao.getNoApproveInfoById(parMap);
        List<Map<String, Object>> appNolistLast = new ArrayList<Map<String, Object>>();
        if (appNolist.size() > 0 && appNolist != null)
        {
            // 短工号跟名字叠加格式list
            for (Map<String, Object> m : appNolist)
            {
                Map<String, Object> appMap = new HashMap<String, Object>();
                appMap.put("approve_personnel", m.get("approve_personnel_name").toString() + m.get("approve_personnel_no").toString());
                appMap.put("level_flag", Integer.parseInt(m.get("level_flag").toString()));
                appMap.put("level_index", Integer.parseInt(m.get("level_index").toString()));
                appNolistLast.add(appMap);
            }
            appNolist.clear();
            appNolist = listTransformation(appNolistLast);
        }

        // 签收流程
        parMap.put("approve_content", "已签收");
        List<Map<String, Object>> receive_list = DocumentsDao.getApproveShouInfoById(parMap);
        dealFilePath(resmap);
        resmap.put("history", applist == null ? new ArrayList() : applist);
        resmap.put("check_list", appNolist == null ? new ArrayList() : appNolist);
        resmap.put("receive_list", receive_list == null ? new ArrayList() : receive_list);
        return resmap;
    }

    @Override
    public Map<String, Object> getLeaveOrderInfoV115(HttpServletRequest request)
    {
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("order_id", request.getParameter("order_id"));
        // 获取离职单据信息
        Map<String, Object> result = DocumentsDao.getLeaveInfoByOrderId(parMap);
        // 获取审批历史流程
        List<Map<String, Object>> history = DocumentsDao.getLeaveHistory(parMap);
        // 待审批流程
        List<Map<String, Object>> appNolist = DocumentsDao.getNoApproveInfoById(parMap);
        List<Map<String, Object>> appNolistLast = new ArrayList<Map<String, Object>>();
        if (appNolist.size() > 0 && appNolist != null)
        {
            // 短工号跟名字叠加格式list
            for (Map<String, Object> m : appNolist)
            {
                Map<String, Object> appMap = new HashMap<String, Object>();
                appMap.put("approve_personnel", m.get("approve_personnel_name").toString() + m.get("approve_personnel_no").toString());
                appMap.put("level_flag", Integer.parseInt(m.get("level_flag").toString()));
                appMap.put("level_index", Integer.parseInt(m.get("level_index").toString()));
                appNolistLast.add(appMap);
            }
            appNolist.clear();
            appNolist = listTransformation(appNolistLast);
        }

        // 签收流程
        parMap.put("approve_content", "已签收");
        List<Map<String, Object>> receive_list = DocumentsDao.getApproveShouInfoById(parMap);
        result.put("history", history == null ? new ArrayList() : history);
        result.put("check_list", appNolist == null ? new ArrayList() : appNolist);
        result.put("receive_list", receive_list == null ? new ArrayList() : receive_list);
        return result;
    }

    /**
    * level_index相同合并list
    * @param list
    * @return
    */
    public static List<Map<String, Object>> listTransformation(List<Map<String, Object>> list)
    {
        // 合并后list
        List<Map<String, Object>> appNolist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++)
        {
            String level_index = list.get(i).get("level_index").toString();
            int flag = 0;// 0为新增数据，1为增加count
            for (int j = 0; j < appNolist.size(); j++)
            {
                String level_index_ = appNolist.get(j).get("level_index").toString();
                if (level_index.equals(level_index_))
                {
                    String sum = appNolist.get(j).get("approve_personnel") + "、" + list.get(i).get("approve_personnel");
                    appNolist.get(j).put("approve_personnel", sum);
                    flag = 1;
                    continue;
                }
            }
            if (flag == 0)
            {
                appNolist.add(list.get(i));
            }
        }
        for (Map<String, Object> map : appNolist)
        {
            map.remove("level_index");
            System.out.println(map);
        }
        return appNolist;
    }

    /**
     * @Title: getDocInfoByIdV117
     * @Description: 待我审批和我的单据流程显示待审批节点和签收节点
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 下午3:33:32
     * @see com.zx.moa.ioa.documentsmanage.service.IDocumentsService#getDocInfoByIdV117(javax.servlet.http.HttpServletRequest)
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    @Override
    public Map<String, Object> getDocInfoByIdV117(HttpServletRequest request)
    {
        Map<String, Object> resmap = new HashMap<String, Object>();
        String order_id = request.getParameter("order_id");
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("order_id", order_id);
        // 主表记录
        resmap = DocumentsDao.getDocInfoById(parMap);
        // 签收记录
        List<Map<String, Object>> receive_list = DocumentsDao.getApproveShouInfoById117(parMap);
        // 审批历史流程
        List<Map<String, Object>> applist = DocumentsDao.getApproveInfoById117(parMap);
        // 此单据为撤销单据
        if (resmap.get("order_state").equals("7"))
        {
            String cancelreason = resmap.get("cancelreason") == null ? "" : resmap.get("cancelreason").toString();
            // 取撤销日志
            parMap.put("order_no", resmap.get("order_no").toString());
            List<Map<String, Object>> logs = DocumentsDao.getSysLogByOrderNo(parMap);
            if (logs.size() > 0)
            {
                Map<String, Object> log = logs.get(0);
                log.put("approve_state", -1);
                log.put("approve_content", cancelreason);
                // 未签收
                if (receive_list == null || receive_list.size() == 0)
                {
                    applist.add(log);
                }
                // 已签收
                else
                {
                    receive_list.add(log);
                }
            }
        }

        // 待审批流程
        List<Map<String, Object>> appNolist = DocumentsDao.getNoApproveInfoById(parMap);
        List<Map<String, Object>> appNolistLast = new ArrayList<Map<String, Object>>();
        if (appNolist.size() > 0 && appNolist != null)
        {
            // 短工号跟名字叠加格式list
            for (Map<String, Object> m : appNolist)
            {
                Map<String, Object> appMap = new HashMap<String, Object>();
                appMap.put("approve_personnel", m.get("approve_personnel_name").toString() + m.get("approve_personnel_no").toString());
                appMap.put("level_flag", Integer.parseInt(m.get("level_flag").toString()));
                appMap.put("level_index", Integer.parseInt(m.get("level_index").toString()));
                appNolistLast.add(appMap);
            }
            appNolist.clear();
            appNolist = listTransformation(appNolistLast);
        }

        // 签收流程
        parMap.put("approve_content", "已签收");
        if (receive_list == null || receive_list.size() == 0)
        {
            List<Map<String, Object>> unReceive_list = DocumentsDao.getApproveUnShouInfoById117(parMap);
            if (unReceive_list != null && unReceive_list.size() > 0)
            {
                for (Map<String, Object> map : unReceive_list)
                {
                    map.put("approve_personnel_name", map.get("approve_personnel_name").toString() + map.get("approve_personnel_no"));
                }
                String str = "";
                for (Map<String, Object> map : unReceive_list)
                {
                    str += map.get("approve_personnel_name") + "、";
                }
                str = str.substring(0, str.length() - 1);

                Map<String, Object> m = new HashMap<String, Object>();
                m.put("approve_personnel_name", str);
                m.put("approve_personnel_no", "");
                m.put("approve_personnel_dept_name", "");
                m.put("approve_personnel_post_name", "");
                m.put("approve_content", "");
                m.put("approve_timestamp", "");
                m.put("approve_state", 10);
                receive_list.add(m);
            }
        }

        // 提单人list:submitter_list
        List<Map<String, Object>> submitter_list = DocumentsDao.selSubmitter(parMap);
        dealFilePath(resmap);
        resmap.put("history", applist == null ? new ArrayList() : applist);
        resmap.put("check_list", appNolist == null ? new ArrayList() : appNolist);
        resmap.put("receive_list", receive_list == null ? new ArrayList() : receive_list);
        resmap.put("submitter_list", submitter_list == null ? new ArrayList<>() : submitter_list);

        // 判断考勤类申请是否有撤销按钮
        if (resmap.get("order_state").equals("7"))
        {
            resmap.put("revoke_type", 2);
        }
        else
        {
            String applyTypeStr = DocumentsDao.selectChildrenApplyTypeInfo(26);
            Calendar cl = Calendar.getInstance();
            int datenum = cl.get(Calendar.DATE);
            int month = cl.get(Calendar.MONTH) + 1;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
            // 将小时至0
            c.set(Calendar.HOUR_OF_DAY, 0);
            // 将分钟至0
            c.set(Calendar.MINUTE, 0);
            // 将秒至0
            c.set(Calendar.SECOND, 0);
            // 将毫秒至0
            c.set(Calendar.MILLISECOND, 0);
            // 获取本月第一天的时间戳
            long now_time = c.getTimeInMillis();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (applyTypeStr.indexOf(resmap.get("apply_type_id").toString()) > 0)
            {
                if (resmap.get("order_state").equals("2") || resmap.get("order_state").equals("4") || resmap.get("order_state").equals("5"))
                {
                    List<Map<String, Object>> list = DocumentsDao.selectActualHolidayTimeMoa(resmap.get("order_id").toString());
                    if (datenum >= 10)
                    {

                        for (Map<String, Object> map : list)
                        {
                            // 开始时间不为null
                            if (map.get("start_time") != null)
                            {
                                try
                                {
                                    Date start_time = sdf.parse(map.get("start_time").toString());

                                    if (now_time > start_time.getTime())
                                    {
                                        resmap.put("revoke_type", 2);
                                    }

                                }
                                catch (ParseException e)
                                {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                            // 结束时间不为null
                            if (map.get("end_time") != null)
                            {
                                try
                                {
                                    Date end_time = sdf.parse(map.get("end_time").toString());

                                    if (now_time > end_time.getTime())
                                    {
                                        resmap.put("revoke_type", 2);
                                    }

                                }
                                catch (ParseException e)
                                {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                        }

                    }
                    else
                    {
                        Date date = new Date();
                        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM");
                        for (Map<String, Object> map2 : list)
                        {
                            try
                            {
                                // 开始时间不为null
                                if (map2.get("start_time") != null)
                                {
                                    Date start_time = sdf.parse(map2.get("start_time").toString());
                                    Calendar clStart = Calendar.getInstance();
                                    clStart.setTime(start_time);
                                    clStart.add(Calendar.MONTH, 1);
                                    if (sdff.format(date).compareTo(sdff.format(clStart.getTime())) > 0)
                                    {
                                        resmap.put("revoke_type", 2);

                                    }
                                }
                                // 结束时间不为null
                                if (map2.get("end_time") != null)
                                {
                                    Date end_time = sdf.parse(map2.get("end_time").toString());
                                    Calendar clEnd = Calendar.getInstance();
                                    clEnd.setTime(end_time);
                                    clEnd.add(Calendar.MONTH, 1);
                                    if (sdff.format(date).compareTo(sdff.format(clEnd.getTime())) > 0)
                                    {
                                        resmap.put("revoke_type", 2);
                                    }
                                }
                            }
                            catch (ParseException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            else
            {
                resmap.put("revoke_type", 2);
            }
        }
        PmPersonnel pm = SysUtil.getLoginUser(request);
        if (!resmap.get("original_apply_personnel_no").toString().equals(pm.getPersonnel_shortcode()))
        {
            resmap.put("revoke_type", 2);
        }
        if (!resmap.get("apply_personnel_no").toString().equals(pm.getPersonnel_shortcode()))
        {
            resmap.put("revoke_type", 2);
        }
        if (resmap.get("revoke_type") == null)
        {
            resmap.put("revoke_type", 1);
        }
        resmap.remove("order_id");
        resmap.remove("apply_type_id");
        resmap.remove("order_state");
        resmap.remove("cancelreason");
        resmap.remove("apply_personnel_no");
        return resmap;
    }

    /**
     * @Title: getLeaveOrderInfoV117
     * @Description: 读取待审批单据详情（理财离职申请）
     * @param request
     * @return 
     * @author: zhaowei
     * @time:2016年12月9日 下午3:33:43
     * @see com.zx.moa.ioa.documentsmanage.service.IDocumentsService#getLeaveOrderInfoV117(javax.servlet.http.HttpServletRequest)
     * history:
     * 1、2016年12月9日 zhaowei 创建方法
     */
    @Override
    public Map<String, Object> getLeaveOrderInfoV117(HttpServletRequest request)
    {
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("order_id", request.getParameter("order_id"));
        // 获取离职单据信息
        Map<String, Object> result = DocumentsDao.getLeaveInfoByOrderId(parMap);
        // 获取审批历史流程
        List<Map<String, Object>> history = DocumentsDao.getLeaveHistory117(parMap);
        // 此单据为撤销单据
        if (result.get("order_state").equals("7"))
        {
            String cancelreason = result.get("cancelreason") == null ? "" : result.get("cancelreason").toString();
            // 取撤销日志
            parMap.put("order_no", result.get("order_no").toString());
            List<Map<String, Object>> logs = DocumentsDao.getSysLogByOrderNo(parMap);
            if (logs.size() > 0)
            {
                Map<String, Object> log = logs.get(0);
                log.put("approve_state", "-1");
                log.put("approve_content", cancelreason);
                history.add(log);
            }
        }

        // 待审批流程
        List<Map<String, Object>> appNolist = DocumentsDao.getNoApproveInfoById(parMap);
        List<Map<String, Object>> appNolistLast = new ArrayList<Map<String, Object>>();
        if (appNolist.size() > 0 && appNolist != null)
        {
            // 短工号跟名字叠加格式list
            for (Map<String, Object> m : appNolist)
            {
                Map<String, Object> appMap = new HashMap<String, Object>();
                appMap.put("approve_personnel", m.get("approve_personnel_name").toString() + m.get("approve_personnel_no").toString());
                appMap.put("level_flag", Integer.parseInt(m.get("level_flag").toString()));
                appMap.put("level_index", Integer.parseInt(m.get("level_index").toString()));
                appNolistLast.add(appMap);
            }
            appNolist.clear();
            appNolist = listTransformation(appNolistLast);
        }

        // 签收流程
        parMap.put("approve_content", "已签收");
        List<Map<String, Object>> receive_list = DocumentsDao.getApproveShouInfoById117(parMap);

        List<Map<String, Object>> unReceive_list = DocumentsDao.getApproveUnShouInfoById117(parMap);
        List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
        if (unReceive_list != null && unReceive_list.size() > 0)
        {
            for (Map<String, Object> map : unReceive_list)
            {
                map.put("approve_personnel_name", map.get("approve_personnel_name").toString() + map.get("approve_personnel_no"));
            }
            String str = "";
            for (Map<String, Object> map : unReceive_list)
            {
                str += map.get("approve_personnel_name") + "、";
            }
            str = str.substring(0, str.length() - 1);
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("approve_personnel_name", str);
            m.put("approve_personnel_no", "");
            m.put("approve_personnel_dept_name", "");
            m.put("approve_personnel_post_name", "");
            m.put("approve_content", "");
            m.put("approve_timestamp", "");
            li.add(m);
        }

        int state = DocumentsDao.getOrderState(parMap);
        if (state != 4)
        {
            receive_list = li;
        }
        else
        {
            receive_list = DocumentsDao.getApproveShouInfoById117(parMap);
        }
        // 提单人list:submitter_list
        List<Map<String, Object>> submitter_list = DocumentsDao.selSubmitter(parMap);
        result.put("history", history == null ? new ArrayList() : history);
        result.put("check_list", appNolist == null ? new ArrayList() : appNolist);
        result.put("receive_list", receive_list == null ? new ArrayList() : receive_list);
        result.put("submitter_list", submitter_list == null ? new ArrayList<>() : submitter_list);

        result.remove("order_state");
        result.remove("cancelreason");

        return result;
    }
}
