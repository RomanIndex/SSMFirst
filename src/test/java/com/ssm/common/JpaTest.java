package com.ssm.common;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.Ssm;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.entity.jpa.JpaAccount;
import com.ssm.admin.service.JpaAccountService;
import com.ssm.admin.service.SsmAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "file:D:/IDEAtest/SSMMaven/src/main/resources/spring-core-config.xml")
//@ContextConfiguration("classpath:spring-core-config.xml")
/* jpa-config.xml里面有import spring-core，所有这里不能再加入 */
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class JpaTest {
    @Autowired private JpaAccountService jpaAccountService;
    @Autowired private SsmAccountService oracleAccountService;

    @Test
    public void account(){
        JpaAccount jpaAccount = new JpaAccount();
        jpaAccount.setAge(23);
        jpaAccount.setName("张三丰");
        jpaAccount.setCreateTime(Calendar.getInstance().getTime());
        jpaAccountService.add(jpaAccount);
        System.out.println("》》》"+ JSON.toJSONString(jpaAccount));
    }

    @Test
    public void ssm(){
        //DataSourceTypeManager.clearCustom();
        //DataSourceTypeManager.set(DataSourceEnum.aliMysql);//有点问题，切换好像有缓存在，NO，是完全OK了，dao里的配置最后生效
        Ssm ssm = new Ssm();
        ssm.setName("撒的撒的");
        jpaAccountService.ssm(ssm);
        System.out.println("》》》"+ JSON.toJSONString(ssm));
    }

    @Test
    public void oracleGet(){
        /* aop jar包正常就不需要这里 硬处理 了*/
        //DataSourceTypeManager.clearCustom();
        //DataSourceTypeManager.set(DataSourceEnum.wwOracle);
        String empNo = "accountOracle";
        List<SsmAccount> list = (List<SsmAccount>) (oracleAccountService.listAccountByKey().getData());
        System.out.println("》》》"+ JSON.toJSONString(list));
    }
}
