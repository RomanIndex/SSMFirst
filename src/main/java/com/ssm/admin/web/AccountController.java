package com.ssm.admin.web;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.AccountRoleService;
import com.ssm.admin.service.AccountService;
import com.ssm.admin.service.RoleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description="用户API", value = "用户API", tags = {"SSM后台：用户接口"})
@CrossOrigin("*")
@RestController
@RequestMapping("ssm/admin/account")
public class AccountController {
    @Autowired private AccountService accountService;
    @Autowired private AccountRoleService accountRoleService;
    @Autowired private RoleService roleService;



    @ApiOperation(value = "根据 用户编号 查询基本信息", notes = "")
    @RequestMapping(value = "/{empNo}", method = RequestMethod.GET)
    public Result<?> getVoById(@PathVariable("empNo")String empNo) {
        return accountService.getVoById(empNo);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<?> query(AdminQueryView query) {
        return accountService.query(query);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmAccount obj) {
        return accountService.create(obj);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmAccount obj) {
        return accountService.update(obj);
    }

    @ApiOperation(value = "删除用户", notes = "O(∩_∩)O哈哈~")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empNo", value = "员工编号", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String empNo) {
        return accountService.deleteById(empNo);
    }

}
