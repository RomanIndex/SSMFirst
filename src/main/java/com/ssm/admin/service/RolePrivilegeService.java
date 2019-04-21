package com.ssm.admin.service;

import com.ssm.admin.entity.SsmRolePrivilege;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;

import java.util.List;

public interface RolePrivilegeService extends CommonService<SsmRolePrivilege, Integer>{
    List<SsmRolePrivilege> getByRole(String roleId);

    Result<?> mixUpdatePrivilege(String roleId, List<String> codes);

    List<TreegridView> getMenuTreegrid(String roleId);

    List<SsmRolePrivilege> listDistinctByRoleIds(List<String> roleIds);
}
