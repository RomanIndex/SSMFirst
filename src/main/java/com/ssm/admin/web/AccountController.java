package com.ssm.admin.web;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.param.AccountVo;
import com.ssm.admin.service.AccountService;
import com.ssm.admin.service.impl.AccountServiceImpl;
import com.ssm.admin.service.CommonService;
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmAccount obj) {
        Result result = accountService.create(obj);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmAccount obj) {
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String id) {
        return null;
    }
}
