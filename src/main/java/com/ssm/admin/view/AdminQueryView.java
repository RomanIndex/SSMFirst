package com.ssm.admin.view;

import com.ssm.base.view.QueryModel;

public class AdminQueryView extends QueryModel {
    private String moduleKeyword;

    private String accountKeyword;

    private String roleKeyword;

    private String privilegeKeyword;

    private String operateEnum;

    public String getOperateEnum() {
        return operateEnum;
    }

    public void setOperateEnum(String operateEnum) {
        this.operateEnum = operateEnum;
    }

    public String getModuleKeyword() {
        return moduleKeyword;
    }

    public void setModuleKeyword(String moduleKeyword) {
        this.moduleKeyword = moduleKeyword;
    }

    public String getAccountKeyword() {
        return accountKeyword;
    }

    public void setAccountKeyword(String accountKeyword) {
        this.accountKeyword = accountKeyword;
    }

    public String getRoleKeyword() {
        return roleKeyword;
    }

    public void setRoleKeyword(String roleKeyword) {
        this.roleKeyword = roleKeyword;
    }

    public String getPrivilegeKeyword() {
        return privilegeKeyword;
    }

    public void setPrivilegeKeyword(String privilegeKeyword) {
        this.privilegeKeyword = privilegeKeyword;
    }
}
