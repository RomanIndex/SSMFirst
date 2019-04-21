package com.ssm.admin.service;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;

import java.util.List;

public interface ModuleService extends CommonService<SsmModule, String>{
    Result<?> listMenuByRoleId(String roleId);

    Result<?> getTopMenu();

    Result<?> getSecondMenu(String parentId);

    Result<?> getBtnMenu(String belongModule);

    List<TreegridView> getMenuTreegrid(String name);

    Result<?> privilege2menu(List<SsmPrivilege> privileges);
}
