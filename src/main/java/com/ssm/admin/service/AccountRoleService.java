package com.ssm.admin.service;

import com.ssm.admin.entity.SsmAccountRole;
import com.ssm.base.view.Result;

import java.util.List;

public interface AccountRoleService extends CommonService<SsmAccountRole, Integer>{
    List<SsmAccountRole> getByEmpNo(String empNo);

    Result<?> updateRoleByEmpNo(String empNo, List<String> roleIds);
}
