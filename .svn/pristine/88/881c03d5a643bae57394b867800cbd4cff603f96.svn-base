package com.zx.moa.wms.loan.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.vo.GridParamBean;


public class WmsCreCreditHeadSearchBeanVO extends GridParamBean{

    private static final long serialVersionUID = 1L;
    
    private Integer wms_cre_credit_head_id;// 单据主键

    private String bill_code;// 单据编号

    private String salesman_name;// 业务员

    private String create_timestamp;// 申请时间
    
    private String create_timestamp1;// 申请时间区间
    
    private String create_timestamp_start;//申请时间开始
    
    private String create_timestamp_end;//申请时间结束

    private String id_card;// 身份证号

    private String flag;// 是否为贷款复核

    private String flag_water;// 流水审批 1

    private String flag_info;// 信息审批 2

    private String flag_phone;// 电审审批 3

    private String flag_certificate;// 征信审批 4

    private String flag_yd;// 验点审批5

    private String customer_name;// 客户姓名

    private String mobile_telephone;// 手机号码

    private String cre_type;// 产品类型

    private String city;// 所属城市

    private String bill_status;// 单据状态
    
    private String dept_city_sel;//所属城市

    private String dept_name_sel;//所属门店
    
    private String many_column_like;
    
    private String period; //时间段(近...个月的单据)
    
    private Integer p_wms_sys_dict_data_id;
    
    private Integer  wms_sys_dict_id;
    
    //小类ID数组
    private Integer wms_sys_dict_data_ids[];

    //大类ID数组
    private Integer p_wms_sys_dict_data_ids[];
    
    //每个小类上传图片数量数组
    private Integer imgCounts[];
    
    private MultipartFile[] imgFile;
    
    private Timestamp now_time_timestamp;
    
    private String data_type_name;
    
    private String data_detail_name;
    
    private String attachment_old_name;
    
    private String attachment_new_name;
    
    private String attachment_address;
    
    private String attachment_type;
    
    private Integer wms_cre_housing_file_info_id;
    
    private String host;//ip
    
    private String port;//端口
    
    //保存类型 1:房贷申请 2:房贷办件 3:房贷补件 4:办件补件
    private Integer save_type;
    
    private Integer user_id;
    
    private String file_info_json;
    
    private String url;
    
    
    //备注(接口文档3.3)
    private String advice;
    
    //1.符合进件标准(默认值) 2.不符合进件标准(接口文档3.3)
    private String pass;
    
    //申请时间(接口文档3.4)
    private String application_time;
    
    //门店(接口文档3.4)
    private String store_value;
    
    //业务组(接口文档3.4)
    private String business_group;
    
    //是否需要分页(接口文档3.4 需要分页:1 不需要分页:0)
    private Integer is_need_paging;
    
    //房产面积(接口文档3.15/3.16/3.17)
    private Object house_building_area;
    
    //还款方式(接口文档3.15/3.16/3.17)
    private String payment_contract_type;
    
    //借款期数(接口文档3.15/3.16/3.17)
    private Object borrow_deadline;
    
    //借款利率(接口文档3.15/3.16/3.17)
    private BigDecimal borrow_interest;
    
    //房产面积(接口文档3.15/3.16/3.17)
    private BigDecimal fees;
    
    //它项费(接口文档3.15/3.16/3.17)
    private BigDecimal it_cost_fee;
   
    //加急费(接口文档3.15/3.16/3.17)
    private BigDecimal expedited_fee;
    
    //日滞纳金(接口文档3.15/3.16/3.17)
    private BigDecimal liquidated_damages;
   
    //起始日期(接口文档3.15/3.16/3.17)
    private String borrow_begin_date;
    
    //终止日期(接口文档3.15/3.16/3.17)
    private String borrow_end_date;
    
    //扣除利息(接口文档3.15/3.16/3.17)
    private BigDecimal deduction_of_interest;
    
    //合同金额(接口文档3.15/3.16/3.17)
    private BigDecimal principal_lower;
    
    //转账金额(接口文档3.15/3.16/3.17)
    private BigDecimal loan_amount;
    
    //公证(接口文档3.15/3.16/3.17)
    private int notary_is_finish;
    
    //它项(接口文档3.15/3.16/3.17)
    private int he_is_finish;
    
    //备注(接口文档3.15/3.16)
    private String remark;
    
    //放款申请附件信息json字符串(接口文档3.16)
    private String wmsFinaCreLoanAppAttListJson;
    
