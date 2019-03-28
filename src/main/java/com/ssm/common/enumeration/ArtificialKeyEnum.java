package com.ssm.common.enumeration;

public enum ArtificialKeyEnum {
    train("TR", 6, "DNY_BASIC", "店内会"),
    clerk("CL", 6, "OTC_CLERK", "店员");

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
