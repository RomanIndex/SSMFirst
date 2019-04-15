package com.ssm.admin.service;

import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;

public interface PrivilegeService extends CommonService<SsmPrivilege, String>{
    Result<?> query(AdminQueryView query);

    Result<?> getOperateList();

    Result<?> getPrivilegeByRole(String roleId);
}
