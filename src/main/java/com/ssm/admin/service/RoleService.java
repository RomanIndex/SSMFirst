package com.ssm.admin.service;

import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;

public interface RoleService extends CommonService<SsmRole, String>{
    Result<?> jpaQuery(AdminQueryView query);

    Result<?> getRoleByAccount(String empNo);

    Result<?> getRoleBranch(String empNo);
}
