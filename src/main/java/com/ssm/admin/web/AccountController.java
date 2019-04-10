package com.ssm.admin.web;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.param.AccountVo;
import com.ssm.admin.service.AccountService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("ssm/admin/account")
public class AccountController {
    @Autowired private AccountService accountService;

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

    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String id) {
        return accountService.deleteById(id);
    }
}
