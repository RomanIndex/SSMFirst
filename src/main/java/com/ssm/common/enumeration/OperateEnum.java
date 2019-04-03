package com.ssm.common.enumeration;

public enum OperateEnum {
    add("新增"),
    delete("删除"),
    update("修改"),
    select("查询"),
    sort("排序"),
    filter("过滤"),
    upload("上传"),
    download("下载");

    private String name;

    OperateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