    private String Id_card_list;
    private String bank_list;
    private String transfer_list;
    private String protocol_list;
    private String dept_id;
    private String team_id;
    
    
    
    
    //发送房产评估单基本信息详细信息(接口文档3.40)
    private String total_floor;//总楼层数
    private String house_layer;//所在楼层
    private String is_mortgage;//是否抵押
    private String house_lighting;//采光
    private String housing_category;//房屋类别
    private String decoration_Standard;//装修状况
    private String housing_pattern;//房屋格局
    private String house_usage;//房屋用途
    private String house_occupancy_rate;//入住率
    private String is_active;//成交率
    private String marital_status;//婚姻状况
    private String online_fold;//网上报价
    private String rental_price;//出租价格
    private String house_transaction_price;//成交价
    
    //发送房产评估单房屋信息详细信息(接口文档3.41)
    private String housing_towards;//房屋朝向
    private String residential_manage;//园区管理
    private String facilities;//配套设施
    private String co_habitation;//共同居住
    private String residential_environ;//小区卫生
    private String house_cleanliness;//房屋洁净
    
    private Map<String, Object> resMap;
    
    private Map<String, String> resMap1;
    
    

	//筛选时间例如2016-01
    private String date_info;
    //筛选门店编码
    private String dept_info;
    //查询标识 1：门店 2：公司
    private String is_type;
    
    private List<Map<String, Object>> list;
    
    private List<Map<String, Object>> list1;

    private PmPersonnel personnel;
    
    private String title_name;
    
    private String filter_employees;
    
    private String salesman_info;
    
	private String is_collection;
	
	private String is_save; //保存标识，1保存2补录
    
	private String house_address_more;
	
	private String community_name;
	
	private String house_buy_date;
	
	private String building_age;
	
	/** 单据状态统计时间 **/
    private String statistics_time;
	
	/*
     * 认领状态(MIF接口3.2.6 v2.1.0 )
     * 全部：null 已认领：1 未认领：2
     */
    private String operation_type;

    /**
	 * @return the is_save
	 */
	public String getIs_save() {
		return is_save;
	}

	/**
	 * @param is_save the is_save to set
	 */
	public void setIs_save(String is_save) {
		this.is_save = is_save;
	}

	/**
	 * @return the is_collection
	 */
	public String getIs_collection() {
		return is_collection;
	}

	/**
	 * @param is_collection the is_collection to set
	 */
	public void setIs_collection(String is_collection) {
		this.is_collection = is_collection;
	}
    
    
    
    public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public Integer getWms_cre_credit_head_id() {
        return wms_cre_credit_head_id;
    }

    public void setWms_cre_credit_head_id(Integer wms_cre_credit_head_id) {
        this.wms_cre_credit_head_id = wms_cre_credit_head_id;
    }

    public String getBill_code() {
        return bill_code;
    }

    public void setBill_code(String bill_code) {
        this.bill_code = bill_code;
    }

    public String getSalesman_name() {
        return salesman_name;
    }

    public void setSalesman_name(String salesman_name) {
        this.salesman_name = salesman_name;
    }

