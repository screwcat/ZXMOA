package com.zx.moa.util.gen.entity.wms;

import java.sql.Timestamp;

import com.zx.moa.util.mybatis.BaseEntity;

public class SysDept implements BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 部门ID **/
    private Integer dept_id;

    /** 部门编码 **/
    private String dept_code;

    /** 部门名称 **/
    private String dept_name;

    /** 部门级别 **/
    private Integer dept_level;

    /** 父部门ID **/
    private Integer dept_pid;

    /** 部门负责人ID **/
    private Integer dept_personId;

    /** 部门负责人 **/
    private String dept_person;

    /** 是否虚拟（Y/N） **/
    private String dept_virtual;

    /** 通讯地址 **/
    private String dept_address;

    /** 邮政编码 **/
    private String dept_postalCode;

    /** 部门电话 **/
    private String dept_tel;

    /** 传真 **/
    private String dept_fax;

    /** 邮箱 **/
    private String dept_email;

    /** 部门职责 **/
    private String dept_duty;

    /** 部门创建时间 **/
    private Timestamp dept_createTime;

    /** 是否是叶子(Y:是/N:不是) **/
    private String dept_leaf;

    /** 备注 **/
    private String dept_remark;

    /** 关联组织ID **/
    private Integer dept_organizationId;

    /** 创建人 **/
    private Integer create_id;

    /** 创建人姓名 **/
    private String create_user;

    /** 创建时间 **/
    private Timestamp create_timestamp;

    /** 修改人 **/
    private String last_update_user;

    /** 修改时间 **/
    private Timestamp last_update_timestamp;

    /** 标记该记录的启用状态1：正常，0：禁用（已删除） **/
    private String enable_flag;

    /**  **/
    private String dept_level_coding;

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_level(Integer dept_level) {
        this.dept_level = dept_level;
    }

    public Integer getDept_level() {
        return dept_level;
    }

    public void setDept_pid(Integer dept_pid) {
        this.dept_pid = dept_pid;
    }

    public Integer getDept_pid() {
        return dept_pid;
    }

    public void setDept_personId(Integer dept_personId) {
        this.dept_personId = dept_personId;
    }

    public Integer getDept_personId() {
        return dept_personId;
    }

    public void setDept_person(String dept_person) {
        this.dept_person = dept_person;
    }

    public String getDept_person() {
        return dept_person;
    }

    public void setDept_virtual(String dept_virtual) {
        this.dept_virtual = dept_virtual;
    }

    public String getDept_virtual() {
        return dept_virtual;
    }

    public void setDept_address(String dept_address) {
        this.dept_address = dept_address;
    }

    public String getDept_address() {
        return dept_address;
    }

    public void setDept_postalCode(String dept_postalCode) {
        this.dept_postalCode = dept_postalCode;
    }

    public String getDept_postalCode() {
        return dept_postalCode;
    }

    public void setDept_tel(String dept_tel) {
        this.dept_tel = dept_tel;
    }

    public String getDept_tel() {
        return dept_tel;
    }

    public void setDept_fax(String dept_fax) {
        this.dept_fax = dept_fax;
    }

    public String getDept_fax() {
        return dept_fax;
    }

    public void setDept_email(String dept_email) {
        this.dept_email = dept_email;
    }

    public String getDept_email() {
        return dept_email;
    }

    public void setDept_duty(String dept_duty) {
        this.dept_duty = dept_duty;
    }

    public String getDept_duty() {
        return dept_duty;
    }

    public void setDept_createTime(Timestamp dept_createTime) {
        this.dept_createTime = dept_createTime;
    }

    public Timestamp getDept_createTime() {
        return dept_createTime;
    }

    public void setDept_leaf(String dept_leaf) {
        this.dept_leaf = dept_leaf;
    }

    public String getDept_leaf() {
        return dept_leaf;
    }

    public void setDept_remark(String dept_remark) {
        this.dept_remark = dept_remark;
    }

    public String getDept_remark() {
        return dept_remark;
    }

    public void setDept_organizationId(Integer dept_organizationId) {
        this.dept_organizationId = dept_organizationId;
    }

    public Integer getDept_organizationId() {
        return dept_organizationId;
    }

    public void setCreate_id(Integer create_id) {
        this.create_id = create_id;
    }

    public Integer getCreate_id() {
        return create_id;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_timestamp(Timestamp create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public Timestamp getCreate_timestamp() {
        return create_timestamp;
    }

    public void setLast_update_user(String last_update_user) {
        this.last_update_user = last_update_user;
    }

    public String getLast_update_user() {
        return last_update_user;
    }

    public void setLast_update_timestamp(Timestamp last_update_timestamp) {
        this.last_update_timestamp = last_update_timestamp;
    }

    public Timestamp getLast_update_timestamp() {
        return last_update_timestamp;
    }

    public void setEnable_flag(String enable_flag) {
        this.enable_flag = enable_flag;
    }

    public String getEnable_flag() {
        return enable_flag;
    }

    public void setDept_level_coding(String dept_level_coding) {
        this.dept_level_coding = dept_level_coding;
    }

    public String getDept_level_coding() {
        return dept_level_coding;
    }

}