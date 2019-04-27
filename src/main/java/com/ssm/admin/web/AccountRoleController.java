package com.ssm.admin.web;

import com.ssm.admin.service.AccountRoleService;
import com.ssm.base.util.Authority;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(description="用户角色API", value = "用户角色API", tags = {"SSM后台：用户角色接口"})
@CrossOrigin("*")
@RestController
@Authority
@RequestMapping("admin/accountRole")
public class AccountRoleController {
    @Autowired private AccountRoleService accountRoleService;

    @ApiOperation(value = "根据 员工编号 查拥有的 角色", notes = "havedRole：已拥有的；leftRole：尚未拥有的")
    @RequestMapping(value = "/roleScope", method = RequestMethod.GET)
    public Result<?> getByEmpNo(String empNo) {
        return accountRoleService.getRoleScopeByAccount(empNo);
    }

    @ApiOperation(value = "（混合）更新 用户 的 角色", notes = "")
    @RequestMapping(value = "/updateByAccount", method = RequestMethod.POST)
    public Result<?> mixUpdateRole(String empNo, String[] roleIds) {
        List<String> list = roleIds == null ? new ArrayList<>() : Arrays.asList(roleIds);
        return accountRoleService.updateByAccount(empNo, list);
    }
}
