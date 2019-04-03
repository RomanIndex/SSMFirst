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
    private String id;

    /*private String roleId;

    @Column(columnDefinition="varchar(32) COMMENT '名称'")
    private String name;

    private Integer type;

    private int level;

    private String weight;

    private String priValues;

    private Integer status;*/

    @CreationTimestamp
    private Date createTime;

    //private String describe;//【Mysql天坑之不能用该字段2019-4-4 : 1:24】

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}