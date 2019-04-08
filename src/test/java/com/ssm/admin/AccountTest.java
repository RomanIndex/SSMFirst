package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.AccountService;
import com.ssm.base.view.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class AccountTest {
    @Autowired private AccountService accountService;

    @Test
    public void findBy(){
        SsmAccount account = accountService.get("YH34661");
        System.out.println(JSON.toJSON(account));
    }

    @Test
    public void add(){
        SsmAccount account = new SsmAccount();
        //account.setEmpNo("123456");
        account.setName("张三"+ (int)(Math.random() * 10000));
        Result result = accountService.create(account);
        System.out.println(JSON.toJSON(result));
    }

    @Test
    public void update(){
        SsmAccount account = new SsmAccount();
        account.setEmpNo("YH550816");
        account.setName("张三"+ (int)(Math.random() * 10000));
        //account.setEmail("^_^@qq.com");
        //account.setMobile("120");
        Result result = accountService.update(account);
        System.out.println(JSON.toJSON(result));
    }
}
