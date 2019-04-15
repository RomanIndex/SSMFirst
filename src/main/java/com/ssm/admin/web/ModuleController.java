package com.ssm.admin.web;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description="模块API", value = "模块API", tags = {"SSM后台：模块接口（含左侧菜单）"})
@CrossOrigin("*")
@RestController
@RequestMapping("ssm/admin/module")
public class ModuleController {
    @Autowired private ModuleService moduleService;

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public Result<?> getTopMenu() {
        return moduleService.getTopMenu();
    }

    @RequestMapping(value = "/second", method = RequestMethod.GET)
    public Result<?> getSecondMenu(String belongId) {
        return moduleService.getSecondMenu(belongId);
    }

    @RequestMapping(value = "/btn", method = RequestMethod.GET)
    public Result<?> getBtn(String parentId) {
        return moduleService.getBtnMenu(parentId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmModule obj) {
        return moduleService.create(obj);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmModule obj) {
        return moduleService.update(obj);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String roleId) {
        return moduleService.deleteById(roleId);
    }
}
