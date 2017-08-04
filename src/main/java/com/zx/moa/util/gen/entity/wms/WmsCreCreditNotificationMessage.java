package com.zx.moa.util.gen.entity.wms;

import com.zx.moa.util.mybatis.BaseEntity;

public class WmsCreCreditNotificationMessage implements BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    private int wms_cre_credit_notification_message_id;

    /** 单据的编码 贷款主表中的单据编号 **/
    private String bill_code;

    /** 贷款主表主键 **/
    private int wms_cre_credit_head_id;

    /** 通知消息内容：您有一张单据编号为FD20160415000001的单据，请及时审批 **/
    private String notification_message;

    /** 消息接收人id **/
    private int personnel_id;

    /** 消息接收人短工号 **/
    private String personnel_shortCode;

    /** 消息接收人姓名 **/
    private String personnel_name;

    /** 消息状态 1是未读 0是已读 **/
    private int is_read;

    /** 单据的状态： 贷款主表中当前单据的状态： 用于手机端点击当前消息跳转至哪个列表页面 **/
    private String bill_status;

    /** 创建人 **/
    private int create_user_id;

    /** 创建人姓名 **/
    private String create_user_name;

    /** 创建时间 **/
    private java.sql.Timestamp create_timestamp;

    /** 上次修改人主键 ：就是消息读取人 **/
    private int last_update_user_id;

    /** 上次修改时间：就是消息读取时间 **/
    private java.sql.Timestamp last_update_timestamp;

    /** 数据状态 **/
    private char enable_flag;

    /**对应app别名**/
    private String app_name;

	public int getWms_cre_credit_notification_message_id() {
		return wms_cre_credit_notification_message_id;
	}

	public String getBill_code() {
		return bill_code;
	}

	public int getWms_cre_credit_head_id() {
		return wms_cre_credit_head_id;
	}

	public String getNotification_message() {
		return notification_message;
	}

	public int getPersonnel_id() {
		return personnel_id;
	}

	public String getPersonnel_shortCode() {
		return personnel_shortCode;
	}

	public String getPersonnel_name() {
		return personnel_name;
	}

	public int getIs_read() {
		return is_read;
	}

	public String getBill_status() {
		return bill_status;
	}

	public int getCreate_user_id() {
		return create_user_id;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public java.sql.Timestamp getCreate_timestamp() {
		return create_timestamp;
	}

	public int getLast_update_user_id() {
		return last_update_user_id;
	}

	public java.sql.Timestamp getLast_update_timestamp() {
		return last_update_timestamp;
	}

	public char getEnable_flag() {
		return enable_flag;
	}

	public void setWms_cre_credit_notification_message_id(
			int wms_cre_credit_notification_message_id) {
		this.wms_cre_credit_notification_message_id = wms_cre_credit_notification_message_id;
	}

	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	public void setWms_cre_credit_head_id(int wms_cre_credit_head_id) {
		this.wms_cre_credit_head_id = wms_cre_credit_head_id;
	}

	public void setNotification_message(String notification_message) {
		this.notification_message = notification_message;
	}

	public void setPersonnel_id(int personnel_id) {
		this.personnel_id = personnel_id;
	}

	public void setPersonnel_shortCode(String personnel_shortCode) {
		this.personnel_shortCode = personnel_shortCode;
	}

	public void setPersonnel_name(String personnel_name) {
		this.personnel_name = personnel_name;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public void setBill_status(String bill_status) {
		this.bill_status = bill_status;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public void setCreate_timestamp(java.sql.Timestamp create_timestamp) {
		this.create_timestamp = create_timestamp;
	}

	public void setLast_update_user_id(int last_update_user_id) {
		this.last_update_user_id = last_update_user_id;
	}

	public void setLast_update_timestamp(java.sql.Timestamp last_update_timestamp) {
		this.last_update_timestamp = last_update_timestamp;
	}

	public void setEnable_flag(char enable_flag) {
		this.enable_flag = enable_flag;
	}

    public String getApp_name()
    {
        return app_name;
    }

    public void setApp_name(String app_name)
    {
        this.app_name = app_name;
    }

}