    public String getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(String create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public String getCreate_timestamp1() {
        return create_timestamp1;
    }

    public void setCreate_timestamp1(String create_timestamp1) {
        this.create_timestamp1 = create_timestamp1;
    }

    public String getCreate_timestamp_start() {
        return create_timestamp_start;
    }

    public void setCreate_timestamp_start(String create_timestamp_start) {
        this.create_timestamp_start = create_timestamp_start;
    }

    public String getCreate_timestamp_end() {
        return create_timestamp_end;
    }

    public void setCreate_timestamp_end(String create_timestamp_end) {
        this.create_timestamp_end = create_timestamp_end;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag_water() {
        return flag_water;
    }

    public void setFlag_water(String flag_water) {
        this.flag_water = flag_water;
    }

    public String getFlag_info() {
        return flag_info;
    }

    public void setFlag_info(String flag_info) {
        this.flag_info = flag_info;
    }

    public String getFlag_phone() {
        return flag_phone;
    }

    public void setFlag_phone(String flag_phone) {
        this.flag_phone = flag_phone;
    }

    public String getFlag_certificate() {
        return flag_certificate;
    }

    public void setFlag_certificate(String flag_certificate) {
        this.flag_certificate = flag_certificate;
    }

    public String getFlag_yd() {
        return flag_yd;
    }

    public void setFlag_yd(String flag_yd) {
        this.flag_yd = flag_yd;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMobile_telephone() {
        return mobile_telephone;
    }

    public void setMobile_telephone(String mobile_telephone) {
        this.mobile_telephone = mobile_telephone;
    }

    public String getCre_type() {
        return cre_type;
    }

    public void setCre_type(String cre_type) {
        this.cre_type = cre_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBill_status() {
        return bill_status;
    }

    public void setBill_status(String bill_status) {
        this.bill_status = bill_status;
    }

    public String getDept_city_sel() {
        return dept_city_sel;
    }

    public void setDept_city_sel(String dept_city_sel) {
        this.dept_city_sel = dept_city_sel;
    }

    public String getDept_name_sel() {
        return dept_name_sel;
    }

    public void setDept_name_sel(String dept_name_sel) {
        this.dept_name_sel = dept_name_sel;
    }

    public String getMany_column_like() {
        return many_column_like;
    }

    public void setMany_column_like(String many_column_like) {
        this.many_column_like = many_column_like;
    }

    public Integer[] getWms_sys_dict_data_ids() {
        return wms_sys_dict_data_ids;
    }

    public void setWms_sys_dict_data_ids(Integer[] wms_sys_dict_data_ids) {
        this.wms_sys_dict_data_ids = wms_sys_dict_data_ids;
    }

    public Integer[] getP_wms_sys_dict_data_ids() {
        return p_wms_sys_dict_data_ids;
    }

    public void setP_wms_sys_dict_data_ids(Integer[] p_wms_sys_dict_data_ids) {
        this.p_wms_sys_dict_data_ids = p_wms_sys_dict_data_ids;
    }

    public Integer[] getImgCounts() {
        return imgCounts;
    }

    public void setImgCounts(Integer[] imgCounts) {
        this.imgCounts = imgCounts;
    }

    public MultipartFile[] getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile[] imgFile) {
        this.imgFile = imgFile;
    }

    public Integer getSave_type() {
        return save_type;
    }

    public void setSave_type(Integer save_type) {
        this.save_type = save_type;
    }

    public Timestamp getNow_time_timestamp() {
        return now_time_timestamp;
    }

    public void setNow_time_timestamp(Timestamp now_time_timestamp) {
        this.now_time_timestamp = now_time_timestamp;
    }

    public String getData_type_name() {
        return data_type_name;
    }

    public void setData_type_name(String data_type_name) {
        this.data_type_name = data_type_name;
    }

    public String getData_detail_name() {
        return data_detail_name;
    }

    public void setData_detail_name(String data_detail_name) {
        this.data_detail_name = data_detail_name;
    }

    public String getAttachment_old_name() {
        return attachment_old_name;
    }

    public void setAttachment_old_name(String attachment_old_name) {
        this.attachment_old_name = attachment_old_name;
    }

    public String getAttachment_new_name() {
        return attachment_new_name;
    }

    public void setAttachment_new_name(String attachment_new_name) {
        this.attachment_new_name = attachment_new_name;
    }

    public String getAttachment_address() {
        return attachment_address;
    }

    public void setAttachment_address(String attachment_address) {
        this.attachment_address = attachment_address;
    }

    public String getAttachment_type() {
        return attachment_type;
    }

    public void setAttachment_type(String attachment_type) {
        this.attachment_type = attachment_type;
    }

    public Integer getWms_cre_housing_file_info_id() {
        return wms_cre_housing_file_info_id;
    }

    public void setWms_cre_housing_file_info_id(Integer wms_cre_housing_file_info_id) {
        this.wms_cre_housing_file_info_id = wms_cre_housing_file_info_id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFile_info_json() {
        return file_info_json;
    }

    public void setFile_info_json(String file_info_json) {
        this.file_info_json = file_info_json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getApplication_time() {
        return application_time;
    }

    public void setApplication_time(String application_time) {
        this.application_time = application_time;
    }

    public String getStore_value() {
        return store_value;
    }

    public void setStore_value(String store_value) {
        this.store_value = store_value;
    }

    public String getBusiness_group() {
        return business_group;
    }

    public void setBusiness_group(String business_group) {
        this.business_group = business_group;
    }

    public Integer getIs_need_paging() {
        return is_need_paging;
    }

    public void setIs_need_paging(Integer is_need_paging) {
        this.is_need_paging = is_need_paging;
    }

    public Object getHouse_building_area() {
        return house_building_area;
    }

    public void setHouse_building_area(Object house_building_area) {
        this.house_building_area = house_building_area;
    }

    public String getPayment_contract_type() {
        return payment_contract_type;
    }

    public void setPayment_contract_type(String payment_contract_type) {
        this.payment_contract_type = payment_contract_type;
    }

    public Object getBorrow_deadline() {
        return borrow_deadline;
    }

    public void setBorrow_deadline(Object borrow_deadline) {
        this.borrow_deadline = borrow_deadline;
    }
    
    public BigDecimal getBorrow_interest() {
        return borrow_interest;
    }

    public void setBorrow_interest(BigDecimal borrow_interest) {
        this.borrow_interest = borrow_interest;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public BigDecimal getIt_cost_fee() {
        return it_cost_fee;
    }

    public void setIt_cost_fee(BigDecimal it_cost_fee) {
        this.it_cost_fee = it_cost_fee;
    }

    public BigDecimal getExpedited_fee() {
        return expedited_fee;
    }

    public void setExpedited_fee(BigDecimal expedited_fee) {
        this.expedited_fee = expedited_fee;
    }

    public BigDecimal getLiquidated_damages() {
        return liquidated_damages;
    }

    public void setLiquidated_damages(BigDecimal liquidated_damages) {
        this.liquidated_damages = liquidated_damages;
    }

    public String getBorrow_begin_date() {
        return borrow_begin_date;
    }

    public void setBorrow_begin_date(String borrow_begin_date) {
        this.borrow_begin_date = borrow_begin_date;
    }

    public String getBorrow_end_date() {
        return borrow_end_date;
    }

    public void setBorrow_end_date(String borrow_end_date) {
        this.borrow_end_date = borrow_end_date;
    }

    public BigDecimal getDeduction_of_interest() {
        return deduction_of_interest;
    }

    public void setDeduction_of_interest(BigDecimal deduction_of_interest) {
        this.deduction_of_interest = deduction_of_interest;
    }

    public BigDecimal getPrincipal_lower() {
        return principal_lower;
    }

    public void setPrincipal_lower(BigDecimal principal_lower) {
        this.principal_lower = principal_lower;
    }

    public BigDecimal getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(BigDecimal loan_amount) {
        this.loan_amount = loan_amount;
    }

    public int getNotary_is_finish() {
        return notary_is_finish;
    }

    public void setNotary_is_finish(int notary_is_finish) {
        this.notary_is_finish = notary_is_finish;
    }

    public int getHe_is_finish() {
        return he_is_finish;
    }

    public void setHe_is_finish(int he_is_finish) {
        this.he_is_finish = he_is_finish;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWmsFinaCreLoanAppAttListJson() {
        return wmsFinaCreLoanAppAttListJson;
    }

    public void setWmsFinaCreLoanAppAttListJson(String wmsFinaCreLoanAppAttListJson) {
        this.wmsFinaCreLoanAppAttListJson = wmsFinaCreLoanAppAttListJson;
    }

    public String getId_card_list() {
        return Id_card_list;
    }

    public void setId_card_list(String id_card_list) {
        Id_card_list = id_card_list;
    }

    public String getBank_list() {
        return bank_list;
    }

    public void setBank_list(String bank_list) {
        this.bank_list = bank_list;
    }

    public String getTransfer_list() {
        return transfer_list;
    }

    public void setTransfer_list(String transfer_list) {
        this.transfer_list = transfer_list;
    }

    public String getProtocol_list() {
        return protocol_list;
    }

    public void setProtocol_list(String protocol_list) {
        this.protocol_list = protocol_list;
    }

	public Map<String, Object> getResMap() {
		return resMap;
	}

	public void setResMap(Map<String, Object> resMap) {
		this.resMap = resMap;
	}

	public String getTotal_floor() {
		return total_floor;
	}

	public void setTotal_floor(String total_floor) {
		this.total_floor = total_floor;
	}

	public String getHouse_layer() {
		return house_layer;
	}

	public void setHouse_layer(String house_layer) {
		this.house_layer = house_layer;
	}

	public String getIs_mortgage() {
		return is_mortgage;
	}

	public void setIs_mortgage(String is_mortgage) {
		this.is_mortgage = is_mortgage;
	}

	public String getHouse_lighting() {
		return house_lighting;
	}

	public void setHouse_lighting(String house_lighting) {
		this.house_lighting = house_lighting;
	}

	public String getHousing_category() {
		return housing_category;
	}

	public void setHousing_category(String housing_category) {
		this.housing_category = housing_category;
	}

	public String getDecoration_Standard() {
		return decoration_Standard;
	}

	public void setDecoration_Standard(String decoration_Standard) {
		this.decoration_Standard = decoration_Standard;
	}

	public String getHousing_pattern() {
		return housing_pattern;
	}

	public void setHousing_pattern(String housing_pattern) {
		this.housing_pattern = housing_pattern;
	}

	public String getHouse_usage() {
		return house_usage;
	}

	public void setHouse_usage(String house_usage) {
		this.house_usage = house_usage;
	}

	public String getHouse_occupancy_rate() {
		return house_occupancy_rate;
	}

	public void setHouse_occupancy_rate(String house_occupancy_rate) {
		this.house_occupancy_rate = house_occupancy_rate;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}

	public String getOnline_fold() {
		return online_fold;
	}

	public void setOnline_fold(String online_fold) {
		this.online_fold = online_fold;
	}

	public String getRental_price() {
		return rental_price;
	}

	public void setRental_price(String rental_price) {
		this.rental_price = rental_price;
	}

	public String getHouse_transaction_price() {
		return house_transaction_price;
	}

	public void setHouse_transaction_price(String house_transaction_price) {
		this.house_transaction_price = house_transaction_price;
	}

	public String getHousing_towards() {
		return housing_towards;
	}

	public void setHousing_towards(String housing_towards) {
		this.housing_towards = housing_towards;
	}

	public String getResidential_manage() {
		return residential_manage;
	}

	public void setResidential_manage(String residential_manage) {
		this.residential_manage = residential_manage;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getCo_habitation() {
		return co_habitation;
	}

	public void setCo_habitation(String co_habitation) {
		this.co_habitation = co_habitation;
	}

	public String getResidential_environ() {
		return residential_environ;
	}

	public void setResidential_environ(String residential_environ) {
		this.residential_environ = residential_environ;
	}

	public String getHouse_cleanliness() {
		return house_cleanliness;
	}

	public void setHouse_cleanliness(String house_cleanliness) {
		this.house_cleanliness = house_cleanliness;
	}

	public Map<String, String> getResMap1() {
		return resMap1;
	}

	public void setResMap1(Map<String, String> resMap1) {
		this.resMap1 = resMap1;
	}

    public String getDate_info()
    {
        return date_info;
    }

    public void setDate_info(String date_info)
    {
        this.date_info = date_info;
    }

    public String getDept_info()
    {
        return dept_info;
    }

    public void setDept_info(String dept_info)
    {
        this.dept_info = dept_info;
    }

    public String getIs_type()
    {
        return is_type;
    }

    public void setIs_type(String is_type)
    {
        this.is_type = is_type;
    }

    public List<Map<String, Object>> getList()
    {
        return list;
    }

    public void setList(List<Map<String, Object>> list)
    {
        this.list = list;
    }

    public PmPersonnel getPersonnel()
    {
        return personnel;
    }

    public void setPersonnel(PmPersonnel personnel)
    {
        this.personnel = personnel;
    }

    public List<Map<String, Object>> getList1()
    {
        return list1;
    }

    public void setList1(List<Map<String, Object>> list1)
    {
        this.list1 = list1;
    }

    public String getTitle_name()
    {
        return title_name;
    }

    public void setTitle_name(String title_name)
    {
        this.title_name = title_name;
    }

    public String getFilter_employees()
    {
        return filter_employees;
    }

    public String getSalesman_info()
    {
        return salesman_info;
    }

    public void setSalesman_info(String salesman_info)
    {
        this.salesman_info = salesman_info;
    }

    public void setFilter_employees(String filter_employees)
    {
        this.filter_employees = filter_employees;
    }

    public String getHouse_address_more()
    {
        return house_address_more;
    }

    public void setHouse_address_more(String house_address_more)
    {
        this.house_address_more = house_address_more;
    }

    public String getCommunity_name()
    {
        return community_name;
    }

    public void setCommunity_name(String community_name)
    {
        this.community_name = community_name;
    }

    public String getOperation_type()
    {
        return operation_type;
    }

    public void setOperation_type(String operation_type)
    {
        this.operation_type = operation_type;
    }

    public String getHouse_buy_date()
    {
        return house_buy_date;
    }

    public void setHouse_buy_date(String house_buy_date)
    {
        this.house_buy_date = house_buy_date;
    }

    public String getBuilding_age()
    {
        return building_age;
    }

    public void setBuilding_age(String building_age)
    {
        this.building_age = building_age;
    }

    /**
     * @return the statistics_time
     */
    public String getStatistics_time()
    {
        return statistics_time;
    }

    /**
     * @param statistics_time the statistics_time to set
     */
    public void setStatistics_time(String statistics_time)
    {
        this.statistics_time = statistics_time;
    }

    

}
