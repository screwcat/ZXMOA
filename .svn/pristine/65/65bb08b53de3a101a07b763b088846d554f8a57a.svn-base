package com.zx.moa.wms.loan.persist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zx.moa.util.gen.entity.wms.WmsCreCreditNotificationMessage;
import com.zx.moa.util.mybatis.MyBatisRepository;
import com.zx.moa.util.mybatis.BaseDao;

@MyBatisRepository
public interface WmsCreCreditNotificationMessageDao extends BaseDao <WmsCreCreditNotificationMessage> {

	Integer getMessage(Map<String,Object> map);
	
	List<Map<String, Object>> searchMessageList(Map<String,Object> map);

	void deleteMessage(@Param("messageid")Integer wms_cre_credit_notification_message_id);
	
	
	/**
     * 
     * @Title: getMessageForFour
     * @Description: TODO(3.2.2        新通知标识)
     * @param userId
     * @return 
     * @author: jiaodelong
	 * @param appname 
     * @time:2017年3月17日 上午9:44:16
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    Integer getMessageForFour(Map<String,Object> map);
    
    /**
     * 
     * @Title: getMessageForFour
     * @Description: TODO(3.2.3     通知中心列表)
     * @param userId
     * @return 
     * @author: jiaodelong
     * @param appname 
     * @time:2017年3月17日 上午9:44:16
     * history:
     * 1、2017年3月17日 jiaodelong 创建方法
     */
    List<Map<String, Object>> searchMessageListForFour(Map<String,Object> map);

}