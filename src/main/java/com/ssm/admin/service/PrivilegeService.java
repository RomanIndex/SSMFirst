package com.ssm.admin.service;

import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import com.ssm.common.enumeration.OperateEnum;

import java.util.List;

public interface PrivilegeService extends CommonService<SsmPrivilege, String>{
    Result<?> query(AdminQueryView query);

    Result<?> getOperateList();

    Result<?> getPrivilegeByRole(String roleId);

    List<SsmPrivilege> getTicket(OperateEnum type);

    SsmPrivilege getTicket(String moduleId);

    List<SsmPrivilege> listPrivilegeByAccount(String account, OperateEnum show);

    Result<?> checkAuth(String account, String authUrl);
}
