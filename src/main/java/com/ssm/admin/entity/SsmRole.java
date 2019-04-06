package com.ssm.admin.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "SSM_ROLE")
public class SsmRole {
    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.ssm.common.service.CustomUUIDGenerator")
    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String name;

    @JoinColumn(nullable = false, columnDefinition="COMMENT '1：普通角色，2：角色组'")
    private short type;

    @Column(length = 32)
    private String groupRoleId;

    @Column(columnDefinition="smallint default 0 COMMENT '级别，用户不能授权比他会员等级高的角色'")
    private short level;

    @JoinColumn(columnDefinition="COMMENT '拟定用于快速计算角色下权限的字段，待开发'")
    private String weight;

    private String priValues;

    //private String describe;//【Mysql天坑之不能用describe字段2019-4-4 : 1:24】
    private String remark;

    private boolean status = true;

    @CreationTimestamp
    private Date createTime;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPriValues() {
        return priValues;
    }

    public void setPriValues(String priValues) {
        this.priValues = priValues;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getGroupRoleId() {
        return groupRoleId;
    }

    public void setGroupRoleId(String groupRoleId) {
        this.groupRoleId = groupRoleId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}