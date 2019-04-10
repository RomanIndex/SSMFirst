package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.view.RecursionMenuVo;
import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.service.impl.ModuleServiceImpl;
import com.ssm.base.service.ExcelService;
import com.ssm.base.view.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class ModuleTest {
    @Autowired private ModuleServiceImpl moduleServiceImpl;
    @Autowired private ExcelService excelService;

    @Test
    public void saveModule(){
        SsmModule module = new SsmModule();
        module.setModuleId("mmmm123456");
        module.setType((short) 1);
        module.setName("name");
        module.setUrl("aa/nn/kkk");
        //moduleService.save(module);
        System.out.println("》》》"+ JSON.toJSONString(module));
    }

    @Test
    public void importModuleExcel(){
        String path = "D:\\SSMFile\\initTableExcel\\SSM_MODULE.xlsx";
        System.out.println("文件路径："+ path);
        File file = new File(path);
        try {
            FileInputStream input = new FileInputStream(file);

            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));

            Result result = excelService.readExcel(multipartFile, SsmModule.class);//单导入模拟数据

            Map<String, List> map = (Map<String, List>) result.getData();
            List<SsmModule> modules = map.get("SsmModule");
            moduleServiceImpl.batchSave(modules);

            System.out.println("********"+ JSON.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMenus() {
        Result<?> result = moduleServiceImpl.listMenuByRoleId(null);
        List<RecursionMenuVo> menus = (List<RecursionMenuVo>) result.getData();

        for(RecursionMenuVo each : menus){
            System.out.println("顶级菜单："+ each.getName());
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
