package com.zx.moa.ioa.task.vo;

import java.sql.Timestamp;

public class TaskVO {
	
	//任务ID
	private Integer task_id;
	//任务父节点ID
	private Integer task_pid;
	//任务标题
	private String task_title;
	//任务编号
	private String task_num;
	//发布人ID
	private Integer publish_user_id;
	//发布人姓名
	private String publish_user_name;
	//发布人工号
	private String publish_user_code;
	//任务状态：1进行中、2待验收、3结束、4撤销
	private Integer task_status;
	//任务截止时间
	private Timestamp finish_time;
	//任务初始截止时间
	private Timestamp inital_finish_datetime;
	//反馈周期(数据字典CODE)：0不限制、1一天一次、其他具体几天一次
	private String feedback_cycle;
	//提醒时间(数据字典CODE)：-1 不提醒、0 准时、0.5提前30分钟、其他具体小时数
	private String remind_time;
	//接受反馈人ID：根任务为发布人、子任务为创建人
	private Integer accept_feedback_id;
	//接受反馈人姓名：根任务为发布人、子任务为创建人
	private String accept_feedback_name;
	//接受反馈人工号
	private String accept_feedback_code;
	//是否反馈
	private String is_feedback;
	//结束类型:1验收、2提前结束、3撤销、4转移
	private Integer finish_type;
	//任务结束时间
	private Timestamp finish_datetime;
	//任务指派人显示信息
	private String feedback_user_info;
	//创建人ID
	private Integer create_id;
	//创建人姓名工号
	private String create_user;
	//创建时间
	private Timestamp create_datetime;
	//修改人姓名工号
	private String update_user;
	//修改时间
	private Timestamp update_datetiem;
	//是否启用：1启用、0禁用
	private String enable_flag;
	//是否查看子任务
	private Integer is_readonly;
	
	private Integer max_id;
	
	//1 需反馈，2 已指派 3我创建
	private Integer which_tab;
	
	
	/**
     * @return the which_tab
     */
    public Integer getWhich_tab()
    {
        return which_tab;
    }
    /**
     * @param which_tab the which_tab to set
     */
    public void setWhich_tab(Integer which_tab)
    {
        this.which_tab = which_tab;
    }
    public Integer getMax_id() {
		return max_id;
	}
	public void setMax_id(Integer max_id) {
		this.max_id = max_id;
	}
	public Integer getIs_readonly() {
		return is_readonly;
	}
	public void setIs_readonly(Integer is_readonly) {
		this.is_readonly = is_readonly;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public Integer getTask_pid() {
		return task_pid;
	}
	public void setTask_pid(Integer task_pid) {
		this.task_pid = task_pid;
	}
	public String getTask_title() {
		return task_title;
	}
	public void setTask_title(String task_title) {
		this.task_title = task_title;
	}
	public String getTask_num() {
		return task_num;
	}
	public void setTask_num(String task_num) {
		this.task_num = task_num;
	}
	public Integer getPublish_user_id() {
		return publish_user_id;
	}
	public void setPublish_user_id(Integer publish_user_id) {
		this.publish_user_id = publish_user_id;
	}
	public String getPublish_user_name() {
		return publish_user_name;
	}
	public void setPublish_user_name(String publish_user_name) {
		this.publish_user_name = publish_user_name;
	}
	public String getPublish_user_code() {
		return publish_user_code;
	}
	public void setPublish_user_code(String publish_user_code) {
		this.publish_user_code = publish_user_code;
	}
	public Integer getTask_status() {
		return task_status;
	}
	public void setTask_status(Integer task_status) {
		this.task_status = task_status;
	}
	public Timestamp getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Timestamp finish_time) {
		this.finish_time = finish_time;
	}
	public Timestamp getInital_finish_datetime() {
		return inital_finish_datetime;
	}
	public void setInital_finish_datetime(Timestamp inital_finish_datetime) {
		this.inital_finish_datetime = inital_finish_datetime;
	}
	public String getFeedback_cycle() {
		return feedback_cycle;
	}
	public void setFeedback_cycle(String feedback_cycle) {
		this.feedback_cycle = feedback_cycle;
	}
	public String getRemind_time() {
		return remind_time;
	}
	public void setRemind_time(String remind_time) {
		this.remind_time = remind_time;
	}
	public Integer getAccept_feedback_id() {
		return accept_feedback_id;
	}
	public void setAccept_feedback_id(Integer accept_feedback_id) {
		this.accept_feedback_id = accept_feedback_id;
	}
	public String getAccept_feedback_name() {
		return accept_feedback_name;
	}
	public void setAccept_feedback_name(String accept_feedback_name) {
		this.accept_feedback_name = accept_feedback_name;
	}
	public String getAccept_feedback_code() {
		return accept_feedback_code;
	}
	public void setAccept_feedback_code(String accept_feedback_code) {
		this.accept_feedback_code = accept_feedback_code;
	}
	public String getIs_feedback() {
		return is_feedback;
	}
	public void setIs_feedback(String is_feedback) {
		this.is_feedback = is_feedback;
	}
	public Integer getFinish_type() {
		return finish_type;
	}
	public void setFinish_type(Integer finish_type) {
		this.finish_type = finish_type;
	}
	public Timestamp getFinish_datetime() {
		return finish_datetime;
	}
	public void setFinish_datetime(Timestamp finish_datetime) {
		this.finish_datetime = finish_datetime;
	}
	public String getFeedback_user_info() {
		return feedback_user_info;
	}
	public void setFeedback_user_info(String feedback_user_info) {
		this.feedback_user_info = feedback_user_info;
	}
	public Integer getCreate_id() {
		return create_id;
	}
	public void setCreate_id(Integer create_id) {
		this.create_id = create_id;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Timestamp getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(Timestamp create_datetime) {
		this.create_datetime = create_datetime;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Timestamp getUpdate_datetiem() {
		return update_datetiem;
	}
	public void setUpdate_datetiem(Timestamp update_datetiem) {
		this.update_datetiem = update_datetiem;
	}
	public String getEnable_flag() {
		return enable_flag;
	}
	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}

	
	
	
	
}
