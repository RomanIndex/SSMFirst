package com.ssm.common.enumeration;

public enum ArtificialKeyEnum {
    account("YH", 6, "SSM_ACCOUNT", "用户"),
    role("ROLE", 6, "SSM_ROLE", "角色"),
    module("MOD", 6, "SSM_MODULE", "模块"),
    privilege("PVG", 6, "SSM_PRIVILEGE", "权限"),//module + operateEnum组成
    time("TIME", 6, "SSM_TIME_CONTROL", "时间控制");

    private String prefix;
    private int limit;
    private String table;
    private String remark;

    ArtificialKeyEnum(String prefix, int limit, String table, String remark) {
        this.prefix = prefix;
        this.limit = limit;
        this.table = table;
        this.remark = remark;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
