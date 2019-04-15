package com.ssm.admin.service;

import com.ssm.admin.entity.SsmModule;
import com.ssm.base.view.Result;

public interface ModuleService extends CommonService<SsmModule, String>{
    Result<?> getTopMenu();

    Result<?> getSecondMenu(String belongId);

    Result<?> getBtnMenu(String parentId);
}
