package com.ssm.admin.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "SSM_ACCOUNT")
public class SsmAccount {
    private String id;

    @Id
    private String empNo;

    private String password;

    private String name;

    private String headImg;

    private String mobile;

    private String birth;

    private String email;

    private String address;

    private Short roleValues;

    private String loginUrl;

    private Short onlineStatus;

    private Short source;

    private Short status;

    private Date createTime;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getRoleValues() {
        return roleValues;
    }

    public void setRoleValues(Short roleValues) {
        this.roleValues = roleValues;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public Short getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Short onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}