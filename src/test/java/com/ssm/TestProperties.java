package com.ssm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssm.base.util.PropertyUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class TestProperties {
	
	@Test
	public void test() {
		System.out.println("i am running TestProperties!!!!!!!!");
	}
	
	@Test
	public void aaaaaaa() {
		System.out.println(PropertyUtil.getProperty("AA_DOMAIN", "defaultValue"));
		System.out.println(PropertyUtil.WX_OPEN_ID);
	}

}
