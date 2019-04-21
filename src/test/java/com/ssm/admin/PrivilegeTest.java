package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.base.view.Result;
import com.ssm.common.enumeration.OperateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class PrivilegeTest {
    @Autowired private PrivilegeService privilegeService;

    @Test
    public void savePri(){
        SsmPrivilege obj = new SsmPrivilege();
        obj.setName(null);
        obj.setModuleId("mo123456");
        obj.setOperateEnumName(OperateEnum.update.name());
        obj.setValidDateId("永久有效");
        System.out.println("插入前》》"+ JSON.toJSONString(obj));
        Result result = privilegeService.create(obj);
        System.out.println("插入后》》"+ JSON.toJSONString(result.getData()));
    }

    @Test
    public void getPriByAccount(){
        String account = "YH302595";
        OperateEnum operate = OperateEnum.show;
        List<SsmPrivilege> privileges = privilegeService.listPrivilegeByAccount(account, operate);
        System.out.println("》》》》"+ JSON.toJSONString(privileges));
    }
}
