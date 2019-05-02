package com.ssm.admin.service;

import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import com.ssm.common.enumeration.OperateEnum;

import java.util.List;

public interface PrivilegeService extends CommonService<SsmPrivilege, String>{
    Result<?> query(AdminQueryView query);

    Result<?> getOperateList();

    Result<?> checkAuth(String account, String authUrl);

    /* 单表基础性查询 */
    SsmPrivilege getByModule(String moduleId);

    List<SsmPrivilege> listByRole(String roleId);

    List<SsmPrivilege> listByRoleAndOperate(String roleId, OperateEnum operateEnum);

    List<SsmPrivilege> listByOperate(OperateEnum operateEnum);

    List<SsmPrivilege> listByAccount(String account);

    List<SsmPrivilege> listByAccountAndOperate(String account, OperateEnum operateEnum);

}
