package com.zx.moa.crm.customermanage.vo;


public class CRMCustomerInfo {
	//客户表id
	private Integer costomer_id;
	private String customer_num;
	private Integer personnel_id;
	//客户姓名
	private String customer_name;
	private String contact_number;
	private String other_contact_way;
	private Integer customer_sources;
	private String conduct_stock;
	private Integer achieve_access;
	private String QQ_number;
	private String wx_number;
	private String email_adress;
	private String id_card_number;
	private String customer_gender;
	private String customer_birthday;
	private Integer marriage_states;
	private Integer educational_status;
	private String unit_name;
	private Integer enterprise_nature;
	private Integer company_size;
	private Integer company_vocation;
	private Integer customer_job;
	private Integer monthly_income;
	private Integer convenient_contact_time;
	private Integer hope_contact_type;
	private String domicile_place;
	private String present_address;
	private String customer_interest;
	private String remark;
	private Integer create_id;
    private String create_user;
    //日期
	private String create_timestamp;
	private String last_update_user;
	private String last_update_timestamp;
	private String enable_flag;
	private	Integer all_plan=0;
	private Integer executed_plan=0;
	private Integer unexecuted_plan=0;
	private String product_id;
	private String like_product;
	private String personnel_name;
	private String exec_plan_state;
	private String personnel_shortcode;
	private String important_customer;
	private String lable_name;
	private Integer customer_level;
	private String number;
	
	
	
