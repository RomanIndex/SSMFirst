package com.ssm.admin.service;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;

import java.util.List;

public interface ModuleService extends CommonService<SsmModule, String>{
    Result<?> listMenuByRoleId(String roleId);

    Result<?> getTopMenu();

    Result<?> getSecondMenu(String belongId);

    Result<?> getBtnMenu(String parentId);

    List<TreegridView> getModuleForTreegrid(String roleId);
}
