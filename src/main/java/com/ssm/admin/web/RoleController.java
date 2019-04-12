package com.ssm.admin.web;

import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.RoleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description="角色API", value = "角色API", tags = {"SSM后台：角色接口"})
@CrossOrigin("*")
@RestController
@RequestMapping("ssm/admin/role")
public class RoleController {
    @Autowired private RoleService roleService;

    @RequestMapping(value = "/jpaQuery", method = RequestMethod.GET)
    public Result<?> query(AdminQueryView query) {
        return roleService.jpaQuery(query);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmRole obj) {
        return roleService.create(obj);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmRole obj) {
        return roleService.update(obj);
    }

    @ApiOperation(value = "删除角色", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "roleId", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String roleId) {
        return roleService.deleteById(roleId);
    }
}
