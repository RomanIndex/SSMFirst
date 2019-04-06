package com.ssm.admin.entity;

import com.ssm.common.enumeration.OperateEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "SSM_PRIVILEGE")
public class SsmPrivilege {
    private boolean status = true;

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.ssm.common.service.CustomUUIDGenerator")
    private String code;

    private String name;

    @JoinColumn(columnDefinition="COMMENT '不能给用户授予高于其会员等级的权限'")
    private short level;

    @Column(length = 32)
    private String moduleId;

    private String operateEnumName;

    @Column(length = 32)
    private String validDateId;

    @CreationTimestamp
    private Date createTime;

    public String getOperateEnumName() {
        return operateEnumName;
    }

    public void setOperateEnumName(String operateEnumName) {
        this.operateEnumName = operateEnumName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}