package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.common.enumeration.OperateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        SsmPrivilege reObj = privilegeService.save(obj);
        System.out.println("插入后》》"+ JSON.toJSONString(reObj));
    }
}
