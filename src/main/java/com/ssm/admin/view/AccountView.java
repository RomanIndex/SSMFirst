package com.ssm.admin.view;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.entity.SsmRole;
import com.ssm.common.util.DateFormatUtil;

import java.util.List;

public class AccountView extends SsmAccount{
    List<SsmRole> roles;

    private String onlineStatusName;

    private String lastLoginTimeName;

    private String sourceName;

    private String statusName;

    private String createTimeName;

    public String getLastLoginTimeName() {
        this.lastLoginTimeName = DateFormatUtil.formatTime(getLastLoginTime());
        return lastLoginTimeName;
    }

    public void setLastLoginTimeName(String lastLoginTimeName) {
        this.lastLoginTimeName = lastLoginTimeName;
    }

    public String getOnlineStatusName() {
        int i = getOnlineStatus();
        this.onlineStatusName = i == 0 ? "离线" : i == 1 ? "在线" : i == 2 ? "隐身" : "其他";
        return onlineStatusName;
    }

    public void setOnlineStatusName(String onlineStatusName) {
        this.onlineStatusName = onlineStatusName;
    }

    public String getSourceName() {
        int i = getSource();
        this.sourceName = i == 1 ? "朋友推荐" : i == 2 ? "广告" : i == 3 ? "自己搜索" : "其他";
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getStatusName() {
        this.statusName = isStatus() ? "有效" : "无效";
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreateTimeName() {
        this.createTimeName = DateFormatUtil.formatDate(getCreateTime());
        return createTimeName;
    }

    public void setCreateTimeName(String createTimeName) {
        this.createTimeName = createTimeName;
    }

    public List<SsmRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SsmRole> roles) {
        this.roles = roles;
    }
}
