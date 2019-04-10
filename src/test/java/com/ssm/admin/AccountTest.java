package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.param.AccountVo;
import com.ssm.admin.service.AccountService;
import com.ssm.admin.service.impl.AccountServiceImpl;
import com.ssm.admin.view.AdminQueryView;
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
    @Autowired private AccountServiceImpl accountImpl;

    @Test
    public void getVoById(){
        String empNo = "YH6666";
        AccountVo vo = accountImpl.getVoById(empNo);
        //SsmAccount account = accountService.getById(empNo);
        System.out.println(JSON.toJSON(vo));
    }

    @Test
    public void jpaQuery(){
        AdminQueryView query = new AdminQueryView();
        query.setPageNo(1);
        query.setPageSize(10);
        Result result = accountService.jpaQuery(query);
        System.out.println(JSON.toJSON(result.getData()));
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
