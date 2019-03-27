package com.ssm.admin.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "SSM_ROLE")
public class SsmRole {
    private String id;

    @Id
    private String roleId;

    private String name;

    private Short type;

    private Short roleLevel;

    private Short weight;

    private Short priValues;

    private Short status;

    private Date createTime;

    private String describe;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Short roleLevel) {
        this.roleLevel = roleLevel;
    }

    public Short getWeight() {
        return weight;
    }

    public void setWeight(Short weight) {
        this.weight = weight;
    }

    public Short getPriValues() {
        return priValues;
    }

    public void setPriValues(Short priValues) {
        this.priValues = priValues;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}