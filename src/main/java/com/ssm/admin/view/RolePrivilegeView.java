package com.ssm.admin.view;

import com.ssm.admin.entity.SsmRolePrivilege;

public class RolePrivilegeView extends SsmRolePrivilege {
    private String moduleId;

    private String operate;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
