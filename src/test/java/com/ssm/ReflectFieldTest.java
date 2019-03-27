package com.ssm;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.admin.entity.SsmMenu;
import com.ssm.base.service.ReflectFieldService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class ReflectFieldTest {
	
	@Resource private ReflectFieldService reflectFieldService;
	
	@Test
	public void test(){
		System.out.println("can i do 打啊打！");
	}
	
	@Test
	public void menu(){
		SsmMenu menu = createObj();
		reflectFieldService.getKeyAndValue(menu);
		reflectFieldService.getValueByKey(menu, "icon");
	}

	private SsmMenu createObj() {
		SsmMenu menu = new SsmMenu();
		menu.setName("嫖了菜单！");
		menu.setSequence(12);
		menu.setIcon("Icon1205");
		menu.setCreateTime(new Date());
		return menu;
	}

}
