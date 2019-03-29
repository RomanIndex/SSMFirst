package com.ssm.admin.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "SSM_ACCOUNT")
public class SsmAccount {
    private String id;

    private String empNo;

    private String password;

    private String name;

    private Date createTime;

    /*

    private String headImg;

    private String mobile;

    private String birth;

    private String email;

    private String address;

    private Short roleValues;

    private String loginUrl;

    private Short onlineStatus;

    private Short source;

    private Short status;*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}