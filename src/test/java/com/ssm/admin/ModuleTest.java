package com.ssm.admin;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.view.RecursionChildVo;
import com.ssm.admin.entity.SsmModule;
import com.ssm.base.service.ExcelService;
import com.ssm.base.util.poi.ReadExcel;
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
    @Autowired private ModuleService moduleService;
    @Autowired private ExcelService excelService;

    @Test
    public void saveModule(){
        SsmModule module = new SsmModule();
        module.setModuleId("mmmm123456");
        module.setType(1);
        module.setName("name");
        module.setUrl("aa/nn/kkk");
        //moduleService.save(module);
        System.out.println("》》》"+ JSON.toJSONString(module));
    }

    @Test
    public void importHelper(){
        Class clzz = SsmModule.class;
        Object cellVal = "1";
        try {
            //Class fieldClazz = clzz.getDeclaredField("belongModule").getType();
            Class fieldClazz = clzz.getField("belongModule").getType();
            Object fieldTypeValue = ReadExcel.getFieldTypeValue(fieldClazz, cellVal);
            System.out.println("》》"+ JSON.toJSON(fieldTypeValue));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println("---------END!----------");
    }

    @Test
    public void importModuleExcel(){
        String path = "D:\\GitHubProject\\SSMFirst\\src\\main\\resources\\ssm-config-file\\SSM_MODULE.xlsx";
        //String path = "D:\\LocalPicDev\\SSM_MODULE.xlsx";
        System.out.println("文件路径："+ path);
        File file = new File(path);
        try {
            FileInputStream input = new FileInputStream(file);

            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));

            Result result = excelService.readExcel(multipartFile, SsmModule.class);//单导入模拟数据

            Map<String, List> map = (Map<String, List>) result.getData();
            List<SsmModule> modules = map.get("SsmModule");
            moduleService.batchCreate(modules);

            System.out.println("********"+ JSON.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTop(){
        Result result = moduleService.getTopMenu();
        System.out.println("》》》"+ JSON.toJSONString(result.getData()));
    }

    @Test
    public void getMenus() {
        Result<?> result = moduleService.listByRole(null);
        List<RecursionChildVo> menus = (List<RecursionChildVo>) result.getData();

        for(RecursionChildVo each : menus){
            System.out.println("顶级菜单："+ each.getName());
            if(ObjectUtils.notEqual(each.getChildren(), null) && each.getChildren().size() > 0){
                cycleChild(each.getName(), each.getChildren());
            }
        }
    }

    //递归调用
    private void cycleChild(String pareantName, List<RecursionChildVo> childMenus) {
        for(RecursionChildVo each : childMenus){
            System.out.println("--"+ pareantName+ "--"+ each.getName());
            if(ObjectUtils.notEqual(each.getChildren(), null) && each.getChildren().size() > 0){
                System.out.println(">>"+ each.getName());
                cycleChild(each.getName(), each.getChildren());
            }
        }
    }
}
