package com.ssm.admin.entity;

import java.util.Date;
import java.util.List;

public class RecursionMenu {
    private String menuId;

    private String name;

    private String url;

    private String parentId;

    private String icon;

    private Integer sequence;

    private Integer status;

    private Date createTime;
    
    private List<RecursionMenu> childMenus;

    public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RecursionMenu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<RecursionMenu> childMenus) {
		this.childMenus = childMenus;
	}

	public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}