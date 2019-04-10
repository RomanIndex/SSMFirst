package com.ssm.admin.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SsmBaseEntity implements Serializable {

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @CreationTimestamp
    private Date createTime;//强烈建议，时间字段，统一用服务器的，不要用数据库的

    @Column(length = 32 )
    private String createUser;

    @UpdateTimestamp
    private Date updateTime;

    @Column(length = 32 )
    private String updateUser;

    private boolean status = true;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
