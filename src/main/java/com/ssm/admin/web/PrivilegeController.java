package com.ssm.admin.web;

import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.util.Authority;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description="权限API", value = "权限API", tags = {"SSM后台：权限接口"})
@CrossOrigin("*")
@RestController
@Authority
@RequestMapping("admin/privilege")
public class PrivilegeController {
    @Autowired private PrivilegeService privilegeService;

    @RequestMapping(value = "/operateList", method = RequestMethod.GET)
    public Result<?> getOperateEnumList() {
        return privilegeService.getOperateList();
    }

    /* 根据 角色 查询 拥有的 权限 */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result<?> getByRole(String roleId) {
        return privilegeService.getPrivilegeByRole(roleId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<?> query(AdminQueryView query) {
        return privilegeService.query(query);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmPrivilege obj) {
        return privilegeService.create(obj);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmPrivilege obj) {
        return privilegeService.update(obj);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String code) {
        return privilegeService.deleteById(code);
    }
}
