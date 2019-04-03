package com.ssm;

import com.google.common.base.Splitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssm.base.util.PropertyUtil;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class TestProperties {
	
	@Test
	public void test() {
		System.out.println("i am running TestProperties!!!!!!!!");
	}
	
	@Test
	public void aaaaaaa() {
		System.out.println(PropertyUtil.getProperty(PropertyUtil.WX_OPEN_ID, "defaultValue"));
		System.out.println(PropertyUtil.WX_OPEN_ID);
		String str = PropertyUtil.getProperty(PropertyUtil.IMPORT_EXCEL_STUDENT);
		//List<String> staticList = asList("JDK6", "JDK8", "JDK10");//asList 是 Arrays 的静态方法，staticList不能添加、删除等操作
		//List<String> list = new ArrayList<>(Arrays.asList("name", "age", "money", "mobile", "createTime"));
		List<String> list = Splitter.on(",").trimResults().splitToList(str);
		list.stream().forEach(i -> System.out.println(i));
	}

}
