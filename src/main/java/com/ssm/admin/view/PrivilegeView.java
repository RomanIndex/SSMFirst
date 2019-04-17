package com.ssm.admin.view;

import com.ssm.admin.entity.SsmPrivilege;

public class PrivilegeView extends SsmPrivilege {
    private String moduleName;

    private String operateName;//中文名称

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }
}
