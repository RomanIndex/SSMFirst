package com.ssm.admin.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SSM_MODULE")
public class SsmModule {
    private boolean status = true;

    @JoinColumn(nullable = false, columnDefinition="COMMENT '1：模块；2：菜单；3：按钮'")
    private short type;

    @JoinColumn(columnDefinition="COMMENT '所属模块，也是顶级菜单，type和子菜单一定不相同'")
    private String belongModule;

    @JoinColumn(columnDefinition="COMMENT '父级菜单，type和子菜单一定相同'")
    private String parentId;

    private short seq;

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.ssm.common.service.CustomUUIDGenerator")
    @Column(length = 32)
    private String moduleId;

    @Column(length = 32, nullable = false)
    private String name;

    private String style;

    @Column(nullable = false)
    private String url;

    private String image;

    private String remark;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @CreationTimestamp//创建时自动生成时间
    private Date createTime;

    private String createUser;

    @UpdateTimestamp//数据更新时主动更新时间
    private Date updateTime;

    private String updateUser;

    public String getBelongModule() {
        return belongModule;
    }

    public void setBelongModule(String belongModule) {
        this.belongModule = belongModule;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public short getSeq() {
        return seq;
    }

    public void setSeq(short seq) {
        this.seq = seq;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
}