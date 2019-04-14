package com.ssm.admin.service;

import com.ssm.admin.entity.SsmAccountRole;

import java.util.List;

public interface AccountRoleService extends CommonService<SsmAccountRole, Integer>{
    List<SsmAccountRole> getRoleByEmpNo(String empNo);
}
