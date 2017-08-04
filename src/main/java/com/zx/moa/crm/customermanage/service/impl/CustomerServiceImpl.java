package com.zx.moa.crm.customermanage.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.moa.crm.customermanage.persist.CustomerDao;
import com.zx.moa.crm.customermanage.service.ICustomerService;
import com.zx.moa.crm.customermanage.vo.CRMCustomerInfo;
import com.zx.moa.util.DateUtil;
import com.zx.platform.syscontext.util.StringUtil;

@Service("customerService")
public  class CustomerServiceImpl implements ICustomerService{

	@Autowired
	private CustomerDao customerDao;

	
	    /**
     * 查询客户信息
     */
	@Override
	public List<Map<String, Object>> getInfoAll(Map<String, Object> map)
	{
		List<Map<String, Object>> resallmap = customerDao.getInfoAll(map);
		int count = customerDao.countInfoAll(map);
		
		Map<String, Object> resmap = new HashMap<String, Object>();

		resmap.put("resallmap", resallmap);
		resmap.put("count", count);
		
		return resallmap;
	}

	    /**
     * 根据客户costomer_id获取客户信息
     * @param costomer_id 
     * @return
     */
	@Override
	public Map<String, Object> getCustomerById(Integer costomer_id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("costomer_id", costomer_id);
		map = customerDao.selectCustomerById(map);
		return map;
	}

    /**
    * @Title: getCustomerByIdV121
    * @Description: TODO(查询客户信息)
    * @param costomer_id
    * @return 
    * @author: suncf
    * @time:2017年2月6日 上午11:50:34
    * @see com.zx.moa.crm.customermanage.service.ICustomerService#getCustomerByIdV121(java.lang.Integer)
    * history:
    * 1、2017年2月6日 suncf 创建方法
    */
    @Override
    public Map<String, Object> getCustomerByIdV121(Integer costomer_id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("costomer_id", costomer_id);
        map = customerDao.selectCustomerByIdV121(map);
        return map;
    }
    
    /**
     * 
     * @Title: verifyMethod
     * @Description: TODO(检验数据是否正确)
     * @param queryInfo
     * @return 
     * @author: 张明建
     * @time:2017年2月6日 下午2:05:42
     * history:
     * 1、2017年2月6日 张明建 创建方法
     */
    public  String verifyMethod(CRMCustomerInfo queryInfo)
    {
        //判断验证操作是修改还是新增操作，如果是修改操作值为false,新增为true
        Boolean flag = (queryInfo.getCostomer_id()!=null&&queryInfo.getCostomer_id()!=0)?false:true;
        
        // 手机号正则验证
        final String CONCATNUMBER_REGULAR ="^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        // 身份证号正则验证
        final String ID_CARD_REGULAR = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9X]$";
        // 手机提示语
        final String[] MESSAGE = new String[] { 
 "手机号码和固定电话不能都为空", "手机号码", "固定电话", "身份证号码", "格式不正确", "已存在，请重新填写", "此客户曾经放弃，无法录入系统", "手机号码和固定电话不能相同", "客户姓名不能为空", "客户名称不可以包含逗号和冒号" };
        String result = "";
        //客户名称不能为空
        if (StringUtils.isBlank(queryInfo.getCustomer_name()))
        {
            return MESSAGE[8];
        }
        
        // 判断固定电话和手机至少输入一个
            if (flag && StringUtils.isBlank(queryInfo.getOther_contact_way())  && StringUtils.isBlank(queryInfo.getContact_number()))
            {
                return MESSAGE[0];
            }
            // 判断固定电话或手机是否是已经放弃客户
        /* if(flag&&StringUtils.isNotBlank(queryInfo.getContact_number())&&isTelAbandonTimeOut(queryInfo,queryInfo.getContact_number())){
              return MESSAGE[6];
         } 
         if(flag&&StringUtils.isNotBlank(queryInfo.getOther_contact_way())&&isTelAbandonTimeOut(queryInfo,queryInfo.getOther_contact_way())){
             return MESSAGE[6];
        }  */
        // 判断手机号码是否正确
        if (StringUtils.isNotBlank(queryInfo.getContact_number()) && !regularVerify(CONCATNUMBER_REGULAR, queryInfo.getContact_number()))
        {
            result = MESSAGE[1];
        }

        // 判断固话是否为空
        if (StringUtil.isNotBlank(queryInfo.getOther_contact_way()))
        {
            if (queryInfo.getOther_contact_way().length() < 6)
            {
                return MESSAGE[2] + MESSAGE[4];
            }
        }

        // 判断身份证号格式是否正确
        if(StringUtils.isNotBlank(queryInfo.getId_card_number()) ){
            if ( !regularVerify(ID_CARD_REGULAR, queryInfo.getId_card_number()))
            {
                result = result.equals("") ? MESSAGE[3] : result + "、" + MESSAGE[3];
            }
        }
        if (!result.equals(""))
        {
            return result + MESSAGE[4];
        }
        
        String Customer_name = queryInfo.getCustomer_name();
        // 验证客户姓名不允许有逗号和冒号
        if (Customer_name.indexOf(",") >= 0 || Customer_name.indexOf("，") >=0 || Customer_name.indexOf(":") >=0 || Customer_name.indexOf("：") >=0)
        {
            return MESSAGE[9];
        }
        // 判断手机号是否重复
        // queryInfo.setNumber(queryInfo.getContact_number());
        // if(customerDao.whetherConcatNumberRepeat(queryInfo)>0){
        // result = result.equals("") ? MESSAGE[1] : result + "、" + MESSAGE[1];
        // }
        // 判断固定电话是否重复
        // queryInfo.setNumber(queryInfo.getOther_contact_way());
        // if(customerDao.whetherConcatNumberRepeat(queryInfo)>0){
        // result = result.equals("") ? MESSAGE[2] : result + "、" + MESSAGE[2];
        // }
         
         
        // 判断身份证号码是否重复
         
        // if(StringUtils.isNotBlank(queryInfo.getId_card_number())&&customerDao.whetherIdCardRepeat(queryInfo)>0){
        // result = result.equals("") ? MESSAGE[3] : result + "、" + MESSAGE[3];
        // }
        // if (!result.equals(""))
        // {
        // return result + MESSAGE[5];
        // }
        
        return "true";
    }

    /**
     * 
     * @Title: isTelAbandonTimeOut
     * @Description: TODO(验证放弃客户)
     * @param queryInfo
     * @param phone
     * @return 
     * @author: 张明建
     * @time:2017年2月8日 下午2:36:21
     * history:
     * 1、2017年2月8日 张明建 创建方法
     */
    public boolean isTelAbandonTimeOut(CRMCustomerInfo queryInfo, String phone)
    {
        Integer personnel_id=queryInfo.getPersonnel_id();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnel_id", personnel_id);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -2);
        map.put("date", DateUtil.date2String(calendar.getTime(), "yyyy-MM-dd"));
        map.put("phone_number", phone);
        String res = customerDao.getTelAbandonTimeOut(map);
        if (StringUtil.isEmpty(res))
        {
            return false;
        }
        else
        {
            if (res.compareTo(DateUtil.date2String(calendar.getTime(), "yyyy-MM-dd")) < 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    
    /**
     * 
     * @Title: regular
     * @Description: TODO(正则表达式验证)
     * @param regular 正则表达式
     * @param value 要验证的值
     * @return 
     * @author: 张明建
     * @time:2017年2月6日 下午2:56:12
     * history:
     * 1、2017年2月6日 张明建 创建方法
     */
    private Boolean regularVerify(String regular, String value)
    {
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(value);
        return m.find();
    }
	
}