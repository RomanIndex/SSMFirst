package com.ssm.admin.view;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.entity.SsmRole;

import java.util.List;

public class AccountView extends SsmAccount{
    List<SsmRole> roles;

    public List<SsmRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SsmRole> roles) {
        this.roles = roles;
    }
}
