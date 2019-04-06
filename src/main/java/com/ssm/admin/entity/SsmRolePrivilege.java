package com.ssm.admin.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "SSM_ROLE_PRIVILEGE")
public class SsmRolePrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String priCode;

    private boolean status = true;

    @Column(length = 32)
    private String validDateId;

    @CreationTimestamp
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPriCode() {
        return priCode;
    }

    public void setPriCode(String priCode) {
        this.priCode = priCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getValidDateId() {
        return validDateId;
    }

    public void setValidDateId(String validDateId) {
        this.validDateId = validDateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}