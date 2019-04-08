package com.ssm.admin.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
@Entity
@DynamicUpdate//更新时，不将null值更新到数据库
@DynamicInsert
//@SelectBeforeUpdate(true)//让数据在更新时先查一遍数据库，再返回来进行对比，如果没更改不提交更新语句
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "SSM_ACCOUNT")
public class SsmAccount extends SsmBaseEntity{
    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.ssm.common.service.CustomUUIDGenerator")
    @Column(name = "emp_no", length = 32)
    private String empNo;

    @Column(length = 32)
    private String password;

    private String name;

    @Column(length = 11)
    private String mobile;

    private String email;

    @Column(name = "role_value", length = 32)
    private String roleValues;

    @Column(name = "login_url", length = 32)
    private String loginUrl;

    @Column(name = "online_status")
    private short onlineStatus;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    private String source;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleValues() {
        return roleValues;
    }

    public void setRoleValues(String roleValues) {
        this.roleValues = roleValues;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public short getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(short onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}