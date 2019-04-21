package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.RoleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
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
public class RoleTest {
    @Autowired
    private RoleService roleService;

    @Test
    public void saveRole(){
        SsmRole obj = new SsmRole();
        obj.setType((short) 1);
        obj.setName("生成表角色");
        roleService.create(obj);
        System.out.println("》》》"+ JSON.toJSONString(obj));
    }

    @Test
    public void JpaQuery(){
        AdminQueryView query = new AdminQueryView();
        query.setPageNo(1);
        query.setPageSize(10);
        Result result = roleService.jpaQuery(query);
        System.out.println("》》》"+ JSON.toJSONString(result.getData()));
    }
}
