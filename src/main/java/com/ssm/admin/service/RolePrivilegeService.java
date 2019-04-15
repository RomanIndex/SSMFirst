package com.ssm.admin.service;

import com.ssm.admin.entity.SsmRolePrivilege;

import java.util.List;

public interface RolePrivilegeService extends CommonService<SsmRolePrivilege, Integer>{
    List<SsmRolePrivilege> getByRole();
}
