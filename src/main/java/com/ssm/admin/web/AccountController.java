package com.ssm.admin.web;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.param.AccountVo;
import com.ssm.admin.service.AccountService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(description="用户API", value = "用户API", tags = {"SSM后台：用户接口"})
@CrossOrigin("*")
@Controller
@RequestMapping("ssm/admin/account")
public class AccountController {
    @Autowired private AccountService accountService;

    @ApiOperation(value = "根据 用户编号 查询基本信息", notes = "")
    @ResponseBody
    @RequestMapping(value = "/{empNo}", method = RequestMethod.GET)
    public AccountVo getVoById(@PathVariable("empNo")String empNo) {
        return accountService.getVoById(empNo);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<?> query(AdminQueryView query) {
        return accountService.query(query);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmAccount obj) {
        return accountService.create(obj);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmAccount obj) {
        return accountService.update(obj);
    }

    @ApiOperation(value = "删除用户", notes = "O(∩_∩)O哈哈~")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工编号", required = true, dataType = "String", paramType = "query"),
    })
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String id) {
        return accountService.deleteById(id);
    }
}
