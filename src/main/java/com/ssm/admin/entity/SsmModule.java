package com.ssm.admin.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "SSM_MODULE")
public class SsmModule extends SsmBaseEntity{

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
}