package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.RecursionMenu;
import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.service.SsmModuleService;
import com.ssm.base.view.Result;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class SsmModuleTest {
    @Autowired private SsmModuleService moduleService;

    @Test
    public void saveModule(){
        SsmModule module = new SsmModule();
        int fourNumber = (int) (Math.random() * 10000);
        module.setType(1);
        module.setModuleId("MD_"+ fourNumber);
        module.setName("iEnglishEcoding???");
        module.setUrl("o no ,i am nonull");
        //menuService.save(module);
        System.out.println("°∑°∑°∑"+ JSON.toJSONString(module));
    }

    @Test
    public void getMenus() {
        Result<?> result = moduleService.listMenuByRoleId(null);
        List<RecursionMenu> menus = (List<RecursionMenu>) result.getData();

        for(RecursionMenu each : menus){
            System.out.println("°∑°∑"+ each.getName());
            if(ObjectUtils.notEqual(each.getChildMenus(), null) && each.getChildMenus().size() > 0){
                cycleChild(each.getName(), each.getChildMenus());
            }
        }
    }

    //µ›πÈ±È¿˙£¨≤¢¥Ú”°
    private void cycleChild(String pareantName, List<RecursionMenu> childMenus) {
        for(RecursionMenu each : childMenus){
            System.out.println("--"+ pareantName+ "£∫"+ each.getName());
            if(ObjectUtils.notEqual(each.getChildMenus(), null) && each.getChildMenus().size() > 0){
                System.out.println(">>"+ each.getName());
                cycleChild(each.getName(), each.getChildMenus());
            }
        }
    }
}
