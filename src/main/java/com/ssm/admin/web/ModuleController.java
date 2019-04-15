package com.ssm.admin.web;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description="模块API", value = "模块API", tags = {"SSM后台：模块接口（含左侧菜单）"})
@CrossOrigin("*")
@RestController
@RequestMapping("ssm/admin/module")
public class ModuleController {
    @Autowired private ModuleService moduleService;

    //返回所有静态module，供 树形网络 使用（后续要做出可查询的）
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public Result<?> treeModule(String roleId) {
        List<TreegridView> list = moduleService.getModuleForTreegrid(roleId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("rows", list);

        return Result.success("查询所有静态module成功！", map);
    }

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
