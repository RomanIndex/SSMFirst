package com.ssm.admin.view;

import java.util.Date;
import java.util.List;

public class RecursionMenuVo {
    private String menuId;

    private String name;

    private String url;

    private String parentId;

    private String icon;

    private short seq;

    private boolean status;

    private Date createTime;

    public short getSeq() {
        return seq;
    }

    public void setSeq(short seq) {
        this.seq = seq;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private List<RecursionMenuVo> childMenus;

	public List<RecursionMenuVo> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<RecursionMenuVo> childMenus) {
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