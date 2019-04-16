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

    List<SsmPrivilege> getTicket(String moduleId, OperateEnum type);
}
