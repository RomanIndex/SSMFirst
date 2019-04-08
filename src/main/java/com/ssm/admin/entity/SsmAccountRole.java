package com.ssm.admin.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "SSM_ACCOUNT_ROLE")
public class SsmAccountRole extends SsmBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(length = 32)
    private String empNo;

    @Column(length = 32)
    private String roleId;

    //private String desc;//2018-08-13 02:10:xx（数据库都无此字段，不JUnit，在那瞎试？？？）
    private String remark;

    @Column(length = 32)
    private String validDateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValidDateId() {
        return validDateId;
    }

    public void setValidDateId(String validDateId) {
        this.validDateId = validDateId;
    }
}