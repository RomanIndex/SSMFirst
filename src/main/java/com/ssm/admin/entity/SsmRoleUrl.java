package com.ssm.admin.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "SSM_ROLE_URL")
public class SsmRoleUrl {
    @Id
	private String id;

    private String roleId;

    private String url;

    private Short status;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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