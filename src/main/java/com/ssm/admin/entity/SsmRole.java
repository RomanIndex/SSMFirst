package com.ssm.admin.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "SSM_ROLE")
public class SsmRole extends SsmBaseEntity{
    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.ssm.common.service.CustomUUIDGenerator")
    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String name;

    @JoinColumn(nullable = false, columnDefinition="COMMENT '1����ͨ��ɫ��2����ɫ��'")
    private short type;

    @Column(length = 32)
    private String groupRoleId;

    @Column(columnDefinition="smallint default 0 COMMENT '�����û�������Ȩ������Ա�ȼ��ߵĽ�ɫ'")
    private short level;

    @JoinColumn(columnDefinition="COMMENT '�ⶨ���ڿ��ټ����ɫ��Ȩ�޵��ֶΣ�������'")
    private String weight;

    private String priValues;

    //private String describe;//��Mysql���֮������describe�ֶ�2019-4-4 : 1:24��
    private String remark;

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
}