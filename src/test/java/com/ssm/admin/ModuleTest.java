package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.view.RecursionMenuVo;
import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.service.ModuleService;
import com.ssm.base.view.Result;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class ModuleTest {
    @Autowired private ModuleService moduleService;

    @Test
    public void saveModule(){
        SsmModule module = new SsmModule();
        module.setType((short) 1);
        module.setName("name");
        module.setUrl("aa/nn/kkk");
        moduleService.save(module);
        System.out.println("》》》"+ JSON.toJSONString(module));
    }

    @Test
    public void getMenus() {
        Result<?> result = moduleService.listMenuByRoleId(null);
        List<RecursionMenuVo> menus = (List<RecursionMenuVo>) result.getData();

        for(RecursionMenuVo each : menus){
            System.out.println("我好"+ each.getName());
            if(ObjectUtils.notEqual(each.getChildMenus(), null) && each.getChildMenus().size() > 0){
                cycleChild(each.getName(), each.getChildMenus());
            }
        }
    }

    //递归调用
    private void cycleChild(String pareantName, List<RecursionMenuVo> childMenus) {
        for(RecursionMenuVo each : childMenus){
            System.out.println("--"+ pareantName+ "--"+ each.getName());
            if(ObjectUtils.notEqual(each.getChildMenus(), null) && each.getChildMenus().size() > 0){
                System.out.println(">>"+ each.getName());
                cycleChild(each.getName(), each.getChildMenus());
            }
        }
    }
}
