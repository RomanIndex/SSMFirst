package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.SsmRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
//@ContextConfiguration(locations = "file:D:/IDEAtest/SSMMaven/src/main/resources/spring-core-config.xml")
//@ContextConfiguration("classpath:spring-core-config.xml")
/* jpa-config.xml里面有import spring-core，所以这里不能再加入 */
public class SsmRoleTest {
    @Autowired
    private SsmRoleService roleService;

    @Test
    public void saveRole(){
        SsmRole obj = new SsmRole();
        int fourNumber = (int) (Math.random() * 10000);
        //obj.setDescribe("onno,luanma"+ fourNumber);//乱码，错误精准定位*，缩小范围，发现是这里乱码,中文逗号也不行
        //obj.setType(1);
        roleService.save(obj);
        System.out.println("》》》"+ JSON.toJSONString(obj));
    }
}