	/**
     * @return the number
     */
    public String getNumber()
    {
        return number;
    }
    /**
     * @param number the number to set
     */
    public void setNumber(String number)
    {
        this.number = number;
    }
    /**
     * @param all_plan the all_plan to set
     */
    public void setAll_plan(Integer all_plan)
    {
        this.all_plan = all_plan;
    }
    /**
     * @param executed_plan the executed_plan to set
     */
    public void setExecuted_plan(Integer executed_plan)
    {
        this.executed_plan = executed_plan;
    }
    /**
     * @param unexecuted_plan the unexecuted_plan to set
     */
    public void setUnexecuted_plan(Integer unexecuted_plan)
    {
        this.unexecuted_plan = unexecuted_plan;
    }
    public Integer getCustomer_level() {
		return customer_level;
	}
	public void setCustomer_level(Integer customer_level) {
		this.customer_level = customer_level;
	}
	private Integer certificate_type;
	
	
	//客户状态
    private Integer customer_state;
    //个人资材情况
    private String personal_assets;
    //家庭成员情况
    private String family_members;
    //短信发送状态
    private String sms_send_state;
    public String getSms_send_state()
    {
        return sms_send_state;
    }
    public void setSms_send_state(String sms_send_state)
    {
        this.sms_send_state = sms_send_state;
    }
    public Integer getCreate_id()
    {
        return create_id;
    }
    public void setCreate_id(Integer create_id)
    {
        this.create_id = create_id;
    }
	public Integer getCostomer_id() {
		return costomer_id;
	}
	public void setCostomer_id(Integer costomer_id) {
		this.costomer_id = costomer_id;
	}
	public String getCustomer_num() {
		return customer_num;
	}
	public void setCustomer_num(String customer_num) {
		this.customer_num = customer_num;
	}
	public Integer getPersonnel_id() {
		return personnel_id;
	}
	public void setPersonnel_id(Integer personnel_id) {
		this.personnel_id = personnel_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public String getOther_contact_way() {
		return other_contact_way;
	}
	public void setOther_contact_way(String other_contact_way) {
		this.other_contact_way = other_contact_way;
	}
	public Integer getCustomer_sources() {
		return customer_sources;
	}
	public void setCustomer_sources(Integer customer_sources) {
		this.customer_sources = customer_sources;
	}
	public String getConduct_stock() {
		return conduct_stock;
	}
	public void setConduct_stock(String conduct_stock) {
		this.conduct_stock = conduct_stock;
	}
	public Integer getAchieve_access() {
		return achieve_access;
	}
	public void setAchieve_access(Integer achieve_access) {
		this.achieve_access = achieve_access;
	}
	public String getQQ_number() {
		return QQ_number;
	}
	public void setQQ_number(String qQ_number) {
		QQ_number = qQ_number;
	}
	public String getWx_number() {
		return wx_number;
	}
	public void setWx_number(String wx_number) {
		this.wx_number = wx_number;
	}
	public String getEmail_adress() {
		return email_adress;
	}
	public void setEmail_adress(String email_adress) {
		this.email_adress = email_adress;
	}
	public String getId_card_number() {
		return id_card_number;
	}
	public void setId_card_number(String id_card_number) {
		this.id_card_number = id_card_number;
	}
	public String getCustomer_gender() {
		return customer_gender;
	}
	public void setCustomer_gender(String customer_gender) {
		this.customer_gender = customer_gender;
	}
	public String getCustomer_birthday() {
		return customer_birthday;
	}
	public void setCustomer_birthday(String customer_birthday) {
		this.customer_birthday = customer_birthday;
	}
	public Integer getMarriage_states() {
		return marriage_states;
	}
	public void setMarriage_states(Integer marriage_states) {
		this.marriage_states = marriage_states;
	}
	public Integer getEducational_status() {
		return educational_status;
	}
	public void setEducational_status(Integer educational_status) {
		this.educational_status = educational_status;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public Integer getEnterprise_nature() {
		return enterprise_nature;
	}
	public void setEnterprise_nature(Integer enterprise_nature) {
		this.enterprise_nature = enterprise_nature;
	}
	public Integer getCompany_size() {
		return company_size;
	}
	public void setCompany_size(Integer company_size) {
		this.company_size = company_size;
	}
	public Integer getCompany_vocation() {
		return company_vocation;
	}
	public void setCompany_vocation(Integer company_vocation) {
		this.company_vocation = company_vocation;
	}
	public Integer getCustomer_job() {
		return customer_job;
	}
	public void setCustomer_job(Integer customer_job) {
		this.customer_job = customer_job;
	}
	public Integer getMonthly_income() {
		return monthly_income;
	}
	public void setMonthly_income(Integer monthly_income) {
		this.monthly_income = monthly_income;
	}
	public Integer getConvenient_contact_time() {
		return convenient_contact_time;
	}
	public void setConvenient_contact_time(Integer convenient_contact_time) {
		this.convenient_contact_time = convenient_contact_time;
	}
	public Integer getHope_contact_type() {
		return hope_contact_type;
	}
	public void setHope_contact_type(Integer hope_contact_type) {
		this.hope_contact_type = hope_contact_type;
	}
	public String getDomicile_place() {
		return domicile_place;
	}
	public void setDomicile_place(String domicile_place) {
		this.domicile_place = domicile_place;
	}
	public String getPresent_address() {
		return present_address;
	}
	public void setPresent_address(String present_address) {
		this.present_address = present_address;
	}
	public String getCustomer_interest() {
		return customer_interest;
	}
	public void setCustomer_interest(String customer_interest) {
		this.customer_interest = customer_interest;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_timestamp() {
		return create_timestamp;
	}
	public void setCreate_timestamp(String create_timestamp) {
		this.create_timestamp = create_timestamp;
	}
	public String getLast_update_user() {
		return last_update_user;
	}
	public void setLast_update_user(String last_update_user) {
		this.last_update_user = last_update_user;
	}
	public String getLast_update_timestamp() {
		return last_update_timestamp;
	}
	public void setLast_update_timestamp(String last_update_timestamp) {
		this.last_update_timestamp = last_update_timestamp;
	}
	public String getEnable_flag() {
		return enable_flag;
	}
	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}
	public int getAll_plan() {
		return all_plan;
	}
	public void setAll_plan(int all_plan) {
		this.all_plan = all_plan;
	}
	public int getExecuted_plan() {
		return executed_plan;
	}
	public void setExecuted_plan(int executed_plan) {
		this.executed_plan = executed_plan;
	}
	public int getUnexecuted_plan() {
		return unexecuted_plan;
	}
	public void setUnexecuted_plan(int unexecuted_plan) {
		this.unexecuted_plan = unexecuted_plan;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getLike_product() {
		return like_product;
	}
	public void setLike_product(String like_product) {
		this.like_product = like_product;
	}
	public String getPersonal_assets()
    {
        return personal_assets;
    }
    public void setPersonal_assets(String personal_assets)
    {
        this.personal_assets = personal_assets;
    }
    public String getFamily_members()
    {
        return family_members;
    }
    public void setFamily_members(String family_members)
    {
        this.family_members = family_members;
    }
    public Integer getCustomer_state() {
		return customer_state;
	}
	public void setCustomer_state(Integer customer_state) {
		this.customer_state = customer_state;
	}
	public String getPersonnel_name() {
		return personnel_name;
	}
	public void setPersonnel_name(String personnel_name) {
		this.personnel_name = personnel_name;
	}
	public String getExec_plan_state() {
		return exec_plan_state;
	}
	public void setExec_plan_state(String exec_plan_state) {
		this.exec_plan_state = exec_plan_state;
	}
	public String getPersonnel_shortcode() {
		return personnel_shortcode;
	}
	public void setPersonnel_shortcode(String personnel_shortcode) {
		this.personnel_shortcode = personnel_shortcode;
	}
	public String getImportant_customer() {
		return important_customer;
	}
	public void setImportant_customer(String important_customer) {
		this.important_customer = important_customer;
	}
	public String getLable_name() {
		return lable_name;
	}
	public void setLable_name(String lable_name) {
		this.lable_name = lable_name;
	}
	public Integer getCertificate_type() {
		return certificate_type;
	}
	public void setCertificate_type(Integer certificate_type) {
		this.certificate_type = certificate_type;
	}

	
}
