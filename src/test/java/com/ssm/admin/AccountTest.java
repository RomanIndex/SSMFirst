package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.AccountService;
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
        SsmAccount account = accountService.findBy("aaa");
        System.out.println(JSON.toJSON(account));
    }

    @Test
    public void add(){
        SsmAccount account = new SsmAccount();
        account.setEmpNo("123456");
        account.setName("张三");
        SsmAccount act = accountService.save(account);
        System.out.println(JSON.toJSON(act));
    }
}
