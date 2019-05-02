package com.ssm.admin.service;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;

import java.util.List;

public interface ModuleService extends CommonService<SsmModule, String>{
    Result<?> listByRole(String roleId);

    Result<?> getTopMenu();

    Result<?> getSecondMenu(String parentId);

    Result<?> getBtnMenu(String belongModule);

    Result<?> privilege2menu(List<SsmPrivilege> privileges);

    SsmModule getByUrl(String authUrl);

    List<TreegridView> getTreegridView(String moduleName);
}
